package com.firetv.precondition;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import org.testng.Assert;
import org.testng.Reporter;

import com.firetv.commonpojo.AppTestDataPojo;
import com.firetv.util.UIMacroBase;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.PressesKey;
import io.appium.java_client.remote.MobileCapabilityType;

public class PDI
{
	static AppTestDataPojo appTestData = new AppTestDataPojo();
	private static AppiumDriver<?> driver;
	private static final String url = "http://127.0.0.1:4723/wd/hub";
	static int delay1 = 5000;
	static int delay2 = 10000;
	static String appStatus;
	static Boolean iselementpresent = false;
	static Boolean isScreensaverPresent = false;
	static int retryCount = 0;
	static Boolean result = false; //Set result to false
	static String DSN;
	static String ASIN;
	static String Package;
	static String LauncherClass;
	static LoadSheetIntoDB lsdb = new LoadSheetIntoDB();
	public static UIMacroBase macroBase = new UIMacroBase();
	

	public static void Test_PDIVerification(String ASIN) throws Exception
	{
		//ASIN =macroBase.getDeviceDSN();
		while (result == false && retryCount<=3)
		{
			System.out.println("result: "+result);
			System.out.println("retryCount: "+retryCount);
			retryCount++;
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
			capabilities.setCapability("deviceName", "FireTVStick");
			capabilities.setCapability("platformVersion", appTestData.getDeviceModel());
			capabilities.setCapability("udid", appTestData.getDsn());
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("noReset", true);
			capabilities.setCapability("fullReset", false);
			capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 1200);
			capabilities.setCapability("appPackage", "com.amazon.tv.launcher");
			capabilities.setCapability("appActivity", "com.amazon.tv.launcher.ui.HomeActivity_vNext");
			driver = new AndroidDriver<>(new URL(url), capabilities);
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			System.out.println("-Executing PDI and Launch Verification Test Case-");
			// Code to uninstall the app if present on device
			DSN=appTestData.getDsn();
			try {
				Process process = Runtime.getRuntime().exec("adb -s "+DSN+" uninstall " + appTestData.getAppPackage() + "");
				System.out.println("Uninstalling the app, if present");
				Thread.sleep(delay2); // Wait for 10 seconds to load the content
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// End of code to uninstall the app if present on device
	
			//Code to check for screen saver
			isScreensaverPresent = driver.findElementsById("com.amazon.ftv.screensaver:id/itemFrame").size() != 0;
			if (isScreensaverPresent == true)
			{
				System.out.println("Screensaver present. Dismissing the screensaver to proceed...");
				while (isScreensaverPresent == true)
				{
					((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.BACK));
					Thread.sleep(delay1);
					isScreensaverPresent = driver.findElementsById("com.amazon.ftv.screensaver:id/itemFrame").size() != 0;
				}
			}
			//End of code to check for screen saver
	
			// Code for app PDI
			try {
				Process process = Runtime.getRuntime().exec("adb shell am force-stop com.amazon.venezia");
				Thread.sleep(delay2);
				process = Runtime.getRuntime().exec("adb shell am start -d \"amzn://apps/android?asin="+ASIN+"\"");
				System.out.println("Launching the app detail page with ASIN: "+ASIN);
				Thread.sleep(delay2); // Wait for 10 seconds to load the content
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				iselementpresent = driver.findElementsById("com.amazon.venezia:id/buy_button").size() != 0;
				if (iselementpresent == true)
				{	
					appStatus = driver.findElementById("com.amazon.venezia:id/buy_button").getAttribute("contentDescription");
					if ((appStatus.equals("Get  Free to download")) || (appStatus.equals("Get Free to download")))
					{
						((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER)); //Click on Get button to start downloading app
						System.out.println("Downloading the app...");
						Thread.sleep(delay1); //Wait for 5 seconds to load the content
						((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER)); //Click on Ok button in case present
						Thread.sleep(delay1); //Wait for 5 seconds to load the content                              
					}
		
					if ((appStatus.equals("Download You own it")) || (appStatus.equals("Download  You own it")))
					{
						((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER)); //Click on Download button to start downloading app
						System.out.println("Downloading the app...");
						Thread.sleep(delay1); //Wait for 5 seconds to load the content
						((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER)); //Click on Ok button in case present
						Thread.sleep(delay1); //Wait for 5 seconds to load the content                              
					}
		
					if (appStatus.equals("Update"))
					{
						((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER)); //Click on Download button to start downloading app
						System.out.println("Old version of the app present on device, updating the app...");
						Thread.sleep(delay1); //Wait for 5 seconds to load the content
						((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER)); //Click on OK button in case present
						Thread.sleep(delay1); //Wait for 5 seconds to load the content                              
					}
		
					appStatus = driver.findElementById("com.amazon.venezia:id/buy_button").getAttribute("contentDescription");
					if (appStatus.contains("Queued") || appStatus.contains("Downloading") || appStatus.contains("Processing"))
					{
						System.out.println("Waiting for the app to finish downloading!");
						while (appStatus.contains("Queued") || appStatus.contains("Downloading") || appStatus.contains("Processing"))
						{
							//Code to check for screen saver
							isScreensaverPresent = driver.findElementsById("com.amazon.ftv.screensaver:id/itemFrame").size() != 0;
							if (isScreensaverPresent == true)
							{
								while (isScreensaverPresent == true)
								{
									((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.BACK));
									Thread.sleep(delay1);
									isScreensaverPresent = driver.findElementsById("com.amazon.ftv.screensaver:id/itemFrame").size() != 0;
								}
							}
							//End of code to check for screen saver
							Thread.sleep(delay1); //Wait for 5 seconds         
							appStatus = driver.findElementById("com.amazon.venezia:id/buy_button").getAttribute("contentDescription");
						}
					}
		
					appStatus = driver.findElementById("com.amazon.venezia:id/buy_button").getAttribute("contentDescription");
					if (appStatus.contains("Installing"))
					{
						System.out.println("Waiting for app to finish installing!");
						while (appStatus.contains("Installing"))
						{
							Thread.sleep(delay1); //Wait for 5 seconds
							//Code to check for screen saver
							isScreensaverPresent = driver.findElementsById("com.amazon.ftv.screensaver:id/itemFrame").size() != 0;
							if (isScreensaverPresent == true)
							{
								while (isScreensaverPresent == true)
								{
									((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.BACK));
									Thread.sleep(delay1);
									isScreensaverPresent = driver.findElementsById("com.amazon.ftv.screensaver:id/itemFrame").size() != 0;
								}
							}
							//End of code to check for screen saver
							appStatus = driver.findElementById("com.amazon.venezia:id/buy_button").getAttribute("contentDescription");                    
						}
					}
		
					Thread.sleep(delay1); //Wait for 5 seconds
			
					boolean isAppInsstalled = driver.isAppInstalled(appTestData.getAppPackage());
					System.out.println("isAppInstalled "+isAppInsstalled);
					if (isAppInsstalled == true)
					{
						result = true; //Set result to true
					}
				}
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				result = false;
				System.out.println("Catch Block ");
			}
			
			//End of code for app PDI
		}
		if (result == true)
		{
			Reporter.log("App downloaded and installed on the device successfully!", true);
			Assert.assertTrue(result); //Passes the test case
		}
		else
		{
			result = false; //Set result to false
			Reporter.log("App download and install failed on the device!", true);
			Assert.assertTrue(result); //Fails the test case
		}
		
	}
}
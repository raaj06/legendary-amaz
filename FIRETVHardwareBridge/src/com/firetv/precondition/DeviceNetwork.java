package com.firetv.precondition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import org.testng.Assert;
import org.testng.Reporter;

import com.firetv.commonpojo.AppTestDataPojo;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.PressesKey;
import io.appium.java_client.remote.MobileCapabilityType;

public class DeviceNetwork {

	AppTestDataPojo appTestData = new AppTestDataPojo();
	private AppiumDriver<?> driver;
	private static final String url = "http://127.0.0.1:4723/wd/hub";
	static int delay1s = 1000;
	static int delay2s = 2000;
	static int delay3s = 3000;
	static int delay4s = 4000;
	static int delay1 = 5000;
	static int delay2 = 10000;
	static int delay3 = 20000;
	static int delay4 = 30000;
	static int delay5 = 40000;
	static String NetworkConnected = "";
	static String DesiredNetwork = "";
	static String NetworkPassStatus = "";
	static String NetworkAvailable = "";
	static String NetworkAvailable_Down = "";
	static String NetworkAvailableRose = "";
	static String SeeAllNetworks = "";
	static String wifi_connection_status_detail = "";
	static String SeeAllNetworksRoses = "";
	static Boolean SeeAllNetworksPresent = false;
	static Boolean NetworkAvailablePresent = false;
	static Boolean NetworkAvailableRosePresent = false;
	static Boolean NetworkConnectedPresentRose = false;
	static Boolean SeeAllNetworksRosePresent = false;
	static Boolean RoseNetworkConnectedPresent = false;
	static Boolean iselementpresent = false;
	static Boolean NetworkConnectedPresent = false;
	static Boolean iselementpresent1 = false;
	static Boolean iselementpresent2 = false;
	static Boolean DeviceHomePage = false;
	static Boolean wifi_connection_status = false;
	static Boolean NotificationCheck = false;
	static Boolean wifi_connection_status2=false;
	int devicehomepageloadcount = 1;
	int availableNetworkcount = 1;
	static Boolean isScreensaverPresent = false;
	static Boolean isWiFiSettingsScreenPresent = false;
	static Boolean isNetworkNotFound = false;
	static Boolean isConfigurationFailure = false;
	static Boolean isInvalidPassword = false;
	static int retryCount = 0;
	static Boolean result = false; //Set result to false
	static int count1 = 0;
	static int count2 = 0;
	static String DSN;
	static String NetworkNeeded;
	static String NetworkPassword;


	public void DeviceNetworkPrecondition()
			throws InterruptedException, ParseException, MalformedURLException {
		Reporter.log("-Beginning Device Network Setup-", true);
		NetworkPassword=appTestData.getNetworkPassword();
		while (result == false && retryCount<=3)
		{
			try
			{
				if (retryCount>0)
				{
					System.out.println("-Retrying Device Network Setup-");
				}
				NetworkConnected = "";
				DesiredNetwork = "";
				NetworkPassStatus = "";
				NetworkAvailable = "";
				NetworkAvailable_Down = "";
				NetworkAvailableRose = "";
				SeeAllNetworks = "";
				wifi_connection_status_detail = "";
				SeeAllNetworksRoses = "";
				SeeAllNetworksPresent = false;
				NetworkAvailablePresent = false;
				NetworkAvailableRosePresent = false;
				NetworkConnectedPresentRose = false;
				SeeAllNetworksRosePresent = false;
				RoseNetworkConnectedPresent = false;
				iselementpresent = false;
				NetworkConnectedPresent = false;
				iselementpresent1 = false;
				iselementpresent2 = false;
				DeviceHomePage = false;
				wifi_connection_status = false;
				NotificationCheck = false;
				wifi_connection_status2=false;
				devicehomepageloadcount = 1;
				availableNetworkcount = 1;
				isScreensaverPresent = false;
				isWiFiSettingsScreenPresent = false;
				isNetworkNotFound = false;
				isConfigurationFailure = false;
				isInvalidPassword = false;
				result = false;
				count1 = 0;
				count2 = 0;
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
				driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
				// End of Launch Code
				DSN=appTestData.getDsn();
				//Code to check for screen saver presence
				isScreensaverPresent = driver.findElementsById("com.amazon.ftv.screensaver:id/itemFrame").size() != 0;
				if (isScreensaverPresent == true)
				{
					System.out.println("Screensaver present! Dismissing the screensaver to proceed...");
					while (isScreensaverPresent == true)
					{
						((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.BACK));
						Thread.sleep(delay2s); // Wait for 2 seconds to load the content
						isScreensaverPresent = driver.findElementsById("com.amazon.ftv.screensaver:id/itemFrame").size() != 0;
					}
				}
				//End of code to check for screen saver presence
		
				//Code to get Device Model
				Object deviceProperty = null;
				try {
					final Process process = Runtime.getRuntime().exec("adb -s "+DSN+" shell getprop"); //Execute adb command of X-ray output
					InputStream inStream = process.getInputStream();
					final BufferedReader br= new BufferedReader(
							new InputStreamReader(inStream));
					String line;
					while ((line = br.readLine()) != null) {
						deviceProperty = deviceProperty + line + "\n"; //Get dump of adb device property output to 'deviceProperty'
					}
					br.close();
					try {
						process.waitFor();
					}
					catch (final InterruptedException e) {
						System.out.println(e.getMessage());
					}
				} catch (final Exception e) {
					System.out.println(e.getMessage());
				}
		
				// System.out.println("deviceProperty: \n"+deviceProperty);
				String s;
				if(!((String) deviceProperty).isEmpty())
				{
					s = String.valueOf(deviceProperty); //Copy contents of deviceProperty to a string variable 's'
					// System.out.println("Value of s: \n"+s);
					Pattern pattern = Pattern.compile("\\[ro.product.model\\]: \\[(.*?)\\]"); //Regular expression to get value of model id from device property list
					Matcher matcher = pattern.matcher(s);
					while (matcher.find())
					{
						s=matcher.group(1); //Copy output of matched pattern from the above mentioned regular expression to the string variable 's'
					}
				}
				else
				{
					s = "No device connected";
				}
				//System.out.println("Connected Device Model: "+s); //Prints contents of string variable 's' which in turn contains Model ID of device
				//End of code to get Device Model
				DesiredNetwork=appTestData.getNetworkName();
				//End of code to set network values
				
				//Code to navigate to Settings screen
				count1 = 0; //Resetting count1
				while (isWiFiSettingsScreenPresent == false && count1<=3)
				{
					count2 = 0; //Resetting count2
					try {
						Process process = Runtime.getRuntime().exec("adb -s "+DSN+" shell am force-stop com.amazon.tv.settings.v2");
						Thread.sleep(delay1); // Wait for 5 seconds to load the content
						process = Runtime.getRuntime().exec("adb -s "+DSN+" shell am start -a android.settings.WIFI_SETTINGS -n com.amazon.tv.settings.v2/.tv.network.NetworkActivity");
						System.out.println("Navigating to Network Settings screen");
						Thread.sleep(delay1); // Wait for 5 seconds to load the content
						isWiFiSettingsScreenPresent = driver.findElementsById("ccom.amazon.tv.settings.v2:id/wifi_signal_strength_detail").size() != 0;
						while (isWiFiSettingsScreenPresent == false && count2 <=5)
						{
							process = Runtime.getRuntime().exec("adb -s "+DSN+" shell am start -a android.settings.WIFI_SETTINGS -n com.amazon.tv.settings.v2/.tv.network.NetworkActivity");
							Thread.sleep(delay2s); // Wait for 2 seconds to load the content
							isWiFiSettingsScreenPresent = driver.findElementsById("com.amazon.tv.settings.v2:id/wifi_signal_strength_detail").size() != 0;
							count2++;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					count1++;
				}
				//End of code to navigate to Settings screen
		
				//Code to navigate to Network settings and configuring network
				if (isWiFiSettingsScreenPresent == true)
				{
					//Code to configure WiFi network
					
					RoseNetworkConnectedPresent = driver.findElementsByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.view.ViewGroup/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.TextView[1]").size() != 0;
					if(RoseNetworkConnectedPresent == true)
					{
						NetworkConnected = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.view.ViewGroup/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.TextView[1]").getText();
					}
					else
					{
						NetworkConnectedPresent = driver.findElementsByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.view.View/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.TextView[1]").size() != 0;
						if(NetworkConnectedPresent == true)
						{
							NetworkConnected =  driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.view.View/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.TextView[1]").getText();
						}
					}
					
					wifi_connection_status = driver.findElementsById("com.amazon.tv.settings.v2:id/wifi_connection_status_detail").size() != 0;
					if (wifi_connection_status == true)
					{
						System.out.println("Network Connected: " + NetworkConnected);
					}
					if (String.valueOf(DesiredNetwork).equals(String.valueOf(NetworkConnected))) 
					{
						result = true; // Set result to true
						Reporter.log("Network: Device connected to " + DesiredNetwork + " Successfuly", true);
					}
					else 
					{
						System.out.println("Selecting " + DesiredNetwork + " to proceed");
						for (int i = 0; i <= 2; i++) 
						{
							Thread.sleep(delay1s);
							((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
						}
						Thread.sleep(delay1s);
						((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
						Thread.sleep(delay2s);
						NetworkAvailablePresent = driver.findElementsByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.view.View/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.TextView[1]").size() != 0;
						NetworkAvailableRosePresent = driver.findElementsByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.view.ViewGroup/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.TextView[1]").size() != 0;
						if(NetworkAvailablePresent == true)
						{
							NetworkAvailable = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.view.View/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.TextView[1]").getText();
						}
						else if(NetworkAvailableRosePresent){
							NetworkAvailable = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.view.ViewGroup/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.TextView[1]").getText();
						}
						if(NetworkAvailableRosePresent == true){
							while ((!String.valueOf(DesiredNetwork).equals(String.valueOf(NetworkAvailable)))&& (availableNetworkcount <= 19)) {
								((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));	
								Thread.sleep(1500);
								NetworkAvailable_Down = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.view.ViewGroup/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.TextView[1]").getText();
								if (String.valueOf(NetworkAvailable).equals(String.valueOf(NetworkAvailable_Down)))
								{
									availableNetworkcount = 20;
									System.out.println("Reached end of available networks");
								}
								else
								{
									NetworkAvailable = NetworkAvailable_Down;
									System.out.println("Available network by going down is " + NetworkAvailable);
									availableNetworkcount++;
								}
							}
						}
						if(NetworkAvailablePresent == true)
						{
							while ((!String.valueOf(DesiredNetwork).equals(String.valueOf(NetworkAvailable)))&& (availableNetworkcount <= 19)) 
							{
								((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
								Thread.sleep(1500);
								NetworkAvailable_Down = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.view.View/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.TextView[1]").getText();
								if (String.valueOf(NetworkAvailable).equals(String.valueOf(NetworkAvailable_Down)))
								{
									availableNetworkcount = 20;
									System.out.println("Reached end of available networks");
								}
								else
								{
									NetworkAvailable = NetworkAvailable_Down;
									System.out.println("Available network by going down is " + NetworkAvailable);
									availableNetworkcount++;
								}
							}
						}
						if((!String.valueOf(DesiredNetwork).equals(String.valueOf(NetworkAvailable)))
								&& (availableNetworkcount == 20))
						{
							result = false;
							Reporter.log("Device Network: Didn't find " + DesiredNetwork + " in Available Networks", true);						
						}
						else
						{
							iselementpresent = driver.findElementsById("com.amazon.tv.settings.v2:id/wifi_connection_status_detail").size() != 0;
							if (iselementpresent == true)
							{
								if(iselementpresent==true)
								{
									NetworkPassStatus = driver.findElementById("com.amazon.tv.settings.v2:id/wifi_connection_status_detail").getText();	
								}
								if (NetworkPassStatus.contains("Saved"))
								{
									((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
								}
								else
								{
									((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
									Thread.sleep(delay2s);
									try 
									{
										Process process = Runtime.getRuntime().exec("adb shell input text '" + NetworkPassword + "'");
										Thread.sleep(delay2s);
										((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.MEDIA_PLAY_PAUSE));
									} 
									catch (IOException e) 
									{
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								Thread.sleep(delay1s); // wait time to establish WiFi connection
								iselementpresent = driver.findElementsById("com.amazon.tv.settings.v2:id/wifi_connection_status_detail").size() != 0;
								if(iselementpresent==true)
								{
									wifi_connection_status_detail = driver.findElementById("com.amazon.tv.settings.v2:id/wifi_connection_status_detail").getText();
									count1 = 0;
									while (!(wifi_connection_status_detail.equals("Connected")) && count1<=5)
									{
										Thread.sleep(delay1s); // wait time to establish WiFi connection
										wifi_connection_status_detail = driver.findElementById("com.amazon.tv.settings.v2:id/wifi_connection_status_detail").getText();
										count1++;
									}
								}
								if (wifi_connection_status_detail.contains("Connecting")) 
								{
									result = false;
									Reporter.log("Network: Device connection to " + DesiredNetwork + " unsuccessful due to Invalid Password!", true);
								} 
								else
								{
									NetworkConnectedPresent = driver.findElementsByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.view.View/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.TextView[1]").size() != 0;
									NetworkConnectedPresentRose = driver.findElementsByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.view.ViewGroup/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.TextView[1]").size() != 0;
		
									if(NetworkConnectedPresent ==true){
										NetworkConnected = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.view.View/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.TextView[1]").getText();
									}else if(NetworkConnectedPresentRose ==true)
									{
										NetworkConnected = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.view.ViewGroup/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.TextView[1]").getText();
									}
									if ((String.valueOf(DesiredNetwork).equals(String.valueOf(NetworkConnected))) && (wifi_connection_status_detail.equals("Connected"))) 
									{
										result = true; // Set result to true
										Reporter.log("Network: Device connected to " + DesiredNetwork + " Successfuly", true);
									} 
									else 
									{
										result = false;
										Reporter.log("Device Network: Device connection to " + DesiredNetwork + " unsuccessful", true);
									}
								}
							}
							else
							{
								result = false;
								Reporter.log("Device Network: Didn't find " + DesiredNetwork + " in Available Networks", true);
							}
						}
					}
					//End of code to configure WiFi network
				}
				else
				{
					result = false;
					Reporter.log("Device Network: Failure configuring desired network!", true);
				}
			}
			catch (final Exception e)
			{
				result = false;
				Reporter.log("Device Network: Failure configuring desired network!", true);
			}
		}
		
		if (result == true)
		{
			Assert.assertTrue(result); //Passes the test case
		}
		else
		{
			Assert.assertTrue(result); //Fails the test case
		}
		driver.quit();
	}
}

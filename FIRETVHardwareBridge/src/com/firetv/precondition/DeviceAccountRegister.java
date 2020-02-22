package com.firetv.precondition;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.PressesKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import com.firetv.commonpojo.AppTestDataPojo;

public class DeviceAccountRegister {
	AppTestDataPojo appTestData = new AppTestDataPojo();
	private AppiumDriver<?> driver;
	int delay1s = 1000;
	int delay2s = 2000;
	int delay1 = 5000;
	int delay2 = 10000;
	int delay3 = 20000;
	int delay4 = 30000;
	int delay5 = 60000;
	String strTime1;
	String strTime2;
	String temp;
	DateFormat time = new SimpleDateFormat("mm:ss");
	Date time1;
	Date time2;
	
	static String Deregi;
	//static String Accountname;
	static String loggedInAccount;
	static Boolean osVersion;
	static Boolean wifiPasswordSave;
	static Boolean continuebutton;
	static Boolean alexaTerms;
	static Boolean isElementPresent;
	static Boolean isSettingsScreenPresent;
	static Boolean isScreensaverPresent;
	static Boolean roseExpFull;
	static Boolean signinScreen;
	static Boolean signingInScreen;
	static Boolean isActivationCodePresent;
	static Boolean chromeActivationSuccess;
	static Boolean iselementpresent;
	static Boolean deregisterInProgress;
	static int count = 0;
	static int count2 = 0;
	static int retryCount = 0;
	static Boolean result = false; //Set result to false
	static String DSN;
	static String AccountId;
	static String AccountPassword;
	static String Accountname;
	

	public void setUp() throws InterruptedException, MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "FireTVStick");
		capabilities.setCapability("platformVersion", appTestData.getDeviceModel());
		capabilities.setCapability("udid", appTestData.getDsn());
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("fullReset", false);
		capabilities.setCapability("newCommandTimeout", Integer.valueOf(1200));
		capabilities.setCapability("appPackage", "com.amazon.tv.launcher");
		capabilities.setCapability("appActivity", "com.amazon.tv.launcher.ui.HomeActivity_vNext");
		final String url = "http://127.0.0.1:4723/wd/hub";
		driver = new AndroidDriver<>(new URL(url), capabilities);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}

	public void launch() throws InterruptedException, ParseException
	{
		//Code to check for screen saver presence
		isScreensaverPresent = driver.findElementsById("com.amazon.ftv.screensaver:id/itemFrame").size() != 0;
		if (isScreensaverPresent == true)
		{
			System.out.println("Screensaver present! Dismissing the screensaver to proceed...");
			while (isScreensaverPresent == true)
			{
				((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.BACK));
				Thread.sleep(delay1); // Wait for 5 seconds to load the content
				isScreensaverPresent = driver.findElementsById("com.amazon.ftv.screensaver:id/itemFrame").size() != 0;
			}
		}
		//End of code to check for screen saver presence

		//Code to check presence of Release Notes
		iselementpresent = driver.findElementsById("com.amazon.tv.launcher:id/release_notes_container").size() != 0;
		if (iselementpresent == true) // Condition to check if app is present in foreground
		{
			System.out.println("Release Notes observed, dismissing it to proceed...");
			((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
			Thread.sleep(5000); // Wait for 5 seconds to load the content
		}
		//End of code to check presence of Release Notes
	}

	public boolean deviceAccountVerification(String DSN, String AccountId, String AccountPassword) throws InterruptedException, ParseException {
		Reporter.log("-Executing Account Verification Check-", true);
		try
		{
			System.out.println("Required Registered User: " + Accountname);
			try {
				Process process = Runtime.getRuntime().exec("adb -s "+DSN+" shell am start -n com.amazon.tv.launcher/com.amazon.tv.launcher.ui.HomeActivity_vNext");
				Thread.sleep(delay1); // Wait for 5 seconds to load the content
				process = Runtime.getRuntime().exec("adb -s "+DSN+" shell am start -a android.intent.action.MAIN -c android.intent.category.HOME --es navigate_node l_settings");
				Reporter.log("Navigating to Settings screen", true);
				Thread.sleep(delay1); // Wait for 5 seconds to load the content
				isSettingsScreenPresent = driver.findElementsById("com.amazon.tv.launcher:id/horizontal_grid_view").size() != 0;
				while (isSettingsScreenPresent == false && count <=5)
				{
					signinScreen = driver.findElementsById("com.amazon.tv.oobe:id/account_sign_in_option").size()!=0;
					if (signinScreen == true)
					{
						break;
					}
					roseExpFull= driver.findElements(By.id("com.amazon.tv.oobe:id/full_experience_option")).size()!=0;
					if (roseExpFull == true)
					{
						break;
					}
					process = Runtime.getRuntime().exec("adb -s "+DSN+" shell am start -a android.intent.action.MAIN -c android.intent.category.HOME --es navigate_node l_settings");
					Thread.sleep(delay1); // Wait for 5 seconds to load the content
					isSettingsScreenPresent = driver.findElementsById("com.amazon.tv.launcher:id/horizontal_grid_view").size() != 0;
					count++;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Code to check if device is not registered
			roseExpFull= driver.findElements(By.id("com.amazon.tv.oobe:id/full_experience_option")).size()!=0;
			if(roseExpFull)
			{
				driver.findElement(By.id("com.amazon.tv.oobe:id/full_experience_option")).click();
				Thread.sleep(delay1s); // Wait for 1 seconds to load the content
			}
			signinScreen = driver.findElementsById("com.amazon.tv.oobe:id/account_sign_in_option").size()!=0;
			//End of code to check if device is not registered
			if (signinScreen == false)
			{
				//Code to navigate to Settings screen
				while (isSettingsScreenPresent == false)
				{
					count = 0; //Resetting count
					try {
						Process process = Runtime.getRuntime().exec("adb -s "+DSN+" shell am start -n com.amazon.tv.launcher/com.amazon.tv.launcher.ui.HomeActivity_vNext");
						Thread.sleep(delay1); // Wait for 5 seconds to load the content
						process = Runtime.getRuntime().exec("adb -s "+DSN+" shell am start -a android.intent.action.MAIN -c android.intent.category.HOME --es navigate_node l_settings");
						Reporter.log("Navigating to Settings screen", true);
						Thread.sleep(delay1); // Wait for 5 seconds to load the content
						isSettingsScreenPresent = driver.findElementsById("com.amazon.tv.launcher:id/horizontal_grid_view").size() != 0;
						while (isSettingsScreenPresent == false && count <=5)
						{
							process = Runtime.getRuntime().exec("adb -s "+DSN+" shell am start -a android.intent.action.MAIN -c android.intent.category.HOME --es navigate_node l_settings");
							Thread.sleep(delay1); // Wait for 5 seconds to load the content
							isSettingsScreenPresent = driver.findElementsById("com.amazon.tv.launcher:id/horizontal_grid_view").size() != 0;
							count++;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		
				}
				//End of code to navigate to Settings screen
				
				//Code to navigate to Account Settings Screen
				((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
				Thread.sleep(500);
				for (int i = 0; i < 7; i++) {
					((PressesKey) driver).longPressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
					Thread.sleep(500);
				}
				/*for (int i = 0; i < 13; i++) {
					((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
					Thread.sleep(500);
				}*/
				((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
				//End of code to navigate to Account Settings Screen
				osVersion = driver.findElementsById("com.amazon.tv.settings.v2:id/action_description").size()!= 0;
				if (osVersion == true) {
					temp = driver.findElementById("com.amazon.tv.settings.v2:id/action_description").getText();
				}
		
				loggedInAccount = temp.substring(7); // Get current registered user name
				if (loggedInAccount.equalsIgnoreCase("sign in"))
				{
					System.out.println("Device not registered with any account");
				}
				else
				{
					System.out.println("Current Registered User: " + loggedInAccount);
				}
				if (Accountname.equalsIgnoreCase(loggedInAccount))
				{
					Reporter.log("Device already logged in with correct account. Device Account Registration Passed!", true);
					result = true;
					return result;
				}
				else
				{
					if (loggedInAccount.equalsIgnoreCase("sign in"))
					{
						Thread.sleep(delay1s); // Wait for 1 seconds to load the content
						((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
						//Code to wait for Amazon Account Page
						iselementpresent = driver.findElementsById("com.amazon.tv.settings.v2:id/action_title").size() != 0;
						while (iselementpresent == false && count<=2)
						{
							Thread.sleep(delay2s); // Wait for 2 seconds to load the content
							iselementpresent = driver.findElementsById("com.amazon.tv.settings.v2:id/action_title").size() != 0;
							count++;
						}
						//End of code to wait for Amazon Account Page
						((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER)); //Click on Register button
					}
					else
					{
						Thread.sleep(delay1s); // Wait for 1 seconds to load the content
						((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER)); //Click on Registered account name
						//Code to wait for Amazon Account Page
						iselementpresent = driver.findElementsById("com.amazon.tv.settings.v2:id/action_title").size() != 0;
						while (iselementpresent == false && count<=2)
						{
							Thread.sleep(delay2s); // Wait for 2 seconds to load the content
							iselementpresent = driver.findElementsById("com.amazon.tv.settings.v2:id/action_title").size() != 0;
							count++;
						}
						//End of code to wait for Amazon Account Page
						((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER)); //Click on Deregister button
						//Code to wait for Deregister Account Page
						iselementpresent = driver.findElementsById("android:id/parentPanel").size() != 0;
						while (iselementpresent == false && count<=2)
						{
							Thread.sleep(delay2s); // Wait for 2 seconds to load the content
							iselementpresent = driver.findElementsById("android:id/parentPanel").size() != 0;
							count++;
						}
						//End of code to wait for Deregister Account Page
						((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_LEFT)); //Navigate to Deregister button
						Thread.sleep(delay1s); // Wait for 1 seconds to load the content
						((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER)); //Click Deregister button
						Thread.sleep(delay1);
						deregisterInProgress = driver.findElementsById("com.amazon.tv.oobe:id/progress_dialog_title").size()!=0;
						if (deregisterInProgress == true) {
							System.out.println("De-registering in progress...");
							while (deregisterInProgress == true) {
								Thread.sleep(delay1s);
								deregisterInProgress = driver.findElementsById("com.amazon.tv.oobe:id/progress_dialog_title").size()!=0;
							}
							System.out.println("De-registration complete! Moving on with registration...");
						}
					}
					Thread.sleep(delay1s); // Wait for 1 seconds to load the content
					roseExpFull= driver.findElements(By.id("com.amazon.tv.oobe:id/full_experience_option")).size()!=0;
					if(roseExpFull)
					{
						driver.findElement(By.id("com.amazon.tv.oobe:id/full_experience_option")).click();
						Thread.sleep(delay1s); // Wait for 1 seconds to load the content
					}
				}
				signinScreen = driver.findElementsById("com.amazon.tv.oobe:id/account_sign_in_option").size()!=0;
				if (signinScreen == false)
				{
					count=0;
					System.out.println("Waiting for Sign in screen");
					while (signinScreen == false && count<=20)
					{
						Thread.sleep(delay1s); // Wait for 1 seconds to load the content
						signinScreen = driver.findElementsById("com.amazon.tv.oobe:id/account_sign_in_option").size()!=0;
						count++;
					}
				}
			}
			if (signinScreen == true)
			{
				System.out.println("Beginning registration process..");
				((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
				((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
				Thread.sleep(delay1s); // Wait for 1 seconds to load the content
				((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
				Thread.sleep(delay2s); // Wait for 2 seconds to load the content
				((WebElement) driver.findElementById("com.amazon.tv.oobe:id/account_fragment_frame")).sendKeys(new CharSequence[] { AccountId });
				Thread.sleep(delay1s); // Wait for 1 seconds to load the content
				((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.MEDIA_PLAY_PAUSE));
				Thread.sleep(delay2s); // Wait for 2 seconds to load the content
				((WebElement) driver.findElementById("com.amazon.tv.oobe:id/account_fragment_frame")).sendKeys(new CharSequence[] { AccountPassword });
				Thread.sleep(delay1s); // Wait for 1 seconds to load the content
				((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.MEDIA_PLAY_PAUSE));
				Thread.sleep(delay2s);
				signingInScreen = driver.findElementsById("com.amazon.tv.oobe:id/account_wait_message").size()!=0;
				if (signingInScreen == true)
				{
					count=0;
					System.out.println("Sign-in in progress, please wait!!");
					while (signingInScreen == true && count<=20)
					{
						Thread.sleep(delay1s); // Wait for 1 seconds to load the content
						signingInScreen = driver.findElementsById("com.amazon.tv.oobe:id/account_wait_message").size()!=0;
						count++;
					}
					System.out.println("Continuing...");
				}
				Thread.sleep(delay2);
				isActivationCodePresent = driver.findElementsById("com.amazon.tv.oobe:id/cbl_link_code").size() != 0;
				if (isActivationCodePresent == true)
				{
					count = 0;
					while (chromeActivationSuccess == false && count <=3)
					{
						temp = ((WebElement) driver.findElementById("com.amazon.tv.oobe:id/cbl_link_code")).getText();
						System.out.println("Launching Chrome to Activate the device using code "+temp);
						System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
						WebDriver driver2 = new ChromeDriver();
						Thread.sleep(delay1);
						driver2.get("https://www.amazon.com/code");
						Thread.sleep(delay2s);
						((RemoteWebDriver) driver2).findElementById("ap_email").sendKeys(new CharSequence[] { AccountId });
						Thread.sleep(delay1s);
						((RemoteWebDriver) driver2).findElementById("ap_password").sendKeys(new CharSequence[] { AccountPassword });
						((RemoteWebDriver) driver2).findElementById("signInSubmit").click();
						Thread.sleep(delay2s);
						((RemoteWebDriver) driver2).findElementById("cbl-registration-field").sendKeys(new CharSequence[] { temp });
						Thread.sleep(delay1s);
						((RemoteWebDriver) driver2).findElementById("cbl-continue-button").click();
						Thread.sleep(delay2s);
						iselementpresent = ((RemoteWebDriver) driver2).findElementsById("cbl-success-message").size() != 0;
						if (iselementpresent == false)
						{
							System.out.println("Waiting for account registration success message...");
							count2 = 0;
							while (iselementpresent == false && count2<=20)
							{
								iselementpresent = ((RemoteWebDriver) driver2).findElementsById("cbl-success-message").size() != 0;
								Thread.sleep(delay2s);
								count2++;
							}
						}
						if (iselementpresent == true)
						{
							System.out.println("Device activated from Chrome!");
							chromeActivationSuccess = true;
							driver2.quit();
						}
						else
						{
							System.out.println("Device activation from Chrome failed!");
							chromeActivationSuccess = false;
							driver2.quit();
						}
						count++;
					}
				}
				
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
				
				if(s.equals("AFTKMST12")) //AFTKMST12 = Rose/Keira
				{
					wifiPasswordSave = driver.findElementsById("android:id/button1").size()!=0;
					if(wifiPasswordSave == true)
					{
						((WebElement) driver.findElementById("android:id/button1")).click();
					}
					Thread.sleep(delay1); //Wait for 5 seconds
					alexaTerms = driver.findElementsById("com.amazon.tv.legal.notices:id/alexa_terms_continue_btn").size() != 0;
					if (alexaTerms == true)
					{
						System.out.println("Accepting Alexa's Terms of Use");
						((WebElement) driver.findElementById("com.amazon.tv.legal.notices:id/alexa_terms_continue_btn")).click();
						Thread.sleep(delay1);
					}
					//Code to navigate to Settings screen
					isSettingsScreenPresent = false;
					while (isSettingsScreenPresent == false)
					{
						count = 0; //Resetting count
						try {
							Process process = Runtime.getRuntime().exec("adb -s "+DSN+" shell am start -n com.amazon.tv.launcher/com.amazon.tv.launcher.ui.HomeActivity_vNext");
							Thread.sleep(delay1); // Wait for 5 seconds to load the content
							process = Runtime.getRuntime().exec("adb -s "+DSN+" shell am start -a android.intent.action.MAIN -c android.intent.category.HOME --es navigate_node l_settings");
							System.out.println("Navigating to Settings screen");
							Thread.sleep(delay1); // Wait for 5 seconds to load the content
							isSettingsScreenPresent = driver.findElementsById("com.amazon.tv.launcher:id/horizontal_grid_view").size() != 0;
							while (isSettingsScreenPresent == false && count <=5)
							{
								process = Runtime.getRuntime().exec("adb -s "+DSN+" shell am start -a android.intent.action.MAIN -c android.intent.category.HOME --es navigate_node l_settings");
								Thread.sleep(delay1); // Wait for 5 seconds to load the content
								isSettingsScreenPresent = driver.findElementsById("com.amazon.tv.launcher:id/horizontal_grid_view").size() != 0;
								count++;
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			
					}
					//End of code to navigate to Settings screen
					
					//Code to navigate to Account Settings Screen
					System.out.println("Navigating to Account Settings screen for Account Verification");
					((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
					Thread.sleep(delay1s);
					/*for (int i = 0; i < 13; i++) {
						((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
						Thread.sleep(500);
					}*/
					for (int i = 0; i < 7; i++) {
						((PressesKey) driver).longPressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
						Thread.sleep(500);
					}
					((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
					Thread.sleep(delay2s);
					//End of code to navigate to Account Settings Screen
					Thread.sleep(500);
					osVersion = driver.findElementsById("com.amazon.tv.settings.v2:id/action_description").size()!= 0;
					if (osVersion == true) {
						temp = driver.findElementById("com.amazon.tv.settings.v2:id/action_description").getText();
					}
			
					loggedInAccount = temp.substring(7); // Get current registered user name
					System.out.println("New Registered User: " + loggedInAccount);
					if (Accountname.equalsIgnoreCase(loggedInAccount))
					{
						result = true;
						Reporter.log("Device logged in with correct account. Device Account Registration Passed!", true);
						return result;
					}
					else
					{
						result = false;
						Reporter.log("Device account registration failed!", true);
						return result;
					}
				}
				else
				{
					continuebutton = driver.findElementsById("com.amazon.tv.oobe:id/tac_continue_button").size() != 0;
					count=0;
					if (continuebutton==false)
					{
						System.out.println("Waiting for device to be registered");
						while (continuebutton==false && count<=20)
						{
							Thread.sleep(this.delay1s);
							continuebutton = driver.findElementsById("com.amazon.tv.oobe:id/tac_continue_button").size() != 0;
							count++;								
						}
					}
					if (continuebutton==false)
					{
						result = false;
						Reporter.log("Unable to navigate to MY account page. Device account registration failed!", true);
						return result;
					}
					else
					{
						temp = driver.findElementById("com.amazon.tv.oobe:id/tac_main_text").getText(); //Get name of new account registered in variable temp
						((WebElement) driver.findElementById("com.amazon.tv.oobe:id/tac_continue_button")).click();
						Thread.sleep(delay1);
						wifiPasswordSave = driver.findElementsById("android:id/button1").size()!=0;
						if(wifiPasswordSave == true)
						{
							((WebElement) driver.findElementById("android:id/button1")).click();
						}
						Thread.sleep(delay1); //Wait for 5 seconds
						alexaTerms = driver.findElementsById("com.amazon.tv.legal.notices:id/alexa_terms_continue_btn").size() != 0;
						if (alexaTerms == true)
						{
							System.out.println("Accepting Alexa's Terms of Use");
							((WebElement) driver.findElementById("com.amazon.tv.legal.notices:id/alexa_terms_continue_btn")).click();
							Thread.sleep(delay1);
						}
						
				
						loggedInAccount = temp.substring(4,(temp.length()-1)); // Get current registered user name
						System.out.println("New Registered User: " + loggedInAccount);
						if (Accountname.equalsIgnoreCase(loggedInAccount))
						{
							result = true;
							Reporter.log("Device logged in with correct account. Device Account Registration Passed!", true);
							return result;
						}
						else
						{
							result = false;
							Reporter.log("Device account registration failed!", true);
							return result;
						}
					}
				}
			}
			else
			{
				result = false; //Set result to false
				Reporter.log("Failed to load Sign In screen. Device account registration failed!", true);
				return result;
			}
		}
		catch (Exception Exception) 
		{
			result = false;
			Reporter.log("Device account registration configuration failed!", true);
			return result;
		}
	}

	public void deviceAccountSetup() throws Exception
	{
		osVersion = false;
		wifiPasswordSave = false;
		continuebutton = false;
		alexaTerms = false;
		isElementPresent = false;
		isSettingsScreenPresent = false;
		isScreensaverPresent = false;
		roseExpFull = false;
		signinScreen = false;
		signingInScreen = false;
		isActivationCodePresent = false;
		chromeActivationSuccess = false;
		iselementpresent = false;
		deregisterInProgress = false;
		count = 0;
		count2 = 0;
		retryCount = 0;
		result = false; //Sets result to false
		DSN=appTestData.getDsn();
		AccountId=appTestData.getDeviceUsername();
		AccountPassword=appTestData.getDevicePassword();
		Accountname=appTestData.getAccoutName();
		while (result == false && retryCount<=3)
		{
			if (retryCount>0)
			{
				System.out.println("-Retrying Device Network Setup-");
			}
			setUp();
			launch();
			result = deviceAccountVerification(DSN, AccountId, AccountPassword);
			retryCount++;
		}
		//Final verification of Account Verification test case
		if (result == true)
		{
			Assert.assertTrue(result); //Passes the test case
		}
		else
		{
			Assert.assertTrue(result); //Fails the test case
		}
		//End of final verification of Account Verification test case
	}
}

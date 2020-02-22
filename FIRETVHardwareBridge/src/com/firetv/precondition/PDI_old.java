package com.firetv.precondition;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.Reporter;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.PressesKey;
import io.appium.java_client.remote.MobileCapabilityType;

public class PDI_old {
	private static AppiumDriver<?> driver;
	private static final String url = "http://127.0.0.1:4723/wd/hub";
	static int delay1 = 5000;
	static int delay2 = 10000;
	int delay3 = 20000;
	int delay4 = 30000;
	static int delay5 = 60000;
	String strTime1;
	String strTime2;
	DateFormat time = new SimpleDateFormat("mm:ss");
	Date time1;
	Date time2;
	static Boolean iselementpresent = false;
	static Boolean iselementpresent1 = false;
	static String ASINinCloud = "";
	static String registereddeviceName = "";
	static Boolean IsNextButtonpresent1 = true;
	static Boolean IsNextButtonpresent2 = true;
	static Boolean AppPushStatus = true;
	static Boolean StillSignInButtonPresent = true;
	static Boolean CodeVerificationNeeded = true;
	static String FireTVOption = "";

	public static void Test_PDIVerification(String deviceOS, String DSN, String deviceUsername, String devicePassword,
			String ASIN, String PreferedMarketPlace, String Package, String LauncherClass)
					throws InterruptedException, ParseException, MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability("deviceName", "FireTVStick");
		capabilities.setCapability("platformVersion", deviceOS);
		capabilities.setCapability("udid", DSN);
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
		try {
			Process process = Runtime.getRuntime().exec("adb uninstall " + Package + "");
			System.out.println("Uninstalling the app, if present");
			Thread.sleep(delay2); // Wait for 10 seconds to load the content
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// End of code to uninstall the app if present on device
		// Code to get the device name
		((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.HOME));
		Thread.sleep(1000);
		for (int i = 0; i <= 12; i++) {
			((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
			Thread.sleep(1000);
		}
		((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		Thread.sleep(1000);
		for (int i = 0; i <= 15; i++) {
			((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
			Thread.sleep(500);
		}
		Thread.sleep(1000);
		for (int i = 0; i <= 2; i++) {
			((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
			Thread.sleep(500);
		}
		((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		Thread.sleep(2000);
		iselementpresent = driver.findElementsById("com.amazon.tv.settings:id/left_pane_title").size() != 0;
		if (iselementpresent == true) {
			FireTVOption = driver.findElementById("com.amazon.tv.settings:id/left_pane_title").getText();
		}
		iselementpresent1 = driver.findElementsById("com.amazon.tv.settings.v2:id/left_pane_title").size() != 0;
		if (iselementpresent1 == true) {
			FireTVOption = driver.findElementById("com.amazon.tv.settings.v2:id/left_pane_title").getText();
		}

		if (FireTVOption.contains("Network")) {
			for (int i = 0; i <= 15; i++) {
				((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
				Thread.sleep(500);
			}
			Thread.sleep(1000);
			for (int i = 0; i <= 2; i++) {
				((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
				Thread.sleep(500);
			}
			((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
			Thread.sleep(1000);
		}
		((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		Thread.sleep(2000);
		iselementpresent = driver.findElementsById("com.amazon.tv.settings:id/about_device_name_value").size() != 0;
		if (iselementpresent == true) {
			registereddeviceName = driver.findElementById("com.amazon.tv.settings:id/about_device_name_value")
					.getText();
		}
		iselementpresent1 = driver.findElementsById("com.amazon.tv.settings.v2:id/about_device_name_value").size() != 0;
		if (iselementpresent1 == true) {
			registereddeviceName = driver.findElementById("com.amazon.tv.settings.v2:id/about_device_name_value")
					.getText();
		}
		Thread.sleep(2000);
		System.out.println("DeviceName : " + registereddeviceName);
		// End of code to get the device name

		// Code for app PDI
		/*
		 * ChromeOptions options = new ChromeOptions();
		 * options.addArguments("--headless");
		 * System.setProperty("webdriver.chrome.driver","chromedriver.exe");
		 * WebDriver driver2 =new ChromeDriver(options);
		 */

		WebDriver driver2 = null;
		ChromeOptions options = new ChromeOptions();

		String OSName = null;
		OSName = System.getProperty("os.name");
		OSName = OSName.toLowerCase();
		if (OSName.contains("mac")) {
			String homeDirectorName = System.getProperty("user.home");
			System.setProperty("webdriver.chrome.driver", homeDirectorName + "/Documents/driver/chromedriver");
			// options.addArguments("--no-sandbox");
			options.addArguments(new String[] { "----disable-notifications" });
			driver2 = new ChromeDriver(options);
			driver2.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
			// webDriver.get(webLink);
			driver2.switchTo().defaultContent();
		}

		else if (OSName.contains("windows")) {
			String homeDirectorName = System.getProperty("user.home");
			System.setProperty("webdriver.chrome.driver", homeDirectorName + "/Documents/driver/chromedriver.exe");
			options.addArguments(new String[] { "----disable-notifications" });
			driver2 = new ChromeDriver(options);
			// webDriver.get(webLink);
			driver2.manage().window().maximize();
		} else {
			System.out.println("Given OS is not supported for testing");
		}

		// String homeDirectorName = System.getProperty("user.home");
		// System.setProperty("webdriver.chrome.driver", homeDirectorName +
		// "/Documents/driver/chromedriver");
		// /* System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		// */
		// WebDriver driver2 = new ChromeDriver();

		Thread.sleep(delay1); // Wait for 5 seconds to load the content
		if (PreferedMarketPlace.contains("US")) {
			driver2.get("https://www.amazon.com/");
		}
		if (PreferedMarketPlace.contains("UK")) {
			driver2.get("https://www.amazon.co.uk/");
		}
		if (PreferedMarketPlace.contains("DE")) {
			driver2.get("https://www.amazon.de/");
		}
		System.out.println("Navigating to Amazon Portal");
		Thread.sleep(delay2); // Wait for 10 seconds to load the content
		((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.HOME));
		if (PreferedMarketPlace.contains("US")) {
			((RemoteWebDriver) driver2).findElementById("nav-link-accountList").click();
		}
		if ((PreferedMarketPlace.contains("UK")) || (PreferedMarketPlace.contains("DE"))) {
			((RemoteWebDriver) driver2).findElementById("nav-link-yourAccount").click();
		}
		System.out.println("Clicking on Sign In");
		Thread.sleep(delay1); // Wait for 5 seconds to load the content
		((RemoteWebDriver) driver2).findElementById("ap_email").sendKeys(deviceUsername);
		System.out.println("Entered Username : " + deviceUsername);
		Thread.sleep(delay1); // Wait for 5 seconds to load the content
		((RemoteWebDriver) driver2).findElementById("ap_password").sendKeys(devicePassword);
		System.out.println("Entered Password : " + devicePassword);
		Thread.sleep(delay1);
		StillSignInButtonPresent = ((RemoteWebDriver) driver2).findElementsById("signInSubmit").size() != 0;
		System.out.println(" Sign In Button Present  : " + StillSignInButtonPresent);
		((RemoteWebDriver) driver2).findElementById("signInSubmit").click();
		System.out.println("Clicking on Sign in After entering Credentials ");
		Thread.sleep(delay1); // Wait for 5 seconds to load the content
		StillSignInButtonPresent = ((RemoteWebDriver) driver2).findElementsById("signInSubmit").size() != 0;
		System.out.println("Sign In Button Present After Login : " + StillSignInButtonPresent);
		CodeVerificationNeeded = ((RemoteWebDriver) driver2)
				.findElementsByXPath("//*[@id=\"cvf-page-content\"]/div[1]/div/div/form/div[1]/h1").size() != 0;
		System.out.println("Verification Needed ? " + CodeVerificationNeeded);
		if ((StillSignInButtonPresent == false) && (CodeVerificationNeeded == false)) {
			if (PreferedMarketPlace.contains("US")) {
				driver2.get("https://www.amazon.com/gp/mas/your-account/myapps?sort=b");
			}
			if (PreferedMarketPlace.contains("UK")) {
				driver2.get("https://www.amazon.co.uk/gp/mas/your-account/myapps?sort=b");
			}
			if (PreferedMarketPlace.contains("DE")) {
				driver2.get("https://www.amazon.de/gp/mas/your-account/myapps?sort=b");
			}
			System.out.println("Navigating to Apps and Devices Section");
			Thread.sleep(delay2);
			((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.HOME));
			int rowcount = 2;
			int nextpagecount = 5;
			iselementpresent1 = true;
			JavascriptExecutor jse = (JavascriptExecutor) driver2;
			int Page = 1;
			while ((rowcount <= 11) && (iselementpresent1 = true)
					&& (((IsNextButtonpresent1 == true) || (IsNextButtonpresent2 == true)))) {
				iselementpresent1 = ((RemoteWebDriver) driver2)
						.findElementsByXPath(
								"//*[@id=\"masrw-my-apps\"]/table/tbody/tr[" + rowcount + "]/td[1]/div[2]/a")
						.size() != 0;
				if (iselementpresent1 == true) {
					ASINinCloud = ((RemoteWebDriver) driver2)
							.findElementByXPath(
									"//*[@id=\"masrw-my-apps\"]/table/tbody/tr[" + rowcount + "]/td[1]/div[2]/a")
							.getAttribute("href");
					if (ASINinCloud.contains(ASIN)) {
						((RemoteWebDriver) driver2).findElementById("a-autoid-" + (rowcount - 2) + "-announce").click();
						System.out.println("Apps Found : Clicking on Action Button");
						break;
					} else {
						jse.executeScript("scroll(0, 70);");
						rowcount++;
					}
				}
				if (rowcount == 12) {
					jse.executeScript("scroll(0, 670);");
					Thread.sleep(1000);
					IsNextButtonpresent1 = ((RemoteWebDriver) driver2)
							.findElementsByXPath("//*[@id=\"masrw-my-apps\"]/table/tbody/tr[" + rowcount
									+ "]/td/div/ul/li[" + nextpagecount + "]/a")
							.size() != 0;
					IsNextButtonpresent2 = ((RemoteWebDriver) driver2)
							.findElementsByXPath(
									"//*[@id=\"masrw-my-apps\"]/table/tbody/tr[" + rowcount + "]/td/div/ul/li[7]/a")
							.size() != 0;
					if ((IsNextButtonpresent1 == true) || (IsNextButtonpresent2 == true)) {
						System.out.println("Apps not found in the page " + Page + " : Clicking on Next Page...");
						if (nextpagecount > 7) {
							((RemoteWebDriver) driver2).findElementByXPath(
									"//*[@id=\"masrw-my-apps\"]/table/tbody/tr[" + rowcount + "]/td/div/ul/li[7]/a")
							.click(); // Clicking on next page
						} else {
							((RemoteWebDriver) driver2).findElementByXPath("//*[@id=\"masrw-my-apps\"]/table/tbody/tr["
									+ rowcount + "]/td/div/ul/li[" + nextpagecount + "]/a").click();
						}
						Thread.sleep(delay1);
						rowcount = 2;
						nextpagecount++;
						Page++;
						if ((Page >= 3) && (Page % 3 == 0)) {
							((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.HOME));
						}
					}

				}
			}
			Thread.sleep(5000);
			if (((IsNextButtonpresent1 == true) || (IsNextButtonpresent2 == true)) && (iselementpresent1 == true)) {
				Thread.sleep(delay1);
				((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.HOME));
				AppPushStatus = AppPushMethod(driver, driver2, PreferedMarketPlace, registereddeviceName, ASINinCloud,
						ASIN);
				Thread.sleep(delay2);
				if (AppPushStatus == true) {
					System.out.println("App sent to device ");
					((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.HOME));
					driver2.quit();
					((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_UP));
					boolean isappinsstalled = driver.isAppInstalled(Package);
					if (isappinsstalled == false) {
						System.out.println(
								"Waiting for app to be downloaded and installed on the device. Please wait...");
						while (isappinsstalled == false) {
							Thread.sleep(delay2);
							isappinsstalled = driver.isAppInstalled(Package);
						}
					}
					Thread.sleep(delay1);
					System.out.println("App downloaded and installed on the device. Launching now...");
					Thread.sleep(2000);

				} else {
					boolean result = false; // Set result to false
					Reporter.log("Error Occured while pushing the app", true);
					Assert.assertTrue(result); // Fails the test case
				}
			} else {
				driver2.quit();
				boolean result = false; // Set result to false
				Reporter.log("App is not present in " + deviceUsername + "'s account", true);
				Assert.assertTrue(result); // Fails the test case
			}
		} else {
			boolean result = false; // Set result to false
			String Errormsg = "";
			iselementpresent = ((RemoteWebDriver) driver2)
					.findElementsByXPath("//*[@id=\"auth-error-message-box\"]/div/div/ul/li/span").size() != 0;
			if (iselementpresent == true) {
				Errormsg = ((RemoteWebDriver) driver2)
						.findElementByXPath("//*[@id=\"auth-error-message-box\"]/div/div/ul/li/span").getText();
			}
			iselementpresent1 = ((RemoteWebDriver) driver2)
					.findElementsByXPath("//*[@id=\"cvf-page-content\"]/div[1]/div/div/form/div[1]/h1").size() != 0;
			if (iselementpresent1 == true) {
				Errormsg = ((RemoteWebDriver) driver2)
						.findElementByXPath("//*[@id=\"cvf-page-content\"]/div[1]/div/div/form/div[1]/h1").getText();
			}
			Reporter.log("There is Error with Credential : Error msg - " + Errormsg + "", true);
			Assert.assertTrue(result); // Fails the test case
			driver2.quit();
		}
	}

	public static boolean AppPushMethod(AppiumDriver<?> driver, WebDriver driver2, String PreferedMarketPlace,
			String registereddeviceName, String ASINinCloud, String ASIN) throws InterruptedException {
		((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.HOME));
		int g = 1;
		boolean iselementpresent = true;
		boolean elementpresent = true;
		String success = "";
		String Status = "";
		String success_en = "";
		if (PreferedMarketPlace.contains("US")) {
			success = "Thanks!";
			Status = "Thanks!";
		}
		if (PreferedMarketPlace.contains("UK")) {
			success = "Thanks!";
			Status = "Thanks!";
		}
		if (PreferedMarketPlace.contains("DE")) {
			success = "Danke!";
			Status = "Danke!";
			success_en = "Thanks!";
		}
		((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.HOME));
		if (g > 1) {
			System.out.println("Unable to push the app : Retrying " + g);
			if (PreferedMarketPlace.contains("US")) {
				driver2.get("https://www.amazon.com/gp/mas/your-account/myapps?sort=b");
			}
			if (PreferedMarketPlace.contains("UK")) {
				driver2.get("https://www.amazon.co.uk/gp/mas/your-account/myapps?sort=b");
			}
			if (PreferedMarketPlace.contains("DE")) {
				driver2.get("https://www.amazon.de/gp/mas/your-account/myapps?sort=b");
			}
			System.out.println("Navigating to Apps and Devices Section");
			Thread.sleep(10000);
			((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.HOME));
			int rowcount = 2;
			int nextpagecount = 5;
			int Pagecount = 1;
			boolean iselementpresent1 = true;
			boolean IsNextButtonpresent1 = true;
			boolean IsNextButtonpresent2 = true;
			JavascriptExecutor jse = (JavascriptExecutor) driver2;
			while ((rowcount <= 11) && ((IsNextButtonpresent1 == true) || (IsNextButtonpresent2 == true))
					&& (iselementpresent1 = true)) {
				iselementpresent1 = ((RemoteWebDriver) driver2)
						.findElementsByXPath(
								"//*[@id=\"masrw-my-apps\"]/table/tbody/tr[" + rowcount + "]/td[1]/div[2]/a")
						.size() != 0;
				if (iselementpresent1 == true) {
					ASINinCloud = ((RemoteWebDriver) driver2)
							.findElementByXPath(
									"//*[@id=\"masrw-my-apps\"]/table/tbody/tr[" + rowcount + "]/td[1]/div[2]/a")
							.getAttribute("href");
					if (ASINinCloud.contains(ASIN)) {
						((RemoteWebDriver) driver2).findElementById("a-autoid-" + (rowcount - 2) + "-announce").click();
						System.out.println("Apps Found : Clicking on Action Button");
						break;
					} else {
						jse.executeScript("scroll(0, 70);");
						rowcount++;
					}
				}
				if (rowcount == 12) {
					System.out.println("Apps not found in the page " + Pagecount + " : Clicking on Next Page...");
					jse.executeScript("scroll(0, 670);");
					Thread.sleep(1000);
					IsNextButtonpresent1 = ((RemoteWebDriver) driver2)
							.findElementsByXPath("//*[@id=\"masrw-my-apps\"]/table/tbody/tr[" + rowcount
									+ "]/td/div/ul/li[" + nextpagecount + "]/a")
							.size() != 0;
					IsNextButtonpresent2 = ((RemoteWebDriver) driver2)
							.findElementsByXPath(
									"//*[@id=\"masrw-my-apps\"]/table/tbody/tr[" + rowcount + "]/td/div/ul/li[7]/a")
							.size() != 0;
					{
						if (nextpagecount > 7) {
							((RemoteWebDriver) driver2).findElementByXPath(
									"//*[@id=\"masrw-my-apps\"]/table/tbody/tr[" + rowcount + "]/td/div/ul/li[7]/a")
							.click(); // Clicking on next page
						} else {
							((RemoteWebDriver) driver2).findElementByXPath("//*[@id=\"masrw-my-apps\"]/table/tbody/tr["
									+ rowcount + "]/td/div/ul/li[" + nextpagecount + "]/a").click();
						}
						Thread.sleep(5000);
						rowcount = 2;
						nextpagecount++;
						Pagecount++;
					}
				}
			}
		}

		int popupcount = 1;
		elementpresent = ((RemoteWebDriver) driver2)
				.findElementsByXPath("//*[@id=\"a-popover-content-" + popupcount + "\"]/ul/span/a").size() != 0;
		while ((elementpresent == false) && (popupcount <= 11)) {
			popupcount++;
			elementpresent = ((RemoteWebDriver) driver2)
					.findElementsByXPath("//*[@id=\"a-popover-content-" + popupcount + "\"]/ul/span/a").size() != 0;
			if (elementpresent == true) {
				break;
			} else {
				continue;
			}
		}
		if (elementpresent == true) {
			((RemoteWebDriver) driver2)
			.findElementByXPath("//*[@id=\"a-popover-content-" + popupcount + "\"]/ul/span/a").click();
		}
		System.out.println("Clicking on Deliver to Option");
		Thread.sleep(5000);
		((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.HOME));
		boolean iselementpresent2 = false;
		boolean iselementpresent3 = false;
		iselementpresent2 = ((RemoteWebDriver) driver2)
				.findElementsByXPath("/html/body/div[6]/div/div/div[1]/div[2]/div/div/span/span/span/span").size() != 0;
		int popovercount=0;
		iselementpresent3 = ((RemoteWebDriver) driver2)
				.findElementsByXPath("//*[@id=\"a-popover-content-"+popovercount+"\"]/div[2]/div/div/span/span/span/span").size() != 0;
		while ((iselementpresent3 == false) && (popovercount <= 11)) {
			popovercount++;
			iselementpresent3 = ((RemoteWebDriver) driver2)
					.findElementsByXPath("//*[@id=\"a-popover-content-"+popovercount+"\"]/div[2]/div/div/span/span/span/span").size() != 0;
			if (iselementpresent3 == true) {
				break;
			} else {
				continue;
			}
		}
		if ((iselementpresent2 == false) && (iselementpresent3 == false)) {
			((RemoteWebDriver) driver2).findElementByXPath("//*[@id=\"deliverAppButton\"]/span/input").click();
		} else {
			if(iselementpresent2)
			{
				((RemoteWebDriver) driver2)
				.findElementByXPath("/html/body/div[6]/div/div/div[1]/div[2]/div/div/span/span/span/span").click();
			}
			if(iselementpresent3)
			{
				((RemoteWebDriver) driver2)
				.findElementByXPath("//*[@id=\"a-popover-content-"+popovercount+"\"]/div[2]/div/div/span/span/span/span").click();
			}
			System.out.println("Clicking on Dropdown list to select device to push the app");
			Thread.sleep(3000);
			((RemoteWebDriver) driver2).findElementByLinkText(registereddeviceName).click();
			System.out.println("Selecting " + registereddeviceName + " to push the app");
			Thread.sleep(2000);
			boolean iselementpresent4 = false;
			boolean iselementpresent5 = false;
			iselementpresent4 = ((RemoteWebDriver) driver2)
					.findElementsByXPath("/html/body/div[6]/div/div/div[2]/div[1]/span[2]/span/input").size() != 0;
			iselementpresent5 = ((RemoteWebDriver) driver2)
					.findElementsByXPath("//*[@id=\"deliverAppButton\"]/span/input").size() != 0;
			if(iselementpresent4)
			{
				((RemoteWebDriver) driver2).findElementByXPath("/html/body/div[6]/div/div/div[2]/div[1]/span[2]/span/input")
				.click();
			}
			if(iselementpresent5)
			{
				((RemoteWebDriver) driver2).findElementByXPath("//*[@id=\"deliverAppButton\"]/span/input")
				.click();
			}
			System.out.println("Clicking on deliver button");
		}
		Thread.sleep(5000);
		popupcount = 1;
		elementpresent = ((RemoteWebDriver) driver2)
				.findElementsByXPath("//*[@id=\"a-popover-content-" + popupcount + "\"]/div[2]/div/div/strong[1]")
				.size() != 0;
		while ((elementpresent == false) && (popupcount <= 11)) {
			popupcount++;
			elementpresent = ((RemoteWebDriver) driver2)
					.findElementsByXPath("//*[@id=\"a-popover-content-" + popupcount + "\"]/div[2]/div/div/strong[1]")
					.size() != 0;
			if (elementpresent == true) {
				break;
			} else {
				continue;
			}
		}
		if (elementpresent == true) {
			Status = ((RemoteWebDriver) driver2)
					.findElementByXPath("//*[@id=\"a-popover-content-" + popupcount + "\"]/div[2]/div/div/strong[1]")
					.getText();
			Thread.sleep(1000);

		} else {
			Status = "Error";
		}
		g++;
		System.out.println("Pushing Status on " + (g - 1) + " attempt : " + Status);
		while ((!((Status.contains(success)) || (Status.contains(success_en)))) && (g <= 3))
			;
		((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.HOME));
		if (g == 4) {
			iselementpresent = false;
		}
		driver2.quit();
		return iselementpresent;
	}

}
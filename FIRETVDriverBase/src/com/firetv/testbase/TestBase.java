package com.firetv.testbase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.asserts.SoftAssert;

import com.amazon.coral.google.common.base.Throwables;
import com.firetv.api.JsonHandling;
import com.firetv.api.JsonHandlingVendorApps;
import com.firetv.commonpojo.AppTestDataPojo;
import com.firetv.commonpojo.AppTestPojoVendorApps;
import com.firetv.util.UIMacroBase;
import com.google.common.util.concurrent.UncheckedExecutionException;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.events.api.general.AppiumWebDriverEventListener;

public class TestBase {

	public static Properties prop;
	public static AppiumDriver<?> driver;
	static String platformVersion;
	static String appPackage;
	static String appActivity;
	public static WebDriver webDriver;
	static URL url;
	public static AppiumWebDriverEventListener e_driver;
	public static Logger log = Logger.getLogger(TestBase.class.getName());
	static DesiredCapabilities capabilities = DesiredCapabilities.android();
	public static String test_apps_json_path;
	public static AppTestDataPojo appInput = new AppTestDataPojo();
	public static SoftAssert softAssert = new SoftAssert();
	String DSN;

	public TestBase() {
		FileInputStream ip = null;
		String workSpaceLocation = System.getProperty("user.dir");
		String workspacePath = workSpaceLocation.replace("FIRETVSteadyStateTests", "FIRETVDriverBase");
		try {
			prop = new Properties();
			ip = new FileInputStream(workspacePath + "/src/com/firetv/config/config.properties");
			prop.load(ip);
			org.apache.log4j.PropertyConfigurator.configure(workspacePath + "/src/com/firetv/config/log4j.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getDBConnection() throws Exception{
		FileInputStream ip = null;
		String workSpaceLocation = System.getProperty("user.dir");
		String workspacePath = workSpaceLocation.replace("FIRETVSteadyStateTests", "FIRETVDriverBase");
		Class.forName("org.postgresql.Driver");
		System.out.println("connected");
		Properties properties = new Properties();
		ip = new FileInputStream(workspacePath + "/src/com/firetv/config/db_config.properties");
		properties.load(ip);
		Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
		return conn;
	}
	
	/**
	 * Accessing test data from json file into desired capabilities
	 * 
	 * @param appName
	 *            - Test app name
	 * @param platformVersion
	 *            - Android version
	 */
	public static void throughJson(String platformVersion, String DSN) {
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability("deviceName", "My Phone");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("dsn", DSN);
		capabilities.setCapability("platformVersion", platformVersion);
		capabilities.setCapability("appPackage", appInput.getAppPackage());
		capabilities.setCapability("appActivity", appInput.getAppActivity());
		capabilities.setCapability("newCommandTimeout", 1200);
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("fullReset", false);
	}
	
	/**
	 *  Same vendor apps
	 * Accessing test data from json file into desired capabilities
	 * 
	 * @param appName
	 *            - Test app name
	 * @param platformVersion
	 *            - Android version
	 */
	public static void throughJson2(String platformVersion) {
		AppTestPojoVendorApps appInput = new AppTestPojoVendorApps();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability("deviceName", "My Phone");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", platformVersion);
		capabilities.setCapability("appPackage", appInput.getAppPackage());
		capabilities.setCapability("appActivity", appInput.getAppActivity());
		capabilities.setCapability("newCommandTimeout", 1200);
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("fullReset", false);
	}
	

	/**
	 * Andrioid driver initialization
	 * 
	 * @return driver
	 */
	public static AppiumDriver androidDriver(String platformVersion, String DSN) {
		throughJson(platformVersion, DSN);
		try {
			url = new URL(prop.getProperty("appiumServerURL"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			driver = new AndroidDriver<>(url, capabilities);
		} catch (UncheckedExecutionException e) {
			Throwables.throwIfUnchecked(e.getCause());
			throw new RuntimeException(e.getCause());
		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	
	/**
	 * Andrioid driver initialization for vendor apps
	 * 
	 * @return driver
	 */
	public static AppiumDriver androidDriver2(String platformVersion) {
		throughJson2(platformVersion);
		try {
			url = new URL(prop.getProperty("appiumServerURL"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			driver = new AndroidDriver<>(url, capabilities);
		} catch (UncheckedExecutionException e) {
			Throwables.throwIfUnchecked(e.getCause());
			throw new RuntimeException(e.getCause());
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	

	public static void chromeDriverForMac(String webLink) {
		String OSName = null;
		OSName = System.getProperty("os.name");
		OSName = OSName.toLowerCase();
		if (OSName.contains("mac")) {
			String homeDirectorName = System.getProperty("user.home");
			System.setProperty("webdriver.chrome.driver", homeDirectorName + prop.getProperty("chrometDriverPath"));
			ChromeOptions options = new ChromeOptions();
			// options.addArguments("--no-sandbox");
			options.addArguments(new String[] { "----disable-notifications" });
			webDriver = new ChromeDriver(options);
			webDriver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
			webDriver.get(webLink);
			webDriver.switchTo().defaultContent();
		}

		else if (OSName.contains("windows")) {
			String homeDirectorName = System.getProperty("user.home");
			System.setProperty("webdriver.chrome.driver",  homeDirectorName + prop.getProperty("chrometDriverPathwindos"));
			ChromeOptions options = new ChromeOptions();
			options.addArguments(new String[] { "----disable-notifications" });
			webDriver = new ChromeDriver(options);
			webDriver.get(webLink);
			webDriver.manage().window().maximize();
		} else {
			log.info("Given OS is not supported for testing");
		}
	}

	public static WebDriver getWebdriverInstance() {
		return webDriver;
	}

	public static void chromeDriverForWindows(String webLink) {
		System.setProperty("webdriver.chrome.driver", prop.getProperty("chrometDriverPathwindos"));
		ChromeOptions options = new ChromeOptions();
		options.addArguments(new String[] { "----disable-notifications" });
		webDriver = new ChromeDriver(options);
		webDriver.get(webLink);
		webDriver.manage().window().maximize();
	}

	public static void chromeDriverSwitchToNextTab(int i) {
		ArrayList<String> tabCount = new ArrayList<String>(webDriver.getWindowHandles());
		webDriver.switchTo().window(tabCount.get(i));
	}

	public static void quitChromeDriver() {
		webDriver.quit();
	}

	/**
	 * initalization method
	 * 
	 * @param appName
	 *            - Test app name
	 * @param platformVersion
	 *            - Android version
	 */
	public void initAppium() throws Exception {
        UIMacroBase macroBase = new UIMacroBase();
        platformVersion=macroBase.getDeviceOS();
        DSN= macroBase.getDeviceDSN();
        driver = androidDriver(platformVersion, DSN);
 }
	

	/**
	 * initalization method for same vendor apps
	 * 
	 * @param appName
	 *            - Test app name
	 * @param platformVersion
	 *            - Android version
	 */
	public void initAppiumVendorApps(String platformVersion) {
		driver = androidDriver2(platformVersion);
	}

//	public void initPrerequesties(String appName, String appTestDataMarketplace, String appUsername, String appPassword, String deviceUsername, String devicePassword, String serviceProviderid, String serviceProviderPassword) throws Exception {
//		test_apps_json_path = System.getProperty("user.dir") + prop.getProperty(appTestDataMarketplace);
//		JsonHandling setJson = new JsonHandling(test_apps_json_path, appName, appUsername, appPassword, deviceUsername, devicePassword,serviceProviderid,serviceProviderPassword);
//		setJson.setJSON();
//	} 
	
	public void initPrerequestiesTest(String appTestDataMarketplace, String ASIN) throws Exception {
		test_apps_json_path = System.getProperty("user.dir") + prop.getProperty(appTestDataMarketplace);
		JsonHandling setJson = new JsonHandling(test_apps_json_path, ASIN);
		setJson.setJSON(ASIN);
	} 
	
	/**
	 * Prerequesties for same vendor apps
	 * 
	 */
	public void initPrerequestiesVendorApps(String appName, String appTestDataMarketplace, String appUsername, String appPassword, String deviceUsername, String devicePassword, String serviceProviderid, String serviceProviderPassword) {
		test_apps_json_path = System.getProperty("user.dir") + prop.getProperty(appTestDataMarketplace);
		JsonHandlingVendorApps setJson = new JsonHandlingVendorApps(test_apps_json_path, appName, appUsername, appPassword, deviceUsername, devicePassword,serviceProviderid,serviceProviderPassword);
		setJson.setJSON();
	} 

}

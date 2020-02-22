package com.firetv.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.firetv.action.DriverException;
import com.firetv.interfaceinitialization.ADBCommands;
import com.firetv.interfaceinitialization.KeyCode;
import com.firetv.testbase.TestBase;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.PressesKey;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class UIMacroBase extends TestBase implements ADBCommands, KeyCode {

	ProcessBuilder pb;
	public static Process process;
	String DSN;

	/**
	 * Wait method to click an element untill the element is clickablee.
	 * 
	 * @param element
	 */
	public static void waitForElement(By by) {
		log.info("Waiting for the " + by + " present");
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}

	/**
	 * Wait method until the element is found
	 * 
	 * @param ElementTobeFound
	 * @param seconds
	 */
	public static void waitTillElementFound(By by, int seconds) {
		log.info("Waiting for the " + by + " present");
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public static void waitInSec(int sec) {
		try {
			Thread.sleep(sec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void chromeNotificationDisable() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments(new String[] { "--disable-notifications" });
	}

	/**
	 * Uninstall a package
	 * 
	 * @param appPackage
	 *            - Pass the package name which needs to uninstall
	 */
	@Override
	public void adbUninstall(String appPackage) {
		try {
			process = Runtime.getRuntime().exec("adb uninstall " + appPackage);
			process.waitFor();
			if (process.exitValue() != 0) {
				throw new Exception("unable to uninstall the package" + appPackage);
			}

		} catch (Exception e) {
			log.fatal("Failure running command. " + e.getMessage(), e);
			e.printStackTrace();
			throw new DriverException(e);
		}
	}

	/**
	 * Run an adb commands
	 */
	@Override
	public void runAdb() {
		try {
			process = Runtime.getRuntime().exec("adb");
			process.waitFor();
			if (process.exitValue() != 0) {
				throw new Exception("unable to run adb command");
			}
		} catch (Exception e) {
			log.fatal("Failure running command. " + e.getMessage(), e);
			e.printStackTrace();
			throw new DriverException(e);
		}
	}

	/**
	 * Force stop an application using adb commands
	 * 
	 * @param -
	 *            Pass the package name which needs to forcestop
	 */
	@Override
	public void adbForcestop(String appPackage) {
		try {
			process = Runtime.getRuntime().exec("adb shell am force-stop " + appPackage);
			process.waitFor();
			if (process.exitValue() != 0) {
				throw new Exception("unable to forcestop the package" + appPackage);
			}
		} catch (Exception e) {
			log.fatal("Failure running command. " + e.getMessage(), e);
			e.printStackTrace();
			throw new DriverException(e);
		}
	}
	
	public static String getDeviceDSN() throws Exception
	{
		int deviceCount = 0;
		String s = null;
		ArrayList<String> deviceList = new ArrayList<String>();
		try {
	        Process process = Runtime.getRuntime().exec("adb devices");
	        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));  
	        String line = null;
	        Pattern pattern = Pattern.compile("^([a-zA-Z0-9.:\\-]+)(\\s+)(device)");
	        Matcher matcher;
	        while ((line = in.readLine()) != null) {  
	            if (line.matches(pattern.pattern())) {
	                matcher = pattern.matcher(line);
	                if (matcher.find())
	                {
	                	s=matcher.group(1);
	                	deviceList.add(s); //this adds an element to the list
	                	//System.out.println("Connected Device DSN: "+s); //Prints contents of string variable 's' which in turn contains Model ID of device
	                	deviceCount++;
	                }
	            }
	        }  
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
        //System.out.println("Connected Device DSN: "+s); //Prints contents of string variable 's' which in turn contains Model ID of device
        //End of code to get Device DSN
		if (deviceCount == 1)
		{
			return deviceList.get(0);
		}
		else
		{
			return "false";
		}	
	}

	/**
	 * Launch an application using adb commands
	 * 
	 * @param appPackage
	 *            - Pass the package name
	 * @param appActivity
	 *            - Pass the Home Activity name
	 */
	@Override
	public void adbLaunch(String appPackage, String appActivity) {
		try {
			DSN=getDeviceDSN();
			log.info("Connected DSN : "+DSN);
			process = Runtime.getRuntime().exec("adb shell am start -n " + appPackage + "/" + appActivity);
			process.waitFor();
			if (process.exitValue() != 0) {
				throw new Exception("unable to launch the package" + appPackage);
			}
		} catch (Exception e) {
			log.fatal("Failure running command. " + e.getMessage(), e);
			e.printStackTrace();
			throw new DriverException(e);
		}

	}

	@Override
	public void clearAdb(String appPackage) {
		try {
			process = Runtime.getRuntime().exec("adb shell pm clear " + appPackage);
			process.waitFor();
			if (process.exitValue() != 0) {
				throw new Exception("unable to launch the package" + appPackage);
			}
		} catch (Exception e) {
			log.fatal("Failure running command. " + e.getMessage(), e);
			e.printStackTrace();
			throw new DriverException(e);
		}
	}

	/**
	 * Enabling Overlay services
	 */
	@Override
	public Process getOverlayService(String DSN) {
		try {
			process = Runtime.getRuntime()
					.exec("adb -s " + DSN + " shell dumpsys activity service com.amazon.ssm/.OverlayService");
			process.waitFor();
			if (process.exitValue() != 0) {
				throw new Exception("unable to get the overlay services" + DSN);
			}
		} catch (Exception e) {
			log.fatal("Failure running command. " + e.getMessage(), e);
			e.printStackTrace();
			throw new DriverException(e);
		}
		return process;
	}
	
	/**
	 * Getting device model
	 */
	public static String getDeviceModel() throws Exception {
		String DSN;
		String s2;
		DSN = getDeviceDSN();
		//Code to get Device OS
		if (DSN.equals("false"))
		{
			log.info("False"); 
			s2 = "false";
		}
		else
		{
			Object deviceProperty = null;
			try {
				final Process process = Runtime.getRuntime().exec("adb -s "+DSN+" shell getprop"); //Execute adb command to get device properties
				InputStream inStream = process.getInputStream();
				final BufferedReader br= new BufferedReader(
						new InputStreamReader(inStream));
				String line2;
				while ((line2 = br.readLine()) != null) {
					deviceProperty = deviceProperty + line2 + "\n"; //Get dump of adb device property output to 'deviceProperty'
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

			if(!((String) deviceProperty).isEmpty())
			{
				s2 = String.valueOf(deviceProperty); //Copy contents of deviceProperty to a string variable 's'
				// System.out.println("Value of s2: \n"+s2);
				Pattern pattern = Pattern.compile("\\[ro.product.model\\]: \\[(.*?)\\]"); //Regular expression to get value of model id from device property list
				Matcher matcher = pattern.matcher(s2);
				while (matcher.find())
				{
					s2=matcher.group(1); //Copy output of matched pattern from the above mentioned regular expression to the string variable 's'
				}
			}
			else
			{
				s2 = "false";
			}
		}
		return s2;
	}
	
	public static String getDeviceOS() throws Exception {
		String deviceModel=getDeviceModel();
        //System.out.println("Connected Device Model: "+s2); //Prints contents of string variable 's' which in turn contains Model ID of device
        if (deviceModel.equals("AFTB") || deviceModel.equals("AFTS") || deviceModel.equals("AFTM") || deviceModel.equals("AFTT") || deviceModel.equals("AFTRS"))
        {
               return "5.1.1";
        }
        else if (deviceModel.equals("AFTN") || deviceModel.equals("AFTA") || deviceModel.equals("AFTMM") || deviceModel.equals("AFTKMST12") || deviceModel.equals("AFTJMST12") || deviceModel.equals("AFTBAMR311") || deviceModel.equals("AFTEAMR311"))
        {
               return "7.1";
        }
        else
        {
               return "Unsupported Device OS";
        }
        //End of code to get Device OS		
	}

	
	/**
	 * Input text using ADB Shell command
	 */
	@Override
	public void inputTextUsingADB(String DSN,String text) {
		try {
			process = Runtime.getRuntime()
					.exec("adb -s " + DSN + " shell input text '"+text+"'");
			process.waitFor();
			if (process.exitValue() != 0) {
				throw new Exception("unable to get the overlay services" + DSN);
			}
		} catch (Exception e) {
			log.fatal("Failure running command. " + e.getMessage(), e);
			e.printStackTrace();
			throw new DriverException(e);
		}
	}

	/**
	 * Enabling xray service
	 */
	@Override
	public void enableXray() {
		try {
			process = Runtime.getRuntime().exec("adb shell am start com.amazon.ssm/com.amazon.ssm.ControlPanel");
			process.waitFor();
			if (process.exitValue() != 0) {
				throw new Exception("unable to enable x-ray");
			}
		} catch (Exception e) {
			log.fatal("Failure running command. " + e.getMessage(), e);
			e.printStackTrace();
			throw new DriverException(e);
		}
	}

	@Override
	public void goBack() {
		((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.BACK));
	}

	@Override
	public void goHome() {
		((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.HOME));
	}

	@Override
	public void goMenu() {
		((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.MENU));
	}

	@Override
	public void dpadUp() {
		((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_UP));
	}

	@Override
	public void dpadDown() {
		((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
	}

	@Override
	public void dpadLeft() {
		((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
	}

	@Override
	public void dpadRight() {
		((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
	}

	@Override
	public void dpadCenter() {
		((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
	}

	@Override
	public void seekForward() {
		((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.MEDIA_FAST_FORWARD));
	}

	@Override
	public void seeKBackward() {
		((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.MEDIA_REWIND));
	}

	@Override
	public void pause() {
		((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.MEDIA_PLAY_PAUSE));
	}

	@Override
	public void longPressForHome() {
		((PressesKey) driver).longPressKey(new KeyEvent(AndroidKey.HOME));
	}
	
	@Override
	public void seekForwardlongPress() {
		((PressesKey) driver).longPressKey(new KeyEvent(AndroidKey.MEDIA_FAST_FORWARD));
	}
	
	@Override
	public void seeKBackwardlongPress() {
		((PressesKey) driver).longPressKey(new KeyEvent(AndroidKey.MEDIA_REWIND));
	}

	public void dpadUpLoop(int count, int wait){
		for(int i=0;i<count;i++){
			dpadUp();
			waitInSec(wait);
		}
	}
	
	public void dpadDownLoop(int count, int wait){
		for(int i=0;i<count;i++){
			dpadDown();
			waitInSec(wait);
		}
	}
	
	public void dpadCenterLoop(int count, int wait){
		for(int i=0;i<count;i++){
			dpadCenter();
			waitInSec(wait);
		}
	}
	
	public void dpadRightLoop(int count, int wait){
		for(int i=0;i<count;i++){
			dpadRight();
			waitInSec(wait);
		}
	}
	
	public void dpadLeftLoop(int count, int wait){
		for(int i=0;i<count;i++){
			dpadLeft();
			waitInSec(wait);
		}
	}
	public void goBackLoop(int count, int wait){
		for(int i=0;i<count;i++){
			goBack();
			waitInSec(wait);
		}
}
}
package us.macro;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.testng.Reporter;

import com.firetv.action.DriverException;
import com.firetv.action.UIActionResult;
import com.firetv.commonpojo.AppTestDataPojo;
import us.model.CommonModelSteadystate;
import com.firetv.util.UIMacroBase;
import com.firetv.precondition.Credentials;


public class CommonMacroSteadystate extends UIMacroBase {

	CommonModelSteadystate commonmodelsteadystate;
	public static String frameRate = null;
	AppTestDataPojo appTestData = new AppTestDataPojo();
	static String connectedDeviceModel=null;
	Credentials cd = new Credentials();

	public CommonMacroSteadystate() {
		super();
		commonmodelsteadystate = new CommonModelSteadystate(driver);
	}

	/**
	 * Launch the Xray and make the Advanced option ON
	 * 
	 * @param DSN
	 *            Passing the DSN of the device
	 * @return UIActionResult
	 */
	public UIActionResult videoPlayPrerequisite(String DSN) {
		try {
			DSN = appTestData.getDsn();
			enableXray();
			getOverlayService(DSN);
			if (!commonmodelsteadystate.isXrayLayoutPresent()) {
				log.info("VALIDATION FAILED: X ray window is not opened");
				return UIActionResult.actionFailed("VALIDATION FAILED: X ray window is not opened");
			}
			if (commonmodelsteadystate.getAdvanceOptionText().equalsIgnoreCase("Advanced Options OFF")) {
				log.info("Enabling Advance option");
				commonmodelsteadystate.clickXrayAdvacneOption();
			}
			goBack();
			return UIActionResult.actionSuccess();
		} catch (Exception e) {
			return UIActionResult.actionFailed(e);
		}
	}
	
	/**
	 * Send device password to device using ADB shell command
	 * 
	 * @param DSN
	 *            Passing the DSN of the device
	 * @param string
	 *            Passing the text required to be typed
	 * @return UIActionResult
	 */
	public UIActionResult inputDevicePasswordUsingADB(String DSN, String text) {
		try {
			inputTextUsingADB(DSN, text);
			waitInSec(2000);
			pause(); //Submit text
			return UIActionResult.actionSuccess();
		} catch (Exception e) {
			return UIActionResult.actionFailed(e);
		}
	}

	/**
	 * Verifying the frame rate of playing video. It returns true if video
	 * playing.
	 * 
	 * @param DSN
	 * @return UIActionResult
	 * @throws Exception
	 */
	public UIActionResult getFrameRate(String DSN) throws Exception {
		DSN = appTestData.getDsn();
		Object xRayDump = null;
		try {
			InputStream inStream = getOverlayService(DSN).getInputStream();
			final BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
			String line;
			while ((line = br.readLine()) != null) {
				xRayDump = xRayDump + line + "\n"; // Get dump of X-ray output
													// to 'xRayDump'
			}
			br.close();
			try {
				process.waitFor();
			} catch (final Exception e) {
				return UIActionResult.actionFailed(e);
			}
			// System.out.println(xRayDump); //Prints contents of 'xRayDump'
			// which contains entire dump of X-ray output
			if (!((String) xRayDump).isEmpty()) {
				frameRate = String.valueOf(xRayDump); // Copy contents of
														// xRayDump to a string
														// variable 's'
				Pattern pattern = Pattern.compile("\\[com.amazon.ssm.multimedia.video.framerate\\]: \\[(.*?)\\]"); // Regular
																													// expression
																													// to
																													// get
																													// value
																													// of
																													// frame
																													// rate
																													// from
																													// X-ray
																													// output
				Matcher matcher = pattern.matcher(frameRate);
				while (matcher.find()) {
					frameRate = matcher.group(1); // Copy output of matched
													// pattern from
					// the above mentioned regular
					// expression to the string variable
					// 's'
					// System.out.println(s); //Prints contents of string
					// variable 's' which in turn contains Frame Rate
				}
				if (frameRate.equals("null") || frameRate.equals("nan")) // Condition
																			// to
																			// check
				// if frame rate is
				// null i.e. no
				// video played
				{
					boolean result = false; // Set result to false

					return UIActionResult.actionFailed("No video playback observed");
				} else {
					boolean result = true; // Set result to true
					log.info(" Video playback observed at " + frameRate + " frames per second");
					return UIActionResult.actionSuccess();
				}
			}
			return UIActionResult.actionSuccess();
		} catch (Exception e) {
			return UIActionResult.actionFailed(e);
		}
	}

	public UIActionResult activateOptimumServiceProvider() {
		try {
			waitInSec(15000);
			commonmodelsteadystate.typeOptimumID(AppTestDataPojo.getServiceProviderid());
			waitInSec(2000);
			commonmodelsteadystate.typeOptimumPassword(AppTestDataPojo.getServiceProviderPassword());
			waitInSec(2000);
			commonmodelsteadystate.clickSignIn();
			waitInSec(2000);
			return UIActionResult.actionSuccess("Activating optimum service provider");
		} catch (Exception e) {
			return UIActionResult.actionFailed(e);
		}
	}

	public UIActionResult activateOptimumServiceProviderWithFrame() {
		try {
			waitInSec(15000);
			commonmodelsteadystate.switchFromFrame();
			commonmodelsteadystate.typeOptimumID(AppTestDataPojo.getServiceProviderid());
			commonmodelsteadystate.typeOptimumPassword(AppTestDataPojo.getServiceProviderPassword());
			commonmodelsteadystate.clickSignIn();
			return UIActionResult.actionSuccess("Activating optimum service provider");
		} catch (Exception e) {
			return UIActionResult.actionFailed(e);
		}
	}
	
	public UIActionResult activateOptimumServiceProviderInApp() {
		try {
			commonmodelsteadystate.enterUsernameOptimumActivation(appTestData.getServiceProviderid());
   		 	waitInSec(1000);
   		 	pause();
   		 	commonmodelsteadystate.enterPasswordOptimumActivation(appTestData.getServiceProviderPassword());
   		 	waitInSec(1000);
   		 	pause();
   		 	waitInSec(1000);
   		 	commonmodelsteadystate.clickSignInOptimumActivation();
   		 	waitInSec(10000);
			return UIActionResult.actionSuccess("Activating Optimum service provider within app...");
		} catch (Exception e) {
			return UIActionResult.actionFailed(e);
		}
	}

	public UIActionResult activateatnTServiceProvider(String email, String password) {
		try {
			waitInSec(15000);
			commonmodelsteadystate.enterUserName(email);
			waitInSec(1000);
			commonmodelsteadystate.enterPassword(password);
			waitInSec(1000);
			commonmodelsteadystate.clickOnSignInButton();
			return UIActionResult.actionSuccess("Activating AT&T service provider");
		} catch (Exception e) {
			return UIActionResult.actionFailed(e);
		}
	}

	/**
	 * Log in to facebook with valid credentials
	 * 
	 * @param email
	 *            - pass the mail-id for the facebook
	 * @param pass
	 *            - pass the password for the facebook
	 * @return
	 */
	public UIActionResult activateFacebook(String email, String pass) {
		try {
			commonmodelsteadystate.enterFacebookEmail(email);
			commonmodelsteadystate.enterFacebookPassword(pass);
			commonmodelsteadystate.clickOnFacebookLogintab();
			waitInSec(10000);
			return UIActionResult.actionSuccess("Activiting Facebook");
		} catch (Exception e) {
			return UIActionResult.actionFailed(e);

		}

	}
	
	
	private UIActionResult invokingHUD() {
		log.info("Invoking HUD");
		int i = 1;
		try{
			longPressForHome();
			waitInSec(3000);
			connectedDeviceModel = UIMacroBase.getDeviceModel();
			log.info("connected Device Model: " + connectedDeviceModel);
			try{
				if(connectedDeviceModel.equals("AFTRS") || connectedDeviceModel.equals("AFTKMST12") || connectedDeviceModel.equals("AFTJMST12") || connectedDeviceModel.equals("AFTBAMR311") || connectedDeviceModel.equals("AFTEAMR311"))
				{
					log.info("HUD Test case is not valid for Smart TV devices. Continuing...");
					return UIActionResult.actionSuccess();
				}else if(connectedDeviceModel.equals("false")){
					log.info("Unable to identify the connected device.   " + connectedDeviceModel);
				}
			}

			catch (Exception e) {
				log.info("Unable to identify the connected device. " + e);

			}
			if (commonmodelsteadystate.isNewSmtpHUDSettingsPresent()) {
				log.info("HUD found..");
			} else {
				while ((!commonmodelsteadystate.isNewSmtpHUDSettingsPresent() && (i<=5))){
					longPressForHome();
					i++;
				}
			}
			log.info("HUD Invoked on "+i+" try");
			return UIActionResult.actionSuccess();
		} catch (Exception e) {
			return UIActionResult.actionFailedOptional("Failed because : " + e.getStackTrace());

		}
	}



	/**
	 * Invoke HUD and Press back to app page
	 * 
	 * @return UIActionResult
	 */

	public UIActionResult hudTestCase() {
		invokingHUD();
		waitInSec(500);
		goBack();
		return UIActionResult.actionSuccess();
	}

	
	/**
	 * Smtp:Invoke HUD,click on settings now press back to device home screen
	 * and relaunch the app Smart TV: we are not performing any action for smart
	 * TV we have made that in IF condition
	 * 
	 * @return UIActionResult
	 */
	public UIActionResult settingsTestCase() {
		log.info("Executing Setting Test Case");
		try {
			invokingHUD();
			if (commonmodelsteadystate.isNewSmtpHUDSettingsPresent()) {
				waitInSec(500);
				for (int i = 0; i <= 2; i++) {
					dpadRight();
					waitInSec(500);
				}
				dpadCenter();
				waitInSec(5000);
				if(commonmodelsteadystate.isSettingpageloaded()){
					log.info("Settings page loadded");
				}else if(commonmodelsteadystate.isDeviceHomePageLoaded()){
					log.info("Home page loadded");
				}else{
					return UIActionResult.actionFailed("Settings/Home page is not loaded");
				}
				goBack();
				waitInSec(500);
			} 
			else {
				if(!commonmodelsteadystate.isSmartTVHUDPresent())
				{
					longPressForHome();
				}
				log.info("Setting test case is not applicable for the smart TV's");
			}
			return UIActionResult.actionSuccess();
		} catch (Exception e) {
			return UIActionResult.actionFailedOptional("HUD Settings Failed because of : "+e);
		}
	}
	
	/**
	 * Below code will check whether the connected tv is smart TV or smtp device
	 * This we can use in app macro to execute setting test case
	 * 
	 * @return boolean value
	 */
	public boolean isItSmartTV() {
		try {
			return commonmodelsteadystate.isSmartTVHUDPresent();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Go home, Validate home page and re-launch the application from device
	 * home screen
	 * 
	 * @return UIActionResult
	 */
	public UIActionResult relaunchApps(String appPackage, String appActivity) {
		try {
			waitInSec(5000);
			goHome();
			waitInSec(5000);
			verifyDeviceHome();
			waitInSec(5000);
			adbLaunch(appPackage, appActivity);
			waitInSec(5000);
			return UIActionResult.actionSuccess();
		} catch (Exception e) {
			return UIActionResult.actionFailed(e);
		}

	}

	private UIActionResult verifyDeviceHome() {
		try {
			if (!commonmodelsteadystate.isDeviceHomePageLoaded()) {
				return UIActionResult.actionFailed("Home verification failed");
			}
			return UIActionResult.actionSuccess();
		} catch (Exception e) {
			return UIActionResult.actionFailedOptional("Failed because of : "+e);
		}
	}

	/**
	 * Invoke HUD check which device is there SMTP or Smart TV, press back from
	 * HUD need to pass the App specific element(From App Macro) which we want
	 * to check after pressing back from HUD
	 * 
	 * @param hudPresent
	 * @return UIActionResult
	 */

	public UIActionResult verifyHUDVerification(boolean hudPresent) {
		try {
			invokingHUD();
			if (commonmodelsteadystate.isNewSmtpHUDSettingsPresent()) {
				goBack();
				waitInSec(2000);
				if (!hudPresent)
				{
					return UIActionResult.actionFailed("App page in not present in the background");
				}
			}
			else {
				if(!commonmodelsteadystate.isSmartTVHUDPresent())
				{
					longPressForHome();
					waitInSec(1000);
				}
				goBack();
				log.info("HUD tests is not applicable for smart tv's Pressing back");
			}
			return UIActionResult.actionSuccess();
		} catch (Exception e) {
			return UIActionResult.actionFailedOptional("HUD navigation Failed");
		}
	}
	
	/**
	 * Switching to frame by index
	 * 
	 */
	public UIActionResult switchFrame(int frameIndex) {
		try {
			waitInSec(1000);
			commonmodelsteadystate.switchFrame(frameIndex);
			return UIActionResult.actionSuccess("Switching to frame");
		} catch (Exception e) {
			return UIActionResult.actionFailed(e);
		}
	}
	

	/**
	 * Switching to frame by IDorName
	 * 
	 */
	public UIActionResult switchToFrameIDorName(String frameIDorName) {
		try {
			waitInSec(1000);
			commonmodelsteadystate.switchFramebyIDorName(frameIDorName);
			return UIActionResult.actionSuccess("Switching to frame by IDorName");
		} catch (Exception e) {
			return UIActionResult.actionFailed(e);
		}
	}

	public UIActionResult launchApplicationWithASIN(String ASIN) {
  		log.info("-Launching application with ASIN-");
  		try {
  			String LauncherClass = "amzn://apps/android?asin="+ASIN+"";
  			Process process = Runtime.getRuntime().exec("adb shell am start -d "+LauncherClass+"");
  			return UIActionResult.actionSuccess("App launch with ASIN passed");
  		} catch (Exception e) {
  			return UIActionResult.actionFailedOptional("App launch with ASIN failed");
  		}
  	}

	/**
	 * Downloads app from app details page
	 * 
	 */
	public UIActionResult downloadAppFromDetailsPage(String appPackageName) {
		try{
		Boolean iselementpresent = false; 
		String appStatus = null;
		
		iselementpresent = commonmodelsteadystate.checkBuyButton();
		if (iselementpresent == true)
		{	
			appStatus = commonmodelsteadystate.getButtonName();
			if ((appStatus.equals("Get  Free to download")) || (appStatus.equals("Get Free to download")))
			{
				dpadCenter();
				log.info("Downloading the app...");
				waitInSec(5000); 
				dpadCenter();
				waitInSec(5000);                       
			}
			if ((appStatus.equals("Download You own it")) || (appStatus.equals("Download  You own it")))
			{
				dpadCenter(); 
				log.info("Downloading the app...");
				waitInSec(5000); 
				dpadCenter(); 
				waitInSec(5000);                      
			}
			if (appStatus.equals("Update"))
			{
				dpadCenter(); 
				log.info("Old version of the app present on device, updating the app...");
				waitInSec(5000); 
				dpadCenter(); 
				waitInSec(5000);                           
			}
			 appStatus = commonmodelsteadystate.getButtonName();
			if (appStatus.contains("Queued") || appStatus.contains("Downloading") || appStatus.contains("Processing"))
			{
				log.info("Waiting for the app to finish downloading!");
				while (appStatus.contains("Queued") || appStatus.contains("Downloading") || appStatus.contains("Processing"))
				{
					Boolean isScreensaverPresent = commonmodelsteadystate.screenSavePresent();
					if (isScreensaverPresent == true)
					{
						while (isScreensaverPresent == true)
						{
							goBack();
							waitInSec(5000);
							isScreensaverPresent = commonmodelsteadystate.screenSavePresent();
						}
					}
					waitInSec(5000);   
					 appStatus = commonmodelsteadystate.getButtonName();
				}
			}
			appStatus = commonmodelsteadystate.getButtonName();
			if (appStatus.contains("Installing"))
			{
				log.info("Waiting for app to finish installing!");
				while (appStatus.contains("Installing"))
				{
					waitInSec(5000); 
					Boolean isScreensaverPresent = commonmodelsteadystate.screenSavePresent();
					if (isScreensaverPresent == true)
					{
						while (isScreensaverPresent == true)
						{
							goBack();
							waitInSec(5000);
							isScreensaverPresent = commonmodelsteadystate.screenSavePresent();
						}
					}
					appStatus = commonmodelsteadystate.getButtonName();
				}
			}

			waitInSec(5000); 
			String appPackageInstallCheck = (appPackageName);
			boolean isAppInsstalled = driver.isAppInstalled(appPackageInstallCheck);
			if (isAppInsstalled == true)
			{
				return UIActionResult.actionSuccess("App downloaded and installed on the device successfully!");
			}
			else
			{
				return UIActionResult.actionFailed("App download and install failed on the device!");
			}
		}
		else
		{
			return UIActionResult.actionFailed("App download and install failed on the device!");
		}	

		}
		catch (Exception e) {
			return UIActionResult.actionFailedOptional("App install with ASIN failed");
		}
	}

	

	
	
	
	
	public UIActionResult getInputBitRate(String DSN) throws Exception {
		DSN = appTestData.getDsn();
		Object xRayDump = null;
		try {
			InputStream inStream = getOverlayService(DSN).getInputStream();
			final BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
			String line;
			while ((line = br.readLine()) != null) {
				xRayDump = xRayDump + line + "\n"; // Get dump of X-ray output
													// to 'xRayDump'
			}
			br.close();
			try {
				process.waitFor();
			} catch (final Exception e) {
				return UIActionResult.actionFailed(e);
			}
			// System.out.println(xRayDump); //Prints contents of 'xRayDump'
			// which contains entire dump of X-ray output
			if (!((String) xRayDump).isEmpty()) {
				frameRate = String.valueOf(xRayDump); // Copy contents of
														// xRayDump to a string
														// variable 's'
				Pattern pattern = Pattern.compile("\\[com.amazon.ssm.multimedia.video.inputFrameRate\\]: \\[(.*?)\\]"); // Regular
																													// expression
																													// to
																													// get
																													// value
																													// of
																													// frame
																													// rate
																													// from
																													// X-ray
																													// output
				Matcher matcher = pattern.matcher(frameRate);
				while (matcher.find()) {
					frameRate = matcher.group(1); // Copy output of matched
													// pattern from
					// the above mentioned regular
					// expression to the string variable
					// 's'
					// System.out.println(s); //Prints contents of string
					// variable 's' which in turn contains Frame Rate
				}
				if (frameRate.equals("null") || frameRate.equals("nan")) // Condition
																			// to
																			// check
				// if frame rate is
				// null i.e. no
				// video played
				{
					boolean result = false; // Set result to false

					return UIActionResult.actionFailed("No video playback observed");
				} else {
					boolean result = true; // Set result to true
					log.info(" Video playback observed at " + frameRate + " frames per second");
					return UIActionResult.actionSuccess();
				}
			}
			return UIActionResult.actionSuccess();
		} catch (Exception e) {
			return UIActionResult.actionFailed(e);
		}
	}

}

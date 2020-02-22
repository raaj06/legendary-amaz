package us.macro;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
 
import com.firetv.action.UIActionResult;
import com.firetv.commonpojo.AppTestDataPojo;
import us.model.WSNModel;
import com.firetv.util.UIMacroBase;
 
public class WSNMacro extends UIMacroBase{
 
       private WSNModel wsnmodel;
       private CommonMacroSteadystate commonmacrosteadystate;
       private AppTestDataPojo appTestData = new AppTestDataPojo();
       private DateFormat timeValue = new SimpleDateFormat("mm:ss");
       private String time1 = null;
       private String time2 = null;
       private String time3 = null;
       private String time5 = null;
       private int seekbarcounttime=0;
 
       private Date Currentime;
       private Date Benchtime;
 
       public WSNMacro() {
              super();
              wsnmodel = new WSNModel(driver);
              commonmacrosteadystate = new CommonMacroSteadystate();
 
       }
 
       /**
       * Verifying the logo locators are present in the home page of the
       * application
       * 
       * @return UIActionResult
       */
       public UIActionResult verifyLogoPresent() {
              log.info("Checking app logo");
              try {
                     waitInSec(10000);
                   log.info("asdf");
                     int qq=0;
                     while (!wsnmodel.isLogoElementPresent()) {
                    	 log.info("Waiting for app to load..!");
                    	 waitInSec(10000);
                    	 qq++;
                    	 if(qq>5){
                    		 log.info("App home page not loaded for long time");
                    		 break;
                    	 }
                     }
                     if (!wsnmodel.isLogoElementPresent()) {
                           return UIActionResult.actionFailed("Logo is not present in home page");
                     }
                     return UIActionResult.actionSuccess("App launch passed.");
              } catch (Exception e) {
                     return UIActionResult.actionFailed(e);
              }
       }
 
       /**
       * Verifing video under selected tabs
       * 
       * @return UIActionResult
       */
       public UIActionResult playVideoUnderTabs(navigationTabName tab, String searchText) {
              log.info("Selecting tabs under "+tab);
              try {
                     waitInSec(2000);
                     navigateToAppHome();
                     waitInSec(1000);
                   //  commonmacrosteadystate.videoPlayPrerequisite(appTestData.getDsn());
                     waitInSec(3000);
                     {
                           switch (tab) {
                           case HOME:
                                  dpadDownLoop(2, 1000);
                                  dpadCenter();
                                  waitInSec(20000);
                                  break;
                           case SHOWS:
                                  dpadRight();
                                  waitInSec(1000);
                                  dpadDown();
                                  waitInSec(1000);
                                  log.info("Checking if shows video frame is present");
                                  isFramePresent();
                                  dpadCenter();
                                  waitInSec(2000);
                                  dpadCenter();
                                  waitInSec(5000);
                                  isContinueWatchPresentThenClickOnStartOver();
                                  break;
                          
                           case SEARCH:
                               dpadRightLoop(3, 4000);
                               log.info("Checking if Navigated to Live");
                               isNavigatedtoLive(); 
                               waitInSec(3000);
                               dpadRight();
                               waitInSec(4000);
                               dpadRight(); 
                               waitInSec(8000);
                               log.info("Checking if Search result present");
                               isSearchPresent(); 
                               waitInSec(3000);
                               dpadDown();
                               waitInSec(4000);
                               wsnmodel.enterSearchtext(searchText);
                               waitInSec(2000);
                               pause();
                               waitInSec(2000);
                               dpadUpLoop(3, 1000);
                               dpadDownLoop(3, 1000);
                               waitInSec(8000);
                               dpadCenter();
                               waitInSec(2000);
                               dpadCenter();
                               waitInSec(2000);
                               break;
                           }
                     }
                     if(wsnmodel.checkVideoPlay()){
                     return UIActionResult.actionSuccess("Video play started..!");
                     }else{
                    	 return UIActionResult.actionFailed("Video play failed.!");
                     }
              } catch (Exception e) {
                     return UIActionResult.actionFailed("Video play Test failed "+ e);
              }
       }
 
     

	private UIActionResult isFramePresent() {
              try {
                     if (!wsnmodel.isShowsVideoFramePresent()) {
                           return UIActionResult.actionFailed("Shows video frame is not avilable");
                     }
                     return UIActionResult.actionSuccess("Shows video Frame is present");
              } catch (Exception e) {
                     return UIActionResult.actionFailed(e);
              }
       }
 
       private UIActionResult verifyBrowsevideoContainer() {
              try {
                     if (!wsnmodel.isBrowseVideoContainerPresent()) {
                           return UIActionResult.actionFailed("Shows video container is not avilable");
                     }
                     return UIActionResult.actionSuccess("Browse video container is present");
              } 
              catch (Exception e) {
                     return UIActionResult.actionFailed(e);
              }
       }
 
       private UIActionResult navigateToAppHome() {
              try {
            	  if(wsnmodel.titlePresent()){
            	  int title =0;
            	  while(!wsnmodel.titlePresent()){
            		  dpadDown();
            		  waitInSec(1000);
            		  title++;
            		  if(title>5){
            			  log.info("Unable to check home page");
            			  break;
            		   }  
            	  }}else{
            		  log.info("Home page present");
            	  }
                  
                     return UIActionResult.actionSuccess();
              } catch (Exception e) {
                     return UIActionResult.actionFailed(e);
              }
 
       }
 
 
       private UIActionResult isSearchPresent() {
              try {
                     if (!wsnmodel.isSearchselected()) {
                           return UIActionResult.actionFailed("Unable to navigate to Search");
                     }
                     return UIActionResult.actionSuccess("Navigation to Search Success");
              } catch (Exception e) {
                     return UIActionResult.actionFailed(e);
              }
 
       }
 
       private UIActionResult isNavigatedtoLive() {
              try {
                     if (!wsnmodel.isLiveTVselected()) {
                           return UIActionResult.actionFailed("Unable to navigate till LIVE TV");
                     }
                     return UIActionResult.actionSuccess("Navigation to Live Success");
 
              } catch (Exception e) {
                     return UIActionResult.actionFailed(e);
              }
       }
       
       private UIActionResult isContinueWatchPresentThenClickOnStartOver(){
    	   log.info("Checking if Continue watch is present after playing the video");
              try {
                     if(!wsnmodel.isContinueWatchPresent()){
                           log.info("Unable to Find Continue Watch Element.");
                     }
                     else
                     {
                           wsnmodel.clickStartOver();
                     }
                     return UIActionResult.actionSuccess("Continue Watch Element Found");
              } catch (Exception e) {
                     return UIActionResult.actionFailed(e);
              }
       }
 
       public enum navigationTabName {
 
              HOME, SHOWS, SEARCH
 
       }
 
 
       private UIActionResult checkRestart() {
              try {
                     log.info("StartOver button check");
                     if (wsnmodel.isStartOverPresent()) {
                           log.info("Cliking StartOver button");
                           waitInSec(5000);
                           wsnmodel.clickStartOver();
                           waitInSec(2000);
                     }
                     return UIActionResult.actionSuccess();
              } catch (Exception e) {
                     return UIActionResult.actionFailed(e);
              }
       }
 
       private UIActionResult webactivation() {
              try {
                     log.info("Activation starts");
                     webUIActivation();
                     waitInSec(5000);
                     quitChromeDriver();
                     return UIActionResult.actionSuccess();
              } catch (Exception e) {
                     return UIActionResult.actionFailed(e);
              }
       }
 
       private UIActionResult webUIActivation() {
              try {
                     String getCode = wsnmodel.getValidationCode();
                     log.info("Activation Code from the UI "+getCode);
                     chromeDriverForMac(appTestData.getWeblink());
                     waitInSec(1000);
                     int w=0;
                     while(!wsnmodel.checkCodePage()){
                    	 waitInSec(5000);
                    	 log.info("Waiting for page to load..!");
                    	 w++;
                    	 if(w>5){
                    		 log.info("Page is taking too long to load...!");
                    		 break;
                    	 }
                     }
                     wsnmodel.textFieldActivationCode(getCode);
                     waitInSec(1000);
                     wsnmodel.clickActivate();
                     waitInSec(10000);
                     log.info("Selecting Optimum");
                     wsnmodel.chooseOptimum();
                     waitInSec(10000);
                     log.info("Entering login details for Optimum");
                     commonmacrosteadystate.activateOptimumServiceProvider();
                     log.info("App login details entered. Waiting for activation..");
                     waitInSec(5000);
                     if(wsnmodel.webCheckSuccess()){
                     return UIActionResult.actionSuccess("Web activation passed.");
                     }else{
                    	 return UIActionResult.actionFailed("Web activation failed.");
                     }
              } catch (Exception e) {
                     return UIActionResult.actionFailed(e);
              }
       }
 
       /**
       * Verifying video play-back verification
       * 
       * @param DSN
       * @
       */
       public UIActionResult verifyVideoPlayBackVerification() {
              log.info("Video play-back verification starts");
              try {
            	  waitInSec(10000);
                     commonmacrosteadystate.getFrameRate(appTestData.getDsn());
                     return UIActionResult.actionSuccess("Video playback Observed at frame rate of "+commonmacrosteadystate.frameRate);
              } catch (Exception e) {
                     return UIActionResult.actionFailedOptional("No Video Playback Observed");
              }
       }
 
       private UIActionResult checkAD() {
              try {
                     log.info("Checking for advertisement");
                     wsnmodel.isAdsPresent();
                     return UIActionResult.actionSuccess();
              } catch (Exception e) {
                     return UIActionResult.actionFailedOptional("Checking for Advertisement failed");
              }
       }
 
       /**
       * Verifying video pause verification
       * 
       * @param DSN
       * @return UIActionResult
       */
       public UIActionResult verifyVideoPauseVerification() {
              log.info("Video pause verification starts");
              try {
                     waitInSec(2000);
                     dpadDown();
                     waitInSec(2000);
                     pause();
                     waitInSec(2000);
                     invokeSeekbar();
                     waitInSec(1000);
                     time1 = wsnmodel.getCurrentTime();
                     log.info(time1);
                     waitInSec(2000);
                     dpadDown();
                     waitInSec(2000);
                     time2 = wsnmodel.getCurrentTime();
                     log.info(time2);
                     dpadDown();
                     waitInSec(2000);
                     log.info("Resuming back...");
                     pause();
                     waitInSec(2000);
                     if (!time1.equalsIgnoreCase(time2)) {
                           return UIActionResult.actionFailedOptional("Video playback pause failed");
                     } else {
                           return UIActionResult.actionSuccess("Video playback pause successful");
                     }
              } catch (Exception e) {
                     return UIActionResult.actionFailedOptional("Video playback pause test failed" + e);
              }
       }
 
       /**
       * Verifying video forward verification
       * 
       * @param DSN
       * @return UIActionResult
       */
       public UIActionResult verifyForwardVerification() {
              log.info("Video Forward verification starts");
              try {
                     waitInSec(5000);
                     pause();
                     waitInSec(1000);
                     while ((wsnmodel.isSeekbarPresent() == false) && (seekbarcounttime<=6)) {
                           log.info("Invoking seekbar");
                           waitInSec(1000);
                           dpadDown();
                           waitInSec(1000);
                           pause();
                           wsnmodel.isSeekbarPresent();
                           seekbarcounttime++;
                     }
                     if((wsnmodel.isSeekbarPresent() == true) && (seekbarcounttime<=7))
                     {
                           waitInSec(1000);
                           time3 = wsnmodel.getCurrentTime();
                           Currentime = timeValue.parse(time3);
                           log.info(time3);
                           log.info("Resuming back...");
                           pause();
                           for (int i = 0; i < 3; i++) {
                                  waitInSec(2000);
                                  seekForward();
                           }
                           waitInSec(10000);
                           checkAD();
                           pause();
                           invokeSeekbar();
                           
                           waitInSec(1000);
                           time1 = wsnmodel.getCurrentTime();
                           waitInSec(1000);
                           Benchtime = timeValue.parse(time1);
                           log.info(time1);
                           pause();
                           waitInSec(1000);
                           if (Currentime.after(Benchtime)) {
                                  return UIActionResult.actionFailedOptional("Video playback forward Failed");
                           }
                           return UIActionResult.actionSuccess("Video playback Forward successful");
                     }
                     else
                     {
                           return UIActionResult.actionFailedOptional("Unable to get the seekbar in forward test case");
                     }
              } catch (Exception e) {
                     return UIActionResult.actionFailedOptional("Video playback forward test Failed" + e);
              }
       }
 
       private void invokeSeekbar() {
    	   int s =0;
    	   while (wsnmodel.isSeekbarPresent() == false) {
               log.info("Invoking seekbar");
               waitInSec(1000);
               dpadDown();
               waitInSec(1000);
               pause();
               wsnmodel.isSeekbarPresent();
               s++;
               if(s>10){
            	   log.info("Unbale to invoke seekbar");
            	   break;
               }
        }
		
	}

	/**
       * Verifying video rewind verification
       * 
       * @param DSN
       * @return UIActionResult
       */
       public UIActionResult verifyRewindVerification() {
              log.info("Video rewind verification starts");
              try {
                     waitInSec(1000);
                     pause();
                     waitInSec(1000);
                     seekbarcounttime=0;
                     while ((wsnmodel.isSeekbarPresent() == false) && (seekbarcounttime<=6)) {
                           log.info("Invoking seekbar");
                           waitInSec(1000);
                           dpadDown();
                           waitInSec(1000);
                           pause();
                           wsnmodel.isSeekbarPresent();
                     }
                     if((wsnmodel.isSeekbarPresent() == true) && (seekbarcounttime<=7))
                     {
                            waitInSec(1000);
                           time3 = wsnmodel.getCurrentTime();
                           Benchtime = timeValue.parse(time3);
                           log.info(time3);
                           log.info("Resuming back...");
                           pause();
                           for (int i = 0; i < 5; i++) {
                                  waitInSec(2000);
                                  seeKBackward();
                           }
                           waitInSec(1000);
                           checkAD();
                           pause();
                           invokeSeekbar();
                           waitInSec(1000);
                           time5 = wsnmodel.getCurrentTime();
                           Currentime = timeValue.parse(time5);
                           log.info(time5);
                           log.info("Resuming back..");
                           pause();
                           waitInSec(10000);
                           if (Benchtime.before(Currentime)) {
                                  return UIActionResult.actionFailedOptional("Video playback rewind Failed");
                           } else {
                                  return UIActionResult.actionSuccess("Video playback rewind successful");
                           }
                     }
                     else
                     {
                           return UIActionResult.actionFailedOptional("Unable to get the seekbar while performing rewind test case");
                     }
              } catch (Exception e) {
                     return UIActionResult.actionFailedOptional("Video playback rewind test Failed" + e);
              }
       }
 
       /**
       * Verifying HUD Settings verification
       * 
       * @param DSN
       * @return UIActionResult
       */
       public UIActionResult verifyHUDVerification() {
              log.info("HUD verification starts");
              try {
                     commonmacrosteadystate.hudTestCase();
                     waitInSec(5000);
                     if (!wsnmodel.isvideoPresent()) {
                           return UIActionResult.actionFailedOptional("HUD verification failed");
                     }
                     return UIActionResult.actionSuccess("HUD verification Passed");
              } catch (Exception e) {
                     return UIActionResult.actionFailedOptional("HUD navigation test Failed" + e);
              }
       }
 
       /**
       * Verifying HUD verification
       * 
       * @param DSN
       * @return UIActionResult
       */
       public UIActionResult verifyHUDSettingsVerification() {
              log.info("HUD settings verification starts");
              try {
                     commonmacrosteadystate.settingsTestCase();
                     if (commonmacrosteadystate.isItSmartTV() == false) {
                           waitInSec(2000);
                           adbLaunch(appTestData.getAppPackage(), appTestData.getAppActivity());
                           log.info("App launched");
                           Thread.sleep(20000);
                           if (wsnmodel.isLogoElementPresent()) {
                                  log.info("On relaunch app navigated to device home screen. Selecting video again..");
                                  navigateToAppHome();
                                  waitInSec(1000);
                                  dpadCenter();
                                  waitInSec(1000);
                                 dpadDownLoop(2, 1000);
                                  dpadCenter();
                                  waitInSec(2000);
                                   dpadCenter();
                                  waitInSec(2000);
                                  checkRestart();
                                  waitInSec(10000);
                           }
                           checkAD();
                           if (wsnmodel.isvideoPresent()) {
                                  log.info("Setting From HUD Navigation Passed");
                           }
                     } else {
                           log.info("Setting test case in not required for Smart TV");
                           log.info("Pressing back to go back to video from HUD");
                           goBack();
                           waitInSec(5000);
                     }
                     checkAD();
                     if (!wsnmodel.isvideoPresent()) {
                           return UIActionResult.actionFailedOptional("HUD settings Failed");
                     }
                     return UIActionResult.actionSuccess("HUD settings passed.");
              } catch (Exception e) {
                     return UIActionResult.actionFailedOptional("HUD settings test Failed" + e);
              }
 
       }
 
       /**
       * Verifying re-launch verification
       * 
       * @return UIActionResult
       */
       public UIActionResult verifyRelaunchVerification() {
              log.info("Relaunch verification starts");
              try {
                     log.info("Relaunch test verification starts");
                     commonmacrosteadystate.relaunchApps(appTestData.getAppPackage(), appTestData.getAppActivity());
                     log.info("app launched");
                     waitInSec(10000);
                     if (wsnmodel.isLogoElementPresent()) {
                           log.info("On relaunch app navigated to device home screen. Selecting video again..");
                           navigateToAppHome();
                           waitInSec(1000);
                           dpadCenter();
                           waitInSec(1000);
                           dpadDownLoop(2, 1000);
                           dpadCenter();
                           waitInSec(2000);
                           dpadCenter();
                           waitInSec(2000);
                           checkRestart();
                           waitInSec(10000);
                     }
                     checkAD();
                     if (!wsnmodel.isvideoPresent()) {
                           return UIActionResult.actionFailed("Relaunch Failed");
                     }
                     return UIActionResult.actionSuccess("Relaunch verification passed");
              } catch (Exception e) {
                     return UIActionResult.actionFailed("Relaunch test Failed" + e);
              }
       }
 
      
 
       public UIActionResult navigationTest() {
              try {
                     navigateToAppHome();
                     dpadNavigation();
                     waitInSec(1000);
                     dpadRight();
                     waitInSec(1000);
                     dpadNavigation();
                     waitInSec(1000);
                     dpadRight();
                     waitInSec(1000);
                     dpadNavigation();
                     waitInSec(1000);
                     dpadRight();
                     waitInSec(1000);
                     isNavigatedtoLive(); 
                     dpadRight();
                     waitInSec(1000);
                     dpadNavigation();
                     waitInSec(1000);
                     dpadRightLoop(3, 1000);
                     waitInSec(10000);
                     if(wsnmodel.checkSetting()){
                     return UIActionResult.actionSuccess("Navigation to Settings passed");
                     }else{
                    	 return UIActionResult.actionFailedOptional("Navigation to Settings failed");
                     }
              } catch (Exception e) {
                     return UIActionResult.actionFailedOptional("Navigation Test failed" + e);
              }
 
       }
 
       private void dpadNavigation() {
		dpadDownLoop(6, 1000);
    	   dpadRightLoop(6, 1000);
    	   dpadLeftLoop(7, 1000);
    	   dpadUpLoop(8, 1000);
		
	}

	private UIActionResult navigateToSettings() {
    	   try{
    	   for (int i = 0; i < 3; i++) {
               waitInSec(5000);
               dpadRight();
         }
         waitInSec(5000);
         log.info("Checking if Navigated to Live");
         isNavigatedtoLive(); 
         for (int i = 0; i < 3; i++) {
               waitInSec(5000);
               dpadRight();
         }
         waitInSec(5000);
         if(!wsnmodel.checkSetting()){
         return UIActionResult.actionFailed("Unable to navigate to settings page.");
         }
         return UIActionResult.actionSuccess();
    	   }catch (Exception e) {
               return UIActionResult.actionFailedOptional("Unable to navigate to settings. " + e);
        }

 }
         
	

	/**
       * Verifying logout verification
       * 
       * @return UIActionResult
       */
       public UIActionResult VerifyLogoutVerification() {
              log.info("Logout verification starts");
              try {
            	     navigateToAppHome();
            	     navigateToSettings();
                     waitInSec(1000);
                     dpadDown();
                     waitInSec(1000);
                     dpadCenter();
                     waitInSec(6000);
                     if (!wsnmodel.getSignoutStatus().equalsIgnoreCase("Sign In")) {
                           return UIActionResult.actionFailedOptional("Logout verification failed");
                     }
                     return UIActionResult.actionSuccess("Logout verification passed");
              } catch (Exception e) {
                     return UIActionResult.actionFailedOptional("Logout verification failed");
              }
       }
 
       /**
       * Verifying re-launch verification
       * 
       * @return UIActionResult
       */
       public UIActionResult VerifyExitVerification() {
              log.info("Exit verification starts");
              try {
                     waitInSec(1000);
                     goBack();
                     waitInSec(1000);
                     goBack();
                     waitInSec(1000);
                     dpadCenter();
                     waitInSec(2000);
                     if (wsnmodel.isLogoElementPresent()) {
                           return UIActionResult.actionFailed("App exit verification failed");
                     }
                     return UIActionResult.actionSuccess("App exit verification passed");
 
              } catch (Exception e) {
                     return UIActionResult.actionFailed("App exit verification failed");
              }
       }
 
       /**
       * Verifying activation verification
       * 
       * @return UIActionResult
       */
       public UIActionResult activateVerification() {
              log.info("Activation verification starts");
              try {
                     waitInSec(1000);
                     navigateToAppHome();
                     navigateToSettings();
                     waitInSec(1000);
                     dpadRight();
                     if (wsnmodel.isactivationRequired()) {
                           webactivation();
                           waitInSec(10000);
                     } 
                     log.info("app is already activated");
                     if (!wsnmodel.verifyOptimumOnHome()) {
                           return UIActionResult.actionFailed("App activation failed");
                     }
                     return UIActionResult.actionSuccess("App activation passed");
              } catch (Exception e) {
                     return UIActionResult.actionFailed(e);
              }
       }
   

}
 

package us.macro;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
 
import com.firetv.action.UIActionResult;
import com.firetv.commonpojo.AppTestDataPojo;
import us.model.GulfPortModel;
import com.firetv.util.UIMacroBase;
import com.sun.jna.platform.win32.COM.DispatchVTable.GetTypeInfoCountCallback;
 
public class GulfPortMacro extends UIMacroBase{
 
       private GulfPortModel gulfportmodel;
       private CommonMacroSteadystate commonmacrosteadystate;
       private AppTestDataPojo appTestData = new AppTestDataPojo();
       private DateFormat timeValue = new SimpleDateFormat("mm:ss");
       private int seekbarcounttime=0;
 
       private Date Currentime; //used to determine timings from seekbar.
       private Date Benchtime;
       String time1;  
       String time2;
       String time3;
       String time4;
       String time5;
 
       public GulfPortMacro() {
              super();
              gulfportmodel = new GulfPortModel(driver);
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
                   log.info("got logo");
                     int qq=0;
                     while (!gulfportmodel.isLogoElementPresent()) {
                    	 log.info("Waiting for app to load..!");
                    	 waitInSec(10000);
                    	 qq++;
                    	 if(qq>5){
                    		 log.info("App home page not loaded for long time");
                    		 break;
                    	 }
                     }
                     if (!gulfportmodel.isLogoElementPresent()) {
                           return UIActionResult.actionFailed("Logo is not present in home page");
                     }
                     return UIActionResult.actionSuccess("App launch passed.");
              } catch (Exception e) {
                     return UIActionResult.actionFailed("App launch failed" +e);
              }
       }
 
       /**
       * Verifying video under selected tabs
       * 
       * @return UIActionResult
       */
       public UIActionResult playVideoUnderTabs(navigationTabName tab, String searchText) {
              log.info("Selecting tabs under "+tab);
              System.out.println(tab);
              try {
            	   waitInSec(2000);
                   commonmacrosteadystate.videoPlayPrerequisite(appTestData.getDsn());
                     waitInSec(2000);
                     {
                           switch (tab) {
                           case GulfportLive:
                                  dpadDownLoop(2, 3000);
                                  dpadCenter();
                                 log.info(gulfportmodel.checkError()); //checking for video playback error.
                                 if(gulfportmodel.checkError()){
                                	 return UIActionResult.actionFailedOptional("Video playback error");
                                 }else{
                                	 UIActionResult.actionSuccess("Video playing without error");
                                 }                                                            
                                 waitInSec(4000);
                                  break;
                           case Football:
                        	      waitInSec(2000);
                                  dpadUp();
                                  waitInSec(1000);
                                  dpadCenter();
                                  waitInSec(1000);
                                  dpadDown();
                                  waitInSec(1000);
                                  dpadCenterLoop(1, 2000);
                                  dpadDownLoop(2, 2000);
                                  dpadCenter();                               
                                  waitInSec(4000);
                                  break;
                            case SEARCH:
                               dpadUpLoop(1, 1000);
                               dpadCenter();
                               dpadUp();
                               dpadCenterLoop(1, 1000);
                               log.info("Checking if Search result present");
                               gulfportmodel.enterSearchtext("ocean");
                               dpadDown();
                               dpadCenterLoop(1, 2000);
                               break;
                           }
                     }
                     if(gulfportmodel.checkVideoPlay()){
                     return UIActionResult.actionSuccess("Video play started..!");
                     }else{
                    	 dpadCenter();
                    	 waitInSec(1000);
                    	 return UIActionResult.actionFailed("Video play failed.!");
                   }
              } catch (Exception e) {
                     return UIActionResult.actionFailed("Video play Test failed "+ e);
              }
			
       }
 
     


	public enum navigationTabName {
 
    	   GulfportLive, Football, SEARCH,
 
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
            	    
                     commonmacrosteadystate.getFrameRate(appTestData.getDsn());
         			return UIActionResult.actionSuccess("Video playback verification passed");
      		} catch (Exception e) {
      			return UIActionResult.actionFailedOptional("Video playback verification failed");
      		}
      	}
 
       /**
       * Verifying video pause verification
       * 
       * @param DSN
       * @return UIActionResult
       */
       public UIActionResult verifyVideoPauseVerification() {
              log.info("Starting video pause verification ");
              try {
                  waitInSec(2000);
                  pause();
                  waitInSec(2000);
                  time2 = gulfportmodel.getCurrentTime();
                  log.info(time2);
                  waitInSec(10000);
                  time4 = gulfportmodel.getCurrentTime();
                  log.info(time4);
                  dpadDown();
                  waitInSec(2000);
                  log.info("Resuming back...");
                  waitInSec(2000);
                  if (!time2.equals(time4)) {
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
   		log.info("-Executing Video Forward verification-");
   		try {
   			
   			waitInSec(5000);
   			pause();
   			waitInSec(1000);
   			time3 = gulfportmodel.getCurrentTime();
   			Currentime = timeValue.parse(time3);
   			log.info(time3);
   			pause();
   			for (int i = 0; i < 4; i++) {
   				waitInSec(2000);
   				seekForward();
   			}
   			waitInSec(1000);
   			pause();
   			waitInSec(1000);
   			time1 = gulfportmodel.getCurrentTime();
   			waitInSec(1000);
   			Benchtime = timeValue.parse(time1);
   			log.info(time1);
   			pause();
   			waitInSec(10000);
   			if (Currentime.after(Benchtime)) {
   				return UIActionResult.actionFailedOptional("Video playback forward Failed");
   			}
   			return UIActionResult.actionSuccess("Video forward verification passed");
   		} catch (Exception e) {
   			return UIActionResult.actionFailedOptional("Video playback forward Failed");
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
                     while ((gulfportmodel.isSeekbarPresent() == false) && (seekbarcounttime<=6)) {
                           log.info("Invoking seekbar");
                           waitInSec(1000);
                           dpadDown();
                           waitInSec(1000);
                           pause();
                           gulfportmodel.isSeekbarPresent();
                     }
                     if((gulfportmodel.isSeekbarPresent() == true) && (seekbarcounttime<=7))
                     {
                            waitInSec(1000);
                           time3 = gulfportmodel.getCurrentTime();
                           Benchtime = timeValue.parse(time3);
                           log.info(time3);
                           log.info("Resuming back...");
                           pause();
                           for (int i = 0; i < 5; i++) {
                                  waitInSec(2000);
                                  seeKBackward();
                           }
                           waitInSec(1000);
                           
                           pause();
                      
                           waitInSec(1000);
                           time5 = gulfportmodel.getCurrentTime();
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
                     if (!gulfportmodel.isvideoPresent()) {
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
                           if (gulfportmodel.isLogoElementPresent()) {
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
                           }                         
                           if (gulfportmodel.isvideoPresent()) {
                                  log.info("Setting From HUD Navigation Passed");
                           }
                     } else {
                           log.info("Setting test case in not required for Smart TV");
                           log.info("Pressing back to go back to video from HUD");
                           goBack();
                           waitInSec(5000);
                     }                 
                     if (!gulfportmodel.isvideoPresent()) {
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
                     if (gulfportmodel.isLogoElementPresent()) {
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
                           waitInSec(10000);
                     }                   
                     if (!gulfportmodel.isvideoPresent()) {
                           return UIActionResult.actionFailed("Relaunch Failed");
                     }
                     return UIActionResult.actionSuccess("Relaunch verification passed");
              } catch (Exception e) {
                     return UIActionResult.actionFailed("Relaunch test Failed" + e);
              }
       }
 
      
 
       public UIActionResult navigationTest() {
              try {
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
                     dpadRight();
                     waitInSec(1000);
                     dpadNavigation();
                     waitInSec(1000);
                     dpadRightLoop(3, 1000);
                     waitInSec(10000);
                     if(gulfportmodel.checkSetting()){
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
         for (int i = 0; i < 3; i++) {
               waitInSec(5000);
               dpadRight();
         }
         waitInSec(5000);
         if(!gulfportmodel.checkAbout()){
         return UIActionResult.actionFailed("Unable to navigate to settings page.");
         }
         return UIActionResult.actionSuccess();
    	   }catch (Exception e) {
               return UIActionResult.actionFailedOptional("Unable to navigate to settings. " + e);
        }

 }
         
	
	 private UIActionResult navigateToAppHome() {
         try {
       	  if(gulfportmodel.titlePresent()){
       	  int title =0;
       	  while(!gulfportmodel.titlePresent()){
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
                 if (!gulfportmodel.isSearchselected()) {
                       return UIActionResult.actionFailed("Unable to navigate to Search");
                 }
                 return UIActionResult.actionSuccess("Navigation to Search Success");
          } catch (Exception e) {
                 return UIActionResult.actionFailed(e);
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
                     if (gulfportmodel.isLogoElementPresent()) {
                           return UIActionResult.actionFailed("App exit verification failed");
                     }
                     return UIActionResult.actionSuccess("App exit verification passed");
 
              } catch (Exception e) {
                     return UIActionResult.actionFailed("App exit verification failed");
              }
       }
 

}
 

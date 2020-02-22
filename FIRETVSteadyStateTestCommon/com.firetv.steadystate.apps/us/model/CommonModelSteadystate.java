package us.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.firetv.action.UIActionResult;
import us.locator.CommonLocatorSteadystate;
import com.firetv.util.UIModelBase;

public class CommonModelSteadystate extends UIModelBase {

	WebDriver driver;
	CommonLocatorSteadystate commonlocatorsteadystate;

	public CommonModelSteadystate(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		commonlocatorsteadystate = new CommonLocatorSteadystate();
	}

	public void switchFromFrame() {
		switchFromFrameIndex(2);
	}

	/**
	 * Verifying - is xray layout present.
	 * 
	 * @return boolean value
	 */
	public boolean isXrayLayoutPresent() {
		log.info("Checking for xray layout opened" + commonlocatorsteadystate.xrayLayout);
		return isElementPresent(commonlocatorsteadystate.xrayLayout);
	}

	/**
	 * Clicking the advance option in x-ray overlay
	 */
	public void clickXrayAdvacneOption() {
		log.info("Clicking advance option button" + commonlocatorsteadystate.xrayAdvanceOption);
		doClick(commonlocatorsteadystate.xrayAdvanceOption);
	}

	/**
	 * Retrieve the text from advanced option
	 * 
	 * @return
	 */
	public String getAdvanceOptionText() {
		log.info("Retrieving advance option text" + commonlocatorsteadystate.xrayAdvanceOption);
		return getText(commonlocatorsteadystate.xrayAdvanceOption);
	}

	public void typeOptimumID(String optimumID) {
		log.info("Write optimum id in text field");
		doTypeInWeb(commonlocatorsteadystate.optiumID, optimumID);
	}

	public void typeOptimumPassword(String optiumumPassword) {
		log.info("Type optimum password");
		doTypeInWeb(commonlocatorsteadystate.optiumPassword, optiumumPassword);
	}

	public void clickSignIn() {
		log.info("Clicking signin button");
		doClickInWeb(commonlocatorsteadystate.optimumSignIn);
	}
	
	public void enterUserName(String username) {
        log.info("Writing code in Search text field");
        doTypeInWeb(commonlocatorsteadystate.atnTUserName, username);
    }
    
    public void enterPassword(String pswd) {
        log.info("Writing code in Search text field");
        doTypeInWeb(commonlocatorsteadystate.atnTPassWord, pswd);
    }
    
    public void clickOnSignInButton() {
        log.info("Clicking on Sign in");
        doClickInWeb(commonlocatorsteadystate.atnTSignIn);
    }

	public void enterFacebookEmail(String optimumID) {
		log.info("Type Facebook Id");
		doTypeInWeb(commonlocatorsteadystate.enterFacebookEmail, optimumID);
	}

	public void enterFacebookPassword(String optimumPassword) {
		log.info("Type Facebook Password");
		doTypeInWeb(commonlocatorsteadystate.EnterFacebookPassword, optimumPassword);
	}

	public void clickOnFacebookLogintab() {
		log.info("Clicking Login button");
		doClickInWeb(commonlocatorsteadystate.FacebookLogintab);
	}
	
	public void enterUsernameOptimumActivation(String username){
		log.info("Entering username for Profile Activation");
		doClick(commonlocatorsteadystate.usernameOptimumActivation);
		doType(commonlocatorsteadystate.usernameOptimumActivation, username);
	}

	public void enterPasswordOptimumActivation(String password){
		log.info("Entering password for Profile Activation");
		doType(commonlocatorsteadystate.passwordOptimumActivation, password);
	}

	public void clickSignInOptimumActivation() {
		log.info("Clicking on Sign In");
		doClick(commonlocatorsteadystate.signinOptimumActivation);
	}
	
	public boolean isOldSmtpHUDSettingsPresent() {
		log.info("Checking for the availibility of the old Setting Element");
		return isElementsPresent(commonlocatorsteadystate.oldhudSmtpsettings);
	}
	
	public boolean isNewSmtpHUDSettingsPresent() {
		log.info("Checking for the availibility of the new Setting Element");
		return isElementsPresent(commonlocatorsteadystate.newhudSmtpsettings);
	}

	public boolean isSettingpageloaded() {
		log.info("Checking for the Availibility of the Setting page" + commonlocatorsteadystate.deviceSettingPage);
		return isElementsPresent(commonlocatorsteadystate.deviceSettingPage);
	}

	public void isadPresent(By by) {
		if (isElementsPresent(by)) {
			log.info("waiting for Advertiesement to finsih");
			while (isElementsPresent(by)) {
				isElementsPresent(by);
				waitInSec(5000);
			}
			waitInSec(5000);
			log.info("Advertisement Finished");
		} 
	}
	
	public boolean isHudForSMTP(By by) {
		return isElementPresentBoolean(by);
	}

	

	public boolean isDeviceHomePageLoaded() {
		log.info("Checking home page loading");
		return isElementsPresent(commonlocatorsteadystate.appHomePageVerfication);
	}
	public boolean isSmartTVHUDPresent() {
		log.info("Checking for the availibility of the HUD overlay on smart TV's");
		return isElementsPresent(commonlocatorsteadystate.hudforSmartTV);
	}

	/**
	 * Switching to frame
	 * 
	 */
	public void switchFrame(int frameIndex) {
		switchFromFrameIndex(frameIndex);
	}
	
	/**
	 * Switching to frame
	 * 
	 */
	public void switchFramebyIDorName(String frameIDorName) {
		waitInSec(1000);
		switchFromFrame(frameIDorName);
	}

	public Boolean checkBuyButton() {
		log.info("Checking buy button");
		return isElementsPresent(commonlocatorsteadystate.buyButton);
	}

	public String getButtonName() {
		return getContentDescription(commonlocatorsteadystate.buyButton);
	}
	
	public Boolean screenSavePresent() {
		return isElementsPresent(commonlocatorsteadystate.screenSave);
	}
	



}

package us.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import us.locator.WSNLocator;

import com.firetv.util.UIModelBase;

import io.appium.java_client.AppiumDriver;

public class WSNModel extends UIModelBase {

	private WebDriver driver;
	private WSNLocator wsnlocator;

	private CommonModelSteadystate commonmodelsteadystate;

	public WSNModel(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wsnlocator = new WSNLocator();
		commonmodelsteadystate = new CommonModelSteadystate(driver);
	}

	public boolean isLogoElementPresent() {
		log.info("Checking app logo"+ wsnlocator.logo );
		return isElementsPresent(wsnlocator.logo);
	}

	public String getValidationCode() {
		log.info("Getting Activation code");
		return getText(wsnlocator.getCode);
	}

	public void textFieldActivationCode(String code) {
		log.info("Writing code in activation text field");
		doTypeInWeb(wsnlocator.activationTextBox, code);
	}

	public void clickActivate() {
		log.info("Clicking Activate button");
		doClickInWeb(wsnlocator.activate);
	}

	public void chooseOptimum() {
		log.info("Clicking Optimum");
		doClickInWeb(wsnlocator.optimum);

	}

	public void isAdsPresent() {
		log.info("Checking ad is present or not");
		commonmodelsteadystate.isadPresent(wsnlocator.ad);
	}

	public String getCurrentTime() {
		log.info("Getting video time");
		return getText(wsnlocator.currentTime);
	}

	public boolean isvideoPresent() {
		log.info("Checking video present or not");
		return isElementsPresent(wsnlocator.video);
	}

	public boolean isStartOverPresent() {
		log.info("Checking startover button");
		return isElementsPresent(wsnlocator.startOver);
	}

	public void clickStartOver() {
		log.info("Clicking startover");
		doClick(wsnlocator.startOver);
	}

	public boolean isSeekbarPresent() {
		log.info("Checking seekbar");
		return isElementsPresent(wsnlocator.seekBar);
	}

	public void enterSearchtext(String searchText) {
		log.info("Writing code in Search text field");
		doType(wsnlocator.searchBox, searchText);
	}

	public boolean isSignOutPresent() {
		log.info("Checking signout");
		return isElementsPresent(wsnlocator.signOutButton);
	}

	public String getSignoutStatus() {
		log.info("Checking signin status");
		return getText(wsnlocator.signOutButton);
	}

	public boolean isLiveTVselected() {
		log.info("Checking live TV tab");
		return isElementsPresent(wsnlocator.liveTVcontent);
	}

	public boolean isSearchselected() {
		log.info("Checking search page presence");
		return isElementsPresent(wsnlocator.searchBox);
	}

	public boolean isBrowseVideoContainerPresent() {
		log.info("Checking video containers");
		return isElementsPresent(wsnlocator.videoContainer);
	}

	public boolean isactivationRequired() {
		log.info("Checking activation status");
		return isElementsPresent(wsnlocator.getCode);
	}

	public boolean verifyOptimumOnHome() {
		log.info("Checking Optimum logo on app home");
		return isElementsPresent(wsnlocator.optimumIconOnHome);
	}

	public boolean isContinueWatchPresent() {
		log.info("Checking Continue Watch is present or not!");
		return isElementsPresent(wsnlocator.continueWatching);
	}

	public boolean isShowsVideoFramePresent() {
		log.info("Checking shows video frame is present or not!");
		return isElementPresent(wsnlocator.framePresent);
	}

	public boolean checkSetting() {
		log.info(wsnlocator.settingsPage);
		return isElementsPresent(wsnlocator.settingsPage);
	}

	public boolean checkCodePage() {
		return isElementsExistInWeb(wsnlocator.activationTextBox);
	}

	public boolean webCheckSuccess() {
		return isElementsExistInWeb(wsnlocator.checkSuccess);
	}

	public boolean titlePresent() {
		return isElementsPresent(wsnlocator.titleHome);
	}

	public boolean checkVideoPlay() {
		return isElementsPresent(wsnlocator.videoPlay);
	}

}

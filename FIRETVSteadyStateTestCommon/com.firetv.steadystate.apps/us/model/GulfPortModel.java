package us.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import us.locator.GulfPortLocator;

import com.firetv.util.UIModelBase;

import io.appium.java_client.AppiumDriver;

public class GulfPortModel extends UIModelBase {

	private WebDriver driver;
	private GulfPortLocator gulfportlocator;

	private CommonModelSteadystate commonmodelsteadystate;

	public GulfPortModel(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		gulfportlocator = new GulfPortLocator();
		commonmodelsteadystate = new CommonModelSteadystate(driver);
	}

	public boolean isLogoElementPresent() {
		log.info("Checking app logo"+ gulfportlocator.logo );
		return isElementsPresent(gulfportlocator.logo);
	}

	public boolean checkVideoPlay() {
		log.info("Checking if video plays"+ gulfportlocator.videoPlayer);
		return isElementsPresent(gulfportlocator.videoPlayer);
	}

//	public String getValidationCode() {
//		log.info("Getting Activation code");
//		return getText(gulfportlocator.getCode);
//	}
//	public String getErrorCode() {
//		log.info("Getting e code");
//		return getText(gulfportlocator.);
//	}
	public boolean checkError() {
		return isElementPresent(gulfportlocator.videoerror);
	}

//	public void textFieldActivationCode(String code) {
//		log.info("Writing code in activation text field");
//		doTypeInWeb(gulfportlocator.activationTextBox, code);
//	}
//
//	public void clickActivate() {
//		log.info("Clicking Activate button");
//		doClickInWeb(gulfportlocator.activate);
//	}
//
//	public void chooseOptimum() {
//		log.info("Clicking Optimum");
//		doClickInWeb(gulfportlocator.optimum);
//
//	}
//
//	public void isAdsPresent() {
//		log.info("Checking ad is present or not");
//		commonmodelsteadystate.isadPresent(gulfportlocator.ad);
//	}
//
	
	
	
	public String getCurrentTime2() {
		log.info("Getting video time");
		return getText(gulfportlocator.currentTime2);
	}
	

	public boolean isvideoPresent() {
		log.info("Checking video present or not");
		return isElementsPresent(gulfportlocator.video);
	}
//
//	public boolean isStartOverPresent() {
//		log.info("Checking startover button");
//		return isElementsPresent(gulfportlocator.startOver);
//	}
//
//	public void clickStartOver() {
//		log.info("Clicking startover");
//		doClick(gulfportlocator.startOver);
//	}
//
	public boolean isSeekbarPresent() {
		log.info("Checking seekbar");
		return isElementsPresent(gulfportlocator.seekBar);
	}
//
	public void enterSearchtext(String searchText) {
		log.info("Writing code in Search text field");
		doType(gulfportlocator.searchBox, searchText);
	}

	

	

//	public boolean isLiveTVselected() {
//		log.info("Checking live TV tab");
//		return isElementsPresent(gulfportlocator.liveTVcontent);
//	}
//
	public boolean isSearchselected() {
		log.info("Checking search page presence");
		return isElementsPresent(gulfportlocator.searchBox);
	}

//	public boolean isBrowseVideoContainerPresent() {
//		log.info("Checking video containers");
//		return isElementsPresent(gulfportlocator.videoContainer);
//	}
//
//	public boolean isactivationRequired() {
//		log.info("Checking activation status");
//		return isElementsPresent(gulfportlocator.getCode);
//	}
//
//	public boolean verifyOptimumOnHome() {
//		log.info("Checking Optimum logo on app home");
//		return isElementsPresent(gulfportlocator.optimumIconOnHome);
//	}
//
//	
//	public boolean isShowsVideoFramePresent() {
//		log.info("Checking shows video frame is present or not!");
//		return isElementPresent(gulfportlocator.framePresent);
//	}
//
	public boolean checkAbout() {
		log.info(gulfportlocator.settingsPage);
		return isElementsPresent(gulfportlocator.settingsPage);
	}

	public String getCurrentTime() {
		log.info("getting current time");
		return getText(gulfportlocator.currentTime);
	}

	

//	public boolean checkCodePage() {
//		return isElementsExistInWeb(gulfportlocator.activationTextBox);
//	}
//
//	public boolean webCheckSuccess() {
//		return isElementsExistInWeb(gulfportlocator.checkSuccess);
//	}
//
//	public boolean titlePresent() {
//		return isElementsPresent(gulfportlocator.titleHome);
//	}
//
//	public boolean checkVideoPlay() {
//		return isElementsPresent(gulfportlocator.videoPlay);
//	}

}

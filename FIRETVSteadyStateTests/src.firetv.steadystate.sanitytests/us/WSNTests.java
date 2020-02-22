package us;

import java.io.IOException;
import java.text.ParseException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.firetv.api.JsonHandling;
import com.firetv.commonpojo.AppTestDataPojo;
import us.macro.WSNMacro.navigationTabName;
import us.macro.WSNMacro;
import com.firetv.precondition.DeviceAccountRegister;
import com.firetv.precondition.DeviceNetwork;
import com.firetv.precondition.PDI;
import com.firetv.precondition.SystemNetwork;
import com.firetv.util.UITestBase;

@Listeners(com.firetv.action.MyTestListenerAdapter.class)
public class WSNTests extends UITestBase {

	private WSNMacro wsnmacro;
	private JsonHandling jsonhandling;
	public static String app;



	@BeforeClass(alwaysRun = true)
	@Parameters({ "ASIN" })
	public void precondition(String ASIN)
			throws Exception, InterruptedException, ParseException {
		initPrerequestiesTest("testAppJsonPathUS", ASIN);
		AppTestDataPojo appTestData = new AppTestDataPojo();
		DeviceNetwork deviceNetwork = new DeviceNetwork();
		SystemNetwork systemNetwork = new SystemNetwork();
		DeviceAccountRegister deviceAccountRegister = new DeviceAccountRegister();
		deviceNetwork.DeviceNetworkPrecondition();
	//	deviceAccountRegister.deviceAccountSetup();
//		systemNetwork.setUp();
		PDI.Test_PDIVerification(ASIN);
	}
	
	@BeforeMethod (alwaysRun = true)
	public void testSetUp()
			throws Exception, InterruptedException, ParseException {
		initAppium();
		wsnmacro = new WSNMacro();
		
	}

	
	
	@Test(retryAnalyzer = com.firetv.action.RetryAnalyzer.class, groups = { "sanity" }, priority=1)
	public void navigationVerification() throws IOException {
		wsnmacro.verifyLogoPresent();
		//wsnmacro.navigationTest();
		softAssert.assertAll();
	}

	@Test(retryAnalyzer = com.firetv.action.RetryAnalyzer.class, groups = {"ille" }, priority=2)
	@Parameters({ "searchText" })
	public void homeVideoSection_forILLE(String searchText) throws IOException {
		wsnmacro.verifyLogoPresent();
		wsnmacro.playVideoUnderTabs(navigationTabName.HOME, searchText);
		wsnmacro.verifyVideoPlayBackVerification();
		softAssert.assertAll();
	}

	@Test(retryAnalyzer = com.firetv.action.RetryAnalyzer.class, groups = { "sanity"}, priority=3)
	@Parameters({"searchText" })
	public void featuredVideoSection(String searchText) throws IOException {
		wsnmacro.verifyLogoPresent();
		//wsnmacro.playVideoUnderTabs(navigationTabName.FEATURED, searchText);
		wsnmacro.verifyVideoPlayBackVerification();
		wsnmacro.verifyVideoPauseVerification();
		wsnmacro.verifyForwardVerification();
		wsnmacro.verifyRewindVerification();
		wsnmacro.verifyHUDVerification();
		wsnmacro.verifyHUDSettingsVerification();
		wsnmacro.verifyRelaunchVerification();
		softAssert.assertAll();
	}

	@Test(retryAnalyzer = com.firetv.action.RetryAnalyzer.class, groups = { "sanity"}, priority=5)
	@Parameters({ "searchText" })
	public void showsVideoPlaybackVerification(String searchText) throws IOException {
		wsnmacro.verifyLogoPresent();
		wsnmacro.playVideoUnderTabs(navigationTabName.SHOWS,searchText);
		wsnmacro.verifyVideoPlayBackVerification();
		wsnmacro.verifyVideoPauseVerification();
		wsnmacro.verifyForwardVerification();
		wsnmacro.verifyRewindVerification();    
		softAssert.assertAll();
	}

	

	@Test(retryAnalyzer = com.firetv.action.RetryAnalyzer.class, groups = { "sanity"}, priority=7)
	@Parameters({"searchText"})
	public void searchvideoSection(String searchText){
		wsnmacro.verifyLogoPresent();
		wsnmacro.playVideoUnderTabs(navigationTabName.SEARCH,searchText);
		wsnmacro.verifyVideoPlayBackVerification();
		wsnmacro.verifyVideoPauseVerification();
		wsnmacro.verifyForwardVerification();
		wsnmacro.verifyRewindVerification();    
		softAssert.assertAll();
	}

//	@Test(retryAnalyzer = com.firetv.action.RetryAnalyzer.class, groups = { "sanity"}, priority=8)
//	@Parameters({"searchText" })
//	public void livevideoplayVideosInHomeSection(String searchText){
//		wsnmacro.verifyLogoPresent();
//		wsnmacro.playVideoUnderTabs(navigationTabName.LIVE, searchText);
//		wsnmacro.verifyVideoPlayBackVerification();
//		softAssert.assertAll();
//	}
	
	@Test(retryAnalyzer = com.firetv.action.RetryAnalyzer.class, groups = { "sanity", "ille" }, priority=9)
	public void logoutAndExitVerification() throws IOException {
		wsnmacro.verifyLogoPresent();
		wsnmacro.VerifyLogoutVerification();
		wsnmacro.VerifyExitVerification();
		softAssert.assertAll();
	}

}

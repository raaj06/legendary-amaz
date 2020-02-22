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
import us.macro.GulfPortMacro.navigationTabName;
import us.macro.GulfPortMacro;

import com.firetv.precondition.DeviceAccountRegister;
import com.firetv.precondition.DeviceNetwork;
import com.firetv.precondition.PDI;
import com.firetv.precondition.SystemNetwork;
import com.firetv.util.UITestBase;

@Listeners(com.firetv.action.MyTestListenerAdapter.class)
public class GulfPortTests extends UITestBase {

	private GulfPortMacro gulfportmacro;
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
//		deviceNetwork.DeviceNetworkPrecondition();
//	    deviceAccountRegister.deviceAccountSetup();
//      systemNetwork.setUp();
//		PDI.Test_PDIVerification(ASIN);
	}
	
	@BeforeMethod (alwaysRun = true)
	public void testSetUp()
			throws Exception, InterruptedException, ParseException {
		initAppium();
		gulfportmacro = new GulfPortMacro();
	}
	
	@Test(retryAnalyzer = com.firetv.action.RetryAnalyzer.class, groups = { "sanity" }, priority=1)
	public void navigationVerification() throws IOException {
         gulfportmacro.verifyLogoPresent();
		 softAssert.assertAll();
	}
	
	@Test(retryAnalyzer = com.firetv.action.RetryAnalyzer.class, groups = {"sanity" }, priority=2)
	@Parameters({ "searchText" })
	public void homeVideoSection_forILLE(String searchText) throws IOException {
		gulfportmacro.verifyLogoPresent();
		gulfportmacro.playVideoUnderTabs(navigationTabName.GulfportLive, searchText);
		gulfportmacro.verifyVideoPlayBackVerification();
		softAssert.assertAll();
	}
	
	@Test(retryAnalyzer = com.firetv.action.RetryAnalyzer.class, groups = { "ille"}, priority=1)
	@Parameters({"searchText" })
	public void featuredVideoSection(String searchText) throws IOException {
		gulfportmacro.verifyLogoPresent();
		gulfportmacro.playVideoUnderTabs(navigationTabName.Football, searchText);
		gulfportmacro.verifyVideoPlayBackVerification();
		gulfportmacro.verifyVideoPauseVerification();
        gulfportmacro.verifyForwardVerification();
//		gulfportmacro.verifyRewindVerification();
//		gulfportmacro.verifyHUDVerification();
//		gulfportmacro.verifyHUDSettingsVerification();
//		gulfportmacro.verifyRelaunchVerification();
//		softAssert.assertAll();
	}
	
	@Test(retryAnalyzer = com.firetv.action.RetryAnalyzer.class, groups = { "sanity"}, priority=4)
	@Parameters({"searchText"})
	public void searchvideoSection(String searchText){
		gulfportmacro.verifyLogoPresent();
		gulfportmacro.playVideoUnderTabs(navigationTabName.SEARCH,searchText);
		gulfportmacro.verifyVideoPlayBackVerification();
		gulfportmacro.verifyVideoPauseVerification();
		gulfportmacro.verifyForwardVerification();
		gulfportmacro.verifyRewindVerification();    
		softAssert.assertAll();
	}
	
}
	
	
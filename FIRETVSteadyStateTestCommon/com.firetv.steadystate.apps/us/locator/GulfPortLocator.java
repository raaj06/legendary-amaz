package us.locator;

import org.openqa.selenium.By;

import com.firetv.commonpojo.AppTestDataPojo;
 
public class GulfPortLocator {  
	String acti = AppTestDataPojo.getAppPackage();
	String trim = acti.substring(21, 26);
	
	//public final By playPauseButtonTwo = By.id("com.discovery.firetv."+trim+":id/play");

	// public final By continueWatchingTest = By.id("com.discovery.firetv."+trim+":id/continue_button");
    
	   public final By logo = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[1]");
	   public final By videoPlayer = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.view.View[2]");
	   public final By framePresent = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.support.v7.widget.RecyclerView/android.widget.LinearLayout/android.widget.LinearLayout/android.support.v7.widget.RecyclerView/android.widget.FrameLayout[1]/android.widget.RelativeLayout/android.view.View");
       public final By getCode = By.id("com.discovery.firetv.velgo:id/code_text");
       public final By videoerror =By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]");
       
       
       public final By activationTextBox = By.xpath("//*[@name='code']");
       public final By activate = By.xpath("//*[@class='activateDeviceSubmit']");
       public final By optimum = By.xpath("//*[@id='react-root']/div/div[1]/div/main/div/div/div[2]/div[2]/div/div/ul/li[3]/div");
       public final By sucessMessage = By.xpath("//*[@id='react-root']/div/div[1]/div/main/div/main/h1");
       public final By ad = By.id("com.discovery.firetv.velgo:id/ad_counter");
       public final By currentTime2 = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[3]");
       public final By video = By.id("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.view.View[2]");
       public final By startOver = By.id("com.discovery.firetv.velgo:id/start_over_button");
       public final By seekBar = By.id("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]/android.view.View");
       public final By searchBox = By.id("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout");
       public final By signOutButton = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.Button[1]");
       public final By liveTVcontent = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.FrameLayout[4]/android.widget.Button");
       public final By videoContainer = By.id("com.discovery.firetv.velgo:id/row_content");
       public final By settingsPage = By.xpath("//*[@text='Contact Us']");
       public final By checkSuccess = By.xpath("//*[@class='activateDevice__mainSuccess']");
	   public final By titleHome = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[3]/android.view.View[2]");
	   public final By videoPlay = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.view.View[2]");
	   public final By currentTime = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[2]");
       
       
       
}
 

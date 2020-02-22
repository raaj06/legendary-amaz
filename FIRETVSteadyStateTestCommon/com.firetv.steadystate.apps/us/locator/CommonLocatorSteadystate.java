package us.locator;

import org.openqa.selenium.By;

public class CommonLocatorSteadystate {

	public final By xrayAdvanceOption = By.id("com.amazon.ssm:id/ssm_xray_advanced");
	public final By xrayLayout = By.xpath(
			"/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]");
	public final By optiumID = By.xpath("//*[@id='username']");
	public final By optiumPassword = By.xpath("//*[@id='password']");
	public final By optimumSignIn = By.xpath("//*[@id='signin_button']");
	public final By enterFacebookEmail = By.xpath("//*[@id=\"email\"]");
	public final By EnterFacebookPassword = By.xpath("//*[@id=\"pass\"]");
	public final By FacebookLogintab = By.xpath("//button[@id='loginbutton']");
	public final By oldhudSmtpsettings = By.id("com.amazon.tv.settings:id/hud_settings_button");
	public final By newhudSmtpsettings = By.id("com.amazon.tv.settings.v2:id/hud_settings_button");
	public final By deviceSettingPage = By.id("com.amazon.tv.launcher:id/horizontal_grid_view");
	public final By appHomePageVerfication = By.id("com.amazon.tv.launcher:id/horizontal_gridview");
	public final By hudforSmartTV= By.id("com.amazon.tv.quicksettings:id/list_container");
	public final By atnTUserName = By.id("nameBox");
    public final By atnTPassWord = By.id("pwdBox");
    public final By atnTSignIn = By.id("submitLogin");
    public final By usernameOptimumActivation = By.id("IDToken1");
	public final By passwordOptimumActivation = By.id("IDToken2");
	public final By signinOptimumActivation = By.id("signin_button");
	public final By buyButton = By.id("com.amazon.venezia:id/buy_button");
	public final By screenSave = By.id("com.amazon.ftv.screensaver:id/itemFrame");
}

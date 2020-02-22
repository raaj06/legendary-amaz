package com.firetv.interfaceinitialization;

public interface ADBCommands {

	public void adbUninstall(String appPackage);
	
	public void adbLaunch(String appPackage, String appActivity);
	
	public Process getOverlayService(String DSN);
	
	public void enableXray();
	
	public void adbForcestop(String appPackage);
	
	public void runAdb();
	
	public void clearAdb(String appPackage);
	
	public void inputTextUsingADB(String DSN,String text);
	
}

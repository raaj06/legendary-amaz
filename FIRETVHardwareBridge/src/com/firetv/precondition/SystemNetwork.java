package com.firetv.precondition;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.util.Arrays;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.firetv.commonpojo.AppTestDataPojo;


public class SystemNetwork {
	int delay1s = 1000;
	int delay2s = 2000;
	int delay3s = 3000;
	int delay4s = 4000;
	int delay1 = 5000;
	int delay2 = 10000;
	int delay3 = 20000;
	int delay4 = 30000;
	int delay5 = 40000;
	String username;
	AppTestDataPojo appTestData = new AppTestDataPojo();
	static String NetworkName;
	static String NetworkPassword;

	public void setUp() throws InterruptedException, IOException {

		System.out.println("Continuing with System network Check Test Case...");
		NetworkName=appTestData.getNetworkName();
		NetworkPassword=appTestData.getNetworkPassword();
		System.out.println(NetworkName + " Needed to proceed with the app");

		String OSName = null;
		OSName = System.getProperty("os.name");
		OSName = OSName.toLowerCase();
		if (OSName.contains("windows")) {
//			try {
//				Runtime rt = Runtime.getRuntime();
//				rt.exec("cmd /c start \"\" \"C:\\Users\\firetv\\Desktop\\cmd.exe - Shortcut\" netsh interface set interface Ethernet disable");
//				rt.exec("cmd /c cmd /k \" netsh interface set interface Ethernet disable Powrprof.dll,SetSuspendState");
//				Thread.sleep(this.delay2s); 
//
//				} catch (IOException e) { 
//				e.printStackTrace();
//				}
			
			int count = 0;
			try {    
						ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "C:\\Windows\\System32\\netsh wlan show profile");
				        builder.redirectErrorStream(true);
				        Process p = builder.start();
				        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
				        int j=0;
				        String line;
				        while (true) {
				            line = r.readLine();
				            j++;
				            if (line == null) { break; }
				            System.out.println(line);
				            if(line.contains(NetworkName)){
				            	count += 1;
				            }
				        }
			Thread.sleep(this.delay2s);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Thread.sleep(this.delay1);
			System.out.println("No. of times " + NetworkName + " Network Found in list of avialble Network ? : " + count);
			Thread.sleep(this.delay1);
			if (count >= 1) {
				
				int retryCount = 5;
				Boolean connectedNetwork = false;	
				Boolean connectedStatus = false;				

				while (connectedNetwork == false && retryCount > 0 && connectedStatus == false){
					try {
						ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c","C:\\Windows\\System32\\netsh wlan connect name=" + NetworkName+"");
				        builder.redirectErrorStream(true);
				        Process p = builder.start();
				   		Thread.sleep(this.delay2s);
				   		retryCount -= 1;
					} catch (IOException e) {
						e.printStackTrace();
					}
					Thread.sleep(this.delay1);
					try {    
						ProcessBuilder builderCheck = new ProcessBuilder("cmd.exe", "/c", "C:\\Windows\\System32\\netsh wlan show interfaces");
				        builderCheck.redirectErrorStream(true);
				        Process p = builderCheck.start();
				        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
				        String lineCheck;
				        while (true) {
				            lineCheck = r.readLine();
				            if (lineCheck == null) { break; }
				            System.out.println(lineCheck);
				            if(lineCheck.contains(NetworkName)){
				            connectedNetwork = true;
				         }
				            if(lineCheck.contains("connected")){
				            	connectedStatus = true;
				            }
				        }
				        if(connectedNetwork==true){
				        	if(connectedStatus==true){
				        	break;
				        	}else{
				        		System.out.println("Unable to connect to the network. Please re-enter correct password and try again...! ");
				        	}
				        }
				        Thread.sleep(this.delay2s);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
			
				Thread.sleep(this.delay2s);
				if ((count >= 1) && (connectedNetwork) && (connectedStatus)) {
					boolean result = true;
					Reporter.log("System Network : connected to " + NetworkName + " Successfully", true);
					Assert.assertTrue(result);
				}else if ((count >= 1) && (connectedNetwork==false)) {
					boolean result = false;
					Reporter.log("System Network :  " + NetworkName + " Network found. But Failed to connect...! Please check the password.", false);
					Assert.assertTrue(result);
				}
				else {
					boolean result = false;
					Reporter.log("System Network : connected to " + NetworkName + " Unsuccessfull", false);
					Assert.assertTrue(result);
				}
			} else {
				System.out.println(NetworkName + " is not available in system");
				boolean result = false;
				Reporter.log(NetworkName + " is not available in System", false);
				Assert.assertTrue(result);
			}
			Thread.sleep(delay1s);
		}
		if (OSName.contains("mac")) {

			try {
				System.out.println("Network Change running for Mac OS");
				Process process;
				Thread.sleep(delay3s);
				String[] commands = { "/bin/sh", "-c",
						"/System/Library/PrivateFrameworks/Apple80211.framework/Versions/A/Resources/airport scan" };
				process = Runtime.getRuntime().exec(commands);
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = "";
				Boolean NetworkNeededAvailable = false;
				try {
					while ((line = stdInput.readLine()) != null) {
						String[] words = line.split(" ");
						for (String word : words) {
							// System.out.print(word);
							if (word.equals(NetworkName)) {
								NetworkNeededAvailable = true;
							}
						}
					}
				} catch (Exception e) {
				}
				if (NetworkNeededAvailable == true) {
					System.out.println(NetworkName + " Network Found in Available WiFi Networks : Proceeding.....");
					process = Runtime.getRuntime()
							.exec("networksetup -setairportnetwork en0 " + NetworkName + " " + NetworkPassword + "");
					Thread.sleep(20000);
					String[] commands1 = { "/bin/sh", "-c",
							"/System/Library/PrivateFrameworks/Apple80211.framework/Versions/Current/Resources/airport -I | /usr/bin/grep -w 'SSID' | /usr/bin/cut -d: -f2" };
					process = Runtime.getRuntime().exec(commands1);
					BufferedReader stdInput1 = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String NewNetworkConnected1 = null;
					String s1 = null;
					while ((s1 = stdInput1.readLine()) != null) {
						NewNetworkConnected1 = s1;
					}
					//System.out.println("NewNetworkConnected : " + NewNetworkConnected1);
					NewNetworkConnected1 = NewNetworkConnected1.substring(1); // removing whiltespace
					System.out.println("NewNetworkConnected : " + NewNetworkConnected1);
					if (String.valueOf(NewNetworkConnected1).equals(String.valueOf(NetworkName))) {
						boolean result = true;
						Reporter.log("System Network : connected to " + NetworkName + " Successfully", true);
						Assert.assertTrue(result);
					} else {
						boolean result = false;
						Reporter.log("System Network : connected to " + NetworkName + " Unsuccessfull", false);
						Assert.assertTrue(result);
					}
				} else {
					boolean result = false;
					Reporter.log(NetworkName + " is not available in System", false);
					Assert.assertTrue(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
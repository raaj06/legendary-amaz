package com.firetv.api;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import org.json.JSONException;
//import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.firetv.commonpojo.AppTestDataPojo;
import com.firetv.db.DataPull;
import com.firetv.util.UIMacroBase;

public class JsonHandling {
	
	static String filePath;
	static String appName;
	static String appUsername;
	static String appPassword;
	static String deviceUsername;
	static String devicePassword;
	static String serviceProviderid;
	static String serviceProviderPassword;
	
	static String ASIN;
	
	public JsonHandling(String filePath, String ASIN){
		this.filePath=filePath;
		this.ASIN = ASIN;
	}
	
	public JsonHandling(String filePath, String appName, String appUsername, String appPassword, String deviceUsername, String devicePassword, String serviceProviderid, String serviceProviderPassword){
		this.filePath=filePath;
		this.appName=appName;
		this.appUsername = appUsername;
		this.appPassword = appPassword;
		this.deviceUsername = deviceUsername;
		this.devicePassword = devicePassword;
		this.serviceProviderid = serviceProviderid;
		this.serviceProviderPassword = serviceProviderPassword;
	}
	
	/**
	 * Handling JSON and set it into Pojo class
	 * @throws Exception 
	 */
    public static void setJSON(String ASIN) throws Exception
    {
    	
    	UIMacroBase macroBase = new UIMacroBase();
    	String deviceDSN = macroBase.getDeviceDSN();
    	String deviceOS = macroBase.getDeviceOS();
    	System.out.print("Device DSN "+ deviceDSN);
    	
        JSONParser jsonParser = new JSONParser();    
        try (FileReader reader = new FileReader(filePath))
        {
        	
        	//Reading the data from DB
            JSONObject appDataDB;
    		DataPull dataPull = new DataPull();
    		appDataDB = (JSONObject) dataPull.getDBAppData(ASIN).get("appdetails");
    		System.out.println("Test "+ appDataDB.get("app_username").toString());
        	
        	// Below code read data from Json File
        	Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject pojoObject = (JSONObject) jsonObject.get(appDataDB.get("app_name").toString());     
            
            FileInputStream ip = null;
    		String workSpaceLocation = System.getProperty("user.dir");
    		String workspacePath = workSpaceLocation.replace("FIRETVSteadyStateTests", "FIRETVDriverBase");
    			//prop = new Properties();
    			//ip = new FileInputStream(workspacePath + "/src/com/firetv/config/credentials.properties");
    			//prop.load(ip);
            AppTestDataPojo pojoClass = new AppTestDataPojo();
            pojoClass.setAppPackage(pojoObject.get("appPackage").toString());
            pojoClass.setAppActivity(pojoObject.get("appActivity").toString());
            pojoClass.setAppUsername(appDataDB.get("app_username").toString());
            pojoClass.setAppPassword(appDataDB.get("app_password").toString());
            pojoClass.setDeviceUsername(appDataDB.get("deviceusername").toString());
            pojoClass.setDevicePassword(appDataDB.get("devicepassword").toString());
            pojoClass.setServiceProviderid(appDataDB.get("service_provider_uname").toString());
            pojoClass.setAccoutName(appDataDB.get("accountname").toString());
            pojoClass.setNetworkName(appDataDB.get("networkname").toString());
            pojoClass.setNetworkPassword(appDataDB.get("networkpassword").toString());
            pojoClass.setServiceProviderPassword(appDataDB.get("service_provider_password").toString());
            pojoClass.setDsn(deviceDSN);
            pojoClass.setDeviceOS(deviceOS);
            pojoClass.setNavigationTabCount(pojoObject.get("navigationTabCount").toString());
            pojoClass.setWeblink(pojoObject.get("weblink").toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
	
}

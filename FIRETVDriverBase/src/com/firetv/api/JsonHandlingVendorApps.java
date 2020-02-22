package com.firetv.api;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.firetv.commonpojo.AppTestPojoVendorApps;

public class JsonHandlingVendorApps {
	
	static String filePath;
	static String appName;
	static String appUsername;
	static String appPassword;
	static String deviceUsername;
	static String devicePassword;
	static String serviceProviderid;
	static String serviceProviderPassword;
	public static Properties prop;
	
	public JsonHandlingVendorApps(String filePath, String appName, String appUsername, String appPassword, String deviceUsername, String devicePassword, String serviceProviderid, String serviceProviderPassword){
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
	 */
    public static void setJSON()
    {
        JSONParser jsonParser = new JSONParser();    
        try (FileReader reader = new FileReader(filePath))
        {
        	Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject pojoObject = (JSONObject) jsonObject.get(appName);
            FileInputStream ip = null;
    		String workSpaceLocation = System.getProperty("user.dir");
    		String workspacePath = workSpaceLocation.replace("FIRETVTests", "FIRETVDriverBase");
    			prop = new Properties();
    			ip = new FileInputStream(workspacePath + "/src/com/firetv/config/credentials.properties");
    			prop.load(ip);
            AppTestPojoVendorApps pojoClass = new AppTestPojoVendorApps();
            pojoClass.setAppPackage(pojoObject.get("appPackage").toString());
            pojoClass.setAppActivity(pojoObject.get("appActivity").toString());
            pojoClass.setAppPackage1(pojoObject.get("appPackage1").toString());
            pojoClass.setAppActivity1(pojoObject.get("appActivity1").toString());
            pojoClass.setAppPackage2(pojoObject.get("appPackage2").toString());
            pojoClass.setAppActivity2(pojoObject.get("appActivity2").toString());
            pojoClass.setAppPackage3(pojoObject.get("appPackage3").toString());
            pojoClass.setAppActivity3(pojoObject.get("appActivity3").toString());
            pojoClass.setAppPackage4(pojoObject.get("appPackage4").toString());
            pojoClass.setAppActivity4(pojoObject.get("appActivity4").toString());
            pojoClass.setAppPackage5(pojoObject.get("appPackage5").toString());
            pojoClass.setAppActivity5(pojoObject.get("appActivity5").toString());
            pojoClass.setAppPackage6(pojoObject.get("appPackage6").toString());
            pojoClass.setAppActivity6(pojoObject.get("appActivity6").toString());
            pojoClass.setAppPackage7(pojoObject.get("appPackage7").toString());
            pojoClass.setAppActivity7(pojoObject.get("appActivity7").toString());
            pojoClass.setAppPackage8(pojoObject.get("appPackage8").toString());
            pojoClass.setAppActivity8(pojoObject.get("appActivity8").toString());
            pojoClass.setAppUsername(appUsername);
            pojoClass.setAppPassword(appPassword);
            pojoClass.setDeviceUsername(deviceUsername);
            pojoClass.setDevicePassword(devicePassword);
            pojoClass.setServiceProviderid(serviceProviderid);
            pojoClass.setServiceProviderPassword(serviceProviderPassword);
            pojoClass.setNavigationTabCount(pojoObject.get("navigationTabCount").toString());
            pojoClass.setWeblink(pojoObject.get("weblink").toString());
            pojoClass.setWeblink1(pojoObject.get("weblink1").toString());
            pojoClass.setWeblink2(pojoObject.get("weblink2").toString());
            pojoClass.setWeblink3(pojoObject.get("weblink3").toString());
            pojoClass.setWeblink4(pojoObject.get("weblink4").toString());
            pojoClass.setWeblink5(pojoObject.get("weblink5").toString());
            pojoClass.setWeblink6(pojoObject.get("weblink6").toString());
            pojoClass.setWeblink7(pojoObject.get("weblink7").toString());
 

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    public static String getValueByJPath(JSONObject responsejson, String jpath){
    	
		Object obj = responsejson;
		for(String s : jpath.split("/")) 
			if(!s.isEmpty()) 
				if(!(s.contains("[") || s.contains("]")))
					obj = ((JSONObject) obj).get(s);
				else if(s.contains("[") || s.contains("]"))
					obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
		return obj.toString();
	}
	
}

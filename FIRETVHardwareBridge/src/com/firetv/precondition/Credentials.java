package com.firetv.precondition;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Parameters;

public class Credentials {
	static String aSIN;
	static String location =null;
	static int lastRowNumCred, lastRowNumDeviceDetails;
	static String appUserName;
	static String appPassword;
	static String deviceUserName;
	static String devicePassword;
	static String serviceProviderId;
	static String serviceProviderPswd=null;
	static String ASIN=null;
	static String systemUserName=null;
	static String marketPlaceCred=null;
	static String marketPlaceDevDet=null;
	static String accountName=null;
	static String deviceNetwork=null;
	static String deviceNetworkPswd=null;
	static XSSFWorkbook workbook;
	static String path=null;
	static String s;
	static int deviceCount = 0;
	static ArrayList<String> deviceList = new ArrayList<String>();
	static String dsn;
	static String deviceModel;
	public static String connectedDevicemdl;
	
	static String readPath(){
	String workSpaceLocation = System.getProperty("user.dir");
	String workspacePath = workSpaceLocation.replace("FIRETVTests", "FIRETVDriverBase");
	path = workspacePath + "/src/com/firetv/config/credentials.properties";
	return path;
	}

	//@BeforeTest
	//@Test
	@Parameters("ASIN")
	public void readCredentialsExcel(String testASIN) throws Exception {
		//aSIN=name;
		try {
			System.out.println("Checking and deleting if existing credentials are there in property file....");
			deletePropertyFile();
		} catch (Exception e) {
			System.out.println("Property file is clear, Proceding.......");
		}
		System.out.println("Reading Credentinal for " + testASIN+" ASIN");
		List<String> list = new ArrayList<String>();
		String OSName = null;
		OSName = System.getProperty("os.name");
		OSName = OSName.toLowerCase();
		systemUserName=System.getProperty("user.name");
		if (OSName.contains("windows"))
		{
			location="C:/Users/"+systemUserName+"/Documents/driver/AppDetails.xlsx";
		}

		if (OSName.contains("mac"))
		{
			location="/Users/"+systemUserName+"/Documents/driver/AppDetails.xlsx";
		}
		File file = new File(readPath());
		//file.createNewFile();
		
		Properties pr = new Properties();
		/* pr.setProperty("FirstName", "Dushyant");
		 pr.setProperty("LastName", "Sahu");*/

		
		try {
			lastRowNumCred = getRowNum(location,"AppDetails"); // getting the last row no present in excel sheet
			lastRowNumDeviceDetails=getRowNum(location,"DeviceDetails");
			pr.load(new FileReader(path));
		} catch (IOException e) {
			System.out.println("Unable to get last row num "+e);
			//throw new RuntimeException(e);
		}
		for(int i = 1; i <= lastRowNumCred; i++)
		{
			try {
				ASIN=getExcelData(location,"AppDetails",i,0);
				if(ASIN.contains(testASIN))
				{
					list.add(appUserName=getExcelData(location,"AppDetails",i,2));
					list.add(appPassword=getExcelData(location,"AppDetails",i,3));
					
					list.add(marketPlaceCred=getExcelData(location,"AppDetails",i,6));
					for(int j = 1; j <= lastRowNumCred; j++)
					{
						list.add(marketPlaceDevDet=getExcelData(location,"DeviceDetails",j,0));
						if(String.valueOf(marketPlaceCred).equals(String.valueOf(marketPlaceDevDet)))
						{
							list.add(accountName=getExcelData(location,"DeviceDetails",j,1));
							list.add(deviceUserName=getExcelData(location,"DeviceDetails",j,2));
							list.add(devicePassword=getExcelData(location,"DeviceDetails",j,3));	
							list.add(deviceNetwork=getExcelData(location,"DeviceDetails",j,4));
							list.add(deviceNetworkPswd=getExcelData(location,"DeviceDetails",j,5));
							break;
						}
						else
						{
							continue;
						}
					}
					list.add(serviceProviderId=getExcelData(location,"AppDetails",i,4));
					list.add(serviceProviderPswd=getExcelData(location,"AppDetails",i,5));
					break;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		list.add(dsn = getDeviceDSN());
		if(dsn.equalsIgnoreCase("false"))
		{
			System.out.println("No or more than 1 device connected : please make sure 1 device only should be connected to continue......");
		}
		list.add(deviceModel=getDeviceOS());
		System.out.println("appUserName : "+appUserName);
		System.out.println("appPassword : "+appPassword);
		System.out.println("accountName : "+accountName);
		System.out.println("deviceUserName : "+deviceUserName);
		System.out.println("devicePassword : "+devicePassword);
		System.out.println("deviceNetwork : "+deviceNetwork);
		System.out.println("deviceNetworkPswd : "+deviceNetworkPswd);
		System.out.println("serviceProviderId : "+serviceProviderId);
		System.out.println("serviceProviderPswd : "+serviceProviderPswd);
		System.out.println("connected device : "+dsn);
		System.out.println("connected device platform version "+deviceModel);
		
			pr.setProperty("AppUserName", appUserName);
			pr.setProperty("AppPassword", appPassword);
			pr.setProperty("AccountName", accountName);
			pr.setProperty("DeviceUserName", deviceUserName);
			pr.setProperty("DevicePassword", devicePassword);
			pr.setProperty("DeviceNetwork", deviceNetwork);
			pr.setProperty("DeviceNetworkPswd", deviceNetworkPswd);
			pr.setProperty("ServiceProviderId", serviceProviderId);
			pr.setProperty("ServiceProviderPswd", serviceProviderPswd);
			pr.setProperty("DSN", dsn);
			pr.setProperty("DeviceOS", deviceModel);
			FileOutputStream fis2 = new FileOutputStream(new File(path));
			pr.store(fis2, "Test Data");
			fis2.close();
			//readPropertyData();
		}
	
	public static void deletePropertyFile() throws FileNotFoundException, IOException
	{
		System.out.println("Deleting credential from property file.... ");
		Properties pr = new Properties();
		pr.load(new FileReader(readPath()));
        pr.remove("AppUserName");
        pr.remove("AppPassword");
        pr.remove("AccountName");
        pr.remove("DeviceUserName");
        pr.remove("DevicePassword");
        pr.remove("DeviceNetwork");
        pr.remove("DeviceNetworkPswd");
        pr.remove("ServiceProviderId");
        pr.remove("ServiceProviderPswd");
        pr.remove("DSN");
        pr.remove("DeviceOS");
        FileOutputStream fis3 = new FileOutputStream(path);
        pr.store(fis3, null);
	}
	
	private static int getRowNum(String xPath, String sName) throws IOException
	{
		try
		{
			FileInputStream fis = new FileInputStream(new File(xPath));
			workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheet(sName);
			return sheet.getLastRowNum();
		}
		catch (Exception e)
		{
			return 0;
		}
	}
	
	private static String getExcelData(String xPath, String sName, int row, int column) throws Exception
	{
		try
		{
			FileInputStream fis = new FileInputStream(new File(xPath));
			workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet(sName);

			Iterator itr = sheet.rowIterator();
			if (itr.hasNext())
			{
				XSSFCell value = sheet.getRow(row).getCell(column);
				String cellValue=value.toString();
				return cellValue;
			}
		}
		catch (Exception e)
		{
			return "";
		}
		return sName;
	}
	
	public static String getDeviceDSN() throws Exception
	{
		//Code to get Device DSN
		try {
	        Process process = Runtime.getRuntime().exec("adb devices");
	        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));  
	        String line = null;

	        Pattern pattern = Pattern.compile("^([a-zA-Z0-9.:\\-]+)(\\s+)(device)");
	        Matcher matcher;
	        
	        while ((line = in.readLine()) != null) {  
	            if (line.matches(pattern.pattern())) {
	                matcher = pattern.matcher(line);
	                if (matcher.find())
	                {
	                	s=matcher.group(1);
	                	deviceList.add(s); //this adds an element to the list
	                	//System.out.println("Connected Device DSN: "+s); //Prints contents of string variable 's' which in turn contains Model ID of device
	                	deviceCount++;
	                }
	            }
	        }  
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
        //System.out.println("Connected Device DSN: "+s); //Prints contents of string variable 's' which in turn contains Model ID of device
        //End of code to get Device DSN
		if (deviceCount == 1)
		{
			return deviceList.get(0);
		}
		else
		{
			return "false";
		}	
	}
	
	public static String getDeviceOS() throws Exception
	{
		String DSN;
		DSN = dsn;
		//Code to get Device OS
		if (DSN.equals("false"))
		{
			return "false";
		}
		else
		{
			Object deviceProperty = null;
	        try {
	               final Process process = Runtime.getRuntime().exec("adb -s "+DSN+" shell getprop"); //Execute adb command to get device properties
	               InputStream inStream = process.getInputStream();
	               final BufferedReader br= new BufferedReader(
	                             new InputStreamReader(inStream));
	               String line2;
	               while ((line2 = br.readLine()) != null) {
	                      deviceProperty = deviceProperty + line2 + "\n"; //Get dump of adb device property output to 'deviceProperty'
	               }
	               br.close();
	               try {
	                      process.waitFor();
	               }
	               catch (final InterruptedException e) {
	                      System.out.println(e.getMessage());
	               }
	        } catch (final Exception e) {
	               System.out.println(e.getMessage());
	        }
	        String s2;
	        if(!((String) deviceProperty).isEmpty())
	        {
	               s2 = String.valueOf(deviceProperty); //Copy contents of deviceProperty to a string variable 's'
	               // System.out.println("Value of s2: \n"+s2);
	               Pattern pattern = Pattern.compile("\\[ro.product.model\\]: \\[(.*?)\\]"); //Regular expression to get value of model id from device property list
	               Matcher matcher = pattern.matcher(s2);
	               while (matcher.find())
	               {
	                      s2=matcher.group(1); //Copy output of matched pattern from the above mentioned regular expression to the string variable 's'
	               }
	        }
	        else
	        {
	               s2 = "false";
	        }
	        connectedDevicemdl=s2;
	        //System.out.println("Connected Device Model: "+s2); //Prints contents of string variable 's' which in turn contains Model ID of device
	        if (s2.equals("AFTB") || s2.equals("AFTS") || s2.equals("AFTM") || s2.equals("AFTT") || s2.equals("AFTRS"))
	        {
	        	return "5.1.1";
	        }
	        else if (s2.equals("AFTN") || s2.equals("AFTA") || s2.equals("AFTMM") || s2.equals("AFTKMST12") || s2.equals("AFTJMST12") || s2.equals("AFTBAMR311") || s2.equals("AFTEAMR311"))
	        {
	        	return "7.1";
	        }
	        else
	        {
	        	return "Unsupported Device OS";
	        }
		}
        //End of code to get Device OS

	}
}

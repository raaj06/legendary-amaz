package com.firetv.db;

public class ImportData {

	
	/**
	 * This method is for internal test, this is not directly into framework
	 * @param args
	 * @throws Exception
	 */
	
	public static void main(String[] args)
			throws Exception {

		DataPull data = new DataPull();
		LoadSheetIntoDB lsib = new LoadSheetIntoDB();
		String filepath = null;
		String OSName = null;
		OSName = System.getProperty("os.name");
		OSName = OSName.toLowerCase();
		String systemUserName = System.getProperty("user.name");
		if (OSName.contains("windows")) {
			filepath = "C:/Users/" + systemUserName + "/Documents/driver/AppDetails.xlsx";
		}

		if (OSName.contains("mac")) {
			filepath = "/Users/" + systemUserName + "/Documents/driver/AppDetails.xlsx";
		}
		//data.getDBAppData("B004TC7EJK");
		lsib.populateDeviceData(filepath, "App");
		lsib.populateDeviceData(filepath, "Device");
	}

}
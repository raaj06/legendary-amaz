package com.firetv.precondition;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class ImportData {

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		try {
			
			LoadSheetIntoDB readData = new LoadSheetIntoDB();
			String filepath=null;
			String OSName = null;
			OSName = System.getProperty("os.name");
			OSName = OSName.toLowerCase();
			String systemUserName=System.getProperty("user.name");
			if (OSName.contains("windows"))
			{
				filepath="C:/Users/"+systemUserName+"/Documents/driver/AppDetails.xlsx";
			}

			if (OSName.contains("mac"))
			{
				filepath="/Users/"+systemUserName+"/Documents/driver/AppDetails.xlsx";
			}
			readData.populateData(filepath, "AppData");
			readData.populateData(filepath, "DeviceData");

			// TODO Auto-generated method stub
		} catch (Exception e) {
			System.out.println("Error : "+e);
		}
	}

}
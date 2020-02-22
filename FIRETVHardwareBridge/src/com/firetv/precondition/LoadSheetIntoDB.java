package com.firetv.precondition;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LoadSheetIntoDB {

	public String populateData(String inputfile, String table)
			throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
		XSSFWorkbook wb = null;
		try {

			// @SuppressWarnings("resource")
			wb = new XSSFWorkbook(new FileInputStream(inputfile));
			XSSFSheet sheet=null;
			if(table.contains("AppData"))
			{
				sheet = wb.getSheetAt(0);// Get the first sheet from the workbook
			}
			else
			{
				sheet = wb.getSheetAt(1);// Get the second sheet from the workbook
			}
			Row row = null;
			// Cell cell=null;

			// Iterate through each row from the first sheet

			Iterator<Row> rowIterator = sheet.iterator();
			Boolean parseHeader = false;

			ArrayList<String> headers = new ArrayList<String>();
			ArrayList<String> rowValues = null;

			while (rowIterator.hasNext()) {
				row = rowIterator.next(); // Pointer is on first row.

				if (parseHeader) {
					rowValues = new ArrayList<String>();
					// parseHeader = false;
				}

				Iterator<Cell> ce = row.cellIterator(); // Access to all the
				// cells of desired
				// header rows. (Header
				// Cell Values
				// equivalent to column
				// name)

				// int k=0;
				// int column =0;

				while (ce.hasNext()) {
					Cell cellValue = ce.next();

					if (!parseHeader) {
						headers.add(cellValue.getStringCellValue());
					} else {
						try {
							rowValues.add(cellValue.getStringCellValue());
						} catch (Exception e) {
							long value = (long) cellValue.getNumericCellValue();
							String strVal = Long.toString(value);
							rowValues.add(strVal);
						}
					}
				}

				if (!parseHeader) 
				{
					parseHeader = true;
				} 
				else 
				{
					String rowValuesString = "";

					for (String fieldValue : rowValues) 
					{
						rowValuesString += fieldValue + " ";
					}
					// System.out.println(rowValuesString);
					InsertRowInDB(headers, rowValues, table);
					System.out.println("");
				}
			}
		}

		catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException : "+e);
		}

		catch (IOException e) {
			System.out.println("IOException : "+e);
		}

		catch (Exception e) {
			System.out.println("Exception : "+e);
		}

		finally 
		{
			System.out.println("The work book has been closed successfully");
		}
		return "Sucess";
	}

	public void InsertRowInDB(ArrayList<String> headers, ArrayList<String> values, String table)
			throws SQLException, ClassNotFoundException {

		Class.forName("org.postgresql.Driver");
		System.out.println("connected");
		Connection conn = DriverManager.getConnection("jdbc:postgresql://ftv-db.c2p4jx8lycsn.us-east-2.rds.amazonaws.com:5432/FTVAutomation", "postgres", "d98aN7KT6GRVbmAWqn6Kgyh7h");

		String insertColumns = String.join(",", headers);
		StringBuffer preparedStatementClause = new StringBuffer();
		for (int i = 0; i < headers.size(); i++) {
			if (i < (headers.size())) {
				preparedStatementClause.append("?");

			}

			if (i < (headers.size() - 1)) {
				preparedStatementClause.append(",");
			}
		}

		System.out.println("****** Header " + insertColumns);
		System.out.println("****** Values " + preparedStatementClause);
		int count = 0;
		String SQL=null;
		if(table.contains("AppData"))
		{
			System.out.println("asin " + values.get(0));
			SQL = String.format("select count(*) from appcredentials where asin = '%s';", values.get(0));
		}
		else
		{
			System.out.println("marketplace " + values.get(0));
			SQL = String.format("select count(*) from devicedetails where marketplace = '%s';", values.get(0));
		}
		System.out.println(SQL);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		rs.next();
		count = rs.getInt(1);

		System.out.println("Count "+count);
		if (count == 0) {
			String query=null;
			if(table.contains("AppData"))
			{
				query = String.format("Insert into appcredentials(%s) values(%s)", insertColumns,
						preparedStatementClause);
			}
			else
			{
				query = String.format("Insert into devicedetails(%s) values(%s)", insertColumns,
						preparedStatementClause);
			}
			PreparedStatement ps = conn.prepareStatement(query);
			for (int i = 0; i < values.size(); i++)
			{
				ps.setString(i + 1, values.get(i));
			}
			System.out.println(ps);
			ps.executeUpdate();
			System.out.println("Values Inserted Successfully");
			conn.close();
		}
		else
		{
			if(table.contains("AppData"))
			{
				System.out.println("asin Already in DB");
			}
			else
			{
				System.out.println("marketplace Already in DB");
			}

			conn.close();
		}
	}
}
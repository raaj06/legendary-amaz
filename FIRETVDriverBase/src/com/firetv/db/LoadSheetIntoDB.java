package com.firetv.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.firetv.testbase.TestBase;

public class LoadSheetIntoDB {

	public String populateDeviceData(String inputfile, String tableName) {
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(new FileInputStream(inputfile));

			XSSFSheet sheet = null;
			if (tableName.contains("App")) {
				sheet = wb.getSheetAt(0);
			} else {
				sheet = wb.getSheetAt(1);
			}
			Row row = null;
			Iterator<Row> rowIterator = sheet.iterator();
			Boolean parseHeader = false;
			ArrayList<String> headers = new ArrayList<String>();
			ArrayList<String> rowValues = null;
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				if (parseHeader) {
					rowValues = new ArrayList<String>();
				}
				Iterator<Cell> ce = row.cellIterator();
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
				if (!parseHeader) {
					parseHeader = true;
				} else {
					String rowValuesString = "";
					for (String fieldValue : rowValues) {
						rowValuesString += fieldValue + " ";
					}
					InsertRowInDB(headers, rowValues, tableName);
					System.out.println("");
				}
			}
			return "Success";
		} catch (Exception e) {
			return null;
		}
	}

	public void InsertRowInDB(ArrayList<String> headers, ArrayList<String> values, String table)
			throws Exception {

		Connection conn = TestBase.getDBConnection();

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
		String SQL = null;
		if (table.contains("App")) {
			System.out.println("asin " + values.get(0));
			SQL = String.format("select count(*) from APPCredentials where asin = '%s';", values.get(0));
		} else {
			System.out.println("marketplace " + values.get(0));
			SQL = String.format("select count(*) from devicedetails where Marketplace = '%s';", values.get(0));
		}
		System.out.println(SQL);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		rs.next();
		count = rs.getInt(1);

		System.out.println("Count " + count);
		if (count == 0) {
			String query = null;
			if (table.contains("App")) {
				query = String.format("Insert into APPCredentials(%s) values(%s)", insertColumns,
						preparedStatementClause);
			} else {
				query = String.format("Insert into devicedetails(%s) values(%s)", insertColumns,
						preparedStatementClause);
			}
			PreparedStatement ps = conn.prepareStatement(query);
			for (int i = 0; i < values.size(); i++) {
				ps.setString(i + 1, values.get(i));
			}
			System.out.println(ps);
			ps.executeUpdate();
			System.out.println("Values Inserted Successfully");
			conn.close();
		} else {
			if (table.contains("App")) {
				System.out.println("asin Already in DB");
			} else {
				System.out.println("marketplace Already in DB");
			}

			conn.close();
		}
	}
}

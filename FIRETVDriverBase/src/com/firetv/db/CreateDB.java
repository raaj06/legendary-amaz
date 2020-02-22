package com.firetv.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.firetv.testbase.TestBase;

public class CreateDB {

	public static void main(String args[]) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = TestBase.getDBConnection();
			stmt = c.createStatement();
			String sql = "CREATE TABLE  devicedetails " + "(Marketplace VARCHAR(255)   NOT NULL,"
					+ " AccountName           VARCHAR(255)    NOT NULL, " + " DeviceUsername       VARCHAR(255)  ,    "
					+ " DevicePassword         VARCHAR(255)     , " + " NetworkName         VARCHAR(255) , "
					+ "NetworkPassword    VARCHAR(255))";
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
			
			stmt = c.createStatement();
			sql = "CREATE TABLE  appcredential_info"
					+"("
					+ "asin VARCHAR PRIMARY KEY     NOT NULL,"
					+ "appdetails jsonb not null default '{}'::jsonb"
					+")";
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}
}

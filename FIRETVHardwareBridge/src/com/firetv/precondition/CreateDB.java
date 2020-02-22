package com.firetv.precondition;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateDB {

	public static void main(String args[]) {

		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
					.getConnection("jdbc:postgresql://ftv-db.c2p4jx8lycsn.us-east-2.rds.amazonaws.com:5432/FTVAutomation",
							"postgres", "d98aN7KT6GRVbmAWqn6Kgyh7h");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "CREATE TABLE  APPCredentials " +
					"(ASIN VARCHAR PRIMARY KEY     NOT NULL," +
					" AppName           VARCHAR(255)    NOT NULL, " +
					" AppUserName       VARCHAR(255)  ,    " +
					" Password         VARCHAR(255)     , " +
					" ServiceId         VARCHAR(255) , " +
					"ProvidePassword    VARCHAR(255) ," +
					"Marketplace        VARCHAR(255) NOT NULL )";

			stmt.executeUpdate(sql);
			stmt.close();
			c.close();

			stmt = c.createStatement();
			sql = "CREATE TABLE  devicedetails " +
					"(marketplace VARCHAR PRIMARY KEY     NOT NULL," +
					" accountname           VARCHAR(255)    NOT NULL, " +
					" deviceusername       VARCHAR(255)  NOT NULL,    " +
					" devicepassword         VARCHAR(255)     NOT NULL, " +
					" networkname         VARCHAR(255) NOT NULL, " +
					"networkpassword        VARCHAR(255) NOT NULL )";

			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		}
		catch ( Exception e ) 
		{
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}
}



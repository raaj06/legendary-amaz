package com.firetv.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.firetv.testbase.TestBase;

public class DataPull {

	public JSONObject getDataFromDB(String ASIN) throws Exception {
		Connection conn = TestBase.getDBConnection();
		try {
			String sql = String.format("select appdetails from appcredential_info where asin = '%s';", ASIN);
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			JSONObject json = null;
			JSONParser parser = new JSONParser();
			json = (JSONObject) parser.parse(rs.getString(1));
			System.out.println("Json " + json);
			return json;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public JSONObject getDBAppData(String ASIN) throws Exception {
		Connection conn = TestBase.getDBConnection();
		int count = 0;
		JSONObject jsonObject;
		String getASINData = String.format("select count(*) from appcredential_info where asin = '%s';", ASIN);
		System.out.println(getASINData);
		Statement stmtDataDB = conn.createStatement();
		ResultSet rsData = stmtDataDB.executeQuery(getASINData);
		rsData.next();
		count = rsData.getInt(1);
		String SQL = String.format("select * from APPCredentials where asin = '%s';", ASIN);
		System.out.println(SQL);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		rs.next();
		System.out.println("rs " + rs.getString("marketplace"));
		String deviceDataSQL = String.format("select * from devicedetails where marketplace = '%s';",
				rs.getString("marketplace"));
		Statement deviceDataStatement = conn.createStatement();
		System.out.println(deviceDataSQL);
		ResultSet deviceDataRS = deviceDataStatement.executeQuery(deviceDataSQL);
		deviceDataRS.next();
		if (count == 0) {
			String json = "{ \"asin\":\"" + rs.getString("asin") + "\", \"appdetails\": { \"asintype\":\"" + rs.getString("asintype")
					+ "\",\"app_name\":\""+ rs.getString("appname") + "\", \"app_username\":\"" + rs.getString("appusername")
					+ "\",\"app_password\":\"" + rs.getString("password") + "\",\"service_provider_uname\":\""
					+ rs.getString("serviceid") + "\",\"service_provider_password\":\""
					+ rs.getString("providepassword") + "\",\"marketplace\":\"" + rs.getString("marketplace")
					+ "\" , \"accountname\":\"" + deviceDataRS.getString("accountname") + "\", \"deviceusername\":\""
					+ deviceDataRS.getString("deviceusername") + "\", \"devicepassword\":\""
					+ deviceDataRS.getString("devicepassword") + "\", \"networkname\":\""
					+ deviceDataRS.getString("networkname") + "\", \"networkpassword\":\""
					+ deviceDataRS.getString("networkpassword") + "\"} }";
			String sql = "INSERT INTO appcredential_info (asin, appdetails) VALUES (?, cast(? as json))";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, rs.getString("asin"));
			ps.setString(2, json);
			System.out.println(ps);
			ps.executeUpdate();
			System.out.println("Values Inserted Successfully");

		} else {
			System.out.println("ASIN Already in DB");
			conn.close();

		}
		DataPull dataPull = new DataPull();
		jsonObject = dataPull.getDataFromDB(rs.getString("asin"));
		conn.close();
		return jsonObject;
	}

}

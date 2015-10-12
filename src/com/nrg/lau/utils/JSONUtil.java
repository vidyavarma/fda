package com.nrg.lau.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONUtil {
	
	private static Logger log = Logger.getLogger(JSONUtil.class);
	
	public static JSONArray convert(ResultSet rs) throws Exception {
		
		JSONArray json = new JSONArray();
		ResultSetMetaData rsmd = rs.getMetaData();
		int numColumns = rsmd.getColumnCount();
		rs.setFetchSize(300);
		while (rs.next()) {
		    JSONObject obj = new JSONObject();
		    for (int i = 1; i < numColumns + 1; i++) {
		        String columnName = rsmd.getColumnName(i);		        
		        obj.put(columnName, rs.getString(columnName));
		    }
		    json.put(obj);
		}
		log.info("JSON Util length() -> " + json.length());
		
		try	{	
			if (rs != null) 	rs.close();							
		}catch (Exception e)	{}
	
		return json;
	}
	
	public static String getJSON(DataSource ds, String sql) throws Exception{
		
		Statement stmt	= null;
		Connection con	= null;
		ResultSet rs 	= null;
		String rtn		= "";
		
		try {
			con = ds.getConnection();
			stmt = con.createStatement();
			rs 	= stmt.executeQuery(sql);
			if(rs != null)	rtn = convert(rs).toString();
			
		}catch (Exception e) {
			log.error(e);
		} finally {			
			try
			{				
				if (rs != null) 	rs.close();
				if (stmt != null) 	stmt.close();
				if (con != null)	con.close();				
			}catch (Exception e)	{}
		}
		
		return rtn;
	}
}

package com.nrg.lau.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.nrg.lau.regulatory.beans.LauFetchedRecords;

public class JSONDataSplitUpdateSession {

	private static Logger log = Logger.getLogger(JSONDataSplitUpdateSession.class);
	
	public void convert(ResultSet rs, HttpServletRequest request, String sessionProperty) throws Exception {

		HttpSession session  = request.getSession();
		int batchSize = 300;
		
		//FetchedRecords 
		JSONArray json = new JSONArray();
		ResultSetMetaData rsmd = rs.getMetaData();
		int numColumns = rsmd.getColumnCount();
		rs.setFetchSize(batchSize);
		int cntr = 0;
		while (rs.next()) {
			
		    JSONObject obj = new JSONObject();
		    for (int i = 1; i < numColumns + 1; i++) {
		        String columnName = rsmd.getColumnName(i);		        
		        obj.put(columnName, rs.getString(columnName));
		    }
		    json.put(obj);
		    cntr++;
		    if (cntr == batchSize)
		    {
		    	cntr = 0;
				
		    	LauFetchedRecords fetchRecords = null;
		    	if(session.getAttribute(sessionProperty) != null) {
		    		fetchRecords = (LauFetchedRecords) session.getAttribute(sessionProperty);
		    		fetchRecords.setJason(json);
		    	} else {
		    		fetchRecords = new LauFetchedRecords();
		    		fetchRecords.setJason(json);
		    	}
		    	
		    	session.setAttribute(sessionProperty, fetchRecords);  
		    }
		}
		
		log.info("JSON Util length() -> " + json.length());
		
		try	{	
			if (rs != null) 	rs.close();							
		}catch (Exception e)	{}
	
	}	
}
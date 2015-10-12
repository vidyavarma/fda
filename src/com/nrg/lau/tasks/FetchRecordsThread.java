package com.nrg.lau.tasks;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.nrg.lau.regulatory.beans.LauFetchedRecords;

public class FetchRecordsThread extends Thread{

	private static Logger log = Logger.getLogger(FetchRecordsThread.class);

	private HttpServletRequest request;
	private String sessionProperty = "";
	private ResultSet rs = null;
	private Connection con = null;
	private HttpSession cursession;
	
	public HttpSession getCursession() {
		return cursession;
	}

	public void setCursession(HttpSession cursession) {
		this.cursession = cursession;
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getSessionProperty() {
		return sessionProperty;
	}

	public void setSessionProperty(String sessionProperty) {
		this.sessionProperty = sessionProperty;
	}

	@Override
	public void run() {

		LauFetchedRecords fetchRecords = null;

		try {
			log.info("$$$$ thread before session read");
			if (this.getCursession() != null)
			{
				log.info("REQUEST GOOD !!!!!!-----");
			}
			else
			{
				log.info("NULL REQUEST !!!!!-----");
			}
			log.info("$$$$ 1111111111111");
			if(getRs() != null && getSessionProperty() != null && this.getCursession() != null) {
				log.info("$$$$ thread before 1111 session read");
				HttpSession session  = this.getCursession();	
				log.info("$$$$ thread AFTER session read");
				boolean bLessThanFetchSize = true;
				int batchSize = 200;
				int arraySize = 0;

				log.info("Start thread with sessionProperty -> " + getSessionProperty());

				//FetchedRecords 
				JSONArray json = new JSONArray();
				ResultSetMetaData rsmd = getRs().getMetaData();
				int numColumns = rsmd.getColumnCount();
				log.info("Column count -> " + numColumns);
				getRs().setFetchSize(batchSize);
				int cntr = 1;


				while (getRs().next()) {

					JSONObject obj = new JSONObject();
					bLessThanFetchSize = true;

					for (int i = 1; i < numColumns + 1; i++) {
						String columnName = rsmd.getColumnName(i);		        
						obj.put(columnName, getRs().getString(columnName));
					}

					json.put(obj);

					if (cntr == batchSize)	{

						bLessThanFetchSize = false;
						log.info("Batch #: " + arraySize + " , total records so far #: " + json.length());				    	
						arraySize++;
						cntr = 0;

						try {
							if(session.getAttribute(getSessionProperty()) != null) {
								fetchRecords = (LauFetchedRecords) session.getAttribute(getSessionProperty());
								fetchRecords.setJason(json);
							} else {
								throw new Exception(getSessionProperty() + " not found in session, stopping execution");
							}

							session.setAttribute(getSessionProperty(), fetchRecords);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							throw new Exception(getSessionProperty() + " not found in session, stopping execution");
						}
					}
					cntr++;
				}

				if (bLessThanFetchSize)
				{
					log.info("Outside BATCH #: " + arraySize);					
					try {
						if(session.getAttribute(getSessionProperty()) != null) {
							fetchRecords = (LauFetchedRecords) session.getAttribute(getSessionProperty());
							fetchRecords.setJason(json);
						} else {
							throw new Exception(getSessionProperty() + " not found in session, stopping execution");
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						throw new Exception(getSessionProperty() + " not found in session, stopping execution");
					}

				}

				fetchRecords.setFetchComplete(true);
				session.setAttribute(getSessionProperty(), fetchRecords); 

			} else {
				log.info("Property not set - ResultSet, HttpServletRequest, sessionProperty");
			}

		}catch (Exception e) {

			fetchRecords =  new LauFetchedRecords();
			fetchRecords.setFetchComplete(true);
			fetchRecords.setErrorStatus(true);
			fetchRecords.setErrorMsg(e.getMessage());
			try {
				getRequest().getSession().setAttribute(getSessionProperty(), fetchRecords);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				log.info("Session not found , stopping execution");
			}
			log.error(e);
		} catch(OutOfMemoryError oe) {
			fetchRecords =  new LauFetchedRecords();
			fetchRecords.setFetchComplete(true);
			fetchRecords.setErrorStatus(true);
			fetchRecords.setErrorMsg(oe.getMessage());
			try {
				getRequest().getSession().setAttribute(getSessionProperty(), fetchRecords);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.info("Session not found , stopping execution");
			}
			log.error(oe);
		} finally {
			try	{	
				if (getRs() != null) 	getRs().close();		
			//	if (getCon() != null) 	getCon().close();	
			}catch (Exception e)	{}
		}
	}

}

package com.nrg.lau.regulatory.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.regulatory.beans.LauFetchedRecords;
import com.nrg.lau.security.AppContext;

@Controller
@Scope("session")
public class FetchRecordsController {

	private static Logger log = Logger.getLogger(FetchRecordsController.class);
	
	
	@Autowired
	@Qualifier("getSharedConnection")
	private com.nrg.lau.dao.SharedConnectionDAO getSharedConnection;

	@RequestMapping(value = { "/getFetchedRecords" })
	public void fetchRecords(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String sessionProperty = request.getParameter("sessionProperty");
		String startIndex = request.getParameter("startIndex");
		final int FETCH_SIZE = 2000;
		int fetchSize = FETCH_SIZE;
		log.info("sessionProperty -> " + sessionProperty);
		log.info("startIndex -> " + startIndex);

		int index = 0;
		if (startIndex != null) {
			index = Integer.valueOf(startIndex);
		}

		HttpSession session = request.getSession();
		JSONArray rtn = new JSONArray();
		LauFetchedRecords fetchRecords = null;

		if (session.getAttribute(sessionProperty) != null) {

			fetchRecords = new LauFetchedRecords();
			fetchRecords = (LauFetchedRecords) session
					.getAttribute(sessionProperty);

			try {

				JSONArray json = fetchRecords.getJason();

				// if no records, return FETCH_RECORDS = 0
				if (json == null || json.length() == 0) {
					JSONObject obj = new JSONObject();
					obj.append("FETCH_RECORDS", "0");
					rtn.put(obj);
				}

				if (json != null) {

					log.info("json.length() -> " + json.length());
					// if index sent from flex = length of records, return
					// FETCH_RECORDS = 0
					// this will make sure duplicate records are not sent back
					if (index == json.length()) {
						JSONObject obj = new JSONObject();
						obj.append("FETCH_RECORDS", "0");
						rtn.put(obj);
					} else {
						if (json.length() < index + FETCH_SIZE) {
							fetchSize = json.length();
						} else {
							fetchSize = index + FETCH_SIZE;
						}
						for (int i = index; i < fetchSize; i++) {
							rtn.put(json.getJSONObject(i));
						}
					}
				}

			} catch (JSONException e) {
				log.error(e);
			}
		}

		if (fetchRecords != null && fetchRecords.isFetchComplete()
				&& fetchSize == fetchRecords.getJason().length()) {
			session.removeAttribute(sessionProperty);
		}

		if (fetchRecords != null && fetchRecords.isErrorStatus()) {
			session.removeAttribute(sessionProperty);
			try {
				rtn = new JSONArray();
				JSONObject obj = new JSONObject();
				obj.append("ERROR_MSG", fetchRecords.getErrorMsg());
				rtn.put(obj);
			} catch (Exception e) {
				log.error(e);
			}

		}

		log.info("Length of returned JSONArray() -> " + rtn.length());
		response.setContentType("application/json");
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();
		pw.print(rtn);
		pw.flush();
		pw.close();
		log.info("after flush");

	}

	@RequestMapping(value = { "/stopFetchedRecords" })
	public void stopFetchRecords(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		log.info("stopFetchedRecords............");
		String sessionProperty = request.getParameter("sessionProperty");
		HttpSession session = request.getSession();
		if (session.getAttribute(sessionProperty) != null) {
			session.removeAttribute(sessionProperty);
		}

	}

	// to keep the connection used for creating the charts alive
	@RequestMapping(value = { "/pingChartConnection" })
	public void pingChartConnection(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		log.info("Ping Started.............");
		//ApplicationContext ctx = AppContext.getApplicationContext();
		//com.nrg.lau.dao.SharedConnectionDAO shConnection = (com.nrg.lau.dao.SharedConnectionDAO) ctx
		//		.getBean("getSharedConnection");
		getSharedConnection.pingConnection();
		log.info("Ping Ended.............");

	}

	// call thos service as part of logout
	@RequestMapping(value = { "/closeChartConnection" })
	public void closeChartConnection(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		log.info("Close connection Started.............");
		//ApplicationContext ctx = AppContext.getApplicationContext();
		//com.nrg.lau.dao.SharedConnectionDAO shConnection = (com.nrg.lau.dao.SharedConnectionDAO) ctx
		//		.getBean("getSharedConnection");
		getSharedConnection.closeConnection();
		log.info("Close connection Ended.............");

	}
}
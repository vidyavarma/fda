package com.nrg.lau.regulatory.render;

import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.regulatory.beans.LauFetchedRecords;
import com.nrg.lau.security.AppContext;
import com.nrg.lau.tasks.FetchRecordsThread;
import com.nrg.lau.utils.JSONUtil;

public class GetChartFilterChunkFetch extends JSONUtil implements View {

	private static Logger log = Logger
			.getLogger(GetChartFilterChunkFetch.class);

	@Override
	public String getContentType() {
		return "text/xml";
	}

	@Override
	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - GetChartFilterChunkFetch render()");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);

		PrintWriter pw = response.getWriter();

		pw.write(getData(request).toString());
		pw.flush();
		pw.close();

		log.info("EXIT - GetChartFilterChunkFetch render()");
	}


	private StringBuffer getData(HttpServletRequest request) throws Exception {

		StringBuffer tmp = new StringBuffer();
		ResultSet rs = null;

		try {
			String queryId = "";
			String userId = CommonDAO.getUSERID(request);
			String groupId = CommonDAO.getUSERGROUPID(request);
			if (request.getParameter("queryID") != null) {
				queryId = request.getParameter("queryID");
			}
			ApplicationContext ctx 	= 	AppContext.getApplicationContext();
			com.nrg.lau.dao.SharedConnectionDAO shConnection  	= 	(com.nrg.lau.dao.SharedConnectionDAO)ctx.getBean("getSharedConnection");
			Connection con 		= shConnection.getCon();
			String sql = "{call ? := LAU_LIST.executeQuery(?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.setString(2, queryId);
			stmt.setString(3, userId);
			stmt.setString(4, groupId);

			log.info("userId" + userId + " ,groupId:" + groupId + ", queryId"
					+ queryId);
			stmt.execute();

			Calendar date = new GregorianCalendar();
			String sessionProperty = "FETCHEDRECORDS"
					+ date.getTime().getTime();
			try {

				rs = (ResultSet) stmt.getObject(1);
				log.info("START json -----");
				request.getSession().setAttribute(sessionProperty,
						new LauFetchedRecords());
				// FetchRecordsThread fetchRecords =
				// (FetchRecordsThread)ctx.getBean("fetchRecordsThread");

				FetchRecordsThread fetchRecords = new FetchRecordsThread();
				fetchRecords.setRequest(request);
				fetchRecords.setCursession(request.getSession());
				fetchRecords.setRs(rs);
				fetchRecords.setCon(con);
				fetchRecords.setSessionProperty(sessionProperty);
				if (fetchRecords.getCursession() != null) {
					log.info("SESSION GOOD @@@@@-----");
				} else {
					log.info("NULL SESSION @@@@@ -----");
				}
				log.info("STARTING THREAD -----");
				fetchRecords.start();

				tmp = new StringBuffer();
				tmp.append("[{\"SESSION_PROPERTY\":\"" + sessionProperty
						+ "\"}]");

				log.info("END json -----");
			//	log.info(stmt.getObject(4));
			//	log.info(stmt.getObject(5));
				log.info(tmp);
			} catch (Exception e) {
				log.info("EXCEPTION @@@@@ -----");
				LauFetchedRecords fetchRecords = new LauFetchedRecords();
				fetchRecords.setFetchComplete(true);
				fetchRecords.setErrorStatus(true);
				fetchRecords.setErrorMsg(e.getMessage());
				request.getSession()
						.setAttribute(sessionProperty, fetchRecords);
			}

			log.info(" END  LAU_QueryBuilder.ExecuteQuery------------------------------------------------");
		} catch (Exception e) {
			log.error(e);
			throw new Exception(e);
		}
		return tmp;
	}
}

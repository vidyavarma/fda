package com.nrg.lau.render;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.io.*;
import java.net.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import com.nrg.lau.commons.Constants;
import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.dao.CommonDAO;

public class GetReportListAction implements View {

	private static Logger log = Logger.getLogger(GetReportListAction.class);
	private DataSource ds;
	private String repstatus = "";
	private String startdate = "";
	private String enddate = "";
	private String userId	= "";
	private String groupId	= "";
	private String usergroup = "";
	private String product = "";
	private String country = "";
	private String specialattn = "";
	private String event = "";
	private String status ="";
	private String reportId ="";
	private String promoteCaseId ="";
	
	private String assignedto = "";
	private String activitydate = "";
	private String activityreason ="";
	private String activitySearch = "";
	
	private String procodeSearch = "";
	private String manufactSearch = "";
	private String reviewSearch = "";
	
	
	
	
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - GetReportListAction render()");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);

		userId = CommonDAO.getUSERID(request);
		groupId = CommonDAO.getUSERGROUPID(request);

		PrintWriter pw = response.getWriter();
		if (Constants.DEBUG)
			pw.write(readData("/getList.do").toString());
		else
			{
			if( (request.getParameter("repstatus") != null))
				repstatus =request.getParameter("repstatus");
			if( (request.getParameter("startdate") != null))
				startdate =request.getParameter("startdate");
			if( (request.getParameter("enddate") != null))
				enddate=request.getParameter("enddate");	
			if( (request.getParameter("usergroup") != null))
				usergroup=request.getParameter("usergroup");	
			if( (request.getParameter("product") != null))
				product=request.getParameter("product");			
			if( (request.getParameter("country") != null))
				country=request.getParameter("country");					
			if( (request.getParameter("specialattn") != null))
				specialattn=request.getParameter("specialattn");
			if( (request.getParameter("event") != null))
				event=request.getParameter("event");
			if( (request.getParameter("status") != null))
				status=request.getParameter("status");
			if( (request.getParameter("reportId") != null))
				reportId=request.getParameter("reportId");
			if( (request.getParameter("promoteCaseId") != null))
				promoteCaseId=request.getParameter("promoteCaseId");			
			
			if( (request.getParameter("assignedto") != null))
				assignedto=request.getParameter("assignedto");
			if( (request.getParameter("activitydate") != null))
				activitydate=request.getParameter("activitydate");
			if( (request.getParameter("activityreason") != null))
				activityreason=request.getParameter("activityreason");
			if( (request.getParameter("activitySearch") != null))
				activitySearch=request.getParameter("activitySearch");
			
			if( (request.getParameter("procodeSearch") != null))
				procodeSearch=request.getParameter("procodeSearch");
			if( (request.getParameter("manufactSearch") != null))
				manufactSearch=request.getParameter("manufactSearch");
			if( (request.getParameter("reviewSearch") != null))
				reviewSearch=request.getParameter("reviewSearch");


			// S.Abraham 12/12/12 if user groupd is passed then use that else use groupcontext group id
			if ( usergroup.length() > 0)
				groupId = usergroup;
			log.info("repstatus:"+repstatus+" reportId:"+ reportId+" promoteCaseId:"+ promoteCaseId+" startdate:"+ startdate+ " enddate:"+enddate+" ,product"
					+ product + ", country " +country+ " usergroup "+groupId+ " specialattn"+specialattn+ " event"+event+",status"+status+
					", assignedto "+assignedto+" ,activitydate "+activitydate + ", activityreason "+activityreason+", activitySearch "+activitySearch 
					+", procodeSearch"+procodeSearch+ ", manufactSearch"+manufactSearch +", reviewSearch"+reviewSearch				
					
					);
			pw.write(getData().toString());	
			
			}
		log.info("repstatus:"+repstatus+" reportId:"+ reportId+" promoteCaseId:"+ promoteCaseId+" startdate:"+ startdate+ " enddate:"+enddate);
		pw.flush();
		pw.close();

		log.info("EXIT - HomeListView render()");
	}
	public void setDataSource(DataSource dataSource) {
		new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("Rep listt -Initialize db template()");
	}
	private StringBuffer readData(String fileName) throws IOException {

		StringBuffer sb = new StringBuffer();
		URL fileURL = GetReportListAction.class.getResource(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				fileURL.openStream()));
		String theBuf = new String();

		while ((theBuf = reader.readLine()) != null) {
			sb.append(theBuf + "\n");
		}

		reader.close();
		log.info("GetReportListAction:"+sb);
		return sb;
		// return sb.toString();
	}
	private StringBuffer getData() {
		
		StringBuffer tmp 	= new StringBuffer();
		Connection con		= null;

		try {
			
			con 		= ds.getConnection();
			String sql 	= "{call ? := LAU_UTL.getFullReportList(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			stmt.setString(2, userId);
			stmt.setString(3, repstatus);
			stmt.setString(4, startdate);
			stmt.setString(5, enddate);
			stmt.setString(6, product);
			stmt.setString(7, country);
			stmt.setString(8,groupId);
			stmt.setString(9, specialattn);
			stmt.setString(10, event);
			stmt.setString(11, status);
			
			stmt.setString(12, assignedto);
			stmt.setString(13, activitydate);
			stmt.setString(14, activityreason);
			stmt.setString(15, activitySearch);
			stmt.setString(16, reportId);
			stmt.setString(17, promoteCaseId);
			
			stmt.setString(18, procodeSearch);
			stmt.setString(19, manufactSearch);
			stmt.setString(20, reviewSearch);
			log.info(sql);
			stmt.execute();
			Clob clob = stmt.getClob(1);
			if (clob != null) {
				Reader reader = clob.getCharacterStream();
				CharArrayWriter writer = new CharArrayWriter();
				int i = -1;
				while ((i = reader.read()) != -1) {
					writer.write(i);
				}
				tmp.append(new String(writer.toCharArray()));
			}

			stmt.close();
			con.close();

		} catch (SQLException e) {
			log.error(e, e);			
		} catch (Exception e) {
			log.error(e, e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				log.error(e, e);
			}
		}
		//log.info("GetReportListAction:"+tmp);
		return tmp;
	}
}

package com.nrg.lau.render;

import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.XMLException;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.sharepoint.PromoteService;

public class SetPromoteAction implements View {

	private static Logger log = Logger.getLogger(SetPromoteAction.class);
	private DataSource ds;
//	private DataSource primods;
	private SimpleJdbcTemplate template;
	private String status = "";
	private String userId	= "";
	private java.sql.Timestamp dt  = null;
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long reportId = 0;

		log.info("ENTER - Promote Report render()");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();
		// /////////////

		if (request.getParameter("reportId") != null) {
			log.info("report id - :" + request.getParameter("reportId"));
			reportId = Long.valueOf((String) request.getParameter("reportId"));
			userId = CommonDAO.getUSERID(request);
			log.info("userId - :" + userId);
			dt = CommonDAO.getTimestamp(template);
			getData(reportId,request);
		} else {
			status = XMLException.xmlError(new Exception(),
					"Report Id not available!");
		}
		pw.write(status);
		pw.flush();
		pw.close();
		// //////////////
		log.info("EXIT - Promote Report render()");
	}

	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
		log.info("Promote Report list -Initialize db template()");
	}
	public void setPrimoDataSource(DataSource dataSource) {
		template = new SimpleJdbcTemplate(dataSource);
		//this.primods = dataSource;
		log.info("Promote Report list -Initialize primo db template()");
	}

	private void getData(long reportId,HttpServletRequest request) {
		Connection con = null;

		try {
			// 03/25/2013 S.Abraham Add a record in activity log to show promote has started.
			updateActivity(reportId, request, "PROMOTE STARTED");
			con = ds.getConnection();
			log.info("before auto commit status:"+con.getAutoCommit());
			con.setAutoCommit(false);
			log.info("after auto commit status:"+con.getAutoCommit());
			String sql = "{call ? := aers_promotion.PromoteReport(?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.NUMBER);
			stmt.setLong(2, reportId);
			stmt.setString(3,userId);
			stmt.registerOutParameter(4, OracleTypes.VARCHAR);
			log.info(sql);
			
			stmt.execute();
			log.info("Promote after execute..:");
			int update = stmt.getInt(1);
			String returnMsg = stmt.getString(4);
			log.info("Promote STATUS:"+update);
			stmt.close();
			
			if (update == 1)
			{
				status = XMLException.status("Promote Report Successful!");
				// check in the report
				int id = this.template.update("UPDATE LAU_REPORTS SET REPORT_RESERVED_DATE = null,REPORT_RESERVED_BY = null," +
						"UPDATE_USER_ID=?, UPDATE_TIMESTAMP=? WHERE  REPORT_ID = ?",userId,dt,reportId);
				log.info("SetCheckInAction after promote update() ID -> " + id);
				con.commit();
				con.close();
				//int intInsStatus = CommonDAO.insertActivityDetails(template, null, reportId, "PROMOTE", "", "", request,userId,dt);
				updateActivity(reportId, request, "PROMOTE");	
				
				try {
					boolean rtnStatus = PromoteService.promoteReport(returnMsg) ;
					log.info("rtnStatus " + rtnStatus + ","+returnMsg );
					if(rtnStatus != true) {
						status = XMLException.xmlError(new Exception("Sharepoint Returned Error"),"Report promoted, but file upload to Sharepoint failed.");
					}
				}catch (Exception e) {
					status = XMLException.xmlError(e, "Report promoted, but file upload to Sharepoint failed.");
				}	

			}
			else
			{
				status = XMLException.xmlError(new Exception(),"Promote Report failed! " + returnMsg);
				// 03/25/2013 S.Abraham Add a record in activity log to show promote has failed.
				updateActivity(reportId, request, "PROMOTE FAILED");	
				con.commit();
				con.close();
			}
			
		} catch (SQLException e) {
			log.error(e, e);
			status = XMLException.xmlError(e, "Promote Report failed!");
			// 03/25/2013 S.Abraham Add a record in activity log to show promote has failed.
			updateActivity(reportId, request, "PROMOTE FAILED");	
		} catch (Exception e) {
			log.error(e, e);
			status = XMLException.xmlError(e, "Promote Report failed!");
			// 03/25/2013 S.Abraham Add a record in activity log to show promote has failed.
			updateActivity(reportId, request, "PROMOTE FAILED");	
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				log.error(e, e);
				status = XMLException.xmlError(e, "Promote Report failed!");
			}
		}
	}
	// 03/25/2013 S.Abraham Add a record in activity log to show promote has started.
	private void updateActivity(long reportId, HttpServletRequest request, String status) {
		log.info("updateActivity - Primo status -> " + status);
		int intInsStatus = CommonDAO.insertActivityDetails(template, null, reportId,  status, "", "", request,userId,dt);
		log.info("updateActivity - activity log creation status: " + intInsStatus);
	}
}

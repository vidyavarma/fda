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

public class GetReportCurrentStatusAction implements View {

	private static Logger log = Logger.getLogger(GetReportCurrentStatusAction.class);
	private DataSource ds;
	private String status = "";
	private String userId	= "";

	
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long reportId = 0;

		log.info("ENTER - getCurrentStatus Report render()");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();
		// /////////////

		if (request.getParameter("reportId") != null) {
			log.info("report id - :" + request.getParameter("reportId"));
			reportId = Long.valueOf((String) request.getParameter("reportId"));
			userId = CommonDAO.getUSERID(request);
			getData(reportId,request);
		} else {
			status = XMLException.xmlError(new Exception(),
					"Report Id not available!");
		}
		pw.write(status);
		pw.flush();
		pw.close();
		// //////////////
		log.info("EXIT - getCurrentStatus Report render()");
	}

	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
		log.info("getCurrentStatus Report list -Initialize db template()");
	}
	

	private void getData(long reportId,HttpServletRequest request) {
		Connection con = null;

		try {

			con = ds.getConnection();
			String sql = "{call ? :=  lau_utl.CanStillDo(?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.VARCHAR);
			stmt.setLong(2, reportId);
			stmt.setString(3, userId);
			log.info(sql);
			
			stmt.execute();
			log.info("getCurrentStatus after execute..:");
			String repstatus = stmt.getString(1);
			log.info("getCurrentStatus STATUS:"+repstatus);
			stmt.close();
			
			if (repstatus.equals("Y"))
			{
				status = XMLException.status("Report status not changed!");
				con.close();
			}
			else
			{
				status = XMLException.xmlError(new Exception(),"Report status Changed!");
				con.close();
			}
			
		} catch (SQLException e) {
			log.error(e, e);
			status = XMLException.xmlError(e, "getCurrentStatus Report failed!");
		} catch (Exception e) {
			log.error(e, e);
			status = XMLException.xmlError(e, "getCurrentStatus Report failed!");
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				log.error(e, e);
				status = XMLException.xmlError(e, "getCurrentStatus Report failed!");
			}
		}
	}
}

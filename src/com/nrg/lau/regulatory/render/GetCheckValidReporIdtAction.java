package com.nrg.lau.regulatory.render;

import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.utils.JSONUtil;

public class GetCheckValidReporIdtAction extends AbstractController {

	private static Logger log = Logger
			.getLogger(GetCheckValidReporIdtAction.class);
	private DataSource ds;

	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String reportName = request.getParameter("reportName");

		String userId = CommonDAO.getUSERID(request);
		String groupId = CommonDAO.getUSERGROUPID(request);
		log.info("reportName -> " + reportName);
		if (reportName != null && !reportName.isEmpty()) {

			String sql = "select REPORT_ID from LAU_REPORTS where LAU_REPORT_ID = '"
					+ reportName + "'";
			String reportID = "";
			try {
				reportID = new SimpleJdbcTemplate(ds).queryForObject(sql,
						String.class);
				;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.info("Invalid LAU REPORT ID  -> " + reportName);
			}
			if (reportID.length() > 0) {

				log.info("reportID-> " + reportID);
				String outStr = getData(userId, groupId, reportID).toString();
				if (outStr == null || outStr.length() == 0) {
					response.setContentType(Constants.CONTENT_TYPE);
					response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
					PrintWriter pw = response.getWriter();
					pw.write(XMLException
							.xmlError(new NullPointerException(),
									"User doesn't have the permission to view this report."));
					pw.flush();
					pw.close();
				}

				else {
					PrintWriter pw = response.getWriter();
					pw.write(outStr);
					pw.flush();
					pw.close();
				}
			} else {
				response.setContentType(Constants.CONTENT_TYPE);
				response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
				PrintWriter pw = response.getWriter();
				pw.write(XMLException.xmlError(new NullPointerException(),
						"Report Id not found"));
				pw.flush();
				pw.close();
			}

		}

		return null;
	}

	private StringBuffer getData(String userId, String groupId, String reportID) {

		StringBuffer tmp = new StringBuffer();
		Connection con = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			String sql = "{call ? := LAU_UTL.getReport(?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.setString(2, reportID);
			stmt.setString(3, userId);
			stmt.setString(4, groupId);

			log.info("userId" + userId + " ,groupId:" + groupId);
			stmt.execute();
			rs = (ResultSet) stmt.getObject(1);
			tmp.append(JSONUtil.convert(rs).toString());

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
		// log.info(tmp);
		return tmp;
	}
}

/*
 * import org.springframework.web.servlet.view.RedirectView;

 * request.setAttribute("reportid", reportID); RedirectView view = new
 * RedirectView("getReport.do"); ModelAndView model = new ModelAndView(view);
 * model.addObject("reportid", reportID); return model;
 */
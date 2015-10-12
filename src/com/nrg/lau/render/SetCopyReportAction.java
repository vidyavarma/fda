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
import com.nrg.lau.commons.XMLException;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.dao.CommonDAO;

public class SetCopyReportAction implements View {

	private static Logger log = Logger.getLogger(GetHomeReportAction.class);
	private DataSource ds;
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
		long numCopies = 0;
		userId = CommonDAO.getUSERID(request);
		dt = CommonDAO.getTimestamp(template);
		log.info("ENTER - Copy Report render()");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();
		// /////////////

		if ((request.getParameter("reportId")) != null
				&& (request.getParameter("numcopies") != null)) {
			log.info("report id - :" + request.getParameter("reportId")
					+ "num copies- :" + request.getParameter("numcopies"));
			reportId = Long.valueOf((String) request.getParameter("reportId"));
			numCopies = Long.valueOf((String) request.getParameter("numcopies"));
			
			getData(reportId,numCopies,request);
		} else {
			status = XMLException.xmlError(new Exception(),
					"Report Id not available!");
		}
		pw.write(status);
		pw.flush();
		pw.close();
		// //////////////
		log.info("EXIT - Copy Report render()");
	}

	public void setDataSource(DataSource dataSource) {
		new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		this.template = new SimpleJdbcTemplate(dataSource);
		log.info("Copy Report list -Initialize db template()");
	}

	private void getData(long reportId,long numCopies, HttpServletRequest request) {
		Connection con = null;

		try {

			con = ds.getConnection();
			String sql = "{call ? := LAU_COPY_REPORTS(?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.NUMBER);
			stmt.setLong(2, reportId);
			stmt.setLong(3, numCopies);
			stmt.setString(4,userId);
			log.info(sql+"reportId:"+reportId+"numCopies:"+numCopies+"USERID:"+userId);
			log.info(stmt);
			stmt.execute();
			int update = stmt.getInt(1);

			stmt.close();

			if (update == 1)
			{
				int intInsStatus = CommonDAO.insertActivityDetails(null, ds, reportId, "COPY", "", "", request,userId,dt);
				status = XMLException.status("Report has been copied Successfuly!!");
				con.commit();
				log.info("copy report commited");
			}
			else
				status = XMLException.xmlError(new Exception(),"Copy report failed!");
			con.close();

		} catch (SQLException e) {
			log.error(e, e);
			status = XMLException.xmlError(e, "Copy Report failed!");
		} catch (Exception e) {
			log.error(e, e);
			status = XMLException.xmlError(e, "Copy Report failed!");
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				log.error(e, e);
				status = XMLException.xmlError(e, "Copy Report failed!");
			}
		}
	}
}

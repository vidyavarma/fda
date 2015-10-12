package com.nrg.lau.render;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.dao.CommonDAO;

@SuppressWarnings("unused")
public class CheckPromoteErrorsAction implements View {
	
	private static Logger log = Logger.getLogger(GetHomeReportAction.class);
	private SimpleJdbcTemplate template;
	private DataSource ds;	
	private String userId	= "";
	
	
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String tableName = "" ,keyName = "" ,orderby ="", extraFilter="",reportId = "";
		

		if(request.getParameter("reportid") != null)	{
			reportId = request.getParameter("reportid");
		}
		userId = CommonDAO.getUSERID(request);
		log.info("ENTER - Check Promote Errors render()");
		log.info("Check Promote Errors render() - tableName,keyName,reportId -->  "
						+ reportId);

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();
		pw.write(getData(reportId));
		pw.flush();
		pw.close();

		log.info("EXIT - Check Promote Errors render()");
	}

	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("Check Promote Errors - Initialize db template()");
	}

	private String getData(String reportId) {
		
		StringBuffer tmp 	= new StringBuffer();
		Connection con 		= null;
		String  str ="";

		try {
			
			con 		= ds.getConnection();
			String sql 	= "{call ? := lau_promote_constraints.checkReport(?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.VARCHAR);
			stmt.setString(2, reportId);
			stmt.setString(3, userId);
			log.info(sql);
			stmt.execute();
			str = stmt.getString(1);
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
		return str;
	}
}

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
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.dao.CommonDAO;

@SuppressWarnings("unused")
public class GetReportSummaryAction implements View {
	
	private static Logger log = Logger.getLogger(GetReportSummaryAction.class);
	private SimpleJdbcTemplate template;
	private DataSource ds;	
	private String status = "";
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String tableName = "" ,keyName = "" ,orderby ="", extraFilter="";
		long reportId = 0;

		if (request.getParameter("reportId") != null) {
			log.info("report id - :" + request.getParameter("reportId"));
			reportId = Long.valueOf((String) request.getParameter("reportId"));
				log.info("ENTER - GetReportSummaryAction render()");
			log.info("GetReportSummaryAction () - tableName,keyName,reportId -->  "
							+ reportId);

			response.setContentType(Constants.CONTENT_TYPE);
			response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
			PrintWriter pw = response.getWriter();
			pw.write(getData(reportId).toString());
			pw.flush();
			pw.close();
		} else {
			status = XMLException.xmlError(new Exception(),
					"Report Id not available!");
		}
		
		

		log.info("EXIT - GetReportSummaryAction()");
	}

	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("GetReportSummaryAction - Initialize db template()");
	}
	private StringBuffer getData(long reportId) {
		
		StringBuffer tmp 	= new StringBuffer();
		Connection con		= null;

		try {
			
			con 		= ds.getConnection();
			
			String sql 	= "{call ? := lau_utl.getReportSummary(?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			stmt.setLong(2, reportId);
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
		log.info(tmp);
		return tmp;
	}
	
}

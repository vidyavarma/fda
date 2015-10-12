package com.nrg.lau.render;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.IReportsConstants;

@SuppressWarnings("unused")
public class GetAttachmentDetailsAction implements View {

	private static Logger log = Logger.getLogger(GetAttachmentDetailsAction.class);
	private SimpleJdbcTemplate template;
	private DataSource ds;

	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String reportId = "";
		log.info("ENTER - GetAttachmentDetailsAction render()");
		if(request.getParameter("reportid") != null)	{
			reportId = request.getParameter("reportid");
		}

		
		log.info("GetAttachmentDetailsAction render() - reportId -->  "
				+ reportId);

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();
		pw.write(getData(reportId).toString());
		pw.flush();
		pw.close();

		log.info("EXIT - GetAttachmentDetailsAction render()");
	}

	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("GetAttachmentDetailsAction - Initialize db template()");
	}

	private StringBuffer getData(String reportId) {

		StringBuffer tmp = new StringBuffer();
		Connection con = null;
		Clob clob = null;

		try {

			con = ds.getConnection();
			String sql = "select dbms_xmlgen.getxml("
					+ "'SELECT A.ATTACHMENT_ID ,  A.REPORT_ID ,  NVL(A.OBJECT_ID,B.OBJECT_ID) AS OBJECT_ID,  A.DOCUMENT_NAME ," 
					+ "  A.ACTIVITY_ID,  A.PROMOTE_DOCUMENT,  A.PROMOTE_DOCUMENT AS PROMOTE_DOCUMENT_DB,  A.EXTERNAL_DOCUMENT_URL,"
					+"  A.FILE_NAME,  A.DOCUMENT_TYPE ,  A.VIEW_STATUS,  A.BINARY_FILE_TYPE,  2 AS TRANSACTION_TYPE "
					+" FROM LAU_REPORT_ATTACHMENTS A, LAU_GENERATED_LETTERS B"
					+" WHERE A.LAU_GENERATED_LETTER_ID = B.LAU_GENERATED_LETTER_ID(+) AND A.REPORT_ID =  "
					+ reportId + " ORDER BY A.DISPLAY_NUMBER') from dual";
			log.info(sql);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			//weblogic.jdbc.wrapper.ResultSet wlsResultSet = (weblogic.jdbc.wrapper.ResultSet) rs;
		//	OracleResultSet oracleResultSet = (OracleResultSet) wlsResultSet
			//		.getVendorObj();
			while (rs.next())
				clob = rs.getClob(1);
			stmt.close();

			

			if (clob != null) {
				Reader reader = clob.getCharacterStream();
				CharArrayWriter writer = new CharArrayWriter();
				int i = -1;
				while ((i = reader.read()) != -1) {
					writer.write(i);
				}
				tmp.append(new String(writer.toCharArray()));
				log.info("report xml:" + tmp);
			}
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

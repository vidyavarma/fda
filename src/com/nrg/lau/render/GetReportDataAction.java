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

@SuppressWarnings("unused")
public class GetReportDataAction implements View {
	
	private static Logger log = Logger.getLogger(GetHomeReportAction.class);
	private SimpleJdbcTemplate template;
	private DataSource ds;	

	
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String tableName = "" ,keyName = "" ,orderby ="", extraFilter="",reportId = "";
		
		if(request.getAttribute("parameter1") != null)	{
			tableName = (String)request.getAttribute("parameter1");
		}
		if(request.getAttribute("parameter2") != null)	{
			keyName = (String)request.getAttribute("parameter2");
		}
		if(request.getAttribute("parameter3") != null)	{
			orderby = (String)request.getAttribute("parameter3");
		}
		if(request.getAttribute("parameter4") != null)	{
			extraFilter = (String)request.getAttribute("parameter4");
		}
		if(request.getParameter("reportid") != null)	{
			reportId = request.getParameter("reportid");
		}
		
		log.info("ENTER - GetReportDataAction render()");
		log.info("GetReportDataAction render() - tableName,keyName,reportId -->  "
						+ tableName + "  , " + keyName + "  , " +orderby+", "+ extraFilter+ ", "+ reportId);

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();
		pw.write(getData(tableName,keyName,reportId,orderby,extraFilter).toString());
		pw.flush();
		pw.close();

		log.info("EXIT - GetReportDataAction render()");
	}

	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("GetReportDataAction - Initialize db template()");
	}

	private StringBuffer getData(String tableName,String keyName,String reportId,String orderby, String extraFilter) {
		
		StringBuffer tmp 	= new StringBuffer();
		Connection con 		= null;

		try {
			
			con 		= ds.getConnection();
			String sql 	= "{call ? := LAU_UTL.GETXMLFROMTABLE(?,?,?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			stmt.setString(2, tableName);
			stmt.setString(3, keyName);
			stmt.setString(4, reportId);
			stmt.setString(5, orderby);
			stmt.setString(6, extraFilter);			
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
				//log.info("report xml:" + tmp);
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
	//	log.info(tmp);
		return tmp;
	}
}

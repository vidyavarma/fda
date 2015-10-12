package com.nrg.lau.render;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.io.*;
import java.net.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;

public class GetE2bMessageInfoAction implements View {

	private static Logger log = Logger.getLogger(GetE2bMessageInfoAction.class);
	private DataSource ds;

	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	public void setDataSource(DataSource dataSource) {
		new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("get E2BMEssageInfo -Initialize db template()");
	}

	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - E2BMEssageInfo render()");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();

		String strE2B = "WHERE 1 = 1";
		String partnerIDVar = "";
		if(request.getParameter("partnerID") != null)	{
			partnerIDVar = request.getParameter("partnerID");
		}
		
		String failVar = "";
		if(request.getParameter("failureCode") != null)	{
			failVar = request.getParameter("failureCode");
		}
		
		String startDateVar = "";
		if(request.getParameter("startDate") != null)	{
			startDateVar = request.getParameter("startDate");
		}
		
		String stopDateVar = "";
		
		if(request.getParameter("stopDate") != null)	{
			stopDateVar = request.getParameter("stopDate");
		}
		
		
		if (partnerIDVar.length() > 0) 
		{
			strE2B = strE2B + " AND E2B_PARTNER_ID = ''" + partnerIDVar + "''";			
		}
		if (failVar.length() > 0)
			strE2B = strE2B + " AND FAIL = ''" + failVar + "''";
		if (startDateVar.length() > 0 && stopDateVar.length() > 0) {
			String stDate = "to_char(to_date(''" + startDateVar
					+ "'',''DD-MON-YYYY''),''YYYYMMDD'')";
			String spDate = "to_char(to_date(''" + stopDateVar
					+ "'',''DD-MON-YYYY''),''YYYYMMDD'')";
			strE2B = strE2B + " AND substr(E2B_MESSAGE_DATE,1,8) BETWEEN "
					+ stDate + " AND " + spDate;
			log.info("Date: " + strE2B);
		}

		log.info("E2BMessageInfo render() - request.getParameter(parameter1) -->  "
				+ strE2B);

		if (Constants.DEBUG) {
			log.info("entered Reading!!");
			pw.write(readData("/getE2bMessageInfo.do").toString());
		} else {
			log.info("entered writing");
			pw.write(getData(strE2B).toString());
		}
		pw.flush();
		pw.close();

		log.info("EXIT - E2BMEssageInfoAction render()");
	}

	private StringBuffer readData(String fileName) throws IOException {

		StringBuffer sb = new StringBuffer();
		URL fileURL = GetE2bMessageInfoAction.class.getResource(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				fileURL.openStream()));
		String theBuf = new String();

		while ((theBuf = reader.readLine()) != null) {
			sb.append(theBuf + "\n");
		}

		reader.close();
		log.info("E2BMEssageInfoAction: " + sb.toString());
		return sb;
		// return sb.toString();
	}

	private String getData(String strE2B) throws Exception {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

		String retStr = "";

		String sqlStatement = "SELECT * FROM E2B_MESSAGE_INFO_VW " + strE2B;
		log.info("Reached just before query processing! " + sqlStatement);
		retStr = jdbcTemplate.queryForObject("SELECT dbms_xmlgen.getxml('"
				+ sqlStatement + "' ) as xmlVal FROM DUAL",
				new RowMapper<String>() {
					public String mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						String strClob = "";
						strClob = rs.getString("xmlVal");
						return strClob;
					}
				});
		if (retStr == null) {//if query returned no data
			retStr = "<ROWSET><ROW> </ROW> </ROWSET>";
		}
log.info("returned length:"+retStr.length());
		return retStr;

	}

}

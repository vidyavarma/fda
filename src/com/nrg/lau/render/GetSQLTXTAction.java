package com.nrg.lau.render;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.io.*;
import java.net.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;

public class GetSQLTXTAction implements View {

	private static Logger log = Logger.getLogger(GetSQLTXTAction.class);
	private DataSource ds;

	@Override
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	public void setDataSource(DataSource dataSource) {
		new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("get sqlTxt -Initialize db template()");
	}

	@Override
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - SQLTXT render()");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();

		String strE2B = "WHERE 1 = 1";
		String docName = "";
		docName = request.getParameter("docName");
		String nodeName = "";
		nodeName = request.getParameter("nodeName");
		
		if (docName.length() > 0) 
		{
			strE2B = strE2B + " AND DOC_NAME = ''" + docName + "''";
			if (nodeName.length() > 0)
				strE2B = strE2B + " AND NODE_NAME = ''" + nodeName + "''";
					
		}

		log.info("SqlTxt render() - request.getParameter(parameter1) -->  "
				+ strE2B);

		if (Constants.DEBUG) {
			log.info("entered Reading!!");
			pw.write(readData("/getSQLTXT.do").toString());
		} else {
			log.info("entered writing");
			pw.write(getData(strE2B).toString());
		}
		pw.flush();
		pw.close();

		log.info("EXIT - SqlText render()");
	}

	private StringBuffer readData(String fileName) throws IOException {

		StringBuffer sb = new StringBuffer();
		URL fileURL = GetSQLTXTAction.class.getResource(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				fileURL.openStream()));
		String theBuf = new String();

		while ((theBuf = reader.readLine()) != null) {
			sb.append(theBuf + "\n");
		}

		reader.close();
		log.info("getSqlTxtAction: " + sb.toString());
		return sb;
		// return sb.toString();
	}

	private String getData(String strE2B) throws Exception {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

		String retStr = "";

		String sqlStatement = "SELECT XMLELEMENT(SQLTXT,XMLCDATA(SQLTXT)) FROM LAU_XML_NODES"+" "+ strE2B;
		log.info("Reached just before query processing! " + sqlStatement);
		retStr = jdbcTemplate.queryForObject("SELECT dbms_xmlgen.getxml('"
				+ sqlStatement + "' ) as xmlVal FROM DUAL",
				new RowMapper<String>() {
					@Override
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
log.info(retStr);
		return retStr;

	}

}

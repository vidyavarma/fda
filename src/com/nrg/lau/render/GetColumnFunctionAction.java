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

public class GetColumnFunctionAction implements View {

	private static Logger log = Logger.getLogger(GetColumnFunctionAction.class);
	private DataSource ds;
	
	String docName = "";
	String nodeName = "";
	String tagName = "";

	@Override
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	public void setDataSource(DataSource dataSource) {
		new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("get ColFunction -Initialize db template()");
	}

	@Override
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - ColFunction render()");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();

		docName = request.getParameter("docName");
		nodeName = request.getParameter("nodeName");
		tagName = request.getParameter("tagName");

		if( (docName.length() > 0)  && (nodeName.length() > 0) && (tagName.length() > 0) ){
			log.info("entered writing");
			pw.write(getData());
		}

		pw.flush();
		pw.close();

		log.info("EXIT - ColumnFunction render()");
	}


	private String getData() throws Exception {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

		String retStr = "";
		log.info("docName ..."+docName+" ,"+ nodeName +tagName );
		retStr = jdbcTemplate.queryForObject("SELECT to_clob(XMLELEMENT(\"ROWSET\",XMLAGG(XMLELEMENT(\"ROW\",XMLELEMENT(COLUMN_FCT_NAME,XMLCDATA(COLUMN_FCT_NAME)))))) AS xmlVal " +
				" FROM LAU_XML_COLUMNS where DOC_NAME =? AND NODE_NAME = ? AND XML_TAG_NAME = ?",new Object[]{docName,nodeName,tagName},
				new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						String strClob = "";
						
						strClob = rs.getString("xmlVal");
						log.info("11111111:"+strClob);
						return strClob;
					}
				});
		if (retStr == null) {// if query returned no data
			retStr = "<ROWSET><ROW> xx</ROW> </ROWSET>";
		}
		log.info(retStr);
		return retStr;

	}

}

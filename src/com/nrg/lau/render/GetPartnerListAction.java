package com.nrg.lau.render;

import java.io.PrintWriter;
import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.io.*;
import java.net.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import com.nrg.lau.commons.Constants;
import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.dao.CommonDAO;

public class GetPartnerListAction implements View {

	private static Logger log = Logger.getLogger(GetPartnerListAction.class);
	private DataSource ds;
	private String userId = "";

	public String getContentType() {
		return "text/xml";
	}

	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER -GetPartnerListAction  render()");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		userId = CommonDAO.getUSERID(request);
		PrintWriter pw = response.getWriter();
		log.info("Calling getData()");
		pw.write(getData());

		pw.flush();
		pw.close();

		log.info("EXIT - GetRefListAction render()");
	}

	public void setDataSource(DataSource dataSource) {
		new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("GetRefListAction -Initialize db template()");
	}

	private String getData()  {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		String retStr = "";

		try {
			log.info("inside getData() ");
			retStr = jdbcTemplate
					.queryForObject(
							" SELECT dbms_xmlgen.getxml('SELECT DISTINCT e2b_partner_id as CODE, e2b_partner_id as CODE_VALUE "
									+ "FROM e2b_message_info_vw ORDER BY e2b_partner_id ASC') as xmlVal FROM DUAL",
							new RowMapper<String>() {
								public String mapRow(ResultSet rs, int rowNum)
										throws SQLException {
									String strClob = "";
									strClob = rs.getString("xmlVal");
									return strClob;
								}
							});
		

		} catch (Exception e) {
			retStr = "";
			log.error(e);
			
		}
		log.info("aaaaaaaaaaaaaaaa:"+retStr);
		if (retStr == null) 
		{
			retStr = "";
		}
		log.info("$&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:"+retStr);
		return retStr;

	}
}

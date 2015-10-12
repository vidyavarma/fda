package com.nrg.lau.regulatory.render;

import java.io.CharArrayWriter;
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

public class GetAssignedProcodes implements View {

	private static Logger log = Logger.getLogger(GetAssignedProcodes.class);
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
		pw.write(getData().toString());	

		pw.flush();
		pw.close();

		log.info("EXIT - GetAssignedProcodes render()");
	}

	public void setDataSource(DataSource dataSource) {
		new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("GetAssignedProcodes -Initialize db template()");
	}

	private StringBuffer getData() {

		StringBuffer tmp = new StringBuffer();
		Connection con = null;

		try {

			con = ds.getConnection();

			String sql = "{call ? := LAU_UTL.GetAssignedProcodes()}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);

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
		//log.info(tmp);
		return tmp;
	}
	/*
	 * private String getData() {
	 * 
	 * JdbcTemplate jdbcTemplate = new JdbcTemplate(ds); String retStr = "";
	 * 
	 * try { log.info("inside getData() "); retStr = jdbcTemplate
	 * .queryForObject(
	 * "SELECT XMLELEMENT(\"ROW\", XMLAgg( XMLELEMENT(\"USER\",XMLATTRIBUTES(USER_ID AS \"USER_ID\"),"
	 * + " XMLAGG(XMLELEMENT(\"PRODUCT_CODE\"," +
	 * "XMLATTRIBUTES(NVL(PRODUCT_GROUP,PRODUCT_CODE) AS \"label\",DECODE(PRODUCT_GROUP,NULL,'CODE','GRP') AS \"type\"))"
	 * +
	 * "ORDER BY NVL(PRODUCT_GROUP,PRODUCT_CODE))))) AS \"xmlVal\" FROM LAU_PROD_GROUP_ASSIGNMENTS a GROUP BY user_id"
	 * , new RowMapper<String>() { public String mapRow(ResultSet rs, int
	 * rowNum) throws SQLException { String strClob = ""; strClob =
	 * rs.getString("xmlVal"); return strClob; } });
	 * 
	 * 
	 * } catch (Exception e) { retStr = ""; log.error(e);
	 * 
	 * } log.info("aaaaaaaaaaaaaaaa:"+retStr); if (retStr.length() == 0) {
	 * retStr = ""; } log.info("$&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:"+retStr); return
	 * retStr;
	 * 
	 * }
	 */
}

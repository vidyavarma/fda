package com.nrg.lau.render;

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

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;

public class GetTradingPartnerAction implements View {

	private static Logger log = Logger.getLogger(GetTradingPartnerAction.class);
	private DataSource ds;
	
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	public void setDataSource(DataSource dataSource) {
		new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("get TradingPartner -Initialize db template()");
	}

	
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - GetTradingPartner render()");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();
	//	pw.write(readData("/getTemplates.do").toString());
		if (Constants.DEBUG){
			log.info("entered Reading!!");
			pw.write(readData("/getTradingPartner.do").toString());
		}
		else {
			log.info("entered writing");
			pw.write(getData().toString());
		}
		pw.flush();
		pw.close();

		log.info("EXIT - GetTradingPartnerAction render()");
	}

	private StringBuffer readData(String fileName) throws IOException {

		log.info("11111");
		StringBuffer sb = new StringBuffer();
		URL fileURL = GetTradingPartnerAction.class.getResource(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				fileURL.openStream()));
		String theBuf = new String();

		while ((theBuf = reader.readLine()) != null) {
			sb.append(theBuf + "\n");
		}

		reader.close();
		log.info("GetTradingPartnerAction: "+sb.toString());
		return sb;
		// return sb.toString();
	}
	
	private String getData() throws Exception {

		log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		JdbcTemplate jdbcTemplate= new JdbcTemplate(ds);
		String retStr ="";
		retStr = jdbcTemplate.queryForObject(
				"SELECT dbms_xmlgen.getxml('SELECT * FROM LAU_TRADING_PARTNER') as xmlVal FROM DUAL",
				new RowMapper<String>() {
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						String strClob = "";
						strClob =  rs.getString("xmlVal");
						return strClob;
					}
				});
		log.info("$&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:"+retStr);
		return retStr;

	}
	
	/*private StringBuffer getData() {
		
		StringBuffer tmp 	= new StringBuffer();
		Connection con		= null;
		JdbcTemplate jdbcTemplate= new JdbcTemplate(ds);
		log.info("Entered getData()");
		try {
			
			con 		= ds.getConnection();
			log.info("got connection");
			String sql 	= "";
			sql 	= jdbcTemplate.queryForObject(
					"SELECT dbms_xmlgen.getxml('SELECT * FROM LAU_TRADING_PARTNER') as xmlVal FROM DUAL",
							new RowMapper<String>() {
								public String mapRow(ResultSet rs, int rowNum) throws SQLException {
									String strClob = "";
									strClob =  rs.getString("xmlVal");
									return strClob;
								}
							});
			log.info("$&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:"+sql);
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
	//	log.info(tmp);
		return tmp;
	}
*/
}

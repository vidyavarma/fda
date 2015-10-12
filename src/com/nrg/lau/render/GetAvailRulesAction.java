package com.nrg.lau.render;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.io.*;
import java.net.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.dao.CommonDAO;

public class GetAvailRulesAction implements View {

	private static Logger log = Logger.getLogger(GetAvailRulesAction.class);
	private DataSource ds;
	private String userId	= "";
	public String getContentType() {
		return "text/xml";
	}
	public void setDataSource(DataSource dataSource) {
		new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("GetpERMISSIONS -Initialize db template()");
	}
	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	try{
		log.info("ENTER - GetAvailRulesAction render()");
		userId = CommonDAO.getUSERID(request);
		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();
		pw.write(getData().toString());	
		pw.flush();
		pw.close();
		log.info("EXIT - GetAvailRulesAction render()");
	}catch(Exception e){
		log.error( e, e);
	}
}

private StringBuffer getData() {
		
		StringBuffer tmp 	= new StringBuffer();
		Connection con		= null;

		try {
			
			con 		= ds.getConnection();
			String sql 	= "{call ? := LAU_NOTIFICATION.GetAllActiveRulesforuser(?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			stmt.setString(2,userId);
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
			log.error( e, e);			
		} catch (Exception e) {
			log.error( e, e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				log.error( e, e);
			}
		}
		log.info("GetAvailRulesAction" + tmp);
		return tmp;
	}
}

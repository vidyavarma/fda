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
import com.nrg.lau.commons.Constants;
import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.dao.CommonDAO;

public class GetGroupInboxAction implements View {

	private static Logger log = Logger.getLogger(GetGroupInboxAction .class);
	private DataSource ds;
	private String userId = "";
	private String userGroupId= "";
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - GetuserInbox  render()");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		userId = CommonDAO.getUSERID(request);
		userGroupId= (String) request.getParameter("usergroupid");
		PrintWriter pw = response.getWriter();
		if (Constants.DEBUG)
			pw.write(readData("/getList.do").toString());
		else
			{	
			pw.write(getData().toString());	
			}

		pw.flush();
		pw.close();

		log.info("EXIT - GetuserInbox render()");
	}
	public void setDataSource(DataSource dataSource) {
		new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("GetuserInbox -Initialize db template()");
	}
	private StringBuffer readData(String fileName) throws IOException {

		StringBuffer sb = new StringBuffer();
		URL fileURL = GetGroupInboxAction.class.getResource(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				fileURL.openStream()));
		String theBuf = new String();

		while ((theBuf = reader.readLine()) != null) {
			sb.append(theBuf + "\n");
		}

		reader.close();
		log.info(sb);
		return sb;
		// return sb.toString();
	}
	private StringBuffer getData() {
		
		StringBuffer tmp 	= new StringBuffer();
		Connection con		= null;

		try {
			
			con 		= ds.getConnection();
			String sql 	= "{call ? := LAU_UTL.getGroupInbox(?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			stmt.setString(2,userId);
			stmt.setString(3,userGroupId);
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
}

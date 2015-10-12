package com.nrg.lau.render;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
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
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.dao.LauReportAttachmentsDAO;

public class SetNotificationsReadAction implements View{
	
	private static Logger log = Logger.getLogger(SetNotificationsReadAction.class);
	private DataSource ds;
	private String userId	= "";
	private String notification_id = "0";
	private String userComments = "";
	private String msgStatus = "";
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
		log.info("ENTER - SetNotificationsReadAction render()");
		userId = CommonDAO.getUSERID(request);
		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();
		
		if( request.getParameter("notificationid") != null)	{			
			notification_id	= (String)request.getParameter("notificationid");
		}
		
		if( request.getParameter("usercomments") != null)	{			
			userComments	= (String)request.getParameter("usercomments");
		}
		
		if( request.getParameter("msgstatus") != null)	{			
			msgStatus	= (String)request.getParameter("msgstatus");
		}
		
		pw.write(getData().toString());	
		pw.flush();
		pw.close();
		log.info("EXIT - SetNotificationsReadAction render()");
	}catch(Exception e){
		log.error( e, e);
	}
}

	private StringBuffer readData(String fileName) throws IOException {
		StringBuffer sb = new StringBuffer();
		URL fileURL = SetNotificationsReadAction.class.getResource(fileName);
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
			String sql 	= "{call ? := LAU_NOTIFICATION.MarkNotificationStatus(?,?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			stmt.setString(2,userId);
			stmt.setString(3,notification_id);
			stmt.setString(4,userComments);
			stmt.setString(5,msgStatus);
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
		log.info("SetNotificationsReadAction" + tmp);
		return tmp;
	}
}

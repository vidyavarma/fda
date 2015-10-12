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

public class GetActivityAction implements View {

	private static Logger log = Logger.getLogger(GetActivityAction.class);
	private DataSource ds;
	
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - GetActivityAction render()");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
//myActivities
		String myActivities = "";
		if(request.getParameter("myActivities") != null)	{
			myActivities = request.getParameter("myActivities").toString();			
		}
		String userId = CommonDAO.getUSERID(request);
		String userGroupId = request.getParameter("userGroupId");		//
		String grpContext = CommonDAO.getUSERGROUPID(request);
		log.info("userID: "+userId+" groupID: "+userGroupId+" context: "+grpContext);
		PrintWriter pw = response.getWriter();
		if (Constants.DEBUG)
			pw.write(readData("/getMyReportsList.do").toString());
		else
			pw.write(getData(userId,userGroupId,grpContext,myActivities).toString());	
		pw.flush();
		pw.close();

		log.info("EXIT - GetActivityAction render()");
	}
	public void setDataSource(DataSource dataSource) {
		new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("GetActivityListAction -Initialize db template()");
	}
	private StringBuffer readData(String fileName) throws IOException {

		StringBuffer sb = new StringBuffer();
		URL fileURL = GetActivityAction.class.getResource(fileName);
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
	private StringBuffer getData(String userId,String userGroupId,String grpContext, String myActivities) {
		
		StringBuffer tmp 	= new StringBuffer();
		Connection con		= null;

		try {
			
			con 		= ds.getConnection();

			String sql 	= "{call ? := LAU_UTL.getActivityList(?,?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			stmt.setString(2,userId);
			stmt.setString(3,userGroupId);
			stmt.setString(4,grpContext);
			stmt.setString(5,myActivities);
			
			
			log.info("SQL: "+sql);
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

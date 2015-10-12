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
import com.nrg.lau.commons.Constants;
import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.security.AppContext;
import com.nrg.lau.utils.JSONUtil;

public class GetMyGroupReportsListAction implements View {

	private static Logger log = Logger.getLogger(GetMyGroupReportsListAction.class);
	private DataSource ds;
	private String userId	= "";
	private String userGroupId= "";
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - GetMyGroupReportsListAction render()");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		userGroupId= (String) request.getParameter("usergroupid");
		userId = CommonDAO.getUSERID(request);
		
		PrintWriter pw = response.getWriter();
		if (Constants.DEBUG)
			pw.write(readData("/getMyReportsList.do").toString());
		else
			pw.write(getData(request).toString());	
		pw.flush();
		pw.close();

		log.info("EXIT - GetMyGroupReportsListAction render()");
	}
	private StringBuffer readData(String fileName) throws IOException {

		StringBuffer sb = new StringBuffer();
		URL fileURL = GetMyGroupReportsListAction.class.getResource(fileName);
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
	private StringBuffer getData(HttpServletRequest request) {
		
		StringBuffer tmp 	= new StringBuffer();

		ResultSet rs  		= null;

		try {
			
			ApplicationContext ctx 	= 	AppContext.getApplicationContext();
			com.nrg.lau.dao.SharedConnectionDAO shConnection  	= 	(com.nrg.lau.dao.SharedConnectionDAO)ctx.getBean("getSharedConnection");
			Connection con 		= shConnection.getConVerify();		
			String sql = "{? = call LAU_LIST.myGroupReportList(?,?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);
			String queryID="";		
			String groupIdSelected = CommonDAO.getUSERGROUPID(request);
			if(request.getParameter("queryID") != null)	{
				//
				queryID = request.getParameter("queryID").toString();
				
			}
			stmt.registerOutParameter(1, OracleTypes.VARCHAR);
			stmt.setString(2, queryID);
			stmt.registerOutParameter(2, OracleTypes.VARCHAR);
			stmt.setString(3, userId);
			stmt.setString(4, groupIdSelected );
			stmt.setString(5, userGroupId);
			log.info ("queryID, userId, groupId:" + queryID +", "+userId + ", "+userGroupId);
			log.info(sql);
			stmt.execute();
			log.info(stmt.getObject(1));

			String qryCount =  (String) stmt.getObject(1);
			queryID =  (String) stmt.getObject(2);
			JSONArray jsonArray = new JSONArray();
			JSONObject obj = new JSONObject();
			obj.append("TOTAL_RECORDS", qryCount);
			obj.append("QUERY_ID", queryID);
			jsonArray.put(obj);
			tmp.append(jsonArray.toString());
			log.info("RETURNED STRING........... :"+tmp);


		} catch (SQLException e) {
			log.error(e, e);			
		} catch (Exception e) {
			log.error(e, e);
		} finally {
			
		}
	//	log.info(tmp);
		return tmp;
	}
}

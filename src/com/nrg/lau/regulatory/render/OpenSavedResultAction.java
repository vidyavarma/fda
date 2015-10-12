package com.nrg.lau.regulatory.render;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

public class OpenSavedResultAction implements View {

	private static Logger log = Logger.getLogger(OpenSavedResultAction.class);
	private String userId	= "";
	private String groupId	= "";
	
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - savedResultSet render()");
		String resultSetID = "";
		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		resultSetID = request.getParameter("resultSetID");
		userId = CommonDAO.getUSERID(request);
		groupId = CommonDAO.getUSERGROUPID(request);
		log.info(resultSetID);
		PrintWriter pw = response.getWriter();
		pw.write(getData(resultSetID).toString());	
		pw.flush();
		pw.close();

		log.info("EXIT - getSavedReportList render()");
	}

	public static JSONArray convert(ResultSet rs) throws Exception {
		
		JSONArray json = new JSONArray();
		ResultSetMetaData rsmd = rs.getMetaData();
		int numColumns = rsmd.getColumnCount();
		
		while (rs.next()) {
		    JSONObject obj = new JSONObject();
		    for (int i = 1; i < numColumns + 1; i++) {
		        String columnName = rsmd.getColumnName(i);		        
		        obj.put(columnName, rs.getString(columnName));
		    }
		    json.put(obj);
		}
		
		return json;
	}
	private StringBuffer getData(String resultSetID) {
		log.info("getData: resultSetID = "+resultSetID);
		StringBuffer tmp 	= new StringBuffer();
		ApplicationContext ctx 	= 	AppContext.getApplicationContext();
		com.nrg.lau.dao.SharedConnectionDAO shConnection  	= 	(com.nrg.lau.dao.SharedConnectionDAO)ctx.getBean("getSharedConnection");
		Connection con 		= shConnection.getConVerify();
		ResultSet rs  		= null;

		try {
			String queryID="";	
			String sql 	= "{call ? := LAU_LIST.openResultSet(?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.VARCHAR);
			stmt.setString(2,resultSetID);
			stmt.setString(3, queryID);
			stmt.registerOutParameter(3, OracleTypes.VARCHAR);
			log.info("resultSetID"+resultSetID+"userId"+userId+ " ,groupId:"+ groupId);
			stmt.execute();
			log.info(stmt.getObject(1));

			String qryCount =  (String) stmt.getObject(1);
			queryID =  (String) stmt.getObject(3);
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
		} 
	//	log.info(tmp);
		return tmp;
	}
}

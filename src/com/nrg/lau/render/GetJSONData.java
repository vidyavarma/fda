package com.nrg.lau.render;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.dao.CommonDAO;

@SuppressWarnings("unused")
public class GetJSONData implements View {
	
	private static Logger log = Logger.getLogger(GetJSONData.class);
	private SimpleJdbcTemplate template;
	private DataSource ds;	
	private String status = "";
	public String getContentType() {
		return "text/javascript";
	}

	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			String userId	= CommonDAO.getUSERID(request);
			String groupId	= CommonDAO.getUSERGROUPID(request);
			response.setContentType("text/javascript");
			response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
			PrintWriter pw = response.getWriter();
			pw.write(getData(userId,groupId).toString());
			pw.flush();
			pw.close();	

		log.info("EXIT - GetActivityList()");
	}

	public void setDataSource(DataSource dataSource) {		
		this.ds = dataSource;
		log.info("GetActivityList - Initialize db template()");
	}
	private StringBuffer getData(String userId, String groupId) {
		
		StringBuffer tmp 	= new StringBuffer();
		Connection con		= null;
		ResultSet rs  		= null;
		StringWriter out = new StringWriter();
		
		try {
			
			con 		= ds.getConnection();
			
			String sql 	= "{? = call getMyReportList(?,?)}";
			CallableStatement stmt = con.prepareCall(sql);
			
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.setString(2,userId);
			stmt.setString(3,groupId);
			stmt.execute();

			rs = (ResultSet) stmt.getObject(1);			
			tmp.append(convert(rs).toString());

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
	
}

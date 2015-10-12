package com.nrg.lau.render;

import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.security.AppContext;
import com.nrg.lau.utils.JSONUtil;

public class GetMyReportsListAction extends JSONUtil implements View {

	private static Logger log = Logger.getLogger(GetMyReportsListAction.class);

	private String userId	= "";
	private String groupId	= "";
	@Override
	public String getContentType() {
		return "text/xml";
	}

	@Override
	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - HomeListView render()");
		
		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);

		userId = CommonDAO.getUSERID(request);
		groupId = CommonDAO.getUSERGROUPID(request);
		PrintWriter pw = response.getWriter();
		pw.write(getData(request).toString());	
		pw.flush();
		pw.close();

		log.info("EXIT - My Report render()");
	}

	private StringBuffer getData(HttpServletRequest request) {
		
		StringBuffer tmp 	= new StringBuffer();
		ApplicationContext ctx 	= 	AppContext.getApplicationContext();
		com.nrg.lau.dao.SharedConnectionDAO shConnection  	= 	(com.nrg.lau.dao.SharedConnectionDAO)ctx.getBean("getSharedConnection");
		Connection con 		= shConnection.getConVerify();
		try {
			String sql = "{? = call LAU_LIST.myReportList(?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);
			String queryID="";		
			if(request.getParameter("queryID") != null)	{
				//
				queryID = request.getParameter("queryID").toString();
				
			}
			stmt.registerOutParameter(1, OracleTypes.VARCHAR);
			stmt.setString(2, queryID);
			stmt.registerOutParameter(2, OracleTypes.VARCHAR);
			stmt.setString(3, userId);
			stmt.setString(4, groupId);
			log.info ("queryID, userId, groupId:" + queryID +", "+userId + ", "+groupId);
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
			/*try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				log.error(e, e);
			}*/
		}
	//	log.info(tmp);
		return tmp;
	}
}

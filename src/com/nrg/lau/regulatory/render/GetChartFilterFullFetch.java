package com.nrg.lau.regulatory.render;

import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.security.AppContext;
import com.nrg.lau.utils.JSONUtil;
import org.json.JSONArray;
public class GetChartFilterFullFetch extends JSONUtil implements View {

	private static Logger log = Logger.getLogger(GetChartFilterFullFetch.class);

	@Override
	public String getContentType() {
		return "text/xml";
	}

	@Override
	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - GetChartFilterFullFetch render()");

		response.setContentType("application/json");
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);

		PrintWriter pw = response.getWriter();

		pw.print(getData(request));
		pw.flush();
		pw.close();

		log.info("EXIT - GetChartFilterFullFetch render()");
	}


	private JSONArray getData(HttpServletRequest request) {

		StringBuffer tmp = new StringBuffer();
		ResultSet rs = null;
		JSONArray obj = null;
		try {
			String queryId = "";
			String userId = CommonDAO.getUSERID(request);
			String groupId = CommonDAO.getUSERGROUPID(request);
			if(request.getParameter("queryID") != null)	{
				queryId = request.getParameter("queryID");
			}
			ApplicationContext ctx 	= 	AppContext.getApplicationContext();
			com.nrg.lau.dao.SharedConnectionDAO shConnection  	= 	(com.nrg.lau.dao.SharedConnectionDAO)ctx.getBean("getSharedConnection");
			Connection con 		= shConnection.getCon();
			String sql = "{call ? := LAU_LIST.executeQuery(?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.setString(2,queryId);
			stmt.setString(3, userId);
			stmt.setString(4, groupId);

			log.info("userId" + userId + " ,groupId:" + groupId + ", queryId"+queryId);
			stmt.execute();
			rs = (ResultSet) stmt.getObject(1);
			obj = convert(rs);
			stmt.close();
			
			
		} catch (SQLException e) {
			log.error(e, e);
		} catch (Exception e) {
			log.error(e, e);
		} finally {
		}
		// log.info(tmp);
		return obj;
	}
}

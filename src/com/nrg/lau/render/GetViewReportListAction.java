package com.nrg.lau.render;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
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
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.utils.JSONUtil;

public class GetViewReportListAction extends JSONUtil implements View {

	private static Logger log = Logger.getLogger(GetViewReportListAction.class);
	private DataSource ds;


	
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - GetViewReportListAction render()");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		String userId	= "";
		String groupId	= "";
		userId = CommonDAO.getUSERID(request);
		groupId = CommonDAO.getUSERGROUPID(request);

		PrintWriter pw = response.getWriter();
		if (Constants.DEBUG)
			pw.write(readData("/getList.do").toString());
		else
			{
			
			pw.write(getData(userId,groupId).toString());	
			
			}

		pw.flush();
		pw.close();

		log.info("EXIT - GetViewReportListAction render()");
	}
	public void setDataSource(DataSource dataSource) {
		new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("Rep listt -Initialize db template()");
	}
	private StringBuffer readData(String fileName) throws IOException {

		StringBuffer sb = new StringBuffer();
		URL fileURL = GetViewReportListAction.class.getResource(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				fileURL.openStream()));
		String theBuf = new String();

		while ((theBuf = reader.readLine()) != null) {
			sb.append(theBuf + "\n");
		}

		reader.close();
		log.info("GetViewReportListAction:"+sb);
		return sb;
		// return sb.toString();
	}
	private StringBuffer getData(String userId,String groupId) {
		
		StringBuffer tmp 	= new StringBuffer();
		Connection con		= null;
		ResultSet rs  		= null;

		try {
			
			con 		= ds.getConnection();
			String sql 	= "{call ? := LAU_UTL.getViewReportList(?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.setString(2, userId);
			stmt.setString(3, groupId);
			// p_usergroupcontext
			
			log.info(sql);
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
		//log.info("GetViewReportListAction:"+tmp);
		return tmp;
	}
}

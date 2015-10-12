package com.nrg.lau.regulatory.render;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import com.nrg.lau.commons.Constants;
import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.security.AppContext;

public class GetQueryChartsDataAction implements View {

	private static Logger log = Logger.getLogger(GetQueryChartsDataAction.class);

	@Override
	public String getContentType() {
		return "text/xml";
	}

	@Override
	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - GGetQueryChartsDataAction render()");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);


		PrintWriter pw = response.getWriter();

		// if (Constants.DEBUG)
		// pw.write(readData("/getAdhocQueryFieldList.do").toString());
		// else
		pw.write(getData(request).toString());
		pw.flush();
		pw.close();

		log.info("EXIT - GGetQueryChartsDataAction render()");
	}

	private StringBuffer getData(HttpServletRequest request) {

		StringBuffer tmp = new StringBuffer();

		try {

			String queryId = "";
			String dbFunctionNm = "";
			String userId = CommonDAO.getUSERID(request);
			String groupId = CommonDAO.getUSERGROUPID(request);
			if(request.getParameter("queryID") != null)	{
				queryId = request.getParameter("queryID");
			}
			if(request.getParameter("FUNCTIONNAME") != null)	{
				dbFunctionNm = request.getParameter("FUNCTIONNAME");
			}			
			ApplicationContext ctx 	= 	AppContext.getApplicationContext();
			com.nrg.lau.dao.SharedConnectionDAO shConnection  	= 	(com.nrg.lau.dao.SharedConnectionDAO)ctx.getBean("getSharedConnection");
			Connection con 		= shConnection.getCon();

			String sql = "{call ? := "+dbFunctionNm+"(?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			stmt.setString(2,queryId);
			log.info("queryId---->"+queryId);
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
		} catch (SQLException e) {
			log.error(e, e);
		} catch (Exception e) {
			log.error(e, e);
		} finally {
			
		}
		 log.info("---------CHART XML RETURNED"+tmp);
		return tmp;
	}
}

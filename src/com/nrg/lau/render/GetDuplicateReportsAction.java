package com.nrg.lau.render;

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

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.dao.CommonDAO;
@SuppressWarnings("unused")
public class GetDuplicateReportsAction implements View {
	
	private static Logger log = Logger.getLogger(GetDuplicateReportsAction.class);
	private SimpleJdbcTemplate template;
	private DataSource ds;	
	private String userId	= "";
	
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		userId = CommonDAO.getUSERID(request);
		String tableName = "" ,keyName = "" ,orderby ="", extraFilter="",reportId = "";
		String inputParameter = "";

		if(request.getParameter("INPUTPARAMETER") != null)	{
			inputParameter = request.getParameter("INPUTPARAMETER");
		}
		
		log.info("ENTER - GetDuplicateReportsAction render()");
		log.info("GetDuplicateReportsAction () - tableName,keyName,reportId -->  "
						+ inputParameter);

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();
		pw.write(getData(inputParameter).toString());
		pw.flush();
		pw.close();

		log.info("EXIT - GetDuplicateReportsAction()");
	}

	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("GetDuplicateReportsAction - Initialize db template()");
	}
	private StringBuffer getData(String inputParameter) {
		
		StringBuffer tmp 	= new StringBuffer();
		Connection con		= null;

		try {
			
			con 		= ds.getConnection();
			
			String sql 	= "{call ? := FINDDUPLICATES(?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			stmt.setString(2,userId);
			stmt.setString(3, inputParameter);
		//	log.info("userId:"+userId);
		//	log.info("inputParameter:"+inputParameter);
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

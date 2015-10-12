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
public class GetCodeListAction implements View {
	
	private static Logger log = Logger.getLogger(GetCodeListAction.class);
	private SimpleJdbcTemplate template;
	private DataSource ds;
	
	
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String listName = "";
		log.info("ENTER - CodeistView render()");
		if(request.getAttribute("parameter1") != null)	{
			listName = (String)request.getAttribute("parameter1");
		}
		else if(request.getParameter("parameter1") != null)
		{
			listName = (String)request.getParameter("parameter1");
		}
		log.info("CodeListView render() - request.getParameter(parameter1) -->  "
						+ listName );
		String userId	= "";
		String groupId	= "";
		userId = CommonDAO.getUSERID(request);
		groupId = CommonDAO.getUSERGROUPID(request);
		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();
		pw.write(getData(listName,userId,groupId).toString());
		pw.flush();
		pw.close();

		log.info("EXIT - CodeListView render()");
	}

	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("Codelist -Initialize db template()");
	}
	/**
	 * FUNCTION codelist(
    p_codelist   IN VARCHAR2,
    p_code       IN VARCHAR2 DEFAULT NULL,
    p_lang       IN VARCHAR2 DEFAULT 'en',
    p_user       IN VARCHAR2 DEFAULT NULL,
    p_user_group IN VARCHAR2 DEFAULT NULL)
	 */

	private StringBuffer getData(String parameter,String userId, String groupId) {
		
		StringBuffer tmp 	= new StringBuffer();
		Connection con		= null;

		try {
			
			con 		= ds.getConnection();
			String sql 	= "{call ? := LAU_UTL.CODELIST(?,?,?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			stmt.setString(2, parameter);//codelist
			stmt.setString(3, null);//p_code
			stmt.setString(4, "en");//p_lang
			stmt.setString(5, userId);//userId
			stmt.setString(6, groupId);//userId
			
			//    'saju@novemberresearch.com',
		   // 'Administration'
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
		//log.info(tmp);
		return tmp;
	}
}

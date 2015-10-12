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

public class GetCodeListAllAction implements View {
	private SimpleJdbcTemplate template;
	private DataSource ds;
	private String listName;

	/**
	 * @param listName
	 *            the listName to set
	 */
	public void setListName(String listName) {
		this.listName = listName;
	}

	private static Logger log = Logger.getLogger(GetCodeListAllAction.class);

	
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - CodeistView render()");
		log
				.info("CodeistView render() - request.getParameter(listname)   --->  "
						+ request.getParameter("listname"));
		String userId	= "";
		String groupId	= "";
		userId = CommonDAO.getUSERID(request);
		groupId = CommonDAO.getUSERGROUPID(request);
		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();
		pw.write(getData(request.getParameter("listname"),userId,groupId ).toString());
		pw.flush();
		pw.close();

		log.info("EXIT - CodeListView render()");
	}

	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("Codelist -Initializw db template()");
	}

	private StringBuffer getData(String param1,String userId, String groupId)  {
		StringBuffer tmp = new StringBuffer();

		Connection con = null;

		try {
			con = ds.getConnection();

			String sql = "{call ? := LAU_UTL.CODELIST(?,?,?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			stmt.setString(2, param1);
			stmt.setString(3, "");//p_code
			stmt.setString(4, "en");//p_lang
			stmt.setString(5, userId);//userId
			stmt.setString(6, groupId);//userId
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
			//	log.info("Codelist xml:" + tmp);
			}

			// log.info("Retrieved " + str);
			stmt.close();
			con.close();

		} catch (SQLException e) {
			log.error(e, e);
			// request.setAttribute("error", error);
		} catch (Exception e) {
			log.error(e, e);
			// log.error(error);
			//
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				log.error(e, e);
			}
		}
		return tmp;
	}
}

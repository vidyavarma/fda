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

@SuppressWarnings("unused")
public class GetE2BAction implements View {

	private static Logger log = Logger.getLogger(GetE2BAction.class);
	private SimpleJdbcTemplate template;
	private DataSource ds;

	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")

	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String listName = "";
		log.info("ENTER - GetE2BAction render()");
		if(request.getAttribute("reportId") != null)	{
			listName = (String)request.getAttribute("parameter1");
		}
		log.info("CodeListView render() - request.getParameter(parameter1) -->  "
						+ listName );

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();

		pw.write(getData(request.getParameter("reportId")).toString());
		pw.flush();
		pw.close();

		log.info("EXIT - CodeListView render()");
	}

	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("Codelist -Initialize db template()");
	}

	private StringBuffer getData(String repid) {

		StringBuffer tmp 	= new StringBuffer();
		Connection con		= null;

		try {
			//ret := LAU_e2b.main(38, x_clob, x_err_msg);
			con 		= ds.getConnection();
			String sql 	= "{call ? := LAU_e2b.main(?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.registerOutParameter(1, OracleTypes.VARCHAR);//RETURN
			log.info("In get data, rep id is"+repid);
			stmt.setString(2, "38");//IN
			stmt.registerOutParameter(3, OracleTypes.CLOB);//OUT
			stmt.registerOutParameter(4, OracleTypes.VARCHAR);//OUT

			log.info(sql);
			stmt.execute();
			Clob clob = stmt.getClob(3);
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
			System.out.println("22222222");
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
		log.info(tmp);
		System.out.println(tmp);
		return tmp;
	}
}

package com.nrg.lau.render;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.io.*;
import java.net.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.dao.CommonDAO;

public class CopyE2BColumnAction implements View {

	private static Logger log = Logger.getLogger(CopyE2BColumnAction.class);
	private DataSource ds;
	private String userId = "";
	private String docName = "";
	private String nodeName = "";
	private String oldTagName = "";
	private String newTagName = "";
	
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	public void setDataSource(DataSource dataSource) {
		new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("get CopyE2BColumn -Initialize db template()");
	}

	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - CopyE2BColumn render()");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();
		try
		{
		docName = request.getParameter("docName");
		nodeName = request.getParameter("nodeName");
		oldTagName = request.getParameter("oldTagName");
		newTagName = request.getParameter("newTagName");
		//userId = request.getParameter("userId"); // for Junit Testing
		userId = CommonDAO.getUSERID(request);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("EXCEPTION - REQUEST PARAMETRS)");
			e.printStackTrace();
		}

		
		if (Constants.DEBUG) {
			log.info("entered Reading!!");
			pw.write(readData("/copyE2BColumn.do").toString());
		} else {
			log.info("entered writing");
			pw.write(getData().toString());
		}
		pw.flush();
		pw.close();

		log.info("EXIT - CopyE2BColumn render()");
	}

	private StringBuffer readData(String fileName) throws IOException {

		StringBuffer sb = new StringBuffer();
		URL fileURL = CopyE2BColumnAction.class.getResource(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				fileURL.openStream()));
		String theBuf = new String();

		while ((theBuf = reader.readLine()) != null) {
			sb.append(theBuf + "\n");
		}

		reader.close();
		log.info("CopyE2BcolumnAction: " + sb.toString());
		return sb;
		// return sb.toString();
	}
	
	private StringBuffer getData() throws Exception {
		
		log.info("Entered getData**********");
		StringBuffer tmp = new StringBuffer();
		Connection con = null;

		try {

			con = ds.getConnection();

			String sql = "{call ? := LAU_UTL.copyE2bColumns(?,?,?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			stmt.setString(2,docName);
			stmt.setString(3, nodeName);
			stmt.setString(4,oldTagName);
			stmt.setString(5, newTagName);
			stmt.setString(6,userId);
			log.info("docName"+docName);
			log.info("nodeName"+nodeName);
			log.info("oldTagName"+oldTagName);
			log.info("newTagName"+newTagName);
			log.info("userId"+userId);
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
			log.info("**************"+tmp);
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
		
		return tmp;
	}

	

}

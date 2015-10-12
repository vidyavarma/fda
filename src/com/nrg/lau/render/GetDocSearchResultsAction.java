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
public class GetDocSearchResultsAction implements View {

	private static Logger log = Logger.getLogger(GetDocSearchResultsAction.class);
	private SimpleJdbcTemplate template;
	private DataSource ds;
	
	private String lauRepId = "";
	private String promotedCaseId = "";
	private String repLastNm = "";
	private String repCntry = "";
	private String prodName = "";
	private String docName = "";
	 

	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")

	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String listName = "";
		log.info("ENTER - GetDocSearchResultsAction render()");
		if(request.getAttribute("reportId") != null)	{
			listName = (String)request.getAttribute("parameter1");
		}
		log.info("CodeListView render() - request.getParameter(parameter1) -->  "
						+ listName );

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();

		/**
		 *  LAU_REPORT_ID      IN VARCHAR2 := NULL,
	    PROMOTED_CASE_ID   IN VARCHAR2 := NULL,
	    REPORTER_LAST_NAME IN VARCHAR2 := NULL,
	    REPORTER_COUNTRY   IN VARCHAR2 := NULL,
	    PRODUCT_NAME       IN VARCHAR2 := NULL,
	    DOCUMENT_NAME       IN VARCHAR2
	    **/
		try {
			lauRepId = request.getParameter("reportid").toString();
			promotedCaseId = request.getParameter("promotedcaseid").toString();
			repLastNm = request.getParameter("reportername").toString();
			repCntry = request.getParameter("reportercountry").toString();
			prodName = request.getParameter("prodname").toString();
			docName = request.getParameter("docname").toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("EXCEPTION - REQUEST PARAMETRS)");
			e.printStackTrace();
		}
		
		pw.write(getData().toString());
		pw.flush();
		pw.close();

		log.info("EXIT - CodeListView render render()");
	}

	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("GetDocSearchResultsAction -Initialize db template()");
	}

	private StringBuffer getData() {

		StringBuffer tmp 	= new StringBuffer();
		Connection con		= null;

		try {
			//ret := LAU_e2b.main(38, x_clob, x_err_msg);
			
			con 		= ds.getConnection();
			String sql 	= "{call ? := LAU_DOOCS.GETDOCS(?,?,?,?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.registerOutParameter(1, OracleTypes.CLOB);//RETURN
			
			stmt.setString(2, lauRepId);//IN
			stmt.setString(3, promotedCaseId);//IN
			stmt.setString(4, repLastNm);//IN
			stmt.setString(5, repCntry);//IN
			stmt.setString(6, prodName);//IN
			stmt.setString(7, docName);//IN
			log.info("lauRepId:"+lauRepId);
			log.info("promotedCaseId:"+promotedCaseId);
			log.info("repLastNm:"+repLastNm);
			log.info("repCntry:"+repCntry);
			log.info("prodName:"+prodName);
			log.info("docName:"+docName);
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
		log.info(tmp);
		System.out.println(tmp);
		return tmp;
	}
}

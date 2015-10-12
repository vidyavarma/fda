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
public class GetDrillDownReportDetailsAction implements View {
	
	private static Logger log = Logger.getLogger(GetDrillDownReportDetailsAction.class);
	private SimpleJdbcTemplate template;
	private DataSource ds;	
	private String userId	= "";
	private String calYear= "";
	private String calMonth= "";
	private String userGroups= "";
	private String repCntry= "";
	private String repStatus= "";
	private String repState= "";
	
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
		
		calYear = request.getParameter("selectedyear").toString();
		calMonth = request.getParameter("selectedmonth").toString();
		userGroups = request.getParameter("usergroup").toString();
		repStatus = request.getParameter("repstatus").toString();
		repState = request.getParameter("repstate").toString();
		
		log.info("ENTER - GetDrillDownReportDetailsAction render(): year:"+calYear+",month:"+calMonth+" ,userGroups"+
				userGroups +", repStatus"+repStatus +", repState"+repState);
		log.info("GetDrillDownReportDetailsAction () - tableName,keyName,reportId -->  "
						+ inputParameter);

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();
		pw.write(getData().toString());
		pw.flush();
		pw.close();

		log.info("EXIT - GetDrillDownReportDetailsAction()");
	}

	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("GetDrillDownReportDetailsAction - Initialize db template()");
	}
	private StringBuffer getData() {
		
		StringBuffer tmp 	= new StringBuffer();
		Connection con		= null;

		try {
			
			con 		= ds.getConnection();
			/**
			 * FUNCTION getReportDetails(
    p_user      IN VARCHAR2,
    p_year      IN VARCHAR2 := NULL,
    p_month     IN VARCHAR2 := NULL,
    p_usergroup IN VARCHAR2 := NULL,
    p_status    IN VARCHAR2 := NULL,
    p_state     IN VARCHAR2 := NULL)
  RETURN CLOB;
  
			 */
			String sql 	= "{call ? := LAU_METRICS.getReportDetails(?,?,?,?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			stmt.setString(2,userId);
			stmt.setString(3, calYear);
			stmt.setString(4,calMonth);
			stmt.setString(5,userGroups );
			stmt.setString(6, repStatus);
			stmt.setString(7,repState);
			
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

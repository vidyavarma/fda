package com.nrg.lau.regulatory.render;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.IReportChildRtnSetDAO;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.regulatory.beans.LauRunAdhocQuery;
import com.nrg.lau.regulatory.dao.LauRunAdhocQueryDAO;
import com.nrg.lau.security.AppContext;

public class GetAdhocQueryStringAction implements View {

	private static Logger log = Logger.getLogger(GetAdhocQueryStringAction.class);
	private SimpleJdbcTemplate template;
	private java.sql.Timestamp dt  = null; 
	private DataSource ds;
	private String userId = "";
	private String groupId = "";
	String rtnSqlQuery = "";

	public String getContentType() {
		return "text/xml";
	}

	@Override
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("ENTER - GetAdhocQueryStringAction render()");		
		response.setContentType(Constants.CONTENT_TYPE);		
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);		
		userId = CommonDAO.getUSERID(request);		
		dt = CommonDAO.getTimestamp(template);		
		groupId = CommonDAO.getUSERGROUPID(request);		
		PrintWriter pw = response.getWriter();		
		
		String str = getData(request,template,userId,dt);
		log.info("str------"+str);
		pw.write(str);
		log.info("after write------");
		pw.flush();
		pw.close();
		log.info("EXIT - GetAdhocQueryStringAction render()");
	}

	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("GetAdhocQueryStringAction -Initialize db template()");
	}

	
	private String getData(HttpServletRequest request, SimpleJdbcTemplate template, String user,java.sql.Timestamp dstamp) {	
		
		try {			
			ApplicationContext ctx  =  AppContext.getApplicationContext();			
		    DataSource ds = (DataSource)ctx.getBean("dataSource");		   
		    IReportChildRtnSetDAO<LauRunAdhocQuery> runAdhocQueryDAO = new LauRunAdhocQueryDAO(ds,true);
		    log.info("Before rtnQuery --------------------------  " );
		    rtnSqlQuery = (String)runAdhocQueryDAO.save(request, template, 0, user, dstamp);
		    log.info("rtnSqlQuery------"+rtnSqlQuery);
		} 
		catch (Exception e) {
			log.error(e, e);
		} 	
		log.info("rtnSqlQuery1------"+rtnSqlQuery);
		return rtnSqlQuery;
	}
}

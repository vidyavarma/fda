package com.nrg.lau.regulatory.render;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.regulatory.dao.LauAdhocQueryDAO;

public class SetAdhocQueryAction implements View{
	private static Logger log = Logger.getLogger(SetAdhocQueryAction.class);
	private DataSource ds;
	private SimpleJdbcTemplate template;
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	public String getContentType() {
		return "text/xml";
	}
	
	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("ENTER - SetAdhocQueryAction render()");
		
		String status ="";	
		String qryName ="";	
		try {
			userId = CommonDAO.getUSERID(request);
			//userId = "priya";
			dt = CommonDAO.getTimestamp(template);
			log.info("userId in action---->"+userId);
			
			qryName = request.getParameter("queryName") ;			
			log.info("qryName in action---->"+qryName);
			LauAdhocQueryDAO lauAdhocQueryDAO = new LauAdhocQueryDAO();
			String namePresent =  lauAdhocQueryDAO.save(request, template,userId,dt);
			log.info("namePresent---->"+namePresent);
			if(namePresent.trim().equals("Name exists")){
				status = namePresent;
			}else{
				status = XMLException.status("Adhoc query save was successful!");
			}		
			log.info("status---->"+status);		
			/*lauAdhocQueryDAO.save(request, template,userId,dt);
			status = XMLException.status("Adhoc query save was successful!");*/			
		}catch (Exception e) {
			log.error(e);
			status = XMLException.xmlError(e, "Adhoc query save failed!");
		}	
		
		response.setContentType(Constants.CONTENT_TYPE);
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);	    
	    PrintWriter pw = response.getWriter();		
		pw.write(status);		
		pw.flush();
		pw.close();	
		
		log.info("EXIT - SetAdhocQueryAction render()");
	}
	
	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
		this.template = new SimpleJdbcTemplate(dataSource);
	}
}

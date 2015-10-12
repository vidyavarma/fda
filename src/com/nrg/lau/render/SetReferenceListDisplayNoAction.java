package com.nrg.lau.render;

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
import com.nrg.lau.dao.LauReferenceListDisplayNoSetDAO;

@SuppressWarnings("unchecked")
public class SetReferenceListDisplayNoAction implements View{
	
	private static Logger log = Logger.getLogger(SetReferenceListDisplayNoAction.class);
	private SimpleJdbcTemplate template;
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	public String getContentType() {
		return "text/xml";
	}
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String status = "";
		try {
			userId = CommonDAO.getUSERID(request);
			dt = CommonDAO.getTimestamp(template);
			LauReferenceListDisplayNoSetDAO lauReferenceListDisplayNoSetDAO = new LauReferenceListDisplayNoSetDAO();
			lauReferenceListDisplayNoSetDAO.save(request, template, 0,userId,dt);
			status = XMLException.status("S", "ReferenceListDisplayNumber updated!");
		}catch (Exception e) {
			log.error(e);
			status = XMLException.xmlError(e, "ReferenceListDisplayNumber failed!");
		}	
		
		response.setContentType(Constants.CONTENT_TYPE);
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);	    
	    PrintWriter pw = response.getWriter();		
		pw.write(status);		
		pw.flush();
		pw.close();	

			
	}
	
	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
	}
}

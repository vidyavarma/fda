package com.nrg.lau.regulatory.render;


import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.web.servlet.View;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.apache.log4j.Logger;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.regulatory.dao.LauProductGroupingDAO;


public class SetProductGroupingAction implements View {
 
	private static Logger log = Logger.getLogger(SetProductGroupingAction.class);
	private SimpleJdbcTemplate template;
	private DataSource ds;	

	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("ENTER - SetProductGroupingAction render()");
		
		String status ="";		
		try	{
			LauProductGroupingDAO lauUserProcodesDAO = new LauProductGroupingDAO();
			lauUserProcodesDAO.save(request, template, ds);
			status = XMLException.status("save was successful");
		}catch (Exception e) {
			e.printStackTrace();
			status = XMLException.xmlError(e, "failed");
		}
		
		response.setContentType(Constants.CONTENT_TYPE);
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);	    
	    PrintWriter pw = response.getWriter();		
		pw.write(status);		
		pw.flush();
		pw.close();	
		
		log.info("EXIT - SetProductGroupingAction render()");
			
	}

	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
		this.template = new SimpleJdbcTemplate(dataSource);
	}
}


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
import com.nrg.lau.dao.LauSiebelReportAttachmentsDAO;

public class SetSiebelAttachmentAction implements View{
	
	private static Logger log = Logger.getLogger(SetSiebelAttachmentAction.class);
	private SimpleJdbcTemplate template;
	private DataSource ds;	
	
	public String getContentType() {
		return "text/xml";
	}
	
	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("ENTER - Siebel SetAttachmentAction render()");
		
		String status ="";		
		try	{
			LauSiebelReportAttachmentsDAO lauReportAttachmentsDAO = new LauSiebelReportAttachmentsDAO();
			lauReportAttachmentsDAO.save(request, template, ds);
			status = XMLException.status("Sibel Attachment save was successful");
		}catch (Exception e) {
			//e.printStackTrace();
			status = XMLException.xmlError(e, "Attachment failed");
		}
		
		response.setContentType(Constants.CONTENT_TYPE);
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);	    
	    PrintWriter pw = response.getWriter();		
		pw.write(status);		
		pw.flush();
		pw.close();	
		
		log.info("EXIT - Sibele SetAttachmentAction render()");
		
	}
	
	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
		this.template = new SimpleJdbcTemplate(dataSource);
	}
}
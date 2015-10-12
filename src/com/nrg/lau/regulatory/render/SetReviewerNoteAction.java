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
import com.nrg.lau.commons.IReportChildRtnSetDAO;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.regulatory.beans.LauReviewerNote;
import com.nrg.lau.regulatory.dao.LauReportReviewStatusSetDAO;
import com.nrg.lau.regulatory.dao.LauReviewerNoteDAO;

public class SetReviewerNoteAction implements View{
	private static Logger log = Logger.getLogger(SetReviewerNoteAction.class);
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
		log.info("ENTER - SetReviewerNoteAction render()");		
		String status ="";		
		try {			
			dt = CommonDAO.getTimestamp(template);			
			userId = CommonDAO.getUSERID(request);
			dt = CommonDAO.getTimestamp(template);				
			LauReviewerNoteDAO lauReviewerNoteDAO = new LauReviewerNoteDAO();
			lauReviewerNoteDAO.save(request, ds, userId, dt);			
			status = XMLException.status("Save was successful");			
		}catch (Exception e) {
			log.error(e);			
		}			
		response.setContentType(Constants.CONTENT_TYPE);
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);	    
	    PrintWriter pw = response.getWriter();		
		pw.write(status);		
		pw.flush();
		pw.close();	
		
		log.info("EXIT - SetReviewerNoteAction render()");
	}
	
	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
		this.template = new SimpleJdbcTemplate(dataSource);
	}
}

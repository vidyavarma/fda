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

public class DeleteAdhocQueryAction implements View{
	private static Logger log = Logger.getLogger(DeleteAdhocQueryAction.class);
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
		log.info("ENTER - DeleteAdhocQueryAction render()------>");
		
		String status ="";	
		String queryId ="";	
		
		try {
			userId = CommonDAO.getUSERID(request);			
			dt = CommonDAO.getTimestamp(template);
			queryId = request.getParameter("queryId") ;			
			log.info("queryId in action---->"+queryId);
			LauAdhocQueryDAO lauAdhocQueryDAO = new LauAdhocQueryDAO();
			/*boolean notfnPrsnt = lauAdhocQueryDAO.findNotfnExists(template, queryId);
			if(notfnPrsnt == false){
				lauAdhocQueryDAO.delete(template, queryId);
				status = XMLException.status("Adhoc query delete was successful!");
			}else{
				status = XMLException.status("Notification exists!");
			}*/
			lauAdhocQueryDAO.delete(template, queryId);
			//lauAdhocQueryDAO.save(request, template,userId,dt);
			status = XMLException.status("Adhoc query delete was successful!");			
		}catch (Exception e) {
			log.error(e);
			status = XMLException.xmlError(e, "Adhoc query delete failed!");
		}	
		
		response.setContentType(Constants.CONTENT_TYPE);
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);	    
	    PrintWriter pw = response.getWriter();		
		pw.write(status);		
		pw.flush();
		pw.close();	
		
		log.info("EXIT - DeleteAdhocQueryAction render()");
	}
	
	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
		this.template = new SimpleJdbcTemplate(dataSource);
	}
}

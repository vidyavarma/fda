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
import com.nrg.lau.regulatory.dao.LauSetQueryJoinTableDAO;

public class SetQueryJoinTable implements View{
	private static Logger log = Logger.getLogger(SetQueryJoinTable.class);
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
		log.info("ENTER - SetQueryJoinTableTemplateAction render()");
		
		String status ="";		
		try {
			//userId = CommonDAO.getUSERID(request);			
			dt = CommonDAO.getTimestamp(template);
			//log.info("userId in action---->"+userId);
			/*LauAdhocQueryDAO lauAdhocQueryDAO = new LauAdhocQueryDAO();
			lauAdhocQueryDAO.save(request, template,userId,dt);*/
			LauSetQueryJoinTableDAO lauSetQueryJoinTAbleDAO = new LauSetQueryJoinTableDAO();
			lauSetQueryJoinTAbleDAO.save(request, template,ds);
			status = XMLException.status("QueryJoinTable save was successful!");			
		}catch (Exception e) {
			log.error(e);
			status = XMLException.xmlError(e, "QueryJoinTable save was successful!");
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

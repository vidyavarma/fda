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

public class AddFieldFavoritesAction implements View{
	private static Logger log = Logger.getLogger(AddFieldFavoritesAction.class);
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
		log.info("ENTER - AddFieldFavoritesAction render()------>");
		
		String status ="";	
		String selectedFieldID ="";	
		
		try {
			userId = CommonDAO.getUSERID(request);			
			dt = CommonDAO.getTimestamp(template);
			selectedFieldID = request.getParameter("selectedFieldID") ;			
			log.info("selectedFieldID in action---->"+selectedFieldID);
			log.info("userId in action---->"+userId);
			log.info("dt in action---->"+dt);
			int id = 0;
			//LauAdhocQueryDAO lauAdhocQueryDAO = new LauAdhocQueryDAO();
			//lauAdhocQueryDAO.delete(template, queryId);
			//lauAdhocQueryDAO.save(request, template,userId,dt);
			try {
				   if(ds != null)
				   {
					    template = new SimpleJdbcTemplate(ds);
				   }
				    id = template.update(SQL_INSERT_FAVORITES_STRING,new Object[] {
				    		userId,selectedFieldID,userId,dt});
				    log.info("id---->"+id); 
				}catch (Exception e) {
				   log.info("AddFieldFavoritesAction() failed: " + e.getMessage());
				   log.error(e);
				}	
			
			status = XMLException.status("AddFieldFavoritesAction was successful!");			
		}catch (Exception e) {
			log.error(e);
			status = XMLException.xmlError(e, "AddFieldFavoritesAction failed!");
		}	
		
		response.setContentType(Constants.CONTENT_TYPE);
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);	    
	    PrintWriter pw = response.getWriter();		
		pw.write(status);		
		pw.flush();
		pw.close();	
		
		log.info("EXIT - AddFieldFavoritesAction render()");
	}
	
	private final String SQL_INSERT_FAVORITES_STRING = "INSERT INTO LAU_QUERY_FIELD_FAVORITES (USER_ID,FIELD_ID,UPDATE_USER_ID, UPDATE_TIMESTAMP) VALUES (?,?,?,?)";
	
	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
		this.template = new SimpleJdbcTemplate(dataSource);
	}
}

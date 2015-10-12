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
import com.nrg.lau.regulatory.dao.LauRunAdhocQueryDAO;

public class SetRunAdhocQuery implements View{
	
	private static Logger log = Logger.getLogger(SetRunAdhocQuery.class);
	private DataSource ds;
	private SimpleJdbcTemplate template;
		
	public String getContentType() {
		return "text/xml";
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String status 	= "";
		try	{
		
			java.sql.Timestamp dt 	= CommonDAO.getTimestamp(template);
			String userId 			= CommonDAO.getUSERID(request);
			
			IReportChildRtnSetDAO lauRunAdhocQueryDAO = new LauRunAdhocQueryDAO(ds);
			status = (String) lauRunAdhocQueryDAO.save(request, template, 0, userId, dt);
		
		}catch (Exception e) {
			e.printStackTrace();
			status = XMLException.xmlError(e, "failed");
			log.info(status);
		}
		
		response.setContentType(Constants.CONTENT_TYPE);
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);	    
	    PrintWriter pw = response.getWriter();		
		pw.write(status);		
		pw.flush();
		pw.close();		
			
	}
	
	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
		this.template = new SimpleJdbcTemplate(dataSource);
	}
}

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

@SuppressWarnings("unchecked")
public class SetCheckInAction implements View{
	
	private static Logger log 	= Logger.getLogger(SetCheckInAction.class);
	private SimpleJdbcTemplate template;
	private String userId 		= "";	
	private java.sql.Timestamp dt  = null;
	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
	}
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		long reportId = 0;
		String status = "";
		log.info("ENTER - SetCheckInAction render()");
		
		response.setContentType(Constants.CONTENT_TYPE);
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
	    PrintWriter pw = response.getWriter();
	    
		if(request.getParameter("reportId") != null)	{
			reportId	= Long.valueOf((String)request.getParameter("reportId"));
			userId		= CommonDAO.getUSERID(request); 
			dt = CommonDAO.getTimestamp(template);
			try {
		    	int update = setCheckOut(reportId);
		    	if(update == 1)
				{
		    		int intInsStatus = CommonDAO.insertActivityDetails(template, null, reportId, "CHECK IN", "", "", request,userId,dt);
		    		status = XMLException.status("Check in Successful!");
				}
		    	else status = XMLException.xmlError(new Exception(),"Checkin failed!");
		    }catch(Exception e) {
		    	status = XMLException.xmlError(e, "Check in failed!");
		    }		    
					
		}else {
			status = XMLException.xmlError(new Exception(),"Report Id not available!");
		}
		
		pw.write(status);		
		pw.flush();
		pw.close();	
		
		log.info("EXIT - SetCheckInAction render()");
	}
	
	private int setCheckOut(long reportId) throws Exception {
		int id = 0;
		id = this.template.update("UPDATE LAU_REPORTS SET REPORT_RESERVED_DATE = null,REPORT_RESERVED_BY = null,UPDATE_USER_ID=?, UPDATE_TIMESTAMP=? WHERE  REPORT_ID = ?",
				getParameters(reportId));
		log.info("SetCheckInAction update() ID -> " + id);
		return id;
	}
	private Object[] getParameters(long reportId) throws Exception	{
		return new Object[]{
			this.userId,
			CommonDAO.getTimestamp(this.template),
			reportId
		};
	}
	public String getContentType() {
		return "text/xml";
	}	
}

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
public class SetCheckOutAction implements View{
	
	private static Logger log 	= Logger.getLogger(SetCheckOutAction.class);
	private String userId 		= "";
	private java.sql.Timestamp dt  = null;
	private SimpleJdbcTemplate template;
		
	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
	}
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		long reportId = 0;
		String status = "";
		log.info("ENTER - SetCheckoutAction render()");
		
		response.setContentType(Constants.CONTENT_TYPE);
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
	    PrintWriter pw = response.getWriter();

		if( request.getParameter("reportId") != null)	{
			log.info("report id - :"+ request.getParameter("reportId"));
			reportId	= Long.valueOf((String)request.getParameter("reportId"));
			userId		= CommonDAO.getUSERID(request); 	
			dt = CommonDAO.getTimestamp(template);
		    try {
		    	int update = setCheckOut(reportId);
		    	//this.template.
		    	if(update == 1) {
		    		int intInsStatus = CommonDAO.insertActivityDetails(template, null, reportId, "CHECK OUT", "", "", request,userId,dt);
		    		status = XMLException.status("Check out Successful!");
		    	}
		    	else status = XMLException.xmlError(new Exception(),"Checkout failed!");
		    }catch(Exception e) {
		    	status = XMLException.xmlError(e, "Check out failed!");
		    }		    
					
		}else {
			status = XMLException.xmlError(new Exception(),"Report Id not available!");
		}
		
		pw.write(status);		
		pw.flush();
		pw.close();	
		
		log.info("EXIT - SetCheckoutAction render()");
	}
	
	private int setCheckOut(long reportId) throws Exception {
		int id = 0;
		id = this.template.update("UPDATE LAU_REPORTS SET REPORT_RESERVED_DATE = ?,REPORT_RESERVED_BY = ?, "+
				" UPDATE_USER_ID=?, UPDATE_TIMESTAMP=? WHERE  REPORT_ID = ? and REPORT_RESERVED_BY is null and REPORT_RESERVED_DATE is null",
				getParameters(reportId));
		log.info("SetCheckoutAction update() ID -> " + id);
		return id;
	}
	private Object[] getParameters(long reportId) throws Exception	{
		return new Object[]{
			CommonDAO.getTimestamp(this.template),
			userId,
			userId,
			dt,
			reportId
		};
	}

	public String getContentType() {
		return "text/xml";
	}	
}

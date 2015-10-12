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
public class SetNoAction implements View{
	
	private static Logger log 	= Logger.getLogger(SetNoAction.class);
	private String userId 		= "";
	private java.sql.Timestamp dt  = null;
	private SimpleJdbcTemplate template;
	private String reason = "";
	private String reasondtl = "";
	private String repstatus = "";
	private String actLogSepstatus = "";
	private String status = "";
		
	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
	}
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		long reportId = 0;

		log.info("ENTER - Set No Action Action render()");
		
		response.setContentType(Constants.CONTENT_TYPE);
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
	    PrintWriter pw = response.getWriter();

		if( (request.getParameter("reportId") != null) &&( request.getParameter("reason") != null) &&( request.getParameter("repstatus") != null))
		{

		//	reasondtl
		//	reason
		//	repstatus
			
			log.info("report id - :"+ request.getParameter("reportId"));
			reportId	= Long.valueOf((String)request.getParameter("reportId"));
			this.repstatus =request.getParameter("repstatus");
			this.reasondtl =request.getParameter("reasondtl");
			this.reason =request.getParameter("reason");
			this.actLogSepstatus = this.repstatus;
			if (this.repstatus.equalsIgnoreCase("UNDO NO ACTION"))
			{
				this.repstatus = "DATA ENTRY";
			}
			
			userId		= CommonDAO.getUSERID(request); 	
			dt = CommonDAO.getTimestamp(template);
		    try {
		    	int update = setData(reportId,reason,reasondtl,repstatus);
		    	//this.template.
		    	if(update == 1) {
		    		int intInsStatus = CommonDAO.insertActivityDetails(template, null, reportId, actLogSepstatus, reason,reasondtl, request,userId,dt);
		    		status = XMLException.status("No Action Successful!");
		    	}
		    	else status = XMLException.xmlError(new Exception(),"No Action update failed!");
		    }catch(Exception e) {
		    	status = XMLException.xmlError(e, "No Action update failed!");
		    }		    
					
		}else {
			status = XMLException.xmlError(new Exception(),"Report Id not available!");
		}
		
		pw.write(status);		
		pw.flush();
		pw.close();	
		
		log.info("EXIT - Set No Action render()");
	}
	
	private int setData(long reportId,String rsn,String rsndtl,String rstatus) throws Exception {
		int id = 0;
		id = this.template.update("UPDATE LAU_REPORTS SET REPORT_STATUS=?, DEMOTION_REASON =?, DEMOTION_REASON_DETAILS =?, "+
		" UPDATE_USER_ID=?, UPDATE_TIMESTAMP=?,REPORT_RESERVED_BY = null,REPORT_RESERVED_DATE =null WHERE  REPORT_ID = ? " +
		"and nvl(REPORT_RESERVED_BY,?) = ? and nvl(REPORT_STATUS,'-9999') != ? ",
		getParameters(reportId));
		log.info("SetNo Action update() ID -> " + id);
		return id;
	}
	private Object[] getParameters(long reportId) throws Exception	{
		return new Object[]{
			repstatus,reason,reasondtl,
			this.userId,
			dt,
			reportId,
			this.userId,
			this.userId,
			repstatus
		};
	}

	public String getContentType() {
		return "text/xml";
	}	
}

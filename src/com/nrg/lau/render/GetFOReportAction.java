package com.nrg.lau.render;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.reports.LauFOReport;

public class GetFOReportAction implements View {
	
	private static Logger log = Logger.getLogger(GetFOReportAction.class);
	private DataSource ds;	
	
	public String getContentType() {
		return "text/xml";
	}
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("ENTER - GetFOReportAction render()");
		String status ="";
		try{			
			
			if(request.getParameter("reportId") != null && 				 
					request.getParameter("dataTemplateId") != null &&
					request.getParameter("layoutTemplateId") != null && request.getParameter("lauReportId") != null && request.getParameter("templateName") != null)	{
				
				log.info("Request Parameters -" + 
						" reportId -> " + request.getParameter("reportId") +
						" contactId -> " + request.getParameter("contactId") +
						" dataTemplateId -> " + request.getParameter("dataTemplateId") +
						" layoutTemplateId -> " + request.getParameter("layoutTemplateId")+
						" lauReportId -> " + request.getParameter("lauReportId")+
						" templateName -> " + request.getParameter("templateName"));
				
				LauFOReport foReport = new LauFOReport();
				foReport.getReport(response, ds, request
						,request.getParameter("reportId")
						,request.getParameter("dataTemplateId")
						,request.getParameter("layoutTemplateId")
						,request.getParameter("lauReportId")
						,request.getParameter("templateName")
						);
			} else {
				
				String params = "Request Parameter missing -" + 
						" reportId -> " + request.getParameter("reportId") +
						" contactId -> " + request.getParameter("contactId") +
						" dataTemplateId -> " + request.getParameter("dataTemplateId") +
						" layoutTemplateId -> " + request.getParameter("layoutTemplateId") +
						" lauReportId -> " + request.getParameter("lauReportId")+
						" templateName -> " + request.getParameter("templateName");
				log.info(params);
				
				status = XMLException.status("F", params);
				response.setContentType(Constants.CONTENT_TYPE);
			    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);	    
			    PrintWriter pw = response.getWriter();		
				pw.write(status);		
				pw.flush();
				pw.close();	
			}
		
		}catch (Exception e) {
			log.error(e);
			status = XMLException.status("F", e.getMessage());
			response.setContentType(Constants.CONTENT_TYPE);
		    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);	    
		    PrintWriter pw = response.getWriter();		
			pw.write(status);		
			pw.flush();
			pw.close();	
		}		
		log.info("EXIT - GetFOReportAction render()");
		
	}
	
	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
	}
}
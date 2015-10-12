package com.nrg.lau.render;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.dao.LauReportActivityAttachmentSetTransactionManager;
import com.nrg.lau.security.AppContext;

public class SetReportActivityAttachmentAction implements View{
	
	
	public String getContentType() {
		return "text/xml";
	}
	
	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		ApplicationContext ctx 	= 	AppContext.getApplicationContext();
		LauReportActivityAttachmentSetTransactionManager lauReportActivityAttachmentSetTransactionManager = (LauReportActivityAttachmentSetTransactionManager)ctx.getBean("setReportActivityAttachment");
		String status = lauReportActivityAttachmentSetTransactionManager.setReport(request);
			
		response.setContentType(Constants.CONTENT_TYPE);
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);	    
	    PrintWriter pw = response.getWriter();		
		pw.write(status);		
		pw.flush();
		pw.close();	
			
	}
}

package com.nrg.lau.render;


import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.dao.LauLayoutTemplateSetTransactionManager;
import com.nrg.lau.security.AppContext;

public class SetLayoutTemplateAction implements View {
 
	private static Logger log = Logger.getLogger(SetDataTemplateAction.class);
	private SimpleJdbcTemplate template;
	private DataSource ds;	

	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("ENTER - SetLayoutTemplateAction render()");
		
		String status ="";		
		try	{
			// PR-766 update layout template in transaction by vinay.kumar
			
			ApplicationContext ctx 	= 	AppContext.getApplicationContext();
			LauLayoutTemplateSetTransactionManager lauLayoutTemplateSetTransactionManager = (LauLayoutTemplateSetTransactionManager)ctx.getBean("setLayoutTemplateTransaction");
			status =lauLayoutTemplateSetTransactionManager.save(request);
			/*
			LauLayoutTemplateDAO lauLayoutTemplateDAO = new LauLayoutTemplateDAO();
			lauLayoutTemplateDAO.save(request, template, ds);
			status = XMLException.status("Template save was successful");
			*/
			
			
		}catch (Exception e) {
			e.printStackTrace();
			status = XMLException.xmlError(e, "failed");
		}
		
		response.setContentType(Constants.CONTENT_TYPE);
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);	    
	    PrintWriter pw = response.getWriter();		
		pw.write(status);		
		pw.flush();
		pw.close();	
		
		log.info("EXIT - SetDataTemplateAction render()");
			
	}

	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
		this.template = new SimpleJdbcTemplate(dataSource);
	}
}

/*
 <?xml version="1.0"?>
<ROWSET>
 <ROW>
  <LAYOUT_TEMPLATE_ID>1</LAYOUT_TEMPLATE_ID>
  <TEMPLATE_NAME>RTF_Template</TEMPLATE_NAME>
  <STANDARD_REPORT>1</STANDARD_REPORT>
  <UPDATE_USER_ID>Rahul</UPDATE_USER_ID>
  <LAYOUT_TEMPLATE></LAYOUT_TEMPLATE>
 </ROW>
</ROWSET>
*/

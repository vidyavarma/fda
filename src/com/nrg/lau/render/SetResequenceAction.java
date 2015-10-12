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
import com.nrg.lau.dao.LauReportAttachmentsDAO;

public class SetResequenceAction implements View{
	
	private static Logger log = Logger.getLogger(SetResequenceAction.class);
	private SimpleJdbcTemplate template;
	private DataSource ds;	
	private String status = "";
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	public String getContentType() {
		return "text/xml";
	}
	
	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long reportId = 0;
		userId = CommonDAO.getUSERID(request);
		dt = CommonDAO.getTimestamp(template);
		String seqType = "";
		String tableName ="";
		String tablePKName ="";
		long recordId = 0;
		long recordPosition = 0;
		
		String dispColName = "DISPLAY_NUMBER";
		long startNumber = 1;
		
		
		log.info("ENTER - Resequence render()");

		if( (request.getParameter("sequencetype") != null))
			seqType =request.getParameter("sequencetype");
		if( (request.getParameter("reportid") != null))
			reportId =Long.valueOf((String) request.getParameter("reportid"));
		if( (request.getParameter("recordid") != null))
			recordId =Long.valueOf((String) request.getParameter("recordid"));
		if( (request.getParameter("newindex") != null))
			recordPosition =Long.valueOf((String) request.getParameter("newindex"));	
		if (seqType.equalsIgnoreCase("CONTACTS"))
		{
			tableName ="LAU_CONTACT";
			tablePKName = "CONTACT_ID";
		}
		else if (seqType.equalsIgnoreCase("PRODUCTS"))
		{
			tableName ="LAU_PRODUCTS";
			tablePKName = "PRODUCT_ID";
			startNumber = 1000;
		}
		else if (seqType.equalsIgnoreCase("EVENTS"))
		{
			tableName ="LAU_EVENTS";
			tablePKName = "EVENT_ID";
		}
		else if (seqType.equalsIgnoreCase("ATTACHMENTS"))
		{
			tableName ="LAU_REPORT_ATTACHMENTS";
			tablePKName = "ATTACHMENT_ID";
		}
		else if (seqType.equalsIgnoreCase("EXTERNALREFERNCE"))
		{
			tableName ="LAU_EXTERNAL_REFERENCES";
			tablePKName = "EXTERNAL_REFERENCE_ID";
		}
		try	{
			if (tableName.length() > 1 && tablePKName.length() > 1)
				{
				int id = 0;
				log.info(" BEFORE setReseqenceDisplayOrder update() ID - table name:"+tableName+",repid:"+reportId+", PK,:"+tablePKName+":"+recordId +",position"+recordPosition);
				id = template.update("begin LAU_UTL.RESEQUENCEALL(?,?,?,?,?,?,?,?,?); end;"
						,new Object[] {reportId,userId,dt,tableName,dispColName,startNumber,tablePKName,recordId, recordPosition});
				log.info("setReseqenceDisplayOrder update() ID -> " + id + ":, table name:"+tableName+",repid:"+reportId+", startnu,:"+startNumber);
				if(id == 1) {
		    		status = XMLException.status("Resequence Successful!");
		    	}
			}

		}catch (Exception e) {
			e.printStackTrace();
			status = XMLException.xmlError(e, "Attachment failed");
		}
		
		response.setContentType(Constants.CONTENT_TYPE);
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);	    
	    PrintWriter pw = response.getWriter();		
		pw.write(status);		
		pw.flush();
		pw.close();	
		
		log.info("EXIT - Resequence render()");
		
	}
	
	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
		this.template = new SimpleJdbcTemplate(dataSource);
	}
}

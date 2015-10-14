package com.nrg.lau.dao;


import java.io.StringReader;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauDataTemplate;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.service.bi.BIPublisher;
import com.nrg.lau.service.bi.BIPublisherLoader;
import com.nrg.lau.utils.ReadConfig;

public class LauDataTemplateDAO {
	
	private Vector<LauDataTemplate> templates	= null;
	private LauDataTemplate dataTemplate	= null;
	private static Logger log	= Logger.getLogger(LauDataTemplateDAO.class);
   								
	private final String SQL_UPDATE_STRING = "UPDATE LAU_DATA_TEMPLATES SET UPDATE_USER_ID=?," +
			"UPDATE_TIMESTAMP=?,TEMPLATE_NAME=?,TEMPLATE_DESCRIPTION=?,STANDARD_REPORT=? WHERE " +
			"DATA_TEMPLATE_ID=?";
	
				
	public void save(HttpServletRequest request,SimpleJdbcTemplate template,
			DataSource datasource) throws Exception {
		log.info("Entered SAVE!!!");
		String	userId = CommonDAO.getUSERID(request);
		log.info("USERID: "+userId);
		java.sql.Timestamp dt = CommonDAO.getTimestamp(template);
		log.info("TimeStamp: "+dt.toString());
		log.info("To check if null: "+request.getParameter(IReportsConstants.LAU_DATA_TEMPLATE_PARAM_NAME));
		if(request.getParameter(IReportsConstants.LAU_DATA_TEMPLATE_PARAM_NAME) != null &&
				request.getParameter(IReportsConstants.LAU_DATA_TEMPLATE_PARAM_NAME).length() > 0) {

			log.info("LauDataTemplateDAO save() LAU_DATA_TEMPLATE_PARAM_NAME -> "
					+ request.getParameter(IReportsConstants.LAU_DATA_TEMPLATE_PARAM_NAME));
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_DATA_TEMPLATE_PARAM_NAME));

			Iterator<LauDataTemplate> itr = this.templates.iterator();
			while(itr.hasNext())	{
				LauDataTemplate lauDataTemplate = (LauDataTemplate)itr.next();
				log.info("itr.hasNext() -> " + lauDataTemplate.toString());
				this.dataTemplate	= null;
				this.dataTemplate	= lauDataTemplate;
				log.info("lauDataTemplate.TempName"+lauDataTemplate.getTemplateName());
				log.info("lauDataTemplate.getTransactionType()******"+lauDataTemplate.getTransactionType());
				if(lauDataTemplate.getTransactionType() == 2){
					log.info("updateUser - TRANSACTION_TYPE [2] -> " + lauDataTemplate.getDataTemplateId());
					updateTemplate(datasource,request,template,userId,dt);
				}	
				if(lauDataTemplate.getTransactionType() == IReportsConstants.deleteFlag){
					
					log.info("lauDataTemplateDAO - save() -> [TRANSACTION_TYPE 0]: " + lauDataTemplate.toString());
					CommonDAO.setDbmsClientInfo(template,userId );
					template.update("DELETE FROM LAU_DATA_TEMPLATES WHERE DATA_TEMPLATE_ID = ?", 
							new Object[]{lauDataTemplate.getDataTemplateId()});
					
					//BI Publisher 11g web service integration
					boolean biServer = Boolean.valueOf(ReadConfig.getValue("BI_SERVER"));	
					if(biServer) {
						
						log.info("*** Start deleting BI Publisher datamodel ***");		
						BIPublisher bi = BIPublisherLoader.getBIPublisher();
						boolean rtn = bi.deleteDataModel(String.valueOf(lauDataTemplate.getDataTemplateId()));
						log.info("*** End deleting BI Publisher datamodel *** return -> "+ rtn);
						
					}//*****************************************
				}
			}//end of while(itr.hasNext())

		}	else {
			log.info(IReportsConstants.LAU_DATA_TEMPLATE_PARAM_NAME + " not found in request");
		}

	}
	
	private Object[] getParameters(LauDataTemplate lauTemplate, String userId, java.sql.Timestamp dt)
	{	log.info("Entered getParameters");
		log.info("Temp Name---->"+lauTemplate.getTemplateName());
		log.info("Temp DESC---->"+lauTemplate.getTemplateDescription());
		log.info("Temp Report---->"+lauTemplate.getStdReport());
		log.info("Temp ---->"+lauTemplate.getDataTemplate());
		log.info("Temp Id---->"+lauTemplate.getDataTemplateId());
		log.info("Id"+userId);
		log.info("DT"+dt);
		return new Object[]{
				userId,
				dt,
				lauTemplate.getTemplateName(),
				lauTemplate.getTemplateDescription(),
				lauTemplate.getStdReport(),
				lauTemplate.getDataTemplateId()
				
		};
		
	}	
	
	
	private void createBeansFromXml(String xml) throws Exception {

		templates			= new Vector<LauDataTemplate>();
		String mainXmlTag 	= "ROWSET/ROW";
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauDataTemplate.class );

		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/DATA_TEMPLATE_ID", "dataTemplateId" );
		digester.addBeanPropertySetter( mainXmlTag+"/TEMPLATE_NAME", "templateName" );
		digester.addBeanPropertySetter( mainXmlTag+"/TEMPLATE_DESCRIPTION", "templateDescription" );
		digester.addBeanPropertySetter( mainXmlTag+"/STANDARD_REPORT", "stdReport" );
		//digester.addBeanPropertySetter( mainXmlTag+"/DATA_TEMPLATE", "dataTemplate" );	
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );

		//Move to next LauReportAttachments
		digester.addSetNext( mainXmlTag, "addXmlData" );
		digester.parse(new StringReader(xml));

	}

	public void addXmlData(LauDataTemplate lauDataTemplates) {
		templates.add(lauDataTemplates);
		log.info(lauDataTemplates.toString());
	}
	
	
	public void updateTemplate(DataSource datasource, HttpServletRequest request,
			SimpleJdbcTemplate template, String userId,java.sql.Timestamp dt)	throws Exception {
		int id = 0;
		
		log.info("ID: "+this.dataTemplate.getDataTemplateId());
		log.info("DT----->"+dt);
		log.info("userid----->"+userId);
		log.info("this.dataTemplate.getTemplateName()--->"+ this.dataTemplate.getTemplateName());
		log.info("this.dataTemplate.getTemplateDescription()--->"+this.dataTemplate.getTemplateDescription());
		log.info("this.dataTemplate.getStdReport()--->"+this.dataTemplate.getStdReport());
		
		id = template.update(SQL_UPDATE_STRING,getParameters(dataTemplate,userId,dt));
	
		log.info("LauDataTemplateDAO delete() ID -> " + id);

	}
	
}
	

/*
<?xml version="1.0"?>
<ROWSET>
 <ROW>
  <DATA_TEMPLATE_ID>1</DATA_TEMPLATE_ID>
  <TEMPLATE_NAME>Product_Template</TEMPLATE_NAME>
  <DATA_TEMPLATE> </DATA_TEMPLATE>
  <STANDARD_REPORT>1</STANDARD_REPORT>
  <UPDATE_USER_ID>Rahul</UPDATE_USER_ID>
 </ROW>
</ROWSET>
*/
package com.nrg.lau.dao;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauReportActivities;
import com.nrg.lau.beans.LauXmlDoc;
import com.nrg.lau.commons.IReportsConstants;

public class LauE2BDocActionDAO {

	private Vector<LauXmlDoc> templates	= null;
	private LauXmlDoc xmlDoc	= null;
	private static Logger log	= Logger.getLogger(LauE2BDocActionDAO.class);
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
   								
	private final String SQL_UPDATE_STRING = "UPDATE LAU_XML_DOCUMENTS SET UPDATE_USER_ID=?," +
			"UPDATE_TIMESTAMP=?,DOCUMENT_DESCRIPTION=?,LANGUAGE_CODE=? WHERE " +
			"DOC_NAME=?";
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_XML_DOCUMENTS(DOC_NAME,UPDATE_USER_ID," +
			"UPDATE_TIMESTAMP,DOCUMENT_DESCRIPTION,LANGUAGE_CODE) VALUES(?,?,?,?,?)";
		
		
	public void save(HttpServletRequest request,SimpleJdbcTemplate template,
			DataSource datasource) throws Exception {
		log.info("Entered SAVE!!!");
		userId = CommonDAO.getUSERID(request);
		log.info("USERID: "+userId);
		dt = CommonDAO.getTimestamp(template);
		log.info("TimeStamp: "+dt.toString());
		log.info("To check if null: "+request.getParameter(IReportsConstants.LAU_XML_DOCUMENTS_PARAM_NAME));
		if(request.getParameter(IReportsConstants.LAU_XML_DOCUMENTS_PARAM_NAME) != null &&
				request.getParameter(IReportsConstants.LAU_XML_DOCUMENTS_PARAM_NAME).length() > 0) {

			log.info("LauE2BDocActionDAO save() LAU_XML_DOCUMENTS_PARAM_NAME -> "
					+ request.getParameter(IReportsConstants.LAU_XML_DOCUMENTS_PARAM_NAME));
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_XML_DOCUMENTS_PARAM_NAME));

			Iterator<LauXmlDoc> itr = this.templates.iterator();
			while(itr.hasNext())	{
				LauXmlDoc lauXmlDoc = (LauXmlDoc)itr.next();
				log.info("itr.hasNext() -> " + lauXmlDoc.toString());
				this.xmlDoc	= null;
				this.xmlDoc	= lauXmlDoc;
				log.info("lauXmlDoc.DocName"+lauXmlDoc.getDocName());
				log.info("lauXmlDoc.getTransactionType()******"+lauXmlDoc.getTransactionType());
				
				if(lauXmlDoc.getTransactionType() == IReportsConstants.deleteFlag){
					log.info("lauE2BDocActionDAO - save() -> [TRANSACTION_TYPE 0]: " + lauXmlDoc.toString());
					deleteDocument(template,userId,dt);
				}
				else if(lauXmlDoc.getTransactionType() == 1){
					log.info("Insert Doc");
					insertDocument(template,SQL_INSERT_STRING,userId,dt);
				}
				else{
					log.info("updateUser - TRANSACTION_TYPE [2] -> " + lauXmlDoc.getDocName());
					updateDocument(template,userId,dt);
				}
				
			}//end of while(itr.hasNext())

		}	else {
			log.info(IReportsConstants.LAU_XML_DOCUMENTS_PARAM_NAME + " not found in request");
		}

	}
	
	private void deleteDocument(SimpleJdbcTemplate template, String userId, Timestamp dt) {
		int id = 0;
		id = template.update("DELETE FROM LAU_XML_COLUMNS WHERE DOC_NAME = ?", 
				new Object[]{xmlDoc.getDocName()});
		log.info("delete Column() ID -> " + id);
		id = template.update("DELETE FROM LAU_XML_NODES WHERE DOC_NAME = ?", 
				new Object[]{xmlDoc.getDocName()});
		log.info("delete Node() ID -> " + id);
		id = template.update("DELETE FROM LAU_XML_DOCUMENTS WHERE DOC_NAME = ?", 
				new Object[]{xmlDoc.getDocName()});
		log.info("delete Document() ID -> " + id);
		
	}

	private void insertDocument(SimpleJdbcTemplate template, String sql, String userId, java.sql.Timestamp dt) throws Exception
	{
						
		int id = 0;
		id = template.update(SQL_INSERT_STRING,getInsertParameters(template,userId,dt));
		log.info("LauE2BDocActionDAO insert() ID -> " + id);

	}
	

	private Object[] getInsertParameters(SimpleJdbcTemplate template,String userId, java.sql.Timestamp dt) {
				
		LauXmlDoc lauXmlDoc = this.xmlDoc;
		return new Object[]{
					lauXmlDoc.getDocName(),
					userId ,
					dt,
					lauXmlDoc.getDocDesc(),
					lauXmlDoc.getLangCode()
					
	};
}


	private Object[] getParameters(LauXmlDoc lauDoc, String userId, java.sql.Timestamp dt)
	{	log.info("Entered getParameters");
		
		log.info("Doc Name---->"+lauDoc.getDocName());
		log.info("Doc DESC---->"+lauDoc.getDocDesc());
		log.info("Doc LangCode---->"+lauDoc.getLangCode());
		log.info("Id"+userId);
		log.info("DT"+dt);
		return new Object[]{
				userId,
				dt,
				lauDoc.getDocDesc(),
				lauDoc.getLangCode(),
				lauDoc.getDocName()
				
		};
		
	}	
	
	
	private void createBeansFromXml(String xml) throws Exception {

		templates			= new Vector<LauXmlDoc>();
		String mainXmlTag 	= "ROWSET/ROW";
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauXmlDoc.class );

		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/DOC_NAME", "docName" );
		digester.addBeanPropertySetter( mainXmlTag+"/DOCUMENT_DESCRIPTION", "docDesc" );
		digester.addBeanPropertySetter( mainXmlTag+"/LANGUAGE_CODE", "langCode" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );

		//Move to next LauReportAttachments
		digester.addSetNext( mainXmlTag, "addXmlData" );
		digester.parse(new StringReader(xml));

	}

	public void addXmlData(LauXmlDoc lauXmlDoc) {
		templates.add(lauXmlDoc);
		log.info(lauXmlDoc.toString());
	}
	
	
	public void updateDocument(SimpleJdbcTemplate template, String userId,java.sql.Timestamp dt)	throws Exception {
		int id = 0;
		
		log.info("ID: "+this.xmlDoc.getDocName());
		log.info("DT----->"+dt);
		log.info("userid----->"+userId);
		log.info("this.xmlDoc.getTemplateName()--->"+ this.xmlDoc.getDocDesc());
		log.info("this.xmlDoc.getTemplateDescription()--->"+this.xmlDoc.getLangCode());
			
		id = template.update(SQL_UPDATE_STRING,getParameters(xmlDoc,userId,dt));
	
		log.info("LauDataTemplateDAO delete() ID -> " + id);

	}
}
/*
 * <?xml version="1.0"?>
<ROWSET>
 <ROW>
  <DOC_NAME>DTD21</DOC_NAME>
  <DOCUMENT_DESCRIPTION>ICH DTD 2.1 Standard</DOCUMENT_DESCRIPTION>
  <LANGUAGE_CODE>DTD21</LANGUAGE_CODE>
  <TRANSACTION_TYPE>1</TRANSACTION_TYPE>
 </ROW>
</ROWSET>
**/
package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauExternalReferences;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauExternalReferencesSetDAO implements IReportChildSetDAO<LauExternalReferences> {

	private static Logger log	= Logger.getLogger(LauExternalReferencesSetDAO.class);
	private Vector<LauExternalReferences> reports		= null;
	private LauExternalReferences externalReferencesDetails 	= null;	
	private java.sql.Timestamp dt  = null;  
	private String userId	= "";
	
	protected void finalize() throws Throwable {		
		this.reports.removeAllElements();
		this.reports = null;
		super.finalize();
	}
	

	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId,String user,java.sql.Timestamp dstamp) throws Exception {
		//Check to make sure that XML is there in Request.
		userId = user;
		dt = dstamp;
		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_EXTERNAL_REFERENCES_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_EXTERNAL_REFERENCES_PARAM_NAME).length() > 0) {
			
			log.info("LauExternalReferencesSetDAO save() LAU_EXTERNAL_REFERENCES_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_EXTERNAL_REFERENCES_PARAM_NAME));
			
			//Create LauExternalReferences beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_EXTERNAL_REFERENCES_PARAM_NAME));
			Iterator<LauExternalReferences> itr = this.reports.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauExternalReferences lauExternalReferences = (LauExternalReferences)itr.next();
				log.info("itr.hasNext() -> " + lauExternalReferences.toString());
				this.externalReferencesDetails	= null;
				this.externalReferencesDetails	= lauExternalReferences;
				if(lauExternalReferences.getEXTERNAL_REFERENCE_ID() == 0	)	{
						insert(template,reportId);
				}else {
					if(lauExternalReferences.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else	update(template);				
				}
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_EXTERNAL_REFERENCES_PARAM_NAME + " not found in request");
		}
		
	}
	

	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {
		int id = 0;
		long externalRefId = CommonDAO.getPrimaryKey(template);
		this.externalReferencesDetails.setEXTERNAL_REFERENCE_ID(externalRefId);
		if(this.externalReferencesDetails.getREPORT_ID() == 0)	{
			this.externalReferencesDetails.setREPORT_ID(reportId);	}
		log.info("Generated Primary Key for EXTERNAL_REFERENCE_ID  ->" + externalRefId);
		id = template.update(SQL_INSERT_STRING,getInsertParameters(template));
		log.info("LauExternalReferencesSetDAO insert() ID -> " + id);
		
	}
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_EXTERNAL_REFERENCES " +
			"(DISPLAY_NUMBER,EXTERNAL_REFERENCE_TYPE,EXTERNAL_REFERENCE_NUMBER," +
			"EXTERNAL_REFERENCE_SOURCE,EXTERNAL_REF_REPORT_FORM,UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID," +
			"EXTERNAL_REFERENCE_ID) VALUES (?,?,?,?,?,?,?,?,?)";
	

	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauExternalReferencesSetDAO update() ID -> " + id);
		
	}
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_EXTERNAL_REFERENCES SET " +
			"DISPLAY_NUMBER=?,EXTERNAL_REFERENCE_TYPE=?,EXTERNAL_REFERENCE_NUMBER=?," +
			"EXTERNAL_REFERENCE_SOURCE=?,EXTERNAL_REF_REPORT_FORM=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? " +
			"WHERE REPORT_ID=? AND EXTERNAL_REFERENCE_ID=?";
	

	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update("DELETE FROM LAU_EXTERNAL_REFERENCES WHERE EXTERNAL_REFERENCE_ID = ?",
				new Object[]{this.externalReferencesDetails.getEXTERNAL_REFERENCE_ID()});
		CommonDAO.setReseqenceDisplayOrder(template, this.externalReferencesDetails.getREPORT_ID(), userId,dt, 
				"LAU_EXTERNAL_REFERENCES", "DISPLAY_NUMBER", 1, null,null,1);
		log.info("LauExternalReferencesSetDAO delete() ID -> " + id);	
		
	}
	
	private Object[] getParameters()
	{
		LauExternalReferences lauExternalReferences = this.externalReferencesDetails; 
		return new Object[]{
				lauExternalReferences.getDISPLAY_NUMBER(),
				lauExternalReferences.getEXTERNAL_REFERENCE_TYPE(),
				lauExternalReferences.getEXTERNAL_REFERENCE_NUMBER(),
				lauExternalReferences.getEXTERNAL_REFERENCE_SOURCE(),
				lauExternalReferences.getEXTERNAL_REF_REPORT_FORM(),
				userId,dt,
				lauExternalReferences.getREPORT_ID(),				
				lauExternalReferences.getEXTERNAL_REFERENCE_ID()				
		};
	}
	private Object[] getInsertParameters(SimpleJdbcTemplate template)
	{
		LauExternalReferences lauExternalReferences = this.externalReferencesDetails; 
		return new Object[]{
				CommonDAO.getNextDisplayNumber(template, lauExternalReferences.getREPORT_ID(), "LAU_EXTERNAL_REFERENCES",0),
				lauExternalReferences.getEXTERNAL_REFERENCE_TYPE(),
				lauExternalReferences.getEXTERNAL_REFERENCE_NUMBER(),
				lauExternalReferences.getEXTERNAL_REFERENCE_SOURCE(),
				lauExternalReferences.getEXTERNAL_REF_REPORT_FORM(),
				userId,dt,
				lauExternalReferences.getREPORT_ID(),				
				lauExternalReferences.getEXTERNAL_REFERENCE_ID()				
		};
	}	

	public void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag = "ROWSET/ROW";
		reports				= new Vector<LauExternalReferences>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauExternalReferences.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/EXTERNAL_REFERENCE_ID", "EXTERNAL_REFERENCE_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "REPORT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		
		digester.addBeanPropertySetter( mainXmlTag+"/DISPLAY_NUMBER", "displayNumber2" );
		digester.addBeanPropertySetter( mainXmlTag+"/EXTERNAL_REFERENCE_TYPE", "EXTERNAL_REFERENCE_TYPE" );
		digester.addBeanPropertySetter( mainXmlTag+"/EXTERNAL_REFERENCE_NUMBER", "EXTERNAL_REFERENCE_NUMBER" );
		digester.addBeanPropertySetter( mainXmlTag+"/EXTERNAL_REFERENCE_SOURCE", "EXTERNAL_REFERENCE_SOURCE" );	
		digester.addBeanPropertySetter( mainXmlTag+"/EXTERNAL_REF_REPORT_FORM", "EXTERNAL_REF_REPORT_FORM" );			
		
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "UPDATE_USER_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "UPDATE_TIMESTAMP" );	
		digester.addBeanPropertySetter( mainXmlTag+"/INTERNAL_LINK_ID", "INTERNAL_LINK_ID");
		//Move to next LauExternalReferences
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));
		
	}
	
	public void addXmlData(LauExternalReferences lauExternalReferences) {
		reports.add(lauExternalReferences);
		log.info(lauExternalReferences.toString());
	}
	
	/**
	 	LAU_EXTERNAL_REFERENCES
	 	
	 	EXTERNAL_REFERENCE_ID     NOT NULL NUMBER                            
		REPORT_ID                 NOT NULL NUMBER                            
		DISPLAY_NUMBER                     NUMBER                            
		EXTERNAL_REFERENCE_TYPE            VARCHAR2(50)                      
		EXTERNAL_REFERENCE_NUMBER          VARCHAR2(300)                     
		EXTERNAL_REFERENCE_SOURCE          VARCHAR2(500)                     
		UPDATE_USER_ID                     VARCHAR2(300)                     
		UPDATE_TIMESTAMP                   TIMESTAMP(2) WITH LOCAL TIME ZONE
		
	 */

}

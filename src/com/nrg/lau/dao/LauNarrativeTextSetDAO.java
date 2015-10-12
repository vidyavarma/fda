package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauNarrativeText;
import com.nrg.lau.commons.IReportNarrativeDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauNarrativeTextSetDAO implements IReportNarrativeDAO<LauNarrativeText> {

	private static Logger log	= Logger.getLogger(LauNarrativeTextSetDAO.class);
	private Vector<LauNarrativeText> reports= null;
	private LauNarrativeText narrativeText 	= null;	
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	HashMap<String, Long> narrativeMap = new HashMap<String, Long>();
	
	protected void finalize() throws Throwable {		
		this.reports.removeAllElements();
		this.reports = null;
		super.finalize();
	}
	
	public void  save(HttpServletRequest request, SimpleJdbcTemplate template,			
			long reportId, String user, java.sql.Timestamp dstamp,
			HashMap<String, String> patientMap,
			HashMap<String, List<Long>> productMap) throws Exception {
		
	}
	
	public HashMap<String, Long> saveData(HttpServletRequest request, SimpleJdbcTemplate template,			
			long reportId, String user, java.sql.Timestamp dstamp,
			HashMap<String, String> patientMap,
			HashMap<String, List<Long>> productMap) throws Exception {		

		for (Map.Entry entry : patientMap.entrySet()) {
			log.info("key,val patient map----> " + entry.getKey() + ","
					+ entry.getValue());
		}

		userId = user;
		dt = dstamp;
		// Check to make sure that XML is there in Request.
		if (request
				.getParameter(IReportsConstants.LAU_NARRATIVE_TEXT_PARAM_NAME) != null
				&& request.getParameter(
						IReportsConstants.LAU_NARRATIVE_TEXT_PARAM_NAME)
						.length() > 0) {

			log.info("LauNarrativeTextDAO save() LAU_NARRATIVE_TEXT_PARAM_NAME -> "
					+ request
							.getParameter(IReportsConstants.LAU_NARRATIVE_TEXT_PARAM_NAME));

			// Create LauReports beans from XML Request
			createBeansFromXml(request
					.getParameter(IReportsConstants.LAU_NARRATIVE_TEXT_PARAM_NAME));
			Iterator<LauNarrativeText> itr = this.reports.iterator();
			
			// Report Id is 0 it's UPDATE else a new INSERT			
			while (itr.hasNext()) {				
				String patientDetailsInternalLinkId = "";
				String productInternalLinkId = "";
				String patientDetailsId = "";
				String productId = "";
				long prodId = 0;
				LauNarrativeText lauNarrativeText = (LauNarrativeText) itr
						.next();
				this.narrativeText = null;
				this.narrativeText = lauNarrativeText;
				
				log.info("INTERNAL_LINK_ID----->" + this.narrativeText.getINTERNAL_LINK_ID().trim());
				log.info("PATIENT INTERNAL_LINK_ID----->" + this.narrativeText.getPATIENT_INTERNAL_LINK_ID().trim());
				log.info("PRODUCT INTERNAL_LINK_ID----->" + this.narrativeText.getPRODUCT_INTERNAL_LINK_ID().trim());
				if(this.narrativeText.getPATIENT_INTERNAL_LINK_ID().trim()!=null){
					patientDetailsInternalLinkId = this.narrativeText.getPATIENT_INTERNAL_LINK_ID().trim();
				}
				if(this.narrativeText.getPRODUCT_INTERNAL_LINK_ID().trim()!=null){
					productInternalLinkId = this.narrativeText.getPRODUCT_INTERNAL_LINK_ID().trim();
				}
				if(this.narrativeText.getPatientDetailsId()!=null){
					patientDetailsId = this.narrativeText.getPatientDetailsId();
				}
				if(this.narrativeText.getProdID().trim()!=null){
					productId = this.narrativeText.getProdID();
				}
				if(patientDetailsId.equals("")){
					if(!patientDetailsInternalLinkId.equals("")){
						patientDetailsId = (String) patientMap.get(narrativeText.getPATIENT_INTERNAL_LINK_ID().trim());
					}
				}
				if(productId.equals("")){
					if(!productInternalLinkId.equals("")){						
						List<Long> list = productMap.get(narrativeText.getPRODUCT_INTERNAL_LINK_ID().trim());
						prodId = list.get(1);
						productId = Long.toString(prodId);						
					}
				}

				if (lauNarrativeText.getNarrativeTextId() == 0) {					
					narrativeMap = insertVal(template, reportId, patientDetailsId, productId);
				} else {
					if (lauNarrativeText.getTransactionType() == IReportsConstants.deleteFlag) {
						delete(template);
					} else
					    narrativeMap = updateData(template);
				}
			}// end of while(itr.hasNext())
		} else {
			log.info(IReportsConstants.LAU_NARRATIVE_TEXT_PARAM_NAME
					+ " not found in request");
		}
         return narrativeMap;
	}
		
	public HashMap<String, Long> insertVal(SimpleJdbcTemplate template, long reportId, String patientDetailsId, String prodId)
			throws Exception {	
		log.info("in insertVal-----> ");
		int id = 0;		
		long narrativeTextId = CommonDAO.getPrimaryKey(template);
		this.narrativeText.setNarrativeTextId(narrativeTextId);	
		this.narrativeText.setLogicalRecordId(narrativeTextId);	
		if (this.narrativeText.getPatientDetailsId().length() == 0) {
		this.narrativeText.setPatientDetailsId(patientDetailsId);
		}
		if (this.narrativeText.getProdID().length() == 0) {
			this.narrativeText.setProdID(prodId);	
		}
		log.info(this.narrativeText.getPatientDetailsId());
		if(this.narrativeText.getReportId() == 0)	{
			this.narrativeText.setReportId(reportId);	}
		log.info("Generated Primary Key for NARRATIVE_TEXT_ID ->" + narrativeTextId);
		
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("LauNarrativeTextDAO insert() ID -> " + id);
		
		log.info("INTERNAL_LINK_ID()----> " + this.narrativeText.getINTERNAL_LINK_ID());
		log.info("NarrativeTextId----> " + this.narrativeText.getNarrativeTextId());
		narrativeMap.put(this.narrativeText.getINTERNAL_LINK_ID(), this.narrativeText.getNarrativeTextId());
		return narrativeMap;
	}
	
	 
	/*public void insert(SimpleJdbcTemplate template, long reportId, String patientDetailsId, long productId)
			throws Exception {			
		int id = 0;		
		long narrativeTextId = CommonDAO.getPrimaryKey(template);
		this.narrativeText.setNarrativeTextId(narrativeTextId);	
		if (this.narrativeText.getPatientDetailsId().length() == 0) {
		this.narrativeText.setPatientDetailsId(patientDetailsId);
		}
		log.info(this.narrativeText.getPatientDetailsId());
		if(this.narrativeText.getReportId() == 0)	{
			this.narrativeText.setReportId(reportId);	}
		log.info("Generated Primary Key for NARRATIVE_TEXT_ID ->" + narrativeTextId);
		
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("LauNarrativeTextDAO insert() ID -> " + id);
		
	}*/
	
	
	public void update(SimpleJdbcTemplate template) throws Exception {
		
	}
	
	public HashMap<String, Long> updateData(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		this.narrativeText.setLogicalRecordId(this.narrativeText.getNarrativeTextId());		
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauNarrativeTextDAO update() ID -> " + id);	
		
		log.info("INTERNAL_LINK_ID() up----> " + this.narrativeText.getINTERNAL_LINK_ID());
		log.info("NarrativeTextId up----> " + this.narrativeText.getNarrativeTextId());
		
		narrativeMap.put(this.narrativeText.getINTERNAL_LINK_ID(), this.narrativeText.getNarrativeTextId());
		return narrativeMap;
	}
	
	private Object[] getParameters()
	{
		LauNarrativeText lauNarrativeText = this.narrativeText; 
		log.info(lauNarrativeText.getNarrativeTextType());		
		log.info(lauNarrativeText.getTextUpdatedFlag());
		log.info(lauNarrativeText.getNarrativeText());
		log.info(lauNarrativeText.getLangCode());
		log.info(lauNarrativeText.getProdID());
		log.info(lauNarrativeText.getContentFlag());
		log.info(lauNarrativeText.getReportId());
		log.info(lauNarrativeText.getNarrativeTextId());
		return new Object[]{
			  lauNarrativeText.getNarrativeTextType(),
			  lauNarrativeText.getPatientDetailsId(),
			  lauNarrativeText.getTextUpdatedFlag(),
			  lauNarrativeText.getNarrativeText(),
			  lauNarrativeText.getLangCode(),
			  lauNarrativeText.getProdID(),
			  lauNarrativeText.getContentFlag(),
			  userId,
			  dt,
			  lauNarrativeText.getLogicalRecordId(),
			  lauNarrativeText.getReportId(),
			  lauNarrativeText.getNarrativeTextId()
		};
	}
	
	
	public void createBeansFromXml(String xml) throws Exception {		
		String mainXmlTag = "ROWSET/ROW";
		reports				= new Vector<LauNarrativeText>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauNarrativeText.class );		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/NARRATIVE_TEXT_ID", "narrativeTextId" );
		digester.addBeanPropertySetter( mainXmlTag+"/PATIENT_DETAILS_ID", "patientDetailsId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/NARRATIVE_TEXT_TYPE", "narrativeTextType" );
		digester.addBeanPropertySetter( mainXmlTag+"/TEXT_UPDATED_FLAG", "textUpdatedFlag" );
		digester.addBeanPropertySetter( mainXmlTag+"/NARRATIVE_TEXT", "narrativeText" );
		digester.addBeanPropertySetter( mainXmlTag+"/LANGUAGE_CODE", "langCode" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_ID", "prodID" );
		digester.addBeanPropertySetter( mainXmlTag+"/PATIENT_INTERNAL_LINK_ID", "PATIENT_INTERNAL_LINK_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_INTERNAL_LINK_ID", "PRODUCT_INTERNAL_LINK_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/PUBLIC_CONTENT_FLAG", "contentFlag" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "updateUserId" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		//digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "updateTimeStamp" );
		digester.addBeanPropertySetter( mainXmlTag+"/INTERNAL_LINK_ID", "INTERNAL_LINK_ID");		
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );			
		digester.parse(new StringReader(xml));		
	}
	
	public void addXmlData(LauNarrativeText lauNarrativeText) {
		reports.add(lauNarrativeText);
		log.info(lauNarrativeText.toString());
	}	
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_NARRATIVE_TEXT SET NARRATIVE_TEXT_TYPE=?,PATIENT_DETAILS_ID=?," +
		"TEXT_UPDATED_FLAG=?,NARRATIVE_TEXT=?,LANGUAGE_CODE=?,PRODUCT_ID=?,PUBLIC_CONTENT_FLAG=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?,LOGICAL_RECORD_ID=? WHERE REPORT_ID=? AND NARRATIVE_TEXT_ID=?";
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_NARRATIVE_TEXT (NARRATIVE_TEXT_TYPE,PATIENT_DETAILS_ID," +
		"TEXT_UPDATED_FLAG,NARRATIVE_TEXT,LANGUAGE_CODE,PRODUCT_ID,PUBLIC_CONTENT_FLAG,UPDATE_USER_ID,UPDATE_TIMESTAMP,"
		+ "LOGICAL_RECORD_ID,REPORT_ID,NARRATIVE_TEXT_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_NARRATIVE_TEXT WHERE NARRATIVE_TEXT_ID = ?",
				new Object[]{this.narrativeText.getNarrativeTextId()});
		log.info("LauNarrativeTextSetDAO delete() ID -> " + id);		
	}


	@Override
	public void insert(SimpleJdbcTemplate template, long reportId,
			String patientDetailsId) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 	LAU_NARRATIVE_TEXT
	 	
	 	NARRATIVE_TEXT_ID NUMBER(22 , 0) NOT NULL,
		REPORT_ID NUMBER(22 , 0) NOT NULL,
		NARRATIVE_TEXT_TYPE VARCHAR2(100),
		TEXT_UPDATED_FLAG VARCHAR2(5),
		NARRATIVE_TEXT CLOB,
		UPDATE_USER_ID VARCHAR2(300) NOT NULL,
		UPDATE_TIMESTAMP date NOT NULL
	 
	 */

}

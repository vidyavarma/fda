package com.nrg.lau.dao;

import java.io.StringReader;
import java.sql.Timestamp;
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

public class LauLanguageNarrativeSetDAO implements IReportNarrativeDAO<LauNarrativeText> {
	private static Logger log	= Logger.getLogger(LauLanguageNarrativeSetDAO.class);
	private Vector<LauNarrativeText> reports= null;
	private LauNarrativeText narrativeLangText 	= null;	
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	HashMap<String, Long> narrativeMap = new HashMap<String, Long>();
	
	protected void finalize() throws Throwable {		
		this.reports.removeAllElements();
		this.reports = null;
		super.finalize();
	}
	
	//has to complete update and delete, 
	//also add logicalRecordId in the bean
    //patient and product code
	
	public void saveData(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, String user, Timestamp dstamp,
			HashMap<String, String> patientMap,
			HashMap<String, List<Long>> productMap, HashMap<String, Long> narrativeMap ) throws Exception {	
		String patientDetailsInternalLinkId = "";
		String productInternalLinkId = "";
		String patientDetailsId = "";
		String productId = "";
		long prodId = 0;
		userId = user;
		dt = dstamp;
		if (request
				.getParameter(IReportsConstants.LAU_NARRATIVE_LANG_PARAM_NAME) != null
				&& request.getParameter(
						IReportsConstants.LAU_NARRATIVE_LANG_PARAM_NAME)
						.length() > 0) {

			log.info("LauNarrativeTextDAO save() LAU_NARRATIVE_LANG_PARAM_NAME -------> "
					+ request
							.getParameter(IReportsConstants.LAU_NARRATIVE_LANG_PARAM_NAME));
			createBeansFromXml(request
					.getParameter(IReportsConstants.LAU_NARRATIVE_LANG_PARAM_NAME));
			Iterator<LauNarrativeText> itr = this.reports.iterator();
			while (itr.hasNext()) {	
				
				LauNarrativeText lauNarrativeLangText = (LauNarrativeText) itr
						.next();
				this.narrativeLangText = null;
				this.narrativeLangText = lauNarrativeLangText;				
				log.info("lauNarrativeLangText.getNarrativeTextId()---->"+ lauNarrativeLangText.getNarrativeTextId());
				
				//new conditions
				log.info("INTERNAL_LINK_ID----->" + this.narrativeLangText.getINTERNAL_LINK_ID().trim());
				log.info("PATIENT INTERNAL_LINK_ID----->" + this.narrativeLangText.getPATIENT_INTERNAL_LINK_ID().trim());
				log.info("PRODUCT INTERNAL_LINK_ID----->" + this.narrativeLangText.getPRODUCT_INTERNAL_LINK_ID().trim());
				if(this.narrativeLangText.getPATIENT_INTERNAL_LINK_ID().trim()!=null){
					patientDetailsInternalLinkId = this.narrativeLangText.getPATIENT_INTERNAL_LINK_ID().trim();
				}
				if(this.narrativeLangText.getPRODUCT_INTERNAL_LINK_ID().trim()!=null){
					productInternalLinkId = this.narrativeLangText.getPRODUCT_INTERNAL_LINK_ID().trim();
				}
				if(this.narrativeLangText.getPatientDetailsId()!=null){
					patientDetailsId = this.narrativeLangText.getPatientDetailsId();
				}
				if(this.narrativeLangText.getProdID()!=null){
					productId = this.narrativeLangText.getProdID().trim();
				}
				if(patientDetailsId.equals("")){
					if(!patientDetailsInternalLinkId.equals("")){
						patientDetailsId = (String) patientMap.get(narrativeLangText.getPATIENT_INTERNAL_LINK_ID().trim());
					}
				}
				if(productId.equals("")){
					if(!productInternalLinkId.equals("")){						
						List<Long> list = productMap.get(narrativeLangText.getPRODUCT_INTERNAL_LINK_ID().trim());
						prodId = list.get(1);
						productId = Long.toString(prodId);						
					}
				}
				
				log.info("patientDetailsId----->"+patientDetailsId);
				log.info("productId---->"+productId);
				
				if (lauNarrativeLangText.getNarrativeTextId() == 0) {					
					log.info("INTERNAL_LINK_ID in Language----->"
							+ this.narrativeLangText.getINTERNAL_LINK_ID()
									.trim());
				
					String intLinkID = this.narrativeLangText
							.getINTERNAL_LINK_ID().trim();					
					Long parntNarrTextID = narrativeMap.get(intLinkID);					
					insertVal(template, reportId, patientDetailsId, productId,	parntNarrTextID);					
				}else {
					if (lauNarrativeLangText.getTransactionType() == IReportsConstants.deleteFlag) {
						delete(template);
					} else{
						String intLinkID = this.narrativeLangText
								.getINTERNAL_LINK_ID().trim();
						Long parntNarrTextID = narrativeMap.get(intLinkID);
					    updateData(template, reportId, patientDetailsId, productId,	parntNarrTextID);
					}
				}
			}
		}
		
	}
	
	@Override
	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, String user, Timestamp dstamp,
			HashMap<String, String> patientMap,
			HashMap<String, List<Long>> productMap) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	

	@Override
	public void update(SimpleJdbcTemplate template) throws Exception {
		// TODO Auto-generated method stub
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauNarrativeTextDAO update() ID -> " + id);					
	}
	
	
	public void updateData(SimpleJdbcTemplate template, long reportId, String patientDetailsId, String prodId, Long parntNarrTextID) throws Exception {
		// TODO Auto-generated method stub
		int id = 0;			
		this.narrativeLangText.setLogicalRecordId(parntNarrTextID);	
		if (this.narrativeLangText.getPatientDetailsId().length() == 0) {
			this.narrativeLangText.setPatientDetailsId(patientDetailsId);
			}
			if (this.narrativeLangText.getProdID().length() == 0) {
				this.narrativeLangText.setProdID(prodId);	
			}
		if(this.narrativeLangText.getReportId() == 0)	{
			this.narrativeLangText.setReportId(reportId);	}
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauNarrativeTextDAO update() ID -> " + id);					
	}
	
	public void insertVal(SimpleJdbcTemplate template, long reportId, String patientDetailsId, String prodId, Long parntNarrTextID)
			throws Exception {		
		int id = 0;	
		long narrativeTextId = CommonDAO.getPrimaryKey(template);		
		this.narrativeLangText.setNarrativeTextId(narrativeTextId);		
		this.narrativeLangText.setLogicalRecordId(parntNarrTextID);	
		if (this.narrativeLangText.getPatientDetailsId().length() == 0) {
			this.narrativeLangText.setPatientDetailsId(patientDetailsId);
			}
			if (this.narrativeLangText.getProdID().length() == 0) {
				this.narrativeLangText.setProdID(prodId);	
			}
		if(this.narrativeLangText.getReportId() == 0)	{
			this.narrativeLangText.setReportId(reportId);	}
		id = template.update(SQL_INSERT_STRING,getParameters());		
		log.info("LauNarrativeTextDAO insert() ID -> " + id);
	}

	@Override
	public void insert(SimpleJdbcTemplate template, long reportId,
			String patientDetailsId) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	private Object[] getParameters()
	{
		LauNarrativeText lauNarrativeText = this.narrativeLangText; 
		log.info("NarrativeTextType()aaa-------->"+lauNarrativeText.getNarrativeTextType());		
		log.info("PatientDetailsId-------->"+lauNarrativeText.getPatientDetailsId());
		log.info("TextUpdatedFlag-------->"+lauNarrativeText.getTextUpdatedFlag());
		log.info("NarrativeText-------->"+lauNarrativeText.getNarrativeText());
		log.info("LangCode-------->"+lauNarrativeText.getLangCode());
		log.info("ProdID-------->"+lauNarrativeText.getProdID());
		log.info("ContentFlag-------->"+lauNarrativeText.getContentFlag());
		log.info("userId-------->"+userId);
		log.info("dt-------->"+dt);
		log.info("LogicalRecordId-------->"+lauNarrativeText.getLogicalRecordId());
		log.info("ReportId-------->"+lauNarrativeText.getReportId());
		log.info("NarrativeTextId-------->"+lauNarrativeText.getNarrativeTextId());
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
	

	private final String SQL_UPDATE_STRING = "UPDATE LAU_NARRATIVE_TEXT SET NARRATIVE_TEXT_TYPE=?,PATIENT_DETAILS_ID=?," +
			"TEXT_UPDATED_FLAG=?,NARRATIVE_TEXT=?,LANGUAGE_CODE=?,PRODUCT_ID=?,PUBLIC_CONTENT_FLAG=?,UPDATE_USER_ID=?,"
			+ "UPDATE_TIMESTAMP=?, LOGICAL_RECORD_ID=? WHERE REPORT_ID=? AND NARRATIVE_TEXT_ID=?";
	
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_NARRATIVE_TEXT (NARRATIVE_TEXT_TYPE,PATIENT_DETAILS_ID," +
			"TEXT_UPDATED_FLAG,NARRATIVE_TEXT,LANGUAGE_CODE,PRODUCT_ID,PUBLIC_CONTENT_FLAG,"
			+ "UPDATE_USER_ID,UPDATE_TIMESTAMP,LOGICAL_RECORD_ID, REPORT_ID,NARRATIVE_TEXT_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

	@Override
	public void createBeansFromXml(String xml) throws Exception {
		// TODO Auto-generated method stub
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
	
	public void addXmlData(LauNarrativeText lauNarrativeLangText) {		
		reports.add(lauNarrativeLangText);
		log.info(lauNarrativeLangText.toString());
	}

	@Override
	public void delete(SimpleJdbcTemplate template) throws Exception {
		// TODO Auto-generated method stub
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_NARRATIVE_TEXT WHERE NARRATIVE_TEXT_ID = ?",
				new Object[]{this.narrativeLangText.getNarrativeTextId()});
		log.info("LauLanguageNarrativeSetDAO delete() ID -> " + id);
	}
}

package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauMedicalHistory;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauMedicalHistorySetDAO implements IReportChildSetDAO<LauMedicalHistory> {

	private static Logger log	= Logger.getLogger(LauMedicalHistorySetDAO.class);
	private Vector<LauMedicalHistory> reports	= null;
	private LauMedicalHistory medicalHistory 	= null;	
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	
	protected void finalize() throws Throwable {		
		this.reports.removeAllElements();
		this.reports = null;
		super.finalize();
	}
	
	
	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId,String user,java.sql.Timestamp dstamp) throws Exception {
		userId = user;
		dt = dstamp;
		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_MEDICAL_HISTORY_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_MEDICAL_HISTORY_PARAM_NAME).length() > 0) {
			
			log.info("LauMedicalHistoryDAO save() LAU_MEDICAL_HISTORY_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_MEDICAL_HISTORY_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_MEDICAL_HISTORY_PARAM_NAME));
			Iterator<LauMedicalHistory> itr = this.reports.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauMedicalHistory lauMedicalHistory = (LauMedicalHistory)itr.next();
				log.info("itr.hasNext() -> " + lauMedicalHistory.toString());
				this.medicalHistory	= null;
				this.medicalHistory	= lauMedicalHistory;
				if(lauMedicalHistory.getMedicalHistoryId() == 0)	{
						insert(template,reportId);
				}else  {
					if(lauMedicalHistory.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else	update(template);				
				}
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_MEDICAL_HISTORY_PARAM_NAME + " not found in request");
		}
		
	}
	
	
	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {		
		int id = 0;
		long medicalHistoryId = CommonDAO.getPrimaryKey(template);
		this.medicalHistory.setMedicalHistoryId(medicalHistoryId);
		if(this.medicalHistory.getReportId() == 0)	{
			this.medicalHistory.setReportId(reportId);	}
		log.info("Generated Primary Key for MEDICAL_HISTORY_ID ->" + medicalHistoryId);
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("LauMedicalHistoryDAO insert() ID -> " + id);
		
	}

	
	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauMedicalHistoryDAO update() ID -> " + id);	
		
	}
	
	private Object[] getParameters()
	{
		LauMedicalHistory lauMedicalHistory = this.medicalHistory; 
		return new Object[]{
			lauMedicalHistory.getMedicalHistoryVerbatim(),
			lauMedicalHistory.getDisplayNumber(),
			lauMedicalHistory.getOnsetDate(),
			lauMedicalHistory.getOngoingFlag(),
			lauMedicalHistory.getEndDate(),
			userId,
			dt,
			lauMedicalHistory.getReportId(),
			lauMedicalHistory.getMedicalHistoryId()
		};
	}
	
	
	public void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag = "ROWSET/ROW";
		reports				= new Vector<LauMedicalHistory>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauMedicalHistory.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/MEDICAL_HISTORY_ID", "medicalHistoryId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/MEDICAL_HISTORY_VERBATIM", "medicalHistoryVerbatim" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISPLAY_NUMBER", "displayNumber2" );
		digester.addBeanPropertySetter( mainXmlTag+"/ONSET_DATE", "onsetDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/ONGOING_FLAG", "ongoingFlag" );
		digester.addBeanPropertySetter( mainXmlTag+"/END_DATE", "endDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "updateUserId" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		//digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "updateTimeStamp" );
				
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));
		
	}
	
	public void addXmlData(LauMedicalHistory lauMedicalHistory) {
		reports.add(lauMedicalHistory);
		log.info(lauMedicalHistory.toString());
	}	
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_MEDICAL_HISTORY SET MEDICAL_HISTORY_VERBATIM=?,DISPLAY_NUMBER=?," +
		"ONSET_DATE=?,ONGOING_FLAG=?,END_DATE=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE REPORT_ID=? AND MEDICAL_HISTORY_ID=?";
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_MEDICAL_HISTORY (MEDICAL_HISTORY_VERBATIM,DISPLAY_NUMBER," +
		"ONSET_DATE,ONGOING_FLAG,END_DATE,UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID,MEDICAL_HISTORY_ID) VALUES " +
		"(?,?,?,?,?,?,?,?,?)";

	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_MEDICAL_HISTORY WHERE MEDICAL_HISTORY_ID = ?",
				new Object[]{this.medicalHistory.getMedicalHistoryId()});
		log.info("LauMedicalHistorySetDAO delete() ID -> " + id);			
	}
	
	/**
	 	
	 	LAU_MEDICAL_HISTORY
	 	
	 	MEDICAL_HISTORY_ID NUMBER(22 , 0) NOT NULL,
		REPORT_ID NUMBER(22 , 0) NOT NULL,
		MEDICAL_HISTORY_VERBATIM VARCHAR2(1500),
		DISPLAY_NUMBER NUMBER(22 , 0),
		ONSET_DATE VARCHAR2(24),
		ONGOING_FLAG VARCHAR2(50),
		END_DATE VARCHAR2(24),
		UPDATE_USER_ID VARCHAR2(300) NOT NULL,
		UPDATE_TIMESTAMP date NOT NULL
		
	 */
}

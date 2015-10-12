package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauActivityLog;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauActivityLogSetDAO implements IReportChildSetDAO<LauActivityLog>{

	private static Logger log	= Logger.getLogger(LauActivityLogSetDAO.class);
	private Vector<LauActivityLog> reports	= null;
	private LauActivityLog lauActivityLog 		= null;	
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
		if(request.getParameter(IReportsConstants.LAU_ACTIVITY_LOG_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_ACTIVITY_LOG_PARAM_NAME).length() > 0) {
			
			log.info("lauActivityLogDAO save() LAU_ACTIVITY_LOG_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_ACTIVITY_LOG_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_ACTIVITY_LOG_PARAM_NAME));
			Iterator<LauActivityLog> itr = this.reports.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauActivityLog lauActivityLog = (LauActivityLog)itr.next();
				log.info("itr.hasNext() -> " + lauActivityLog.toString());
				this.lauActivityLog	= null;
				this.lauActivityLog	= lauActivityLog;
				if(lauActivityLog.getActivityLogId() == 0)	{
						insert(template,reportId);
				}else  {
					if(lauActivityLog.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else	update(template);				
				}
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_ACTIVITY_LOG_PARAM_NAME + " not found in request");
		}
		
	}
	
	
	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {		
		int id = 0;
		long activityLogId = CommonDAO.getPrimaryKey(template);
		this.lauActivityLog.setActivityLogId(activityLogId);
		if(this.lauActivityLog.getReportId() == 0)	{
			this.lauActivityLog.setReportId(reportId);	}
		log.info("Generated Primary Key for ACTIVITY_LOG_ID ->" + activityLogId);
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("lauActivityLogDAO insert() ID -> " + id);
		
	}

	
	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("lauActivityLogDAO update() ID -> " + id);	
		
	}
	
	private Object[] getParameters()
	{
		LauActivityLog lauActivityLog = this.lauActivityLog; 
		return new Object[]{
			lauActivityLog.getActivityType(),
			lauActivityLog.getReportUpdateReasonCode(),
			lauActivityLog.getReportUpdateReasonDesc (),
			userId,
			dt,
			lauActivityLog.getReportId(),
			lauActivityLog.getActivityLogId()
		};
	}

	
	public void createBeansFromXml(String xml) throws Exception {
		
		reports				= new Vector<LauActivityLog>();
		Digester digester	= new Digester();
		digester.push(this);
		String mainXmlTag = "ROWSET/ROW";
		digester.addObjectCreate( mainXmlTag, LauActivityLog.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/ACTIVITY_LOG_ID", "activityLogId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/ACTIVITY_TYPE", "activityType" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_UPDATE_REASON_CODE", "reportUpdateReasonCode" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_UPDATE_REASON_DESC", "reportUpdateReasonDesc" );		
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "updateUserId" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		//digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "updateTimeStamp" );
				
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));
		
	}
	
	public void addXmlData(LauActivityLog lauActivityLog) {
		reports.add(lauActivityLog);
		log.info(lauActivityLog.toString());
	}	
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_REPORT_ACTIVITY_LOG SET ACTIVITY_TYPE=?,REPORT_UPDATE_REASON_CODE=?," +
			"REPORT_UPDATE_REASON_CODE=?,REPORT_UPDATE_REASON_DESC=?," +
			"UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE REPORT_ID=? AND ACTIVITY_LOG_ID=?";
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_REPORT_ACTIVITY_LOG(ACTIVITY_TYPE," +
		"REPORT_UPDATE_REASON_CODE,REPORT_UPDATE_REASON_DESC," +
		"UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID,ACTIVITY_LOG_ID) VALUES (?,?,?,?,?,?,?)";

	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_REPORT_ACTIVITY_LOG WHERE ACTION_ITEM_ID = ?",
				new Object[]{this.lauActivityLog.getActivityLogId()});
		log.info("lauActivityLogSetDAO delete() ID -> " + id);		
	}
	
	/**
	 * activityLogId
activityType 
reportUpdateReasonCode 
reportUpdateReasonDesc 
reportId 
updateUserId           
updateTimeStamp
	 "LAU_REPORT_ACTIVITY_LOG"
	  (
	    "ACTIVITY_LOG_ID"           NUMBER NOT NULL ENABLE,
	    "REPORT_ID"                 NUMBER NOT NULL ENABLE,
	    "ACTIVITY_TYPE"             VARCHAR2(50 BYTE) NOT NULL ENABLE,
	    "REPORT_UPDATE_REASON_CODE" VARCHAR2(50 BYTE),
	    "REPORT_UPDATE_REASON_DESC" VARCHAR2(500 BYTE),
	    "UPDATE_USER_ID"            VARCHAR2(300 BYTE) NOT NULL ENABLE,
	    "UPDATE_TIMESTAMP" TIMESTAMP (0)**/

}

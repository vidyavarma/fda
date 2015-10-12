package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauPregnancyHistory;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauPregnancyHistorySetDAO implements IReportChildSetDAO<LauPregnancyHistory> {
	
	private static Logger log	= Logger.getLogger(LauPregnancyHistorySetDAO.class);
	private Vector<LauPregnancyHistory> reports		= null;
	private LauPregnancyHistory pregnancyHistory 	= null;	
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
		if(request.getParameter(IReportsConstants.LAU_PREGNANCY_HISTORY_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_PREGNANCY_HISTORY_PARAM_NAME).length() > 0) {
			
			log.info("LauPregnancyHistoryDAO save() LAU_PREGNANCY_HISTORY_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_PREGNANCY_HISTORY_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_PREGNANCY_HISTORY_PARAM_NAME));
			Iterator<LauPregnancyHistory> itr = this.reports.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauPregnancyHistory lauPregnancyHistory = (LauPregnancyHistory)itr.next();
				log.info("itr.hasNext() -> " + lauPregnancyHistory.toString());
				this.pregnancyHistory	= null;
				this.pregnancyHistory	= lauPregnancyHistory;
				if(lauPregnancyHistory.getPregnancyHistoryId() == 0	)	{
						insert(template,reportId);
				}else  {
					if(lauPregnancyHistory.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else	update(template);				
				}
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_PREGNANCY_HISTORY_PARAM_NAME + " not found in request");
		}
		
	}
	
	
	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {
		int id = 0;
		long pregnancyHistoryId = CommonDAO.getPrimaryKey(template);
		this.pregnancyHistory.setPregnancyHistoryId(pregnancyHistoryId);
		if(this.pregnancyHistory.getReportId() == 0)	{
			this.pregnancyHistory.setReportId(reportId);	}
		log.info("Generated Primary Key for PREGNANCY_HISTORY_ID ->" + pregnancyHistoryId);
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("LauPregnancyHistoryDAO insert() ID -> " + id);
		
	}

	
	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauPregnancyHistoryDAO update() ID -> " + id);	
		
	}
	
	private Object[] getParameters()
	{
		LauPregnancyHistory lauPregnancyHistory = this.pregnancyHistory; 
		return new Object[]{
				lauPregnancyHistory.getDisplayNumber(),
				lauPregnancyHistory.getPregnancyHistoryTerm(),
				lauPregnancyHistory.getPregnancyHistoryComment(),
				userId,
				dt,
				lauPregnancyHistory.getReportId(),
				lauPregnancyHistory.getPregnancyHistoryId()
		};
	}
	
	
	public void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag = "ROWSET/ROW";
		reports				= new Vector<LauPregnancyHistory>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauPregnancyHistory.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/PREGNANCY_HISTORY_ID", "pregnancyHistoryId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISPLAY_NUMBER", "displayNumber2" );
		digester.addBeanPropertySetter( mainXmlTag+"/PREGNANCY_HISTORY_TERM", "pregnancyHistoryTerm" );
		digester.addBeanPropertySetter( mainXmlTag+"/PREGNANCY_HISTORY_COMMENT", "pregnancyHistoryComment" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "updateUserId" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		//digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "updateTimeStamp" );
				
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));
		
	}
	
	public void addXmlData(LauPregnancyHistory lauPregnancyHistory) {
		reports.add(lauPregnancyHistory);
		log.info(lauPregnancyHistory.toString());
	}	
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_PREGNANCY_HISTORY SET DISPLAY_NUMBER=?,PREGNANCY_HISTORY_TERM=?," +
		"PREGNANCY_HISTORY_COMMENT=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE REPORT_ID=? AND PREGNANCY_HISTORY_ID=?";
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_PREGNANCY_HISTORY (DISPLAY_NUMBER,PREGNANCY_HISTORY_TERM," +
		"PREGNANCY_HISTORY_COMMENT,UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID,PREGNANCY_HISTORY_ID) VALUES(?,?,?,?,?,?,?)";

	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_PREGNANCY_HISTORY WHERE PREGNANCY_HISTORY_ID = ?",
				new Object[]{this.pregnancyHistory.getPregnancyHistoryId()});
		log.info("LauPregnancyHistorySetDAO delete() ID -> " + id);			
	}
	
	/**
	 	LAU_PREGNANCY_HISTORY 
	 
	 	PREGNANCY_HISTORY_ID NUMBER(22 , 0) NOT NULL,
		REPORT_ID NUMBER(22 , 0) NOT NULL,
		DISPLAY_NUMBER NUMBER(22 , 0),
		PREGNANCY_HISTORY_TERM VARCHAR2(100),
		PREGNANCY_HISTORY_COMMENT VARCHAR2(4000),
		UPDATE_USER_ID VARCHAR2(300) NOT NULL,
		UPDATE_TIMESTAMP date NOT NULL
	 
	 */

}

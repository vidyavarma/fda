package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauRelatedReports;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauRelatedReportsSetDAO implements IReportChildSetDAO<LauRelatedReports>{
	
	private static Logger log	= Logger.getLogger(LauRelatedReportsSetDAO.class);
	private Vector<LauRelatedReports> reports	= null;
	private LauRelatedReports relatedReports 	= null;	
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
		if(request.getParameter(IReportsConstants.LAU_RELATED_REPORTS_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_RELATED_REPORTS_PARAM_NAME).length() > 0) {
			
			log.info("LauRelatedReportsDAO save() LAU_RELATED_REPORTS_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_RELATED_REPORTS_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_RELATED_REPORTS_PARAM_NAME));
			Iterator<LauRelatedReports> itr = this.reports.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauRelatedReports lauRelatedReports = (LauRelatedReports)itr.next();
				log.info("itr.hasNext() -> " + lauRelatedReports.toString());
				this.relatedReports	= null;
				this.relatedReports	= lauRelatedReports;
				if(lauRelatedReports.getRelatedReportId() == 0	)	{
						insert(template,reportId);
				}else   {
					if(lauRelatedReports.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else	update(template);				
				}
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_RELATED_REPORTS_PARAM_NAME + " not found in request");
		}
		
	}

	
	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {
		int id = 0;
		long relatedReportId = CommonDAO.getPrimaryKey(template);
		this.relatedReports.setRelatedReportId(relatedReportId);
		if(this.relatedReports.getReportId() == 0)	{
			this.relatedReports.setReportId(reportId);	}
		log.info("Generated Primary Key for RELATED_REPORT_ID ->" + relatedReportId);
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("LauRelatedReportsDAO insert() ID -> " + id);
		
	}

	
	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauRelatedReportsDAO update() ID -> " + id);
		
	}
	
	private Object[] getParameters()
	{
		LauRelatedReports lauRelatedReports = this.relatedReports; 
		return new Object[]{
			lauRelatedReports.getRelatedReport(),
			lauRelatedReports.getRelationReason(),
			userId,
			dt,
			lauRelatedReports.getReportId(),
			lauRelatedReports.getRelatedReportId()				
		};
	}
	
	
	public void createBeansFromXml(String xml) throws Exception {
		String mainXmlTag = "ROWSET/ROW";
		reports				= new Vector<LauRelatedReports>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauRelatedReports.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/RELATED_REPORT_ID", "relatedReportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/RELATED_REPORT", "relatedReport" );
		digester.addBeanPropertySetter( mainXmlTag+"/RELATION_REASON", "relationReason" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "updateUserId" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		//digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "updateTimeStamp" );
						
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));		
		
	}
	
	public void addXmlData(LauRelatedReports lauRelatedReports) {
		reports.add(lauRelatedReports);
		log.info(lauRelatedReports.toString());
	}
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_RELATED_REPORTS SET RELATED_REPORT=?,RELATION_REASON=?," +
		"UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE REPORT_ID=? AND RELATED_REPORT_ID=?";
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_RELATED_REPORTS (RELATED_REPORT,RELATION_REASON," +
		"UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID,RELATED_REPORT_ID) VALUES (?,?,?,?,?,?)";

	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_RELATED_REPORTS WHERE RELATED_REPORT_ID = ?",
				new Object[]{this.relatedReports.getRelatedReportId()});
		log.info("LauRelatedReportsSetDAO delete() ID -> " + id);		
	}	
	
	/**
	 	
	 	LAU_RELATED_REPORTS
	 	
	 	RELATED_REPORT_ID NUMBER(22 , 0) NOT NULL,
		REPORT_ID NUMBER(22 , 0) NOT NULL,
		RELATED_REPORT VARCHAR2(1500),
		RELATION_REASON VARCHAR2(50),
		UPDATE_USER_ID VARCHAR2(300) NOT NULL,
		UPDATE_TIMESTAMP date NOT NULL
	 	
	 */
}

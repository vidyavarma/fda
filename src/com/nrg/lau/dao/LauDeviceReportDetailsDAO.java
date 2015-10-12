package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauDeviceReportDetails;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauDeviceReportDetailsDAO implements IReportChildSetDAO<LauDeviceReportDetails>{

	private static Logger log	= Logger.getLogger(LauDeviceReportDetailsDAO.class);
	private Vector<LauDeviceReportDetails> reports	= null;
	private LauDeviceReportDetails deviceReportDetails 		= null;	
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
		if(request.getParameter(IReportsConstants.LAU_DEVICE_REPORT_DETAILS_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_DEVICE_REPORT_DETAILS_PARAM_NAME).length() > 0) {
			
			log.info("LauDeviceReportDetailsDAO save() LAU_DEVICE_REPORT_DETAILS_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_DEVICE_REPORT_DETAILS_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_DEVICE_REPORT_DETAILS_PARAM_NAME));
			Iterator<LauDeviceReportDetails> itr = this.reports.iterator();
			
			//DEVICE_REPORT_ID is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				//log.info("deviceReportDetails.getTransactionType(): "+ deviceReportDetails.getTransactionType());
				LauDeviceReportDetails lauDeviceReportDetails = (LauDeviceReportDetails)itr.next();
				log.info("itr.hasNext() -> " + lauDeviceReportDetails.toString());
				this.deviceReportDetails	= null;
				this.deviceReportDetails	= lauDeviceReportDetails;
				if(deviceReportDetails.getDEVICE_REPORT_ID() == 0)	{
						insert(template,reportId);
				}else  {
					if(deviceReportDetails.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else if(deviceReportDetails.getTransactionType() == 2){
						update(template);				
					}
				}
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_DEVICE_REPORT_DETAILS_PARAM_NAME + " not found in request");
		}
		
	}
	
	
	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {		
		int id = 0;
		long deviceReportDetailsId = CommonDAO.getPrimaryKey(template);
		this.deviceReportDetails.setDEVICE_REPORT_ID(deviceReportDetailsId);
		if(this.deviceReportDetails.getREPORT_ID() == 0)	{
			this.deviceReportDetails.setREPORT_ID(reportId);	}
		log.info("Generated Primary Key for DEVICE_REPORT_ID ->" + deviceReportDetailsId);
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("LauDeviceReportDetailsDAO insert() ID -> " + id);
		
	}

	
	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauDeviceReportDetailsDAO update() ID -> " + id);	
		
	}
	
	private Object[] getParameters()
	{
		LauDeviceReportDetails lauDeviceReportDetails = this.deviceReportDetails; 
		return new Object[]{
			lauDeviceReportDetails.getEVENT_LOCATION(),
			lauDeviceReportDetails.getEVENT_LOCATION_OTHER(),
			lauDeviceReportDetails.getREPORT_SENT_TO_FDA(),
			lauDeviceReportDetails.getREPORT_SENT_TO_FDA_DATE(),
			lauDeviceReportDetails.getREPORT_SENT_TO_MFG(),
			lauDeviceReportDetails.getREPORT_SENT_TO_MFG_DATE(),
			lauDeviceReportDetails.getREPORT_RECIPIENT_CODE(),
			lauDeviceReportDetails.getREPORT_SENT_TO_USER_FACILITY(),
			lauDeviceReportDetails.getREPORT_SENT_TO_DIST(),
			lauDeviceReportDetails.getDISTRIBUTOR_AWARE_DATE(),
			lauDeviceReportDetails.getDISTRIBUTOR_REPORT_NUMBER(),
			lauDeviceReportDetails.getDISTRIBUTOR_REPORT_TYPE(),
			lauDeviceReportDetails.getDISTRIBUTOR_FOLLOWUP_NO(),
			lauDeviceReportDetails.getDISTRIBUTOR_REPORT_DATE(),
			lauDeviceReportDetails.getDEV_REPORT_TYPE_DEATH(),
			lauDeviceReportDetails.getDEV_REPORT_TYPE_INJURY(),
			lauDeviceReportDetails.getDEV_REPORT_TYPE_MALFUNCTION(),
			lauDeviceReportDetails.getDEV_REPORT_TYPE_OTHER(),
			lauDeviceReportDetails.getDEV_REPORT_TYPE_OTHER_SPECIFY(),
			lauDeviceReportDetails.getNUMBER_EXPOSED(),
			lauDeviceReportDetails.getNUMBER_ADVERSELY_AFFECTED(),
			lauDeviceReportDetails.getNUMBER_INVOLVED_UNEXPOSED(),
			lauDeviceReportDetails.getNUMBER_POTENTIALLY_EXPOSED(),
			lauDeviceReportDetails.getAFFECTED_EMPLOYEE_FLAG(),
			lauDeviceReportDetails.getAFFECTED_OPERATOR_FLAG(),
			lauDeviceReportDetails.getCUSTOM_TEXT_01(),
			lauDeviceReportDetails.getCUSTOM_TEXT_02(),
			lauDeviceReportDetails.getCUSTOM_TEXT_03(),
			lauDeviceReportDetails.getCUSTOM_TEXT_04(),
			lauDeviceReportDetails.getCUSTOM_TEXT_05(),
			lauDeviceReportDetails.getCUSTOM_TEXT_06(),
			lauDeviceReportDetails.getCUSTOM_TEXT_07(),
			lauDeviceReportDetails.getCUSTOM_TEXT_08(),
			lauDeviceReportDetails.getCUSTOM_TEXT_09(),
			lauDeviceReportDetails.getCUSTOM_TEXT_10(),
			lauDeviceReportDetails.getCUSTOM_DATE_01(),
			lauDeviceReportDetails.getCUSTOM_DATE_02(),
			lauDeviceReportDetails.getCUSTOM_DATE_03(),
			lauDeviceReportDetails.getCUSTOM_DATE_04(),
			lauDeviceReportDetails.getCUSTOM_DATE_05(),
			lauDeviceReportDetails.getCUSTOM_COMMENT_01(),
			lauDeviceReportDetails.getCUSTOM_COMMENT_02(),
			userId,
			dt,
			lauDeviceReportDetails.getDEVICE_REPORT_ID(),
			lauDeviceReportDetails.getREPORT_ID()			
		};
	}
	
	
	public void createBeansFromXml(String xml) throws Exception {
		
		reports				= new Vector<LauDeviceReportDetails>();
		Digester digester	= new Digester();
		digester.push(this);
		String mainXmlTag = "ROWSET/ROW";
		digester.addObjectCreate( mainXmlTag, LauDeviceReportDetails.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/DEVICE_REPORT_ID", "DEVICE_REPORT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "REPORT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/EVENT_LOCATION", "EVENT_LOCATION" );
		digester.addBeanPropertySetter( mainXmlTag+"/EVENT_LOCATION_OTHER", "EVENT_LOCATION_OTHER" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_SENT_TO_FDA", "REPORT_SENT_TO_FDA" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_SENT_TO_FDA_DATE", "REPORT_SENT_TO_FDA_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_SENT_TO_MFG", "REPORT_SENT_TO_MFG" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_SENT_TO_MFG_DATE", "REPORT_SENT_TO_MFG_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_RECIPIENT_CODE", "REPORT_RECIPIENT_CODE" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_SENT_TO_USER_FACILITY", "REPORT_SENT_TO_USER_FACILITY" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_SENT_TO_DIST", "REPORT_SENT_TO_DIST" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISTRIBUTOR_AWARE_DATE", "DISTRIBUTOR_AWARE_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISTRIBUTOR_REPORT_NUMBER", "DISTRIBUTOR_REPORT_NUMBER" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISTRIBUTOR_REPORT_TYPE", "DISTRIBUTOR_REPORT_TYPE" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISTRIBUTOR_FOLLOWUP_NO", "DISTRIBUTOR_FOLLOWUP_NO" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISTRIBUTOR_REPORT_DATE", "DISTRIBUTOR_REPORT_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEV_REPORT_TYPE_DEATH", "DEV_REPORT_TYPE_DEATH" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEV_REPORT_TYPE_INJURY", "DEV_REPORT_TYPE_INJURY" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEV_REPORT_TYPE_MALFUNCTION", "DEV_REPORT_TYPE_MALFUNCTION" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEV_REPORT_TYPE_OTHER", "DEV_REPORT_TYPE_OTHER" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEV_REPORT_TYPE_OTHER_SPECIFY", "DEV_REPORT_TYPE_OTHER_SPECIFY" );
		digester.addBeanPropertySetter( mainXmlTag+"/NUMBER_EXPOSED", "NUMBER_EXPOSED" );
		digester.addBeanPropertySetter( mainXmlTag+"/NUMBER_ADVERSELY_AFFECTED", "NUMBER_ADVERSELY_AFFECTED" );
		digester.addBeanPropertySetter( mainXmlTag+"/NUMBER_INVOLVED_UNEXPOSED", "NUMBER_INVOLVED_UNEXPOSED" );
		digester.addBeanPropertySetter( mainXmlTag+"/NUMBER_POTENTIALLY_EXPOSED", "NUMBER_POTENTIALLY_EXPOSED" );
		digester.addBeanPropertySetter( mainXmlTag+"/AFFECTED_EMPLOYEE_FLAG", "AFFECTED_EMPLOYEE_FLAG" );
		digester.addBeanPropertySetter( mainXmlTag+"/AFFECTED_OPERATOR_FLAG", "AFFECTED_OPERATOR_FLAG" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_TEXT_01", "CUSTOM_TEXT_01" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_TEXT_02", "CUSTOM_TEXT_02" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_TEXT_03", "CUSTOM_TEXT_03" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_TEXT_04", "CUSTOM_TEXT_04" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_TEXT_05", "CUSTOM_TEXT_05" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_TEXT_06", "CUSTOM_TEXT_06" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_TEXT_07", "CUSTOM_TEXT_07" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_TEXT_08", "CUSTOM_TEXT_08" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_TEXT_09", "CUSTOM_TEXT_09" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_TEXT_10", "CUSTOM_TEXT_10" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_DATE_01", "CUSTOM_DATE_01" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_DATE_02", "CUSTOM_DATE_02" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_DATE_03", "CUSTOM_DATE_03" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_DATE_04", "CUSTOM_DATE_04" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_DATE_05", "CUSTOM_DATE_05" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_COMMENT_01", "CUSTOM_COMMENT_01" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_COMMENT_02", "CUSTOM_COMMENT_02" );
	//	digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "UPDATE_USER_ID" );
	//	digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "UPDATE_TIMESTAMP" );	
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		digester.addBeanPropertySetter( mainXmlTag+"/INTERNAL_LINK_ID", "INTERNAL_LINK_ID");
				
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));
		
	}
	
	public void addXmlData(LauDeviceReportDetails lauDeviceReportDetails) {
		reports.add(lauDeviceReportDetails);
		log.info(lauDeviceReportDetails.toString());
	}	
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_DEVICE_REPORT_DETAILS SET EVENT_LOCATION=?,EVENT_LOCATION_OTHER=?,REPORT_SENT_TO_FDA=?," +
	"REPORT_SENT_TO_FDA_DATE=?,REPORT_SENT_TO_MFG=?,REPORT_SENT_TO_MFG_DATE=?,REPORT_RECIPIENT_CODE=?,REPORT_SENT_TO_USER_FACILITY=?,REPORT_SENT_TO_DIST=?," +
	"DISTRIBUTOR_AWARE_DATE=?,DISTRIBUTOR_REPORT_NUMBER=?,DISTRIBUTOR_REPORT_TYPE=?,DISTRIBUTOR_FOLLOWUP_NO=?,DISTRIBUTOR_REPORT_DATE=?," +
	"DEV_REPORT_TYPE_DEATH=?,DEV_REPORT_TYPE_INJURY=?,DEV_REPORT_TYPE_MALFUNCTION=?,DEV_REPORT_TYPE_OTHER=?,DEV_REPORT_TYPE_OTHER_SPECIFY=?," +
	"NUMBER_EXPOSED=?,NUMBER_ADVERSELY_AFFECTED=?,NUMBER_INVOLVED_UNEXPOSED=?,NUMBER_POTENTIALLY_EXPOSED=?,AFFECTED_EMPLOYEE_FLAG=?,AFFECTED_OPERATOR_FLAG=?,CUSTOM_TEXT_01=?,CUSTOM_TEXT_02=?," +
	"CUSTOM_TEXT_03=?,CUSTOM_TEXT_04=?,CUSTOM_TEXT_05=?,CUSTOM_TEXT_06=?,CUSTOM_TEXT_07=?,CUSTOM_TEXT_08=?,CUSTOM_TEXT_09=?,CUSTOM_TEXT_10=?," +
	"CUSTOM_DATE_01=?,CUSTOM_DATE_02=?,CUSTOM_DATE_03=?,CUSTOM_DATE_04=?,CUSTOM_DATE_05=?,CUSTOM_COMMENT_01=?,CUSTOM_COMMENT_02=?," +
	"UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE DEVICE_REPORT_ID=? AND REPORT_ID=?";
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_DEVICE_REPORT_DETAILS(EVENT_LOCATION,EVENT_LOCATION_OTHER,REPORT_SENT_TO_FDA,REPORT_SENT_TO_FDA_DATE," +
	"REPORT_SENT_TO_MFG,REPORT_SENT_TO_MFG_DATE,REPORT_RECIPIENT_CODE,REPORT_SENT_TO_USER_FACILITY,REPORT_SENT_TO_DIST,DISTRIBUTOR_AWARE_DATE," +
	"DISTRIBUTOR_REPORT_NUMBER,DISTRIBUTOR_REPORT_TYPE,DISTRIBUTOR_FOLLOWUP_NO,DISTRIBUTOR_REPORT_DATE,DEV_REPORT_TYPE_DEATH,DEV_REPORT_TYPE_INJURY," +
	"DEV_REPORT_TYPE_MALFUNCTION,DEV_REPORT_TYPE_OTHER,DEV_REPORT_TYPE_OTHER_SPECIFY,NUMBER_EXPOSED,NUMBER_ADVERSELY_AFFECTED,NUMBER_INVOLVED_UNEXPOSED," +
	"NUMBER_POTENTIALLY_EXPOSED,AFFECTED_EMPLOYEE_FLAG,AFFECTED_OPERATOR_FLAG,CUSTOM_TEXT_01,CUSTOM_TEXT_02,CUSTOM_TEXT_03,CUSTOM_TEXT_04,CUSTOM_TEXT_05,CUSTOM_TEXT_06,CUSTOM_TEXT_07,CUSTOM_TEXT_08," +
	"CUSTOM_TEXT_09,CUSTOM_TEXT_10,CUSTOM_DATE_01,CUSTOM_DATE_02,CUSTOM_DATE_03,CUSTOM_DATE_04,CUSTOM_DATE_05,CUSTOM_COMMENT_01,CUSTOM_COMMENT_02," +
	"UPDATE_USER_ID,UPDATE_TIMESTAMP,DEVICE_REPORT_ID,REPORT_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_DEVICE_REPORT_DETAILS WHERE DEVICE_REPORT_ID = ?",
				new Object[]{this.deviceReportDetails.getDEVICE_REPORT_ID()});
		log.info("LauDeviceReportDetailsDAO delete() ID -> " + id);		
	}
	
	/**
	 	LAU_DEVICE_REPORT_DETAILS
	 	
	 	DEVICE_REPORT_ID NUMBER, 
		REPORT_ID NUMBER, 
		EVENT_LOCATION VARCHAR2(100 CHAR), 
		EVENT_LOCATION_OTHER VARCHAR2(500 CHAR), 
		REPORT_SENT_TO_FDA VARCHAR2(100 CHAR), 
		REPORT_SENT_TO_FDA_DATE VARCHAR2(8 CHAR), 
		REPORT_SENT_TO_MFG VARCHAR2(100 CHAR), 
		REPORT_SENT_TO_MFG_DATE VARCHAR2(8 CHAR), 
		REPORT_RECIPIENT_CODE VARCHAR2(500 CHAR), 
		REPORT_SENT_TO_USER_FACILITY VARCHAR2(100 CHAR), 
		REPORT_SENT_TO_DIST VARCHAR2(100 CHAR), 
		DISTRIBUTOR_AWARE_DATE VARCHAR2(8 CHAR), 
		DISTRIBUTOR_REPORT_NUMBER VARCHAR2(300 CHAR), 
		DISTRIBUTOR_REPORT_TYPE VARCHAR2(100 CHAR), 
		DISTRIBUTOR_FOLLOWUP_NO VARCHAR2(100 CHAR), 
		DISTRIBUTOR_REPORT_DATE VARCHAR2(8 CHAR), 
		DEV_REPORT_TYPE_DEATH VARCHAR2(5 CHAR), 
		DEV_REPORT_TYPE_INJURY VARCHAR2(5 CHAR), 
		DEV_REPORT_TYPE_MALFUNCTION VARCHAR2(5 CHAR), 
		DEV_REPORT_TYPE_OTHER VARCHAR2(5 CHAR), 
		DEV_REPORT_TYPE_OTHER_SPECIFY VARCHAR2(500 CHAR), 
		NUMBER_EXPOSED VARCHAR2(100 CHAR), 
		NUMBER_ADVERSELY_AFFECTED VARCHAR2(100 CHAR), 
		NUMBER_INVOLVED_UNEXPOSED VARCHAR2(100 CHAR), 
		NUMBER_POTENTIALLY_EXPOSED VARCHAR2(100 CHAR), 
		CUSTOM_TEXT_01 VARCHAR2(300 CHAR), 
		CUSTOM_TEXT_02 VARCHAR2(300 CHAR), 
		CUSTOM_TEXT_03 VARCHAR2(300 CHAR), 
		CUSTOM_TEXT_04 VARCHAR2(300 CHAR), 
		CUSTOM_TEXT_05 VARCHAR2(300 CHAR), 
		CUSTOM_TEXT_06 VARCHAR2(300 CHAR), 
		CUSTOM_TEXT_07 VARCHAR2(300 CHAR), 
		CUSTOM_TEXT_08 VARCHAR2(300 CHAR), 
		CUSTOM_TEXT_09 VARCHAR2(300 CHAR), 
		CUSTOM_TEXT_10 VARCHAR2(300 CHAR), 
		CUSTOM_DATE_01 VARCHAR2(8 CHAR), 
		CUSTOM_DATE_02 VARCHAR2(8 CHAR), 
		CUSTOM_DATE_03 VARCHAR2(8 CHAR), 
		CUSTOM_DATE_04 VARCHAR2(8 CHAR), 
		CUSTOM_DATE_05 VARCHAR2(8 CHAR), 
		CUSTOM_COMMENT_01 VARCHAR2(4000 CHAR), 
		CUSTOM_COMMENT_02 VARCHAR2(4000 CHAR), 
		UPDATE_USER_ID VARCHAR2(300 CHAR), 
		UPDATE_TIMESTAMP TIMESTAMP (2) WITH LOCAL TIME ZONE
	 
	 */

}

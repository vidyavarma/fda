package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauDosing;
import com.nrg.lau.commons.IReportsConstants;

public class LauDosingSetDAO{
	
	private static Logger log			= Logger.getLogger(LauDosingSetDAO.class);
	private LauDosing dosing 			= null;	
	private Vector<LauDosing> reports	= null;
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	
	protected void finalize() throws Throwable {		
		this.reports.removeAllElements();
		this.reports = null;
		super.finalize();
	}
	
	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, HashMap<String, List<Long>> productMap,String user,java.sql.Timestamp dstamp) throws Exception {
		userId = user;
		dt = dstamp;
		if(request.getParameter(IReportsConstants.LAU_DOSING_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_DOSING_PARAM_NAME).length() > 0)	{
		
			log.info("LauDosingDAO save() LAU_DOSING_PARAM_NAME -> " 
				+ request.getParameter(IReportsConstants.LAU_DOSING_PARAM_NAME));
			
			//Create LauDosing beans from XML Request
			createBeansDosingXml(request.getParameter(IReportsConstants.LAU_DOSING_PARAM_NAME));
			Iterator<LauDosing> itr = reports.iterator();
				
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauDosing lauDosing = (LauDosing)itr.next();
				log.info("itr.hasNext() -> " + lauDosing.toString());
				this.dosing	= null;
				this.dosing	= lauDosing;
				if(dosing.getDoseId() == 0)	{
						insert(template,reportId,productMap);
				}else  {
					if(dosing.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else	update(template);				
				}
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_DOSING_PARAM_NAME + " not found in request");
		}
		
	}
	
	public void insert(SimpleJdbcTemplate template, long reportId, HashMap<String, List<Long>> productMap)
			throws Exception {
		int id = 0;
		long doseId = CommonDAO.getPrimaryKey(template);
		this.dosing.setDoseId(doseId);
		if(this.dosing.getReportId() == 0)	{
			this.dosing.setReportId(reportId);	}
		if(productMap != null && this.dosing.getProductId() == 0 
				&& productMap.size() > 0) {			
			List<Long> list = productMap.get(this.dosing.getProductName()+this.dosing.getProductType()+this.dosing.getProductDisplayNumber());
			this.dosing.setProductId(list.get(1));
		}	
		log.info("Generated Primary Key for DOSE_ID ->" + doseId);
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("LauDosingDAO insert() ID -> " + id);
		
	}

	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauDosingDAO update() ID -> " + id);	
		
	}
	
	private Object[] getParameters()
	{
		LauDosing lauDosing = this.dosing; 
		return new Object[]{
			lauDosing.getStartDate(),
			lauDosing.getStopDate(),
			lauDosing.getDisplayNumber(),
			lauDosing.getOngoingFlag(),
			lauDosing.getDuration(),
			lauDosing.getDurationUnits(),
			lauDosing.getDose(),
			lauDosing.getDoseUnit(),
			lauDosing.getRoute(),
			lauDosing.getFrequency(),
			lauDosing.getFormulation(),
			lauDosing.getLotNumber(),
			lauDosing.getLotExpirationDate(),
			userId,
			dt,
			lauDosing.getReportId(),
			lauDosing.getProductId(),
			lauDosing.getDoseId()	
		};
	}
	
	public void createBeansDosingXml(String xml) throws Exception {
		
		String mainXmlTag = "ROWSET/ROW";
		reports				= new Vector<LauDosing>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauDosing.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/DOSE_ID", "doseId" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_ID", "productId" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_TYPE", "productType" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_DISPLAY_NUMBER", "productDisplayNumber" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_NAME", "productName" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/START_DATE", "startDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/STOP_DATE", "stopDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISPLAY_NUMBER", "displayNumber2" );
		digester.addBeanPropertySetter( mainXmlTag+"/ONGOING_FLAG", "ongoingFlag" );		
		digester.addBeanPropertySetter( mainXmlTag+"/DURATION", "duration" );
		digester.addBeanPropertySetter( mainXmlTag+"/DURATION_UNITS", "durationUnits" );
		digester.addBeanPropertySetter( mainXmlTag+"/DOSE", "dose" );
		digester.addBeanPropertySetter( mainXmlTag+"/DOSE_UNIT", "doseUnit" );
		digester.addBeanPropertySetter( mainXmlTag+"/ROUTE", "route" );
		digester.addBeanPropertySetter( mainXmlTag+"/FREQUENCY", "frequency" );
		digester.addBeanPropertySetter( mainXmlTag+"/FORMULATION", "formulation" );		
		digester.addBeanPropertySetter( mainXmlTag+"/LOT_NUMBER", "lotNumber" );
		digester.addBeanPropertySetter( mainXmlTag+"/LOT_EXPIRATION_DATE", "lotExpirationDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "updateUserId" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		//digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "updateTimeStamp" );
						
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addDosingXmlData" );		
		digester.parse(new StringReader(xml));
		
	}

	public void addDosingXmlData(LauDosing lauDosing) {
		reports.add(lauDosing);
		log.info(lauDosing.toString());
	}
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_DOSING SET START_DATE=?,STOP_DATE=?,DISPLAY_NUMBER=?," +
		"ONGOING_FLAG=?,DURATION=?,DURATION_UNITS=?,DOSE=?,DOSE_UNIT=?,ROUTE=?,FREQUENCY=?,FORMULATION=?," +
		"LOT_NUMBER=?,LOT_EXPIRATION_DATE=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE REPORT_ID=? AND PRODUCT_ID=? AND DOSE_ID=?";
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_DOSING (START_DATE,STOP_DATE,DISPLAY_NUMBER," +
		"ONGOING_FLAG,DURATION,DURATION_UNITS,DOSE,DOSE_UNIT,ROUTE,FREQUENCY,FORMULATION,LOT_NUMBER," +
		"LOT_EXPIRATION_DATE,UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID,PRODUCT_ID,DOSE_ID) " +
		"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";	
	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_DOSING WHERE DOSE_ID = ?",this.dosing.getDoseId());
		log.info("LauDosingSetDAO delete() ID -> " + id);		
	}
	
	/**
	 
	 	LAU_DOSING
	 
	 	DOSE_ID NUMBER(22 , 0) NOT NULL,
		PRODUCT_ID NUMBER(22 , 0) NOT NULL,
		PRODUCT_NAME VARCHAR2(500),
		REPORT_ID NUMBER(22 , 0) NOT NULL,
		START_DATE VARCHAR2(24),
		STOP_DATE VARCHAR2(24),
		DISPLAY_NUMBER NUMBER(22 , 0),
		ONGOING_FLAG VARCHAR2(50),
		DURATION NUMBER(12 , 0),
		DURATION_UNITS VARCHAR2(50),
		DOSE NUMBER(10 , 3),
		DOSE_UNIT VARCHAR2(50),
		ROUTE VARCHAR2(50),
		FREQUENCY VARCHAR2(50),
		FORMULATION VARCHAR2(50),
		LOT_NUMBER VARCHAR2(100),
		LOT_EXPIRATION_DATE VARCHAR2(24),
		UPDATE_USER_ID VARCHAR2(300) NOT NULL,
		UPDATE_TIMESTAMP date NOT NULL
	 
	 */

}

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

import com.nrg.lau.beans.LauDeviceDetails;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauDeviceDetailsSetDAO implements IReportChildSetDAO<LauDeviceDetails>{
	
	private static Logger log			= Logger.getLogger(LauDeviceDetailsSetDAO.class);
	private LauDeviceDetails devicedetails	= null;	
	private Vector<LauDeviceDetails> reports	= null;
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";	
	
	protected void finalize() throws Throwable {		
		this.reports.removeAllElements();
		this.reports = null;
		super.finalize();
	}
	
	public List<Object> save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, HashMap<String, List<Long>> productMap,String user,java.sql.Timestamp dstamp) throws Exception {
		userId 	= user;
		dt	 	= dstamp;
		List<Object> list = new ArrayList<Object>();
		if(request.getParameter(IReportsConstants.LAU_DEVICE_DETAILS_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_DEVICE_DETAILS_PARAM_NAME).length() > 0)	{
		
			log.info("LauDeviceDetailsSetDAO save() LAU_DEVICE_DETAILS_PARAM_NAME -> " 
				+ request.getParameter(IReportsConstants.LAU_DEVICE_DETAILS_PARAM_NAME));
			
		log.info(productMap + " ====   Test----");
			
			//Create LauDeviceDetails beans from XML Request
		createBeansFromXml(request.getParameter(IReportsConstants.LAU_DEVICE_DETAILS_PARAM_NAME));	
			Iterator<LauDeviceDetails> itr = reports.iterator();
			
			//DEVICE_DETAILS_ID is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauDeviceDetails lauDeviceDetails = (LauDeviceDetails)itr.next();
				log.info("itr.hasNext() -> " + lauDeviceDetails.toString());
				this.devicedetails	= null;
				this.devicedetails	= lauDeviceDetails;			
				
				if(devicedetails.getDEVICE_DETAILS_ID() == 0)	{
					log.info("Inside");
						insert(template,reportId,productMap);
				}else  {
					if(devicedetails.getTransactionType() == IReportsConstants.deleteFlag)	{
						//delete(template);
					}else	update(template);				
				}
				list.add(devicedetails);
			}//end of while(itr.hasNext())
			
		}	else	{
			log.info(IReportsConstants.LAU_DEVICE_DETAILS_PARAM_NAME + " not found in request");
		}
		
		return list;
		
	}
	
	public void insert(SimpleJdbcTemplate template, long reportId, HashMap<String, List<Long>> productMap)
			throws Exception {
		log.info("1===");
		int id = 0;
		long deviceDetailsId = CommonDAO.getPrimaryKey(template);
		this.devicedetails.setDEVICE_DETAILS_ID(deviceDetailsId);
		if(this.devicedetails.getREPORT_ID() == 0)	{
			this.devicedetails.setREPORT_ID(reportId);	}
		if(productMap != null && this.devicedetails.getPRODUCT_ID() == 0 
				&& productMap.size() > 0) {	
			
			
			Iterator iter = productMap.entrySet().iterator();
			 
			while (iter.hasNext()) {
				Map.Entry mEntry = (Map.Entry) iter.next();
				log.info(mEntry.getKey() + " : " + mEntry.getValue());
			}

			
			log.info("2----");
			log.info(this.devicedetails.getProductName()+this.devicedetails.getProductType()+this.devicedetails.getDISPLAY_NUMBER() + " ===");
			//List<Long> list = productMap.get(this.devicedetails.getProductName()+this.devicedetails.getProductType()+this.devicedetails.getDISPLAY_NUMBER());
			List<Long> list = productMap.get(this.devicedetails.getINTERNAL_LINK_ID());
			this.devicedetails.setPRODUCT_ID(list.get(1));
		}	
		log.info("Generated Primary Key for DEVICE DETAILS ID ->" + deviceDetailsId);
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("LauDeviceDetailsSetDAO insert() ID -> " + id);
		
		
		
		
	}

	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauDeviceDetailsSetDAO update() ID -> " + id);	
		
	}
	
	private Object[] getParameters()
	{
		LauDeviceDetails devicedetails = this.devicedetails; 
		return new Object[]{				
			devicedetails.getMODEL_NUMBER(),
			devicedetails.getCATALOG_NUMBER(),
			devicedetails.getSERIAL_NUMBER(),
			devicedetails.getLOT_NUMBER(),
			devicedetails.getEXPIRATION_DATE(),
			devicedetails.getOTHER_DEVICE_NUMBER(),
			devicedetails.getDEVICE_OPERATOR(),
			devicedetails.getDEVICE_OPERATOR_OTHER_SPECIFY(),
			devicedetails.getIMPLANT_DATE(),
			devicedetails.getEXPLANT_DATE(),
			devicedetails.getMANUFACTURED_DATE(),
			devicedetails.getSINGLE_USE_DEVICE(),
			devicedetails.getSINGLE_USE_DEVICE_REPROCESSED(),
			devicedetails.getDEVICE_AVAILABLE_FOR_EVAL(),
			devicedetails.getDEVICE_RETURN_DATE(),
			devicedetails.getDISTRIBUTOR_REPORT_NUMBER(),
			devicedetails.getDEVICE_USAGE(),
			devicedetails.getREMEDIAL_ACTION_TAKEN(),
			devicedetails.getREMEDIAL_ACTION_OTHER(),
			devicedetails.getDEVICE_EVAL_BY_MFG(),
			devicedetails.getDEVICE_EVAL_ATTACHED_FLAG(),
			devicedetails.getDEV_NOT_EVAL_REASON(),
			devicedetails.getDISTRIBUTOR_AWARE_DATE(),
			devicedetails.getDISTRIBUTOR_REPORT_TYPE(),
			devicedetails.getDISTRIBUTOR_FOLLOWUP_NO(),
			devicedetails.getDISTRIBUTOR_REPORT_DATE(),
			devicedetails.getDEVICE_AGE(),
			devicedetails.getDEVICE_AGE_UNITS(),
			devicedetails.getDEVICE_AGE_TEXT(),
			devicedetails.getPACKAGING_SAVED(),
			devicedetails.getLABORATORY_DEVICE_OR_TEST(),
			devicedetails.getCORRECTIVE_ACTION_NUMBER(),
			devicedetails.getCUSTOM_TEXT_01(),
			devicedetails.getCUSTOM_TEXT_02(),
			devicedetails.getCUSTOM_TEXT_03(),
			devicedetails.getCUSTOM_TEXT_04(),
			devicedetails.getCUSTOM_TEXT_05(),
			devicedetails.getCUSTOM_TEXT_06(),
			devicedetails.getCUSTOM_TEXT_07(),
			devicedetails.getCUSTOM_TEXT_08(),
			devicedetails.getCUSTOM_TEXT_09(),
			devicedetails.getCUSTOM_TEXT_10(),
			devicedetails.getCUSTOM_DATE_01(),
			devicedetails.getCUSTOM_DATE_02(),
			devicedetails.getCUSTOM_DATE_03(),
			devicedetails.getCUSTOM_DATE_04(),
			devicedetails.getCUSTOM_DATE_05(),
			devicedetails.getCUSTOM_COMMENT_01(),
			devicedetails.getCUSTOM_COMMENT_02(),
			userId,
			dt,
			devicedetails.getDEVICE_DETAILS_ID(),
			devicedetails.getPRODUCT_ID(),
			devicedetails.getREPORT_ID()			
		};
	}
	
	public void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag = "ROWSET/ROW";		
		reports				= new Vector<LauDeviceDetails>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauDeviceDetails.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_NAME", "productName" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_TYPE", "productType" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_DISPLAY_NUMBER", "productDisplayNumber" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		
		digester.addBeanPropertySetter( mainXmlTag+"/DEVICE_DETAILS_ID", "DEVICE_DETAILS_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_ID", "PRODUCT_ID" );		
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "REPORT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/MODEL_NUMBER", "MODEL_NUMBER" );
		digester.addBeanPropertySetter( mainXmlTag+"/CATALOG_NUMBER", "CATALOG_NUMBER" );
		digester.addBeanPropertySetter( mainXmlTag+"/SERIAL_NUMBER", "SERIAL_NUMBER" );		
		digester.addBeanPropertySetter( mainXmlTag+"/LOT_NUMBER", "LOT_NUMBER" );
		digester.addBeanPropertySetter( mainXmlTag+"/EXPIRATION_DATE", "EXPIRATION_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/OTHER_DEVICE_NUMBER", "OTHER_DEVICE_NUMBER" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEVICE_OPERATOR_HP", "DEVICE_OPERATOR_HP" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEVICE_OPERATOR_PT", "DEVICE_OPERATOR_PT" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEVICE_OPERATOR_OTHER", "DEVICE_OPERATOR_OTHER" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEVICE_OPERATOR", "DEVICE_OPERATOR" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEVICE_OPERATOR_OTHER_SPECIFY", "DEVICE_OPERATOR_OTHER_SPECIFY" );		
		digester.addBeanPropertySetter( mainXmlTag+"/IMPLANT_DATE", "IMPLANT_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/EXPLANT_DATE", "EXPLANT_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/MANUFACTURED_DATE", "MANUFACTURED_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/SINGLE_USE_DEVICE", "SINGLE_USE_DEVICE" );
		digester.addBeanPropertySetter( mainXmlTag+"/SINGLE_USE_DEVICE_REPROCESSED", "SINGLE_USE_DEVICE_REPROCESSED" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEVICE_AVAILABLE_FOR_EVAL", "DEVICE_AVAILABLE_FOR_EVAL" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEVICE_RETURN_DATE", "DEVICE_RETURN_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISTRIBUTOR_REPORT_NUMBER", "DISTRIBUTOR_REPORT_NUMBER" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEVICE_USAGE", "DEVICE_USAGE" );
		digester.addBeanPropertySetter( mainXmlTag+"/REMEDIAL_ACTION_TAKEN", "REMEDIAL_ACTION_TAKEN" );
		digester.addBeanPropertySetter( mainXmlTag+"/REMEDIAL_ACTION_OTHER", "REMEDIAL_ACTION_OTHER" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEVICE_EVAL_BY_MFG", "DEVICE_EVAL_BY_MFG" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEVICE_EVAL_ATTACHED_FLAG", "DEVICE_EVAL_ATTACHED_FLAG" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEV_NOT_EVAL_REASON", "DEV_NOT_EVAL_REASON" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISTRIBUTOR_AWARE_DATE", "DISTRIBUTOR_AWARE_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISTRIBUTOR_REPORT_TYPE", "DISTRIBUTOR_REPORT_TYPE" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISTRIBUTOR_FOLLOWUP_NO", "DISTRIBUTOR_FOLLOWUP_NO" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISTRIBUTOR_REPORT_DATE", "DISTRIBUTOR_REPORT_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEVICE_AGE", "DEVICE_AGE" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEVICE_AGE_UNITS", "DEVICE_AGE_UNITS" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEVICE_AGE_TEXT", "DEVICE_AGE_TEXT" );
		digester.addBeanPropertySetter( mainXmlTag+"/PACKAGING_SAVED", "PACKAGING_SAVED" );
		digester.addBeanPropertySetter( mainXmlTag+"/LABORATORY_DEVICE_OR_TEST", "LABORATORY_DEVICE_OR_TEST" );
		digester.addBeanPropertySetter( mainXmlTag+"/CORRECTIVE_ACTION_NUMBER", "CORRECTIVE_ACTION_NUMBER" );
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
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "UPDATE_USER_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "UPDATE_TIMESTAMP" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISPLAY_NUMBER", "DISPLAY_NUMBER" );
		digester.addBeanPropertySetter( mainXmlTag+"/INTERNAL_LINK_ID", "INTERNAL_LINK_ID");
						
		//Move to next LauDeviceDetails
		digester.addSetNext( mainXmlTag, "addDosingXmlData" );		
		digester.parse(new StringReader(xml));
		
	}

	public void addDosingXmlData(LauDeviceDetails lauDeviceDetails) {
		reports.add(lauDeviceDetails);
	//	log.info(lauDeviceDetails.toString());
	}
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_DEVICE_DETAILS SET MODEL_NUMBER=?,CATALOG_NUMBER=?,SERIAL_NUMBER=?," +
			"LOT_NUMBER=?,EXPIRATION_DATE=?,OTHER_DEVICE_NUMBER=?," +
	"DEVICE_OPERATOR=?,DEVICE_OPERATOR_OTHER_SPECIFY=?,IMPLANT_DATE=?,EXPLANT_DATE=?," +
	"MANUFACTURED_DATE=?,SINGLE_USE_DEVICE=?,SINGLE_USE_DEVICE_REPROCESSED=?,DEVICE_AVAILABLE_FOR_EVAL=?,DEVICE_RETURN_DATE=?,DISTRIBUTOR_REPORT_NUMBER=?," +
	"DEVICE_USAGE=?,REMEDIAL_ACTION_TAKEN=?,REMEDIAL_ACTION_OTHER=?,DEVICE_EVAL_BY_MFG=?,DEVICE_EVAL_ATTACHED_FLAG=?,DEV_NOT_EVAL_REASON=?," +
	"DISTRIBUTOR_AWARE_DATE=?,DISTRIBUTOR_REPORT_TYPE=?,DISTRIBUTOR_FOLLOWUP_NO=?,DISTRIBUTOR_REPORT_DATE=?,DEVICE_AGE=?,DEVICE_AGE_UNITS=?," +
	"DEVICE_AGE_TEXT=?,PACKAGING_SAVED=?,LABORATORY_DEVICE_OR_TEST=?,CORRECTIVE_ACTION_NUMBER=?,CUSTOM_TEXT_01=?,CUSTOM_TEXT_02=?,CUSTOM_TEXT_03=?," +
	"CUSTOM_TEXT_04=?,CUSTOM_TEXT_05=?,CUSTOM_TEXT_06=?,CUSTOM_TEXT_07=?,CUSTOM_TEXT_08=?,CUSTOM_TEXT_09=?,CUSTOM_TEXT_10=?,CUSTOM_DATE_01=?," +
	"CUSTOM_DATE_02=?,CUSTOM_DATE_03=?,CUSTOM_DATE_04=?,CUSTOM_DATE_05=?,CUSTOM_COMMENT_01=?,CUSTOM_COMMENT_02=?,UPDATE_USER_ID=?," +
	"UPDATE_TIMESTAMP=? WHERE DEVICE_DETAILS_ID=? AND PRODUCT_ID=? AND REPORT_ID=?";
	
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_DEVICE_DETAILS (MODEL_NUMBER,CATALOG_NUMBER,SERIAL_NUMBER," +
			"LOT_NUMBER,EXPIRATION_DATE,OTHER_DEVICE_NUMBER," +
	"DEVICE_OPERATOR,DEVICE_OPERATOR_OTHER_SPECIFY,IMPLANT_DATE,EXPLANT_DATE," +
	"MANUFACTURED_DATE,SINGLE_USE_DEVICE,SINGLE_USE_DEVICE_REPROCESSED,DEVICE_AVAILABLE_FOR_EVAL," +
	"DEVICE_RETURN_DATE,DISTRIBUTOR_REPORT_NUMBER,DEVICE_USAGE,REMEDIAL_ACTION_TAKEN,REMEDIAL_ACTION_OTHER," +
	"DEVICE_EVAL_BY_MFG,DEVICE_EVAL_ATTACHED_FLAG,DEV_NOT_EVAL_REASON,DISTRIBUTOR_AWARE_DATE,DISTRIBUTOR_REPORT_TYPE," +
	"DISTRIBUTOR_FOLLOWUP_NO,DISTRIBUTOR_REPORT_DATE,DEVICE_AGE,DEVICE_AGE_UNITS,DEVICE_AGE_TEXT,PACKAGING_SAVED," +
	"LABORATORY_DEVICE_OR_TEST,CORRECTIVE_ACTION_NUMBER,CUSTOM_TEXT_01,CUSTOM_TEXT_02,CUSTOM_TEXT_03,CUSTOM_TEXT_04," +
	"CUSTOM_TEXT_05,CUSTOM_TEXT_06,CUSTOM_TEXT_07,CUSTOM_TEXT_08,CUSTOM_TEXT_09,CUSTOM_TEXT_10,CUSTOM_DATE_01," +
	"CUSTOM_DATE_02,CUSTOM_DATE_03,CUSTOM_DATE_04,CUSTOM_DATE_05,CUSTOM_COMMENT_01,CUSTOM_COMMENT_02,UPDATE_USER_ID," +
	"UPDATE_TIMESTAMP,DEVICE_DETAILS_ID,PRODUCT_ID,REPORT_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
	"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		
		id = template.update("DELETE FROM LAU_DEVICE_DETAILS WHERE DEVICE_DETAILS_ID = ?",this.devicedetails.getDEVICE_DETAILS_ID());
		log.info("LauDeviceDetailsSetDAO delete() ID -> " + id);		
	}

	@Override
	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, String user, Timestamp dstamp) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 
	 	LAU_DEVICE_DETAILS
	 
	 	DEVICE_DETAILS_ID NUMBER, 
		PRODUCT_ID NUMBER, 
		REPORT_ID NUMBER, 
		MODEL_NUMBER VARCHAR2(500 CHAR), 
		CATALOG_NUMBER VARCHAR2(500 CHAR), 
		SERIAL_NUMBER VARCHAR2(500 CHAR), 
		LOT_NUMBER VARCHAR2(500 CHAR), 
		EXPIRATION_DATE VARCHAR2(8 CHAR), 
		OTHER_DEVICE_NUMBER VARCHAR2(500 CHAR), 
		DEVICE_OPERATOR_HP VARCHAR2(5 CHAR), 
		DEVICE_OPERATOR_PT VARCHAR2(5 CHAR), 
		DEVICE_OPERATOR_OTHER VARCHAR2(5 CHAR), 
		DEVICE_OPERATOR_OTHER_SPECIFY VARCHAR2(500 CHAR), 
		IMPLANT_DATE VARCHAR2(8 CHAR), 
		EXPLANT_DATE VARCHAR2(8 CHAR), 
		MANUFACTURED_DATE VARCHAR2(8 CHAR), 
		SINGLE_USE_DEVICE VARCHAR2(100 CHAR), 
		SINGLE_USE_DEVICE_REPROCESSED VARCHAR2(100 CHAR), 
		DEVICE_AVAILABLE_FOR_EVAL VARCHAR2(100 CHAR), 
		DEVICE_RETURN_DATE VARCHAR2(8 CHAR), 
		DISTRIBUTOR_REPORT_NUMBER VARCHAR2(300 CHAR), 
		DEVICE_USAGE VARCHAR2(100 CHAR), 
		REMEDIAL_ACTION_TAKEN VARCHAR2(100 CHAR), 
		REMEDIAL_ACTION_OTHER VARCHAR2(500 CHAR), 
		DEVICE_EVAL_BY_MFG VARCHAR2(100 CHAR), 
		DEVICE_EVAL_ATTACHED_FLAG VARCHAR2(5 CHAR), 
		DEV_NOT_EVAL_REASON VARCHAR2(100 CHAR), 
		DISTRIBUTOR_AWARE_DATE VARCHAR2(8 CHAR), 
		DISTRIBUTOR_REPORT_TYPE VARCHAR2(100 CHAR), 
		DISTRIBUTOR_FOLLOWUP_NO VARCHAR2(100 CHAR), 
		DISTRIBUTOR_REPORT_DATE VARCHAR2(8 CHAR), 
		DEVICE_AGE VARCHAR2(100 CHAR), 
		DEVICE_AGE_UNITS VARCHAR2(100 CHAR), 
		DEVICE_AGE_TEXT VARCHAR2(100 CHAR), 
		PACKAGING_SAVED VARCHAR2(100 CHAR), 
		LABORATORY_DEVICE_OR_TEST VARCHAR2(5 CHAR), 
		CORRECTIVE_ACTION_NUMBER VARCHAR2(100 CHAR), 
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

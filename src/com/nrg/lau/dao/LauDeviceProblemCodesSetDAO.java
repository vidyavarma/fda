package com.nrg.lau.dao;

import java.io.StringReader;
import java.sql.Timestamp;
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
import com.nrg.lau.beans.LauDeviceProblemCodes;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;

//public class LauDeviceProblemCodesSetDAO implements IReportChildSetDAO<LauDeviceProblemCodes>{
public class LauDeviceProblemCodesSetDAO implements IReportChildSetDAO<LauDeviceProblemCodes>{
	
	private static Logger log			= Logger.getLogger(LauDeviceProblemCodesSetDAO.class);
	private LauDeviceProblemCodes deviceProblemCodes 	= null;	
	private Vector<LauDeviceProblemCodes> reports		= null;
	private List<Object> devicedetailsList	= null;
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	
	protected void finalize() throws Throwable {		
		this.reports.removeAllElements();
		this.reports = null;
		super.finalize();
	}
	
	/*public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, List<Object> deviceDetails,String user,java.sql.Timestamp dstamp) throws Exception {
		userId = user;
		dt = dstamp;
		if(request.getParameter(IReportsConstants.LAU_DEVICE_PROBLEM_CODES_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_DEVICE_PROBLEM_CODES_PARAM_NAME).length() > 0)	{
		
			log.info("LauDeviceProblemCodesSetDAO save() LAU_DEVICE_PROBLEM_CODES_PARAM_NAME -> " 
				+ request.getParameter(IReportsConstants.LAU_DEVICE_PROBLEM_CODES_PARAM_NAME));
			
			devicedetailsList = deviceDetails;
			
			//Create LauDeviceProblemCodes beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_DEVICE_PROBLEM_CODES_PARAM_NAME));
			Iterator<LauDeviceProblemCodes> itr = reports.iterator();
				
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauDeviceProblemCodes lauDeviceProblemCodes = (LauDeviceProblemCodes)itr.next();
				log.info("itr.hasNext() -> " + lauDeviceProblemCodes.toString());
				this.deviceProblemCodes	= null;
				this.deviceProblemCodes	= lauDeviceProblemCodes;
				if(deviceProblemCodes.getDEVICE_REPORT_CODE_ID() == 0)	{
						insert(template,reportId);
				}else  {
					if(deviceProblemCodes.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else	update(template);				
				}
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_DEVICE_PROBLEM_CODES_PARAM_NAME + " not found in request");
		}
		
	}*/
	
	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, List<Object> deviceDetails,HashMap<String, List<Long>> productMap, String user,java.sql.Timestamp dstamp) throws Exception {
		userId = user;
		dt = dstamp;
		if(request.getParameter(IReportsConstants.LAU_DEVICE_PROBLEM_CODES_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_DEVICE_PROBLEM_CODES_PARAM_NAME).length() > 0)	{
		
			log.info("LauDeviceProblemCodesSetDAO save() LAU_DEVICE_PROBLEM_CODES_PARAM_NAME -> " 
				+ request.getParameter(IReportsConstants.LAU_DEVICE_PROBLEM_CODES_PARAM_NAME));
			
			devicedetailsList = deviceDetails;
			
			//Create LauDeviceProblemCodes beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_DEVICE_PROBLEM_CODES_PARAM_NAME));
			Iterator<LauDeviceProblemCodes> itr = reports.iterator();
				
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauDeviceProblemCodes lauDeviceProblemCodes = (LauDeviceProblemCodes)itr.next();
				log.info("itr.hasNext() -> " + lauDeviceProblemCodes.toString());
				this.deviceProblemCodes	= null;
				this.deviceProblemCodes	= lauDeviceProblemCodes;
				
				if(deviceProblemCodes.getDEVICE_REPORT_CODE_ID() == 0)	{
						insert(template,reportId,productMap);
				}else  {
					if(deviceProblemCodes.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else	update(template);				
				}
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_DEVICE_PROBLEM_CODES_PARAM_NAME + " not found in request");
		}
		
	}
	
	private long getDeviceDetailsID(List<Object> deviceDetails, String productName) {
		for (int i = 0; i < deviceDetails.size(); i++) {
			LauDeviceDetails lauDeviceDetails = (LauDeviceDetails) deviceDetails.get(i);
			//log.info(" =============  " + productName);
			//log.info(" =============  " + lauDeviceDetails.getDEVICE_DETAILS_ID());
			//log.info(" ==== lauDeviceDetails.getProductName() " + lauDeviceDetails.getProductName());
			if(lauDeviceDetails.getProductName().trim().equalsIgnoreCase(productName.trim())) {
				//log.info("*****************");
				//log.info(" ============= lauDeviceDetails.getDEVICE_DETAILS_ID()  " + lauDeviceDetails.getDEVICE_DETAILS_ID());
				return lauDeviceDetails.getDEVICE_DETAILS_ID();
			}
		}
		return 0;
	}
	
//	public void insert(SimpleJdbcTemplate template, long reportId, HashMap<String, List<Long>> productMap)
//			throws Exception {
		public void insert(SimpleJdbcTemplate template, long reportId, HashMap<String, List<Long>> productMap)
				throws Exception {
		int id = 0;
		long deviceCodeId = CommonDAO.getPrimaryKey(template);
		this.deviceProblemCodes.setDEVICE_REPORT_CODE_ID(deviceCodeId);
		if(this.deviceProblemCodes.getREPORT_ID() == 0)	{
			this.deviceProblemCodes.setREPORT_ID(reportId);	}
		
		log.info("Product Id----"+this.deviceProblemCodes.getPRODUCT_ID());
		if (this.deviceProblemCodes.getPRODUCT_ID() == 0) {
		if(productMap != null && this.deviceProblemCodes.getPRODUCT_ID() == 0 
				&& productMap.size() > 0) {	
			
			
			Iterator iter = productMap.entrySet().iterator();
			 
			while (iter.hasNext()) {
				Map.Entry mEntry = (Map.Entry) iter.next();
				log.info(mEntry.getKey() + " : " + mEntry.getValue());
			}

			
			log.info("2----");
			
			List<Long> list = productMap.get(this.deviceProblemCodes.getINTERNAL_LINK_ID());
			this.deviceProblemCodes.setPRODUCT_ID(list.get(1));
		}
		}
		
		
		long deviceDetailsID = 0;
		if(deviceProblemCodes.getDEVICE_DETAILS_ID() == 0) {
			deviceDetailsID = getDeviceDetailsID(devicedetailsList,
				deviceProblemCodes.getProductName());
		}else {
			deviceDetailsID = deviceProblemCodes.getDEVICE_DETAILS_ID();
		}
		if(deviceDetailsID == 0) {
			throw new Exception("DEVICE_DETAILS_ID not found from devicedetailsList using Product Name " 
					+ deviceProblemCodes.getProductName());
		}else {
			deviceProblemCodes.setDEVICE_DETAILS_ID(deviceDetailsID);
		}	
		
		log.info("DEVICE_DETAILS_ID ->" + deviceDetailsID);
		log.info("Generated Primary Key for DEVICE_REPORT_CODE_ID ->" + deviceCodeId);
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("LauDeviceProblemCodesSetDAO insert() ID -> " + id);
		
	}
	
	/*public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {
		int id = 0;
		long deviceCodeId = CommonDAO.getPrimaryKey(template);
		this.deviceProblemCodes.setDEVICE_REPORT_CODE_ID(deviceCodeId);
		if(this.deviceProblemCodes.getREPORT_ID() == 0)	{
			this.deviceProblemCodes.setREPORT_ID(reportId);	}
		long deviceDetailsID = 0;
		if(deviceProblemCodes.getDEVICE_DETAILS_ID() == 0) {
			deviceDetailsID = getDeviceDetailsID(devicedetailsList,
				deviceProblemCodes.getProductName());
		}else {
			deviceDetailsID = deviceProblemCodes.getDEVICE_DETAILS_ID();
		}
		if(deviceDetailsID == 0) {
			throw new Exception("DEVICE_DETAILS_ID not found from devicedetailsList using Product Name " 
					+ deviceProblemCodes.getProductName());
		}else {
			deviceProblemCodes.setDEVICE_DETAILS_ID(deviceDetailsID);
		}	
		
		log.info("DEVICE_DETAILS_ID ->" + deviceDetailsID);
		log.info("Generated Primary Key for DEVICE_REPORT_CODE_ID ->" + deviceCodeId);
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("LauDeviceProblemCodesSetDAO insert() ID -> " + id);
		
	}*/

	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauDeviceProblemCodesSetDAO update() ID -> " + id);	
		
	}
	
	private Object[] getParameters()
	{
		LauDeviceProblemCodes lauDeviceProblemCodes = this.deviceProblemCodes; 
		return new Object[]{
			lauDeviceProblemCodes.getPRODUCT_ID(),
			lauDeviceProblemCodes.getREPORT_ID(),
			lauDeviceProblemCodes.getDISPLAY_NUMBER(),
			lauDeviceProblemCodes.getCODING_SOURCE(),
			lauDeviceProblemCodes.getDEVICE_REPORT_CODE_TYPE(),
			lauDeviceProblemCodes.getDEVICE_REPORT_CODE_VALUE(),
			userId,
			dt,
			lauDeviceProblemCodes.getDEVICE_REPORT_CODE_ID(),
			lauDeviceProblemCodes.getDEVICE_DETAILS_ID()
			
		};
	}
	
	public void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag = "ROWSET/ROW";
		reports				= new Vector<LauDeviceProblemCodes>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauDeviceProblemCodes.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEVICE_REPORT_CODE_ID", "DEVICE_REPORT_CODE_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEVICE_DETAILS_ID", "DEVICE_DETAILS_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_ID", "PRODUCT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_NAME", "productName" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "REPORT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISPLAY_NUMBER", "DISPLAY_NUMBER" );
		digester.addBeanPropertySetter( mainXmlTag+"/CODING_SOURCE", "CODING_SOURCE" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEVICE_REPORT_CODE_TYPE", "DEVICE_REPORT_CODE_TYPE" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEVICE_REPORT_CODE_VALUE", "DEVICE_REPORT_CODE_VALUE" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "UPDATE_USER_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "UPDATE_TIMESTAMP" );	
		digester.addBeanPropertySetter( mainXmlTag+"/INTERNAL_LINK_ID", "INTERNAL_LINK_ID");
		
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addDosingXmlData" );		
		digester.parse(new StringReader(xml));
		
	}

	public void addDosingXmlData(LauDeviceProblemCodes lauDeviceProblemCodes) {
		reports.add(lauDeviceProblemCodes);
		log.info(lauDeviceProblemCodes.toString());
	}
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_DEVICE_PROBLEM_CODES SET PRODUCT_ID=?,REPORT_ID=?,DISPLAY_NUMBER=?,CODING_SOURCE=?," +
			"DEVICE_REPORT_CODE_TYPE=?,DEVICE_REPORT_CODE_VALUE=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE DEVICE_REPORT_CODE_ID=? AND DEVICE_DETAILS_ID=?";
	
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_DEVICE_PROBLEM_CODES (PRODUCT_ID,REPORT_ID,DISPLAY_NUMBER,CODING_SOURCE,DEVICE_REPORT_CODE_TYPE," +
			"DEVICE_REPORT_CODE_VALUE,UPDATE_USER_ID,UPDATE_TIMESTAMP,DEVICE_REPORT_CODE_ID,DEVICE_DETAILS_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_DEVICE_PROBLEM_CODES WHERE DEVICE_REPORT_CODE_ID = ?",this.deviceProblemCodes.getDEVICE_REPORT_CODE_ID());
		log.info("LauDeviceProblemCodesSetDAO delete() ID -> " + id);		
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
	 
	 	LAU_DEVICE_PROBLEM_CODES
	 
	 	DEVICE_REPORT_CODE_ID NUMBER, 
		DEVICE_DETAILS_ID NUMBER, 
		PRODUCT_ID NUMBER, 
		REPORT_ID NUMBER, 
		DISPLAY_NUMBER NUMBER, 
		CODING_SOURCE VARCHAR2(100 CHAR), 
		DEVICE_REPORT_CODE_TYPE VARCHAR2(100 CHAR), 
		DEVICE_REPORT_CODE_VALUE VARCHAR2(500 CHAR), 
		UPDATE_USER_ID VARCHAR2(300 CHAR), 
		UPDATE_TIMESTAMP TIMESTAMP (2) WITH LOCAL TIME ZONE
	 
	 */

}

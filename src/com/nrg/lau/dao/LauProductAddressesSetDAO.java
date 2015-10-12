package com.nrg.lau.dao;

import java.io.StringReader;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauProductAddresses;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauProductAddressesSetDAO implements IReportChildSetDAO<LauProductAddresses>{
	
	private static Logger log			= Logger.getLogger(LauProductAddressesSetDAO.class);
	private LauProductAddresses productAddress	= null;	
	private Vector<LauProductAddresses> reports	= null;
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	
	protected void finalize() throws Throwable {		
		this.reports.removeAllElements();
		this.reports = null;
		super.finalize();
	}
	
	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, HashMap<String, List<Long>> productMap,String user,java.sql.Timestamp dstamp) throws Exception {
		log.info("save Prod address 1--------> ");
		userId = user;
		dt = dstamp;
		if(request.getParameter(IReportsConstants.LAU_PRODUCT_ADDRESSES_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_PRODUCT_ADDRESSES_PARAM_NAME).length() > 0)	{
		
			log.info("LauProductAddressesSetDAO save() LAU_PRODUCT_ADDRESSES_PARAM_NAME -> " 
				+ request.getParameter(IReportsConstants.LAU_PRODUCT_ADDRESSES_PARAM_NAME));
			
			//Create LAU_PRODUCT_ADDRESSES beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_PRODUCT_ADDRESSES_PARAM_NAME));
			Iterator<LauProductAddresses> itr = reports.iterator();
				
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				log.info("save Prod address 2--------> ");
				LauProductAddresses lauProductAddresses = (LauProductAddresses)itr.next();
				log.info("itr.hasNext() -> " + lauProductAddresses.toString());
				this.productAddress	= null;
				this.productAddress	= lauProductAddresses;
				if(productAddress.getPRODUCT_ADDRESS_ID() == 0)	{
					log.info("save Prod address 3--------> ");
						insert(template,reportId,productMap);
				}else  {
					if(productAddress.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else
						log.info("save Prod address 4--------> ");
						update(template);				
				}
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_PRODUCT_ADDRESSES_PARAM_NAME + " not found in request");
		}
		
	}
	
	public void insert(SimpleJdbcTemplate template, long reportId, HashMap<String, List<Long>> productMap)
			throws Exception {
		int id = 0;
		long productAddressId = CommonDAO.getPrimaryKey(template);
		this.productAddress.setPRODUCT_ADDRESS_ID(productAddressId);
		if(this.productAddress.getREPORT_ID() == 0)	{
			this.productAddress.setREPORT_ID(reportId);	}
		log.info("Product Id 1------> " + this.productAddress.getPRODUCT_ID());	
		if(productMap != null && this.productAddress.getPRODUCT_ID() == 0 
				&& productMap.size() > 0) {			
			//List<Long> list = productMap.get(this.productAddress.getProductName()+this.productAddress.getProductType()+this.productAddress.getDISPLAY_NUMBER());
			List<Long> list = productMap.get(this.productAddress.getINTERNAL_LINK_ID());
			this.productAddress.setPRODUCT_ID(list.get(1));
		}	
		log.info("Generated Primary Key for DOSE_ID ->" + productAddressId);
		log.info("Product Id 2------> " + this.productAddress.getPRODUCT_ID());	
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("LauProductAddressesSetDAO insert() ID -> " + id);
		
	}

	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauProductAddressesSetDAO update() ID -> " + id);	
		
	}
	
	private Object[] getParameters()
	{
		LauProductAddresses lauProductAddresses = this.productAddress; 
		return new Object[]{
				lauProductAddresses.getDISPLAY_NUMBER(),
				lauProductAddresses.getPRODUCT_ADDRESS_TYPE(),
				lauProductAddresses.getPROD_ADDRESSEE_TITLE(),
				lauProductAddresses.getPROD_ADDRESSEE_FIRST_NAME(),
				lauProductAddresses.getPROD_ADDRESSEE_LAST_NAME(),
				lauProductAddresses.getPRODUCT_EMAIL(),
				lauProductAddresses.getPROD_ADDRESSEE_CO_NAME(),
				lauProductAddresses.getPROD_ADDRESSEE_MIDDLE_NAME(),
				lauProductAddresses.getPRODUCT_DEPARTMENT(),
				lauProductAddresses.getPRODUCT_ORGANIZATION(),
				lauProductAddresses.getPRODUCT_ADDRESS1(),
				lauProductAddresses.getPRODUCT_ADDRESS2(),
				lauProductAddresses.getPRODUCT_ADDRESS3(),
				lauProductAddresses.getPRODUCT_ADDRESS4(),
				lauProductAddresses.getPRODUCT_CITY(),
				lauProductAddresses.getPRODUCT_STATE(),
				lauProductAddresses.getPRODUCT_COUNTRY(),
				lauProductAddresses.getPRODUCT_POSTAL_CODE(),
				lauProductAddresses.getPROD_ADDRESSEE_PHONE_CC(),
				lauProductAddresses.getPROD_ADDRESSEE_PHONE_NO(),
				lauProductAddresses.getPROD_ADDRESSEE_PHONE_EXT(),
				lauProductAddresses.getPROD_ADDRESSEE_MOBILE_CC(),
				lauProductAddresses.getPROD_ADDRESSEE_MOBILE_NO(),
				lauProductAddresses.getPROD_ADDRESSEE_MOBILE_EXT(),
				lauProductAddresses.getPROD_ADDRESSEE_FAX_CC(),
				lauProductAddresses.getPROD_ADDRESSEE_FAX_NO(),
				lauProductAddresses.getPROD_ADDRESSEE_FAX_EXT(),
				userId,
				dt,
				lauProductAddresses.getPRODUCT_ADDRESS_ID(),
				lauProductAddresses.getPRODUCT_ID(),
				lauProductAddresses.getREPORT_ID()			
		};
	}
	
	public void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag = "ROWSET/ROW";
		reports				= new Vector<LauProductAddresses>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauProductAddresses.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_NAME", "productName" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_TYPE", "productType" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_ADDRESS_ID", "PRODUCT_ADDRESS_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_ID", "PRODUCT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "REPORT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISPLAY_NUMBER", "DISPLAY_NUMBER" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_ADDRESS_TYPE", "PRODUCT_ADDRESS_TYPE" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_TITLE", "PROD_ADDRESSEE_TITLE" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_FIRST_NAME", "PROD_ADDRESSEE_FIRST_NAME" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_LAST_NAME", "PROD_ADDRESSEE_LAST_NAME" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_EMAIL", "PRODUCT_EMAIL" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_CO_NAME", "PROD_ADDRESSEE_CO_NAME" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_DEPARTMENT2", "PRODUCT_DEPARTMENT2" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_ORGANIZATION2", "PRODUCT_ORGANIZATION2" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_ADDRESS1", "PRODUCT_ADDRESS1" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_ADDRESS2", "PRODUCT_ADDRESS2" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_ADDRESS3", "PRODUCT_ADDRESS3" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_ADDRESS4", "PRODUCT_ADDRESS4" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_CITY", "PRODUCT_CITY" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_STATE", "PRODUCT_STATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_POSTAL_CODE", "PRODUCT_POSTAL_CODE" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_COUNTRY", "PRODUCT_COUNTRY" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_PHONE_CC", "PROD_ADDRESSEE_PHONE_CC" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_PHONE_NO", "PROD_ADDRESSEE_PHONE_NO" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_PHONE_EXT", "PROD_ADDRESSEE_PHONE_EXT" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_MIDDLE_NAME2", "PROD_ADDRESSEE_MIDDLE_NAME2" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_MOBILE_CC2", "PROD_ADDRESSEE_MOBILE_CC" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_MOBILE_NO2", "PROD_ADDRESSEE_MOBILE_NO" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_MOBILE_EXT2", "PROD_ADDRESSEE_MOBILE_EXT" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_FAX_CC2", "PROD_ADDRESSEE_FAX_CC2" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_FAX_NO2", "PROD_ADDRESSEE_FAX_NO2" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_FAX_EXT2", "PROD_ADDRESSEE_FAX_EXT2" );
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
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_MIDDLE_NAME", "PROD_ADDRESSEE_MIDDLE_NAME" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_ORGANIZATION", "PRODUCT_ORGANIZATION" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_DEPARTMENT", "PRODUCT_DEPARTMENT" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_MOBILE_CC", "PROD_ADDRESSEE_MOBILE_CC" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_MOBILE_NO", "PROD_ADDRESSEE_MOBILE_NO" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_MOBILE_EXT", "PROD_ADDRESSEE_MOBILE_EXT" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_FAX_CC", "PROD_ADDRESSEE_FAX_CC" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_FAX_NO", "PROD_ADDRESSEE_FAX_NO" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_FAX_EXT", "PROD_ADDRESSEE_FAX_EXT" );
		//digester.addBeanPropertySetter( mainXmlTag+"/PROD_ADDRESSEE_EMAIL", "PROD_ADDRESSEE_EMAIL" );	
		digester.addBeanPropertySetter( mainXmlTag+"/INTERNAL_LINK_ID", "INTERNAL_LINK_ID");
						
		//Move to next LAU_PRODUCT_ADDRESSES
		digester.addSetNext( mainXmlTag, "addDosingXmlData" );		
		digester.parse(new StringReader(xml));
		
	}

	public void addDosingXmlData(LauProductAddresses lauProductAddresses) {
		reports.add(lauProductAddresses);
		log.info(lauProductAddresses.toString());
	}
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_PRODUCT_ADDRESSES SET DISPLAY_NUMBER=?,PRODUCT_ADDRESS_TYPE=?,PROD_ADDRESSEE_TITLE=?,PROD_ADDRESSEE_FIRST_NAME=?,"
			+ "PROD_ADDRESSEE_LAST_NAME=?,PRODUCT_EMAIL=?,PROD_ADDRESSEE_CO_NAME=?,PROD_ADDRESSEE_MIDDLE_NAME=?,PRODUCT_DEPARTMENT=?,PRODUCT_ORGANIZATION=?,"
			+ "PRODUCT_ADDRESS1=?,PRODUCT_ADDRESS2=?,PRODUCT_ADDRESS3=?,PRODUCT_ADDRESS4=?,PRODUCT_CITY=?,PRODUCT_STATE=?," +
			"PRODUCT_COUNTRY=?,PRODUCT_POSTAL_CODE=?,PROD_ADDRESSEE_PHONE_CC=?,PROD_ADDRESSEE_PHONE_NO=?," +
			"PROD_ADDRESSEE_PHONE_EXT=?,PROD_ADDRESSEE_MOBILE_CC=?,PROD_ADDRESSEE_MOBILE_NO=?," +
			"PROD_ADDRESSEE_MOBILE_EXT=?,PROD_ADDRESSEE_FAX_CC=?,PROD_ADDRESSEE_FAX_NO=?,PROD_ADDRESSEE_FAX_EXT=?,UPDATE_USER_ID=?, UPDATE_TIMESTAMP =?"
			+ " WHERE PRODUCT_ADDRESS_ID=? AND PRODUCT_ID=? AND REPORT_ID=?";
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_PRODUCT_ADDRESSES "
			+ "(DISPLAY_NUMBER,PRODUCT_ADDRESS_TYPE,PROD_ADDRESSEE_TITLE,"
			+ "PROD_ADDRESSEE_FIRST_NAME,PROD_ADDRESSEE_LAST_NAME,"
			+ "PRODUCT_EMAIL,PROD_ADDRESSEE_CO_NAME,PROD_ADDRESSEE_MIDDLE_NAME,PRODUCT_DEPARTMENT,PRODUCT_ORGANIZATION,"
			+ "PRODUCT_ADDRESS1,PRODUCT_ADDRESS2,PRODUCT_ADDRESS3,PRODUCT_ADDRESS4,PRODUCT_CITY,PRODUCT_STATE," +
			"PRODUCT_COUNTRY,PRODUCT_POSTAL_CODE,PROD_ADDRESSEE_PHONE_CC,PROD_ADDRESSEE_PHONE_NO," +
			"PROD_ADDRESSEE_PHONE_EXT,PROD_ADDRESSEE_MOBILE_CC,PROD_ADDRESSEE_MOBILE_NO," +
			"PROD_ADDRESSEE_MOBILE_EXT,PROD_ADDRESSEE_FAX_CC,PROD_ADDRESSEE_FAX_NO,PROD_ADDRESSEE_FAX_EXT," +
			"UPDATE_USER_ID,UPDATE_TIMESTAMP,PRODUCT_ADDRESS_ID,PRODUCT_ID,REPORT_ID) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_PRODUCT_ADDRESSES WHERE PRODUCT_ADDRESS_ID = ?",this.productAddress.getPRODUCT_ADDRESS_ID());
		log.info("LauProductAddressesSetDAO delete() ID -> " + id);		
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
	 
	 	LAU_PRODUCT_ADDRESSES
	 
					PRODUCT_ADDRESS_ID
					PRODUCT_ID
					REPORT_ID
					DISPLAY_NUMBER
					PRODUCT_ADDRESS_TYPE
					PROD_ADDRESSEE_TITLE
					PROD_ADDRESSEE_FIRST_NAME
					PROD_ADDRESSEE_LAST_NAME
					PROD_ADDRESSEE_CO_NAME
	 
	 */

}
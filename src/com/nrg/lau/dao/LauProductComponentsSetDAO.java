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

import com.nrg.lau.beans.LauProductComponents;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauProductComponentsSetDAO implements IReportChildSetDAO<LauProductComponents>{
	
	private static Logger log			= Logger.getLogger(LauProductComponentsSetDAO.class);
	private LauProductComponents productComponents	= null;	
	private Vector<LauProductComponents> reports	= null;
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";	
	
	protected void finalize() throws Throwable {		
		this.reports.removeAllElements();
		this.reports = null;
		super.finalize();
	}
	
	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, HashMap<String, List<Long>> productMap,String user,java.sql.Timestamp dstamp) throws Exception {
		userId 	= user;
		dt	 	= dstamp;
		
		if(request.getParameter(IReportsConstants.LAU_PRODUCT_COMPONENT_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_PRODUCT_COMPONENT_PARAM_NAME).length() > 0)	{
		
			log.info("LauProductComponentsSetDAO save() LAU_PRODUCT_COMPONENT_PARAM_NAME -> " 
				+ request.getParameter(IReportsConstants.LAU_PRODUCT_COMPONENT_PARAM_NAME));
			
			log.info(productMap + " ====   Test----");
			
			//Create LauProductComponents beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_PRODUCT_COMPONENT_PARAM_NAME));	
			Iterator<LauProductComponents> itr = reports.iterator();
			
			//PRODUCT_COMPONENT_ID is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauProductComponents lauProductComponents = (LauProductComponents)itr.next();
				log.info("itr.hasNext() -> " + lauProductComponents.toString());
				this.productComponents	= null;
				this.productComponents	= lauProductComponents;			
				
				if(productComponents.getPRODUCT_COMPONENT_ID() == 0)	{
					log.info("Inside");
						insert(template,reportId,productMap);
				}else  {
					if(productComponents.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else	update(template);				
				}
				
			}//end of while(itr.hasNext())
			
		}	else	{
			log.info(IReportsConstants.LAU_PRODUCT_COMPONENT_PARAM_NAME + " not found in request");
		}
		
	}
	
	public void insert(SimpleJdbcTemplate template, long reportId, HashMap<String, List<Long>> productMap)
			throws Exception {
		log.info("1===");
		int id = 0;
		long productComponentsID = CommonDAO.getPrimaryKey(template);
		this.productComponents.setPRODUCT_COMPONENT_ID(productComponentsID);
		if (this.productComponents.getREPORT_ID() == 0) {
			this.productComponents.setREPORT_ID(reportId);
		}
		if (this.productComponents.getPRODUCT_ID() == 0) {
			if (productMap != null && productMap.size() > 0) {

				Iterator iter = productMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry mEntry = (Map.Entry) iter.next();
					log.info(mEntry.getKey() + " : " + mEntry.getValue());
				}

				log.info("2----");
				log.info(this.productComponents.getProductName()
						+ this.productComponents.getProductType()
						+ this.productComponents.getDISPLAY_NUMBER() + " ===");
				/*List<Long> list = productMap.get(this.productComponents
						.getProductName()
						+ this.productComponents.getProductType()
						+ this.productComponents.getDISPLAY_NUMBER());*/
				List<Long> list = productMap.get(this.productComponents.getINTERNAL_LINK_ID());
				this.productComponents.setPRODUCT_ID(list.get(1));
			} else {
				throw new Exception("PRODUCT_ID not available");
			}
		}
		log.info("Generated Primary Key for PRODUCT_COMPONENT_ID ->" + productComponentsID);
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("LauProductComponentsSetDAO insert() ID -> " + id);
		
	}

	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauProductComponentsDetailsSetDAO update() ID -> " + id);	
		
	}
	
	private Object[] getParameters()
	{
		LauProductComponents lauProductComponents = this.productComponents; 
		return new Object[]{				
			lauProductComponents.getCOMPONENT_UDI(),
			lauProductComponents.getCOMPONENT_DUNS_NUMBER(),
			lauProductComponents.getCUSTOM_TEXT_01(),
			lauProductComponents.getCUSTOM_TEXT_02(),
			lauProductComponents.getCUSTOM_TEXT_03(),
			lauProductComponents.getCUSTOM_TEXT_04(),
			lauProductComponents.getCUSTOM_TEXT_05(),
			lauProductComponents.getCUSTOM_TEXT_06(),
			lauProductComponents.getCUSTOM_TEXT_07(),
			lauProductComponents.getCUSTOM_TEXT_08(),
			lauProductComponents.getCUSTOM_TEXT_09(),
			lauProductComponents.getCUSTOM_TEXT_10(),
			lauProductComponents.getCUSTOM_DATE_01(),
			lauProductComponents.getCUSTOM_DATE_02(),
			lauProductComponents.getCUSTOM_DATE_03(),
			lauProductComponents.getCUSTOM_DATE_04(),
			lauProductComponents.getCUSTOM_DATE_05(),
			lauProductComponents.getCUSTOM_COMMENT_01(),
			lauProductComponents.getCUSTOM_COMMENT_02(),
			userId,
			dt,
			lauProductComponents.getCODING_SOURCE(),
			lauProductComponents.getCOMPONENT_CODE(),
			lauProductComponents.getREPORT_ID(),
			lauProductComponents.getPRODUCT_COMPONENT_ID(),
			lauProductComponents.getPRODUCT_ID()		
		};
	}
	
	public void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag = "ROWSET/ROW";		
		reports				= new Vector<LauProductComponents>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauProductComponents.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/COMPONENT_UDI", "COMPONENT_UDI" );
		digester.addBeanPropertySetter( mainXmlTag+"/COMPONENT_DUNS_NUMBER", "COMPONENT_DUNS_NUMBER" );
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
		digester.addBeanPropertySetter( mainXmlTag+"/CODING_SOURCE", "CODING_SOURCE" );
		digester.addBeanPropertySetter( mainXmlTag+"/COMPONENT_CODE", "COMPONENT_CODE" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_COMPONENT_ID", "PRODUCT_COMPONENT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "REPORT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_ID", "PRODUCT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISPLAY_NUMBER", "DISPLAY_NUMBER" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_TYPE", "productType" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_NAME", "productName" );
		digester.addBeanPropertySetter( mainXmlTag+"/INTERNAL_LINK_ID", "INTERNAL_LINK_ID");
						
		//Move to next LauProductComponentsDetails
		digester.addSetNext( mainXmlTag, "addDosingXmlData" );		
		digester.parse(new StringReader(xml));
		
	}

	public void addDosingXmlData(LauProductComponents lauProductComponents) {
		reports.add(lauProductComponents);
		log.info(lauProductComponents.toString());
	}
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_PRODUCT_COMPONENTS SET COMPONENT_UDI=?,COMPONENT_DUNS_NUMBER=?,CUSTOM_TEXT_01=?,CUSTOM_TEXT_02=?," +
			"CUSTOM_TEXT_03=?,CUSTOM_TEXT_04=?,CUSTOM_TEXT_05=?,CUSTOM_TEXT_06=?,CUSTOM_TEXT_07=?,CUSTOM_TEXT_08=?,CUSTOM_TEXT_09=?,CUSTOM_TEXT_10=?," +
			"CUSTOM_DATE_01=?,CUSTOM_DATE_02=?,CUSTOM_DATE_03=?,CUSTOM_DATE_04=?,CUSTOM_DATE_05=?,CUSTOM_COMMENT_01=?,CUSTOM_COMMENT_02=?," +
			"UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?,CODING_SOURCE=?,COMPONENT_CODE=?,REPORT_ID=? WHERE PRODUCT_COMPONENT_ID=? AND PRODUCT_ID=?";
	
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_PRODUCT_COMPONENTS (COMPONENT_UDI,COMPONENT_DUNS_NUMBER,CUSTOM_TEXT_01,CUSTOM_TEXT_02," +
			"CUSTOM_TEXT_03,CUSTOM_TEXT_04,CUSTOM_TEXT_05,CUSTOM_TEXT_06,CUSTOM_TEXT_07,CUSTOM_TEXT_08,CUSTOM_TEXT_09,CUSTOM_TEXT_10,CUSTOM_DATE_01," +
			"CUSTOM_DATE_02,CUSTOM_DATE_03,CUSTOM_DATE_04,CUSTOM_DATE_05,CUSTOM_COMMENT_01,CUSTOM_COMMENT_02,UPDATE_USER_ID,UPDATE_TIMESTAMP," +
			"CODING_SOURCE,COMPONENT_CODE,REPORT_ID,PRODUCT_COMPONENT_ID,PRODUCT_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_PRODUCT_COMPONENTS WHERE PRODUCT_COMPONENT_ID = ?",this.productComponents.getPRODUCT_COMPONENT_ID());
		log.info("LauProductComponentsDetailsSetDAO delete() ID -> " + id);		
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
	  	LAU_PRODUCT_COMPONENTS
	 
	 	COMPONENT_UDI
		COMPONENT_DUNS_NUMBER
		CUSTOM_TEXT_01
		CUSTOM_TEXT_02
		CUSTOM_TEXT_03
		CUSTOM_TEXT_04
		CUSTOM_TEXT_05
		CUSTOM_TEXT_06
		CUSTOM_TEXT_07
		CUSTOM_TEXT_08
		CUSTOM_TEXT_09
		CUSTOM_TEXT_10
		CUSTOM_DATE_01
		CUSTOM_DATE_02
		CUSTOM_DATE_03
		CUSTOM_DATE_04
		CUSTOM_DATE_05
		CUSTOM_COMMENT_01
		CUSTOM_COMMENT_02
		UPDATE_USER_ID
		UPDATE_TIMESTAMP
		CODING_SOURCE
		COMPONENT_CODE
		REPORT_ID
		PRODUCT_COMPONENT_ID
		PRODUCT_ID
	 
	 */

}

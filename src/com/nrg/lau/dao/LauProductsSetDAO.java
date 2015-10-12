package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauProducts;
import com.nrg.lau.commons.IReportChildRtnSetDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauProductsSetDAO implements IReportChildRtnSetDAO<LauProducts>{
	
	private static Logger log	= Logger.getLogger(LauProductsSetDAO.class);
	private Vector<LauProducts> reports				= null;
	private LauProducts products 					= null;
	private HashMap<String, List<Long>> productMap 	= null;
	private HashMap<Long, List<Long>> productDeleteMap 	= null;
	public List<Object> finalList = null;
	private java.sql.Timestamp dt  = null;  
	private String userId	= "";
	protected void finalize() throws Throwable {		
		this.reports.removeAllElements();
		this.reports = null;		
		super.finalize();
	}
	
	
	public List<Object> save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId,String user,java.sql.Timestamp dstamp) throws Exception {
		userId = user;
		dt = dstamp;
		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_PRODUCTS_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_PRODUCTS_PARAM_NAME).length() > 0) {

			log.info("LauProductsDAO save() LAU_PRODUCTS_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_PRODUCTS_PARAM_NAME));
			productDeleteMap = new HashMap<Long, List<Long>>();
			productMap = new HashMap<String, List<Long>>();
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_PRODUCTS_PARAM_NAME));

			Iterator<LauProducts> itr = this.reports.iterator();

			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauProducts lauProducts = (LauProducts)itr.next();
				log.info("itr.hasNext() -> " + lauProducts.toString());
				this.products	= null;
				this.products	= lauProducts;
				if(lauProducts.getProductId() == 0	)	{
					insert(template,reportId);
				}else   {
					if(lauProducts.getTransactionType() == IReportsConstants.deleteFlag)	{
						log.info("Product delete section");
						List<Long> list = new ArrayList<Long>();
						list.add(lauProducts.getTransactionType());
						list.add(lauProducts.getProductId());
						list.add(lauProducts.getReportId());
						//productMap.put(lauProducts.getProductName()+lauProducts.getProductType()+lauProducts.getDisplayNumber(), list);
						//productMap.put(lauProducts.getINTERNAL_LINK_ID(), list);
						productDeleteMap.put(lauProducts.getProductId(), list);
					}else	update(template);				
				}
			}//end of while(itr.hasNext())
		}
	    finalList = new ArrayList<Object>();
	    finalList.add(productDeleteMap);
	    finalList.add(productMap);
	    log.info("before returning, List Size: "+finalList.size());
		return finalList;

	}
	
//	private void returnData(HashMap<String, List<Long>> productMap,
//			HashMap<Long, List<Long>> productDeleteMap) {
//		finalMap = new HashMap<Object, List<Long>>();
//		finalMap.putAll(productMap);
//		finalMap.putAll(productDeleteMap);//(finalMap:productMap,productDeleteMap)
//		
//	}


	/*public HashMap<Long, List<Long>> saveDelete(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId,String user,java.sql.Timestamp dstamp) throws Exception {
		userId = user;
		dt = dstamp;
		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_PRODUCTS_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_PRODUCTS_PARAM_NAME).length() > 0) {
			
			log.info("LauProductsDAO saveDelete() LAU_PRODUCTS_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_PRODUCTS_PARAM_NAME));
		
			productDeleteMap = new HashMap<Long, List<Long>>();
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_PRODUCTS_PARAM_NAME));
			
			Iterator<LauProducts> itr = this.reports.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauProducts lauProducts = (LauProducts)itr.next();
				log.info("itr.hasNext() -> " + lauProducts.toString());
				this.products	= null;
				this.products	= lauProducts;
				
				if(lauProducts.getTransactionType() == IReportsConstants.deleteFlag)	{
						log.info("Product delete section");
						List<Long> list = new ArrayList<Long>();
						list.add(lauProducts.getTransactionType());
						list.add(lauProducts.getProductId());
						list.add(lauProducts.getReportId());
						//productMap.put(lauProducts.getProductName()+lauProducts.getProductType()+lauProducts.getDisplayNumber(), list);
					
						productDeleteMap.put(lauProducts.getProductId(), list);
					}				
				}
			//end of while(itr.hasNext())
		}
		return productDeleteMap;
		
	}*/
	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {
		int id = 0;
		long productId = CommonDAO.getPrimaryKey(template);
		this.products.setProductId(productId);
		if(this.products.getReportId() == 0)	{
			this.products.setReportId(reportId);	}
		List<Long> list = new ArrayList<Long>();
		list.add(this.products.getTransactionType());
		list.add(this.products.getProductId());
		//productMap.put(this.products.getProductName(), list);
		//productMap.put(this.products.getProductName()+this.products.getProductType()+this.products.getDisplayNumber(), list);
		productMap.put(this.products.getINTERNAL_LINK_ID(), list);
		log.info("Generated Primary Key for PRODUCT_ID ->" + productId);
		id = template.update(SQL_INSERT_STRING,getInsertParameters(template));
		log.info("LauProductsDAO insert() ID -> " + id);
		
	}

	
	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		List<Long> list = new ArrayList<Long>();
		list.add(this.products.getTransactionType());
		list.add(this.products.getProductId());
		productMap.put(this.products.getINTERNAL_LINK_ID(), list);
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauProductsDAO update() ID -> " + id);	
		
	}
	
	private Object[] getParameters()
	{
		LauProducts lauProducts = this.products; 
		return new Object[]{
			lauProducts.getDisplayNumber(),
			lauProducts.getProductType(),
			lauProducts.getMarketedProductType(),
			lauProducts.getIndicationVerbatim(),
			lauProducts.getActionTaken(),
			lauProducts.getNdcNumber(),
			lauProducts.getStrength(),
			lauProducts.getSampleDisposition(),
			lauProducts.getReimbursementType(),
			lauProducts.getReimbursementTo(),
			lauProducts.getReimbursementQuantity(),
			lauProducts.getReimbursementAmount(),
			lauProducts.getReimbursementAccount(),
			lauProducts.getCOMBINATION_PRODUCT(),
			userId,dt,
			lauProducts.getDeaNumber(),
			lauProducts.getProductName(),
			lauProducts.getTradeName(),
			lauProducts.getGenericName(),
			lauProducts.getProdCode(),
			lauProducts.getGLOBAL_UNIQUE_ID(),
			lauProducts.getProductId(),
			lauProducts.getReportId()
		};
	}
	private Object[] getInsertParameters(SimpleJdbcTemplate template)
	{
		LauProducts lauProducts = this.products; 
		return new Object[]{
			CommonDAO.getNextDisplayNumber(template, lauProducts.getReportId(), "LAU_PRODUCTS",999),
			lauProducts.getProductType(),
			lauProducts.getMarketedProductType(),
			lauProducts.getIndicationVerbatim(),
			lauProducts.getActionTaken(),
			lauProducts.getNdcNumber(),
			lauProducts.getStrength(),
			lauProducts.getSampleDisposition(),
			lauProducts.getReimbursementType(),
			lauProducts.getReimbursementTo(),
			lauProducts.getReimbursementQuantity(),
			lauProducts.getReimbursementAmount(),
			lauProducts.getReimbursementAccount(),
			lauProducts.getCOMBINATION_PRODUCT(),
			userId,dt,
			lauProducts.getDeaNumber(),
			lauProducts.getProductId(),
			lauProducts.getReportId(),
			lauProducts.getProductName(),
			lauProducts.getTradeName(),
			lauProducts.getGenericName(),
			lauProducts.getProdCode(),
			lauProducts.getGLOBAL_UNIQUE_ID()
		};
	}	
	
	public void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag = "ROWSET/ROW";
		reports				= new Vector<LauProducts>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauProducts.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_ID", "productId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_NAME", "productName" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRADE_NAME", "tradeName" );
		digester.addBeanPropertySetter( mainXmlTag+"/GENERIC_NAME", "genericName" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_CODE", "prodCode" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISPLAY_NUMBER", "displayNumber2" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_TYPE", "productType" );	
		digester.addBeanPropertySetter( mainXmlTag+"/MARKETED_PRODUCT_TYPE", "marketedProductType" );
		digester.addBeanPropertySetter( mainXmlTag+"/GLOBAL_UNIQUE_ID", "GLOBAL_UNIQUE_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/INDICATION_VERBATIM", "indicationVerbatim" );		
		digester.addBeanPropertySetter( mainXmlTag+"/ACTION_TAKEN", "actionTaken" );		
		digester.addBeanPropertySetter( mainXmlTag+"/NDC_NUMBER", "ndcNumber" );
		digester.addBeanPropertySetter( mainXmlTag+"/STRENGTH", "strength" );
		digester.addBeanPropertySetter( mainXmlTag+"/SAMPLE_DISPOSITION", "sampleDisposition" );
		digester.addBeanPropertySetter( mainXmlTag+"/REIMBURSEMENT_TYPE", "reimbursementType" );
		digester.addBeanPropertySetter( mainXmlTag+"/REIMBURSEMENT_TO", "reimbursementTo" );		
		digester.addBeanPropertySetter( mainXmlTag+"/REIMBURSEMENT_QUANTITY", "reimbursementQuantity" );
		digester.addBeanPropertySetter( mainXmlTag+"/REIMBURSEMENT_AMOUNT", "reimbursementAmount" );
		digester.addBeanPropertySetter( mainXmlTag+"/REIMBURSEMENT_ACCOUNT", "reimbursementAccount" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEA_NUMBER", "deaNumber" );		
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "updateUserId" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_REVIEW_STATUS", null );//any time update/insert, set PRODUCT_REVIEW_STATUS  to null
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_REVIEW_USER_ID", null );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_REVIEW_DATE", null);
		digester.addBeanPropertySetter( mainXmlTag+"/INTERNAL_LINK_ID", "INTERNAL_LINK_ID");
		digester.addBeanPropertySetter( mainXmlTag+"/COMBINATION_PRODUCT", "COMBINATION_PRODUCT");		
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));
		
	}
	
	public void addXmlData(LauProducts lauProducts) {
		reports.add(lauProducts);
		log.info(lauProducts.toString());
	}	
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_PRODUCTS SET DISPLAY_NUMBER=?,PRODUCT_TYPE=?,MARKETED_PRODUCT_TYPE=?," +
		"INDICATION_VERBATIM=?,ACTION_TAKEN=?,NDC_NUMBER=?,STRENGTH=?,SAMPLE_DISPOSITION=?,REIMBURSEMENT_TYPE=?," +
		"REIMBURSEMENT_TO=?,REIMBURSEMENT_QUANTITY=?,REIMBURSEMENT_AMOUNT=?,REIMBURSEMENT_ACCOUNT=?,COMBINATION_PRODUCT=?,UPDATE_USER_ID=?," +
		"UPDATE_TIMESTAMP=?,DEA_NUMBER=?, PRODUCT_NAME=?,TRADE_NAME=?,GENERIC_NAME=?,PRODUCT_CODE=?,GLOBAL_UNIQUE_ID=? "+
		"  WHERE PRODUCT_ID=? AND REPORT_ID=? ";
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_PRODUCTS (DISPLAY_NUMBER,PRODUCT_TYPE,MARKETED_PRODUCT_TYPE," +
		"INDICATION_VERBATIM,ACTION_TAKEN,NDC_NUMBER,STRENGTH,SAMPLE_DISPOSITION,REIMBURSEMENT_TYPE,REIMBURSEMENT_TO," +
		"REIMBURSEMENT_QUANTITY,REIMBURSEMENT_AMOUNT,REIMBURSEMENT_ACCOUNT,COMBINATION_PRODUCT,UPDATE_USER_ID,UPDATE_TIMESTAMP," +
		"DEA_NUMBER,PRODUCT_ID,REPORT_ID,PRODUCT_NAME,TRADE_NAME, GENERIC_NAME,PRODUCT_CODE,GLOBAL_UNIQUE_ID) " +
		" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public void delete(SimpleJdbcTemplate template) throws Exception {
		//Delete action is performed @ LauReportsSetTransactionManager.validateParentRecordDeletion()
		
	}
	
	/**
	  	
	  	LAU_PRODUCTS
	  	
	  	PRODUCT_ID NUMBER Yes 
		REPORT_ID NUMBER Yes 
		DISPLAY_NUMBER NUMBER Yes 
		PRODUCT_NAME VARCHAR2(500) Yes 
		PRODUCT_TYPE VARCHAR2(50) Yes 
		INDICATION_VERBATIM VARCHAR2(500) Yes 
		ACTION_TAKEN VARCHAR2(50) Yes 
		NDC_NUMBER VARCHAR2(100) Yes 
		STRENGTH VARCHAR2(100) Yes 
		SAMPLE_DISPOSITION VARCHAR2(50) Yes 
		REIMBURSEMENT_TYPE VARCHAR2(50) Yes 
		REIMBURSEMENT_TO VARCHAR2(50) Yes 
		REIMBURSEMENT_QUANTITY VARCHAR2(100) Yes 
		REIMBURSEMENT_AMOUNT VARCHAR2(100) Yes 
		REIMBURSEMENT_ACCOUNT VARCHAR2(100) Yes 
		UPDATE_USER_ID VARCHAR2(300) Yes 
		UPDATE_TIMESTAMP TIMESTAMP(0) WITH LOCAL TIME ZONE Yes 
		DEA_NUMBER VARCHAR2(100) Yes 
	 
	 */
	
}

package com.nrg.lau.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauSiebelProducts;
import com.nrg.lau.commons.Constants;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelBusObject;
import com.siebel.data.SiebelDataBean;

public class LauSiebelProductsSetDAO {

	private static Logger log = Logger.getLogger(LauSiebelProductsSetDAO.class);
	private List<String> productList = null;
	private String sMarket = "";
	private  Map<String, Integer> productMap	= new HashMap<String, Integer>();
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	private  long displayNumber = 1000;
	private long doseDisplayNumber = 1000;
	public void save(HttpServletRequest request,
			SimpleJdbcTemplate template, long reportId, SiebelDataBean dataBean,String sMarket,String user,java.sql.Timestamp dstamp)
			throws Exception {
		userId = user;
		dt = dstamp;
		this.sMarket = sMarket;
		log.info("Market is ..."+sMarket);
		if (request.getParameter(Constants.SIEBEL_REQ_USER_ID) != null
				&& request.getParameter(Constants.SIEBEL_REQ_ACTIVITY_ID) != null
				&& request.getParameter(Constants.SIEBEL_REQ_CUST_NO) != null
				&& request.getParameter(Constants.SIEBEL_REQ_CON_ID) != null
				&& reportId != 0) {
			LauReferenceListDetailsGetDAO lauReferenceListDetailsGetDAO = new LauReferenceListDetailsGetDAO();
			   productList =  lauReferenceListDetailsGetDAO.getCodeValues(template, "PRODUCT");
			getSiebelData(dataBean, template, reportId, request
					.getParameter(Constants.SIEBEL_REQ_CON_ID));
		} else {
			log
					.info("LauSiebelProductsSetDAO -> Siebel request parameters not available!");
		}
		//return productMap;
	}

	private LauSiebelProducts setSiebelbean(SiebelBusComp prodComp)
			throws Exception {

		LauSiebelProducts lauSiebelProducts = new LauSiebelProducts();
		lauSiebelProducts.setProductName(prodComp.getFieldValue("Therapy Stage"));
		lauSiebelProducts.setProductIndication(this.sMarket);
		lauSiebelProducts.setStartDate(prodComp.getFieldValue("Date Start Drug"));
		lauSiebelProducts.setStopDate(prodComp.getFieldValue("Date End Drug"));
		lauSiebelProducts.setOngoingFlag(prodComp.getFieldValue("Status"));

		log.info(prodComp.getFieldValue("Therapy Stage"));
		log.info(prodComp.getFieldValue("Date Start Drug"));
		log.info(prodComp.getFieldValue("Date End Drug"));
		log.info(prodComp.getFieldValue("SSA Primary Field"));
		log.info(prodComp.getFieldValue("Status"));
		log.info(this.sMarket);

		return lauSiebelProducts;
	}

	private void getSiebelData(SiebelDataBean dataBean,
			SimpleJdbcTemplate template, long reportId,
			String contactId) throws Exception {
		Boolean bProdFirst = false;
		LauSiebelProducts lauSiebelProducts = null;
		SiebelBusObject busObject = dataBean.getBusObject("Contact");
		SiebelBusComp busComp = busObject.getBusComp("Contact");

		busComp.setViewMode(3);
		busComp.clearToQuery();
		busComp.activateField("Id");
		// busComp.setSearchSpec("ContactId from the URL", contactId);
		busComp.setSearchSpec("Id", contactId);
		busComp.setSortSpec("Created(DESCENDING)");
		//busComp.setSortSpec("SSA Primary Field(DESENDING)");
		busComp.executeQuery(true);
		if (busComp.firstRecord()) {
			SiebelBusComp prodComp = busComp.getMVGBusComp("Therapy BIIB");
			prodComp.clearToQuery();
			prodComp.activateField("Date Start Drug");
			prodComp.activateField("Date End Drug");
			prodComp.activateField("SSA Primary Field");
			prodComp.activateField("Status");
			//prodComp.setSortSpec("SSA Primary Field(DESENDING)");
			prodComp.executeQuery(true);
			bProdFirst = prodComp.firstRecord();
			while (bProdFirst) {
				lauSiebelProducts = setSiebelbean(prodComp);
				
				 log.info("getSiebelData() - LauSiebelProducts -> " +
				lauSiebelProducts.toString());
				 if(productList != null && productList.contains(lauSiebelProducts.getProductName().trim().toUpperCase())) { 
					 insert(template, reportId,lauSiebelProducts);
				 }
				 else
					 log.info("product skipped..."+lauSiebelProducts.getProductName());
				 
				bProdFirst = prodComp.nextRecord();
			}

		}
	}

	private final String SQL_INSERT_STRING = "INSERT INTO LAU_PRODUCTS (PRODUCT_NAME,PRODUCT_ID,DISPLAY_NUMBER,REPORT_ID,"
			+ "UPDATE_TIMESTAMP,UPDATE_USER_ID,PRODUCT_TYPE,INDICATION_VERBATIM) VALUES (?,?,?,?,?,?,?,?)";
	
	private final String SQL_DOSE_INSERT_STRING = "INSERT INTO LAU_DOSING (START_DATE,STOP_DATE,DISPLAY_NUMBER," +	
	"DOSE,DOSE_UNIT,ROUTE,FREQUENCY,FORMULATION,ONGOING_FLAG,UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID,PRODUCT_ID,DOSE_ID) " +
	"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";	

	public void insert(SimpleJdbcTemplate template, long reportId,
			LauSiebelProducts siebelProducts) throws Exception {

		int id = 0;
		//long productId = CommonDAO.getPrimaryKey(template);

		LauSiebelProducts lauSiebelProducts = siebelProducts;
		Integer preProdId = productMap.get(lauSiebelProducts.getProductName());
		long productId =  preProdId != null ? preProdId.longValue():CommonDAO.getPrimaryKey(template);
		lauSiebelProducts.setProductId(productId);
		lauSiebelProducts.setReportId(reportId);
		
		if (preProdId == null)
		{
				log.info("Generated Primary Key for PRODUCT_ID ->" + productId);
				productMap.put(lauSiebelProducts.getProductName(),Integer.valueOf((int)productId) );
				id = template.update(SQL_INSERT_STRING, new Object[] {
						lauSiebelProducts.getProductName(),
						lauSiebelProducts.getProductId(),
						displayNumber++,
						lauSiebelProducts.getReportId(),
						dt, userId,
						"S",lauSiebelProducts.getProductIndication() });
				log.info("LauSiebelProductSetDAO insert() ID -> " + id);
		}
		else 
		{
			log.info("inserting second dose record for  PRODUCT_ID ->" + productId);
		}
	//	if (id==1)
		//{
			long doseId = CommonDAO.getPrimaryKey(template);
			
		      if (lauSiebelProducts.getProductName().equals("AVONEX")) {
	            	lauSiebelProducts.setDose("30");
	            	lauSiebelProducts.setDoseUnit("UG");
	            	lauSiebelProducts.setRoute("IM");
	            	lauSiebelProducts.setFrequency("QW");
		      }
		      else if  (lauSiebelProducts.getProductName().equals("TYSABRI"))
		      { 
	            	lauSiebelProducts.setDose("300");
	            	lauSiebelProducts.setDoseUnit("MG");
	            	lauSiebelProducts.setRoute("IV");
	            	lauSiebelProducts.setFrequency("QM");
	            	lauSiebelProducts.setFormulation("IV_INF");

		      }
		      else if  (lauSiebelProducts.getProductName().equals("FUMADERM"))
		      {
	            	lauSiebelProducts.setDoseUnit("MG");
	            	lauSiebelProducts.setRoute("PO");
	            	lauSiebelProducts.setFormulation("TAB");
		      }

			int idDose = 0;
			idDose = template.update(SQL_DOSE_INSERT_STRING, new Object[] {
				lauSiebelProducts.getStartDate(),
				lauSiebelProducts.getStopDate(),
				doseDisplayNumber++,
				lauSiebelProducts.getDose(),
				lauSiebelProducts.getDoseUnit(),
				lauSiebelProducts.getRoute(),
				lauSiebelProducts.getFrequency(),
				lauSiebelProducts.getFormulation(),
				lauSiebelProducts.getOngoingFlag(),
				userId,dt,lauSiebelProducts.getReportId(),productId,doseId});
			log.info("LauSiebelDose insert() ID -> " + idDose);
		//}
	}

}
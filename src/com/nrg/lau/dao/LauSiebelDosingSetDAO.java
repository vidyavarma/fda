package com.nrg.lau.dao;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauDosing;
import com.nrg.lau.commons.Constants;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelBusObject;
import com.siebel.data.SiebelDataBean;

public class LauSiebelDosingSetDAO {
	
	private static Logger log	= Logger.getLogger(LauSiebelDosingSetDAO.class);
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	
	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, SiebelDataBean dataBean, HashMap<String, Long> productMap
			,String user,java.sql.Timestamp dstamp) throws Exception {
		userId = user;
		dt = dstamp;
		if(request.getParameter(Constants.SIEBEL_REQ_USER_ID)!= null && request.getParameter(Constants.SIEBEL_REQ_ACTIVITY_ID) != null &&
				request.getParameter(Constants.SIEBEL_REQ_CUST_NO) != null && request.getParameter(Constants.SIEBEL_REQ_CON_ID) != null &&
				reportId != 0 && productMap != null && productMap.size() > 0)	{
			getSiebelData(dataBean,template,reportId,
					request.getParameter(Constants.SIEBEL_REQ_CON_ID), productMap);
		}else {
			log.info("LauSiebelDosingSetDAO -> Siebel request parameters not available!");
		}		
	}
	
	private LauDosing setSiebelbean(SiebelBusComp busComp) throws Exception{
		
		LauDosing lauDosing = new LauDosing();
		lauDosing.setProductName(busComp.getFieldValue("Therapy BIIB (MVG) - Therapy Stage (Field)"));
		lauDosing.setStartDate(busComp.getFieldValue("Therapy BIIB (MVG) - Date Start Drug (Field)"));
		lauDosing.setStopDate(busComp.getFieldValue("Therapy BIIB (MVG) - Date End Drug (Field)"));
		return lauDosing;
	}
	
	private void getSiebelData(SiebelDataBean dataBean, SimpleJdbcTemplate template, 
			long reportId, String contactId, 
			HashMap<String, Long> productMap) throws Exception{
		
		LauDosing lauDosing = null;
		SiebelBusObject busObject	= dataBean.getBusObject("Contact");
		SiebelBusComp busComp 		= busObject.getBusComp("Contact").getMVGBusComp("Product BIIB"); 
		busComp.setViewMode(3);
		busComp.clearToQuery();
		busComp.activateField("ContactId from the URL");
		busComp.setSearchSpec("ContactId from the URL", contactId);
		busComp.setSortSpec("Created(DESCENDING)");
		busComp.executeQuery(true);
		if (busComp.firstRecord()) {
			lauDosing = setSiebelbean(busComp);
			log.info("getSiebelData() - LauDosing -> " + lauDosing.toString());
			insert(template, reportId,lauDosing,productMap);
			while (busComp.nextRecord()){
				lauDosing = setSiebelbean(busComp);
				log.info("getSiebelData() - LauDosing -> " + lauDosing.toString());
				insert(template, reportId,lauDosing,productMap);
			}
		}
	}
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_DOSING (PRODUCT_ID,PRODUCT_NAME,DOSE_ID,REPORT_ID," +
			"START_DATE,STOP_DATE,UPDATE_TIMESTAMP,UPDATE_USER_ID) VALUES (?,?,?,?,?,?,?,?)";
	
	public void insert(SimpleJdbcTemplate template, long reportId, 
			LauDosing lauDosing,
			HashMap<String, Long> productMap) throws Exception {
		
		int id = 0;
		long dosingId = CommonDAO.getPrimaryKey(template);
		
		LauDosing dosing = lauDosing;
		dosing.setDoseId(dosingId);
		dosing.setReportId(reportId);
		
		if(productMap != null && productMap.size() > 0) {			
			Long prodId = productMap.get(dosing.getProductName());
			dosing.setProductId(prodId);
		}
		
		log.info("Generated Primary Key for DOSE_ID ->" + dosingId);
		id = template.update(SQL_INSERT_STRING,new Object[]{
			dosing.getProductId(),
			dosing.getProductName(),
			dosing.getDoseId(),
			dosing.getReportId(),
			dosing.getStartDate(),
			dosing.getStopDate(),
			dt,
			userId,
			"S"
		});
		log.info("LauSiebelDosingSetDAO insert() ID -> " + id);
	}
}

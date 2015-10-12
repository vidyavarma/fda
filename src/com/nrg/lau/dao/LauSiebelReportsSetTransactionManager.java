package com.nrg.lau.dao;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.Util;
import com.nrg.lau.commons.XMLException;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;

public class LauSiebelReportsSetTransactionManager {
	private DataSourceTransactionManager transactionManager;
	private SimpleJdbcTemplate template;
	
	private static Logger log	= Logger.getLogger(LauSiebelReportsSetTransactionManager.class);

	
	public void setTxManager(DataSourceTransactionManager transactionManager){
        this.transactionManager = transactionManager;
        this.template = new SimpleJdbcTemplate(transactionManager.getDataSource());    
    }
	
	public String setReport(HttpServletRequest request){
		DefaultTransactionDefinition def	= new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status 			= transactionManager.getTransaction(def);
		String xmlStatus					= "";
		SiebelDataBean dataBean = null;
		 java.sql.Timestamp dt  = null; 
		 String userId	= "";
		
		try	{
			
			userId = CommonDAO.getUSERID(request);
			log.warn ("axis userid is :"+userId );
			
			dt = CommonDAO.getTimestamp(template);
/*			Properties p = System.getProperties();
			Enumeration keys = p.keys();
			while (keys.hasMoreElements()) {
			  String key = (String)keys.nextElement();
			  String value = (String)p.get(key);
			  log.info("--------------------------------------");
			  log.info((key + ": " + value));
			  log.info("--------------------------------------");
			}*/
			System.setProperty("file.encoding", Constants.SIEBEL_FILE_ENCODING);
			//log.info("os:"+System.getProperty("os.name").toLowerCase());

			log.info("connecting to databean");
//get siebel connection info from the database
			LauReferenceListDetailsGetDAO lauReferenceListDetailsGetDAO = new LauReferenceListDetailsGetDAO();
			Map<String, String> listMap = lauReferenceListDetailsGetDAO.getCodeValuesAsMap(template, Constants.SIEBEL_FEFLIST_NAME);			 
			 
			log.info(listMap.get(Constants.SIEBEL_FEFLIST_URL));  

			String siebelUrl = listMap.get(Constants.SIEBEL_FEFLIST_URL);
			String siebelUser = listMap.get(Constants.SIEBEL_FEFLIST_USER);
			String siebelPwd = listMap.get(Constants.SIEBEL_FEFLIST_PASSWORD);
			log.info("axis connection info"+"siebelUrl:"+siebelUrl+"siebelUser:"+siebelUser);
			dataBean = new SiebelDataBean();
			//dataBean.login(Constants.SIEBEL_CONNECT_URL, Constants.SIEBEL_USER, Constants.SIEBEL_USER);
			dataBean.login(siebelUrl, siebelUser, siebelPwd );
			log.info("connected to databean");
			LauSiebelReportsSetDAO lauSiebelReportsSetDAO = new LauSiebelReportsSetDAO();
			long reportId = lauSiebelReportsSetDAO.save(request, template, transactionManager.getDataSource(),userId,dt);
			log.info("report id:>"+reportId);
			log.warn("AXIS report creating user :" + userId + ";reprtid:"+reportId );
			log.info("inserting activity log:>");
			//Insert table LAU_REPORT_ACTIVITY_LOG
			SiebelLauActivityLogSetDAO  lauSiebelActivityLogSetDAO = new  SiebelLauActivityLogSetDAO();
			log.info("inserting activity log2:>");
			 lauSiebelActivityLogSetDAO.save(request, template, reportId,userId,dt);
			
			log.info("before patient details:>");
			LauSiebelPatientDetailsSetDAO lauSiebelPatientDetailsSetDAO = new LauSiebelPatientDetailsSetDAO();
			String sMarket = lauSiebelPatientDetailsSetDAO.save(request, template, reportId, dataBean,userId,dt);
			
			log.info("before reporter details:>");
			LauSiebelReporterDetailsSetDAO lauSiebelReporterDetailsSetDAO = new LauSiebelReporterDetailsSetDAO();
			lauSiebelReporterDetailsSetDAO.save(request, template, reportId, dataBean,userId,dt);
			log.info("before product details:>");
			LauSiebelProductsSetDAO lauSiebelProductsSetDAO = new LauSiebelProductsSetDAO();			
			lauSiebelProductsSetDAO.save(request, template, reportId, dataBean,sMarket,userId,dt);
	
			transactionManager.commit(status);
			log.info("AXIS Transaction committed");
			xmlStatus = XMLException.status(Long.toString(reportId));
			log.info("returned reportid:"+Long.toString(reportId));
			dataBean.logoff();
		
		}catch (Exception e) {	
			
			log.error(e);
			transactionManager.rollback(status);
			//xmlStatus = XMLException.xmlError(e, "AXIS Report  failed");	
			 xmlStatus = XMLException.xmlErrorSiebel(e, "AXIS Report failed");
			 try {
				dataBean.logoff();
			} catch (SiebelException e1) {
				// TODO Auto-generated catch block
				log.error("Exception in LauSiebelReportsSetTransactionManager, full stack trace follows:", e1);
				//e1.printStackTrace();
			}
			
		}		
		return xmlStatus;
	}
}

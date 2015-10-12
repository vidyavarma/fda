package com.nrg.lau.dao;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.nrg.lau.commons.XMLException;

public class LauUserPermissionsSetTransactionManager {
	private DataSourceTransactionManager transactionManager;
	private SimpleJdbcTemplate template;
	private static Logger log	= Logger.getLogger(LauUserPermissionsSetTransactionManager.class);
	
	public void setTxManager(DataSourceTransactionManager transactionManager){
        this.transactionManager = transactionManager;
        this.template = new SimpleJdbcTemplate(transactionManager.getDataSource());    
    }
	
	public String setReport(HttpServletRequest request){
		DefaultTransactionDefinition def	= new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status 			= transactionManager.getTransaction(def);
		String xmlStatus					= "";
		
		try	{
						
			LauUserPermissionsSetDAO	lauUserPermissionsSetDAO = new LauUserPermissionsSetDAO();
			lauUserPermissionsSetDAO.save(request, template);
			
			transactionManager.commit(status);
			log.info("Transaction committed");
			xmlStatus = XMLException.status("User Permission transaction was successful");
		
		}catch (Exception e) {			
			log.error(e);
			transactionManager.rollback(status);
			xmlStatus = XMLException.xmlError(e, "User Permission : transaction failed");			
		}		
		return xmlStatus;
	}
}

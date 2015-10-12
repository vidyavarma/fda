/**
 * 
 */
package com.nrg.lau.dao;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.nrg.lau.commons.XMLException;

/**
 * @author vinay.kumar
 *
 *Handles transaction while updating layout questions associated to template 
 */
public class LauLayoutTemplateSetTransactionManager {
	
	private DataSourceTransactionManager transactionManager;
	private SimpleJdbcTemplate template;
	private static Logger log	= Logger.getLogger(LauLayoutTemplateSetTransactionManager.class);
	
	public void setTxManager(DataSourceTransactionManager transactionManager){
        this.transactionManager = transactionManager;
        this.template = new SimpleJdbcTemplate(transactionManager.getDataSource());    
    }
	public String save(HttpServletRequest request){
		
		DefaultTransactionDefinition def	= new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status 			= transactionManager.getTransaction(def);
		String xmlStatus					= "";
		
		try	{
						
			LauLayoutTemplateDAO lauLayoutTemplateDAO = new LauLayoutTemplateDAO();
			lauLayoutTemplateDAO.save(request, template);
			
			transactionManager.commit(status);
			log.info("Transaction committed");
			xmlStatus = XMLException.status("Template save was successful");
		
		}catch (Exception e) {			
			log.error(e);
			transactionManager.rollback(status);
			xmlStatus = XMLException.xmlError(e, "failed");			
		}		
		return xmlStatus;
		
	}
	
}

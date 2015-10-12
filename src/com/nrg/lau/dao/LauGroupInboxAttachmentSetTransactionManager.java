package com.nrg.lau.dao;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.nrg.lau.commons.XMLException;

public class LauGroupInboxAttachmentSetTransactionManager {
	private DataSourceTransactionManager transactionManager;
	private SimpleJdbcTemplate template;
	private static Logger log	= Logger.getLogger(LauGroupInboxAttachmentSetTransactionManager.class);
	
	public void setTxManager(DataSourceTransactionManager transactionManager){
        this.transactionManager = transactionManager;
        this.template = new SimpleJdbcTemplate(transactionManager.getDataSource());    
    }
	
	public String setReport(HttpServletRequest request){
		DefaultTransactionDefinition def	= new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status 			= transactionManager.getTransaction(def);
		String xmlStatus					= "";
		java.sql.Timestamp dt  = null; 
		String userId	= "";
		
		try	{
			log.info("In LauGroupInboxAttachmentSetTransactionManager");
			userId = CommonDAO.getUSERID(request);
			log.warn ("USER ID is :"+userId );
			
			dt = CommonDAO.getTimestamp(template);
			LauGroupInboxAttachmentSetDAO lauGroupInboxAttachmentSetDAO = new LauGroupInboxAttachmentSetDAO();

			String msg = lauGroupInboxAttachmentSetDAO.save(request, template, transactionManager.getDataSource(),userId,dt);
			
			transactionManager.commit(status);
			log.info("Transaction committed:" + msg);
			xmlStatus = XMLException.status(msg);
		
		}catch (Exception e) {			
			log.error(e);
			transactionManager.rollback(status);
			xmlStatus = XMLException.xmlError(e, "Group Inbox Attachment update failed");			
		}		
		return xmlStatus;
	}
}

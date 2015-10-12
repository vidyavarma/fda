package com.nrg.lau.dao;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.jdbc.support.lob.LobHandler;
//lobHandler
import com.nrg.lau.commons.XMLException;

public class LauReportActivityAttachmentSetTransactionManager {
	private DataSourceTransactionManager transactionManager;
	private LobHandler lobHandler;
	private SimpleJdbcTemplate template;
	private static Logger log	= Logger.getLogger(LauReportActivityAttachmentSetTransactionManager.class);
	
	public void setTxManager(DataSourceTransactionManager transactionManager){
        this.transactionManager = transactionManager;
        this.template = new SimpleJdbcTemplate(transactionManager.getDataSource());    
    }
	public LobHandler getLobHandler() {
		return lobHandler;
	}

	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}
	public String setReport(HttpServletRequest request){
		DefaultTransactionDefinition def	= new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status 			= transactionManager.getTransaction(def);
		String xmlStatus					= "";
		 java.sql.Timestamp dt  = null; 
		 String userId	= "";
		try	{
			log.info("in LauReportActivityAttachmentSetTransactionManager");
			userId = CommonDAO.getUSERID(request);
			log.warn ("axis userid is :"+userId );
			
			dt = CommonDAO.getTimestamp(template);
			LauReportE2bActivityAttachmentSetDAO lauReportActivityAttachmentSetDAO = new LauReportE2bActivityAttachmentSetDAO();

			String msg = lauReportActivityAttachmentSetDAO.save(request, template, transactionManager.getDataSource(),lobHandler,userId,dt);
			
			transactionManager.commit(status);
			log.info("Transaction committed:"+msg);
			//msg = msg.length()>0 ? msg : "Report Activity Attachment updation was successful";
            xmlStatus = XMLException.status(msg);
		
		}catch (Exception e) {	
			log.info("in ..............LauReportActivityAttachmentSetTransactionManage" );
			log.error(e);
			transactionManager.rollback(status);
			xmlStatus = XMLException.xmlError(e, "Initial/Followup report failed");			
		}		
		return xmlStatus;
	}


}

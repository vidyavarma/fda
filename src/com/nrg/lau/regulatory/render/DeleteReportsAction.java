package com.nrg.lau.regulatory.render;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.dao.CommonDAO;

public class DeleteReportsAction implements View{
	private DataSourceTransactionManager txManager;
	private static Logger log = Logger.getLogger(DeleteReportsAction.class);
	private DataSource ds;
	private SimpleJdbcTemplate template;
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	public String getContentType() {
		return "text/xml";
	}
	
	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("ENTER - DeleteReportsAction render()------>");
		
		
		String stats = "";
		String reportID = "";
		String reasonDetail = "";
		userId = CommonDAO.getUSERID(request);
		dt = CommonDAO.getTimestamp(template);
		reportID = request.getParameter("reportid");
		reasonDetail = request.getParameter("reasonDetail");
		log.info("reportID in DELETE action---->" + reportID);
		log.info("reasonDetail in DELETE action---->" + reasonDetail);
		log.info("userId in action---->" + userId);
		log.info("dt in action---->" + dt);
		long repId = Long.parseLong(reportID);
		/*DefaultTransactionDefinition def	= new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status 			= txManager.getTransaction(def);*/
		try {
			updateActivity(repId, request, "DELETE SUCCESSFUL", reasonDetail);			
			int id = 0;
			id = template.update("begin LAU_UTL.deleteReport(?); end;",
					new Object[] { reportID });
			log.info("deleteReport update() ID -> " + id + ":, repid:"
					+ reportID);
			//txManager.commit(status);
			stats = XMLException
					.status("DeleteFieldFavoritesAction was successful!");				
		} catch (Exception e) {
			log.error(e);
			stats = XMLException.xmlError(e,
					"DeleteFieldFavoritesAction failed!");
			//txManager.rollback(status);
		}

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();
		pw.write(stats);
		pw.flush();
		pw.close();

		log.info("EXIT - DeleteReportsAction render()");
	}
	
	
	private void updateActivity(long reportId, HttpServletRequest request,
			String status, String reasonDesc) {
		log.info("updateActivity - Primo status -> " + status);
		int intInsStatus = CommonDAO.insertActivityDetails(template, null,
				reportId, status, "", reasonDesc, request, userId, dt);
		log.info("updateActivity - activity log creation status: "
				+ intInsStatus);
	}
	
	/*public void setTxManager(DataSourceTransactionManager transactionManager){
        this.txManager = transactionManager;
        this.template = new SimpleJdbcTemplate(transactionManager.getDataSource());    
    }*/
	
	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
		this.template = new SimpleJdbcTemplate(dataSource);
	}
}

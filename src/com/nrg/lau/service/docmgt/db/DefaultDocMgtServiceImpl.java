package com.nrg.lau.service.docmgt.db;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.lob.LobHandler;

import com.nrg.lau.beans.ExternalDocuments;
import com.nrg.lau.security.AppContext;
import com.nrg.lau.service.docmgt.DocMgtParameters;
import com.nrg.lau.service.docmgt.DocMgtResponse;
import com.nrg.lau.service.docmgt.DocMgtService;
import com.nrg.lau.service.docmgt.sp.SharePoint;

public class DefaultDocMgtServiceImpl implements DocMgtService<DocMgtResponse> {
	
	private static Logger log = Logger.getLogger(DefaultDocMgtServiceImpl.class);
	private LobHandler lobHandler;

	@Override
	public InputStream retrieveDocument(DocMgtParameters params)
			throws Exception {
	
		log.info("retrieveDocument from database");
		
		InputStream in = null;
		String attachmentId = params.getAttachmentId();
		
		ApplicationContext ctx 	= 	AppContext.getApplicationContext();
		DataSource ds	= (DataSource)ctx.getBean("dataSource");
		LobHandler lobHlr = (LobHandler)ctx.getBean("lobHandler");	
		this.lobHandler = lobHlr;
		
		log.info("attachmentId -> " + attachmentId);
		log.info("lobHandler -> " + lobHandler);
		log.info("ds -> " + ds);
			
		try {
			
/*			String sql 	= "SELECT NVL(A.ATTACHMENT , B.DOCUMENT_BINARY) AS ATTACHMENT FROM LAU_REPORT_ATTACHMENTS A, LAU_EXTERNAL_DOCUMENTS B WHERE " +
					" A.ATTACHMENT_ID = " + attachmentId +
					"AND B.EXTERNAL_DOCUMENT_ID (+) = A.EXTERNAL_DOCUMENT_ID";	*/			
	
			String sql 		= " SELECT NVL(A.ATTACHMENT,B.GENERATED_LETTER_BINARY) AS ATTACHMENT,  A.FILE_NAME,  A.DOCUMENT_NAME,"+
					 " A.LAU_GENERATED_LETTER_ID,  A.ATTACHMENT	FROM LAU_REPORT_ATTACHMENTS A,	  LAU_GENERATED_LETTERS B"+
					" WHERE A.ATTACHMENT_ID  = " + attachmentId + "	AND A.LAU_GENERATED_LETTER_ID = B.LAU_GENERATED_LETTER_ID(+)";
			if(params.getSql().length() > 0) sql = params.getSql();
			
			in = new JdbcTemplate(ds).queryForObject(sql,
				new RowMapper<InputStream>() {
					public InputStream mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new ByteArrayInputStream(lobHandler.getBlobAsBytes(rs,"ATTACHMENT"));
					}
				});
			//PR-1225 - updating view_status if document is viewed. by vinay
			String updateViewStatus ="UPDATE LAU_REPORT_ATTACHMENTS SET VIEW_STATUS=? WHERE ATTACHMENT_ID=?";
			JdbcTemplate  jdbcTemplate = new JdbcTemplate(ds);
			        jdbcTemplate.update(updateViewStatus, new Object[] {"VIEWED",attachmentId});
						           
		} catch (Exception e) {				
			log.error(e, e);
		}
			
		return in;
	}
	
	@Override
	public DocMgtResponse upload(HttpServletRequest request,
			DocMgtParameters params) throws Exception {
		
		//SQL Statements
		String SQL_INSERT_STRING = "INSERT INTO LAU_REPORT_ATTACHMENTS (ATTACHMENT,UPDATE_USER_ID,UPDATE_TIMESTAMP,DOCUMENT_NAME,DOCUMENT_TYPE," +
				"BINARY_FILE_TYPE,REPORT_ID,ATTACHMENT_ID,FILE_NAME,VIEW_STATUS, DISPLAY_NUMBER) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		
		DocMgtResponse response = new DocMgtResponse();
		ExternalDocuments documents = params.getExternalDocs();
		SimpleJdbcTemplate template = new SimpleJdbcTemplate(params.getDs());
		
		try {
			int id = template.update(SQL_INSERT_STRING, new Object[] {				
					documents.getDocumentBytes(),
					documents.getUser(),
					documents.getDstamp(),
					documents.getDocumentName(),
					documents.getDocumentType(),
					documents.getDocumentContentType(),
					params.getLauReportId(),
					params.getAttachmentId(),
					documents.getDocumentOrigFilename(),
					"NEW",
					documents.getDISPLAY_NUMBER()
				});
			log.info("File uploaded to database -> " + id);
			response.setMessage("File uploaded successfully");
			response.setSuccess("Success");
		
		}catch (Exception e) {
			log.error(e);
		}		
		
		return response;
	}
	
	@Override
	public boolean reject(DocMgtParameters params) {		
		
		if(params.getDs() != null) {
			SimpleJdbcTemplate template = new SimpleJdbcTemplate(params.getDs());
			SharePoint.updateDocumentStatus(params.getExternalDocs(), template);
			return true;
		} else {
			log.error("datasource returned null from params.getDs()");
			return false;
		}				
	}	

	@Override
	public DocMgtResponse outBound(DocMgtParameters params) 
			throws Exception {
		return null;
	}

	@Override
	public boolean rename(DocMgtParameters params) {
		return false;
	}

	@Override
	public boolean detach(DocMgtParameters params) {
		return false;
	}	

	@Override
	public boolean newGroup(DocMgtParameters params) {
		return false;
	}	

}
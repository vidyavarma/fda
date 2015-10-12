package com.nrg.lau.dao;

import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.lob.LobHandler;

import com.nrg.lau.beans.LauReportActivities;
import com.nrg.lau.beans.LauReportAttachments;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.e2b.E2BRejectOutput;
import com.nrg.lau.sharepoint.FileUploadService;
import com.nrg.lau.sharepoint.InBoxToReportsService;
import com.nrg.lau.sharepoint.OutboundService;
import com.nrg.lau.sharepoint.SPResponse;
import com.nrg.lau.sharepoint.SPUtil;
import com.nrg.lau.utils.ReadConfig;

public class LauReportE2bActivityAttachmentSetDAO implements
		IReportChildSetDAO<LauReportActivities> {
	private DataSource ds;	
	private static Logger log = Logger
			.getLogger(LauReportE2bActivityAttachmentSetDAO.class);
	private Vector<LauReportActivities> reports = null;
	private Vector<LauReportAttachments> attachment = null;
	private LauReportActivities reportActivities = null;
	private LauReportAttachments reportAttachments = null;
	private String lauReportId = "";
	// private String ObjectID = "";
	private long activityId = 0;
	private java.sql.Timestamp dt = null;
	private String userId = "";
	long reportId = 0;
	private String e2bExchangeId = "";
	private String e2bAckId = ""; // to store the ack id returned by SP

	private LobHandler lobHandler;

	protected void finalize() throws Throwable {
		this.reports.removeAllElements();
		this.reports = null;
		this.attachment.removeAllElements();
		this.attachment = null;
		super.finalize();
	}

	public String save(HttpServletRequest request, SimpleJdbcTemplate template,
			DataSource datasource,LobHandler lobHandler, String user, java.sql.Timestamp dstamp)
					throws Exception {
		// Check to make sure that XML is there in Request.

		String status = "";
		this.lobHandler = lobHandler;
		ds = datasource;
		dt = dstamp;
		userId = user;
		if (request.getParameter(IReportsConstants.LAU_REPORT_ACTIVITIES) != null
				&& request
				.getParameter(IReportsConstants.LAU_REPORT_ACTIVITIES)
				.length() > 0) {

			log.info("LauReportActivityAttachmentSetDAO save() LAU_REPORT_ACTIVITIES -> "
					+ request
					.getParameter(IReportsConstants.LAU_REPORT_ACTIVITIES));

			// Create LauReportActivities beans from XML Request
			createBeansFromXml(request
					.getParameter(IReportsConstants.LAU_REPORT_ACTIVITIES));
			Iterator<LauReportActivities> itr = this.reports.iterator();

			// Report Id is 0 NEW else a new FOLLOWUP
			while (itr.hasNext()) {
				LauReportActivities lauReportActivities = (LauReportActivities) itr
						.next();
				log.info("itr.hasNext() -> " + lauReportActivities.toString());
				this.reportActivities = null;
				this.reportActivities = lauReportActivities;
				if (lauReportActivities.getTransactionType() == IReportsConstants.deleteFlag) {
					delete(template);
				} 
				else 
				{
					//Step 1. Create a new report or followup

					status = handleReportProcessing(request,template, datasource,lauReportActivities);

					if (reportId > 0) {	
						
						this.reportActivities.setREPORT_ID(reportId);
						// Step2 insert ReportActivities record
						insert(template,reportId);

						//step 3 handle attachments
						if (request
								.getParameter(IReportsConstants.LAU_REPORT_ATTACHMENTS_PARAM_NAME) != null
								&& request
								.getParameter(
										IReportsConstants.LAU_REPORT_ATTACHMENTS_PARAM_NAME)
										.length() > 0) {
							handleAttachmentAndAckProcessing(request, template,
									datasource);

							// Added on 01/02/2012 S.Abraham to save summary
							// data
							CommonDAO.setReportSummaryXml(template, reportId,
									userId);
						} else {
							log.info(IReportsConstants.LAU_REPORTS_PARAM_NAME
									+ " not found in request for attachment insert");
						}

					}
				}

			}// end of while(itr.hasNext())
		} else {
			log.info(IReportsConstants.LAU_REPORT_ACTIVITIES
					+ " not found in request");
		}
		return status;
	}

	public void updateExternalDocStatus(SimpleJdbcTemplate template,
			String documentID) throws Exception {
		int id = 0;
		log.info("BEFORE LauReportActivityAttachmentSetDAO update()");
		id = template
				.update("UPDATE LAU_EXTERNAL_DOCUMENTS SET "
						+ "DOCUMENT_STATUS = 'ACCEPTED' WHERE EXTERNAL_DOCUMENT_ID = ?",
						new Object[] { documentID });
		log.info("LauReportActivityAttachmentSetDAO update() ID -> " + id);
	}
	public String handleReportProcessing(HttpServletRequest request,
			SimpleJdbcTemplate template, DataSource datasource,
			LauReportActivities lauReportActivities) throws Exception {
		String status = "";

		if (request.getParameter(IReportsConstants.LAU_REPORT_E2BEXCHANGEID_PARAM_NAME) != null
				&& request.getParameter(
						IReportsConstants.LAU_REPORT_E2BEXCHANGEID_PARAM_NAME).length() > 0) 
		{ 
			e2bExchangeId = request.getParameter(IReportsConstants.LAU_REPORT_E2BEXCHANGEID_PARAM_NAME) ;
		}
		log.info ("#####e2b Exchange Id is : "+e2bExchangeId );
		if (lauReportActivities.getREPORT_ID() != 0) 
		{
			reportId = Long.valueOf(lauReportActivities.getREPORT_ID());
			lauReportId = lauReportActivities.getLAU_REPORT_ID();
	    	int update = setCheckOut(reportId,template);
	    	//this.template.
	    	if(update == 1) {
	    		int intInsStatus = CommonDAO.insertActivityDetails(template, null, reportId, "CHECK OUT", "", "", request,userId,dt);
	    	}
	    	else {
	    		//status = XMLException.xmlError(new Exception(),"Report checkout failed, cannot create a follow up!");
	    		throw new Exception(" Report checkout failed, cannot create a follow up report!");
	    	}

			// Insert LAU_REPORT_ACTIVITY_LOG bug# 848 no S.Abraham 03/25/2013
			int intInsStatus = CommonDAO.insertActivityDetails(template, null, reportId, "FOLLOW UP", "", "", request,userId,dt);
			log.info("Activity log creation status:"+intInsStatus);
			/***
			 * 
			 * IF E2B then Call LAU_E2B_INBOUND.import_e2b_message
			 */
			if (e2bExchangeId.length() > 0) 
			{ 

				ResultOutput  outVal =  importE2bData(datasource, userId,e2bExchangeId,"FU") ;
				log.info("--------------- FU checking retval------::"+outVal.retVal);
				if ((Long.valueOf(outVal.retVal) == 1) || (Long.valueOf(outVal.retVal) == 2))// 0 - Error, > 0 success
				{
					log.info("inside FU...");
					e2bAckId = outVal.ackId;
					reportId = Long.valueOf(outVal.strRepId);// [0]
					lauReportId = outVal.strLauRepId;// [1] contains lau
					status = XMLException.status(status, reportId, lauReportId);
					log.info("REPORT_ID & LAU_REPORT_ID  after e2bimport -> "
							+ reportId + " , " + lauReportId);
					LauActivityLogSetDAO lauActivityLogSetDAO = new LauActivityLogSetDAO();
					lauActivityLogSetDAO.save(request, template, reportId, userId,
							dt);
				}
				else
				{
					log.info("Throwing FU error");
					throw new Exception(outVal.strError);
				}

			}
			status = XMLException.status(status, reportId, "");
			log.info("FOLLOWUP LauReportActivityAttachmentSetDAO() ->");
			// update(template);
		} 
		else {
			if (request.getParameter(IReportsConstants.LAU_REPORTS_PARAM_NAME) != null
					&& request.getParameter(
							IReportsConstants.LAU_REPORTS_PARAM_NAME).length() > 0) {
				/***
				 * 
				 * IF E2B then Call LAU_E2B_INBOUND.import_e2b_message
				 */
				if (e2bExchangeId.length() > 0) 
				{ 

					ResultOutput  outVal =  importE2bData(datasource, userId,e2bExchangeId,"INITIAL") ;
					
					log.info("--------------- checking retval------::"+outVal.retVal);
					if ((Long.valueOf(outVal.retVal) == 1) || (Long.valueOf(outVal.retVal) == 2))// 0 - Error, > 0 success
					{
						log.info("inside ...");
						e2bAckId = outVal.ackId;
						reportId = Long.valueOf(outVal.strRepId);// [0]
						lauReportId = outVal.strLauRepId;// [1] contains lau
						status = XMLException.status(status, reportId, lauReportId);
						log.info("REPORT_ID & LAU_REPORT_ID  after e2bimport -> "
								+ reportId + " , " + lauReportId);
						////////////////////////////////////////////////////////////////
						// Insert LAU_REPORT_ACTIVITY_LOG bug# 848 no S.Abraham
						// 03/23/2013
						LauActivityLogSetDAO lauActivityLogSetDAO = new LauActivityLogSetDAO();
						lauActivityLogSetDAO.save(request, template, reportId, userId,
								dt);
					}
					else
					{
						log.info("Throwing error");
						throw new Exception(outVal.strError);
					}

					log.info ("#####e2b returned parameters are : "+lauReportId );
					log.info ("error  : "+outVal.retVal + ","+outVal.strError);
				}
				else
				{
					////////////////////////////////////////////////////////////////
					LauReportsSetDAO.deleteReportIdForReports = 0;
					LauReportsSetDAO lauReportsDAO = new LauReportsSetDAO();
					// Insert LAU_REPORT_ACTIVITY_LOG bug# 848 no S.Abraham 03/25/2013
					int intInsStatus = CommonDAO.insertActivityDetails(template, null, reportId, "NEW", "", "", request,userId,dt);

					String[] reportIds = lauReportsDAO.save(request, template,
							datasource, userId, dt);
					reportId = Long.valueOf(reportIds[0]);// [0]
					lauReportId = reportIds[1];// [1] contains lau
					status = XMLException.status(status, reportId, lauReportId);
					log.info("REPORT_ID & LAU_REPORT_ID from LauReportActivityAttachmentSetDAO() -> "
							+ reportId + " , " + lauReportId);
					////////////////////////////////////////////////////////////////
					// Insert LAU_REPORT_ACTIVITY_LOG bug# 848 no S.Abraham
					// 03/23/2013
					LauActivityLogSetDAO lauActivityLogSetDAO = new LauActivityLogSetDAO();
					lauActivityLogSetDAO.save(request, template, reportId, userId,
							dt);
				}
			}
		}
		return status;
	}

	public void handleAttachmentAndAckProcessing(HttpServletRequest request,
			SimpleJdbcTemplate template, DataSource datasource)
					throws Exception {

		log.info("INSIDE ATTACHMENT INSERT");

		createBeansFromXmlForAttachment(request
				.getParameter(IReportsConstants.LAU_REPORT_ATTACHMENTS_PARAM_NAME));

		Iterator<LauReportAttachments> itrAtt = this.attachment.iterator();
		// Report Id is 0 it's UPDATE else a new INSERT
		while (itrAtt.hasNext()) {

			LauReportAttachments lauReportAttachments = (LauReportAttachments) itrAtt
					.next();

			String attachmentSQL = "SELECT EXTERNAL_DOCUMENT_ID,DOCUMENT_NAME,OBJECT_ID,FILE_NAME,"
					+ "DOCUMENT_URL FROM LAU_EXTERNAL_DOCUMENTS WHERE EXTERNAL_DOCUMENT_ID IN ("
					+ lauReportAttachments.getEXTERNAL_DOCUMENT_ID() + ")";

			List<Map<String, Object>> rows = template.queryForList(
					attachmentSQL, new Object[] {});

			for (Map<String, Object> row : rows) {

				log.info("EXTERNAL_DOCUMENT_ID -> "
						+ row.get("EXTERNAL_DOCUMENT_ID")
						+ " DOCUMENT_NAME-> " + row.get("DOCUMENT_NAME")
						+ " OBJECT_ID-> " + row.get("OBJECT_ID")
						+ " FILE_NAME -> " + row.get("FILE_NAME")
						+ " DOCUMENT_URL-> " + row.get("DOCUMENT_URL"));

				this.reportAttachments = null;
				this.reportAttachments = lauReportAttachments;

				// Reset the reportAttachments with new
				// values from DB
				this.reportAttachments.setEXTERNAL_DOCUMENT_ID(String
						.valueOf(row.get("EXTERNAL_DOCUMENT_ID")));
				this.reportAttachments.setOBJECT_ID(String.valueOf(row
						.get("OBJECT_ID")));
				this.reportAttachments.setDocumentName(String.valueOf(row
						.get("DOCUMENT_NAME")));
				this.reportAttachments.setFileName(String.valueOf(row
						.get("FILE_NAME")));
				this.reportAttachments.setEXTERNAL_DOCUMENT_URL(String
						.valueOf(row.get("DOCUMENT_URL")));

				log.info("EXTERNAL_DOCUMENT_ID -> "
						+ this.reportAttachments.getEXTERNAL_DOCUMENT_ID()
						+ " DOCUMENT_NAME-> "
						+ this.reportAttachments.getDocumentName()
						+ " OBJECT_ID-> "
						+ this.reportAttachments.getOBJECT_ID()
						+ " FILE_NAME -> "
						+ this.reportAttachments.getFileName()
						+ " DOCUMENT_URL-> "
						+ this.reportAttachments.getEXTERNAL_DOCUMENT_URL());

				// ObjectID =
				// lauReportAttachments.getOBJECT_ID();
				log.info("LauReportActivityAttachmentSetDAO save() LAU_REPORT_ATTACHMENTS_PARAM_NAME -> "
						+ request
						.getParameter(IReportsConstants.LAU_REPORT_ATTACHMENTS_PARAM_NAME));

				this.reportAttachments.setACTIVITY_ID(activityId);
				this.reportAttachments.setReportId(reportId);

				processAttachmentAndAck(template, reportId, request);

				updateExternalDocStatus(template,
						this.reportAttachments.getEXTERNAL_DOCUMENT_ID());

			}
		}
	}

	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		log.info("BEFORE LauReportActivityAttachmentSetDAO update()");
		id = template.update(SQL_UPDATE_STRING, getParameters());
		log.info("LauReportActivityAttachmentSetDAO update() ID -> " + id);

	}

	// SQL Statements
	private final String SQL_UPDATE_STRING = " UPDATE LAU_REPORT_ACTIVITIES SET ACTIVITY_ID=?,REPORT_ID=?,DISPLAY_NUMBER=?,"
			+ "CONTACT_ID=?,DIRECTION=?,CORPORATE_RECEIVED_DATE=?,LOCAL_RECEIVED_DATE=?,PARTNER_RECEIVED_DATE=?,ACTIVITY_DATE=?,"
			+ "ACTIVITY_TYPE=?,ACTIVITY_REASON=?,ACTIVITY_DESCRIPTION=?,PRIORITY=?,ASSIGNED_TO_USER=?,ASSIGNED_TO_GROUP=?,DUE_DATE=?,"
			+ "ACTIVITY_STATUS=?,COMPLETION_DATE=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE REPORT_ID=?,ACTIVITY_ID=?,PROMOTE_ACTIVITY=?";

	private Object[] getParameters() {
		LauReportActivities lauReportActivities = this.reportActivities;
		return new Object[] { lauReportActivities.getDISPLAY_NUMBER(),
				lauReportActivities.getCONTACT_ID(),
				lauReportActivities.getDIRECTION(),
				lauReportActivities.getCORPORATE_RECEIVED_DATE(),
				lauReportActivities.getLOCAL_RECEIVED_DATE(),
				lauReportActivities.getPARTNER_RECEIVED_DATE(),
				lauReportActivities.getACTIVITY_DATE(),
				lauReportActivities.getACTIVITY_TYPE(),
				lauReportActivities.getACTIVITY_REASON(),
				lauReportActivities.getACTIVITY_DESCRIPTION(),
				lauReportActivities.getPRIORITY(),
				lauReportActivities.getASSIGNED_TO_USER(),
				lauReportActivities.getASSIGNED_TO_GROUP(),
				lauReportActivities.getDUE_DATE(),
				lauReportActivities.getACTIVITY_STATUS(),
				lauReportActivities.getCOMPLETION_DATE(), userId, dt,
				lauReportActivities.getREPORT_ID(),
				lauReportActivities.getACTIVITY_ID(),
				lauReportActivities.getPROMOTE_ACTIVITY()

		};
	}

	public void createBeansFromXml(String xml) throws Exception {

		reports = new Vector<LauReportActivities>();
		Digester digester = new Digester();
		digester.push(this);
		String mainXmlTag = "ROWSET/ROW";
		digester.addObjectCreate(mainXmlTag, LauReportActivities.class);

		// Explicitly specify property
		digester.addBeanPropertySetter(mainXmlTag + "/ACTIVITY_ID",
				"ACTIVITY_ID");
		digester.addBeanPropertySetter(mainXmlTag + "/REPORT_ID", "REPORT_ID");
		digester.addBeanPropertySetter(mainXmlTag + "/DISPLAY_NUMBER",
				"DISPLAY_NUMBER");
		digester.addBeanPropertySetter(mainXmlTag + "/CONTACT_ID", "CONTACT_ID");
		digester.addBeanPropertySetter(mainXmlTag + "/DIRECTION", "DIRECTION");
		digester.addBeanPropertySetter(mainXmlTag + "/TRANSACTION_TYPE",
				"transactionType");
		digester.addBeanPropertySetter(mainXmlTag + "/CORPORATE_RECEIVED_DATE",
				"CORPORATE_RECEIVED_DATE");
		digester.addBeanPropertySetter(mainXmlTag + "/LOCAL_RECEIVED_DATE",
				"LOCAL_RECEIVED_DATE");
		digester.addBeanPropertySetter(mainXmlTag + "/PARTNER_RECEIVED_DATE",
				"PARTNER_RECEIVED_DATE");
		digester.addBeanPropertySetter(mainXmlTag + "/ACTIVITY_DATE",
				"ACTIVITY_DATE");
		digester.addBeanPropertySetter(mainXmlTag + "/ACTIVITY_TYPE",
				"ACTIVITY_TYPE");
		digester.addBeanPropertySetter(mainXmlTag + "/ACTIVITY_REASON",
				"ACTIVITY_REASON");
		digester.addBeanPropertySetter(mainXmlTag + "/ACTIVITY_DESCRIPTION",
				"ACTIVITY_DESCRIPTION");
		digester.addBeanPropertySetter(mainXmlTag + "/PRIORITY", "PRIORITY");
		digester.addBeanPropertySetter(mainXmlTag + "/ASSIGNED_TO_USER",
				"ASSIGNED_TO_USER");
		digester.addBeanPropertySetter(mainXmlTag + "/ASSIGNED_TO_GROUP",
				"ASSIGNED_TO_GROUP");
		digester.addBeanPropertySetter(mainXmlTag + "/DUE_DATE", "DUE_DATE");
		digester.addBeanPropertySetter(mainXmlTag + "/ACTIVITY_STATUS",
				"ACTIVITY_STATUS");
		digester.addBeanPropertySetter(mainXmlTag + "/COMPLETION_DATE",
				"COMPLETION_DATE");
		digester.addBeanPropertySetter(mainXmlTag + "/UPDATE_USER_ID",
				"UPDATE_USER_ID");
		digester.addBeanPropertySetter(mainXmlTag + "/UPDATE_TIMESTAMP",
				"UPDATE_TIMESTAMP");
		digester.addBeanPropertySetter(mainXmlTag + "/LAU_REPORT_ID",
				"LAU_REPORT_ID");

		// Move to next LauReports
		digester.addSetNext(mainXmlTag, "addXmlData");
		digester.parse(new StringReader(xml));
		// DOCUMENT_TYPE
	}

	public void addXmlData(LauReportActivities lauReportActivities) {
		reports.add(lauReportActivities);
		log.info(lauReportActivities.toString());
	}

	private void createBeansFromXmlForAttachment(String xml) throws Exception {
		attachment = new Vector<LauReportAttachments>();
		String mainXmlTag = "ROWSET/ROW";
		Digester digester = new Digester();
		digester.push(this);
		digester.addObjectCreate(mainXmlTag, LauReportAttachments.class);

		// Explicitly specify property
		// FILE_NAME
		digester.addBeanPropertySetter(mainXmlTag + "/ATTACHMENT_NAME",
				"attachment");
		digester.addBeanPropertySetter(mainXmlTag + "/ATTACHMENT_NAME",
				"fileName");
		digester.addBeanPropertySetter(mainXmlTag + "/ATTACHMENT_ID",
				"attachmentId");
		digester.addBeanPropertySetter(mainXmlTag + "/REPORT_ID", "reportId2");
		digester.addBeanPropertySetter(mainXmlTag + "/DOCUMENT_NAME",
				"documentName");
		digester.addBeanPropertySetter(mainXmlTag + "/FILE_NAME", "fileName");
		digester.addBeanPropertySetter(mainXmlTag + "/DOCUMENT_TYPE",
				"documentType");
		digester.addBeanPropertySetter(mainXmlTag + "/ACTIVITY_ID",
				"ACTIVITY_ID");
		digester.addBeanPropertySetter(mainXmlTag + "/BINARY_FILE_TYPE",
				"BINARY_FILE_TYPE");
		digester.addBeanPropertySetter(mainXmlTag + "/PROMOTE_DOCUMENT",
				"PROMOTE_DOCUMENT");
		digester.addBeanPropertySetter(mainXmlTag + "/EXTERNAL_DOCUMENT_URL",
				"EXTERNAL_DOCUMENT_URL");
		digester.addBeanPropertySetter(mainXmlTag + "/OBJECT_ID", "OBJECT_ID");
		digester.addBeanPropertySetter(mainXmlTag + "/EXTERNAL_DOCUMENT_ID",
				"EXTERNAL_DOCUMENT_ID");
		digester.addBeanPropertySetter(mainXmlTag + "/ack", "ack");

		// Move to next LauReportAttachments
		digester.addSetNext(mainXmlTag, "addXmlDataForAttachment");
		digester.parse(new StringReader(xml));

	}

	public void addXmlDataForAttachment(
			LauReportAttachments lauReportAttachments) {
		log.info("before add....");
		attachment.add(lauReportAttachments);
		log.info(lauReportAttachments.toString());
	}

	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(
				"DELETE FROM LAU_REPORT_ACTIVITIES WHERE ACTIVITY_ID = ?",
				new Object[] { this.reportActivities.getACTIVITY_ID() });
		log.info("LauReportActivityAttachmentSetDAO delete() ID -> " + id);
	}

	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId) throws Exception {
		// TODO Auto-generated method stub

	}

	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {
		int id = 0;
		long activityId = CommonDAO.getPrimaryKey(template);
		this.reportActivities.setACTIVITY_ID(activityId);
		this.activityId = activityId;
		if (this.reportActivities.getREPORT_ID() == 0) {
			this.reportActivities.setREPORT_ID(reportId);
		}
		log.info("Generated Primary Key for LAU_REPORT_ACTIVITIES  ->"
				+ activityId);
		// set peomote_activity to Y
		this.reportActivities.setPROMOTE_ACTIVITY("Y");
		id = template.update(SQL_INSERT_STRING, getParameters());
		log.info("LauReportActivityAttachmentSetDAO insert() ID -> " + id);

	}

	private final String SQL_INSERT_STRING = "INSERT INTO LAU_REPORT_ACTIVITIES (DISPLAY_NUMBER,CONTACT_ID,"
			+ "DIRECTION,CORPORATE_RECEIVED_DATE,LOCAL_RECEIVED_DATE,PARTNER_RECEIVED_DATE,ACTIVITY_DATE,ACTIVITY_TYPE,"
			+ "ACTIVITY_REASON,ACTIVITY_DESCRIPTION,PRIORITY,ASSIGNED_TO_USER,ASSIGNED_TO_GROUP,DUE_DATE,ACTIVITY_STATUS,"
			+ "COMPLETION_DATE,UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID,ACTIVITY_ID,PROMOTE_ACTIVITY) VALUES "
			+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public void attachOriginalFileToReport(SimpleJdbcTemplate template,
			long reportId, HttpServletRequest request) throws Exception {
		long attachmentId = CommonDAO.getPrimaryKey(template);
		this.reportAttachments.setAttachmentId(attachmentId);
		log.info(" in  Original file - this.reportAttachments() -> "
				+ this.reportAttachments.toString());
		int id = template.update(SQL_ATTACH_INSERT_STRING,
				getParametersAttachments());
		log.info("LauReportActivityAttachmentSetDAO insert() ID -> " + id);

	}
	public void processAttachmentAndAck(SimpleJdbcTemplate template, long reportId,
			HttpServletRequest request) throws Exception {

		int id = 0;
		//	e2bExchangeId  e2bAckId 
		this.reportAttachments.setACTIVITY_ID(activityId);
		if (this.reportAttachments.getReportId() == 0) {
			this.reportAttachments.setReportId(reportId);
		}

		log.info("this.reportAttachments.getAck() -> "
				+ this.reportAttachments.getAck());

		if ((this.reportAttachments.getAck().equalsIgnoreCase("Y"))
				&& (this.reportAttachments.getFileName().toLowerCase()
						.indexOf(".xml") > 0) && (e2bExchangeId.length() == 0) ) {
				log.info("in non e2b attachment and ack");
			// 1. Move the file from group inbox to report folder (attach TO THE REPORT)
			SPResponse sPRes = InBoxToReportsService
					.moveSPDocFromInBoxToReports(
							this.reportAttachments.getOBJECT_ID(), lauReportId);
			if (Boolean.valueOf(sPRes.getSuccess()) == true) {

				String ackFileName = "ACK_"
						+ this.reportAttachments.getFileName();
				log.info("ackFileName -> " + ackFileName);
				
				ResourceBundle resource = ReadConfig.getMessage();
				// Construct new URL of the original file after move
				String urlAfterMove = ReadConfig.replaceBackslash(resource
						.getString("SP_DOCUMENT_ROOT"))
						+ "/"
						+ lauReportId
						+ "/" + this.reportAttachments.getFileName();
				// 2. read the original file contents from sp and generate R ACK FILE
				String fileContents = E2BRejectOutput.writeContentsToFile(
						SPUtil.getSharePointDocument(urlAfterMove), "", "01");
				log.info("fileContents -> " + fileContents);
				// 3. UPLOAD THE ACK FILE TO UPLOAD folder
				SPResponse sPResponse = FileUploadService.uploadACK(request,
						ackFileName, fileContents, lauReportId);
				if (sPResponse.getSuccess().length() > 0) {
					if (Boolean.valueOf(sPResponse.getSuccess()) == true) {

						// 4. INSERT THE DETAILS TO LAU_REPORT_ATTACHMENTS TABLE
						// FOR ACK FILE
						long attachID = CommonDAO.getPrimaryKey(template);
						log.info("Generated Primary Key for LAU_REPORT_ATTACHMENT NEW ONE ->"
								+ attachID);
						// this.reportAttachments.setDocumentName(ackFileName);
						log.info("#################ACK file - this.reportAttachments() -> "
								+ sPResponse.toString());
						log.info("*********ACK file - this.reportAttachments() -> "
								+ this.reportAttachments.toString());
						id = template.update(
								SQL_ATTACH_INSERT_STRING,
								getACKParametersAttachments(ackFileName,
										sPResponse.getFileName(), attachID,
										sPResponse.getFileId()));

						// INSERT FILE USING OUTBOUND
						OutboundService.outBound(fileContents,
								sPResponse.getFileName());

						// INSERT THE DETAILS TO LAU_REPORT_ATTACHMENTS TABLE 						// FOR ORGINAL FILE
						log.info(" insert orig file NON e2b");
						attachOriginalFileToReport(template,reportId,  request);


					} else {
						log.info("sPResponse.getSuccess() -> "
								+ sPResponse.getSuccess());
					}
				}

			} else {
				log.error("A file with the same name and size has already been attached to the report. Please confirm that this is a unique attachment.");
				throw new Exception(sPRes.getMessage());
			}

		} 
		else {
			SPResponse sPRes = InBoxToReportsService
					.moveSPDocFromInBoxToReports(
							this.reportAttachments.getOBJECT_ID(), lauReportId);
			if (Boolean.valueOf(sPRes.getSuccess()) == true) {

				//  Insert an entry in attachment table FOR ORGINAL FILE
				log.info(" insert orig file e2b");
				attachOriginalFileToReport(template,reportId,  request);
			} else {
				log.error("Util.moveSPDocFromInBoxToReports() returned false");
				throw new Exception(sPRes.getMessage());
			}
			///////////////////process e2b acks//////////////////////////
			/////////////////////////////////////////////////////
			///// process e2b acks  - TO BE COMPLETED S.Abraham 03/06/2012
			log.info("...Returned E2b ACK id and length:" + e2bAckId + ", length:"+e2bAckId.length() );
			if ( (e2bExchangeId.length() > 0) &&   ( (e2bAckId.trim().length() > 0) && (!e2bAckId.trim().equalsIgnoreCase("0"))) )
			{
				log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				JdbcTemplate jdbcTemplate= new JdbcTemplate(ds);
				String ackStr ="";
				ackStr = jdbcTemplate.queryForObject(
						"select XML_CONTENT_CLOB from LAU_E2B_EXCHANGE_HISTORY where E2B_EXCHANGE_ID  = ?",
						new Object[]{e2bAckId},
						new RowMapper<String>() {
							public String mapRow(ResultSet rs, int rowNum) throws SQLException {
								String strClob = "";
								strClob = lobHandler.getClobAsString(rs,"XML_CONTENT_CLOB");
								return strClob;
							}
						});
				log.info("$&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
				log.info("...ack returned by e2b .."+ackStr);
				if (ackStr.length() > 0) {
					String ackFileName = "ACK_"
							+ this.reportAttachments.getFileName().substring(0, this.reportAttachments.getFileName().lastIndexOf(".")) +".xml";
					log.info("ackFileName -> " + ackFileName);
					// 3. UPLOAD THE ACK FILE TO UPLOAD folder
					SPResponse sPResponse = FileUploadService.uploadACK(
							request, ackFileName, ackStr, lauReportId);
					if (sPResponse.getSuccess().length() > 0) {
						if (Boolean.valueOf(sPResponse.getSuccess()) == true) {

							// 4. INSERT THE DETAILS TO LAU_REPORT_ATTACHMENTS FOR ACK FILE
							long attachID = CommonDAO.getPrimaryKey(template);

							id = template.update(
									SQL_ATTACH_INSERT_STRING,
									getACKParametersAttachments(ackFileName,
											sPResponse.getFileName(), attachID,
											sPResponse.getFileId()));

							// // 4. pLACE IN OUTBOUND
							OutboundService.outBound(ackStr,
									sPResponse.getFileName());
						} else {
							log.info("sPResponse.getSuccess() -> "
									+ sPResponse.getSuccess());
						}
					}

				}

			}
		}
	}

	// SQL Statements
	private final String SQL_ATTACH_INSERT_STRING = "INSERT INTO LAU_REPORT_ATTACHMENTS (ACTIVITY_ID,"
			+ "DOCUMENT_NAME,DOCUMENT_TYPE,BINARY_FILE_TYPE,FILE_NAME,PROMOTE_DOCUMENT,EXTERNAL_DOCUMENT_URL"
			+ ",OBJECT_ID,UPDATE_USER_ID,UPDATE_TIMESTAMP,ATTACHMENT_ID,REPORT_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

	private Object[] getParametersAttachments() {
		LauReportAttachments reportAttachments = this.reportAttachments;
		return new Object[] {
				reportAttachments.getACTIVITY_ID(),
				reportAttachments.getDocumentName(),
				reportAttachments.getDocumentType(),
				reportAttachments.getBINARY_FILE_TYPE(),
				reportAttachments.getFileName(),
				reportAttachments.getPROMOTE_DOCUMENT(),
				InBoxToReportsService.rewriteSPDocumentURL(lauReportId,
						reportAttachments.getFileName(),
						reportAttachments.getEXTERNAL_DOCUMENT_URL()),
						reportAttachments.getOBJECT_ID(), userId, dt,
						reportAttachments.getAttachmentId(),
						reportAttachments.getReportId() };
	}

	private Object[] getACKParametersAttachments(String ackDocName,
			String ackFileName, long attachmentID, String fileID) {
		LauReportAttachments reportAttachments = this.reportAttachments;
		return new Object[] {
				reportAttachments.getACTIVITY_ID(),
				ackDocName,
				reportAttachments.getDocumentType(),
				reportAttachments.getBINARY_FILE_TYPE(),
				ackFileName,
				reportAttachments.getPROMOTE_DOCUMENT(),
				InBoxToReportsService.rewriteSPDocumentURL(lauReportId,
						ackFileName,
						reportAttachments.getEXTERNAL_DOCUMENT_URL()), fileID,
						userId, dt, attachmentID, reportAttachments.getReportId() };
	}

	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, String user, Timestamp dstamp) throws Exception {
		// TODO Auto-generated method stub

	}
	public  ResultOutput  importE2bData(DataSource ds, String userId,String e2bExchangeId, String initOrFollow) throws SQLException {
		log.info("### in importE2bData (");
		Connection con = null;
		ResultOutput outVal = new ResultOutput();
		try {

			con 		= ds.getConnection();
			String sql 	= "{ call ? := LAU_E2B_INBOUND.import_e2b_message(?,?,?,?,?,?,?,?,?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.VARCHAR); //1
			stmt.setString(2, e2bExchangeId);//P_CLOB_ID NUMBER ,  :2
			stmt.registerOutParameter(3, OracleTypes.VARCHAR);//P_ERR_MSG OUT varchar2,  :3
			stmt.registerOutParameter(4, OracleTypes.VARCHAR);//P_MSG OUT varchar2,  :4
			stmt.registerOutParameter(5, OracleTypes.VARCHAR);// P_REPORT_ID_OUT out VARCHAR2,  :5
			stmt.registerOutParameter(6, OracleTypes.VARCHAR);//P_LAU_REPORT_ID_OUT out VARCHAR2,  :6
			stmt.registerOutParameter(7, OracleTypes.NUMBER);// P_ACK_ID_OUT out NUMBER,  :7

			stmt.setString(8,userId);// P_USER_ID varchar2,  :8 
			stmt.setString(9,"N");// P_MULTICASES VARCHAR2 DEFAULT 'N',  :9
			stmt.setString(10,initOrFollow);//P_PROCESSING_MODE VARCHAR2 DEFAULT 'AUTOMATED',  :10
			stmt.setString(11,"Y");//P_ACK VARCHAR2 DEFAULT 'Y',  :11
			stmt.setString(12,"N");	//P_DISPLAY_OUTPUT VARCHAR2 DEFAULT 'N'     :12
			log.info("15====");
			log.info(sql);
			stmt.execute();
			// FUNCTION output


			//int update = stmt.getInt(1);
			outVal.retVal= stmt.getString(1);// 0 - Error, > 0 success
			outVal.strError= stmt.getString(3);//P_ERR_MSG OUT varchar2,  :3
			outVal.strMsg= stmt.getString(4);//P_MSG OUT varchar2,  :4
			outVal.strRepId= stmt.getString(5);// P_REPORT_ID_OUT out VARCHAR2,  :5
			outVal.strLauRepId= stmt.getString(6);//P_LAU_REPORT_ID_OUT out VARCHAR2,  :6
			outVal.ackId= Integer.toString(stmt.getInt(7));//P_ACK_ID_OUT out NUMBER,  :7

			log.info("Funtion returned ...:"+outVal.retVal + ","+outVal.strError+ ","+outVal.strRepId );
			stmt.close();
			con.close();


		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				log.error(e, e);
			}
		}

		return outVal;

	}
	// Nested class to hold the return values from plsql package
    class ResultOutput {

		public String retVal = "0";
		public String strError= "";
		public String strMsg = "";
		public String strRepId = "";
		public String strLauRepId = "";
		public String ackId = "";
    }
    private int setCheckOut(long reportId,SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update("UPDATE LAU_REPORTS SET REPORT_RESERVED_DATE = ?,REPORT_RESERVED_BY = ?, "+
				" UPDATE_USER_ID=?, UPDATE_TIMESTAMP=? WHERE  REPORT_ID = ? and ( (REPORT_RESERVED_BY is null and REPORT_RESERVED_DATE is null)" +
				" or ( upper(REPORT_RESERVED_BY) = ?) )",
				getParameters(reportId,template));
		log.info("SetCheckoutAction update() ID -> " + id);
		return id;
	}
	private Object[] getParameters(long reportId,SimpleJdbcTemplate template) throws Exception	{
		return new Object[]{
			CommonDAO.getTimestamp(template),
			userId,
			userId,
			dt,
			reportId,
			userId.toUpperCase()
		};
	}
}
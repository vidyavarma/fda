package com.nrg.lau.tasks;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import javax.sql.DataSource;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.ExternalDocuments;
import com.nrg.lau.beans.LauJobScheduler;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.e2b.E2BFileOutput;
import com.nrg.lau.scheduler.FolderWatch;
import com.nrg.lau.sharepoint.GroupInboxService;
import com.nrg.lau.sharepoint.SPResponse;
import com.nrg.lau.utils.ReadConfig;

public class FolderWatchTaskLoadAndSplit implements Callable<Object> {

	protected static Logger log = Logger
			.getLogger("FolderWatchTaskLoadAndSplit");
	private FolderWatch folderWatch;
	private String jobName;
	private DataSource ds;
	private String fileName;

	public FolderWatchTaskLoadAndSplit(FolderWatch folderWatch, String jobName,
			DataSource ds, String fileName) {
		super();
		this.folderWatch = folderWatch;
		this.jobName = jobName;
		this.ds = ds;
		this.fileName = fileName;

	}

	@Override
	public Object call() throws Exception {

		try {

			// Step 1

			/*
			 * LOAD_AND_SPLIT(P_FILENAME IN CLOB, P_SPLIT varchar2 default 'Y',
			 * P_FILE varchar2 default NULL, //new P_ERR_MSG OUT varchar2, P_MSG
			 * OUT varchar2, P_TASK_ID_OUT OUT NUMBER, P_REF_E2B_MESSAGE_OUT OUT
			 * VARCHAR2, P_USER_GROUP_OUT OUT VARCHAR2, //new P_USER_ID
			 * varchar2, P_DISPLAY_OUTPUT VARCHAR2 DEFAULT 'N' ) return
			 * varchar2;
			 */

			String pRefE2bMsgOut = "";
			String errorMsgOut = "";
			String pTaskId = "";
			String pMsg = "";
			String userGroup = "";
			String userId = "primo";

			Connection con = null;
			con = ds.getConnection();
			log.info("Before auto commit: " + con.getAutoCommit());
			con.setAutoCommit(false);
			log.info("After auto commit: " + con.getAutoCommit());
			String sql = "{call ? := LAU_E2B_INBOUND.LOAD_AND_SPLIT(?,?,?,?,?,?,?,?,?,?)}"; // TOTAL
																							// 11
																							// parameters
																							// including
																							// the
																							// return
																							// value
			CallableStatement stmt = con.prepareCall(sql);
			stmt.registerOutParameter(1, OracleTypes.VARCHAR);
			log.info("File Name: "
					+ ReadConfig.getMessage().getString("folderWatchLocation")
					+ "\\" + fileName);
			String fileContents = getFileContents(new File(ReadConfig
					.getMessage().getString("folderWatchLocation")
					+ "\\"
					+ fileName));
			stmt.setCharacterStream(2, new StringReader(fileContents),
					fileContents.length());// P_FILENAME CLOB
			stmt.setString(3, "Y");// P_SPLIT
			// stmt.setCharacterStream(4, new
			// StringReader(fileName),fileName.length());
			stmt.setString(4, fileName);// P_FILE FILE NAME
			stmt.registerOutParameter(5, OracleTypes.VARCHAR);// P_ERR_MSG
			stmt.registerOutParameter(6, OracleTypes.VARCHAR);// P_MSG
			stmt.registerOutParameter(7, OracleTypes.NUMBER);// P_TASK_ID_OUT
			stmt.registerOutParameter(8, OracleTypes.VARCHAR);// P_REF_E2B_MESSAGE_OUT
			stmt.registerOutParameter(9, OracleTypes.VARCHAR);// P_USER_GROUP_OUT
			stmt.setString(10, userId);// P_USER_ID
			stmt.setString(11, "N"); // P_DISPLAY_OUTPUT
			stmt.execute();

			// FUNCTION output
			int update = stmt.getInt(1);
			errorMsgOut = stmt.getString(5);
			pMsg = stmt.getString(6);
			pTaskId = stmt.getString(7);
			pRefE2bMsgOut = stmt.getString(8);
			userGroup = stmt.getString(9);

			log.info("Return UPDATE: " + update);
			log.info("Return P_MSG: " + pMsg);
			log.info("Return P_TASK_ID_OUT: " + pTaskId);
			log.info("Return P_REF_E2B_MESSAGE_OUT: " + pRefE2bMsgOut);
			log.info("Return P_USER_GROUP_OUT: " + userGroup);

			LauJobScheduler lauJobScheduler = new LauJobScheduler();
			lauJobScheduler.setTHREAD_ID(jobName);
			if (errorMsgOut != null)
				lauJobScheduler.setJOB_COMMENT(errorMsgOut);

			stmt.close();
			// update == 2 call import for each record
			// p-multi cases parameter must be
			// non sanofi - N
			if (update > 0) {
				// Update the Job Status to completed
				lauJobScheduler.setSTATUS("C");
				folderWatch.updateJobStatus(lauJobScheduler);

				// Step 2
				// if the FUNCTION returns success, get required details from
				// LAU_E2B_EXCHANGE_HISTORY
				sql = "SELECT E2B_EXCHANGE_ID,E2B_XML_FILE_NAME,E2B_MESSAGE_TYPE,XML_CONTENT_CLOB,E2B_PARTNER_ID,E2B_MESSAGENUMB "
						+ "FROM LAU_E2B_EXCHANGE_HISTORY WHERE TASK_ID = "
						+ pTaskId
						+ " AND E2B_MESSAGE_TYPE != 'MULTI_CASES' AND REF_E2B_MESSAGENUMB = '"
						+ pRefE2bMsgOut + "'";

				List<LauJobScheduler> results = getJdbcTemplate().query(sql,
						new RowMapper<LauJobScheduler>() {
							public LauJobScheduler mapRow(ResultSet rs, int i)
									throws SQLException {

								LauJobScheduler jobScheduler = new LauJobScheduler();
								jobScheduler.setE2B_EXCHANGE_ID(rs
										.getLong("E2B_EXCHANGE_ID"));
								jobScheduler.setE2B_XML_FILE_NAME(rs
										.getString("E2B_XML_FILE_NAME"));
								jobScheduler.setE2B_MESSAGE_TYPE(rs
										.getString("E2B_MESSAGE_TYPE"));
								jobScheduler
										.setXML_CONTENT_CLOB(clobToString(rs
												.getClob("XML_CONTENT_CLOB")));
								jobScheduler.setE2B_PARTNER_ID(rs
										.getString("E2B_PARTNER_ID"));
								jobScheduler.setE2B_MESSAGENUMB(rs
										.getString("E2B_MESSAGENUMB"));

								return jobScheduler;
							}
						});

				// Iterate through results
				int cntrDoc = 1;
				for (LauJobScheduler jobScheduler : results) {

					log.info(jobScheduler.getE2B_EXCHANGE_ID() + " ---");
					log.info(jobScheduler.getE2B_XML_FILE_NAME() + " ---");
					log.info(jobScheduler.getE2B_MESSAGE_TYPE() + " ---");
					// log.info(jobScheduler.getXML_CONTENT_CLOB() + " ---");

					if (update == 1) { // 1 - Manual Import
						// Step 3
						// Create RTF from XML file contents
						InputStream is = new ByteArrayInputStream(jobScheduler
								.getXML_CONTENT_CLOB().getBytes());
						StreamSource source = new StreamSource(
								new InputStreamReader(is));
						StreamSource transformSource = new StreamSource(
								E2BFileOutput.class
										.getResourceAsStream("/E2Bstyle.xsl"));
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						Result result = new StreamResult(baos);

						TransformerFactory tFactory = TransformerFactory
								.newInstance();
						Transformer transformer = tFactory
								.newTransformer(transformSource);
						transformer.transform(source, result);

						// Step 4
						// SharePoint file upload
						String sharePointTitle = "";
						if (jobScheduler.getE2B_MESSAGE_TYPE()
								.equalsIgnoreCase("FRAGMENT")) {
							sharePointTitle = jobScheduler
									.getE2B_XML_FILE_NAME().substring(
											0,
											jobScheduler.getE2B_XML_FILE_NAME()
													.lastIndexOf("."))
									+ "-" + cntrDoc + ".rtf";
						} else {
							sharePointTitle = jobScheduler
									.getE2B_XML_FILE_NAME().substring(
											0,
											jobScheduler.getE2B_XML_FILE_NAME()
													.lastIndexOf("."))
									+ ".rtf";
						}

						SPResponse sPResponse = GroupInboxService
								.uploadToGroupInbox(userGroup, sharePointTitle,
										baos);
						cntrDoc++;
						// If true, file uploaded to SharePoint
						if (sPResponse.getSuccess().length() > 0) {
							if (Boolean.valueOf(sPResponse.getSuccess())) {

								log.info("Success "
										+ Boolean.valueOf(sPResponse
												.getSuccess()));
								log.info("sPResponse.getFileId() -> "
										+ sPResponse.getFileId());
								log.info("sPResponse.getMessage() -> "
										+ sPResponse.getMessage());
								log.info("sPResponse.getFileName() -> "
										+ sPResponse.getFileName());
								log.info("sPResponse.getFileUrl() -> "
										+ sPResponse.getFileUrl());

								// Step 5
								// insert to LAU_EXTERNAL_DOCUMENTS
								ExternalDocuments externalDocs = new ExternalDocuments();
								externalDocs.setStatus("New");
								externalDocs.setUser(ReadConfig.getMessage()
										.getString("systemUser"));
								externalDocs.setDstamp(CommonDAO
										.getTimestamp(getJdbcTemplate()));
								externalDocs.setDocumentName(sPResponse
										.getFileName());
								externalDocs.setDocumentTitle(sharePointTitle);
								externalDocs.setRejectReason("");
								externalDocs
										.setDocDescription("File Uploaded By "
												+ ReadConfig
														.getMessage()
														.getString("systemUser"));
								externalDocs
										.setObjectID(sPResponse.getFileId());
								externalDocs.setFileID(sPResponse.getFileId());
								externalDocs
										.setFileURL(sPResponse.getFileUrl());
								externalDocs.setGroupID(userGroup);
								externalDocs.setE2bExchangeId(jobScheduler
										.getE2B_EXCHANGE_ID());
								externalDocs.setE2B_PARTNER_ID(jobScheduler
										.getE2B_PARTNER_ID());
								externalDocs.setE2B_MESSAGENUMB(jobScheduler
										.getE2B_MESSAGENUMB());
								int rtn = insertDocumentStatus(externalDocs);
								log.info("Return from insertDocumentStatus(externalDocs) -> "
										+ rtn);

							}
						}
					} else if (update == 2) // import each message
					{
						importE2bData( userId,Long.toString(jobScheduler.getE2B_EXCHANGE_ID())) ;
					}
				}

				boolean fileDeleteStatus = folderWatch.fileDelete(new File(
						ReadConfig.getMessage()
								.getString("folderWatchLocation")
								+ "\\"
								+ fileName));
				log.info("File delete status: " + fileDeleteStatus);

			} else {
				// Update the Job Status to error
				lauJobScheduler.setSTATUS("E");
				folderWatch.updateJobStatus(lauJobScheduler);
			}

		} catch (Exception e) {
			log.error(e);
			// Update the Job Status to error
			LauJobScheduler lauJobScheduler = new LauJobScheduler();
			lauJobScheduler.setSTATUS("E");
			lauJobScheduler.setJOB_COMMENT(e.toString());
			folderWatch.updateJobStatus(lauJobScheduler);
		}

		return null;
	}

	private SimpleJdbcTemplate getJdbcTemplate() {
		return new SimpleJdbcTemplate(ds);
	}

	public int insertDocumentStatus(ExternalDocuments externalDocs)
			throws Exception {

		int id = 0;
		String sql = "INSERT INTO LAU_EXTERNAL_DOCUMENTS (DOCUMENT_STATUS,UPDATE_USER_ID,UPDATE_TIMESTAMP,"
				+ "DOCUMENT_REJECTION_REASON,OBJECT_ID,DOCUMENT_NAME,FILE_NAME,DOCUMENT_DESCRIPTION,DOCUMENT_URL,USER_GROUP_ID,"
				+ "EXTERNAL_DOCUMENT_ID,E2B_EXCHANGE_ID,E2B_PARTNER_ID,E2B_MESSAGENUMB,IS_E2B_RTF,FILE_TYPE,RECEIVED_DATE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,'Y','E2B',current_timestamp)";
		try {

			long externalDocumentId = CommonDAO
					.getPrimaryKey(getJdbcTemplate());
			log.info("New externalDocumentId -> " + externalDocumentId);
			id = getJdbcTemplate().update(
					sql,
					new Object[] { externalDocs.getStatus(),
							externalDocs.getUser(), externalDocs.getDstamp(),
							externalDocs.getRejectReason(),
							externalDocs.getObjectID(),
							externalDocs.getDocumentTitle(),
							externalDocs.getDocumentName(),
							externalDocs.getDocDescription(),
							externalDocs.getFileURL(),
							externalDocs.getGroupID(), externalDocumentId,
							externalDocs.getE2bExchangeId(),
							externalDocs.getE2B_PARTNER_ID(),
							externalDocs.getE2B_MESSAGENUMB() });
		} catch (Exception e) {
			log.info("LAU_EXTERNAL_DOCUMENTS insert failed: " + e.getMessage());
			log.error(e);
			throw e;
		}
		return id;
	}

	private String getFileContents(File f) throws Exception {

		String result = null;
		DataInputStream in = null;

		try {

			byte[] buffer = new byte[(int) f.length()];
			in = new DataInputStream(new FileInputStream(f));
			in.readFully(buffer);
			result = new String(buffer);

		} catch (Exception e) {
			log.error(e);
			throw e;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				log.error(e);
			}
		}
		return result;
	}

	private String clobToString(java.sql.Clob data) {

		final StringBuilder sb = new StringBuilder();
		try {

			final Reader reader = data.getCharacterStream();
			final BufferedReader br = new BufferedReader(reader);

			int b;
			while (-1 != (b = br.read())) {
				sb.append((char) b);
			}
			br.close();

		} catch (Exception e) {
			log.error("SQL. Could not convert CLOB to string ", e);
			return e.toString();
		}

		return sb.toString();
	}

	/**
	 * @param ds
	 * @param userId
	 * @param e2bExchangeId
	 * @param initOrFollow
	 * @throws SQLException
	 */
	public void importE2bData( String userId,
			String e2bExchangeId) throws SQLException {
		log.info("### in importE2bData (");
		Connection con = null;

		try {

			con = ds.getConnection();
			String sql = "{ call ? := LAU_E2B_INBOUND.import_e2b_message(?,?,?,?,?,?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.VARCHAR); // 1
			stmt.setString(2, e2bExchangeId);// P_CLOB_ID NUMBER , :2
			stmt.registerOutParameter(3, OracleTypes.VARCHAR);// P_ERR_MSG OUT
																// varchar2, :3
			stmt.registerOutParameter(4, OracleTypes.VARCHAR);// P_MSG OUT
																// varchar2, :4
			stmt.registerOutParameter(5, OracleTypes.VARCHAR);// P_REPORT_ID_OUT
																// out VARCHAR2,
																// :5
			stmt.registerOutParameter(6, OracleTypes.VARCHAR);// P_LAU_REPORT_ID_OUT
																// out VARCHAR2,
																// :6
			stmt.registerOutParameter(7, OracleTypes.NUMBER);// P_ACK_ID_OUT out
																// NUMBER, :7

			stmt.setString(8, userId);// P_USER_ID varchar2, :8
			stmt.setString(9, "N");// P_MULTICASES VARCHAR2 DEFAULT 'N', :9
			//stmt.setString(10, initOrFollow);// P_PROCESSING_MODE VARCHAR2
												// DEFAULT 'AUTOMATED', :10
			//stmt.setString(11, "Y");// P_ACK VARCHAR2 DEFAULT 'Y', :11
			//stmt.setString(12, "N"); // P_DISPLAY_OUTPUT VARCHAR2 DEFAULT 'N'
										// :12
			log.info("15====");
			log.info(sql);
			stmt.execute();
			// FUNCTION output

			// int update = stmt.getInt(1);
			String retVal = stmt.getString(1);// 0 - Error, > 0 success
			String strError = stmt.getString(3);// P_ERR_MSG OUT varchar2, :3
			String strMsg = stmt.getString(4);// P_MSG OUT varchar2, :4
			String strRepId = stmt.getString(5);// P_REPORT_ID_OUT out VARCHAR2,
												// :5
			String strLauRepId = stmt.getString(6);// P_LAU_REPORT_ID_OUT out
													// VARCHAR2, :6
			String ackId = Integer.toString(stmt.getInt(7));// P_ACK_ID_OUT out
															// NUMBER, :7

			log.info("Funtion returned ...:" + retVal + "," + strError + ","
					+ strRepId);
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

	}
}
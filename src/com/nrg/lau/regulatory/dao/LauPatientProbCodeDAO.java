package com.nrg.lau.regulatory.dao;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.commons.IReportNarrativeDAO;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.regulatory.beans.LauPatientProbCode;

public class LauPatientProbCodeDAO implements
IReportNarrativeDAO<LauPatientProbCode> {

	private static Logger log = Logger.getLogger(LauPatientProbCodeDAO.class);
	private Vector<LauPatientProbCode> reports = null;
	private LauPatientProbCode PatientProbCode = null;
	private java.sql.Timestamp dt = null;
	private String userId = "";

	protected void finalize() throws Throwable {
		this.reports.removeAllElements();
		this.reports = null;
		super.finalize();
	}

	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, String user, java.sql.Timestamp dstamp,
			HashMap<String, String> patientMap,  HashMap<String, List<Long>> productMap) throws Exception {
		log.info("save in ------------->");
		userId = user;
		dt = dstamp;
		// Check to make sure that XML is there in Request.
		if (request
				.getParameter(IReportsConstants.LAU_PATIENT_PROBLEM_PARAM_NAME) != null
				&& request.getParameter(
						IReportsConstants.LAU_PATIENT_PROBLEM_PARAM_NAME)
						.length() > 0) {

			log.info("LAU_PATIENT_PROBLEM_CODE save() LAU_PATIENT_PROBLEM_PARAM_NAME -> "
					+ request
					.getParameter(IReportsConstants.LAU_PATIENT_PROBLEM_PARAM_NAME));

			// Create LauReports beans from XML Request
			createBeansFromXml(request
					.getParameter(IReportsConstants.LAU_PATIENT_PROBLEM_PARAM_NAME));
			Iterator<LauPatientProbCode> itr = this.reports.iterator();

			// Report Id is 0 it's UPDATE else a new INSERT
			while (itr.hasNext()) {
				String patientDetId = "";
				long patientDetailsId = 0;
				LauPatientProbCode lauPatientProbCode = (LauPatientProbCode) itr
						.next();
				log.info("itr.hasNext() -> " + lauPatientProbCode.toString());
				this.PatientProbCode = null;
				this.PatientProbCode = lauPatientProbCode;
				log.info("this.PatientProbCode.getPatientDetailsID(): "
						+ this.PatientProbCode.getPatientDetailsID());
				patientDetId = this.PatientProbCode.getPatientDetailsID();
				if (this.PatientProbCode.getPatientDetailsID().length() == 0) {
					if (lauPatientProbCode.getINTERNAL_LINK_ID() == ""
							|| lauPatientProbCode.getINTERNAL_LINK_ID().equals(
									null)) {
						patientDetailsId = 0;
					} else {
						if (!patientMap.isEmpty() && patientMap != null) {
							patientDetId = (String) patientMap
									.get(lauPatientProbCode
											.getINTERNAL_LINK_ID().trim());
							// patientDetailsId = Long.parseLong(patientDetId);
						}
					}
					// log.info("patientDetailsId----->"+patientDetailsId);
					this.PatientProbCode.setPatientDetailsID(patientDetId);
				}
				log.info("this.PatientProbCode.setPatientDetailsID: "
						+ this.PatientProbCode.getPatientDetailsID());
				if (lauPatientProbCode.getPatientProbCodeID() == 0) {
					insert(template, reportId, patientDetId);
				} else {
					if (lauPatientProbCode.getTransactionType() == IReportsConstants.deleteFlag) {
						delete(template);
					} else
						update(template);
				}

			}// end of while(itr.hasNext())
		} else {
			log.info(IReportsConstants.LAU_PATIENT_PROBLEM_PARAM_NAME
					+ " not found in request");
		}
		log.info("<------------- save out ");
	}

	public void insert(SimpleJdbcTemplate template, long reportId,
			String patientDetId) throws Exception {
		log.info("insert in ------------->" + patientDetId);
		int id = 0;
		long patientDetailsId = 0;
		patientDetailsId = Long.parseLong(patientDetId);
		long probId = CommonDAO.getPrimaryKey(template);
		this.PatientProbCode.setPatientProbCodeID(probId);
		if (this.PatientProbCode.getPatientDetailsID().length() == 0) {
			this.PatientProbCode.setPatientDetailsID(patientDetId);
		}
		log.info("this.PatientProbCode.patientID: "
				+ this.PatientProbCode.getPatientDetailsID());
		if (this.PatientProbCode.getReportId() == 0) {
			this.PatientProbCode.setReportId(reportId);
		}
		log.info("Generated Primary Key for PATIENTPROBCODE_ID ->" + probId);
		id = template.update(SQL_INSERT_STRING, getInsertParameters(template));
		log.info("lauPatientProbCodeCodeDAO insert() ID -> " + id);

		log.info("<------------- insert out ");
	}

	public void update(SimpleJdbcTemplate template) throws Exception {
		log.info("Entered Update");
		int id = 0;
		id = template.update(SQL_UPDATE_STRING, getParameters());
		log.info("lauPatientProbCodeCodeDAO update() ID -> " + id);

	}

	private Object[] getParameters() {
		LauPatientProbCode lauPatProbCode = this.PatientProbCode;
		return new Object[] { lauPatProbCode.getDisplayNumber(),
				lauPatProbCode.getCodeSource(),
				lauPatProbCode.getProbCodeType(),
				lauPatProbCode.getProbCodeValue(), userId, dt,
				lauPatProbCode.getPatientDetailsID(),
				lauPatProbCode.getReportId(),
				lauPatProbCode.getPatientProbCodeID()

		};
	}

	private Object[] getInsertParameters(SimpleJdbcTemplate template) {
		LauPatientProbCode lauPatProbCode = this.PatientProbCode;
		log.info("PatientDetailsID------->"
				+ lauPatProbCode.getPatientDetailsID());
		return new Object[] {
				CommonDAO.getNextDisplayNumber(template,
						lauPatProbCode.getReportId(),
						"LAU_PATIENT_PROBLEM_CODES", 0),
						lauPatProbCode.getCodeSource(),
						lauPatProbCode.getProbCodeType(),
						lauPatProbCode.getProbCodeValue(), userId, dt,
						lauPatProbCode.getPatientDetailsID(),
						lauPatProbCode.getReportId(),
						lauPatProbCode.getPatientProbCodeID()

		};
	}

	public void createBeansFromXml(String xml) throws Exception {

		reports = new Vector<LauPatientProbCode>();
		Digester digester = new Digester();
		digester.push(this);
		String mainXmlTag = "ROWSET/ROW";
		digester.addObjectCreate(mainXmlTag, LauPatientProbCode.class);

		// Explicitly specify property
		digester.addBeanPropertySetter(mainXmlTag + "/DISPLAY_NUMBER",
				"displayNumber");
		digester.addBeanPropertySetter(mainXmlTag + "/REPORT_ID", "reportId");
		digester.addBeanPropertySetter(mainXmlTag + "/PROBLEM_CODE_TYPE",
				"probCodeType");
		digester.addBeanPropertySetter(mainXmlTag + "/CODING_SOURCE",
				"codeSource");
		digester.addBeanPropertySetter(mainXmlTag + "/PROBLEM_CODE_VALUE",
				"probCodeValue");
		digester.addBeanPropertySetter(mainXmlTag + "/PATIENT_DETAILS_ID",
				"patientDetailsID");
		digester.addBeanPropertySetter(mainXmlTag + "/PATIENT_PROBLEM_CODE_ID",
				"patientProbCodeID");
		digester.addBeanPropertySetter(mainXmlTag + "/TRANSACTION_TYPE",
				"transactionType");
		digester.addBeanPropertySetter(mainXmlTag + "/INTERNAL_LINK_ID",
				"INTERNAL_LINK_ID");
		// digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP",
		// "updateTimeStamp" );

		// Move to next LauReports
		digester.addSetNext(mainXmlTag, "addXmlData");
		digester.parse(new StringReader(xml));

	}

	public void addXmlData(LauPatientProbCode lauPatientProbCode) {
		reports.add(lauPatientProbCode);

	}

	// SQL Statements
	private final String SQL_UPDATE_STRING = "UPDATE LAU_PATIENT_PROBLEM_CODES SET DISPLAY_NUMBER = ?,CODING_SOURCE=?,PROBLEM_CODE_TYPE=?,"
			+ "PROBLEM_CODE_VALUE=?, UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE PATIENT_DETAILS_ID=? AND REPORT_ID=? AND PATIENT_PROBLEM_CODE_ID=?";

	private final String SQL_INSERT_STRING = "INSERT INTO LAU_PATIENT_PROBLEM_CODES(DISPLAY_NUMBER,CODING_SOURCE,PROBLEM_CODE_TYPE,"
			+ "PROBLEM_CODE_VALUE,UPDATE_USER_ID,UPDATE_TIMESTAMP,PATIENT_DETAILS_ID,REPORT_ID,PATIENT_PROBLEM_CODE_ID) "
			+ "VALUES (?,?,?,?,?,?,?,?,?)";

	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template
				.update("DELETE FROM LAU_PATIENT_PROBLEM_CODES WHERE PATIENT_PROBLEM_CODE_ID = ?",
						new Object[] { this.PatientProbCode
						.getPatientProbCodeID() });
		log.info("lauPatientProbCodeDAO delete() ID -> " + id);
	}

}

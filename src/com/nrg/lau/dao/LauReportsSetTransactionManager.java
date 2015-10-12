package com.nrg.lau.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.nrg.lau.beans.LauProducts;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.regulatory.dao.LauEvaluationCodeDAO;
import com.nrg.lau.regulatory.dao.LauPatientProbCodeDAO;
import com.nrg.lau.regulatory.dao.LauRemedialActionDAO;
import com.nrg.lau.regulatory.dao.LauReportSourceDAO;

public class LauReportsSetTransactionManager {
	private DataSourceTransactionManager transactionManager;
	private SimpleJdbcTemplate template;
	private static Logger log = Logger
			.getLogger(LauReportsSetTransactionManager.class);

	public void setTxManager(DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
		this.template = new SimpleJdbcTemplate(
				transactionManager.getDataSource());
	}

	public String setReport(HttpServletRequest request) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		String xmlStatus = "";
		java.sql.Timestamp dt = null;
		String userId = "";
		long reportId = 0;
		String lauReportId = "";
		try {

			// This is to set a common time stamp
			// for the entire transaction.

			userId = CommonDAO.getUSERID(request);
			dt = CommonDAO.getTimestamp(template);
			if (request.getParameter("reportId") != null) {
				String tRepId = request.getParameter("reportId");
				log.info("report id from parameter - :" + tRepId);
				if (tRepId.length() > 0)
					reportId = Long.valueOf(tRepId);
			}
			if (request.getParameter("laureportId") != null) {
				lauReportId = request.getParameter("laureportId");
				log.info("Lau report id from parameter - :" + lauReportId);

			}
			log.warn("CURRENT USER - " + userId);
			// set the user id in DB - to capture the user doing the delete in
			// audit log
			CommonDAO.setDbmsClientInfo(template, userId);

			// Update/Insert table LAU_REPORTS
			LauReportsSetDAO.deleteReportIdForReports = 0;
			LauReportsSetDAO lauReportsDAO = new LauReportsSetDAO();
			// long reportId = lauReportsDAO.save(request,
			// template,transactionManager.getDataSource());

			String[] reportIds = lauReportsDAO.save(request, template,
					transactionManager.getDataSource(), userId, dt);
			if (reportId == 0) {
				reportId = Long.valueOf(reportIds[0]);// [0] contains report id
				lauReportId = reportIds[1];// [1] contains lau report id
			}

			log.info("LauReportsTransactionManager - setReport() REPORT_ID & LAU_REPORT_ID from lauReportsDAO() -> "
					+ reportId + " , " + lauReportId);

			// Insert table LAU_REPORT_ACTIVITY_LOG
			LauActivityLogSetDAO lauActivityLogSetDAO = new LauActivityLogSetDAO();
			lauActivityLogSetDAO.save(request, template, reportId, userId, dt);
			// Insert table LAU_EVENT
			LauEventsSetDAO lauEventsSetDAO = new LauEventsSetDAO();
			lauEventsSetDAO.save(request, template, reportId, userId, dt);
			// _submitChangeData
			// Update/Insert table LAU_PATIENT_DETAILS
			LauPatientDetailsSetDAO lauPatientDetailsDAO = new LauPatientDetailsSetDAO();
			HashMap<String, String> patientMap = lauPatientDetailsDAO.save(
					request, template, reportId, userId, dt);
			/*
			 * log.info("Map value----> "); for (Map.Entry entry :
			 * patientMap.entrySet()) { log.info("key,val setReport----> " +
			 * entry.getKey() + "," + entry.getValue()); }
			 */

			HashMap<Long, List<Long>> patientDeleteMap = lauPatientDetailsDAO
					.saveDelete(request, template, reportId, userId, dt);

			// Update/Insert table LAU_PRODUCTS
			LauProductsSetDAO lauProductsSetDAO = new LauProductsSetDAO();
			List<Object> finalData = lauProductsSetDAO.save(
					request, template, reportId, userId, dt);
			//for(int i=0;i<finalData.size();i++){
			HashMap<Long, List<Long>> productDeleteMap = (HashMap<Long, List<Long>>) finalData.get(0);
			HashMap<String, List<Long>> productMap = (HashMap<String, List<Long>>) finalData.get(1);
			//}

			log.info("Map value for ProductMAP----> "); 
			for (Map.Entry entry : productMap.entrySet()) 
			{ 
				log.info("key,val ProductMap----> " +entry.getKey() + "," + entry.getValue()); 
			}	
			log.info("Map value for ProductDeleteMAP----> "); 
			for (Map.Entry entry : productMap.entrySet()) 
			{ 
				log.info("key,val ProductDeleteMap----> " +entry.getKey() + "," + entry.getValue());
			}


			//			HashMap<Long, List<Long>> productDeleteMap = lauProductsSetDAO
			//					.saveDelete(request, template, reportId, userId, dt);
			//			HashMap<String, List<Long>> productMap = lauProductsSetDAO.save(
			//					request, template, reportId, userId, dt);

			LauDeviceDetailsSetDAO deviceDetails = new LauDeviceDetailsSetDAO();
			List<Object> devicedetailsList = deviceDetails.save(request,
					template, reportId, productMap, userId, dt);

			LauDeviceProblemCodesSetDAO lauDeviceProblemCodesSetDAO = new LauDeviceProblemCodesSetDAO();
			/*
			 * lauDeviceProblemCodesSetDAO.save(request, template, reportId,
			 * devicedetailsList, userId, dt);
			 */
			lauDeviceProblemCodesSetDAO.save(request, template, reportId,
					devicedetailsList, productMap, userId, dt);

			LauProductAddressesSetDAO productAddress = new LauProductAddressesSetDAO();
			productAddress.save(request, template, reportId, productMap,
					userId, dt);

			LauProductComponentsSetDAO productComp = new LauProductComponentsSetDAO();
			productComp.save(request, template, reportId, productMap, userId,
					dt);

			LauDeviceReportDetailsDAO lauDeviceReportDetailsDAO = new LauDeviceReportDetailsDAO();
			lauDeviceReportDetailsDAO.save(request, template, reportId, userId,
					dt);

			log.info("in con-------> ");
			LauConPrevMedicationSetDAO lauConPrevMedicationDAO = new LauConPrevMedicationSetDAO();
			lauConPrevMedicationDAO.save(request, template, reportId, userId,
					dt, patientMap, productMap);

			// Insert/update tables from summary xml
			LauMultipleSummarySetDAO lauMultipleSummarySetDAO = new LauMultipleSummarySetDAO();
			lauMultipleSummarySetDAO.save(request, template, reportId, userId,
					dt);

			LauReportActivitiesSetDAO lauReportActivitiesSetDAO = new LauReportActivitiesSetDAO();
			long activityID = lauReportActivitiesSetDAO.save(request, template,
					reportId, userId, dt, "");
			log.info("activityID from lauReportActivitiesSetDAO.save -> "
					+ activityID);
			LauContactSetDAO lauContactDAO = new LauContactSetDAO();

			long contactID = lauContactDAO.save(request, template, reportId,
					userId, dt, activityID);
			log.info("contactID from lauContactDAO.save() -> " + contactID);

			// Update/Delete table LAU_REPORT_ATTACHMENTS
			LauAttachmentPropertiesSetDAO lauAttachmentPropertiesSetDAO = new LauAttachmentPropertiesSetDAO();
			lauAttachmentPropertiesSetDAO.save(request, template, reportId,
					userId, dt);

			LauReportActivitiesAttachmentSetDAO lauReportActivitiesAttachmentSetDAO = new LauReportActivitiesAttachmentSetDAO();
			lauReportActivitiesAttachmentSetDAO.save(request, template,
					reportId, userId, dt, activityID);

			LauExternalReferencesSetDAO lauExternalReferencesSetDAO = new LauExternalReferencesSetDAO();
			lauExternalReferencesSetDAO.save(request, template, reportId,
					userId, dt);

			LauEvaluationCodeDAO lauEvaluationCodeDAO = new LauEvaluationCodeDAO();
			lauEvaluationCodeDAO.save(request, template, reportId, userId, dt);

			LauRemedialActionDAO lauRemedialActionDAO = new LauRemedialActionDAO();
			lauRemedialActionDAO.save(request, template, reportId, userId, dt);

			LauReportSourceDAO lauReportSourceDAO = new LauReportSourceDAO();
			lauReportSourceDAO.save(request, template, reportId, userId, dt);

			LauPatientProbCodeDAO lauPatientProbCodeDAO = new LauPatientProbCodeDAO();
			lauPatientProbCodeDAO.save(request, template, reportId, userId, dt,
					patientMap, productMap);

			// Update/Insert table LAU_NARRATIVE_TEXT
			HashMap<String, Long> narrativeMap = new HashMap<String, Long>();
			LauNarrativeTextSetDAO lauNarrativeTextDAO = new LauNarrativeTextSetDAO();
			// lauNarrativeTextDAO.save(request, template, reportId, userId, dt,
			// patientMap, productMap);
			narrativeMap = lauNarrativeTextDAO.saveData(request, template,
					reportId, userId, dt, patientMap, productMap);

			/*			for (Map.Entry entry : narrativeMap.entrySet()) {
				log.info("key,val narrative Map----> " + entry.getKey() + ","
						+ entry.getValue());
			}*/

			LauLanguageNarrativeSetDAO lauLanguageNarrativeSetDAO = new LauLanguageNarrativeSetDAO();
			lauLanguageNarrativeSetDAO.saveData(request, template, reportId,
					userId, dt, patientMap, productMap, narrativeMap);
			// This is to check if Records need to be deleted from
			// Parent tables on deletion if TRANSACTION_TYPE equals 1
			validateParentRecordDeletion(template, productDeleteMap, userId, dt);
			validatePatientRecordDeletion(template, patientDeleteMap, userId,
					dt);
			// Added on 01/02/2012 S.Abraham to save summary data
			CommonDAO.setReportSummaryXml(template, reportId, userId);
			
			// 03/26/2014 S.Abraham Added to reset report/product review status
			log.info("before calling setReportReviewStatus ---> ");
			reSetReportReviewStatus(template,
					reportId, userId);
			log.info("AFTER calling setReportReviewStatus ---> ");
			transactionManager.commit(status);
			log.info("Transaction committed");
			xmlStatus = XMLException.status("Report save was successful",
					reportId, lauReportId);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			transactionManager.rollback(status);
			xmlStatus = XMLException.xmlError(e, "Report  failed");
		}
		return xmlStatus;
	}

	private void reSetReportReviewStatus(SimpleJdbcTemplate template,
			long reportId, String user) throws Exception {
		try {
			log.info("report id and user" + reportId + "," + user);

			int id = template.update(
					"begin LAU_UTL.reSetReportReviewStatus(?,?); end;",
					new Object[] { reportId, user });
			log.info("-------------------completed:"+id );

		} catch (Exception e) {
			log.error(e);
			throw new Exception(e);

		}

	}

	public void validateParentRecordDeletion(SimpleJdbcTemplate template,
			HashMap<Long, List<Long>> productDeleteMap, String user,
			java.sql.Timestamp dt) throws Exception {
		int id = 0;
		// Delete from LAU_PRODUCTS if TRANSACTION_TYPE equals 0
		if (productDeleteMap != null && productDeleteMap.size() > 0) {
			for (Long key : productDeleteMap.keySet()) {
				List<Long> list = productDeleteMap.get(key);
				log.info("list size: " + list.size());
				log.info("productDeleteMap Size---------->"
						+ productDeleteMap.size());
				if (list.get(0)
						.toString()
						.equalsIgnoreCase(
								String.valueOf(IReportsConstants.deleteFlag))) {
					log.info("Product delete db  section");
					for (int i = 0; i < productDeleteMap.size(); i++) {
						for (int n = 0; n < list.size(); n++) {
							template.update(
									"DELETE FROM LAU_DEVICE_PROBLEM_CODES WHERE PRODUCT_ID = ?",
									new Object[] { list.get(n) });
							template.update(
									"DELETE FROM LAU_DEVICE_DETAILS WHERE PRODUCT_ID = ?",
									new Object[] { list.get(n) });
							template.update(
									"DELETE FROM LAU_PRODUCT_ADDRESSES WHERE PRODUCT_ID= ?",
									new Object[] { list.get(n) });
							template.update(
									"DELETE FROM LAU_PRODUCT_COMPONENTS WHERE PRODUCT_ID = ?",
									new Object[] { list.get(n) });
							template.update(
									"DELETE FROM LAU_NARRATIVE_TEXT WHERE PRODUCT_ID = ?",
									new Object[] { list.get(n) });
							template.update(
									"DELETE FROM LAU_DOSING WHERE PRODUCT_ID = ?",
									new Object[] { list.get(n) });
							id = template
									.update("DELETE FROM LAU_PRODUCTS WHERE PRODUCT_ID = ?",
											new Object[] { list.get(n) });

							/*
							 * S.abraham 03/27/2014 No longer needed as this is
							 * taken care in UI
							 * CommonDAO.setReseqenceDisplayOrder(template,
							 * list.get(n), user, dt, "LAU_PRODUCTS",
							 * "DISPLAY_NUMBER", 1000, null, null, 1);
							 */
							log.info("LauProductsSetDAO delete() ID -> " + id);
						}
					}

				}
			}
		}
		// Delete from LAU_REPORTS if LauReportsSetDAO.deleteReportIdForReports
		// equals 1
		if (LauReportsSetDAO.deleteReportIdForReports != 0) {
			id = template.update("DELETE FROM LAU_REPORTS WHERE REPORT_ID = ?",
					new Object[] { LauReportsSetDAO.deleteReportIdForReports });
			log.info("LauReportsSetDAO delete() ID -> " + id);
		}
	}

	public void validatePatientRecordDeletion(SimpleJdbcTemplate template,
			HashMap<Long, List<Long>> patientDeleteMap, String user,
			java.sql.Timestamp dt) throws Exception {
		int id = 0;
		// Delete from LAU_PATIENTS if TRANSACTION_TYPE equals 0
		if (patientDeleteMap != null && patientDeleteMap.size() > 0) {
			for (Long key : patientDeleteMap.keySet()) {
				List<Long> list = patientDeleteMap.get(key);
				log.info("list size(): " + list.size());
				log.info("patientDeleteMap Size---------->"
						+ patientDeleteMap.size());
				if (list.get(0)
						.toString()
						.equalsIgnoreCase(
								String.valueOf(IReportsConstants.deleteFlag))) {
					log.info("Patient delete db  section");

					for (int i = 0; i < patientDeleteMap.size(); i++) {
						for (int n = 0; n < list.size(); n++) {
							log.info("patientDeleteMap Value ---------->"
									+ patientDeleteMap.get(key));
							log.info("List Value ---------->" + list.get(n));
							template.update(
									"DELETE FROM LAU_PATIENT_PROBLEM_CODES WHERE PATIENT_DETAILS_ID = ?",
									new Object[] { list.get(n) });
							template.update(
									"DELETE FROM LAU_NARRATIVE_TEXT WHERE PATIENT_DETAILS_ID = ?",
									new Object[] { list.get(n) });

							id = template
									.update("DELETE FROM LAU_PATIENT_DETAILS WHERE PATIENT_DETAILS_ID = ?",
											new Object[] { list.get(n) });
							log.info("LauPatientSetDAO delete() ID ->" + id
									+ "in Loop:" + i);
						}
					}

				}
			}
		}
		// Delete from LAU_REPORTS if LauReportsSetDAO.deleteReportIdForReports
		// equals 1
		if (LauReportsSetDAO.deleteReportIdForReports != 0) {
			id = template.update("DELETE FROM LAU_REPORTS WHERE REPORT_ID = ?",
					new Object[] { LauReportsSetDAO.deleteReportIdForReports });
			log.info("LauReportsSetDAO delete() ID -> " + id);
		}
	}
}

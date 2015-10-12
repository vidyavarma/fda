package com.nrg.lau.junit;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.apache.log4j.Logger;

import com.nrg.lau.dao.LauNarrativeTextSetDAO;
import com.nrg.lau.dao.LauReportsSetTransactionManager;

public class LauReportsSetTransactionMgr {
	private static Logger log	= Logger.getLogger(LauReportsSetTransactionMgr.class);
	public static long reportId = 0;
	public static long transactionType = 0;
	public static void runReports(ClassPathXmlApplicationContext ctx)	{
		MockHttpServletRequest request = new MockHttpServletRequest();
		setRequestData(request);
		LauReportsSetTransactionManager lauReportsMgr = (LauReportsSetTransactionManager)ctx.getBean("setReport");
		request.setAttribute("lauUsers","unitest");
		log.info("user set");
		lauReportsMgr.setReport(request);
	}
	
	public static void setRequestData(MockHttpServletRequest request)	{
		
		request.setMethod("POST");		
		int action = 0; //	0-INSERT & 1-UPDATE
		if(action != 0)		{
			reportId = 172;
			transactionType = 1; //	This is for DELETE
		}
		//user id
		
		
		//table LAU_REPORTS
		setReportTable(request, action);
		
		//table LAU_PATIENT_DETAILS
		LauRequestData1.setPatientDetailsTable(request, action);
		//table LAU_MEDICAL_HISTORY
		LauRequestData1.setMedicalHistoryTable(request, action);
		//table LAU_ACTION_ITEMS
		LauRequestData1.setActionItemsTable(request, action);
		//table LAU_CONTACT
		LauRequestData1.setContactTable(request, action);
		//table LAU_EVENTS
		LauRequestData1.setEventsTable(request, action);

		//table LAU_NARRATIVE_TEXT
		LauRequestData2.setNarrativeTextTable(request, action);		
		//table LAU_LAB_TESTS
		LauRequestData2.setLabTestsTable(request, action);
		//table LAU_PREGNANCY_DETAILS
		LauRequestData2.setPregnancyDetailsTable(request, action);
		//table LAU_PREGNANCY_HISTORY
		LauRequestData2.setPregnancyHistoryTable(request, action);
		//table LAU_PREGNANCY_OUTCOME
		LauRequestData2.setPregnancyOutcomeTable(request, action);
		
		//table LAU_RELATED_REPORTS
		LauRequestData3.setRelatedReportsTable(request, action);
		//table LAU_REPORTER_DETAILS
		LauRequestData3.setReporterDetailsTable(request, action);
		//table LAU_PRODUCTS
		LauRequestData3.setProductsTable(request, action);
		//table LAU_DOSING
		LauRequestData3.setDosingTable(request, action);
		
       

	}
	
	public static void setReportTable(MockHttpServletRequest request,int action)	{
		String val = "";  
		if(action == 0)	{
/*			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<REPORT_ID></REPORT_ID>" +
					"<LAU_REPORT_ID>2010-10abcd551</LAU_REPORT_ID>" +
					"<EXTERNAL_SOURCE_SYSTEM_ID>9999</EXTERNAL_SOURCE_SYSTEM_ID>" +
					"<REPORT_STATUS>open</REPORT_STATUS>" +
					"<ADVERSE_EVENT_FLAG/>" +
					"<PRODUCT_COMPLAINT_FLAG/>" +
					"<PREGNANCY_REPORT_FLAG/>" +
					"<REPORT_INITIAL_OR_FOLLOWUP/>" +
					"<REPORT_ORIGINATOR_TYPE/>" +
					"<REPORT_CONTACT_DATE/>" +
					"<REPORT_PROCESSED_DATE/>" +
					"<REPORT_REVIEW_STATUS/>" +
					"<REPORT_ALERT/>" +
					"<REPORT_ALERT_COMMENT/>" +
					"<COUNTRY_EVENT_OCCURRED/>" +
					"<REPORT_CREATE_DATE/>" +
					"<REPORT_CREATE_USER_ID/>" +
					"<REPORT_CREATE_USER_GROUP/>" +
					"<REPORT_RESERVED_DATE/>" +
					"<REPORT_RESERVED_BY/>" +
					"<REPORT_CLOSED_DATE/>" +
					"<ROUTE_TO_USER_GROUP/>" +
					"<ROUTE_TO_USER/>" +
					"<DEMOTION_CANDIDATE/>" +
					"<DEMOTION_REASON/>" +
					"<DEMOTION_REASON_DETAILS/>" +
					"<PROMOTED_REPORT_OWNER/>" +
					"<PROMOTED_AE_CASE_ID/>" +
					"<PROMOTED_PC_CASE_ID/>" +
					"<OTHER_CASE_ID/>" +
					"<OTHER_CASE_ID_TYPE/>" +
					"<FOLLOW_UP_TO_CASE_ID/>" +
					"<REPORT_COMMENTS>Create a users file and realm</REPORT_COMMENTS>" +
					"<UPDATE_USER_ID>saju - update 012200</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>04-MAR-10 09.53.39 AM</UPDATE_TIMESTAMP>" +
					"<TRANSACTION_TYPE>" + transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";*/
	
			val = "<?xml version='1.0'?>" +
			"<ROWSET><ROW>" +
			"<REPORT_ID></REPORT_ID>" +
			"<LAU_REPORT_ID>2010-11122aab</LAU_REPORT_ID>" +
			"<EXTERNAL_SOURCE_SYSTEM_ID>9999</EXTERNAL_SOURCE_SYSTEM_ID>" +
			"<REPORT_STATUS>open</REPORT_STATUS>" +
			"<ADVERSE_EVENT_FLAG/>" +
			"<PRODUCT_COMPLAINT_FLAG/>" +
			"<PREGNANCY_REPORT_FLAG/>" +
			"<REPORT_INITIAL_OR_FOLLOWUP/>" +
			"<REPORT_ORIGINATOR_TYPE/>" +
			"<REPORT_CONTACT_DATE/>" +
			"<REPORT_PROCESSED_DATE/>" +
			"<REPORT_REVIEW_STATUS/>" +
			"<REPORT_ALERT/>" +
			"<REPORT_ALERT_COMMENT/>" +
			"<COUNTRY_EVENT_OCCURRED/>" +
			"<REPORT_CREATE_DATE/>" +
			"<REPORT_CREATE_USER_ID/>" +
			"<REPORT_CREATE_USER_GROUP/>" +
			"<REPORT_RESERVED_DATE/>" +
			"<REPORT_RESERVED_BY/>" +
			"<REPORT_CLOSED_DATE/>" +
			"<ROUTE_TO_USER_GROUP/>" +
			"<ROUTE_TO_USER/>" +
			"<DEMOTION_CANDIDATE/>" +
			"<DEMOTION_REASON/>" +
			"<DEMOTION_REASON_DETAILS/>" +
			"<PROMOTED_REPORT_OWNER/>" +
			"<PROMOTED_AE_CASE_ID/>" +
			"<PROMOTED_PC_CASE_ID/>" +
			"<OTHER_CASE_ID/>" +
			"<OTHER_CASE_ID_TYPE/>" +
			"<FOLLOW_UP_TO_CASE_ID/>" +
			"<REPORT_COMMENTS>Create a users file and realm</REPORT_COMMENTS>" +
			"<UPDATE_USER_ID>saju - update 012200</UPDATE_USER_ID>" +
			"<UPDATE_TIMESTAMP>04-MAR-10 09.53.39 AM</UPDATE_TIMESTAMP>" +
			"</ROW>" +
			"</ROWSET>" ;		
		
		}else	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<REPORT_ID>" + reportId + "</REPORT_ID>" +
					"<LAU_REPORT_ID>2010-xxx01</LAU_REPORT_ID>" +
					"<EXTERNAL_SOURCE_SYSTEM_ID>9999</EXTERNAL_SOURCE_SYSTEM_ID>" +
					"<REPORT_STATUS>open</REPORT_STATUS>" +
					"<ADVERSE_EVENT_FLAG/>" +
					"<PRODUCT_COMPLAINT_FLAG/>" +
					"<PREGNANCY_REPORT_FLAG/>" +
					"<REPORT_INITIAL_OR_FOLLOWUP/>" +
					"<REPORT_ORIGINATOR_TYPE/>" +
					"<REPORT_CONTACT_DATE/>" +
					"<REPORT_PROCESSED_DATE/>" +
					"<REPORT_REVIEW_STATUS/>" +
					"<REPORT_ALERT/>" +
					"<REPORT_ALERT_COMMENT/>" +
					"<COUNTRY_EVENT_OCCURRED/>" +
					"<REPORT_CREATE_DATE/>" +
					"<REPORT_CREATE_USER_ID/>" +
					"<REPORT_CREATE_USER_GROUP/>" +
					"<REPORT_RESERVED_DATE/>" +
					"<REPORT_RESERVED_BY/>" +
					"<REPORT_CLOSED_DATE/>" +
					"<ROUTE_TO_USER_GROUP/>" +
					"<ROUTE_TO_USER/>" +
					"<DEMOTION_CANDIDATE/>" +
					"<DEMOTION_REASON/>" +
					"<DEMOTION_REASON_DETAILS/>" +
					"<PROMOTED_REPORT_OWNER/>" +
					"<PROMOTED_AE_CASE_ID/>" +
					"<PROMOTED_PC_CASE_ID/>" +
					"<OTHER_CASE_ID/>" +
					"<OTHER_CASE_ID_TYPE/>" +
					"<FOLLOW_UP_TO_CASE_ID/>" +
					"<REPORT_COMMENTS>Create a users file and realm</REPORT_COMMENTS>" +
					"<UPDATE_USER_ID>JUnit update</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>04-MAR-10 09.53.39 AM</UPDATE_TIMESTAMP>" +
					"<TRANSACTION_TYPE>" + transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		}
		 request.setParameter("lauReportsXmlData",val);
		
	}
	
}

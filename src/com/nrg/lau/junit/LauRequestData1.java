package com.nrg.lau.junit;

import org.springframework.mock.web.MockHttpServletRequest;

public class LauRequestData1 {
	
	public static void setPatientDetailsTable(MockHttpServletRequest request,int action)	{
		String val = "";  
		if(action == 0)	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<PATIENT_DETAILS_ID></PATIENT_DETAILS_ID>" +
					"<REPORT_ID></REPORT_ID>" +
					"<PT_SEX>M</PT_SEX>" +
					"<PT_DOB>10011967</PT_DOB>" +
					"<PT_AGE>43</PT_AGE>" +
					"<PT_AGE_UNITS>Y</PT_AGE_UNITS>" +
					"<PT_AGE_GROUP>AD</PT_AGE_GROUP>" +
					"<PT_TITLE>DR</PT_TITLE>" +
					"<PT_FIRST_NAME>Dan John</PT_FIRST_NAME>" +
					"<PT_MIDDLE_NAME>M</PT_MIDDLE_NAME>" +
					"<PT_LAST_NAME>Majir - R</PT_LAST_NAME>" +
					"<PT_INITIALS>DM</PT_INITIALS>" +
					"<PT_ADDRESS1>ADD1</PT_ADDRESS1>" +
					"<PT_ADDRESS2>ADD2</PT_ADDRESS2>" +
					"<PT_ADDRESS3>ADD3</PT_ADDRESS3>" +
					"<PT_CITY>Boston</PT_CITY>" +
					"<PT_STATE>MA</PT_STATE>" +
					"<PT_POSTAL_CODE/>" +
					"<PT_COUNTRY/>" +
					"<PT_PHONE_NUMBER>617-223-4567</PT_PHONE_NUMBER>" +
					"<PT_FAX_NUMBER/>" +
					"<PT_EMAIL>dm@someserver.com</PT_EMAIL>" +
					"<DEATH_DATE/>" +
					"<CAUSE_OF_DEATH/>" +
					"<AUTOPSY_PERFORMED/>" +
					"<STUDY_ID/>" +
					"<STUDY_PATIENT_ID/>" +
					"<STUDY_ENROLLMENT_STATUS/>" +
					"<STUDY_ENROLLMENT_DATE/>" +
					"<STUDY_WITHDRAWN_DATE/>" +
					"<PATIENT_IDENTIFIER1/>" +
					"<PATIENT_IDENTIFIER2/>" +
					"<PATIENT_IDENTIFIER3/>" +
					"<PATIENT_IDENTIFIER4/>" +
					"<PATIENT_INDICATOR1/>" +
					"<PATIENT_INDICATOR2/>" +
					"<PATIENT_INDICATOR3/>" +
					"<PATIENT_INDICATOR4/>" +
					"<CONSENT_TO_CONTACT_HCP/>" +
					"<HCP_CONTACT_DETAILS_AVAILABLE/>" +
					"<CONSENT_TO_USE_PERSONAL_DTLS/>" +
					"<UPDATE_USER_ID>saju - update 012200</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>08-MAR-10 02.40.46 PM</UPDATE_TIMESTAMP>" +
					"<PT_PREGNANCY_STATUS>1</PT_PREGNANCY_STATUS>"+
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		
		
		}else	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<PATIENT_DETAILS_ID>" + (LauReportsSetTransactionMgr.reportId + 1) + "</PATIENT_DETAILS_ID>" +
					"<REPORT_ID>" + LauReportsSetTransactionMgr.reportId + "</REPORT_ID>" +
					"<PT_SEX>M</PT_SEX>" +
					"<PT_DOB>10011967</PT_DOB>" +
					"<PT_AGE>43</PT_AGE>" +
					"<PT_AGE_UNITS>Y</PT_AGE_UNITS>" +
					"<PT_AGE_GROUP>AD</PT_AGE_GROUP>" +
					"<PT_TITLE>DR</PT_TITLE>" +
					"<PT_FIRST_NAME>Dan John</PT_FIRST_NAME>" +
					"<PT_MIDDLE_NAME>M</PT_MIDDLE_NAME>" +
					"<PT_LAST_NAME>Majir - R</PT_LAST_NAME>" +
					"<PT_INITIALS>DM</PT_INITIALS>" +
					"<PT_ADDRESS1>ADD1</PT_ADDRESS1>" +
					"<PT_ADDRESS2>ADD2</PT_ADDRESS2>" +
					"<PT_ADDRESS3>ADD3</PT_ADDRESS3>" +
					"<PT_CITY>Boston</PT_CITY>" +
					"<PT_STATE>MA</PT_STATE>" +
					"<PT_POSTAL_CODE/>" +
					"<PT_COUNTRY/>" +
					"<PT_PHONE_NUMBER>617-223-4567</PT_PHONE_NUMBER>" +
					"<PT_FAX_NUMBER/>" +
					"<PT_EMAIL>dm@someserver.com</PT_EMAIL>" +
					"<DEATH_DATE/>" +
					"<CAUSE_OF_DEATH/>" +
					"<AUTOPSY_PERFORMED/>" +
					"<STUDY_ID/>" +
					"<STUDY_PATIENT_ID/>" +
					"<STUDY_ENROLLMENT_STATUS/>" +
					"<STUDY_ENROLLMENT_DATE/>" +
					"<STUDY_WITHDRAWN_DATE/>" +
					"<PATIENT_IDENTIFIER1/>" +
					"<PATIENT_IDENTIFIER2/>" +
					"<PATIENT_IDENTIFIER3/>" +
					"<PATIENT_IDENTIFIER4/>" +
					"<PATIENT_INDICATOR1/>" +
					"<PATIENT_INDICATOR2/>" +
					"<PATIENT_INDICATOR3/>" +
					"<PATIENT_INDICATOR4/>" +
					"<CONSENT_TO_CONTACT_HCP/>" +
					"<HCP_CONTACT_DETAILS_AVAILABLE/>" +
					"<CONSENT_TO_USE_PERSONAL_DTLS/>" +
					"<UPDATE_USER_ID>JUnit update</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>08-MAR-10 02.40.46 PM</UPDATE_TIMESTAMP>" +
					"<PT_PREGNANCY_STATUS>1</PT_PREGNANCY_STATUS>"+
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		}
		 request.setParameter("lauPatientDetailsXmlData",val);
		
	}
	
	public static void setMedicalHistoryTable(MockHttpServletRequest request,int action)	{
		String val = "";  
		if(action == 0)	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<MEDICAL_HISTORY_ID></MEDICAL_HISTORY_ID>" +
					"<REPORT_ID></REPORT_ID>" +
					"<MEDICAL_HISTORY_VERBATIM/>" +
					"<DISPLAY_NUMBER></DISPLAY_NUMBER>" +
					"<ONSET_DATE/>" +
					"<ONGOING_FLAG/>" +
					"<END_DATE/>" +
					"<UPDATE_USER_ID>test -- update 00</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>18-MAR-10 12.34.54 PM</UPDATE_TIMESTAMP>" +
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		
		
		}else	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<MEDICAL_HISTORY_ID>" + (LauReportsSetTransactionMgr.reportId + 2) + "</MEDICAL_HISTORY_ID>" +
					"<REPORT_ID>" + LauReportsSetTransactionMgr.reportId + "</REPORT_ID>" +
					"<MEDICAL_HISTORY_VERBATIM/>" +
					"<DISPLAY_NUMBER></DISPLAY_NUMBER>" +
					"<ONSET_DATE/>" +
					"<ONGOING_FLAG/>" +
					"<END_DATE/>" +
					"<UPDATE_USER_ID>JUnit update</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>18-MAR-10 12.34.54 PM</UPDATE_TIMESTAMP>" +
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		}
		 request.setParameter("lauMedicalHistoryXmlData",val);
		
	}
	
	public static void setActionItemsTable(MockHttpServletRequest request,int action)	{
		String val = "";  
		if(action == 0)	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<ACTION_ITEM_ID></ACTION_ITEM_ID>" +
					"<REPORT_ID></REPORT_ID>" +
					"<DISPLAY_NUMBER>2</DISPLAY_NUMBER>" +
					"<ACTION_ITEM_TYPE/>" +
					"<ACTION_ITEM_DESCRIPTION/>" +
					"<PRIORITY/>" +
					"<ASSIGNED_TO_USER/>" +
					"<ASSIGNED_TO_GROUP/>" +
					"<DUE_DATE/>" +
					"<ACTION_ITEM_STATUS/>" +
					"<UPDATE_USER_ID>firsttest - 0000</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>10-MAR-10 11.39.30 AM</UPDATE_TIMESTAMP>" +
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		
		
		}else	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<ACTION_ITEM_ID>" + (LauReportsSetTransactionMgr.reportId + 3) + "</ACTION_ITEM_ID>" +
					"<REPORT_ID>" + LauReportsSetTransactionMgr.reportId + "</REPORT_ID>" +
					"<DISPLAY_NUMBER>2</DISPLAY_NUMBER>" +
					"<ACTION_ITEM_TYPE/>" +
					"<ACTION_ITEM_DESCRIPTION/>" +
					"<PRIORITY/>" +
					"<ASSIGNED_TO_USER/>" +
					"<ASSIGNED_TO_GROUP/>" +
					"<DUE_DATE/>" +
					"<ACTION_ITEM_STATUS/>" +
					"<UPDATE_USER_ID>JUnit update</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>10-MAR-10 11.39.30 AM</UPDATE_TIMESTAMP>" +
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		}
		 request.setParameter("lauActionItemsXmlData",val);
		
	}
	
	public static void setContactTable(MockHttpServletRequest request,int action)	{
		String val = "";  
		if(action == 0)	{
/*			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<CONTACT_ID></CONTACT_ID>" +
					"<mx_internal_uid>5C5BD3CE-EBB0-B38E-58AF-1B9DA342F0BD</mx_internal_uid>"+
					"<REPORT_ID></REPORT_ID>" +
					"<DISPLAY_NUMBER/>" +
					"<CONTACT_TYPE/>" +
					"<CONTACT_METHOD/>" +
					"<CONTACT_DATE/>" +
					"<CONTACTOR_TYPE/>" +
					"<CONTACT_TITLE/>" +
					"<CONTACT_FIRST_NAME/>" +
					"<CONTACT_MIDDLE_NAME/>" +
					"<CONTACT_LAST_NAME/>" +
					"<CONTACT_ADDRESS1/>" +
					"<CONTACT_ADDRESS2/>" +
					"<CONTACT_ADDRESS3/>" +
					"<CONTACT_CITY/>" +
					"<CONTACT_STATE/>" +
					"<CONTACT_POSTAL_CODE/>" +
					"<CONTACT_COUNTRY/>" +
					"<CONTACT_PHONE_NUMBER/>" +
					"<CONTACT_FAX_NUMBER/>" +
					"<CONTACT_EMAIL/>" +
					"<CONTACT_OCCUPATION/>" +
					"<CONTACT_SPECIALTY/>" +
					"<CONTACT_COMMENT/>" +
				    "<TRANSACTION_TYPE>0</TRANSACTION_TYPE>" +
				    "<UPDATE_TIMESTAMP/>" +
				    "<UPDATE_USER_ID>admin</UPDATE_USER_ID>" +
					"</ROW></ROWSET>";*/
		
			val = "<?xml version='1.0'?>" +
			"<ROWSET><ROW>" +
			"<CONSENT_TO_CONTACT_HCP>0</CONSENT_TO_CONTACT_HCP>" +
		    "<CONTACT_ADDRESS1/>" +
		    "<CONTACT_ADDRESS2/>" +
		    "<CONTACT_ADDRESS3/>" +
		    "<CONTACT_CITY/>" +
		    "<CONTACT_COMMENT/>" +
		    "<CONTACT_COUNTRY>USA</CONTACT_COUNTRY>" +
		    "<CONTACT_DATE/>" +
		    "<CONTACT_EMAIL/>" +
		    "<CONTACT_FAX_NUMBER/>" +
		    "<CONTACT_FIRST_NAME>wex</CONTACT_FIRST_NAME>" +
		    "<CONTACT_ID/>" +
		    "<CONTACT_LAST_NAME>Balm</CONTACT_LAST_NAME>" +
		    "<CONTACT_METHOD/>" +
		    "<CONTACT_MIDDLE_NAME/>" +
		    "<CONTACT_OCCUPATION/>" +
		    "<CONTACT_PHONE_NUMBER/>" +
		    "<CONTACT_POSTAL_CODE/>" +
		    "<CONTACT_SPECIALTY/>" +
		    "<CONTACT_STATE/>" +
			 "<CONTACT_REASON>Mr.</CONTACT_REASON>" +
			 "<CONTACT_IS_REPORTER>Mr.</CONTACT_IS_REPORTER>" +		    
		    "<CONTACT_TITLE>Mr.</CONTACT_TITLE>" +
		    "<CONTACT_TYPE>TREATING MD</CONTACT_TYPE>" +
		    "<CONTACTOR_TYPE>1</CONTACTOR_TYPE>" +
		    "<DISPLAY_NUMBER>1</DISPLAY_NUMBER>" +
		    "<mx_internal_uid>5C5BD3CE-EBB0-B38E-58AF-1B9DA342F0BD</mx_internal_uid>" +
		    "<REPORT_ID/>" +
		    "<TRANSACTION_TYPE>0</TRANSACTION_TYPE>" +
		    "<UPDATE_TIMESTAMP/>" +
		    "<UPDATE_USER_ID>admin</UPDATE_USER_ID>" +
		    "</ROW></ROWSET>";

		}else	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<CONTACT_ID>" + (LauReportsSetTransactionMgr.reportId + 4) + "</CONTACT_ID>" +
					"<REPORT_ID>" + LauReportsSetTransactionMgr.reportId + "</REPORT_ID>" +
					"<DISPLAY_NUMBER/>" +
					"<CONTACT_TYPE/>" +
					"<CONTACT_METHOD/>" +
					"<CONTACT_DATE/>" +
					"<CONTACTOR_TYPE/>" +
					"<CONTACT_TITLE>CONTACT_TITLE</CONTACT_TITLE>" +
					"<CONTACT_FIRST_NAME/>" +
					"<CONTACT_MIDDLE_NAME/>" +
					"<CONTACT_LAST_NAME/>" +
					"<CONTACT_ADDRESS1/>" +
					"<CONTACT_ADDRESS2/>" +
					"<CONTACT_ADDRESS3/>" +
					"<CONTACT_CITY/>" +
					"<CONTACT_STATE/>" +
					"<CONTACT_POSTAL_CODE/>" +
					"<CONTACT_COUNTRY/>" +
					"<CONTACT_PHONE_NUMBER/>" +
					"<CONTACT_FAX_NUMBER/>" +
					"<CONTACT_EMAIL/>" +
					"<CONTACT_OCCUPATION/>" +
					 "<CONTACT_REASON>Mr.</CONTACT_REASON>" +
					 "<CONTACT_IS_REPORTER>Mr.</CONTACT_IS_REPORTER>" +
					"<CONTACT_SPECIALTY/>" +
					"<CONTACT_COMMENT/>" +
					"<UPDATE_USER_ID>JUnit update</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>18-MAR-10 12.34.54 PM</UPDATE_TIMESTAMP>" +
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		}
		 request.setParameter("lauContactXmlData",val);
		
	}
	
	public static void setEventsTable(MockHttpServletRequest request,int action)	{
		String val = "";  
		if(action == 0)	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<EVENT_ID></EVENT_ID>" +
					"<REPORT_ID></REPORT_ID>" +
					"<EVENT_VERBATIM>Headeache</EVENT_VERBATIM>" +
					"<DISPLAY_NUMBER>1</DISPLAY_NUMBER>" +
					"<ONSET_DATE/>" +
					"<EVENT_ONGOING_FLAG/>" +
					"<END_DATE/>" +
					"<SERIOUS_FLAG/>" +
					"<HOSPITALIZATION_FLAG/>" +
					"<MEDICALLY_SIGNIFICANT_FLAG/>" +
					"<PATIENT_DIED_FLAG/>" +
					"<LIFE_THREATENING_FLAG/>" +
					"<DISABILITY_FLAG/>" +
					"<CONGENITAL_ANOMALY_FLAG/>" +
					"<HOSPITALIZED_FROM_DATE/>" +
					"<HOSPITALIZED_TO_DATE/>" +
					"<EVENT_OUTCOME/>" +
					"<COMPANY_CAUSALITY/>" +
					"<REPORTER_CAUSALITY/>" +
					"<PRIOR_HISTORY_OF_EVENT/>" +
					"<PRIOR_HISTORY_COMMENT/>" +
					"<RELATED_TO_PROD_COMPLAINT/>" +
					"<RELATED_TO_LOT_NUMBER/>" +
					"<UPDATE_USER_ID>test</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>18-MAR-10 12.34.54 PM</UPDATE_TIMESTAMP>" +
					"<HOSPITALIZATION_NATURE/>" +
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		
		
		}else	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<EVENT_ID>" + (LauReportsSetTransactionMgr.reportId + 5) + "</EVENT_ID>" +
					"<REPORT_ID>" + LauReportsSetTransactionMgr.reportId + "</REPORT_ID>" +
					"<EVENT_VERBATIM>Headeache update</EVENT_VERBATIM>" +
					"<DISPLAY_NUMBER>1</DISPLAY_NUMBER>" +
					"<ONSET_DATE/>" +
					"<EVENT_ONGOING_FLAG/>" +
					"<END_DATE/>" +
					"<SERIOUS_FLAG/>" +
					"<HOSPITALIZATION_FLAG/>" +
					"<MEDICALLY_SIGNIFICANT_FLAG/>" +
					"<PATIENT_DIED_FLAG/>" +
					"<LIFE_THREATENING_FLAG/>" +
					"<DISABILITY_FLAG/>" +
					"<CONGENITAL_ANOMALY_FLAG/>" +
					"<HOSPITALIZED_FROM_DATE/>" +
					"<HOSPITALIZED_TO_DATE/>" +
					"<EVENT_OUTCOME/>" +
					"<COMPANY_CAUSALITY/>" +
					"<REPORTER_CAUSALITY/>" +
					"<PRIOR_HISTORY_OF_EVENT/>" +
					"<PRIOR_HISTORY_COMMENT/>" +
					"<RELATED_TO_PROD_COMPLAINT/>" +
					"<RELATED_TO_LOT_NUMBER/>" +
					"<UPDATE_USER_ID>test</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>18-MAR-10 12.34.54 PM</UPDATE_TIMESTAMP>" +
					"<HOSPITALIZATION_NATURE/>" +
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		}
		 request.setParameter("lauEventsXmlData",val);
		
	}
}
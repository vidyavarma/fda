package com.nrg.lau.junit;

import org.springframework.mock.web.MockHttpServletRequest;

public class LauRequestData2 {
	
	public static void setNarrativeTextTable(MockHttpServletRequest request,int action)	{
		String val = "";  
		if(action == 0)	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<NARRATIVE_TEXT_ID></NARRATIVE_TEXT_ID>" +
					"<REPORT_ID></REPORT_ID>" +
					"<NARRATIVE_TEXT_TYPE>blajhll</NARRATIVE_TEXT_TYPE>" +
					"<TEXT_UPDATED_FLAG/>" +
					"<NARRATIVE_TEXT/>" +
					"<UPDATE_USER_ID>test</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>18-MAR-10 12.34.54 PM</UPDATE_TIMESTAMP>" +
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		
		
		}else	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<NARRATIVE_TEXT_ID>" + (LauReportsSetTransactionMgr.reportId + 6) + "</NARRATIVE_TEXT_ID>" +
					"<REPORT_ID>" + LauReportsSetTransactionMgr.reportId + "</REPORT_ID>" +
					"<NARRATIVE_TEXT_TYPE>blajhll update</NARRATIVE_TEXT_TYPE>" +
					"<TEXT_UPDATED_FLAG/>" +
					"<NARRATIVE_TEXT/>" +
					"<UPDATE_USER_ID>JUnit update</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>18-MAR-10 12.34.54 PM</UPDATE_TIMESTAMP>" +
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		}
		 request.setParameter("lauNarrativeTextXmlData",val);
		
	}
	
	public static void setLabTestsTable(MockHttpServletRequest request,int action)	{
		String val = "";  
		if(action == 0)	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<LAB_TEST_ID></LAB_TEST_ID>" +
					"<REPORT_ID></REPORT_ID>" +
					"<DISPLAY_NBR>1</DISPLAY_NBR>" +
					"<TEST_DATE/>" +
					"<TEST_TIME/>" +
					"<TEST_RESULT/>" +
					"<TEST_UNITS/>" +
					"<TEST_UPPER_BOUND/>" +
					"<TEST_LOWER_BOUND/>" +
					"<TEST_COMMENTS>sd</TEST_COMMENTS>" +
					"<TEST_NAME_VERBATIM/>" +
					"<UPDATE_USER_ID>rahul</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>18-MAR-10 12.34.54 PM</UPDATE_TIMESTAMP>" +
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		
		
		}else	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<LAB_TEST_ID>" + (LauReportsSetTransactionMgr.reportId + 7) + "</LAB_TEST_ID>" +
					"<REPORT_ID>" + LauReportsSetTransactionMgr.reportId + "</REPORT_ID>" +
					"<DISPLAY_NBR>1</DISPLAY_NBR>" +
					"<TEST_DATE/>" +
					"<TEST_TIME/>" +
					"<TEST_RESULT/>" +
					"<TEST_UNITS/>" +
					"<TEST_UPPER_BOUND/>" +
					"<TEST_LOWER_BOUND/>" +
					"<TEST_COMMENTS>sd</TEST_COMMENTS>" +
					"<TEST_NAME_VERBATIM/>" +
					"<UPDATE_USER_ID>JUnit update</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>18-MAR-10 12.34.54 PM</UPDATE_TIMESTAMP>" +
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		}
		 request.setParameter("lauLabTestsXmlData",val);
		
	}
	
	public static void setPregnancyDetailsTable(MockHttpServletRequest request,int action)	{
		String val = "";  
		if(action == 0)	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<PREGNANCY_DETAILS_ID>" +
					"</PREGNANCY_DETAILS_ID>" +
					"<REPORT_ID></REPORT_ID>" +
					"<PREGNANT_FLAG>1</PREGNANT_FLAG>" +
					"<PREG_TEST_PERFORMED>0</PREG_TEST_PERFORMED>" +
					"<PREG_TEST_DETAILS>0</PREG_TEST_DETAILS>" +
					"<PREG_EXPOSURE_STATUS>0</PREG_EXPOSURE_STATUS>" +
					"<ELIGIBLE_FOR_PREG_REGISTRY>0</ELIGIBLE_FOR_PREG_REGISTRY>" +
					"<CONSENT_TO_CONTACT_PT>0</CONSENT_TO_CONTACT_PT>" +
					"<PAST_PREGNANCY_COUNT></PAST_PREGNANCY_COUNT>" +
					"<PAST_PREGNANCY_COMMENTS>0</PAST_PREGNANCY_COMMENTS>" +
					"<PAST_LIVE_BIRTH_COUNT></PAST_LIVE_BIRTH_COUNT>" +
					"<PAST_FULL_TERM_COUNT>0</PAST_FULL_TERM_COUNT>" +
					"<PAST_PREMATURE_COUNT></PAST_PREMATURE_COUNT>" +
					"<PAST_MULTIPLE_BIRTH_COUNT>0</PAST_MULTIPLE_BIRTH_COUNT>" +
					"<PAST_MISCARRIAGE_COUNT>0</PAST_MISCARRIAGE_COUNT>" +
					"<PAST_ECTOPIC_COUNT>0</PAST_ECTOPIC_COUNT>" +
					"<PAST_ABORTION_COUNT>0</PAST_ABORTION_COUNT>" +
					"<FIRST_DAY_LAST_MENSES>0</FIRST_DAY_LAST_MENSES>" +
					"<EXPECTED_DELIVERY_DATE>00</EXPECTED_DELIVERY_DATE>" +
					"<PREG_DURATION_EXPOSURE>0</PREG_DURATION_EXPOSURE>" +
					"<PREG_DURATION_EXPOSURE_UNITS>0</PREG_DURATION_EXPOSURE_UNITS>" +
					"<PREG_OUTCOME>0</PREG_OUTCOME>" +
					"<PREG_OUTCOME_DATE>0</PREG_OUTCOME_DATE>" +
					"<PREG_OUTCOME_COMMENTS>0</PREG_OUTCOME_COMMENTS>" +
					"<PREG_TERM_AT_OUTCOME>0</PREG_TERM_AT_OUTCOME>" +
					"<PREG_TERM_AT_OUTCOME_UNITS>00</PREG_TERM_AT_OUTCOME_UNITS>" +
					"<ELECTIVE_TERM_REASON>0</ELECTIVE_TERM_REASON>" +
					"<MULTIPLE_BIRTH_FLAG>00</MULTIPLE_BIRTH_FLAG>" +
					"<MULTIPLE_BIRTH_DETIALS>0</MULTIPLE_BIRTH_DETIALS>" +
					"<PREG_COMPLICATIONS>0</PREG_COMPLICATIONS>" +
					"<UPDATE_USER_ID>Rahul</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>8-MAR-10 12.34.54 PM</UPDATE_TIMESTAMP>" +
					
					"<PATERNAL_EXPOSURE_FLAG>1</PATERNAL_EXPOSURE_FLAG>" +
					"<PREG_COMPLICATIONS_FLAG>0</PREG_COMPLICATIONS_FLAG>" +
					"<PARTNER_NAME>Nm-UP</PARTNER_NAME>" +
					"<PARTNER_INITIALS>SN</PARTNER_INITIALS>" +
					"<PARTNER_DOB>19700101</PARTNER_DOB>" +
					"<PARTNER_CONTACT_DETAILS>Dtls-UP</PARTNER_CONTACT_DETAILS>" +
					"<GEST_AGE_AT_FIRST_DOSE>0</GEST_AGE_AT_FIRST_DOSE>" +
					"<GEST_AGE_AT_FIRST_DOSE_UNITS>44</GEST_AGE_AT_FIRST_DOSE_UNITS>" +
					"<GEST_AGE_AT_LAST_DOSE>0</GEST_AGE_AT_LAST_DOSE>" +
					"<GEST_AGE_AT_LAST_DOSE_UNITS>66</GEST_AGE_AT_LAST_DOSE_UNITS>" +
					"<GEST_AGE_AT_EVENT>0</GEST_AGE_AT_EVENT>" +
					"<GEST_AGE_AT_EVENT_UNITS>566</GEST_AGE_AT_EVENT_UNITS>" +
					
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		
		
		}else	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<PREGNANCY_DETAILS_ID>" + (LauReportsSetTransactionMgr.reportId + 8) + "</PREGNANCY_DETAILS_ID>" +
					"<REPORT_ID>" + LauReportsSetTransactionMgr.reportId + "</REPORT_ID>" +
					"<PREGNANT_FLAG>1</PREGNANT_FLAG>" +
					"<PREG_TEST_PERFORMED>0</PREG_TEST_PERFORMED>" +
					"<PREG_TEST_DETAILS>0</PREG_TEST_DETAILS>" +
					"<PREG_EXPOSURE_STATUS>0</PREG_EXPOSURE_STATUS>" +
					"<ELIGIBLE_FOR_PREG_REGISTRY>0</ELIGIBLE_FOR_PREG_REGISTRY>" +
					"<CONSENT_TO_CONTACT_PT>0</CONSENT_TO_CONTACT_PT>" +
					"<PAST_PREGNANCY_COUNT>0</PAST_PREGNANCY_COUNT>" +
					"<PAST_PREGNANCY_COMMENTS>0</PAST_PREGNANCY_COMMENTS>" +
					"<PAST_LIVE_BIRTH_COUNT>0</PAST_LIVE_BIRTH_COUNT>" +
					"<PAST_FULL_TERM_COUNT>0</PAST_FULL_TERM_COUNT>" +
					"<PAST_PREMATURE_COUNT>0</PAST_PREMATURE_COUNT>" +
					"<PAST_MULTIPLE_BIRTH_COUNT>0</PAST_MULTIPLE_BIRTH_COUNT>" +
					"<PAST_MISCARRIAGE_COUNT>0</PAST_MISCARRIAGE_COUNT>" +
					"<PAST_ECTOPIC_COUNT>0</PAST_ECTOPIC_COUNT>" +
					"<PAST_ABORTION_COUNT>0</PAST_ABORTION_COUNT>" +
					"<FIRST_DAY_LAST_MENSES>0</FIRST_DAY_LAST_MENSES>" +
					"<EXPECTED_DELIVERY_DATE>00</EXPECTED_DELIVERY_DATE>" +
					"<PREG_DURATION_EXPOSURE>0</PREG_DURATION_EXPOSURE>" +
					"<PREG_DURATION_EXPOSURE_UNITS>0</PREG_DURATION_EXPOSURE_UNITS>" +
					"<PREG_OUTCOME>0</PREG_OUTCOME>" +
					"<PREG_OUTCOME_DATE>0</PREG_OUTCOME_DATE>" +
					"<PREG_OUTCOME_COMMENTS>0</PREG_OUTCOME_COMMENTS>" +
					"<PREG_TERM_AT_OUTCOME>0</PREG_TERM_AT_OUTCOME>" +
					"<PREG_TERM_AT_OUTCOME_UNITS>00</PREG_TERM_AT_OUTCOME_UNITS>" +
					"<ELECTIVE_TERM_REASON>0</ELECTIVE_TERM_REASON>" +
					"<MULTIPLE_BIRTH_FLAG>00</MULTIPLE_BIRTH_FLAG>" +
					"<MULTIPLE_BIRTH_DETIALS>0</MULTIPLE_BIRTH_DETIALS>" +
					"<PREG_COMPLICATIONS>0</PREG_COMPLICATIONS>" +
					"<UPDATE_USER_ID>JUnit update</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>8-MAR-10 12.34.54 PM</UPDATE_TIMESTAMP>" +
					
					"<PATERNAL_EXPOSURE_FLAG>0</PATERNAL_EXPOSURE_FLAG>" +
					"<PREG_COMPLICATIONS_FLAG>1</PREG_COMPLICATIONS_FLAG>" +
					"<PARTNER_NAME>Nm</PARTNER_NAME>" +
					"<PARTNER_INITIALS>S</PARTNER_INITIALS>" +
					"<PARTNER_DOB>19750505</PARTNER_DOB>" +
					"<PARTNER_CONTACT_DETAILS>Dtls</PARTNER_CONTACT_DETAILS>" +
					"<GEST_AGE_AT_FIRST_DOSE>1</GEST_AGE_AT_FIRST_DOSE>" +
					"<GEST_AGE_AT_FIRST_DOSE_UNITS>121</GEST_AGE_AT_FIRST_DOSE_UNITS>" +
					"<GEST_AGE_AT_LAST_DOSE>1</GEST_AGE_AT_LAST_DOSE>" +
					"<GEST_AGE_AT_LAST_DOSE_UNITS>111</GEST_AGE_AT_LAST_DOSE_UNITS>" +
					"<GEST_AGE_AT_EVENT>1</GEST_AGE_AT_EVENT>" +
					"<GEST_AGE_AT_EVENT_UNITS>23</GEST_AGE_AT_EVENT_UNITS>" +
					
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		}
		 request.setParameter("lauPregnancyDetailsXmlData",val);
		
	}
	
	public static void setPregnancyHistoryTable(MockHttpServletRequest request,int action)	{
		String val = "";  
		if(action == 0)	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<PREGNANCY_HISTORY_ID></PREGNANCY_HISTORY_ID>" +
					"<REPORT_ID></REPORT_ID>" +
					"<DISPLAY_NUMBER></DISPLAY_NUMBER>" +
					"<PREGNANCY_HISTORY_TERM>0</PREGNANCY_HISTORY_TERM>" +
					"<PREGNANCY_HISTORY_COMMENT>0</PREGNANCY_HISTORY_COMMENT>" +
					"<UPDATE_USER_ID>Rahul</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>8-MAR-10 12.34.54 PM</UPDATE_TIMESTAMP>" +
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		
		
		}else	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<PREGNANCY_HISTORY_ID>" + (LauReportsSetTransactionMgr.reportId + 9) + "</PREGNANCY_HISTORY_ID>" +
					"<REPORT_ID>" + LauReportsSetTransactionMgr.reportId + "</REPORT_ID>" +
					"<DISPLAY_NUMBER>2</DISPLAY_NUMBER>" +
					"<PREGNANCY_HISTORY_TERM>0</PREGNANCY_HISTORY_TERM>" +
					"<PREGNANCY_HISTORY_COMMENT>0</PREGNANCY_HISTORY_COMMENT>" +
					"<UPDATE_USER_ID>JUnit update</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>8-MAR-10 12.34.54 PM</UPDATE_TIMESTAMP>" +
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		}
		 request.setParameter("lauPregnancyHistoryXmlData",val);
		
	}
	
	public static void setPregnancyOutcomeTable(MockHttpServletRequest request,int action)	{
		String val = "";  
		if(action == 0)	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<PREGNANCY_OUTCOME_ID></PREGNANCY_OUTCOME_ID>" +
					"<REPORT_ID></REPORT_ID>" +
					"<DISPLAY_NUMBER></DISPLAY_NUMBER>" +
					"<SEX>M</SEX>" +
					"<WEIGHT></WEIGHT>" +
					"<LENGTH></LENGTH>" +
					"<HEAD_CIRCUMFERENCE>0</HEAD_CIRCUMFERENCE>" +
					"<APGAR_SCORE_1></APGAR_SCORE_1>" +
					"<APGAR_SCORE_2></APGAR_SCORE_2>" +
					"<ABNORMALITIES_FLAG>0</ABNORMALITIES_FLAG>" +
					"<ABNORMALITIES_COMMENTS>0</ABNORMALITIES_COMMENTS>" +
					"<DELIVERY_DATE>0</DELIVERY_DATE>" +
					"<DELIVERY_TIME>0</DELIVERY_TIME>" +
					"<DELIVERY_MODE>0</DELIVERY_MODE>" +
					"<DELIVERY_DETAILS>0</DELIVERY_DETAILS>" +
					"<PLACENTA_CONDITION>0</PLACENTA_CONDITION>" +
					"<PLACENTA_CONDITION_COMMENTS>0</PLACENTA_CONDITION_COMMENTS>" +
					"<DELIVERY_COMMENTS>0</DELIVERY_COMMENTS>" +
					"<UPDATE_USER_ID>Rahul</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>8-MAR-10 12.34.54 PM</UPDATE_TIMESTAMP>" +
					"<DELIVERY_COMPLICATIONS>DELIVERY_COMPLICATIONS</DELIVERY_COMPLICATIONS>" +
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		
		
		}else	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<PREGNANCY_OUTCOME_ID>" + (LauReportsSetTransactionMgr.reportId + 10) + "</PREGNANCY_OUTCOME_ID>" +
					"<REPORT_ID>" + LauReportsSetTransactionMgr.reportId + "</REPORT_ID>" +
					"<DISPLAY_NUMBER></DISPLAY_NUMBER>" +
					"<SEX>F</SEX>" +
					"<WEIGHT>70</WEIGHT>" +
					"<LENGTH>22</LENGTH>" +
					"<HEAD_CIRCUMFERENCE>0</HEAD_CIRCUMFERENCE>" +
					"<APGAR_SCORE_1>22</APGAR_SCORE_1>" +
					"<APGAR_SCORE_2>3.3</APGAR_SCORE_2>" +
					"<ABNORMALITIES_FLAG>0</ABNORMALITIES_FLAG>" +
					"<ABNORMALITIES_COMMENTS>0</ABNORMALITIES_COMMENTS>" +
					"<DELIVERY_DATE>0</DELIVERY_DATE>" +
					"<DELIVERY_TIME>0</DELIVERY_TIME>" +
					"<DELIVERY_MODE>0</DELIVERY_MODE>" +
					"<DELIVERY_DETAILS>0</DELIVERY_DETAILS>" +
					"<PLACENTA_CONDITION>0</PLACENTA_CONDITION>" +
					"<PLACENTA_CONDITION_COMMENTS>0</PLACENTA_CONDITION_COMMENTS>" +
					"<DELIVERY_COMMENTS>0</DELIVERY_COMMENTS>" +
					"<UPDATE_USER_ID>JUnit update</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>8-MAR-10 12.34.54 PM</UPDATE_TIMESTAMP>" +
					"<DELIVERY_COMPLICATIONS>DELIVERY_COMPLICATIONS</DELIVERY_COMPLICATIONS>" +
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		}
		 request.setParameter("lauPregnancyOutcomeXmlData",val);
		
	}
}

package com.nrg.lau.junit;

import org.springframework.mock.web.MockHttpServletRequest;

public class LauRequestData3 {
	
	public static void setRelatedReportsTable(MockHttpServletRequest request,int action)	{
		String val = "";  
		if(action == 0)	{
			
			val = "<?xml version='1.0'?>" +
				"<ROWSET><ROW>" +
				"<RELATED_REPORT_ID></RELATED_REPORT_ID>" +
				"<REPORT_ID></REPORT_ID>" +
				"<RELATED_REPORT>0</RELATED_REPORT>" +
				"<RELATION_REASON>0</RELATION_REASON>" +
				"<UPDATE_USER_ID>Rahul</UPDATE_USER_ID>" +
				"<UPDATE_TIMESTAMP>8-MAR-10 12.34.54</UPDATE_TIMESTAMP>" +
				"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
				"</ROW></ROWSET>";
		
		
		}else	{
			val = "<?xml version='1.0'?>" +
				"<ROWSET><ROW>" +
				"<RELATED_REPORT_ID>" + (LauReportsSetTransactionMgr.reportId + 11) + "</RELATED_REPORT_ID>" +
				"<REPORT_ID>" + LauReportsSetTransactionMgr.reportId + "</REPORT_ID>" +
				"<RELATED_REPORT></RELATED_REPORT>" +
				"<RELATION_REASON>Is this the reason</RELATION_REASON>" +
				"<UPDATE_USER_ID>JUnit update</UPDATE_USER_ID>" +
				"<UPDATE_TIMESTAMP>8-MAR-10 12.34.54</UPDATE_TIMESTAMP>" +
				"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
				"</ROW> </ROWSET>";
		}
		 request.setParameter("lauRelatedReportsXmlData",val);
		
	}
	
	public static void setReporterDetailsTable(MockHttpServletRequest request,int action)	{
		String val = "";  
		if(action == 0)	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<REPORTER_DETAILS_ID></REPORTER_DETAILS_ID>" +
					"<REPORT_ID></REPORT_ID>" +
					"<REPORTER_SOURCE_TYPE>0</REPORTER_SOURCE_TYPE>" +
					"<REPORTER_TYPE>0</REPORTER_TYPE>" +
					"<REPORTER_IS_PATIENT>0</REPORTER_IS_PATIENT>" +
					"<REPORTER_CONTACT_DATE>0</REPORTER_CONTACT_DATE>" +
					"<REPORTER_CONTACT_METHOD>0</REPORTER_CONTACT_METHOD>" +
					"<REPORTER_CONTACT_REASON>0</REPORTER_CONTACT_REASON>" +
					"<RELATIONSHIP_TO_PATIENT>0</RELATIONSHIP_TO_PATIENT>" +
					"<REPORTER_IS_PRESCRIBER>0</REPORTER_IS_PRESCRIBER>" +
					"<REPORTER_TITLE>0</REPORTER_TITLE>" +
					"<REPORTER_FIRST_NAME>0</REPORTER_FIRST_NAME>" +
					"<REPORTER_MIDDLE_NAME>0</REPORTER_MIDDLE_NAME>" +
					"<REPORTER_LAST_NAME>0</REPORTER_LAST_NAME>" +
					"<REPORTER_ADDRESS1>0</REPORTER_ADDRESS1>" +
					"<REPORTER_ADDRESS2>0</REPORTER_ADDRESS2>" +
					"<REPORTER_ADDRESS3>0</REPORTER_ADDRESS3>" +
					"<REPORTER_CITY>0</REPORTER_CITY>" +
					"<REPORTER_STATE>0</REPORTER_STATE>" +
					"<REPORTER_POSTAL_CODE>0</REPORTER_POSTAL_CODE>" +
					"<REPORTER_COUNTRY>0</REPORTER_COUNTRY>" +
					"<REPORTER_PHONE_NUMBER>0</REPORTER_PHONE_NUMBER>" +
					"<REPORTER_FAX_NUMBER>0</REPORTER_FAX_NUMBER>" +
					"<REPORTER_EMAIL>0</REPORTER_EMAIL>" +
					"<REPORTER_SPECIALTY>0</REPORTER_SPECIALTY>" +
					"<REPORTER_OCCUPATION>0</REPORTER_OCCUPATION>" +
					"<UPDATE_USER_ID>Rahul</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>8-MAR-10 12.34.54</UPDATE_TIMESTAMP>" +
					"<REPORTER_IS_TREATING_MD>Y</REPORTER_IS_TREATING_MD>"+
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		
		
		}else	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<REPORTER_DETAILS_ID>" + (LauReportsSetTransactionMgr.reportId + 12) + "</REPORTER_DETAILS_ID>" +
					"<REPORT_ID>" + LauReportsSetTransactionMgr.reportId + "</REPORT_ID>" +
					"<REPORTER_SOURCE_TYPE>0</REPORTER_SOURCE_TYPE>" +
					"<REPORTER_TYPE>0</REPORTER_TYPE>" +
					"<REPORTER_IS_PATIENT>0</REPORTER_IS_PATIENT>" +
					"<REPORTER_CONTACT_DATE>0</REPORTER_CONTACT_DATE>" +
					"<REPORTER_CONTACT_METHOD>0</REPORTER_CONTACT_METHOD>" +
					"<REPORTER_CONTACT_REASON>0</REPORTER_CONTACT_REASON>" +
					"<RELATIONSHIP_TO_PATIENT>0</RELATIONSHIP_TO_PATIENT>" +
					"<REPORTER_IS_PRESCRIBER>0</REPORTER_IS_PRESCRIBER>" +
					"<REPORTER_TITLE>0</REPORTER_TITLE>" +
					"<REPORTER_FIRST_NAME>0</REPORTER_FIRST_NAME>" +
					"<REPORTER_MIDDLE_NAME>0</REPORTER_MIDDLE_NAME>" +
					"<REPORTER_LAST_NAME>0</REPORTER_LAST_NAME>" +
					"<REPORTER_ADDRESS1>0</REPORTER_ADDRESS1>" +
					"<REPORTER_ADDRESS2>0</REPORTER_ADDRESS2>" +
					"<REPORTER_ADDRESS3>0</REPORTER_ADDRESS3>" +
					"<REPORTER_CITY>0</REPORTER_CITY>" +
					"<REPORTER_STATE>0</REPORTER_STATE>" +
					"<REPORTER_POSTAL_CODE>0</REPORTER_POSTAL_CODE>" +
					"<REPORTER_COUNTRY>0</REPORTER_COUNTRY>" +
					"<REPORTER_PHONE_NUMBER>0</REPORTER_PHONE_NUMBER>" +
					"<REPORTER_FAX_NUMBER>0</REPORTER_FAX_NUMBER>" +
					"<REPORTER_EMAIL>0</REPORTER_EMAIL>" +
					"<REPORTER_SPECIALTY>0</REPORTER_SPECIALTY>" +
					"<REPORTER_OCCUPATION>0</REPORTER_OCCUPATION>" +
					"<UPDATE_USER_ID>JUnit update</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>8-MAR-10 12.34.54</UPDATE_TIMESTAMP>" +
					"<REPORTER_IS_TREATING_MD>Y</REPORTER_IS_TREATING_MD>"+
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		}
		 request.setParameter("lauReporterDetailsXmlData",val);
		
	}
	
	public static void setProductsTable(MockHttpServletRequest request,int action)	{
		String val = "";  
		if(action == 0)	{
			val = "<?xml version='1.0'?><ROWSET><ROW>" +
					"<PRODUCT_ID></PRODUCT_ID>" +
					"<REPORT_ID></REPORT_ID>" +
					"<PRODUCT_NAME>product_1</PRODUCT_NAME>" +
					"<DISPLAY_NUMBER>2</DISPLAY_NUMBER>" +
					"<PRODUCT_TYPE>0</PRODUCT_TYPE>" +
					"<INDICATION_VERBATIM>0</INDICATION_VERBATIM>" +
					"<ACTION_TAKEN>0</ACTION_TAKEN>" +
					"<NDC_NUMBER/><STRENGTH>asas</STRENGTH>" +
					"<SAMPLE_DISPOSITION>sadasd</SAMPLE_DISPOSITION>" +
					"<REIMBURSEMENT_TYPE>as</REIMBURSEMENT_TYPE>" +
					"<REIMBURSEMENT_TO/>" +
					"<REIMBURSEMENT_QUANTITY>100</REIMBURSEMENT_QUANTITY>" +
					"<REIMBURSEMENT_AMOUNT>8989</REIMBURSEMENT_AMOUNT>" +
					"<REIMBURSEMENT_ACCOUNT>mmm</REIMBURSEMENT_ACCOUNT>" +
					"<DEA_NUMBER>s</DEA_NUMBER>" +
					"<UPDATE_USER_ID>Rahul</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>8-MAR-10 12.34.54</UPDATE_TIMESTAMP>" +
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		
		
		}else	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<PRODUCT_ID>" + (LauReportsSetTransactionMgr.reportId + 13) + "</PRODUCT_ID>" +
					"<REPORT_ID>" + LauReportsSetTransactionMgr.reportId + "</REPORT_ID>" +
					"<PRODUCT_NAME>product_1</PRODUCT_NAME>" +
					"<DISPLAY_NUMBER>2</DISPLAY_NUMBER>" +
					"<PRODUCT_TYPE>0</PRODUCT_TYPE>" +
					"<INDICATION_VERBATIM>0</INDICATION_VERBATIM>" +
					"<ACTION_TAKEN>0</ACTION_TAKEN>" +
					"<NDC_NUMBER/><STRENGTH>asas</STRENGTH>" +
					"<SAMPLE_DISPOSITION>sadasd</SAMPLE_DISPOSITION>" +
					"<REIMBURSEMENT_TYPE>as</REIMBURSEMENT_TYPE>" +
					"<REIMBURSEMENT_TO/>" +
					"<REIMBURSEMENT_QUANTITY>100</REIMBURSEMENT_QUANTITY>" +
					"<REIMBURSEMENT_AMOUNT>8989</REIMBURSEMENT_AMOUNT>" +
					"<REIMBURSEMENT_ACCOUNT>mmm</REIMBURSEMENT_ACCOUNT>" +
					"<DEA_NUMBER>s</DEA_NUMBER>" +
					"<UPDATE_USER_ID>JUnit update</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>8-MAR-10 12.34.54</UPDATE_TIMESTAMP>" +
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		}
		 request.setParameter("lauProductsXmlData",val);
		
	}
	
	public static void setDosingTable(MockHttpServletRequest request,int action)	{
		String val = "";  
		if(action == 0)	{
			val = "";
			/*
			 *val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<DOSE_ID></DOSE_ID>" +
					"<PRODUCT_ID></PRODUCT_ID>" +
					"<PRODUCT_NAME>product_1</PRODUCT_NAME>" +
					"<REPORT_ID></REPORT_ID>" +
					"<START_DATE>20100101</START_DATE>" +
					"<STOP_DATE>0</STOP_DATE>" +
					"<DISPLAY_NUMBER>0</DISPLAY_NUMBER>" +
					"<ONGOING_FLAG>0</ONGOING_FLAG>" +
					"<DURATION/>" +
					"<DURATION_UNITS>0</DURATION_UNITS>" +
					"<DOSE>0</DOSE>" +
					"<DOSE_UNIT>0</DOSE_UNIT>" +
					"<ROUTE>0</ROUTE>" +
					"<FREQUENCY>0</FREQUENCY>" +
					"<FORMULATION>0</FORMULATION>" +
					"<LOT_NUMBER>0</LOT_NUMBER>" +
					"<LOT_EXPIRATION_DATE>0</LOT_EXPIRATION_DATE>" +
					"<UPDATE_USER_ID>Rahul</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>8-MAR-10 12.34.54</UPDATE_TIMESTAMP>" +
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
			 */
/**
 * <DISPLAY_NUMBER>1</DISPLAY_NUMBER>
  <DOSE>3</DOSE>
  <DOSE_ID>1</DOSE_ID>
  <DOSE_UNIT/>
  <DURATION/>
  <DURATION_UNITS/>
  <FORMULATION>BATH</FORMULATION>
  <FREQUENCY>HS</FREQUENCY>
  <LOT_EXPIRATION_DATE>20100429</LOT_EXPIRATION_DATE>
  <LOT_NUMBER>2345</LOT_NUMBER>
  <mx_internal_uid>A5590DA6-CF72-36B1-2A48-3C69892A9D0B</mx_internal_uid>
  <ONGOING_FLAG>Y</ONGOING_FLAG>
  <PRODUCT_ID>13</PRODUCT_ID>
  <PRODUCT_NAME>ARFORMOTEROL</PRODUCT_NAME>
  <REPORT_ID>36</REPORT_ID>
  <ROUTE>IHEP</ROUTE>
  <START_DATE>20100419</START_DATE>
  <STOP_DATE/>
  <TRANSACTION_TYPE>1</TRANSACTION_TYPE>
  <UPDATE_TIMESTAMP/>
  <UPDATE_USER_ID>weblogic</UPDATE_USER_ID>
</ROW></ROWSET>
**/		

		
		}else	{
			val = "<?xml version='1.0'?>" +
					"<ROWSET><ROW>" +
					"<DOSE_ID>" + (LauReportsSetTransactionMgr.reportId + 14) + "</DOSE_ID>" +
					"<PRODUCT_ID>" + (LauReportsSetTransactionMgr.reportId + 13) + "</PRODUCT_ID>" +
					"<PRODUCT_NAME>product_1</PRODUCT_NAME>" +
					"<REPORT_ID>" + LauReportsSetTransactionMgr.reportId + "</REPORT_ID>" +
					"<START_DATE>0</START_DATE>" +
					"<STOP_DATE>0</STOP_DATE>" +
					"<DISPLAY_NUMBER>0</DISPLAY_NUMBER>" +
					"<ONGOING_FLAG>0</ONGOING_FLAG>" +
					"<DURATION>0</DURATION>" +
					"<DURATION_UNITS>0</DURATION_UNITS>" +
					"<DOSE>0</DOSE>" +
					"<DOSE_UNIT>0</DOSE_UNIT>" +
					"<ROUTE>0</ROUTE>" +
					"<FREQUENCY>0</FREQUENCY>" +
					"<FORMULATION>0</FORMULATION>" +
					"<LOT_NUMBER>0</LOT_NUMBER>" +
					"<LOT_EXPIRATION_DATE>0</LOT_EXPIRATION_DATE>" +
					"<UPDATE_USER_ID>JUnit update</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP>8-MAR-10 12.34.54</UPDATE_TIMESTAMP>" +
					"<TRANSACTION_TYPE>" + LauReportsSetTransactionMgr.transactionType + "</TRANSACTION_TYPE>" +
					"</ROW></ROWSET>";
		}
		 request.setParameter("lauDosingXmlData",val);
		
	}
}

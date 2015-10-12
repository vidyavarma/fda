package com.nrg.lau.commons;

import java.math.BigDecimal;

public interface IReportsConstants {
	
	public static final BigDecimal ZERO = new BigDecimal ("0");
	public static final long deleteFlag = 0;
	public static final long insertUpdateFlag = 1;
	
	//SEQUENCE

	static final String LAU_SEQ_SQL = "SELECT SEQ_PRIMARY_KEY.nextval FROM dual";
	static final String SEQ_UNIQUE_ID_SQL = "SELECT SEQ_UNIQUE_ID.nextval FROM dual";
	static final String LAU_TIME_STAMP_SQL = "SELECT current_timestamp FROM dual";
	public static final String USER_SESSION = "lauUsers";
	
	static final String LAU_PRODUCT_GROUPING_PARAM_NAME= "lauProductGroupXMLData";	
	//TABLE - LAU_REPORTS
	static final String LAU_REPORTS_PARAM_NAME = "lauReportsXmlData";	
	
	//TABLE - LAU_PATIENT_DETAILS
	static final String LAU_PATIENT_DETAILS_PARAM_NAME = "lauPatientDetailsXmlData";
	
	//TABLE - LAU_PREGNANCY_HISTORY
	static final String LAU_PREGNANCY_HISTORY_PARAM_NAME = "lauPregnancyHistoryXmlData";
	
	//TABLE - LAU_PREGNANCY_OUTCOME
	static final String LAU_PREGNANCY_OUTCOME_PARAM_NAME = "lauPregnancyOutcomeXmlData";
	
	//TABLE - LAU_PREGNANCY_DETAILS
	static final String LAU_PREGNANCY_DETAILS_PARAM_NAME = "lauPregnancyDetailsXmlData";
	
	//TABLE - LAU_RELATED_REPORTS
	static final String LAU_RELATED_REPORTS_PARAM_NAME = "lauRelatedReportsXmlData";
	
	//TABLE - LAU_REPORTER_DETAILS
	static final String LAU_REPORTER_DETAILS_PARAM_NAME = "lauReporterDetailsXmlData";
	
	//TABLE - LAU_CONTACT
	static final String LAU_CONTACT_PARAM_NAME = "lauContactXmlData";
	
	//TABLE - LAU_NARRATIVE_TEXT 
	static final String LAU_NARRATIVE_TEXT_PARAM_NAME = "lauNarrativeTextXmlData";
	
	//TABLE - LAU_PRODUCTS 
	static final String LAU_PRODUCTS_PARAM_NAME = "lauProductsXmlData";
	
	//TABLE - LAU_DOSING 
	static final String LAU_DOSING_PARAM_NAME = "lauDosingXmlData";
	
	//TABLE - LAU_MEDICAL_HISTORY 
	static final String LAU_MEDICAL_HISTORY_PARAM_NAME = "lauMedicalHistoryXmlData";
	
	//TABLE - LAU_LAB_TESTS 
	static final String LAU_LAB_TESTS_PARAM_NAME = "lauLabTestsXmlData";
	
	//TABLE - LAU_ACTION_ITEMS 
	static final String LAU_ACTION_ITEMS_PARAM_NAME = "lauActionItemsXmlData";
	
	//TABLE - LAU_EVENTS 
	static final String LAU_EVENTS_PARAM_NAME = "lauEventsXmlData";
	
	//TABLE - LAU_REPORT_ATTACHMENTS  
	static final String LAU_REPORT_ATTACHMENTS_PARAM_NAME = "lauReportAttachmentsXmlData";
	//E2B eXCHANGE id  
	static final String LAU_REPORT_E2BEXCHANGEID_PARAM_NAME = "E2BExcahngeID";	
	//TABLE - LAU_REPORT_ATTACHMENTS  
	static final String LAU_REPORT_ATTACHMENTS_SIEBEL_PARAM_NAME = "lauReportAttachmentsSiebelXmlData";
	
	static final String LAU_REPORT_ATTACHMENTS_PROPERTIES= "lauReportAttachmentsPropertiesXmlData";
	
	//TABLE - LAU_ACTION_ITEMS 
	static final String LAU_ACTIVITY_LOG_PARAM_NAME = "lauChangeLog";
	
	//TABLE - LAU_PREGNANCY_DETAILS
	static final String LAU_EXTERNAL_REFERENCES_PARAM_NAME = "externalReferenceXmlData";
	
	//TABLE - LAU_DATA_TEMPLATE
	static final String LAU_DATA_TEMPLATE_PARAM_NAME = "lauDataTemplateXmlData";
	
	//TABLE - LAU_LAYOUT_TEMPLATE
	static final String LAU_LAYOUT_TEMPLATE_PARAM_NAME = "lauLayoutTemplateXmlData";
	
	//TABLE - LAU_TRADING_PARTNER
	static final String LAU_TRADING_PARTNER_PARAM_NAME = "lauTradingPartnerXmlData";
	
	//TABLE - LAU_QUESTIONS
	static final String LAU_QUESTIONS_PARAM_NAME = "lauQuestionGroupXMLData";

	//TABLE - LAU_XML_DOCUMENTS
	static final String LAU_XML_DOCUMENTS_PARAM_NAME = "lauDocXmlData";
	
	//TABLE - LAU_XML_NODES
	static final String LAU_XML_NODES_PARAM_NAME = "lauNodeXmlData";
	
	//TABLE - LAU_XML_COLUMNS
	static final String LAU_XML_COLUMNS_PARAM_NAME = "lauColumnXmlData";	
	
	static final String LAU_PROD_GROUP_ASSIGNMENTS_PARAM_NAME = "lauProdGroupAssignmentXmlData";
	
	static final String  LAU_USER_GROUP_PARAM_NAME = "lauUserGroupXmlData";
	static final String  LAU_USERS_PARAM_NAME = "lauUsersXmlData";
	static final String LAU_ROLES_PARAM_NAME = "lauRolesXmlData";
	static final String LAU_REFERENCE_LISTS_PARAM_NAME = "lauReferenceListXmlData";
	static final String LAU_REFERENCE_LIST_DETAILS_PARAM_NAME = "lauReferenceListDetailXmlData";
	
	static final String LAU_REFERENCE_LIST_DISPLAY_NO = "lauReferenceListDisplayNoXmlData";
	static final String  LAU_MULTIPLE_SUMMARY_UPDATE_PARAM_NAME = "lauSummaryMultiXmlData";
	//TABLE - LAU_REPORT_ACTIVITIES  
	static final String LAU_REPORT_ACTIVITIES_PARAM_NAME = "lauActivitiesXmlData"; 
	static final String LAU_REPORT_ACTIVITIES_ATTCHMENT_PARAM_NAME = "lauActivityAttachmentXmlData"; 
	static final String LAU_REPORT_ACTIVITIES= "lauNewActivityXmlData";
	
	//TABLE - LAU_GROUP_INBOX 
	static final String LAU_GROUP_INBOX_PARAM_NAME = "lauGroupInboxXmlData";
	
	//TABLE - LAU_GENERATED_LETTERS  
	static final String LAU_GENERATED_LETTER_PARAM_NAME = "generatedLetterXmlData";
	
	//TABLE - LAU_INCLUDED_QUESTIONS  
	static final String LAU_INCLUDED_QUESTIONS_PARAM_NAME = "includedQuestionsXmlData";
	
	//TABLE - LAU_INCLUDED_REPORTS  
	static final String LAU_INCLUDED_REPORTS_PARAM_NAME = "includedReportsXmlData";
	
	//TABLE - LAU_DEVICE_DETAILS 
	static final String LAU_DEVICE_DETAILS_PARAM_NAME = "lauDeviceDetailsXmlData";
	
	//TABLE - LAU_DEVICE_DETAILS 
	static final String LAU_DEVICE_PROBLEM_CODES_PARAM_NAME = "lauDeviceProblemCodesXmlData";
	
	//TABLE - LAU_PRODUCT_ADDRESSES 
	static final String LAU_PRODUCT_ADDRESSES_PARAM_NAME = "lauProductAddressesXmlData";
	
	//TABLE - LAU_DEVICE_REPORT_DETAILS 
	static final String LAU_DEVICE_REPORT_DETAILS_PARAM_NAME = "lauDeviceReportDetailsXmlData";
	//TABLE -  LAU_PRODUCT_COMPONENTS 
	static final String LAU_PRODUCT_COMPONENT_PARAM_NAME = "lauProductCompDetailsXmlData";
	//TABLE -  LAU_EVALUATION_CODING
	static final String LAU_REPORT_EVAL_PARAM_NAME = "lauReportEvalXmlData";
	//TABLE -  LAU_REPORT_SOURCES
	static final String LAU_REPORT_SOURCES_PARAM_NAME = "lauReportSourcesXmlData";
	//TABLE -  LAU_PATIENT_PROBLEM_CODES
	static final String LAU_PATIENT_PROBLEM_PARAM_NAME = "lauPatientProblemXmlData";
	//TABLE -  LAU_CON_PREV_MEDICATION - Will have details for LAU_PRODUCTS & LAU_DOSING
	static final String LAU_CONMED_PREV_MEDICATION_PARAM_NAME = "lauConmedPrevMedXML";
	//LAU_REPORT_VIEW
	static final String LAU_REPORT_VIEW_PARAM_NAME = "lauReportRevewXML";
	//ADHOC_QUERY
	static final String LAU_ADHOC_QUERY_PARAM_NAME = "lauAdhocQueryXML";
	//TABLE - LAU_QUERIES 
	static final String LAU_QUERY_SAVE_PARAM_NAME = "lauQuerySaveXmlData";
	//TABLE - LAU_QUERIES 
	static final String LAU_FIELD_ATTR_PARAM_NAME = "lauFieldAttrTemplateXmlData";
	//TABLE - LAU_QUERIES 
	static final String LAU_QUERY_JOIN_TABLE_PARAM_NAME = "lauQueryJoinTableTemplateXmlData";
	static final String LAU_LINKED_REPORT_PARAM_NAME = "lauLinkedReportsXML";
	static final String LAU_REVIEWER_NOTE_PARAM_NAME = "lauReviewerNoteXML";
	//TABLE - LAU_NARRATIVE_TEXT 
	static final String LAU_NARRATIVE_LANG_PARAM_NAME = "lauNarrativeLanguageTextXmlData";
	//TABLE - LAU_REMEDIAL_ACTION 
	static final String LAU_REMEDIAL_DETAILS_PARAM_NAME = "lauRemedialActionDetailsXmlData";
}

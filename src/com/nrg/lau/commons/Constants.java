package com.nrg.lau.commons;

public interface Constants {

	//0 - weblogic ServletAuthentication.login
	//1 - LDAP Authentication
	//2 - Return true, this is for DEBUG
	// 04/10/13 S.abrahan authentication option moved to config parameters
	//static final int AUTHENTICATION_OPTION		= 2;

	static final boolean DEBUG 					= false;//true-LOCAL, false- WITH DB CONNECTION
	static final String CONTENT_TYPE 			= "text/xml; charset=UTF-8";
	static final String CONTENT_TYPE_HTML		= "text/html; charset=UTF-8";
	static final String CACHE_CONTROL 			= "max-age=5, must-revalidate";
	//static final String CACHE_CONTROL 		= "no-cache,no-store,must-revalidate";
	static final String SPRING_USER_NAME 		= "j_username";
	static final String SPRING_USER_PASSWORD 	= "j_password";
	static final String LAU_PROPERTIES_FILE 	= "lau";
	//max-age=3600, must-revalidate
	// FLEX XML

	static final String FLEX_XML_HEADER			= "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
	static final String FLEX_XML_ROWSET			= "<ROWSET>";
	static final String FLEX_XML_DATAROOT		= "<DATAROOT>";
	static final String FLEX_XML_END_DATAROOT	= "</DATAROOT>";
	static final String FLEX_XML_START_ROW		= "<ROW>";
	static final String FLEX_XML_END_ROW		= "</ROW>";
	static final String FLEX_XML_END_ROWSET		= "</ROWSET>";
	static final String FLEX_XML_JSESSION		= "<JSESSION_ID>";
	static final String FLEX_XML_END_JSESSION	= "</JSESSION_ID>";

	// SIEBEL
	static final String SIEBEL_FEFLIST_NAME			= "EXTERNAL_SYSTEM_CONNECTION";
	static final String SIEBEL_FEFLIST_URL			= "CONNECT STRING";
	static final String SIEBEL_FEFLIST_USER			= "USER NAME";
	static final String SIEBEL_FEFLIST_PASSWORD		= "PASSWORD";
	static final String SIEBEL_FEFLIST_FOLDERLOC	= "FOLDER LOCATION";

	static final String SIEBEL_FILE_PATH		= "http://watvmcrmtweb1/sba80_temp/";
	static final String SIEBEL_REQ_PARAMS		= "contactId";
	static final String SIEBEL_XML_START		= "<FILE_NAME>";
	static final String SIEBEL_XML_END			= "</FILE_NAME>";
	static final String SIEBEL_CONNECT_URL		= "siebel://watvmcrmtss1:2321/CRM8_TST/SCCObjMgr_enu";
	static final String SIEBEL_USER				= "PRIMOADM";
	static final String SIEBEL_CONTACT_OBJ		= "Contact";
	static final String SIEBEL_CONTACT_COMP		= "Data Entry Attachments BIIB";
	static final String SIEBEL_CONTACT_ID		= "Contact Id";
	static final String SIEBEL_CONTACT_FL_NM	= "ContactFileName";
	static final String SIEBEL_CONTACT_DESC		= "Created(DESCENDING)";
	static final String SIEBEL_FILE_ENCODING	= "utf8";


	static final String SIEBEL_REQ_USER_ID		= "userid";
	static final String SIEBEL_REQ_ACTIVITY_ID	= "activityid";
	static final String SIEBEL_REQ_CUST_NO		= "custnum";
	static final String SIEBEL_REQ_CON_ID		= "conid";

	//EXTERNAL PROCESS VARIABLES
	static final Boolean EXTERNAL_PROCESS_IS_ACTIVE				= false;
	static final String  EXTERNAL_PROCESS_USER_NAME				= "PRIMOAdministrator";
	static final String  EXTERNAL_PROCESS_PASSWORD				= "primo123";
	static final String  EXTERNAL_PROCESS_DEFAULT_PASSWORD		= "primo123";
	static final String  EXTERNAL_PROCESS_DEFAULT_EMAIL			= "donotreply@test.com";
	static final String  EXTERNAL_PROCESS_ADD_WEB_SERVICE_URL	= "http://nov-bpm1:8080/suite/webservice/processmodel/createActivityTask";
	static final String  EXTERNAL_PROCESS_UPDATE_WEB_SERVICE_URL= "http://nov-bpm1:8080/suite/webservice/processmodel/updateActivityTask";
	static final String  EXTERNAL_PROCESS_INTERNAL_WEBSERVICE_URL="http://nov-bpm1:8180/primo/SetAppianValues?WSDL";
	// ACTIVITY LOG TYPES
	static final String SIEBEL_ACTIVITY_TYPE	= "NEW AXIS REPORT";
	static final String  SIEBEL_STATUS_INFORM 	= "GI";
	static final String  SIEBEL_STATUS_TYGRIS 	= "CNS";

}
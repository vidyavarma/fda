package com.nrg.lau.dao;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;

import com.nrg.lau.beans.LauReports;
import com.nrg.lau.commons.IReportParentSetDAO;
import com.nrg.lau.commons.IReportsConstants;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import javax.sql.DataSource;

public class LauReportsSetDAO implements IReportParentSetDAO<LauReports> {

	private String status = "REPORT_STATUS";
	private int num = 1;
	private String CODE = "";
	private LauReports reports	= null;
	private static Logger log	= Logger.getLogger(LauReportsSetDAO.class);
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	private String groupId	= "";

	//deleteReportIdForReports will be populated with the report id
	//if TRANSACTION_TYPE is set to one in Request XML.
	public static long deleteReportIdForReports = 0;
	private String lauReportId = "";
	
	
	public String[] save(HttpServletRequest request,SimpleJdbcTemplate template, DataSource ds,String user,java.sql.Timestamp dstamp) 
	throws Exception {
		userId = user;
		groupId = CommonDAO.getUSERGROUPID(request);
		dt = dstamp;
		long reportId = 0;
		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_REPORTS_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_REPORTS_PARAM_NAME).length() > 0) {
			
			log.info("LauReportsDAO save() LAU_REPORTS_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_REPORTS_PARAM_NAME));
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_REPORTS_PARAM_NAME));
			//Report Id is 0 it's UPDATE else a new INSERT
			if(this.reports.getReportId() == 0) {
				reportId = insert(template, ds);
			} else if(this.reports.getReportId() != 0){
				if(this.reports.getTransactionType() == IReportsConstants.deleteFlag)	{
					deleteReportIdForReports = this.reports.getReportId();
				} else	{
					reportId = update(template);
				}
			}
			
		}	else	{
			log.info(IReportsConstants.LAU_REPORTS_PARAM_NAME + " not found in request");
		}
		//Returning Report Id & Lau Report id as String[]
		String[] str = new String[2];
		str[0] = String.valueOf(reportId); //[0] contains report id
		str[1] = lauReportId;//[1] contains lau report id
		return str;
	}
	
	public long save(HttpServletRequest request,SimpleJdbcTemplate template, DataSource ds) throws Exception {
		return 0;
	}
	
	
	public long insert(SimpleJdbcTemplate template,DataSource ds) throws Exception{
		/***Added ReportStatus changes from primo2.1 on 18/04/2014  - V.Varma ***/
		Connection con					= null;
		ResultSet rs 					= null;
		PreparedStatement pstmt			= null;
		log.info("INSERT FUNCTION----------------------");	
		String SQL_REPORT_STATUS = "SELECT CODE FROM LAU_REFERENCE_LIST_DETAILS " +
				"WHERE REFERENCE_LIST_NAME = ? AND ROWNUM = ? AND DISPLAY_NUMBER = " +
				"(SELECT MIN (DISPLAY_NUMBER) FROM LAU_REFERENCE_LIST_DETAILS)";
		/**************Update ends here*******************/		
		long reportId 	= CommonDAO.getPrimaryKey(template);		
		String lauRepId = CommonDAO.getLauRepId(ds,userId,groupId);
		this.lauReportId	=	lauRepId;
		this.reports.setLauReportId(lauRepId);
		this.reports.setReportId(reportId);
		log.info("Generated Primary Key for REPORT_ID ->" + reportId);
		
		/***Added ReportStatus changes from primo2.1 on 18/04/2014  - V.Varma ***/
		con = ds.getConnection();
		log.info("datasource.getConnection()"); 
		
		pstmt = con.prepareStatement(SQL_REPORT_STATUS);
		pstmt.setString(1,status);
        pstmt.setInt(2, num);
        System.out.println("sql query " + pstmt.toString());
        rs = pstmt.executeQuery();
        if (rs.next())
        {
        	CODE = rs.getString(1);
        }
        log.info("Code value retrieved: "+CODE);
        this.reports.setReportStatus(CODE);
        log.info("REPORT STATUS: "+this.reports.getReportStatus());
        con.close();
		/***********update ends here**********************/
		//Insert the details to LAU_REPORTS
		int id = template.update(SQL_INSERT_STRING,getInsertParameters());
		log.info("LauReportsDAO insert() ID -> " + id);
		//This will return the last inserted REPORT_ID
		return reportId;
	}
	
	
	public long update(SimpleJdbcTemplate template) throws Exception{
		
		//Update using Spring SimpleJdbcTemplate template by passing
		//parameters retrieved from Request XML.
		
		int id = template.update(SQL_UPDATE_STRING,getUpdateParameters());
		log.info("LauReportsDAO update() ID -> " + id);
		//02/22/2012 sa updated TO check back in if REPORT IS no-actioned
		if (this.reports.getReportStatus().equalsIgnoreCase("NO ACTION"))
		{
			log.info("LauReportsDAO Update no action status -> ");
			String SQL_NOACTION_UPDATE_STRING ="UPDATE LAU_REPORTS SET REPORT_RESERVED_DATE=NULL,REPORT_RESERVED_BY=NULL  WHERE  REPORT_ID= ?";
			id = template.update(SQL_NOACTION_UPDATE_STRING,new Object[] { this.reports.getReportId() });
			log.info("LauReportsDAO update no actioned() ID -> " + id);
		}
		return this.reports.getReportId();
	}
	
	private Object[] getInsertParameters() {
		
		LauReports lauReportBean = this.reports;
		log.info(lauReportBean.toString());
		log.info("getReportStatus(): "+lauReportBean.getReportStatus());		
		return new Object[]{
			lauReportBean.getExternalSourceSystemId(),
			lauReportBean.getReportStatus(),
			lauReportBean.getAdverseEventFlag(),
			lauReportBean.getProductComplaintFlag(),
			lauReportBean.getPregnancyReportFlag(),
			lauReportBean.getReportInitialOrFollowup(),
			lauReportBean.getReportOriginatorType(),
			lauReportBean.getReportContactDate(),
			lauReportBean.getReportProcessedDate(),
			lauReportBean.getReportReviewStatus(),
			lauReportBean.getReportAlert(),
			lauReportBean.getReportAlertComment(),
			lauReportBean.getCountryEventOccurred(),
			dt,
			userId,
			dt,
			userId,
			lauReportBean.getReportClosedDate(),
			lauReportBean.getRouteToUserGroup(),
			lauReportBean.getRouteToUser(),
			lauReportBean.getDemotionCandidate(),
			lauReportBean.getDemotionReason(),
			lauReportBean.getDemotionReasonDetails(),
			lauReportBean.getPromotedReportOwner(),
			lauReportBean.getPromotedAeCaseId(),
			lauReportBean.getPromotedPcCaseId(),
			lauReportBean.getOtherCaseId(),
			lauReportBean.getOtherCaseIdType(),
			lauReportBean.getFollowUpToCaseId(),
			lauReportBean.getReportComments(),
			lauReportBean.getPendingDataQuery(),
			userId,
			dt,
			lauReportBean.getLauReportId(),
			lauReportBean.getReportId(),
			groupId,//lauReportBean.getReportCreateUserGroup()
			lauReportBean.getDEVICE_PRODUCT_COMPLAINT_FLAG(),
			lauReportBean.getRUMOR_FLAG(),
			lauReportBean.getREPORT_SOURCE_TYPE(),
			//"<ROW></ROW>",
			lauReportBean.getSUBMITTED_REPORT_TYPE(),
			lauReportBean.getDATE_OF_EVENT(),
			lauReportBean.getREPORT_RECEIVED_DATE(),
			lauReportBean.getFOLLOW_UP_TYPE(),
			lauReportBean.getSUBMITTED_FOLLOW_UP_NUMBER()
		};
	}
private Object[] getUpdateParameters() {
		
		LauReports lauReportBean = this.reports;
		log.info(lauReportBean.toString());
				
		return new Object[]{
			lauReportBean.getExternalSourceSystemId(),
			lauReportBean.getReportStatus(),
			lauReportBean.getAdverseEventFlag(),
			lauReportBean.getProductComplaintFlag(),
			lauReportBean.getPregnancyReportFlag(),
			lauReportBean.getReportInitialOrFollowup(),
			lauReportBean.getReportOriginatorType(),
			lauReportBean.getReportContactDate(),
			lauReportBean.getReportProcessedDate(),
			lauReportBean.getReportReviewStatus(),
			lauReportBean.getReportAlert(),
			lauReportBean.getReportAlertComment(),
			lauReportBean.getCountryEventOccurred(),
			lauReportBean.getReportClosedDate(),
			lauReportBean.getRouteToUserGroup(),
			lauReportBean.getRouteToUser(),
			lauReportBean.getDemotionCandidate(),
			lauReportBean.getDemotionReason(),
			lauReportBean.getDemotionReasonDetails(),
			lauReportBean.getPromotedReportOwner(),
			lauReportBean.getPromotedPcCaseId(),
			lauReportBean.getOtherCaseId(),
			lauReportBean.getOtherCaseIdType(),
			lauReportBean.getFollowUpToCaseId(),
			lauReportBean.getReportComments(),
			lauReportBean.getPendingDataQuery(),
			userId,dt,
			lauReportBean.getDEVICE_PRODUCT_COMPLAINT_FLAG(),
			lauReportBean.getRUMOR_FLAG(),
			lauReportBean.getREPORT_SOURCE_TYPE(),
			lauReportBean.getSUBMITTED_REPORT_TYPE(),
			lauReportBean.getDATE_OF_EVENT(),
			lauReportBean.getREPORT_RECEIVED_DATE(),
			lauReportBean.getFOLLOW_UP_TYPE(),
			lauReportBean.getSUBMITTED_FOLLOW_UP_NUMBER(),
			lauReportBean.getLauReportId(),
			lauReportBean.getReportId()
		};
	}
	
	
	public void createBeansFromXml(String xml) throws Exception {
		
		//reports				= new Vector<LauReports>();
		String mainXmlTag = "ROWSET/ROW";
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauReports.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/LAU_REPORT_ID", "lauReportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/EXTERNAL_SOURCE_SYSTEM_ID", "externalSourceSystemId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_STATUS", "reportStatus" );
		digester.addBeanPropertySetter( mainXmlTag+"/ADVERSE_EVENT_FLAG", "adverseEventFlag" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_COMPLAINT_FLAG", "productComplaintFlag" );
		digester.addBeanPropertySetter( mainXmlTag+"/PREGNANCY_REPORT_FLAG", "pregnancyReportFlag" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_INITIAL_OR_FOLLOWUP", "reportInitialOrFollowup" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ORIGINATOR_TYPE", "reportOriginatorType" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_CONTACT_DATE", "reportContactDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_PROCESSED_DATE", "reportProcessedDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_REVIEW_STATUS", "reportReviewStatus" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ALERT", "reportAlert" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ALERT_COMMENT", "reportAlertComment" );
		digester.addBeanPropertySetter( mainXmlTag+"/COUNTRY_EVENT_OCCURRED", "countryEventOccurred" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_CREATE_DATE", "reportCreateDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_CREATE_USER_ID", "reportCreateUserId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_CREATE_USER_GROUP", "reportCreateUserGroup" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_RESERVED_DATE", "reportReservedDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_RESERVED_BY", "reportReservedBy" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_CLOSED_DATE", "reportClosedDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/ROUTE_TO_USER_GROUP", "routeToUserGroup" );
		digester.addBeanPropertySetter( mainXmlTag+"/ROUTE_TO_USER", "routeToUser" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEMOTION_CANDIDATE", "demotionCandidate" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEMOTION_REASON", "demotionReason" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEMOTION_REASON_DETAILS", "demotionReasonDetails" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROMOTED_REPORT_OWNER", "promotedReportOwner" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROMOTED_AE_CASE_ID", "promotedAeCaseId" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROMOTED_PC_CASE_ID", "promotedPcCaseId" );
		digester.addBeanPropertySetter( mainXmlTag+"/OTHER_CASE_ID", "otherCaseId" );
		digester.addBeanPropertySetter( mainXmlTag+"/OTHER_CASE_ID_TYPE", "otherCaseIdType" );
		digester.addBeanPropertySetter( mainXmlTag+"/FOLLOW_UP_TO_CASE_ID", "followUpToCaseId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_COMMENTS", "reportComments" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "updateUserId" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		digester.addBeanPropertySetter( mainXmlTag+"/PENDING_DATA_QUERY", "pendingDataQuery" );		
		digester.addBeanPropertySetter( mainXmlTag+"/DEVICE_PRODUCT_COMPLAINT_FLAG", "DEVICE_PRODUCT_COMPLAINT_FLAG" );
		digester.addBeanPropertySetter( mainXmlTag+"/RUMOR_FLAG", "RUMOR_FLAG" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_SOURCE_TYPE", "REPORT_SOURCE_TYPE" );
		digester.addBeanPropertySetter( mainXmlTag+"/SUBMITTED_REPORT_TYPE", "SUBMITTED_REPORT_TYPE" );
		digester.addBeanPropertySetter( mainXmlTag+"/DATE_OF_EVENT", "DATE_OF_EVENT" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_RECEIVED_DATE", "REPORT_RECEIVED_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/FOLLOW_UP_TYPE", "FOLLOW_UP_TYPE" );
		digester.addBeanPropertySetter( mainXmlTag+"/SUBMITTED_FOLLOW_UP_NUMBER", "SUBMITTED_FOLLOW_UP_NUMBER" );
		//digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "updateTimeStamp" );
				
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );
		
		digester.parse(new StringReader(xml));
	}
	
	public void addXmlData(LauReports lauReports) {
		this.reports = lauReports;
	}
	
	//SQL Statements
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_REPORTS (EXTERNAL_SOURCE_SYSTEM_ID,REPORT_STATUS," +
			"ADVERSE_EVENT_FLAG,PRODUCT_COMPLAINT_FLAG,PREGNANCY_REPORT_FLAG,REPORT_INITIAL_OR_FOLLOWUP," +
			"REPORT_ORIGINATOR_TYPE,REPORT_CONTACT_DATE,REPORT_PROCESSED_DATE,REPORT_REVIEW_STATUS,REPORT_ALERT," +
			"REPORT_ALERT_COMMENT,COUNTRY_EVENT_OCCURRED,REPORT_CREATE_DATE,REPORT_CREATE_USER_ID," +
			"REPORT_RESERVED_DATE,REPORT_RESERVED_BY,REPORT_CLOSED_DATE,ROUTE_TO_USER_GROUP,ROUTE_TO_USER,DEMOTION_CANDIDATE," +
			"DEMOTION_REASON,DEMOTION_REASON_DETAILS,PROMOTED_REPORT_OWNER,PROMOTED_AE_CASE_ID,PROMOTED_PC_CASE_ID,OTHER_CASE_ID," +
			"OTHER_CASE_ID_TYPE,FOLLOW_UP_TO_CASE_ID,REPORT_COMMENTS,PENDING_DATA_QUERY,UPDATE_USER_ID,UPDATE_TIMESTAMP," +
			"LAU_REPORT_ID,REPORT_ID,REPORT_CREATE_USER_GROUP,DEVICE_PRODUCT_COMPLAINT_FLAG,RUMOR_FLAG," +
			"REPORT_SOURCE_TYPE,SUBMITTED_REPORT_TYPE,DATE_OF_EVENT,REPORT_RECEIVED_DATE,FOLLOW_UP_TYPE,SUBMITTED_FOLLOW_UP_NUMBER) VALUES" +
			"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
			"?,?,?,?,?) ";
	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_REPORTS SET EXTERNAL_SOURCE_SYSTEM_ID=?,REPORT_STATUS=?," +
		"ADVERSE_EVENT_FLAG=?,PRODUCT_COMPLAINT_FLAG=?,PREGNANCY_REPORT_FLAG=?,REPORT_INITIAL_OR_FOLLOWUP=?," +
		"REPORT_ORIGINATOR_TYPE=?,REPORT_CONTACT_DATE=?,REPORT_PROCESSED_DATE=?,REPORT_REVIEW_STATUS=?,REPORT_ALERT=?," +
		"REPORT_ALERT_COMMENT=?,COUNTRY_EVENT_OCCURRED=?," +
		"REPORT_CLOSED_DATE=?,ROUTE_TO_USER_GROUP=?,ROUTE_TO_USER=?,DEMOTION_CANDIDATE=?," +
		"DEMOTION_REASON=?,DEMOTION_REASON_DETAILS=?,PROMOTED_REPORT_OWNER=?,PROMOTED_PC_CASE_ID=?,OTHER_CASE_ID=?," +
		"OTHER_CASE_ID_TYPE=?,FOLLOW_UP_TO_CASE_ID=?,REPORT_COMMENTS=?,PENDING_DATA_QUERY=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?," +
		"DEVICE_PRODUCT_COMPLAINT_FLAG=?,RUMOR_FLAG=?,REPORT_SOURCE_TYPE=?,SUBMITTED_REPORT_TYPE=?,DATE_OF_EVENT=?,REPORT_RECEIVED_DATE=?,FOLLOW_UP_TYPE=?,SUBMITTED_FOLLOW_UP_NUMBER=?" +
		" WHERE LAU_REPORT_ID=? and REPORT_ID= ?";


	/**		
		LAU_REPORTS
		

	"REPORT_ID"                  NUMBER NOT NULL ENABLE,
	"LAU_REPORT_ID"              VARCHAR2(500 CHAR),
	"REPORT_STATUS"              VARCHAR2(50 BYTE),
	"ADVERSE_EVENT_FLAG"         VARCHAR2(5 BYTE),
	"PRODUCT_COMPLAINT_FLAG"     VARCHAR2(5 BYTE),
	"PREGNANCY_REPORT_FLAG"      VARCHAR2(5 BYTE),
	"REPORT_INITIAL_OR_FOLLOWUP" VARCHAR2(50 BYTE),
	"REPORT_ORIGINATOR_TYPE"     VARCHAR2(50 BYTE),
	"REPORT_CONTACT_DATE"        VARCHAR2(8 CHAR),
	"REPORT_PROCESSED_DATE"      VARCHAR2(8 CHAR),
	"REPORT_REVIEW_STATUS"       VARCHAR2(50 BYTE),
	"REPORT_ALERT"               VARCHAR2(50 BYTE),
	"REPORT_ALERT_COMMENT"       VARCHAR2(4000 BYTE),
	"PENDING_DATA_QUERY"         VARCHAR2(5 BYTE),
	"COUNTRY_EVENT_OCCURRED"     VARCHAR2(50 BYTE),
	"REPORT_CREATE_DATE" TIMESTAMP (0)	WITH LOCAL TIME ZONE,
	"REPORT_CREATE_USER_ID"    VARCHAR2(300 BYTE),
	"REPORT_CREATE_USER_GROUP" VARCHAR2(300 BYTE),
	"REPORT_RESERVED_DATE" TIMESTAMP (0)WITH LOCAL TIME ZONE,
	"REPORT_RESERVED_BY"        VARCHAR2(100 BYTE),
	"REPORT_CLOSED_DATE"        VARCHAR2(8 CHAR),
	"ROUTE_TO_USER_GROUP"       VARCHAR2(300 BYTE),
	"ROUTE_TO_USER"             VARCHAR2(300 BYTE),
	"DEMOTION_CANDIDATE"        VARCHAR2(5 BYTE),
	"DEMOTION_REASON"           VARCHAR2(50 BYTE),
	"DEMOTION_REASON_DETAILS"   VARCHAR2(4000 BYTE),
	"PROMOTED_REPORT_OWNER"     VARCHAR2(300 BYTE),
	"PROMOTED_AE_CASE_ID"       VARCHAR2(500 CHAR),
	"PROMOTED_PC_CASE_ID"       VARCHAR2(500 CHAR),
	"OTHER_CASE_ID"             VARCHAR2(500 CHAR),
	"OTHER_CASE_ID_TYPE"        VARCHAR2(100 BYTE),
	"FOLLOW_UP_TO_CASE_ID"      VARCHAR2(500 CHAR),
	"EXTERNAL_SOURCE_SYSTEM_ID" VARCHAR2(500 CHAR),
	"EXT_SRC_SYS_CONTACT_ID"    VARCHAR2(100 BYTE),
	"EXT_SRC_SYST_CUST_NUMBER"  VARCHAR2(100 BYTE),
	"REPORT_COMMENTS" CLOB,
	"UPDATE_USER_ID" VARCHAR2(300 BYTE) NOT NULL ENABLE,
	"UPDATE_TIMESTAMP" TIMESTAMP (0)	
	DEVICE_PRODUCT_COMPLAINT_FLAG          VARCHAR2(5) 
	RUMOR_FLAG                             VARCHAR2(5)   
	REPORT_SOURCE_TYPE                     VARCHAR2(50)
	
	**/	
}
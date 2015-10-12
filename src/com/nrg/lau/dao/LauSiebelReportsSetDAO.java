package com.nrg.lau.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.commons.Constants;

public class LauSiebelReportsSetDAO {
	
	private static Logger log	= Logger.getLogger(LauSiebelReportsSetDAO.class);
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	private String groupId	= "";

	public long save(HttpServletRequest request, SimpleJdbcTemplate template,
			DataSource ds,String user,java.sql.Timestamp dstamp) throws Exception {
		userId = user;
		groupId = CommonDAO.getUSERGROUPID(request);

		dt = dstamp;
		long reportId = 0;
		if(request.getParameter(Constants.SIEBEL_REQ_USER_ID) != null && request.getParameter(Constants.SIEBEL_REQ_ACTIVITY_ID) != null &&
				request.getParameter(Constants.SIEBEL_REQ_CUST_NO) != null && request.getParameter(Constants.SIEBEL_REQ_CON_ID) != null)	{
			reportId = insert(template, request.getParameter(Constants.SIEBEL_REQ_ACTIVITY_ID),
					request.getParameter(Constants.SIEBEL_REQ_CUST_NO),request.getParameter(Constants.SIEBEL_REQ_CON_ID), ds);
		}else {
			log.info("LauSiebelReportsSetDAO -> Siebel request parameters not available!");
		}
		return reportId;
	}
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_REPORTS (LAU_REPORT_ID,REPORT_ID,UPDATE_USER_ID,REPORT_CREATE_USER_ID," +
	"REPORT_CREATE_DATE,REPORT_RESERVED_DATE,REPORT_RESERVED_BY,EXTERNAL_SOURCE_SYSTEM_ID," +
			"EXT_SRC_SYST_CUST_NUMBER,EXT_SRC_SYS_CONTACT_ID,REPORT_STATUS,UPDATE_TIMESTAMP,REPORT_CONTACT_DATE,REPORT_CREATE_USER_GROUP) " +
			"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,to_char(sysdate,'yyyymmdd')," +
			"(SELECT USER_GROUP_ID FROM LAU_USERS WHERE upper(user_id ) = upper(?) ))";
	
	public long insert(SimpleJdbcTemplate template, String activityId, 
			String customerNo, String contactId, DataSource ds) throws Exception {
		
		long reportId 	= CommonDAO.getPrimaryKey(template);		
		SimpleDateFormat prmoFormat = new SimpleDateFormat("yyyymmdd");
		String lauRepId = CommonDAO.getLauRepId(ds,userId,groupId);
		log.info("Generated Primary Key for REPORT_ID ->" + reportId);
		log.info("Siebel request parameters -> " + " [reportId,lauRepId,userId,activityId,customerNo,contactId] [" +
				reportId + lauRepId + userId + activityId + customerNo + contactId + "]");
		//Insert the details to LAU_REPORTS
		int id = template.update(SQL_INSERT_STRING,new Object[]{
			lauRepId,
			reportId,
			userId,
			userId,
			dt,
			dt,
			userId,
			activityId,
			customerNo,
			contactId,
			"DATA ENTRY",
			dt,
			userId
		});
		log.info("LausIEBELReportsDAO insert() ID -> " + id);
		//This will return the last inserted REPORT_ID
		return reportId;
	}
}

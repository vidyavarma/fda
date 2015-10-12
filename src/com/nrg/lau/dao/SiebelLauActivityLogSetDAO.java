package com.nrg.lau.dao;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.commons.Constants;

public class SiebelLauActivityLogSetDAO {

	private static Logger log = Logger
			.getLogger(SiebelLauActivityLogSetDAO.class);

	long activityLogId = 0;

	protected void finalize() throws Throwable {

		super.finalize();
	}

	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId,String user,java.sql.Timestamp dstamp) throws Exception {
		log.info("in activity save");
		String userId = user;
		java.sql.Timestamp dt = dstamp;
		insert(template, reportId,request,userId,dt);
	}

	public void insert(SimpleJdbcTemplate template, long reportId,HttpServletRequest request,String userId, java.sql.Timestamp dt)
			throws Exception {
		int id = 0;
		activityLogId = CommonDAO.getPrimaryKey(template);

		log
				.info("Generated Primary Key for ACTIVITY_LOG_ID ->"
						+ activityLogId);
		id = template.update(SQL_INSERT_STRING, getParameters(reportId,request,userId,dt));
		log.info("lauActivityLogDAO insert() ID -> " + id);

	}

	private Object[] getParameters(long reportId,HttpServletRequest request,String userId, java.sql.Timestamp dt) {

		return new Object[] { Constants.SIEBEL_ACTIVITY_TYPE, "", "",
				userId,dt, reportId, activityLogId };
	}

	private final String SQL_INSERT_STRING = "INSERT INTO LAU_REPORT_ACTIVITY_LOG(ACTIVITY_TYPE,"
			+ "REPORT_UPDATE_REASON_CODE,REPORT_UPDATE_REASON_DESC,"
			+ "UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID,ACTIVITY_LOG_ID) VALUES (?,?,?,?,?,?,?)";

	/**
	 * activityLogId activityType reportUpdateReasonCode reportUpdateReasonDesc
	 * reportId updateUserId updateTimeStamp "LAU_REPORT_ACTIVITY_LOG" (
	 * "ACTIVITY_LOG_ID" NUMBER NOT NULL ENABLE, "REPORT_ID" NUMBER NOT NULL
	 * ENABLE, "ACTIVITY_TYPE" VARCHAR2(50 BYTE) NOT NULL ENABLE,
	 * "REPORT_UPDATE_REASON_CODE" VARCHAR2(50 BYTE),
	 * "REPORT_UPDATE_REASON_DESC" VARCHAR2(500 BYTE), "UPDATE_USER_ID"
	 * VARCHAR2(300 BYTE) NOT NULL ENABLE, "UPDATE_TIMESTAMP" TIMESTAMP (0)
	 **/

}

package com.nrg.lau.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import com.nrg.lau.beans.LauUsers;
import com.nrg.lau.commons.IReportsConstants;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import org.springframework.web.multipart.MultipartFile;
public class CommonDAO {
	private static Logger log = Logger.getLogger(CommonDAO.class);

	// commented on 11/17/2010 to avoid static variables dt and USERID
	// overwritten by another user
	// public static java.sql.Timestamp dt = null;
	// public static String USERID = "";

	// This method will generate the required Primary Key for
	// the calling Class (Table DAO)
	public static long getPrimaryKey(SimpleJdbcTemplate template) {
		return template.queryForLong(IReportsConstants.LAU_SEQ_SQL);
	}
	public static long getUniqueId(SimpleJdbcTemplate template) {
		return template.queryForLong(IReportsConstants.SEQ_UNIQUE_ID_SQL);
	}
	public static java.sql.Timestamp getTimestamp(SimpleJdbcTemplate template) {
		return template.queryForObject(IReportsConstants.LAU_TIME_STAMP_SQL,
				java.sql.Timestamp.class);
	}


	public static String getUSERGROUPID(HttpServletRequest request) {
		String groupId = "";
		if (request.getSession().getAttribute("lauUsers") != null) {
			LauUsers lauUsers = (LauUsers) request.getSession().getAttribute(
					"lauUsers");
			groupId = lauUsers.getUSER_GROUP_ID();
		}
		 log.info ("getUSERGROUPID:userid is :"+groupId );
		return groupId;
	}
	public static String getUSERID(HttpServletRequest request) {
		String userId = "";
		if (request.getSession().getAttribute("lauUsers") != null) {
			LauUsers lauUsers = (LauUsers) request.getSession().getAttribute(
					"lauUsers");
			userId = lauUsers.getUSER_ID();
		}
		// log.warn ("getUSERID:userid is :"+userId );
		return userId;
	}
	
//	public static void setUSERID(HttpServletRequest request){
//			USERID = getUSERID(request);		
//	}
		
	public static String getLauRepId(DataSource ds, String userId,String groupId) throws SQLException {

		Connection con = null;
		String lauRpId = "";
		try {

			con 		= ds.getConnection();
			String sql 	= "{ call ? := LAU_UTL.getLauReportId(?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.VARCHAR);
			stmt.setString(2,userId);
			stmt.setString(3,groupId);
			
			log.info(sql);
			stmt.execute();
			lauRpId = stmt.getString(1);

			stmt.close();
			con.close();
			
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				log.error(e, e);
			}
		}

		return lauRpId;

	}

	public static void setDbmsClientInfo(SimpleJdbcTemplate template,
			String userId) {
		int id = 0;
		id = template.update(
				"begin DBMS_APPLICATION_INFO.SET_CLIENT_INFO (?); end;",
				new Object[] { userId });
		log.info("client infoO update() ID -> " + id + ":" + userId);
	}

	public static void setReseqenceDisplayOrder(SimpleJdbcTemplate template,
			long reportId, String user, java.sql.Timestamp dstamp,
			String tableName, String dispColName, long startNumber,
			String tablePKName, String tablePKId, long posFromTop) {
		int id = 0;
		id = template.update(
				"begin LAU_UTL.RESEQUENCEALL(?,?,?,?,?,?,?,?,?); end;",
				new Object[] { reportId, user, dstamp, tableName, dispColName,
						startNumber, tablePKName, tablePKId, posFromTop });
		log.info("setReseqenceDisplayOrder update() ID -> " + id
				+ ":, table name:" + tableName + ",repid:" + reportId
				+ ", startnu,:" + startNumber);
	}

	public static void setReportSummaryXml(SimpleJdbcTemplate template,
			long reportId, String userId) {
		int id = 0;
		id = template.update(
				"begin LAU_UTL.setReportSummaryXml(?,?); end;",
				new Object[] { reportId,userId});
		log.info("setReportSummaryXml update() ID -> " + id
				+ ":, repid:" + reportId + ", userId:"+userId);
	}
	
	private static String SQL_ACTIVITY_INSERT_STRING = "INSERT INTO LAU_REPORT_ACTIVITY_LOG(ACTIVITY_LOG_ID," +
    "REPORT_ID,ACTIVITY_TYPE,REPORT_UPDATE_REASON_CODE,REPORT_UPDATE_REASON_DESC," +
    "UPDATE_USER_ID,UPDATE_TIMESTAMP) VALUES (?,?,?,?,?,?,?)";

	public static int insertActivityDetails(SimpleJdbcTemplate template, DataSource ds, 
			   long reportId, String activityType, String reasonCode,
			   String reasonDescription, HttpServletRequest request,String user,java.sql.Timestamp dstamp) {
			  
			  int id = 0;
			  long activityLogId = 0;
			  try {
			   if(ds != null)
			   {
				    template = new SimpleJdbcTemplate(ds);
			   }
			   log.info("using ds");
			   activityLogId = CommonDAO.getPrimaryKey(template);
			   log.info("activity id is:"+activityLogId);

			   if(activityLogId != 0) {
			    id = template.update(SQL_ACTIVITY_INSERT_STRING,new Object[] {
			     activityLogId,
			     reportId,
			     activityType,
			     reasonCode,
			     reasonDescription,
			     user,
			     dstamp
			    });
			   }
			  }catch (Exception e) {
			   log.info("CommonDAO - setActivityDetails() -> LAU_REPORT_ACTIVITY_LOG  failed: " + e.getMessage());
			   log.error(e);
			  } 
			  return id;
			 }

	public static String getDbmsClientInfo(SimpleJdbcTemplate template) {
		/*
		 * int id = 0; String nm = "yyy"; id =
		 * template.update("begin DBMS_APPLICATION_INFO.SET_CLIENT_INFO (?); end;"
		 * ,new Object[] {nm}); log.info("client infoO update() ID -> " + id);
		 * String name = "'CLIENT_INFO'";
		 */
		return (String) template.queryForObject(
				"SELECT userenv('CLIENT_INFO') FROM DUAL", String.class);
	}

	public static Long getNextDisplayNumber(SimpleJdbcTemplate template,
			Long repId, String tableName, int startDispNumber) {

		log.info(" commondao display number :"
				+ "select nvl(max(display_number)," + startDispNumber
				+ ")+1 from " + tableName + " where report_id = " + repId);
		return (Long) template.queryForObject("select nvl(max(display_number),"
				+ startDispNumber + ")+1 from " + tableName
				+ " where report_id = " + repId, Long.class);
	}

	public static String getUserGroup(String userid,
			SimpleJdbcTemplate template, DataSource ds) {
		String groupName = "";
		try {
			if(ds != null)	{
			    template = new SimpleJdbcTemplate(ds);
		   }
			groupName = template.queryForObject(
					"SELECT USER_GROUP_ID  FROM lau_users where upper(USER_ID) = ?", String.class,new Object[] {userid.toUpperCase()});
			log.info("group id" + groupName);
		} catch (Exception e) {
			log.info("common.getUserGroup -  failed: " + e.getMessage());
			log.error(e);
		}

		return groupName;
	}
	
	public static String getFileContents(MultipartFile f) {
		
		//String fileAsString = CommonDAO.getFileContents(file);
		String result 		= null;
        DataInputStream in 	= null;

        try {
        	
        	byte[] buffer = new byte[(int) f.getSize()];
            in = new DataInputStream(f.getInputStream());
            in.readFully(buffer);
            result = new String(buffer);
        
        } catch (Exception e) {
            log.error(e);        	
        } finally {
        	try {
                in.close();
            } catch (Exception e) {	
            	log.error(e); 
            }
        }
        return result;
	}

}

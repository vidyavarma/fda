package com.nrg.lau.dao;

import java.io.StringReader;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauReportActivities;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauReportActivitiesSetDAO implements IReportChildSetDAO<LauReportActivities>{

	private static Logger log	= Logger.getLogger(LauReportActivitiesSetDAO.class);
	private Vector<LauReportActivities> reports		= null;
	private LauReportActivities reportActivities = null;
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	private long activityID	= 0;
	private String attachID = "";

	protected void finalize() throws Throwable {
		this.reports.removeAllElements();
		this.reports = null;
		super.finalize();
	}


	public long save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId,String user,java.sql.Timestamp dstamp,String dummy) throws Exception {
		userId 	= user;
		dt 		= dstamp;
		log.info("userId: "+userId+"   dt: "+dt);
		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_REPORT_ACTIVITIES_PARAM_NAME) != null &&
				request.getParameter(IReportsConstants.LAU_REPORT_ACTIVITIES_PARAM_NAME).length() > 0) {

			log.info("LauReportActivitiesSetDAO save() LAU_REPORT_ACTIVITIES_PARAM_NAME -> "
					+ request.getParameter(IReportsConstants.LAU_REPORT_ACTIVITIES_PARAM_NAME));
			
			//Create LauReportActivities beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_REPORT_ACTIVITIES_PARAM_NAME));
			Iterator<LauReportActivities> itr = this.reports.iterator();

			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauReportActivities lauReportActivities = (LauReportActivities)itr.next();
				log.info("itr.hasNext() -> " + lauReportActivities.toString());
				this.reportActivities	= null;
				this.reportActivities	= lauReportActivities;
				
				attachID = lauReportActivities.getATTACHMENTS();
				log.info("attachID: "+attachID);
				
				if(lauReportActivities.getACTIVITY_ID() == 0	)	{
						insert(template,reportId);
				}else   {
					if(lauReportActivities.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else	update(template);
				}
				activityID = lauReportActivities.getACTIVITY_ID();
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_REPORT_ACTIVITIES_PARAM_NAME + " not found in request");
		}
		return activityID;

	}


	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {
		int id = 0, id1=0;
		long activityId = CommonDAO.getPrimaryKey(template);
		this.reportActivities.setACTIVITY_ID(activityId);
		if(this.reportActivities.getREPORT_ID() == 0)	{
			this.reportActivities.setREPORT_ID(reportId);	}
		log.info("Generated Primary Key for ACTIVITY_ID  ->" + activityId);
		id = template.update(SQL_INSERT_STRING,getInsertParameters(template));
		log.info("LauReportActivitiesSetDAO insert() ID -> " + id);
		log.info("userId: "+userId+"   dt: "+dt);
		log.info("attachID: "+attachID+" this.reportActivities.Attachments: "+this.reportActivities.getATTACHMENTS());
		if(this.reportActivities.getATTACHMENTS().length() > 0)
		{
			id1 = template.update("UPDATE LAU_REPORT_ATTACHMENTS SET ACTIVITY_ID = ?"+  //this.reportActivities.getACTIVITY_ID()+
					", UPDATE_USER_ID=?"+
					", UPDATE_TIMESTAMP = ?"+
					"  WHERE ATTACHMENT_ID in ("+ attachID +")",getAttachParameters(template));
			log.info("LauReportActivitiesSetDAO updateAttachment() ID1 -> " + id1);
		}
	}


	private Object[] getAttachParameters(SimpleJdbcTemplate template) {

		LauReportActivities lauReportActivities = this.reportActivities;
		return new Object[]{

				lauReportActivities.getACTIVITY_ID(),
				userId ,
				dt
				
		};
	}


	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0, id1=0, id2=0;
		
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauReportActivitiesSetDAO update() ID -> " + id);
		if(this.reportActivities.getATTACHMENTS().length() > 0)
		{
			
			id1 = template.update(SQL_DELETE_ATTACH_STRING,
					new Object[]{null,userId ,dt,this.reportActivities.getREPORT_ID(),this.reportActivities.getACTIVITY_ID()});
			log.info("LauReportActivitiesSetDAO delete ATTACHMENT() ID -> " + id1);
			
			id2 = template.update("UPDATE LAU_REPORT_ATTACHMENTS SET ACTIVITY_ID = ? "+ //this.reportActivities.getACTIVITY_ID()+
					", UPDATE_USER_ID=? "+
					", UPDATE_TIMESTAMP=? "+
					"  WHERE ATTACHMENT_ID in ("+ attachID +")",getAttachParameters(template));
			log.info("LauReportActivitiesSetDAO updateAttachment() ID2 -> " + id2);
		}
		
	}
	
	
	
	private Object[] getParameters() {
		log.info("userId: "+userId+"   dt: "+dt);
		LauReportActivities lauReportActivities = this.reportActivities;
		return new Object[]{
				lauReportActivities.getDISPLAY_NUMBER(),
				lauReportActivities.getCONTACT_ID(),
				lauReportActivities.getDIRECTION(),
				lauReportActivities.getCORPORATE_RECEIVED_DATE(),
				lauReportActivities.getLOCAL_RECEIVED_DATE(),
				lauReportActivities.getPARTNER_RECEIVED_DATE(),
				lauReportActivities.getACTIVITY_DATE(),
				lauReportActivities.getACTIVITY_TYPE(),
				lauReportActivities.getACTIVITY_REASON(),
				lauReportActivities.getACTIVITY_DESCRIPTION(),
				lauReportActivities.getPRIORITY(),
				lauReportActivities.getASSIGNED_TO_USER(),
				lauReportActivities.getASSIGNED_TO_GROUP(),
				lauReportActivities.getDUE_DATE(),
				lauReportActivities.getACTIVITY_STATUS(),
				lauReportActivities.getCOMPLETION_DATE(),
				lauReportActivities.getACTIVITY_ACTION_DATE(),
				lauReportActivities.getPROMOTE_ACTIVITY(),
				userId ,dt,
				lauReportActivities.getCUSTOM_COMMENT_01(),
				lauReportActivities.getCUSTOM_COMMENT_02(),
				lauReportActivities.getREPORT_ID(),
				lauReportActivities.getACTIVITY_ID()
		};
	}
	private Object[] getInsertParameters(SimpleJdbcTemplate template) {

		LauReportActivities lauReportActivities = this.reportActivities;
		return new Object[]{
				CommonDAO.getNextDisplayNumber(template, lauReportActivities.getREPORT_ID(), "LAU_REPORT_ACTIVITIES",0),
				lauReportActivities.getCONTACT_ID(),
				lauReportActivities.getDIRECTION(),
				lauReportActivities.getCORPORATE_RECEIVED_DATE(),
				lauReportActivities.getLOCAL_RECEIVED_DATE(),
				lauReportActivities.getPARTNER_RECEIVED_DATE(),
				lauReportActivities.getACTIVITY_DATE(),
				lauReportActivities.getACTIVITY_TYPE(),
				lauReportActivities.getACTIVITY_REASON(),
				lauReportActivities.getACTIVITY_DESCRIPTION(),
				lauReportActivities.getPRIORITY(),
				lauReportActivities.getASSIGNED_TO_USER(),
				lauReportActivities.getASSIGNED_TO_GROUP(),
				lauReportActivities.getDUE_DATE(),
				lauReportActivities.getACTIVITY_STATUS(),
				lauReportActivities.getCOMPLETION_DATE(),
				lauReportActivities.getACTIVITY_ACTION_DATE(),
				lauReportActivities.getPROMOTE_ACTIVITY(),
				userId ,dt,
				lauReportActivities.getCUSTOM_COMMENT_01(),
				lauReportActivities.getCUSTOM_COMMENT_02(),
				lauReportActivities.getREPORT_ID(),
				lauReportActivities.getACTIVITY_ID()
		};
	}

	public void createBeansFromXml(String xml) throws Exception {
		String mainXmlTag = "ROWSET/ROW";
		reports				= new Vector<LauReportActivities>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauReportActivities.class );

		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/ACTIVITY_ID", "ACTIVITY_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "REPORT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISPLAY_NUMBER", "DISPLAY_NUMBER" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_ID", "CONTACT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/DIRECTION", "DIRECTION" );
		digester.addBeanPropertySetter( mainXmlTag+"/COMPANY_RECEIVED_DATE", "CORPORATE_RECEIVED_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/LOCAL_RECEIVED_DATE", "LOCAL_RECEIVED_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_RECEIVED_DATE", "PARTNER_RECEIVED_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/ACTIVITY_DATE", "ACTIVITY_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/ACTIVITY_ACTION_DATE", "ACTIVITY_ACTION_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/ACTIVITY_TYPE", "ACTIVITY_TYPE" );
		digester.addBeanPropertySetter( mainXmlTag+"/ACTIVITY_REASON", "ACTIVITY_REASON" );
		digester.addBeanPropertySetter( mainXmlTag+"/ACTIVITY_DESCRIPTION", "ACTIVITY_DESCRIPTION" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRIORITY", "PRIORITY" );
		digester.addBeanPropertySetter( mainXmlTag+"/ASSIGNED_TO", "ASSIGNED_TO_USER" );
		digester.addBeanPropertySetter( mainXmlTag+"/ASSIGNED_TO_GROUP", "ASSIGNED_TO_GROUP" );
		digester.addBeanPropertySetter( mainXmlTag+"/DUE_DATE", "DUE_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/ACTIVITY_STATUS", "ACTIVITY_STATUS" );
		digester.addBeanPropertySetter( mainXmlTag+"/COMPLETION_DATE", "COMPLETION_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "UPDATE_USER_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "UPDATE_TIMESTAMP" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROMOTE_ACTIVITY", "PROMOTE_ACTIVITY" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_COMMENT_01", "CUSTOM_COMMENT_01" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_COMMENT_02", "CUSTOM_COMMENT_02" );
		digester.addBeanPropertySetter( mainXmlTag+"/ATTACHMENTS", "ATTACHMENTS" );
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );
		digester.parse(new StringReader(xml));

	}

	public void addXmlData(LauReportActivities lauReportActivities) {
		reports.add(lauReportActivities);
		log.info(lauReportActivities.toString());
	}

	//SQL Statements
	private final String SQL_UPDATE_STRING = "UPDATE LAU_REPORT_ACTIVITIES SET DISPLAY_NUMBER=?," +
			"CONTACT_ID=?,DIRECTION=?,CORPORATE_RECEIVED_DATE=?,LOCAL_RECEIVED_DATE=?,PARTNER_RECEIVED_DATE=?," +
			"ACTIVITY_DATE=?,ACTIVITY_TYPE=?,ACTIVITY_REASON=?,ACTIVITY_DESCRIPTION=?,PRIORITY=?,ASSIGNED_TO_USER=?," +
			"ASSIGNED_TO_GROUP=?,DUE_DATE=?,ACTIVITY_STATUS=?,COMPLETION_DATE=?,ACTIVITY_ACTION_DATE=?,PROMOTE_ACTIVITY=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?," +
			"CUSTOM_COMMENT_01=?,CUSTOM_COMMENT_02=? WHERE REPORT_ID=? AND ACTIVITY_ID=?";

	private final String SQL_INSERT_STRING = "INSERT INTO LAU_REPORT_ACTIVITIES (DISPLAY_NUMBER," +
			"CONTACT_ID,DIRECTION,CORPORATE_RECEIVED_DATE,LOCAL_RECEIVED_DATE,PARTNER_RECEIVED_DATE,ACTIVITY_DATE," +
			"ACTIVITY_TYPE,ACTIVITY_REASON,ACTIVITY_DESCRIPTION,PRIORITY,ASSIGNED_TO_USER,ASSIGNED_TO_GROUP,DUE_DATE," +
			"ACTIVITY_STATUS,COMPLETION_DATE,ACTIVITY_ACTION_DATE,PROMOTE_ACTIVITY,UPDATE_USER_ID,UPDATE_TIMESTAMP,CUSTOM_COMMENT_01,CUSTOM_COMMENT_02,REPORT_ID,ACTIVITY_ID) " +
			"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private final String SQL_DELETE_ATTACH_STRING = "UPDATE LAU_REPORT_ATTACHMENTS SET " +
			"ACTIVITY_ID=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? " +
			"WHERE REPORT_ID=? AND ACTIVITY_ID=?";
	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_DELETE_ATTACH_STRING,
				new Object[]{null,userId ,dt,this.reportActivities.getREPORT_ID(),this.reportActivities.getACTIVITY_ID()});
		log.info("LauReportActivitiesSetDAO delete ATTACHMENT() ID -> " + id);
		
		id = template.update("DELETE FROM LAU_REPORT_ACTIVITIES WHERE ACTIVITY_ID = ?",
				new Object[]{this.reportActivities.getACTIVITY_ID()});
		log.info("LauReportActivitiesSetDAO delete() ID -> " + id);
	}


	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, String user, Timestamp dstamp) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 	LAU_REPORT_ACTIVITIES

	 	ACTIVITY_ID NUMBER NOT NULL
		REPORT_ID NUMBER NOT NULL
		DISPLAY_NUMBER NUMBER NOT NULL
		ATTACHMENT_ID NUMBER
		CONTACT_ID NUMBER
		DIRECTION VARCHAR2(50 BYTE)
		CORPORATE_RECEIVED_DATE VARCHAR2(8 CHAR)
		LOCAL_RECEIVED_DATE VARCHAR2(8 CHAR)
		PARTNER_RECEIVED_DATE VARCHAR2(8 CHAR)
		ACTIVITY_DATE VARCHAR2(8 CHAR)
		ACTIVITY_TYPE VARCHAR2(50 BYTE)
		ACTIVITY_REASON VARCHAR2(100 BYTE)
		ACTIVITY_DESCRIPTION VARCHAR2(4000 BYTE)
		PRIORITY VARCHAR2(50 BYTE)
		ASSIGNED_TO_USER VARCHAR2(300 BYTE)
		ASSIGNED_TO_GROUP VARCHAR2(300 BYTE)
		DUE_DATE VARCHAR2(8 CHAR)
		ACTIVITY_STATUS VARCHAR2(50 BYTE)
		COMPLETION_DATE VARCHAR2(8 CHAR)
		UPDATE_USER_ID VARCHAR2(300 BYTE) NOT NULL
		UPDATE_TIMESTAMP TIMESTAMP(2) WITH LOCAL TIME ZONE NOT NULL
		CUSTOM_COMMENT_01
	 */

}

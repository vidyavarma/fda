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

public class LauReportActivitiesAttachmentSetDAO implements IReportChildSetDAO<LauReportActivities>{

	private static Logger log	= Logger.getLogger(LauReportActivitiesAttachmentSetDAO.class);
	private Vector<LauReportActivities> reports		= null;
	private LauReportActivities reportActivities = null;
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";

	protected void finalize() throws Throwable {
		this.reports.removeAllElements();
		this.reports = null;
		super.finalize();
	}


	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId,String user,java.sql.Timestamp dstamp,long activityID) throws Exception {
		userId = user;
		dt = dstamp;
		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_REPORT_ACTIVITIES_ATTCHMENT_PARAM_NAME ) != null &&
				request.getParameter(IReportsConstants.LAU_REPORT_ACTIVITIES_ATTCHMENT_PARAM_NAME ).length() > 0) {

			log.info("LauReportActivitiesAttachmentSetDAO save() LAU_REPORT_ACTIVITIES_ATTCHMENT_PARAM_NAME  -> "
					+ request.getParameter(IReportsConstants.LAU_REPORT_ACTIVITIES_ATTCHMENT_PARAM_NAME ));

			//Create LauReportActivities beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_REPORT_ACTIVITIES_ATTCHMENT_PARAM_NAME ));
			Iterator<LauReportActivities> itr = this.reports.iterator();

			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauReportActivities lauReportActivities = (LauReportActivities)itr.next();
				log.info("itr.hasNext() -> " + lauReportActivities.toString());
				this.reportActivities	= null;
				this.reportActivities	= lauReportActivities;

				if(lauReportActivities.getAttachmentTransactionType() == IReportsConstants.deleteFlag)	{
					log.info("in delete");
					delete(template);
				}else
				{
					if(lauReportActivities.getACTIVITY_ID() == 0) {
						lauReportActivities.setACTIVITY_ID(activityID);
					}
					update(template);
				}
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_REPORT_ACTIVITIES_ATTCHMENT_PARAM_NAME  + " not found in request");
		}

	}


	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {
		// TODO Auto-generated method stub
	}


	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauReportActivitiesAttachmentSetDAO update() ID -> " + id);

	}

	private Object[] getParameters() {

		LauReportActivities lauReportActivities = this.reportActivities;
		return new Object[]{
				lauReportActivities.getACTIVITY_ID(),
				userId,dt,
				lauReportActivities.getREPORT_ID(),
				lauReportActivities.getATTACHMENT_ID()
		};
	}
	private Object[] getDeleteParameters() {

		LauReportActivities lauReportActivities = this.reportActivities;
		log.info("ids are"+lauReportActivities.getREPORT_ID()+", "+lauReportActivities.getATTACHMENT_ID());
		return new Object[]{
				"",
				userId,dt,
				lauReportActivities.getREPORT_ID(),
				lauReportActivities.getATTACHMENT_ID()
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
		digester.addBeanPropertySetter( mainXmlTag+"/ATTACHMENT_ID", "ATTACHMENT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "attachmentTransactionType" );
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );
		digester.parse(new StringReader(xml));

	}

	public void addXmlData(LauReportActivities lauReportActivities) {
		reports.add(lauReportActivities);
		log.info(lauReportActivities.toString());
	}

	//SQL Statements
	private final String SQL_UPDATE_STRING = "UPDATE LAU_REPORT_ATTACHMENTS SET " +
			"ACTIVITY_ID=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? " +
			"WHERE REPORT_ID=? AND ATTACHMENT_ID =?";

	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getDeleteParameters());
		log.info("LauReportActivitiesAttachmentSetDAO update() ID 11-> " + id);
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
	 */

}

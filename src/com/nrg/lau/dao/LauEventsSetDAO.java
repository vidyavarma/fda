package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauEvents;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauEventsSetDAO implements IReportChildSetDAO<LauEvents>{

	private static Logger log	= Logger.getLogger(LauEventsSetDAO.class);
	private Vector<LauEvents> reports	= null;
	private LauEvents events			= null;	
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	
	protected void finalize() throws Throwable {		
		this.reports.removeAllElements();
		this.reports = null;
		super.finalize();
	}
	
	
	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId,String user,java.sql.Timestamp dstamp) throws Exception {
		userId = user;
		dt = dstamp;
		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_EVENTS_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_EVENTS_PARAM_NAME).length() > 0) {
			
			log.info("LauEventsDAO save() LAU_EVENTS_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_EVENTS_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_EVENTS_PARAM_NAME));
			Iterator<LauEvents> itr = this.reports.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauEvents lauEvents = (LauEvents)itr.next();
				log.info("itr.hasNext() -> " + lauEvents.toString());
				this.events	= null;
				this.events	= lauEvents;
				if(lauEvents.getEventId() == 0	)	{
						insert(template,reportId);
				}else   {
					if(lauEvents.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else	update(template);				
				}
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_EVENTS_PARAM_NAME + " not found in request");
		}		
	}
	
	
	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {		
		int id = 0, id1= 0;
		long eventId = CommonDAO.getPrimaryKey(template);
		this.events.setEventId(eventId);
		if(this.events.getReportId() == 0)	{
			this.events.setReportId(reportId);	}
		log.info("Generated Primary Key for EVENT_ID ->" + eventId);
		id = template.update(SQL_INSERT_STRING,getInsertParameters(template));
		log.info("LauEventsDAO insert() ID -> " + id);
		
		if(this.events.getAUTOPSY_PERFORMED() != null || this.events.getDEATH_DATE() != null) {
			id1 = template.update(SQL_INSERT_PATIENT_STRING,getInsertPATParameters(template));
			log.info("LauEventsDAO PATIENTSinsert() ID -> " + id1);
		}
		
	}

	
	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauEventsDAO update() ID -> " + id);	
		
	}
	
	private Object[] getParameters()
	{
		LauEvents lauEvents = this.events; 
		return new Object[]{
			lauEvents.getEventVerbatim(),
			lauEvents.getEVENT_TERM(),
			lauEvents.getDisplayNumber(),
			lauEvents.getOnsetDate(),
			lauEvents.getEventOngoingFlag(),
			lauEvents.getEndDate(),
			lauEvents.getSeriousFlag(),
			lauEvents.getHospitalizationFlag(),
			lauEvents.getMedicallySignificantFlag(),
			lauEvents.getPatientDiedFlag(),
			lauEvents.getLifeThreateningFlag(),
			lauEvents.getDisabilityFlag(),
			lauEvents.getCongenitalAnomalyFlag(),
			lauEvents.getHospitalizedFromDate(),
			lauEvents.getHospitalizedToDate(),
			lauEvents.getEventOutcome(),
			lauEvents.getCompanyCausality(),
			lauEvents.getReporterCausality(),
			lauEvents.getPriorHistoryOfEvent(),
			lauEvents.getPriorHistoryComment(),
			lauEvents.getRelatedToProdComplaint(),
			lauEvents.getRelatedToLotNumber(),
			userId,
			dt,
			lauEvents.getHospitalizationNature(),
			lauEvents.getOTHER_SERIOUSNESS_FLAG(),
			lauEvents.getOTHER_SERIOUSNESS_DESC(),
			lauEvents.getEVENT_LLT(),
			lauEvents.getEVENT_PT(),
			lauEvents.getEVENT_SOC(),
			lauEvents.getEVENT_HLT(),
			lauEvents.getEVENT_HLGT(),
			lauEvents.getReportId(),
			lauEvents.getEventId()
		};
	}
	private Object[] getInsertParameters(SimpleJdbcTemplate template)
	{
		LauEvents lauEvents = this.events; 
		return new Object[]{
			lauEvents.getEventVerbatim(),
			lauEvents.getEVENT_TERM(),
			CommonDAO.getNextDisplayNumber(template, lauEvents.getReportId(), "lau_events",0),
			lauEvents.getOnsetDate(),
			lauEvents.getEventOngoingFlag(),
			lauEvents.getEndDate(),
			lauEvents.getSeriousFlag(),
			lauEvents.getHospitalizationFlag(),
			lauEvents.getMedicallySignificantFlag(),
			lauEvents.getPatientDiedFlag(),
			lauEvents.getLifeThreateningFlag(),
			lauEvents.getDisabilityFlag(),
			lauEvents.getCongenitalAnomalyFlag(),
			lauEvents.getHospitalizedFromDate(),
			lauEvents.getHospitalizedToDate(),
			lauEvents.getEventOutcome(),
			lauEvents.getCompanyCausality(),
			lauEvents.getReporterCausality(),
			lauEvents.getPriorHistoryOfEvent(),
			lauEvents.getPriorHistoryComment(),
			lauEvents.getRelatedToProdComplaint(),
			lauEvents.getRelatedToLotNumber(),
			userId,
			dt,
			lauEvents.getHospitalizationNature(),
			lauEvents.getOTHER_SERIOUSNESS_FLAG(),
			lauEvents.getOTHER_SERIOUSNESS_DESC(),
			lauEvents.getEVENT_LLT(),
			lauEvents.getEVENT_PT(),
			lauEvents.getEVENT_SOC(),
			lauEvents.getEVENT_HLT(),
			lauEvents.getEVENT_HLGT(),
			lauEvents.getReportId(),
			lauEvents.getEventId()
		};
	}	
	
	private Object[] getInsertPATParameters(SimpleJdbcTemplate template)
	{
		LauEvents lauEvents = this.events; 
		return new Object[]{
			CommonDAO.getNextDisplayNumber(template, lauEvents.getReportId(), "lau_events",0),
		    lauEvents.getDEATH_DATE(),
		    lauEvents.getAUTOPSY_PERFORMED(),
		    userId,
			dt,
			lauEvents.getReportId(),
			lauEvents.getEventId()
		};
	}	
	
	public void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag = "ROWSET/ROW";
		reports				= new Vector<LauEvents>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauEvents.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/EVENT_ID", "eventId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/EVENT_VERBATIM", "eventVerbatim" );
		digester.addBeanPropertySetter( mainXmlTag+"/EVENT_TERM", "EVENT_TERM" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISPLAY_NUMBER", "displayNumber2" );
		digester.addBeanPropertySetter( mainXmlTag+"/ONSET_DATE", "onsetDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/EVENT_ONGOING_FLAG", "eventOngoingFlag" );
		digester.addBeanPropertySetter( mainXmlTag+"/END_DATE", "endDate" );		
		digester.addBeanPropertySetter( mainXmlTag+"/SERIOUS_FLAG", "seriousFlag" );
		digester.addBeanPropertySetter( mainXmlTag+"/HOSPITALIZATION_FLAG", "hospitalizationFlag" );
		digester.addBeanPropertySetter( mainXmlTag+"/MEDICALLY_SIGNIFICANT_FLAG", "medicallySignificantFlag" );
		digester.addBeanPropertySetter( mainXmlTag+"/PATIENT_DIED_FLAG", "patientDiedFlag" );
		digester.addBeanPropertySetter( mainXmlTag+"/LIFE_THREATENING_FLAG", "lifeThreateningFlag" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISABILITY_FLAG", "disabilityFlag" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONGENITAL_ANOMALY_FLAG", "congenitalAnomalyFlag" );		
		digester.addBeanPropertySetter( mainXmlTag+"/HOSPITALIZED_FROM_DATE", "hospitalizedFromDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/HOSPITALIZED_TO_DATE", "hospitalizedToDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/EVENT_OUTCOME", "eventOutcome" );
		digester.addBeanPropertySetter( mainXmlTag+"/COMPANY_CAUSALITY", "companyCausality" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_CAUSALITY", "reporterCausality" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRIOR_HISTORY_OF_EVENT", "priorHistoryOfEvent" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRIOR_HISTORY_COMMENT", "priorHistoryComment" );		
		digester.addBeanPropertySetter( mainXmlTag+"/RELATED_TO_PROD_COMPLAINT", "relatedToProdComplaint" );
		digester.addBeanPropertySetter( mainXmlTag+"/RELATED_TO_LOT_NUMBER", "relatedToLotNumber" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "updateUserId" );
		digester.addBeanPropertySetter( mainXmlTag+"/HOSPITALIZATION_NATURE", "hospitalizationNature" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		digester.addBeanPropertySetter( mainXmlTag+"/OTHER_SERIOUSNESS_FLAG", "OTHER_SERIOUSNESS_FLAG" );
		digester.addBeanPropertySetter( mainXmlTag+"/OTHER_SERIOUSNESS_DESC", "OTHER_SERIOUSNESS_DESC" );
		digester.addBeanPropertySetter( mainXmlTag+"/EVENT_LLT", "EVENT_LLT" );
		digester.addBeanPropertySetter( mainXmlTag+"/EVENT_PT", "EVENT_PT" );
		digester.addBeanPropertySetter( mainXmlTag+"/EVENT_SOC", "EVENT_SOC" );
		digester.addBeanPropertySetter( mainXmlTag+"/EVENT_HLT", "EVENT_HLT" );
		digester.addBeanPropertySetter( mainXmlTag+"/EVENT_HLGT", "EVENT_HLGT" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEATH_DATE", "DEATH_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/AUTOPSY_PERFORMED", "AUTOPSY_PERFORMED" );
		//digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "updateTimeStamp" );
		
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));
		
	}
	
	public void addXmlData(LauEvents lauEvents) {
		reports.add(lauEvents);
		log.info(lauEvents.toString());
	}	
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_EVENTS SET EVENT_VERBATIM=?,EVENT_TERM=?,DISPLAY_NUMBER=?,ONSET_DATE=?," +
		"EVENT_ONGOING_FLAG=?,END_DATE=?,SERIOUS_FLAG=?,HOSPITALIZATION_FLAG=?,MEDICALLY_SIGNIFICANT_FLAG=?," +
		"PATIENT_DIED_FLAG=?,LIFE_THREATENING_FLAG=?,DISABILITY_FLAG=?,CONGENITAL_ANOMALY_FLAG=?,HOSPITALIZED_FROM_DATE=?," +
		"HOSPITALIZED_TO_DATE=?,EVENT_OUTCOME=?,COMPANY_CAUSALITY=?,REPORTER_CAUSALITY=?,PRIOR_HISTORY_OF_EVENT=?," +
		"PRIOR_HISTORY_COMMENT=?,RELATED_TO_PROD_COMPLAINT=?,RELATED_TO_LOT_NUMBER=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?," +
		"HOSPITALIZATION_NATURE=?,OTHER_SERIOUSNESS_FLAG=?,OTHER_SERIOUSNESS_DESC=?,EVENT_LLT=?,EVENT_PT=?,EVENT_SOC=?,EVENT_HLT=?,EVENT_HLGT=? WHERE REPORT_ID=? AND EVENT_ID=?";
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_EVENTS(EVENT_VERBATIM,EVENT_TERM,DISPLAY_NUMBER,ONSET_DATE," +
		"EVENT_ONGOING_FLAG,END_DATE,SERIOUS_FLAG,HOSPITALIZATION_FLAG,MEDICALLY_SIGNIFICANT_FLAG," +
		"PATIENT_DIED_FLAG,LIFE_THREATENING_FLAG,DISABILITY_FLAG,CONGENITAL_ANOMALY_FLAG,HOSPITALIZED_FROM_DATE," +
		"HOSPITALIZED_TO_DATE,EVENT_OUTCOME,COMPANY_CAUSALITY,REPORTER_CAUSALITY,PRIOR_HISTORY_OF_EVENT," +
		"PRIOR_HISTORY_COMMENT,RELATED_TO_PROD_COMPLAINT,RELATED_TO_LOT_NUMBER,UPDATE_USER_ID,UPDATE_TIMESTAMP," +
		"HOSPITALIZATION_NATURE,OTHER_SERIOUSNESS_FLAG,OTHER_SERIOUSNESS_DESC,EVENT_LLT,EVENT_PT,EVENT_SOC,EVENT_HLT,EVENT_HLGT,REPORT_ID,EVENT_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private final String SQL_INSERT_PATIENT_STRING = "INSERT INTO LAU_PATIENT_DETAILS (DISPLAY_NUMBER,DEATH_DATE,AUTOPSY_PERFORMED,UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID,PATIENT_DETAILS_ID) VALUES" +
			"(?,?,?,?,?)";
		
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_EVENTS WHERE EVENT_ID = ?",
				new Object[]{this.events.getEventId()});
		CommonDAO.setReseqenceDisplayOrder(template, this.events.getReportId(), userId,dt,
				"LAU_EVENTS", "DISPLAY_NUMBER", 1, null,null,1);
		log.info("LauEventsSetDAO delete() ID -> " + id);		
	}
	
	/**
	 	LAU_EVENTS
	 	
	 	EVENT_ID NUMBER(22 , 0) NOT NULL,
		REPORT_ID NUMBER(22 , 0) NOT NULL,
		EVENT_VERBATIM VARCHAR2(500),
		DISPLAY_NUMBER NUMBER(22 , 0),
		ONSET_DATE VARCHAR2(24),
		EVENT_ONGOING_FLAG VARCHAR2(5),
		END_DATE VARCHAR2(24),
		SERIOUS_FLAG VARCHAR2(5),
		HOSPITALIZATION_FLAG VARCHAR2(5),
		MEDICALLY_SIGNIFICANT_FLAG VARCHAR2(5),
		PATIENT_DIED_FLAG VARCHAR2(5),
		LIFE_THREATENING_FLAG VARCHAR2(5),
		DISABILITY_FLAG VARCHAR2(5),
		CONGENITAL_ANOMALY_FLAG VARCHAR2(5),
		HOSPITALIZED_FROM_DATE VARCHAR2(24),
		HOSPITALIZED_TO_DATE VARCHAR2(24),
		EVENT_OUTCOME VARCHAR2(50),
		COMPANY_CAUSALITY VARCHAR2(50),
		REPORTER_CAUSALITY VARCHAR2(50),
		PRIOR_HISTORY_OF_EVENT VARCHAR2(50),
		PRIOR_HISTORY_COMMENT VARCHAR2(4000),
		RELATED_TO_PROD_COMPLAINT VARCHAR2(50),
		RELATED_TO_LOT_NUMBER VARCHAR2(100),
		UPDATE_USER_ID VARCHAR2(300) NOT NULL,
		UPDATE_TIMESTAMP date NOT NULL,
		HOSPITALIZATION_NATURE VARCHAR2(50),
		OTHER_SERIOUSNESS_DESC
	 
	 */

}

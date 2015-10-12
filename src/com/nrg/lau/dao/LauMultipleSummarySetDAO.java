package com.nrg.lau.dao;

import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;


import com.nrg.lau.beans.LauMultipleSummaryDetails;

import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauMultipleSummarySetDAO implements IReportChildSetDAO<LauMultipleSummaryDetails>{

	private static Logger log	= Logger.getLogger(LauMultipleSummarySetDAO.class);
	private LauMultipleSummaryDetails reports	= null;
	private java.sql.Timestamp dt  = null;  
	private String userId	= "";
	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId,String user,java.sql.Timestamp dstamp) throws Exception {
		//Check to make sure that XML is there in Request.
		userId = user;
		dt = dstamp;
		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_MULTIPLE_SUMMARY_UPDATE_PARAM_NAME) != null &&
				request.getParameter(IReportsConstants.LAU_MULTIPLE_SUMMARY_UPDATE_PARAM_NAME).length() > 0) {

			log.info("LauMultipleSummarySetDAO save() LAU_MULTIPLE_SUMMARY_UPDATE_PARAM_NAME -> "
					+ request.getParameter(IReportsConstants.LAU_MULTIPLE_SUMMARY_UPDATE_PARAM_NAME));

			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_MULTIPLE_SUMMARY_UPDATE_PARAM_NAME));
			//Report Id is 0 it's UPDATE else a new INSERT
			int id = 0;

			String SQL_INSERT_STRING = "";
			if(this.reports.getREPORT_ID() == 0) {

				/*INSERT TO LAU_CONTACT	CONTACT_ID, CONTACT_LAST_NAME,
				 CONTACT_DISPLAY_NUMBER */
				if((this.reports.getCONTACT_LAST_NAME().trim().length() > 0 ||
						this.reports.getCONTACT_DISPLAY_NUMBER().trim().length() > 0) &&
						(this.reports.getCONTACT_TRANSACTION_TYPE() == IReportsConstants.insertUpdateFlag)){

					id = 0;
					SQL_INSERT_STRING = "INSERT INTO LAU_CONTACT(CONTACT_LAST_NAME, DISPLAY_NUMBER,CONTACT_IS_REPORTER," +
							"UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID,CONTACT_ID) VALUES (?,?,?,?,?,?,?)";
					long contactId = CommonDAO.getPrimaryKey(template);
					this.reports.setCONTACT_ID(contactId);
					if(this.reports.getREPORT_ID() == 0)	{
						this.reports.setREPORT_ID(reportId);	}
					log.info("Generated Primary Key for CONTACT_ID ->" + contactId);
					id = template.update(SQL_INSERT_STRING,getContactParameters());
					log.info("LauMultipleSummarySetDAO LAU_CONTACT insert() ID -> " + id);

				}

				/*INSERT TO LAU_PATIENT_DETAILS PATIENT_DETAILS_ID , PT_SEX ,
				 PT_DOB , PT_INITIALS,PT_COUNTRY AS COUNTRY_EVENT_OCCURRED */
				if((this.reports.getPT_SEX().trim().length() > 0 || this.reports.getPT_DOB().trim().length() > 0 ||
					this.reports.getPT_INITIALS().trim().length() > 0 || this.reports.getCOUNTRY_EVENT_OCCURRED().trim().length() > 0) &&
					(this.reports.getPATIENT_TRANSACTION_TYPE() == IReportsConstants.insertUpdateFlag)) {
					log.info("getPT_SEX:"+this.reports.getPT_SEX() +" PT_DOB():"+ this.reports.getPT_DOB() + " PT_INITIALS: "+this.reports.getPT_INITIALS()+ 
							" COUNTRY_EVENT:"+this.reports.getCOUNTRY_EVENT_OCCURRED());
					id = 0;
					SQL_INSERT_STRING = "INSERT INTO LAU_PATIENT_DETAILS (PT_SEX,PT_DOB,PT_INITIALS," +
							"UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID,PATIENT_DETAILS_ID) VALUES(?,?,?,?,?,?,?)";
					long patientDetailsId = CommonDAO.getPrimaryKey(template);
					this.reports.setPATIENT_DETAILS_ID(patientDetailsId);
					if(this.reports.getREPORT_ID() == 0)	{
						this.reports.setREPORT_ID(reportId);	}
					log.info("Generated Primary Key for PATIENT_DETAILS_ID ->" + patientDetailsId);
					id = template.update(SQL_INSERT_STRING,getPatientParameters());
					log.info("LauMultipleSummarySetDAO LAU_PATIENT_DETAILS insert() ID -> " + id);

				}

				/*INSERT TO LAU_EVENTS D.EVENT_ID, D.DISPLAY_NUMBER,
				 D.EVENT_VERBATIM, D.ONSET_DATE */
				if((this.reports.getEVENT_DISPLAY_NUMBER().trim().length() > 0 || 
						this.reports.getEVENT_VERBATIM().trim().length() > 0 || this.reports.getONSET_DATE().trim().length() > 0)
						&&	(this.reports.getEVENT_TRANSACTION_TYPE() == IReportsConstants.insertUpdateFlag)) {

					id = 0;
					SQL_INSERT_STRING = "INSERT INTO LAU_EVENTS(EVENT_VERBATIM,DISPLAY_NUMBER," +
							"ONSET_DATE,UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID,EVENT_ID) VALUES (?,?,?,?,?,?,?)";
					long eventId = CommonDAO.getPrimaryKey(template);
					this.reports.setEVENT_ID(eventId);
					if(this.reports.getREPORT_ID() == 0)	{
						this.reports.setREPORT_ID(reportId);	}
					log.info("Generated Primary Key for EVENT_ID ->" + eventId);
					id = template.update(SQL_INSERT_STRING, getEventParameters());
					log.info("LauMultipleSummarySetDAO LAU_EVENTS insert() ID -> " + id);
				}

				/*INSERT TO LAU_PRODUCTS E.PRODUCT_ID, E.DISPLAY_NUMBER,
				 E.PRODUCT_NAME, E.TRADE_NAME */
				if((this.reports.getPRODUCT_DISPLAY_NUMBER().trim().length() > 0 || 
						this.reports.getPRODUCT_NAME().trim().length() > 0 || this.reports.getTRADE_NAME().trim().length() > 0)
						&&(this.reports.getPRODUCT_TRANSACTION_TYPE() == IReportsConstants.insertUpdateFlag)) {

					id = 0;
					SQL_INSERT_STRING = "INSERT INTO LAU_PRODUCTS (DISPLAY_NUMBER,PRODUCT_TYPE,PRODUCT_NAME,TRADE_NAME,UPDATE_USER_ID," +
							"UPDATE_TIMESTAMP,REPORT_ID,PRODUCT_ID) VALUES (?,?,?,?,?,?,?,?)";
					long productId = CommonDAO.getPrimaryKey(template);
					this.reports.setPRODUCT_ID(productId);
					if(this.reports.getREPORT_ID() == 0)	{
						this.reports.setREPORT_ID(reportId);	}
					log.info("Generated Primary Key for PRODUCT_ID ->" + productId);
					id = template.update(SQL_INSERT_STRING,getProductParameters());
					log.info("LauMultipleSummarySetDAO LAU_PRODUCTS insert() ID -> " + id);
				}

			} else {

				String SQL_UPDATE_STRING = "";
				/*UPDATE TO LAU_CONTACT	CONTACT_ID, CONTACT_LAST_NAME,
				 CONTACT_DISPLAY_NUMBER */
				if((this.reports.getCONTACT_LAST_NAME().trim().length() > 0 ||
						this.reports.getCONTACT_DISPLAY_NUMBER().trim().length() > 0) && 
						(this.reports.getCONTACT_TRANSACTION_TYPE() == IReportsConstants.insertUpdateFlag)){
					id = 0;
					if (this.reports.getCONTACT_ID() > 0)
					{
						SQL_UPDATE_STRING = "UPDATE LAU_CONTACT SET CONTACT_LAST_NAME=?,DISPLAY_NUMBER=?,CONTACT_IS_REPORTER=?," +
								"UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE REPORT_ID=? AND CONTACT_ID=?";
						id = template.update(SQL_UPDATE_STRING,getContactParameters());
						log.info("LauMultipleSummarySetDAO LAU_CONTACT update() ID -> " + id);
					}
					else
					{
						SQL_INSERT_STRING = "INSERT INTO LAU_CONTACT(CONTACT_LAST_NAME, DISPLAY_NUMBER,CONTACT_IS_REPORTER," +
						"UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID,CONTACT_ID) VALUES (?,?,?,?,?,?,?)";
						long contactId = CommonDAO.getPrimaryKey(template);
						this.reports.setCONTACT_ID(contactId);
						if(this.reports.getREPORT_ID() == 0)	{
							this.reports.setREPORT_ID(reportId);	}
						log.info("Generated Primary Key for CONTACT_ID ->" + contactId);
						id = template.update(SQL_INSERT_STRING,getContactParameters());
						log.info("LauMultipleSummarySetDAO LAU_CONTACT insert() ID -> " + id);
					}

				}

				/*INSERT TO LAU_PATIENT_DETAILS PATIENT_DETAILS_ID , PT_SEX ,
				 PT_DOB , PT_INITIALS,PT_COUNTRY AS COUNTRY_EVENT_OCCURRED */
				if((this.reports.getPT_SEX().trim().length() > 0 || this.reports.getPT_DOB().trim().length() > 0 ||
					this.reports.getPT_INITIALS().trim().length() > 0 || this.reports.getCOUNTRY_EVENT_OCCURRED().trim().length() > 0)
					&&(this.reports.getPATIENT_TRANSACTION_TYPE() == IReportsConstants.insertUpdateFlag)){
					id = 0;
					if (this.reports.getPATIENT_DETAILS_ID() > 0)
					{
						SQL_UPDATE_STRING = "UPDATE LAU_PATIENT_DETAILS SET PT_SEX=?,PT_DOB=?,PT_INITIALS=?," +
								"UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE REPORT_ID=? AND PATIENT_DETAILS_ID=?";
						id = template.update(SQL_UPDATE_STRING,getPatientParameters());
						log.info("LauMultipleSummarySetDAO LAU_PATIENT_DETAILS  update() ID -> " + id);
					
					}
					else
					{
						SQL_INSERT_STRING = "INSERT INTO LAU_PATIENT_DETAILS (PT_SEX,PT_DOB,PT_INITIALS," +
								"UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID,PATIENT_DETAILS_ID) VALUES(?,?,?,?,?,?,?)";
						long patientDetailsId = CommonDAO.getPrimaryKey(template);
						this.reports.setPATIENT_DETAILS_ID(patientDetailsId);
						if(this.reports.getREPORT_ID() == 0)	{
							this.reports.setREPORT_ID(reportId);	}
						log.info("Generated Primary Key for PATIENT_DETAILS_ID ->" + patientDetailsId);
						id = template.update(SQL_INSERT_STRING,getPatientParameters());
						log.info("LauMultipleSummarySetDAO LAU_PATIENT_DETAILS insert() ID -> " + id);
					}

				}

				/*INSERT TO LAU_EVENTS D.EVENT_ID, D.DISPLAY_NUMBER,
				 D.EVENT_VERBATIM, D.ONSET_DATE */
				if((this.reports.getEVENT_DISPLAY_NUMBER().trim().length() > 0 || 
						this.reports.getEVENT_VERBATIM().trim().length() > 0 || this.reports.getONSET_DATE().trim().length() > 0)
						&& (this.reports.getEVENT_TRANSACTION_TYPE() == IReportsConstants.insertUpdateFlag)){
					id = 0;
					if (this.reports.getEVENT_ID() > 0)
					{
						SQL_UPDATE_STRING = "UPDATE LAU_EVENTS SET EVENT_VERBATIM=?,DISPLAY_NUMBER=?," +
								"ONSET_DATE=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE REPORT_ID=? AND EVENT_ID=?";
						id = template.update(SQL_UPDATE_STRING,getEventParameters());
						log.info("LauMultipleSummarySetDAO LAU_EVENTS update() ID -> " + id);
					}
					else
					{
						SQL_INSERT_STRING = "INSERT INTO LAU_EVENTS(EVENT_VERBATIM,DISPLAY_NUMBER," +
						"ONSET_DATE,UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID,EVENT_ID) VALUES (?,?,?,?,?,?,?)";
						long eventId = CommonDAO.getPrimaryKey(template);
						this.reports.setEVENT_ID(eventId);
						if(this.reports.getREPORT_ID() == 0)	{
							this.reports.setREPORT_ID(reportId);	}
						log.info("Generated Primary Key for EVENT_ID ->" + eventId);
						id = template.update(SQL_INSERT_STRING, getEventParameters());
						log.info("LauMultipleSummarySetDAO LAU_EVENTS insert() ID -> " + id);
					}
				}

				/*INSERT TO LAU_PRODUCTS E.PRODUCT_ID, E.DISPLAY_NUMBER,
				 E.PRODUCT_NAME, E.TRADE_NAME */
				if((this.reports.getPRODUCT_DISPLAY_NUMBER().trim().length() > 0 || 
						this.reports.getPRODUCT_NAME().trim().length() > 0 || this.reports.getTRADE_NAME().trim().length() > 0)
						&& (this.reports.getPRODUCT_TRANSACTION_TYPE() == IReportsConstants.insertUpdateFlag)){

					id = 0;
					if (this.reports.getPRODUCT_ID() > 0)
					{
						SQL_UPDATE_STRING = "UPDATE LAU_PRODUCTS SET DISPLAY_NUMBER=?,PRODUCT_TYPE=?,PRODUCT_NAME=?," +
								"TRADE_NAME=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE REPORT_ID= ? AND PRODUCT_ID=? ";
						id = template.update(SQL_UPDATE_STRING,getProductParameters());
						log.info("LauMultipleSummarySetDAO LAU_PRODUCTS update() ID -> " + id);
					}
					else
					{
						SQL_INSERT_STRING = "INSERT INTO LAU_PRODUCTS (DISPLAY_NUMBER,PRODUCT_TYPE,PRODUCT_NAME,TRADE_NAME,UPDATE_USER_ID," +
						"UPDATE_TIMESTAMP,REPORT_ID,PRODUCT_ID) VALUES (?,?,?,?,?,?,?,?)";
						long productId = CommonDAO.getPrimaryKey(template);
						this.reports.setPRODUCT_ID(productId);
						if(this.reports.getREPORT_ID() == 0)	{
							this.reports.setREPORT_ID(reportId);	}
						log.info("Generated Primary Key for PRODUCT_ID ->" + productId);
						id = template.update(SQL_INSERT_STRING,getProductParameters());
						log.info("LauMultipleSummarySetDAO LAU_PRODUCTS insert() ID -> " + id);
					}
				}

			}

		}	else	{
			log.info(IReportsConstants.LAU_MULTIPLE_SUMMARY_UPDATE_PARAM_NAME + " not found in request");
		}

	}

	public void createBeansFromXml(String xml) throws Exception {

		String mainXmlTag = "ROWSET/ROW";
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauMultipleSummaryDetails.class );

		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "REPORT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_ID", "CONTACT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_LAST_NAME", "CONTACT_LAST_NAME" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_DISPLAY_NUMBER", "CONTACT_DISPLAY_NUMBER" );
		digester.addBeanPropertySetter( mainXmlTag+"/PATIENT_DETAILS_ID", "PATIENT_DETAILS_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_SEX", "PT_SEX" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_INITIALS", "PT_INITIALS" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_LAST_NAME", "PT_LAST_NAME" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_DOB", "PT_DOB" );
		digester.addBeanPropertySetter( mainXmlTag+"/COUNTRY_EVENT_OCCURRED", "COUNTRY_EVENT_OCCURRED" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_ID", "PRODUCT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_TYPE", "PRODUCT_TYPE" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_DISPLAY_NUMBER", "PRODUCT_DISPLAY_NUMBER" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_NAME", "PRODUCT_NAME" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRADE_NAME", "TRADE_NAME" );
		digester.addBeanPropertySetter( mainXmlTag+"/EVENT_ID", "EVENT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/EVENT_VERBATIM", "EVENT_VERBATIM" );
		digester.addBeanPropertySetter( mainXmlTag+"/EVENT_DISPLAY_NUMBER", "EVENT_DISPLAY_NUMBER" );
		digester.addBeanPropertySetter( mainXmlTag+"/ONSET_DATE", "ONSET_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "TRANSACTION_TYPE" );
		digester.addBeanPropertySetter( mainXmlTag+"/PATIENT_TRANSACTION_TYPE", "PATIENT_TRANSACTION_TYPE" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_TRANSACTION_TYPE", "PRODUCT_TRANSACTION_TYPE" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_TRANSACTION_TYPE", "CONTACT_TRANSACTION_TYPE" );
		digester.addBeanPropertySetter( mainXmlTag+"/EVENT_TRANSACTION_TYPE", "EVENT_TRANSACTION_TYPE" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_IS_REPORTER", "CONTACT_IS_REPORTER" );
		
		
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "UPDATE_USER_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "UPDATE_TIMESTAMP" );

		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );
		digester.parse(new StringReader(xml));

	}

	public void addXmlData(LauMultipleSummaryDetails lauMultipleSummaryDetails) {
		this.reports = lauMultipleSummaryDetails;
		log.info(lauMultipleSummaryDetails.toString());
	}

	private Object[] getEventParameters()
	{
		return new Object[]{
			this.reports.getEVENT_VERBATIM(),
			this.reports.getEVENT_DISPLAY_NUMBER(),
			this.reports.getONSET_DATE(),
			userId,dt,
			this.reports.getREPORT_ID(),
			this.reports.getEVENT_ID()
		};
	}

	private Object[] getPatientParameters()
	{
		/**
		 * SQL_UPDATE_STRING = "UPDATE LAU_PRODUCTS SET DISPLAY_NUMBER=?,PRODUCT_NAME=?," +
							"TRADE_NAME=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE REPORT_ID= ? AND PRODUCT_ID=? ";
		SQL_INSERT_STRING = "INSERT INTO LAU_PATIENT_DETAILS (PT_SEX,PT_DOB,PT_INITIALS," +
							"PT_COUNTRY,UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID,PATIENT_DETAILS_ID) VALUES(?,?,?,?,?,?,?,?)";
		 */
		return new Object[]{
			this.reports.getPT_SEX(),
			this.reports.getPT_DOB(),
			this.reports.getPT_INITIALS(),
			userId,dt,
			this.reports.getREPORT_ID(),
			this.reports.getPATIENT_DETAILS_ID()
		};
	}

	private Object[] getContactParameters()
	{
		return new Object[]{
			this.reports.getCONTACT_LAST_NAME(),
			this.reports.getCONTACT_DISPLAY_NUMBER(),
			this.reports.getCONTACT_IS_REPORTER(),
			userId,dt,
			this.reports.getREPORT_ID(),
			this.reports.getCONTACT_ID()
		};
	}

	private Object[] getProductParameters()
	{
		return new Object[]{
			this.reports.getPRODUCT_DISPLAY_NUMBER(),
			this.reports.getPRODUCT_TYPE(),
			this.reports.getPRODUCT_NAME(),
			this.reports.getTRADE_NAME(),
			userId,dt,
			this.reports.getREPORT_ID(),
			this.reports.getPRODUCT_ID()
		};
	}

	public void delete(SimpleJdbcTemplate template) throws Exception {
		// TODO Auto-generated method stub

	}

	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void update(SimpleJdbcTemplate template) throws Exception {
		// TODO Auto-generated method stub

	}

}
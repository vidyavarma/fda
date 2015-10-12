package com.nrg.lau.dao;

import java.io.StringReader;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauConPrevMedication;
import com.nrg.lau.commons.IReportNarrativeDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauConPrevMedicationSetDAO implements IReportNarrativeDAO<LauConPrevMedication>{
	
	private static Logger log	= Logger.getLogger(LauConPrevMedicationSetDAO.class);
	private Vector<LauConPrevMedication> reports				= null;
	private LauConPrevMedication conPrevMedication				= null;
	private java.sql.Timestamp dt  = null;  
	private String userId	= "";
	
	protected void finalize() throws Throwable {		
		this.reports.removeAllElements();
		this.reports = null;		
		super.finalize();
	}
	
	
	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, String user, Timestamp dstamp,
			HashMap<String, String> patientMap,  HashMap<String, List<Long>> productMap) throws Exception {
		log.info("save in laucon ------------->");
		userId = user;
		dt = dstamp;
		// Check to make sure that XML is there in Request.
		if (request
				.getParameter(IReportsConstants.LAU_CONMED_PREV_MEDICATION_PARAM_NAME) != null
				&& request
						.getParameter(
								IReportsConstants.LAU_CONMED_PREV_MEDICATION_PARAM_NAME)
						.length() > 0) {

			log.info("LauConPrevMedicationSetDAO save() LAU_CONMED_PREV_MEDICATION_PARAM_NAME -> "
					+ request
							.getParameter(IReportsConstants.LAU_CONMED_PREV_MEDICATION_PARAM_NAME));
			log.info("save in laucon 2------------->");
			// Create LauReports beans from XML Request
			createBeansFromXml(request
					.getParameter(IReportsConstants.LAU_CONMED_PREV_MEDICATION_PARAM_NAME));
			Iterator<LauConPrevMedication> itr = this.reports.iterator();

			for (Map.Entry entry : patientMap.entrySet()) {
				log.info("key,val setReport----> " + entry.getKey() + ","
						+ entry.getValue());
			}
			log.info("save in laucon 3------------->");
			// Report Id is 0 it's UPDATE else a new INSERT
			while (itr.hasNext()) {
				log.info("save in laucon 4------------->");
				
				LauConPrevMedication lauConPrevMedication = (LauConPrevMedication) itr
						.next();
				log.info("itr.hasNext() -> " + lauConPrevMedication.toString());
				this.conPrevMedication = null;
				this.conPrevMedication = lauConPrevMedication;
				String patientDetailsId = this.conPrevMedication.getPatientDetailsID();
				log.info("INTERNAL_LINK_ID()---------->"
						+ lauConPrevMedication.getINTERNAL_LINK_ID());
				if (this.conPrevMedication.getPatientDetailsID().length() == 0) {
					if (lauConPrevMedication.getINTERNAL_LINK_ID() == ""
							|| lauConPrevMedication.getINTERNAL_LINK_ID()
									.equals(null)) {
						patientDetailsId = "";
					} else {
						if (!patientMap.isEmpty() && patientMap != null) {
							patientDetailsId = (String) patientMap
									.get(lauConPrevMedication
											.getINTERNAL_LINK_ID().trim());
						}
					}
					this.conPrevMedication.setPatientDetailsID(patientDetailsId);
				}
				log.info("patientDetailsId---------->" + patientDetailsId);
				

				if (conPrevMedication.getProductId() == 0) {
					log.info("save in laucon 5------------->");
					insert(template, reportId, patientDetailsId);
				} else {
					if (conPrevMedication.getTransactionType() == IReportsConstants.deleteFlag) {
						delete(template);
					} else
						update(template);
				}
			}// end of while(itr.hasNext())
		}
		log.info("<------------- save in laucon ");
	}
	
	
	public void insert(SimpleJdbcTemplate template, long reportId, String patientDetailsId)
			throws Exception {
		log.info("insert in laucon ------------->");
		log.info("patientDetailsId ------------->"+patientDetailsId);
		int id = 0;
		long productId = CommonDAO.getPrimaryKey(template);
		conPrevMedication.setProductId(productId);
		conPrevMedication.setPatientDetailsID(patientDetailsId);
		if(conPrevMedication.getReportId() == 0)	{
			conPrevMedication.setReportId(reportId);	}
		
		log.info("Generated Primary Key for PRODUCT_ID ->" + productId);
		id = template.update(SQL_PRODUCT_INSERT_STRING,getProductParameters());
		log.info("LauConPrevMedicationSetDAO insert() for PRODUCT -> " + id);
		
		long doseId = CommonDAO.getPrimaryKey(template);
		conPrevMedication.setDoseId(doseId);
		log.info("Generated Primary Key for DOSE_ID ->" + doseId);
		id = template.update(SQL_DOSE_INSERT_STRING,getDosingParameters());
		log.info("LauConPrevMedicationSetDAO insert() for DOSE -> " + id);
		log.info("<------------- save in laucon ");
	}

	
	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_PRODUCT_UPDATE_STRING,getProductParameters());
		log.info("LauConPrevMedicationSetDAO update() for PRODUCT -> " + id);	
		
		id = template.update(SQL_DOSE_UPDATE_STRING,getDosingParameters());
		log.info("LauConPrevMedicationSetDAO update() for DOSE -> " + id);	
		
	}
	
	private Object[] getProductParameters()
	{
		LauConPrevMedication lauConPrevMedication = this.conPrevMedication; 
		log.info("PatientDetailsID in laucon------->"+lauConPrevMedication.getPatientDetailsID());
		return new Object[]{
			lauConPrevMedication.getDisplayNumber(),
			lauConPrevMedication.getProductType(),
			lauConPrevMedication.getPatientDetailsID(),
			lauConPrevMedication.getProductName(),
			lauConPrevMedication.getTradeName(),
			lauConPrevMedication.getIndicationVerbatim(),
			userId,dt,
			lauConPrevMedication.getReportId(),
			lauConPrevMedication.getProductId()
		};
	}
	private Object[] getDosingParameters()
	{
		LauConPrevMedication lauConPrevMedication = this.conPrevMedication;  
		return new Object[]{
			lauConPrevMedication.getStartDate(),
			lauConPrevMedication.getStopDate(),
			lauConPrevMedication.getDOSE(),
			lauConPrevMedication.getDOSE_UNIT(),
			lauConPrevMedication.getONGOING_FLAG(),
			lauConPrevMedication.getROUTE(),
			lauConPrevMedication.getFREQUENCY(),
			lauConPrevMedication.getDisplayNumber(),
			userId,dt,
			lauConPrevMedication.getProductId(),
			lauConPrevMedication.getDoseId(),
			lauConPrevMedication.getReportId()
			
		};
	}	
	
	public void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag = "ROWSET/ROW";
		reports				= new Vector<LauConPrevMedication>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauConPrevMedication.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_ID", "productId" );
		digester.addBeanPropertySetter( mainXmlTag+"/PATIENT_DETAILS_ID", "patientDetailsID" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRADE_NAME", "tradeName" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISPLAY_NUMBER", "displayNumber" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_TYPE", "productType" );
		digester.addBeanPropertySetter( mainXmlTag+"/INDICATION_VERBATIM", "indicationVerbatim" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		digester.addBeanPropertySetter( mainXmlTag+"/DOSE_ID", "doseId" );
		digester.addBeanPropertySetter( mainXmlTag+"/DOSE", "DOSE" );
		digester.addBeanPropertySetter( mainXmlTag+"/DOSE_UNIT", "DOSE_UNIT" );
		digester.addBeanPropertySetter( mainXmlTag+"/ONGOING_FLAG", "ONGOING_FLAG" );
		digester.addBeanPropertySetter( mainXmlTag+"/FREQUENCY", "FREQUENCY" );
		digester.addBeanPropertySetter( mainXmlTag+"/ROUTE", "ROUTE" );
		digester.addBeanPropertySetter( mainXmlTag+"/START_DATE", "startDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/STOP_DATE", "stopDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/INTERNAL_LINK_ID", "INTERNAL_LINK_ID");
				
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));
		
	}
	
	public void addXmlData(LauConPrevMedication lauConPrevMedication) {
		reports.add(lauConPrevMedication);
		log.info(lauConPrevMedication.toString());
	}	
	
	//SQL Statements
	
	private final String SQL_PRODUCT_INSERT_STRING = "INSERT INTO LAU_PRODUCTS (DISPLAY_NUMBER,PRODUCT_TYPE,PATIENT_DETAILS_ID,PRODUCT_NAME,TRADE_NAME,INDICATION_VERBATIM," +
			"UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID,PRODUCT_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
	
	private final String SQL_PRODUCT_UPDATE_STRING = "UPDATE LAU_PRODUCTS SET DISPLAY_NUMBER=?,PRODUCT_TYPE=?,PATIENT_DETAILS_ID=?,PRODUCT_NAME=?,TRADE_NAME=?,INDICATION_VERBATIM=?," +
			"UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE REPORT_ID=? AND PRODUCT_ID=?";
	
	private final String SQL_DOSE_UPDATE_STRING = "UPDATE LAU_DOSING SET START_DATE=?,STOP_DATE=?,DOSE=?,DOSE_UNIT=?,ONGOING_FLAG=?,ROUTE=?,FREQUENCY=?,DISPLAY_NUMBER=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?" +
			" WHERE PRODUCT_ID=? AND DOSE_ID=? and report_id=?";
	
	private final String SQL_DOSE_INSERT_STRING = "INSERT INTO LAU_DOSING (START_DATE,STOP_DATE,DOSE,DOSE_UNIT,ONGOING_FLAG,ROUTE,FREQUENCY,DISPLAY_NUMBER, UPDATE_USER_ID,UPDATE_TIMESTAMP," +
			"PRODUCT_ID,DOSE_ID,report_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";	

	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_DOSING WHERE PRODUCT_ID = ?",conPrevMedication.getProductId());
		log.info("LauConPrevMedicationSetDAO delete() for DOSE -> " + id);	
		id = template.update("DELETE FROM LAU_PRODUCTS WHERE PRODUCT_ID = ?",conPrevMedication.getProductId());
		log.info("LauConPrevMedicationSetDAO delete() for PRODUCT -> " + id);	
		
		
	}
	
	/**
	  	
	  	LAU_PRODUCTS & LAU_DOSING TABLE
	 
	 */
	
}

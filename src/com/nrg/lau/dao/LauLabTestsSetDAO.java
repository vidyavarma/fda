package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauLabTests;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauLabTestsSetDAO implements IReportChildSetDAO<LauLabTests>{
	
	private static Logger log	= Logger.getLogger(LauLabTestsSetDAO.class);
	private Vector<LauLabTests> reports	= null;
	private LauLabTests labTests 		= null;	
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
		if(request.getParameter(IReportsConstants.LAU_LAB_TESTS_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_LAB_TESTS_PARAM_NAME).length() > 0) {
			
			log.info("LauLabTestsDAO save() LAU_LAB_TESTS_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_LAB_TESTS_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_LAB_TESTS_PARAM_NAME));
			Iterator<LauLabTests> itr = this.reports.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauLabTests lauLabTests = (LauLabTests)itr.next();
				log.info("itr.hasNext() -> " + lauLabTests.toString());
				this.labTests	= null;
				this.labTests	= lauLabTests;
				if(lauLabTests.getLabTestId() == 0)	{
						insert(template,reportId);
				}else   {
					if(lauLabTests.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else	update(template);				
				}
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_LAB_TESTS_PARAM_NAME + " not found in request");
		}
		
	}
	
	
	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {		
		int id = 0;
		long labTestId = CommonDAO.getPrimaryKey(template);
		this.labTests.setLabTestId(labTestId);
		if(this.labTests.getReportId() == 0)	{
			this.labTests.setReportId(reportId);	}
		log.info("Generated Primary Key for LAB_TEST_ID ->" + labTestId);
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("LauLabTestsDAO insert() ID -> " + id);
		
	}

	
	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauLabTestsDAO update() ID -> " + id);	
		
	}
	
	private Object[] getParameters()
	{
		LauLabTests lauLabTests = this.labTests; 
		return new Object[]{
			lauLabTests.getDisplayNbr(),
			lauLabTests.getTestDate(),
			lauLabTests.getTestTime(),
			lauLabTests.getTestResult(),
			lauLabTests.getTestUnits(),
			lauLabTests.getTestUpperBound(),
			lauLabTests.getTestLowerBound(),
			lauLabTests.getTestComments(),
			lauLabTests.getTestNameVerbatim(),
			userId,
			dt,
			lauLabTests.getReportId(),
			lauLabTests.getLabTestId()
		};
	}
	
	
	public void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag = "ROWSET/ROW";
		reports				= new Vector<LauLabTests>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauLabTests.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/LAB_TEST_ID", "labTestId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISPLAY_NUMBER", "displayNbr2" );
		digester.addBeanPropertySetter( mainXmlTag+"/TEST_DATE", "testDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/TEST_TIME", "testTime" );
		digester.addBeanPropertySetter( mainXmlTag+"/TEST_RESULT", "testResult" );
		digester.addBeanPropertySetter( mainXmlTag+"/TEST_UNITS", "testUnits" );		
		digester.addBeanPropertySetter( mainXmlTag+"/TEST_UPPER_BOUND", "testUpperBound" );
		digester.addBeanPropertySetter( mainXmlTag+"/TEST_LOWER_BOUND", "testLowerBound" );
		digester.addBeanPropertySetter( mainXmlTag+"/TEST_COMMENTS", "testComments" );
		digester.addBeanPropertySetter( mainXmlTag+"/TEST_NAME_VERBATIM", "testNameVerbatim" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "updateUserId" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		//digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "updateTimeStamp" );
				
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));
		
	}
	
	public void addXmlData(LauLabTests lauLabTests) {
		reports.add(lauLabTests);
		log.info(lauLabTests.toString());
	}	
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_LAB_TESTS SET DISPLAY_NUMBER =?,TEST_DATE=?,TEST_TIME=?," +
		"TEST_RESULT=?,TEST_UNITS=?,TEST_UPPER_BOUND=?,TEST_LOWER_BOUND=?,TEST_COMMENTS=?,TEST_NAME_VERBATIM=?," +
		"UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE REPORT_ID=? AND LAB_TEST_ID=?";
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_LAB_TESTS (DISPLAY_NUMBER ,TEST_DATE,TEST_TIME," +
		"TEST_RESULT,TEST_UNITS,TEST_UPPER_BOUND,TEST_LOWER_BOUND,TEST_COMMENTS,TEST_NAME_VERBATIM,UPDATE_USER_ID," +
		"UPDATE_TIMESTAMP,REPORT_ID,LAB_TEST_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_LAB_TESTS WHERE LAB_TEST_ID = ?",
				new Object[]{this.labTests.getLabTestId()});
		log.info("LauLabTestsSetDAO delete() ID -> " + id);			
	}
	
	/**
	 	LAU_LAB_TESTS
	 	
	 	LAB_TEST_ID NUMBER(22 , 0) NOT NULL,
		REPORT_ID NUMBER(22 , 0) NOT NULL,
		DISPLAY_NUMBER  NUMBER(6 , 0),
		TEST_DATE VARCHAR2(24),
		TEST_TIME VARCHAR2(6),
		TEST_RESULT VARCHAR2(500),
		TEST_UNITS VARCHAR2(100),
		TEST_UPPER_BOUND VARCHAR2(100),
		TEST_LOWER_BOUND VARCHAR2(100),
		TEST_COMMENTS VARCHAR2(4000),
		TEST_NAME_VERBATIM VARCHAR2(500),
		UPDATE_USER_ID VARCHAR2(300) NOT NULL,
		UPDATE_TIMESTAMP date NOT NULL	 	
	 */

}

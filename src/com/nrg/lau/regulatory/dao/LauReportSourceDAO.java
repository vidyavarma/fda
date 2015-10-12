package com.nrg.lau.regulatory.dao;

import java.io.StringReader;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.regulatory.beans.LauReportSource;

public class LauReportSourceDAO implements IReportChildSetDAO<LauReportSource> {

	private static Logger log	= Logger.getLogger(LauReportSourceDAO.class);
	private Vector<LauReportSource> reports		= null;
	private LauReportSource RepSource 	= null;	
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
		if(request.getParameter(IReportsConstants.LAU_REPORT_SOURCES_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_REPORT_SOURCES_PARAM_NAME).length() > 0 ) {
			
			log.info("LauReportSourceDAO save() LAU_REPORT_SOURCES_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_REPORT_SOURCES_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_REPORT_SOURCES_PARAM_NAME));
			Iterator<LauReportSource> itr = this.reports.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauReportSource lauReportSource = (LauReportSource)itr.next();
				log.info("itr.hasNext() -> " + lauReportSource.toString());
				this.RepSource	= null;
				this.RepSource	= lauReportSource;
				
				if(lauReportSource.getRepSourceID() == 0)	{
						insert(template,reportId);
				}else  {
					if(lauReportSource.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else	update(template);				
				}
				
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_REPORT_SOURCES_PARAM_NAME + " not found in request");
		}
				
	}
	
	
	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {
		int id = 0;
		long repsourceId = CommonDAO.getPrimaryKey(template);
		this.RepSource.setRepSourceID(repsourceId);
		if(this.RepSource.getReportId() == 0)	{
			this.RepSource.setReportId(reportId);	}
		log.info("Generated Primary Key for RepSource_ID ->" + repsourceId);
		id = template.update(SQL_INSERT_STRING,getInsertParameters(template));
		log.info("LauRepSourceDAO insert() ID -> " + id);
	}

	
	public void update(SimpleJdbcTemplate template) throws Exception {
		log.info("Entered Update");
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauRepSourceDAO update() ID -> " + id);	
		
	}
	
	private Object[] getParameters()
	{
		LauReportSource lauRepSource = this.RepSource; 
		return new Object[]{
			  lauRepSource.getRepSourceType(),
			  lauRepSource.getOtherSourceDesc(),
			  userId,
			  dt,
			  lauRepSource.getReportId(),
			  lauRepSource.getRepSourceID()

		};
	}
	private Object[] getInsertParameters(SimpleJdbcTemplate template)
	{
		LauReportSource lauRepSource = this.RepSource; 
		return new Object[]{
				  lauRepSource.getRepSourceType(),
				  lauRepSource.getOtherSourceDesc(),
				  userId,
				  dt,
				  lauRepSource.getReportId(),
				  lauRepSource.getRepSourceID()

		};
	}	
	
	public void createBeansFromXml(String xml) throws Exception {
		
		reports				= new Vector<LauReportSource>();
		Digester digester	= new Digester();
		digester.push(this);
		String mainXmlTag = "ROWSET/ROW";
		digester.addObjectCreate( mainXmlTag, LauReportSource.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/LAU_REPORT_SOURCE_ID", "repSourceID" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_SOURCE_TYPE", "repSourceType" );
		digester.addBeanPropertySetter( mainXmlTag+"/OTHER_SOURCE_DESCRIPTION", "otherSourceDesc" );
		digester.addBeanPropertySetter( mainXmlTag+"/INTERNAL_LINK_ID", "INTERNAL_LINK_ID");
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
			
		//digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "updateTimeStamp" );
				
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );	
		digester.parse(new StringReader(xml));
		
	}
	
	public void addXmlData(LauReportSource lauRepSources) {
		reports.add(lauRepSources);
		
	}
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_REPORT_SOURCES SET REPORT_SOURCE_TYPE=?,OTHER_SOURCE_DESCRIPTION=?," +
		"UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE REPORT_ID=? AND LAU_REPORT_SOURCE_ID=?";	
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_REPORT_SOURCES(REPORT_SOURCE_TYPE,OTHER_SOURCE_DESCRIPTION," +
			"UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID,LAU_REPORT_SOURCE_ID) "
			+ "VALUES (?,?,?,?,?,?)";
	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update("DELETE FROM LAU_REPORT_SOURCES WHERE LAU_REPORT_SOURCE_ID = ?",
				new Object[]{this.RepSource.getRepSourceID()});
		log.info("LauRepSourceDAO delete() ID -> " + id);		
	}

	
}

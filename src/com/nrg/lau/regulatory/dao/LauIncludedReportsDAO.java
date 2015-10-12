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

public class LauIncludedReportsDAO{
	
	private static Logger log	= Logger.getLogger(LauIncludedReportsDAO.class);
	private Vector<LauIncludedReports> reports		= null;
	private LauIncludedReports includedReports = null;	
	private String userId;
	private Timestamp dt;
	
	public String[] save(HttpServletRequest request, SimpleJdbcTemplate template,
			long generatedLetterId, String user, Timestamp dstamp) throws Exception {
		String[] arrayRepIds = null;
		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_INCLUDED_REPORTS_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_INCLUDED_REPORTS_PARAM_NAME).length() > 0) {
			
			log.info("LauIncludedReportsDAO save() LAU_INCLUDED_REPORTS_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_INCLUDED_REPORTS_PARAM_NAME));
			
			this.userId = user;
			this.dt = dstamp;
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_INCLUDED_REPORTS_PARAM_NAME));
			Iterator<LauIncludedReports> itr = this.reports.iterator();
			
			//LAU_QUESTION_ID is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauIncludedReports lauIncludedReports = (LauIncludedReports)itr.next();
				this.includedReports	= null;
				this.includedReports	= lauIncludedReports;
				arrayRepIds =  insert(template,generatedLetterId);
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_INCLUDED_REPORTS_PARAM_NAME + " not found in request");
		}
		return arrayRepIds;
	}

	public void update(SimpleJdbcTemplate template) throws Exception {
		// TODO Auto-generated method stub		
	}

	public String[] insert(SimpleJdbcTemplate template, long generatedLetterId)
			throws Exception {
		int id = 0;
		this.includedReports.setLAU_GENERATED_LETTER_ID(generatedLetterId);
		// insert 1 or more reports
		String arrRepIds[] = this.includedReports.getREPORT_ID().split(",");
		for( int i = 0; i < arrRepIds.length ; i++)
		{
			log.info("LauIncludedReportsDAO insert Report ID -> " + arrRepIds[i]);
			id = template.update(SQL_INSERT_STRING,getParameters(arrRepIds[i]));
			log.info("LauIncludedReportsDAO insert() ID -> " + id);
		}
		log.info("ReportIDS**************"+arrRepIds);	
		return  arrRepIds;
	}
	
	private Object[] getParameters(String strRepId) {
		
		LauIncludedReports lauIncludedReports = this.includedReports;
				
		return new Object[]{			
			userId,dt,
			lauIncludedReports.getLAU_GENERATED_LETTER_ID(),
			strRepId
		};
	}

	public void createBeansFromXml(String xml) throws Exception {
		String mainXmlTag 	= "ROWSET/ROW";
		reports				= new Vector<LauIncludedReports>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauIncludedReports.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/LAU_GENERATED_LETTER_ID", "LAU_GENERATED_LETTER_ID" );		
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "REPORT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "UPDATE_USER_ID" );	
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "UPDATE_TIMESTAMP" );
				
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));	
		
	}
	
	public void addXmlData(LauIncludedReports lauIncludedReports) {
		reports.add(lauIncludedReports);
	}
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_INCLUDED_REPORTS(UPDATE_USER_ID,UPDATE_TIMESTAMP,LAU_GENERATED_LETTER_ID," +
			"REPORT_ID) VALUES (?,?,?,?)";
	
	/*private final String SQL_UPDATE_STRING = "UPDATE LAU_INCLUDED_REPORTS SET UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?," +
			"LAU_GENERATED_LETTER_ID=? WHERE REPORT_ID= ?";*/


	public void delete(SimpleJdbcTemplate template) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

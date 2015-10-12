package com.nrg.lau.regulatory.dao;

import java.io.StringReader;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.commons.IReportChildRtnSetDAO;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.regulatory.beans.LauLinkedReports;

public class LauLinkedReportsDAO implements IReportChildRtnSetDAO<LauLinkedReports>{
	
	private static Logger log	= Logger.getLogger(LauLinkedReportsDAO.class);
	private Vector<LauLinkedReports> reports		= null;
	private LauLinkedReports linkedReports = null;	
	private String userId;
	private Timestamp dt;
	
	public String save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, String user, Timestamp dstamp) throws Exception {
		
		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_LINKED_REPORT_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_LINKED_REPORT_PARAM_NAME).length() > 0) {
			
			log.info("LauLinkedReportsDAO save() LAU_LINKED_REPORT_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_LINKED_REPORT_PARAM_NAME));
			
			this.userId = user;
			this.dt = dstamp;
			
			//Create LinkedReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_LINKED_REPORT_PARAM_NAME));
			Iterator<LauLinkedReports> itr = this.reports.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauLinkedReports lauLinkedReports = (LauLinkedReports)itr.next();
				log.info("itr.hasNext() -> " + lauLinkedReports.toString());
				this.linkedReports	= null;
				this.linkedReports	= lauLinkedReports;
				if(linkedReports.getLINKED_REPORT_ID() == 0	)	{
						insert(template,reportId);
				}else {
					update(template);				
				}
			}//end of while(itr.hasNext())
			
		}	else	{
			log.info(IReportsConstants.LAU_LINKED_REPORT_PARAM_NAME + " not found in request");
		}
		return Long.toString(this.linkedReports.getLINKED_REPORT_ID());
	}

	@Override
	public void update(SimpleJdbcTemplate template) throws Exception {
		
		int id = 0, id1=0, id3=0;
		
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauLinkedReportsDAO update() ID -> " + id);
		String SQL_LAU_REPORTS_STRING = "UPDATE LAU_REPORTS SET LINKED_REPORT_ID = ? WHERE REPORT_ID in ("+linkedReports.getLINKED_REPORTS()+")";
		id1 = template.update(SQL_LAU_REPORTS_STRING,
			new Object[]{
				linkedReports.getLINKED_REPORT_ID()
			});
		
		log.info("LAU_REPORTS updated with  LINKED_REPORT_ID -> " + id1);
		
		
		
	}
	
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_LINKED_REPORTS (PRIMARY_REPORT_ID,LINK_METHOD," +
			"LINK_COMMENT,UPDATE_USER_ID,UPDATE_TIMESTAMP,LINKED_REPORT_ID) VALUES (?,?,?,?,?,?)";
	private final String SQL_UPDATE_STRING = "UPDATE LAU_LINKED_REPORTS SET PRIMARY_REPORT_ID=?,LINK_METHOD=?," +
			"LINK_COMMENT=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE LINKED_REPORT_ID=?";
	//private final String SQL_LAU_REPORTS_STRING = "UPDATE LAU_REPORTS SET LINKED_REPORT_ID = ? WHERE LAU_REPORT_ID in (?)";
	
	private Object[] getParameters()
	{
		LauLinkedReports lauLinkedReports = this.linkedReports; 
		return new Object[]{
				lauLinkedReports.getPRIMARY_REPORT_ID(),	
				lauLinkedReports.getLINK_METHOD(),
				lauLinkedReports.getLINK_COMMENT(),				
				userId,
				dt,				
				lauLinkedReports.getLINKED_REPORT_ID()				
		};
	}

	@Override
	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {
		
		int id = 0;
		long linkedReportsId = CommonDAO.getPrimaryKey(template);
		this.linkedReports.setLINKED_REPORT_ID(linkedReportsId);
		log.info("Generated Primary Key for LINKED_REPORT_ID -> " + linkedReportsId);
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("LauLinkedReportsDAO insert() ID -> " + id);
		String SQL_LAU_REPORTS_STRING = "UPDATE LAU_REPORTS SET LINKED_REPORT_ID = ? WHERE REPORT_ID in ("+linkedReports.getLINKED_REPORTS()+")";
		log.info("Update lau report query:"+SQL_LAU_REPORTS_STRING);
		id = template.update(SQL_LAU_REPORTS_STRING,
			new Object[]{
				linkedReportsId
			});
		
		log.info("LAU_REPORTS updated with  LINKED_REPORT_ID -> " + id);
		
	}

	@Override
	public void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag = "ROWSET/ROW";
		reports				= new Vector<LauLinkedReports>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauLinkedReports.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/LINKED_REPORT_ID", "LINKED_REPORT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRIMARY_REPORT_ID", "PRIMARY_REPORT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/LINK_METHOD", "LINK_METHOD" );
		digester.addBeanPropertySetter( mainXmlTag+"/LINK_COMMENT", "LINK_COMMENT" );
		digester.addBeanPropertySetter( mainXmlTag+"/REMOVED", "REMOVED" );
		digester.addBeanPropertySetter( mainXmlTag+"/LINKED_REPORTS", "LINKED_REPORTS" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "UPDATE_USER_ID" );		
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "UPDATE_TIMESTAMP" );
								
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));	
		
	}
	
	public void addXmlData(LauLinkedReports lauLinkedReports) {
		reports.add(lauLinkedReports);
		log.info(lauLinkedReports.toString());
	}

	@Override
	public void delete(SimpleJdbcTemplate template) throws Exception {
		
	}

}

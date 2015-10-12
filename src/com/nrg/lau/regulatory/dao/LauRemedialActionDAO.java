package com.nrg.lau.regulatory.dao;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.dao.CommonDAO;
//import com.nrg.lau.regulatory.beans.LauEvaluationCode;
import com.nrg.lau.regulatory.beans.LauRemedialAction;

public class LauRemedialActionDAO implements IReportChildSetDAO<LauRemedialAction> {

	private static Logger log	= Logger.getLogger(LauRemedialActionDAO.class);
	private Vector<LauRemedialAction> reports		= null;
	private LauRemedialAction remedialAct 	= null;	
	private java.sql.Timestamp dt  = null;	
	private String userId	= "";
	//private long remActID = 0;	
	
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
		if(request.getParameter(IReportsConstants.LAU_REMEDIAL_DETAILS_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_REMEDIAL_DETAILS_PARAM_NAME).length() > 0 ) {
			
			log.info("LauRemedialActionDAO save() LAU_EVALUATION_CODE_PARAM_NAME -----> " 
					+ request.getParameter(IReportsConstants.LAU_REMEDIAL_DETAILS_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_REMEDIAL_DETAILS_PARAM_NAME));
			Iterator<LauRemedialAction> itr = this.reports.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauRemedialAction lauRemd = (LauRemedialAction)itr.next();
				log.info("itr.hasNext() -> " + lauRemd.toString());
				this.remedialAct	= null;
				this.remedialAct	= lauRemd;
				
				if(lauRemd.getRemedialActionID() == 0)	{
						insert(template,reportId);
				}else  {
					if(lauRemd.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else	update(template);				
				}				
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_REMEDIAL_DETAILS_PARAM_NAME + " not found in request");
		}
				
	}
	
	
	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {		
		int id = 0;
		long remedialId = CommonDAO.getPrimaryKey(template);
		this.remedialAct.setRemedialActionID(remedialId);
		if(this.remedialAct.getReportId() == 0)	{
			this.remedialAct.setReportId(reportId);	}
		log.info("Generated Primary Key for EVALCODE_ID ->" + remedialId);
		id = template.update(SQL_INSERT_STRING,getInsertParameters(template));
		log.info("LauEvalCodeDAO insert() ID -> " + id);
	}

	
	public void update(SimpleJdbcTemplate template) throws Exception {
		log.info("Entered Update");
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauEvalCodeDAO update() ID -> " + id);	
		
	}
	
	private Object[] getParameters()
	{		
		LauRemedialAction lauRemdAct = this.remedialAct;	
		 
		return new Object[]{
				lauRemdAct.getRemedialActionType(),
				lauRemdAct.getOtherActionDesc(),				
				userId,
				dt,
				lauRemdAct.getRemedialActionID()
		};
	}
	private Object[] getInsertParameters(SimpleJdbcTemplate template)
	{						
		LauRemedialAction lauRemdAct = this.remedialAct;			
		return new Object[]{
				lauRemdAct.getRemedialActionID(),
				lauRemdAct.getReportId(),
				lauRemdAct.getRemedialActionType(),				  
				lauRemdAct.getOtherActionDesc(),
				userId,
				dt
		};		
	}	
	
	public void createBeansFromXml(String xml) throws Exception {
		
		reports				= new Vector<LauRemedialAction>();
		Digester digester	= new Digester();
		digester.push(this);
		String mainXmlTag = "ROWSET/ROW";
		digester.addObjectCreate( mainXmlTag, LauRemedialAction.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/REMEDIAL_ACTION_ID", "remedialActionID" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISPLAY_NUMBER", "displayNum" );
		digester.addBeanPropertySetter( mainXmlTag+"/REMEDIAL_ACTION_TYPE", "remedialActionType" );		
		digester.addBeanPropertySetter( mainXmlTag+"/OTHER_ACTION_DESCRIPTION", "otherActionDesc" );		
		digester.addBeanPropertySetter( mainXmlTag+"/INTERNAL_LINK_ID", "INTERNAL_LINK_ID");
		digester.addBeanPropertySetter( mainXmlTag+"/mx_internal_uid", "mxInternalID");
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );	
		digester.parse(new StringReader(xml));
		
	}
	
	public void addXmlData(LauRemedialAction lauRemdActn) {
		reports.add(lauRemdActn);
		
	}
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_REMEDIAL_ACTIONS SET REMEDIAL_ACTION_TYPE=?,OTHER_ACTION_DESCRIPTION=?," +
		"UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE REMEDIAL_ACTION_ID=?";	
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_REMEDIAL_ACTIONS(REMEDIAL_ACTION_ID,REPORT_ID,REMEDIAL_ACTION_TYPE, "
			+ "OTHER_ACTION_DESCRIPTION, UPDATE_USER_ID,UPDATE_TIMESTAMP) VALUES (?,?,?,?,?,?)";
	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update("DELETE FROM LAU_REMEDIAL_ACTIONS WHERE REMEDIAL_ACTION_ID = ?",
				new Object[]{this.remedialAct.getRemedialActionID()});
		log.info("LauRemedialActionDAO delete() ID -> " + id);		
	}

	
}

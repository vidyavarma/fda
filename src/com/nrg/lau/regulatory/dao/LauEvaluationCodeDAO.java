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
import com.nrg.lau.regulatory.beans.LauEvaluationCode;

public class LauEvaluationCodeDAO implements IReportChildSetDAO<LauEvaluationCode> {

	private static Logger log	= Logger.getLogger(LauEvaluationCodeDAO.class);
	private Vector<LauEvaluationCode> reports		= null;
	private LauEvaluationCode EvalCode 	= null;	
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	private long evaluationID = 0;	
	
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
		if(request.getParameter(IReportsConstants.LAU_REPORT_EVAL_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_REPORT_EVAL_PARAM_NAME).length() > 0 ) {
			
			log.info("LauEvaluationCodeDAO save() LAU_EVALUATION_CODE_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_REPORT_EVAL_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_REPORT_EVAL_PARAM_NAME));
			Iterator<LauEvaluationCode> itr = this.reports.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauEvaluationCode lauEval = (LauEvaluationCode)itr.next();
				log.info("itr.hasNext() -> " + lauEval.toString());
				this.EvalCode	= null;
				this.EvalCode	= lauEval;
				
				if(lauEval.getEvalCodeID() == 0)	{
						insert(template,reportId);
				}else  {
					if(lauEval.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else	update(template);				
				}
				evaluationID = lauEval.getEvalCodeID();
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_REPORT_EVAL_PARAM_NAME + " not found in request");
		}
				
	}
	
	
	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {
		int id = 0;
		long evalId = CommonDAO.getPrimaryKey(template);
		this.EvalCode.setEvalCodeID(evalId);
		if(this.EvalCode.getReportId() == 0)	{
			this.EvalCode.setReportId(reportId);	}
		log.info("Generated Primary Key for EVALCODE_ID ->" + evalId);
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
		LauEvaluationCode lauEvalCode = this.EvalCode; 
		return new Object[]{
			  lauEvalCode.getEvalCodeType(),
			  lauEvalCode.getCodeSource(),
			  lauEvalCode.getEvalCode(),
			  userId,
			  dt,
			  lauEvalCode.getReportId(),
			  lauEvalCode.getEvalCodeID()

		};
	}
	private Object[] getInsertParameters(SimpleJdbcTemplate template)
	{
		LauEvaluationCode lauEvalCode = this.EvalCode; 
		return new Object[]{
				lauEvalCode.getEvalCodeType(),
				  lauEvalCode.getCodeSource(),
				  lauEvalCode.getEvalCode(),
				  userId,
				  dt,
				  lauEvalCode.getReportId(),
				  lauEvalCode.getEvalCodeID()

		};
	}	
	
	public void createBeansFromXml(String xml) throws Exception {
		
		reports				= new Vector<LauEvaluationCode>();
		Digester digester	= new Digester();
		digester.push(this);
		String mainXmlTag = "ROWSET/ROW";
		digester.addObjectCreate( mainXmlTag, LauEvaluationCode.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/EVALUATION_CODE_ID", "evalCodeID" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/EVALUATION_CODE_TYPE", "evalCodeType" );
		digester.addBeanPropertySetter( mainXmlTag+"/CODING_SOURCE", "codeSource" );
		digester.addBeanPropertySetter( mainXmlTag+"/EVALUATION_CODE", "evalCode" );
		digester.addBeanPropertySetter( mainXmlTag+"/INTERNAL_LINK_ID", "INTERNAL_LINK_ID");
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		
		//digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "updateTimeStamp" );
				
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );	
		digester.parse(new StringReader(xml));
		
	}
	
	public void addXmlData(LauEvaluationCode lauEvalCode) {
		reports.add(lauEvalCode);
		
	}
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_EVALUATION_CODING SET EVALUATION_CODE_TYPE=?,CODING_SOURCE=?," +
		"EVALUATION_CODE=?, UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE REPORT_ID=? AND EVALUATION_CODE_ID=?";	
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_EVALUATION_CODING(EVALUATION_CODE_TYPE,CODING_SOURCE,EVALUATION_CODE," +
			"UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID,EVALUATION_CODE_ID) "
			+ "VALUES (?,?,?,?,?,?,?)";
	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update("DELETE FROM LAU_EVALUATION_CODING WHERE EVALUATION_CODE_ID = ?",
				new Object[]{this.EvalCode.getEvalCodeID()});
		log.info("LauEvalCodeDAO delete() ID -> " + id);		
	}

	
}

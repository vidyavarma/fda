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

public class LauIncludedQuestionsDAO implements IReportChildSetDAO<LauIncludedQuestions>{
	
	private static Logger log	= Logger.getLogger(LauIncludedQuestionsDAO.class);
	private Vector<LauIncludedQuestions> reports		= null;
	private LauIncludedQuestions includedQuestions = null;	
	private String userId;
	private Timestamp dt;
	
	@Override
	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long generatedLetterId, String user, Timestamp dstamp) throws Exception {
		
		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_INCLUDED_QUESTIONS_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_INCLUDED_QUESTIONS_PARAM_NAME).length() > 0) {
			
			log.info("LauIncludedQuestionsDAO save() LAU_INCLUDED_QUESTIONS_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_INCLUDED_QUESTIONS_PARAM_NAME));
			
			this.userId = user;
			this.dt = dstamp;
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_INCLUDED_QUESTIONS_PARAM_NAME));
			Iterator<LauIncludedQuestions> itr = this.reports.iterator();
			
			//LAU_QUESTION_ID is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauIncludedQuestions lauIncludedQuestions = (LauIncludedQuestions)itr.next();
				this.includedQuestions	= null;
				this.includedQuestions	= lauIncludedQuestions;
				insert(template,generatedLetterId);
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_INCLUDED_QUESTIONS_PARAM_NAME + " not found in request");
		}
		
	}

	@Override
	public void update(SimpleJdbcTemplate template) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void insert(SimpleJdbcTemplate template, long generatedLetterId)
			throws Exception {
		int id = 0;
		this.includedQuestions.setLAU_GENERATED_LETTER_ID(generatedLetterId);
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("LauIncludedQuestionsDAO insert() ID -> " + id);
		
	}
	
	private Object[] getParameters() {
		
		LauIncludedQuestions lauIncludedQuestions = this.includedQuestions;
				
		return new Object[]{
				
			lauIncludedQuestions.getACTUAL_QUESTION_TEXT(),
			userId,dt,
			lauIncludedQuestions.getLAU_GENERATED_LETTER_ID(),
			lauIncludedQuestions.getLAU_QUESTION_ID()
		};
	}

	@Override
	public void createBeansFromXml(String xml) throws Exception {

		String mainXmlTag 	= "ROWSET/ROW";
		reports				= new Vector<LauIncludedQuestions>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauIncludedQuestions.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/LAU_GENERATED_LETTER_ID", "LAU_GENERATED_LETTER_ID" );		
		digester.addBeanPropertySetter( mainXmlTag+"/LAU_QUESTION_ID", "LAU_QUESTION_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/ACTUAL_QUESTION_TEXT", "ACTUAL_QUESTION_TEXT" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "UPDATE_USER_ID" );	
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "UPDATE_TIMESTAMP" );
				
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));	
		
	}
	
	public void addXmlData(LauIncludedQuestions lauIncludedQuestions) {
		reports.add(lauIncludedQuestions);
	}
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_INCLUDED_QUESTIONS(ACTUAL_QUESTION_TEXT,UPDATE_USER_ID,UPDATE_TIMESTAMP,LAU_GENERATED_LETTER_ID," +
			"LAU_QUESTION_ID) VALUES (?,?,?,?,?)";
	
	/*private final String SQL_UPDATE_STRING = "UPDATE LAU_INCLUDED_QUESTIONS SET ACTUAL_QUESTION_TEXT=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?," +
			"LAU_GENERATED_LETTER_ID=? WHERE LAU_QUESTION_ID= ?";*/

	@Override
	public void delete(SimpleJdbcTemplate template) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

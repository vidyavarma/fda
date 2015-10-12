package com.nrg.lau.regulatory.dao;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import javax.sql.DataSource;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.regulatory.beans.LauLetterQuestion;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.dao.CommonDAO;

public class LauLetterQuestionsDAO {

	private Vector<LauLetterQuestion> templates	= null;
	private LauLetterQuestion letterQuestion	= null;
	private static Logger log	= Logger.getLogger(LauLetterQuestionsDAO.class);
	// PR - 865  added question_name field . by vinay.kumar								
	private final String SQL_UPDATE_STRING = "UPDATE LAU_QUESTIONS SET UPDATE_USER_ID=?," +
			"UPDATE_TIMESTAMP=?,QUESTION_GROUP=?,QUESTION_TYPE=?,QUESTION_TEXT=?,REVIEWER_CODE=?,QUESTION_STATUS=?,EDIT_ALLOWED_AT_RUNTIME=?,QUESTION_NAME=? WHERE LAU_QUESTION_ID=?";
	// PR - 865  added question_name field . by vinay.kumar
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_QUESTIONS(QUESTION_GROUP,QUESTION_TYPE,UPDATE_USER_ID," +
			"UPDATE_TIMESTAMP,QUESTION_TEXT,REVIEWER_CODE,QUESTION_STATUS,EDIT_ALLOWED_AT_RUNTIME,LAU_QUESTION_ID,QUESTION_NAME) VALUES(?,?,?,?,?,?,?,?,?,?)";
	
	public void save(HttpServletRequest request,SimpleJdbcTemplate template,
			DataSource datasource) throws Exception {
		log.info("Entered SAVE!!!");
		String	userId = CommonDAO.getUSERID(request);
		//String userId = "saju";
		log.info("USERID: "+userId);
		java.sql.Timestamp dt = CommonDAO.getTimestamp(template);
		log.info("TimeStamp: "+dt.toString());
		log.info("To check if letterQuestions is null: "+request.getParameter(IReportsConstants.LAU_QUESTIONS_PARAM_NAME));
		
		if(request.getParameter(IReportsConstants.LAU_QUESTIONS_PARAM_NAME) != null &&
				request.getParameter(IReportsConstants.LAU_QUESTIONS_PARAM_NAME).length() > 0) {
		
			log.info("LauUserProcodesDAO save() LAU_QUESTIONS_PARAM_NAME -> "
					+ request.getParameter(IReportsConstants.LAU_QUESTIONS_PARAM_NAME));
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_QUESTIONS_PARAM_NAME));

			Iterator<LauLetterQuestion> itr = this.templates.iterator();
			while(itr.hasNext())	{
				LauLetterQuestion lauLetQuestion = (LauLetterQuestion)itr.next();
				log.info("itr.hasNext() -> " + lauLetQuestion.toString());
				this.letterQuestion	= null;
				this.letterQuestion	= lauLetQuestion;
				log.info("QuestionGrp: "+letterQuestion.getQuestionGroup());
				log.info("Type: "+letterQuestion.getQuestionType());
				log.info("getTransactionType()******"+letterQuestion.getTransactionType());
				
				if(letterQuestion.getTransactionType() == IReportsConstants.deleteFlag){
					log.info("lauLetterQuestionsDAO - save() -> [TRANSACTION_TYPE 0]");
					deleteLetterQuestion(template,userId,dt);
				}	
				else if(letterQuestion.getTransactionType() == 1){
					insertLetterQuestion(SQL_INSERT_STRING,template,userId,dt);
				}
				else{
					log.info("updateLetterQuestion - TRANSACTION_TYPE [2] -> " + letterQuestion.getTransactionType());
					updateLetterQuestion(template,userId,dt);
				}

				
			}//end of while(itr.hasNext())

		}	else {
			log.info(IReportsConstants.LAU_QUESTIONS_PARAM_NAME + " not found in request");
		}

	}
	
	private void deleteLetterQuestion(SimpleJdbcTemplate template,
			String userId, Timestamp dt) {
		int id = 0;
		id = template.update("DELETE FROM LAU_QUESTIONS WHERE LAU_QUESTION_ID = ?", 
			       new Object[]{letterQuestion.getQuestionId()});
		log.info("delete LetterQuestions() ID -> " + id);
		
	}

	
	private void updateLetterQuestion(SimpleJdbcTemplate template,
			String userId, Timestamp dt) {
		int id = 0;
		log.info("Group: "+this.letterQuestion.getQuestionGroup());
		log.info("DT----->"+dt);
		log.info("userid----->"+userId);
		log.info("getReviewerCode()--->"+ this.letterQuestion.getReviewerCode());
		log.info("getquestionType()--->"+this.letterQuestion.getQuestionType());
			
		id = template.update(SQL_UPDATE_STRING,getParameters(letterQuestion,userId,dt));
	
		log.info("LauLetterQuestionsDAO update() ID -> " + id);
		
	}
 
	private Object[] getParameters(LauLetterQuestion letterQuestions, String userId, java.sql.Timestamp dt)
	{	log.info("Entered getParameters");
		
		log.info("Group---->"+letterQuestions.getQuestionGroup());
		log.info("Type---->"+letterQuestions.getQuestionType());
		log.info("Text---->"+letterQuestions.getQuestionText());
		log.info("Id"+userId);
		log.info("DT"+dt);
		log.info("status---->"+letterQuestions.getQuestionStatus());
		log.info("ReviewerCode---->"+letterQuestions.getReviewerCode());
		log.info("Question Name---->"+letterQuestions.getQuestionName());
		log.info("Edit Allowed at runtime----->"+letterQuestions.getEditAllowed());
		return new Object[]{
				userId ,
				dt,
				letterQuestions.getQuestionGroup(),
				letterQuestions.getQuestionType(),
				letterQuestions.getQuestionText(),
				letterQuestions.getReviewerCode(),
				letterQuestions.getQuestionStatus(),
				letterQuestions.getEditAllowed(),
				letterQuestions.getQuestionName(),
				letterQuestions.getQuestionId()
				
		};
		
	}	
	
	private void insertLetterQuestion(String sQL_INSERT_STRING2,
			SimpleJdbcTemplate template, String userId, Timestamp dt) {
		int id;
		long lauQuestionId = CommonDAO.getPrimaryKey(template);
		this.letterQuestion.setQuestionId(lauQuestionId);
		log.info("Generated Primary Key for LAU_QUESTION_ID ->" +this.letterQuestion.getQuestionId());
		id = template.update(SQL_INSERT_STRING,getInsertParameters(template,userId,dt));
		log.info("Letter Questions insert() ID -> " + id);
		
	}

	private Object[] getInsertParameters(SimpleJdbcTemplate template,String userId, java.sql.Timestamp dt) {
		LauLetterQuestion lauLetterQuest = this.letterQuestion;
		log.info("Entered getParameters");
		log.info("ID---->"+lauLetterQuest.getQuestionId());
		log.info("Group---->"+lauLetterQuest.getQuestionGroup());
		log.info("Type---->"+lauLetterQuest.getQuestionType());
		log.info("Text---->"+lauLetterQuest.getQuestionText());
		log.info("Id"+userId);
		log.info("DT"+dt);
		log.info("status---->"+lauLetterQuest.getQuestionStatus());
		log.info("ReviewerCode---->"+lauLetterQuest.getReviewerCode());
		log.info("Edit Allowed at runtime----->"+lauLetterQuest.getEditAllowed());
		log.info("Question Name----->"+lauLetterQuest.getQuestionName());
		return new Object[]{
				lauLetterQuest.getQuestionGroup(),
				lauLetterQuest.getQuestionType(),
				userId ,
				dt,
				lauLetterQuest.getQuestionText(),
				lauLetterQuest.getReviewerCode(),
				lauLetterQuest.getQuestionStatus(),
				lauLetterQuest.getEditAllowed(),
				lauLetterQuest.getQuestionId(),
				lauLetterQuest.getQuestionName()
	};
}
	
	
	
	private void createBeansFromXml(String xml) throws Exception {

		templates			= new Vector<LauLetterQuestion>();
		String mainXmlTag 	= "ROWSET/ROW";
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauLetterQuestion.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/LAU_QUESTION_ID", "questionId" );
		digester.addBeanPropertySetter( mainXmlTag+"/QUESTION_GROUP", "questionGroup" );
		digester.addBeanPropertySetter( mainXmlTag+"/QUESTION_TYPE", "questionType" );
		digester.addBeanPropertySetter( mainXmlTag+"/QUESTION_TEXT", "questionText" );
		digester.addBeanPropertySetter( mainXmlTag+"/REVIEWER_CODE", "reviewerCode" );
		digester.addBeanPropertySetter( mainXmlTag+"/QUESTION_STATUS", "questionStatus" );
		digester.addBeanPropertySetter( mainXmlTag+"/EDIT_ALLOWED_AT_RUNTIME", "editAllowed" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		digester.addBeanPropertySetter( mainXmlTag+"/QUESTION_NAME", "questionName" );
		
		//Move to next LauReportAttachments
		digester.addSetNext( mainXmlTag, "addXmlData" );
		digester.parse(new StringReader(xml));

	}
	
		
	public void addXmlData(LauLetterQuestion lauLetterQuestions) {
		templates.add(lauLetterQuestions);
		log.info(lauLetterQuestions.toString());
	}
	
	
}



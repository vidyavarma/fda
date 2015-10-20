package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class LetterGenerationTransactionMgr  extends TestCase{
	
	private static Logger log	= Logger.getLogger(LetterGenerationTransactionMgr.class);	

	public void testLetterGenerationAction() throws Exception	{
	
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		com.nrg.lau.regulatory.dao.LetterGenerationTransactionMgr lauReportsMgr = (com.nrg.lau.regulatory.dao.LetterGenerationTransactionMgr)ctx.getBean("setLetter");
		setRequestData(request);
		request.setAttribute("lauUsers","unitest");
		log.info("user set 1111");
		lauReportsMgr.insertLetterGenValues(request, response);
		log.info("insert done!");
	
	}
	
	public static void setRequestData(MockHttpServletRequest request)	{
		 request.setParameter("generatedLetterXmlData","<ROWSET>  <ROW>    <ADDRESSEE_COMPANY_NAME/>    <ADDRESSEE_CONTACT_EDITED/>    <ADDRESSEE_EMAIL/>    <ADDRESSEE_FIRST_NAME/>    <ADDRESSEE_LAST_NAME>SMITH</ADDRESSEE_LAST_NAME>    <ADDRESSEE_PHONE_CC/>    <ADDRESSEE_PHONE_EXT/>    <ADDRESSEE_PHONE_NO/>    <ADDRESSEE_TITLE>Dr.</ADDRESSEE_TITLE>    <BATCH_PRINTED/>    <DUE_IN_DAYS>234</DUE_IN_DAYS>    <GENERATION_COMMENT>rfgrgthth  hsdaku</GENERATION_COMMENT>    <LAU_GENERATED_LETTER_ID/>    <LAYOUT_TEMPLATE_ID/>    <LETTER_ADDRESS2/>    <LETTER_ADDRESS3/>    <LETTER_ADDRESS4/>    <LETTER_ADDRESSEE_TYPE/>    <LETTER_COUNTRY/>    <LETTER_POSTAL_CODE/>    <UPDATE_USER_ID/>  </ROW></ROWSET>");
		 request.setParameter("includedQuestionsXmlData","<ROWSET>  <ROW>    <ACTUAL_QUESTION_TEXT>This is question 1</ACTUAL_QUESTION_TEXT>    <LAU_GENERATED_LETTER_ID/>    <LAU_QUESTION_ID>1</LAU_QUESTION_ID>   <mx_internal_uid>96E48B11-E9EA-466A-7574-0658CED617CA</mx_internal_uid>    <UPDATE_USER_ID/>  </ROW>  <ROW>    <ACTUAL_QUESTION_TEXT> saj test This is question 2</ACTUAL_QUESTION_TEXT>    <LAU_GENERATED_LETTER_ID/>    <LAU_QUESTION_ID>2</LAU_QUESTION_ID>    <mx_internal_uid>498502DC-0C3F-A4CC-5540-0658CED7A0E0</mx_internal_uid>    <UPDATE_USER_ID/>  </ROW></ROWSET>");
		 request.setParameter("includedReportsXmlData","<ROWSET>  <ROW>    <LAU_GENERATED_LETTER_ID/>   <REPORT_ID>32</REPORT_ID>   <UPDATE_USER_ID/>  </ROW></ROWSET>");
	}
		
	
}

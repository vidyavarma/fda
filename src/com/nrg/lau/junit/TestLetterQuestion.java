package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.nrg.lau.regulatory.render.SetLetterQuestionsAction;


public class TestLetterQuestion extends TestCase {
	private static Logger log	= Logger.getLogger(TestLetterQuestion.class);
	public void testCodeList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
				
		log.info("xxxxxxxxxxx");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		//String strStart = "<?xml version="1.0"?>";
		String str = "<ROWSET><ROW>" +
				"<LAU_QUESTION_ID>765 </LAU_QUESTION_ID>"+
				"<QUESTION_GROUP> A1 </QUESTION_GROUP>"+
				"<QUESTION_TYPE>Selective</QUESTION_TYPE>"+	
				"<QUESTION_TEXT>Please confirm the model number of the desgin shown here?</QUESTION_TEXT>"+
				"<REVIEWER_CODE> </REVIEWER_CODE>"+
				"<QUESTION_STATUS>A </QUESTION_STATUS>"+
				"<EDIT_ALLOWED_AT_RUNTIME>Y </EDIT_ALLOWED_AT_RUNTIME>" +
				"<TRANSACTION_TYPE>1</TRANSACTION_TYPE>" +
				"</ROW></ROWSET>" ;
						
		request.setParameter("lauQuestionGroupXMLData",str);
			
		SetLetterQuestionsAction testObj1 = (SetLetterQuestionsAction)ctx.getBean("setLetterQuestion");
		try {
			testObj1.render(null, request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
}

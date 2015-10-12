package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.nrg.lau.regulatory.render.GetQuestionGroupListAction;


public class TestQuestionGroupList extends TestCase {
	private static Logger log	= Logger.getLogger(TestQuestionGroupList.class);
	public void testQuestionGroupList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
				
		log.info("xxxxxxxxxxx");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		GetQuestionGroupListAction testObj1 = (GetQuestionGroupListAction)ctx.getBean("getQuestionGroupList");
		try {
			testObj1.render(null, request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
}
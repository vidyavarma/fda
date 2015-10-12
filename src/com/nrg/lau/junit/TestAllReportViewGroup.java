package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.nrg.lau.render.GetAllReportViewGroupsAction;


public class TestAllReportViewGroup extends TestCase {
	private static Logger log	= Logger.getLogger(TestAllReportViewGroup.class);
	public void testQuestionGroupList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");
				
		log.info("xxxxxxxxxxx");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		GetAllReportViewGroupsAction testObj1 = (GetAllReportViewGroupsAction)ctx.getBean("getAllReportViewGroupsAction");
		try {
			testObj1.render(null, request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
}
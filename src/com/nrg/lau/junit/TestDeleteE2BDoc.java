package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.nrg.lau.render.DeleteE2BDocAction;

public class TestDeleteE2BDoc extends TestCase {
	private static Logger log	= Logger.getLogger(TestDeleteE2BDoc.class);
	public void testCodeList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
				
		log.info("xxxxxxxxxxx");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		request.setParameter("docName","VidyaTestDocument11");
		request.setParameter("userId", "SAJU");
				
//		
		DeleteE2BDocAction testObj1 = (DeleteE2BDocAction)ctx.getBean("deleteE2BDocAction");
		try {
			testObj1.render(null, request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
}

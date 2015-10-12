package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.nrg.lau.render.DeleteE2BNodeAction;

public class TestDeleteE2BNode extends TestCase {
	private static Logger log	= Logger.getLogger(TestDeleteE2BNode.class);
	public void testCodeList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
				
		log.info("xxxxxxxxxxx");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setParameter("docName", "VidyaTestDocument11");
		request.setParameter("nodeName","ICHICSR0222");
		request.setParameter("userId", "SAJU");
				
//		
		DeleteE2BNodeAction testObj1 = (DeleteE2BNodeAction)ctx.getBean("deleteE2BNodeAction");
		try {
			testObj1.render(null, request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
}

package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.nrg.lau.render.CopyE2BDocAction;

public class TestCopyE2BDoc extends TestCase {
	private static Logger log	= Logger.getLogger(TestCopyE2BDoc.class);
	public void testCodeList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
				
		log.info("xxxxxxxxxxx");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		request.setParameter("oldDocName","VidyaTestDocument");
		request.setParameter("newDocName","VidyaTestDocument11");
		request.setParameter("userId", "SAJU");
				
//		
		CopyE2BDocAction testObj1 = (CopyE2BDocAction)ctx.getBean("copyE2BDocAction");
		try {
			testObj1.render(null, request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
}

package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.nrg.lau.render.DeleteE2BColumnAction;

public class TestDeleteE2BColumn extends TestCase {
	private static Logger log	= Logger.getLogger(TestDeleteE2BColumn.class);
	public void testCodeList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
				
		log.info("xxxxxxxxxxx");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		request.setParameter("docName","VidyaTestDocument11");
		request.setParameter("nodeName","ICHICSR0222");
		request.setParameter("tagName","\"studyname11111\"");
		//request.setParameter("tagName","receivergivename************");
		request.setParameter("userId", "SAJU");
				
//		
		DeleteE2BColumnAction testObj1 = (DeleteE2BColumnAction)ctx.getBean("deleteE2BColumnAction");
		try {
			testObj1.render(null, request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
}

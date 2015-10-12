package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.nrg.lau.render.CopyE2BNodeAction;

public class TestCopyE2BNode extends TestCase {
	private static Logger log	= Logger.getLogger(TestCopyE2BNode.class);
	public void testCodeList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
				
		log.info("xxxxxxxxxxx");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		request.setParameter("docName","VidyaTestDocument11");
		request.setParameter("oldNodeName","ICHICSR01");
		request.setParameter("newNodeName","ICHICSR0222");
		request.setParameter("userId", "SAJU");
				
//		
		CopyE2BNodeAction testObj1 = (CopyE2BNodeAction)ctx.getBean("copyE2BNodeAction");
		try {
			testObj1.render(null, request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
}

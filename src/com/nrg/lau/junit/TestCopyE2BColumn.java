package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.nrg.lau.render.CopyE2BColumnAction;

public class TestCopyE2BColumn extends TestCase {
	private static Logger log	= Logger.getLogger(TestCopyE2BColumn.class);
	public void testCodeList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
				
		log.info("xxxxxxxxxxx");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setParameter("docName","VidyaTestDocument11");
		request.setParameter("nodeName","ICHICSR0222");
		request.setParameter("oldTagName","\"studyname\"");
		request.setParameter("newTagName","\"studyname11111\"");
		request.setParameter("userId", "SAJU");
				
//		
		CopyE2BColumnAction testObj1 = (CopyE2BColumnAction)ctx.getBean("copyE2BColumnAction");
		try {
			testObj1.render(null, request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
}

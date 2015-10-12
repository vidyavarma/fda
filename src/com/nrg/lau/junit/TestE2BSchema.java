package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.nrg.lau.render.GetCodeListAction;
import com.nrg.lau.render.GetE2bMessageInfoAction;
import com.nrg.lau.render.GetE2BSchemaAction;

public class TestE2BSchema extends TestCase {
	private static Logger log	= Logger.getLogger(TestE2BSchema.class);
	public void testCodeList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
				
		log.info("xxxxxxxxxxx");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		GetE2BSchemaAction testObj1 = (GetE2BSchemaAction)ctx.getBean("getE2bSchemaAction");
		try {
			testObj1.render(null, request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
}

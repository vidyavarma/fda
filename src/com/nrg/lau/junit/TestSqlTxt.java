package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.nrg.lau.render.GetSQLTXTAction;;

public class TestSqlTxt extends TestCase {
	private static Logger log	= Logger.getLogger(TestSqlTxt.class);
	public void testCodeList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
		
		log.info("xxxxxxxxxxx");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		
		request.setParameter("docName", "E2BEXT");
		request.setParameter("nodeName", "PATIENT");
				
		GetSQLTXTAction testObj1 = (GetSQLTXTAction)ctx.getBean("getSQLTXTAction");
		try {
			testObj1.render(null, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
}

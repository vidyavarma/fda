package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.nrg.lau.regulatory.render.GetConMedAction;
import com.nrg.lau.render.GetColumnFunctionAction;
import com.nrg.lau.render.GetSQLTXTAction;;

public class TestConMedAction extends TestCase {
	private static Logger log	= Logger.getLogger(TestConMedAction.class);
	public void testCodeList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
		
		log.info("xxxxxxxxxxx");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		request.setParameter("reportid", "7484637");
		request.setParameter("patientdetid","7484639");// "RECEIVER");
					
		GetConMedAction testObj1 = (GetConMedAction)ctx.getBean("getConMedAction");
		try {
			testObj1.render(null, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
}

//http://nov-bpm1:7001/primo/getColumnFunction.do?tagName=lang&nodeName=ICHICSR03&docName=MarkTest01&removecache=1369314994394
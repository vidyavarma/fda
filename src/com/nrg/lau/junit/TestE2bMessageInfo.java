package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.nrg.lau.render.GetCodeListAction;
import com.nrg.lau.render.GetE2bMessageInfoAction;

public class TestE2bMessageInfo extends TestCase {
	private static Logger log	= Logger.getLogger(TestE2bMessageInfo.class);
	public void testCodeList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
		
		//LauReportsSetTransactionManagerJUnit.runReports(ctx);
		log.info("xxxxxxxxxxx");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		//request.setAttribute("parameter1","ACTIVITY_567");
	request.setParameter("partnerID", "");
	request.setParameter("failureCode", "");
	request.setParameter("startDate", "23-APR-2010");
	request.setParameter("stopDate", "30-OCT-2013");
		//setRequestData(request);
		//GetCodeListAction testObj = (GetCodeListAction)ctx.getBean("getCodeListAction");
		//ModelAndViewRender modelAndView = ModelAndViewRender("GetCodeListAllAction")
		GetE2bMessageInfoAction testObj1 = (GetE2bMessageInfoAction)ctx.getBean("getE2bMessageInfoAction");
		try {
		//	testObj.render(null, request, response);
			testObj1.render(null, request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

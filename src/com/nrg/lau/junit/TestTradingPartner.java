package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.nrg.lau.render.GetPartnerListAction;;
import com.nrg.lau.render.GetTradingPartnerAction;

public class TestTradingPartner extends TestCase {
	private static Logger log	= Logger.getLogger(TestTradingPartner.class);
	public void testList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
		
		//LauReportsSetTransactionManagerJUnit.runReports(ctx);
		log.info("xxxxxxxxxxx");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		//request.setAttribute("parameter1","ACTIVITY_567");
		//setRequestData(request);
		GetTradingPartnerAction testObj = (GetTradingPartnerAction)ctx.getBean("getTradingPartnerAction");
		//ModelAndViewRender modelAndView = ModelAndViewRender("GetCodeListAllAction")
		try {
			testObj.render(null, request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

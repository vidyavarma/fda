package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.nrg.lau.render.GetPartnerListAction;;

public class TestPartnerList extends TestCase {
	private static Logger log	= Logger.getLogger(TestPartnerList.class);
	public void testList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
		
		//LauReportsSetTransactionManagerJUnit.runReports(ctx);
		log.info("xxxxxxxxxxx");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setAttribute("parameter1","ACTIVITY_567");
		//setRequestData(request);
		GetPartnerListAction testObj = (GetPartnerListAction)ctx.getBean("getPartnerListAction");
		//ModelAndViewRender modelAndView = ModelAndViewRender("GetCodeListAllAction")
		try {
			testObj.render(null, request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

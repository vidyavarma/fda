package com.nrg.lau.junit;

import junit.framework.TestCase;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.nrg.lau.render.GetReportListAction;

public class TestReportList extends TestCase {
	
	public void testReportList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");
		
		//LauReportsSetTransactionManagerJUnit.runReports(ctx);
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
	//	request.setAttribute("parameter1"," LAU_CONTACT");
		//setRequestData(request);
		GetReportListAction testObj = (GetReportListAction)ctx.getBean("getReportListAction");
		//ModelAndViewRender modelAndView = ModelAndViewRender("GetCodeListAllAction")
		try {
			testObj.render(null, request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

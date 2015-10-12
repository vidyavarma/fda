package com.nrg.lau.junit;

import junit.framework.TestCase;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.nrg.lau.render.GetCheckedoutReportAction;

public class TestCheckoutList extends TestCase {
	
	public void testReportList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");
		
		//LauReportsSetTransactionManagerJUnit.runReports(ctx);
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
	//	request.setAttribute("parameter1","ACTION_ITEM_STATUS");
		//setRequestData(request);
		GetCheckedoutReportAction testObj = (GetCheckedoutReportAction)ctx.getBean("getCheckedoutReportAction");
		//ModelAndViewRender modelAndView = ModelAndViewRender("GetCodeListAllAction")
		try {
			testObj.render(null, request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

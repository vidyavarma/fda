package com.nrg.lau.junit;

import junit.framework.TestCase;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.nrg.lau.render.GetCodeListAction;

public class TestCodeList extends TestCase {
	
	public void testCodeList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
		
		//LauReportsSetTransactionManagerJUnit.runReports(ctx);
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setAttribute("parameter1","OTHER_SOURCES");
		//setRequestData(request);
		GetCodeListAction testObj = (GetCodeListAction)ctx.getBean("getCodeListAction");
		//ModelAndViewRender modelAndView = ModelAndViewRender("GetCodeListAllAction")
		try {
			testObj.render(null, request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

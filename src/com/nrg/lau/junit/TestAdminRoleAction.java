package com.nrg.lau.junit;

import junit.framework.TestCase;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.nrg.lau.render.GetAdminRoleAction;

public class TestAdminRoleAction extends TestCase {
	
	public void testAdminRoleAction()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");
		
		//LauReportsSetTransactionManagerJUnit.runReports(ctx);
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setAttribute("parameter1","LAU_PATIENT_DETAILS");
		request.setAttribute("parameter2","REPORT_ID");
		request.setParameter("reportid","36");
		//setRequestData(request);
		//GetReportDataAction testObj = (GetReportDataAction)ctx.getBean("getReportData");
		GetAdminRoleAction testObj = (GetAdminRoleAction)ctx.getBean("getAdminRoleAction");
		//ModelAndViewRender modelAndView = ModelAndViewRender("getCodeListAction")
		try {
			testObj.render(null, request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

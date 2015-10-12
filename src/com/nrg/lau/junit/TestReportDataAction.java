package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.nrg.lau.dao.LauActionItemsSetDAO;
import com.nrg.lau.render.GetReportDataAction;

public class TestReportDataAction extends TestCase {
	private static Logger log	= Logger.getLogger(TestReportDataAction.class);
	public void testReportDataAction()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
		
		//LauReportsSetTransactionManagerJUnit.runReports(ctx);
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setAttribute("parameter1","LAU_PREGNANCY_HISTORY");
		request.setAttribute("parameter2","REPORT_ID");
		request.setAttribute("parameter3","REPORT_ID");
	//	request.setAttribute("parameter4","NARRATIVE_TEXT_TYPE = 'COMPANY_NARRATIVE'");
		request.setParameter("reportid","48");
		//setRequestData(request);
		//GetReportDataAction testObj = (GetReportDataAction)ctx.getBean("getReportData");
		GetReportDataAction testObj = (GetReportDataAction)ctx.getBean("getTableDataAction");
		//ModelAndViewRender modelAndView = ModelAndViewRender("getCodeListAction")
		try {
			testObj.render(null, request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}



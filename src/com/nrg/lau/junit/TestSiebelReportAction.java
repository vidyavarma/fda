package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.nrg.lau.render.SetSiebelReportsAction;

public class TestSiebelReportAction  extends TestCase {
	private static Logger log	= Logger.getLogger(TestSiebelReportAction.class);
	
	public void testSiebelReportAction()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
		//https://primodev.biogenidec.com/primo/?userid=RHOLLOWA&activityid=2-2BL4P8&custnum=1002252&conid=1-2GS9YK
		//LauReportsSetTransactionManagerJUnit.runReports(ctx);
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setParameter("userid","RHOLLOWA");
		request.setParameter("activityid","2-2BL4P8");
		request.setParameter("custnum","1002252");
		request.setParameter("conid","1-2GS9YK");
		//setRequestData(request);
		//GetReportDataAction testObj = (GetReportDataAction)ctx.getBean("getReportData");
		log.info("start-1");
		SetSiebelReportsAction testObj = (SetSiebelReportsAction)ctx.getBean("setSiebelReportsAction");
		//ModelAndViewRender modelAndView = ModelAndViewRender("getCodeListAction")
		try {
			testObj.render(null, request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

/*static final String SIEBEL_REQ_USER_ID		= "userid";
static final String SIEBEL_REQ_ACTIVITY_ID	= "activityid";
static final String SIEBEL_REQ_CUST_NO		= "custnum";
static final String SIEBEL_REQ_CON_ID		= "conid";*/



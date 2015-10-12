package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.nrg.lau.regulatory.render.SetUserProcodes;

public class TestUserProcode extends TestCase {
	private static Logger log	= Logger.getLogger(TestUserProcode.class);
	public void testCodeList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
				
		log.info("xxxxxxxxxxx");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		//String xmltag = "<?xml version="1.0"?>";
		
		String str = "<ROWSET><ROW>" +
				"<label>FJI</label>" +
				"<mx_internal_uid>5681387A-0743-6217-53B6-2D2100DEA131</mx_internal_uid>"+
				"<type>CODE</type>" +
				"</ROW><ROW>" +
				"<label>HDD</label>" +
				"<type>CODE</type>" +
				"</ROW><ROW>" +
				"<label>HRX</label>" +
				"<type>CODE</type>" +
				"</ROW><ROW>" +
				"<label>KNQ</label>" +
				"<type>CODE</type>" +
				"</ROW><ROW>" +
				"<label>Annuloplasty Rings</label>" +
				"<type>GRP</type>" +
				"</ROW></ROWSET>";
				

		
		request.setParameter("UserID","saju");
		request.setParameter("lauProdGroupAssignmentXmlData", str);
		
		SetUserProcodes testObj1 = (SetUserProcodes)ctx.getBean("setUserProcodes");
		try {
			testObj1.render(null, request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
}

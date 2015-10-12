package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.nrg.lau.regulatory.render.SetProductGroupingAction;


public class TestProductGrouping extends TestCase {
	private static Logger log	= Logger.getLogger(TestProductGrouping.class);
	public void testCodeList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
				
		log.info("xxxxxxxxxxx");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		//String strStart = "<?xml version="1.0"?>";
		String str = "<ROWSET><ROW>" +
				"<PRODUCT_GROUP>General Surgery Devices - Other</PRODUCT_GROUP>" +
				"<PRODUCT_CODE>OHJ</PRODUCT_CODE>" +
				"<PRODUCT_CODE_TYPE>PMA Exempt</PRODUCT_CODE_TYPE>" +
				"<MEDICAL_SPECIALITY>XXX</MEDICAL_SPECIALITY>" +
				"<DESCRIPTION>LOW ELECTRIC FIELD NON-THERMAL TISSUE ABLATION SYSTEM</DESCRIPTION >" +
				"<TRANSACTION_TYPE>0</TRANSACTION_TYPE>" +
				"</ROW></ROWSET>" ;
						
		request.setParameter("lauProductGroupingXmlData",str);
			
		SetProductGroupingAction testObj1 = (SetProductGroupingAction)ctx.getBean("setProductGrouping");
		try {
			testObj1.render(null, request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
}

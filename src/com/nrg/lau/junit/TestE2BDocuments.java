package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.nrg.lau.render.SetE2BDocAction;

public class TestE2BDocuments extends TestCase {
	private static Logger log	= Logger.getLogger(TestE2BDocuments.class);
	public void testCodeList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
				
		log.info("xxxxxxxxxxx");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		//String strStart = "<?xml version="1.0"?>";
		String str = "<ROWSET><ROW><DOC_NAME>VidyaTestDocument_02</DOC_NAME>" +
				"<DOCUMENT_DESCRIPTION>ICH DTD 2.1 Standard</DOCUMENT_DESCRIPTION>" +
				"<LANGUAGE_CODE>DTD21</LANGUAGE_CODE>" +
				"<TRANSACTION_TYPE>0</TRANSACTION_TYPE></ROW></ROWSET>";
		
		request.setParameter("lauDocXmlData",str);
		
		SetE2BDocAction testObj1 = (SetE2BDocAction)ctx.getBean("setE2bDocAction");
		try {
			testObj1.render(null, request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
}

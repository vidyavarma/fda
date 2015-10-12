package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.nrg.lau.render.SetE2BColumnAction;

public class TestXmlColumns extends TestCase {
	private static Logger log	= Logger.getLogger(TestXmlColumns.class);
	public void testCodeList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
				
		log.info("xxxxxxxxxxx");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		//String strStart = "<?xml version="1.0"?>";
		String str = "<ROWSET><ROW><DOC_NAME>VidyaTestDocument_02</DOC_NAME><NODE_NAME>ICHICSR123</NODE_NAME>" +
				"<XML_TAG_NAME>studyname*******</XML_TAG_NAME>" +
				"<COLUMN_FCT_NAME>STUDY_NAME</COLUMN_FCT_NAME>" +
				"<COL_POSITION>100</COL_POSITION><TRANSACTION_TYPE>1</TRANSACTION_TYPE>" +
				"</ROW></ROWSET>";
		
		request.setParameter("lauColumnXmlData",str);
				
		SetE2BColumnAction testObj1 = (SetE2BColumnAction)ctx.getBean("setE2bColumnAction");
		try {
			testObj1.render(null, request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
}

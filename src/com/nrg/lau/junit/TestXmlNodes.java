package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.nrg.lau.render.SetE2BNodeAction;;

public class TestXmlNodes extends TestCase {
	private static Logger log	= Logger.getLogger(TestXmlNodes.class);
	public void testCodeList()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
				
		log.info("xxxxxxxxxxx");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		//String strStart = "<?xml version="1.0"?>";
		String str = "<ROWSET><ROW><DOC_NAME>VidyaTestDocument_02</DOC_NAME>" +
					"<NODE_NAME>ICHICSR123</NODE_NAME><XML_NODE_NAME>ichicsrTest</XML_NODE_NAME>" +
					"<NODE_TYPE>COMPLEX</NODE_TYPE>" +
					"<SQLTXT>(select  XMLFOREST(x_node_query)  from LAU_REPORTS R1 where R1.report_id= LAU_E2B_OUTBOUND.GET_REPORT_ID)</SQLTXT>" +
					"<POSITION>10</POSITION><NODE_LEVEL>1</NODE_LEVEL><USE_REPORTID>Y</USE_REPORTID>" +
					"<MANDATORY_NODE>Y</MANDATORY_NODE><TRANSACTION_TYPE>0</TRANSACTION_TYPE><UPDATE_USER_ID>SAJU</UPDATE_USER_ID>" +
					"<UPDATE_TIMESTAMP></UPDATE_TIMESTAMP></ROW></ROWSET>" ;
						

		
		request.setParameter("lauNodeXmlData",str);
		
		
		SetE2BNodeAction testObj1 = (SetE2BNodeAction)ctx.getBean("setE2bNodeAction");
		try {
			testObj1.render(null, request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
}

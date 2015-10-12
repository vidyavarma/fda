package com.nrg.lau.junit;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.nrg.lau.regulatory.render.SetRunAdhocQuery;

public class TestRunAdhocQuery extends TestCase {
	private static Logger log	= Logger.getLogger(TestRunAdhocQuery.class);
	public void testAdhocQuery()	{
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
		log.info("Inside AdhocQuery --> ");
		MockHttpServletRequest request 	= new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		String str = "<ROWSET><ROW>    <GROUP_ID>NEW1</GROUP_ID>    <GROUP_NAME/>    <LEVEL>0</LEVEL>    <OPERATOR>ALL</OPERATOR>    <TYPE>GROUP</TYPE>  </ROW>"+
		"  <ROW>    <DB_FIELD_NAME>REPORT_CONTACT_DATE</DB_FIELD_NAME>    <DISPLAY_NUMBER>1</DISPLAY_NUMBER>    <FIELD_ID>101</FIELD_ID>    <LEVEL>1</LEVEL>    <OPERATOR>EQUAL</OPERATOR>"+
		"    <PARENTGROUP>NEW1</PARENTGROUP>    <SELECTED>true</SELECTED>    <TYPE>CONDITION</TYPE>    <VALUE>20131104</VALUE>  </ROW>  <ROW>    <DISPLAY_NUMBER>2</DISPLAY_NUMBER>"+
		"  <GROUP_ID>SUB1</GROUP_ID>    <LEVEL>1</LEVEL>    <OPERATOR>ALL</OPERATOR>    <PARENTGROUP>NEW1</PARENTGROUP>    <SELECTED>true</SELECTED>    <TYPE>SUBGROUP</TYPE>"+
		" </ROW>  <ROW>    <DB_FIELD_NAME>ADVERSE_EVENT_FLAG</DB_FIELD_NAME>    <DISPLAY_NUMBER>3</DISPLAY_NUMBER>    <FIELD_ID>105</FIELD_ID>    <LEVEL>2</LEVEL>    <OPERATOR>true</OPERATOR>"+
		"    <PARENTGROUP>SUB1</PARENTGROUP>    <SELECTED>true</SELECTED>    <TYPE>CONDITION</TYPE>  </ROW></ROWSET>";
				
		request.setParameter("lauAdhocQueryXML", str);		
		SetRunAdhocQuery testObj1 = (SetRunAdhocQuery)ctx.getBean("setRunAdhocQuery");
		
		try {
			testObj1.render(null, request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
}

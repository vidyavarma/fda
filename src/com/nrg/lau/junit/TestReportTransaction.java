package com.nrg.lau.junit;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

public class TestReportTransaction extends TestCase {
	
	public void testReportTransaction()	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");		
		LauReportsSetTransactionMgr.runReports(ctx);		
	}
	
}

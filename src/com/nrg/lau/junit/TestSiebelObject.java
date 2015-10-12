package com.nrg.lau.junit;

import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelBusObject;
import com.siebel.data.SiebelDataBean;

import junit.framework.TestCase;

public class TestSiebelObject extends TestCase {

	public void testSiebelObject() {

		try {

			String connectString = "siebel://watvmcrmtss1:2321/CRM8_TST/SCCObjMgr_enu";
			String searchLoginName = "PRIMOADM";

			SiebelDataBean dataBean = new SiebelDataBean();
			dataBean.login(connectString, searchLoginName, "PRIMOADM");

			
			  SiebelBusObject busObject = dataBean.getBusObject("Contact");
			  SiebelBusComp busComp = busObject.getBusComp("Contact");
			  
			  busComp.setViewMode(3); busComp.clearToQuery();
			  busComp.activateField("First Name");
			 // busComp.activateField("ContactFileName");
			  busComp.activateField("Last Name");// busComp.activateField("Id");
			 // busComp.setSearchSpec("Login Name", searchLoginName);
			  busComp.setSearchSpec("Id", "1-3M1P7F");
			  busComp.executeQuery2(true,true);
			  System.out.println("xxxx");
			  if (busComp.firstRecord()) {
				  System.out.println("yyyy");
			  System.out.println("Contact ID: " + busComp.getFieldValue("Id"));
			  System.out.println("First name: " +
			  busComp.getFieldValue("First Name"));
			  System.out.println("CM Id: " +
			  busComp.getFieldValue("Customer Number"));
			  System.out.println("Last name: " +
			  busComp.getFieldValue("Last Name"));
			  System.out.println(busComp.getFieldValue("Contact FileName"));
			  
			  }
			  
			  busComp.release(); busObject.release(); dataBean.logoff();
			 
		} catch (Exception e) {
			System.out.println("------------11111---");
			e.printStackTrace();
		}

	}
}

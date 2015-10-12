package com.nrg.lau.junit;

import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelBusObject;
import com.siebel.data.SiebelDataBean;

import junit.framework.TestCase;

public class TestSiebelObjectInterface extends TestCase {

	public void testSiebelObject()	{

		try	{

			String connectString	= "siebel://watvmcrmtss1:2321/CRM8_TST/ePharmaObjMgr_enu";
			String searchLoginName	= "PRIMOADM";

			SiebelDataBean dataBean = new SiebelDataBean();
			dataBean.login(connectString, "PRIMOADM", "PRIMOADM");

			/*SiebelBusObject busObject	= dataBean.getBusObject("Contact");
			SiebelBusComp busComp 		= busObject.getBusComp("Contact");

			busComp.setViewMode(3);
			busComp.clearToQuery();
			busComp.activateField("First Name");
			busComp.activateField("Last Name");
			busComp.activateField("Id");
			busComp.setSearchSpec("Login Name", searchLoginName);
			busComp.executeQuery2(true,true);

			if (busComp.firstRecord()) {

				System.out.println("Contact ID: " + busComp.getFieldValue("Id"));
				System.out.println("First name: " + busComp.getFieldValue("First Name"));
				System.out.println("Last name: " + busComp.getFieldValue("Last Name"));

			}

			busComp.release();
			busObject.release();
			dataBean.logoff();
*/
		}catch (Exception e) {
			e.printStackTrace();
		}



	}
}

/**
Saju,

Below is the connectstring to Siebel Test (CRM8T)

SiebelDataBean o_dataBean = new SiebelDataBean();
o_dataBean.login("siebel://watvmcrmtss1:2321/CRM8_TST/ePharmaObjMgr_enu", "PRIMOADM", "PRIMOADM");

Thanks,
Don Francis
CRM Technical Lead
Biogen Idec, Inc.
Phone: 617-914-6454
Ext: 46454
**/
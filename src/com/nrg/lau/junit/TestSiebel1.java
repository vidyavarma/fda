package com.nrg.lau.junit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelBusObject;
import com.siebel.data.SiebelDataBean;

import junit.framework.TestCase;

public class TestSiebel1 extends TestCase{
	

	
	public void testAttachmentName()	{
		try {
			String connectString = "siebel://watvmcrmtss1:2321/CRM8_TST/SCCObjMgr_enu";
			String searchLoginName = "PRIMOADM";
			
			SiebelDataBean dataBean = new SiebelDataBean();
			dataBean.login(connectString, searchLoginName, "PRIMOADM");
			
			SiebelBusObject busObject	= dataBean.getBusObject("Contact");
			SiebelBusComp busComp 		= busObject.getBusComp("Data Entry Attachments BIIB"); 
			busComp.setViewMode(3);
			busComp.clearToQuery();
			busComp.activateField("Contact Id");
			busComp.setSearchSpec("Contact Id", "1-3M1P7F");
			busComp.setSortSpec("Created(DESCENDING)");
			busComp.executeQuery(true);
			if (busComp.firstRecord()) {
				System.out.println(busComp.getFieldValue("ContactFileName"));
				System.out.println(busComp.getFieldValue("Attachment Type BIIB"));
				System.out.println(busComp.getFieldValue("Created By Name BIIB"));
				System.out.println(busComp.getFieldValue("Created"));
				System.out.println(busComp.getFieldValue("Attached By Name BIIB"));
				System.out.println(busComp.getFieldValue("Attached Date BIIB"));
				System.out.println(busComp.getFieldValue("AE Indicator BIIB"));
				while (busComp.nextRecord()){
					System.out.println(busComp.getFieldValue("ContactFileName") + "  :::");
					System.out.println(busComp.getFieldValue("Attachment Type BIIB") + "  :::");
					System.out.println(busComp.getFieldValue("Created By Name BIIB") + "  :::");
					System.out.println(busComp.getFieldValue("Created") + "  :::");
					System.out.println(busComp.getFieldValue("Attached By Name BIIB") + "  :::");
					System.out.println(busComp.getFieldValue("Attached Date BIIB") + "  :::");
					System.out.println(busComp.getFieldValue("AE Indicator BIIB") + "  :::");
				}
				
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testGetFileDetails()	{
		try {

			String connectString = "siebel://watvmcrmtss1:2321/CRM8_TST/SCCObjMgr_enu";
			String searchLoginName = "PRIMOADM";

			SiebelDataBean dataBean = new SiebelDataBean();
			dataBean.login(connectString, searchLoginName, "PRIMOADM");
			
			SiebelBusObject busObject	= dataBean.getBusObject("Contact");
			SiebelBusComp busComp 		= busObject.getBusComp("Data Entry Attachments BIIB"); 
			
			busComp.setViewMode(3);
			busComp.clearToQuery();
			busComp.activateField("Contact Id");
			busComp.setSearchSpec("Contact Id", "1-3M1P7F");
			busComp.setSearchSpec("ContactFileName", "Test2");
			busComp.setSortSpec("Created(DESCENDING)");
			busComp.executeQuery(true);
			if (busComp.firstRecord()) {
				
				System.out.println("***************** Attachment *****************");
				
				System.out.println(busComp.invokeMethod("GetFile", new String[]{"ContactFileName"}));				
				System.out.println(busComp.getFieldValue("ContactFileName"));
				System.out.println(busComp.getFieldValue("Attachment Type BIIB"));
				System.out.println(busComp.getFieldValue("Created By Name BIIB"));
				System.out.println(busComp.getFieldValue("Created"));
				System.out.println(busComp.getFieldValue("Attached By Name BIIB"));
				System.out.println(busComp.getFieldValue("Attached Date BIIB"));
				System.out.println(busComp.getFieldValue("AE Indicator BIIB"));
				FileInputStream inputFileInputStream = null;
			    int bytesRead       = 0;
			    byte[] binaryBuffer;

/*			    File outputTextFile2  = new File("\\\\watvmcrmtss1\\sba80_TEMP\\watvmcrmtss1_6076_7208_0_Test2.TIF");
			    inputFileInputStream = new FileInputStream(outputTextFile2);
			    binaryBuffer   = new byte[1024];
			    int i     = 0;
			    while ((bytesRead = inputFileInputStream.read(binaryBuffer)) != -1) {
			     System.out.println("bytesRead   ->  "  + i );
			     i++;
			    }*/
				File outputTextFile2  = new File("\\\\watvmcrmtss1\\sba80_TEMP\\watvmcrmtss1_6076_7208_0_Test2.TIF");
			    File destinationFile = new File("c:\\Test2.TIF");

			    inputFileInputStream = new FileInputStream(outputTextFile2);
			    FileOutputStream fos = new FileOutputStream(destinationFile);
			         
			    
			    
			    int in     = 0;
			    binaryBuffer   = new byte[1024];
			    int i = inputFileInputStream.read(binaryBuffer);
			    while (i != -1) {
			           fos.write(binaryBuffer, 0, i);
			           i = inputFileInputStream.read(binaryBuffer);
			           System.out.println("bytesRead   ->  "  + in );
			     in++;
			         }
				System.out.println("***************** Patient Information *****************");
		/*		
				System.out.println(busComp.getFieldValue("M/M"));
				System.out.println(busComp.getFieldValue("First Name"));
				System.out.println(busComp.getFieldValue("Middle Name"));
				System.out.println(busComp.getFieldValue("Last Name"));
				System.out.println(busComp.getFieldValue("M/F"));
				System.out.println(busComp.getFieldValue("Street Address No Assoc BIIB"));
				System.out.println(busComp.getFieldValue("City No Assoc BIIB"));
				System.out.println(busComp.getFieldValue("State No Assoc BIIB"));
				System.out.println(busComp.getFieldValue("Postal Code No Assoc BIIB"));
				System.out.println(busComp.getFieldValue("Country No Assoc BIIB"));
				System.out.println(busComp.getFieldValue("PhoneNumber BIIB"));
				System.out.println(busComp.getFieldValue("Email Address"));
				System.out.println(busComp.getFieldValue("Birth Date"));
				System.out.println(busComp.getFieldValue("Touch Id SVF BIIB"));
				System.out.println(busComp.getFieldValue("Tygris Status BIIB"));
				System.out.println(busComp.getFieldValue("Tygris Status Date BIIB"));
				System.out.println(busComp.getFieldValue("Customer Number"));
				System.out.println(busComp.getFieldValue("ActivityId from the URL"));
				System.out.println(busComp.getFieldValue("ContactId from the URL"));
				
				System.out.println("***************** Product Information *****************");
				
				System.out.println(busComp.getFieldValue("Therapy BIIB (MVG) - Therapy Stage (Field)"));
				System.out.println(busComp.getFieldValue("Therapy BIIB (MVG) - Date Start Drug (Field)"));
				System.out.println(busComp.getFieldValue("Therapy BIIB (MVG) - Date End Drug (Field)"));
				
				System.out.println("***************** Prescriber Information (PRIMO Contacts) *****************");
				
				System.out.println(busComp.getFieldValue("M/M (using the Prescribing Physician from Affiliation)"));				
				System.out.println(busComp.getFieldValue("First Name (using the Prescribing Physician from Affiliation)"));
				System.out.println(busComp.getFieldValue("ContactFileName"));
				System.out.println(busComp.getFieldValue("ContactFileName"));
				System.out.println(busComp.getFieldValue("ContactFileName"));
				System.out.println(busComp.getFieldValue("ContactFileName"));
				System.out.println(busComp.getFieldValue("ContactFileName"));
				System.out.println(busComp.getFieldValue("ContactFileName"));
				System.out.println(busComp.getFieldValue("ContactFileName"));
				System.out.println(busComp.getFieldValue("ContactFileName"));
				System.out.println(busComp.getFieldValue("ContactFileName"));
				System.out.println(busComp.getFieldValue("ContactFileName"));
				System.out.println(busComp.getFieldValue("ContactFileName"));
				System.out.println(busComp.getFieldValue("ContactFileName"));
				System.out.println(busComp.getFieldValue("ContactFileName"));
				System.out.println(busComp.getFieldValue("ContactFileName"));
				System.out.println(busComp.getFieldValue("ContactFileName"));
				*/
			}			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}

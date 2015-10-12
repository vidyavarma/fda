package com.nrg.lau.beans;

import java.io.Serializable;
import org.apache.log4j.Logger;

public class LauTradingPartner implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log	= Logger.getLogger(LauTradingPartner.class);
	private long tradingPartnerId = 0;
	public long getTradingPartnerId() {
		return tradingPartnerId;
	}


	public void setTradingPartnerId(long tradingPartnerId) {
		this.tradingPartnerId = tradingPartnerId;
	}


	public String getE2bPartnerId() {
		return e2bPartnerId;
	}


	public void setE2bPartnerId(String e2bPartnerId) {
		this.e2bPartnerId = e2bPartnerId;
	}


	public String getE2bDocName() {
		return e2bDocName;
	}


	public void setE2bDocName(String e2bDocName) {
		this.e2bDocName = e2bDocName;
	}


	public String getE2bDocNameFU() {
		return e2bDocNameFU;
	}


	public void setE2bDocNameFU(String e2bDocNameFU) {
		this.e2bDocNameFU = e2bDocNameFU;
	}

	public String getE2bDocNameOutbound() {
		return e2bDocNameOutbound;
	}


	public void setE2bDocNameOutbound(String e2bDocNameOutbound) {
		this.e2bDocNameOutbound = e2bDocNameOutbound;
	}


	public String getE2bDocNameFUOutbound() {
		return e2bDocNameFUOutbound;
	}


	public void setE2bDocNameFUOutbound(String e2bDocNameFUOutbound) {
		this.e2bDocNameFUOutbound = e2bDocNameFUOutbound;
	}


	
	public String getE2bHost() {
		return e2bHost;
	}


	public void setE2bHost(String e2bHost) {
		this.e2bHost = e2bHost;
	}


	public String getE2bImportType() {
		return e2bImportType;
	}


	public void setE2bImportType(String e2bImportType) {
		this.e2bImportType = e2bImportType;
	}


	public String getAckType() {
		return ackType;
	}


	public void setAckType(String ackType) {
		this.ackType = ackType;
	}


	public String getAckTiming() {
		return ackTiming;
	}


	public void setAckTiming(String ackTiming) {
		this.ackTiming = ackTiming;
	}


	public String getE2bUseCaseId() {
		return e2bUseCaseId;
	}


	public void setE2bUseCaseId(String e2bUseCaseId) {
		this.e2bUseCaseId = e2bUseCaseId;
	}


	public String getXmlDecLine() {
		return xmlDecLine;
	}


	public void setXmlDecLine(String xmlDecLine) {
		this.xmlDecLine = xmlDecLine;
	}


	public String getXmlDocTypeLine() {
		return xmlDocTypeLine;
	}


	public void setXmlDocTypeLine(String xmlDocTypeLine) {
		this.xmlDocTypeLine = xmlDocTypeLine;
	}


	public String getAckDecLine() {
		return ackDecLine;
	}


	public void setAckDecLine(String ackDecLine) {
		this.ackDecLine = ackDecLine;
	}


	public String getAckDocTypeLine() {
		return ackDocTypeLine;
	}


	public void setAckDocTypeLine(String ackDocTypeLine) {
		this.ackDocTypeLine = ackDocTypeLine;
	}


	public String getUserGrp() {
		return userGrp;
	}


	public void setUserGrp(String userGrp) {
		this.userGrp = userGrp;
	}


	public String getInDir() {
		return inDir;
	}


	public void setInDir(String inDir) {
		this.inDir = inDir;
	}


	public String getOutDir() {
		return outDir;
	}


	public void setOutDir(String outDir) {
		this.outDir = outDir;
	}


	public String getpType() {
		return pType;
	}


	public void setpType(String pType) {
		this.pType = pType;
	}


	public String getpOrg() {
		return pOrg;
	}


	public void setpOrg(String pOrg) {
		this.pOrg = pOrg;
	}


	public String getpDept() {
		return pDept;
	}


	public void setpDept(String pDept) {
		this.pDept = pDept;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getfName() {
		return fName;
	}


	public void setfName(String fName) {
		this.fName = fName;
	}


	public String getmName() {
		return mName;
	}


	public void setmName(String mName) {
		this.mName = mName;
	}


	public String getlName() {
		return lName;
	}


	public void setlName(String lName) {
		this.lName = lName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getPostal() {
		return postal;
	}


	public void setPostal(String postal) {
		this.postal = postal;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getPhoneExt() {
		return phoneExt;
	}


	public void setPhoneExt(String phoneExt) {
		this.phoneExt = phoneExt;
	}


	public String getCountryCode() {
		return countryCode;
	}


	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getFax() {
		return fax;
	}


	public void setFax(String fax) {
		this.fax = fax;
	}


	public String getFaxExt() {
		return faxExt;
	}


	public void setFaxExt(String faxExt) {
		this.faxExt = faxExt;
	}


	public String getFaxCode() {
		return faxCode;
	}


	public void setFaxCode(String faxCode) {
		this.faxCode = faxCode;
	}


	public String getUpdateUserId() {
		return updateUserId;
	}


	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}


	public Object getUpdateTimeStamp() {
		return updateTimeStamp;
	}


	public void setUpdateTimeStamp(Object updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}


	public long getTransactionType() {
		return transactionType;
	}


	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}


	private String e2bPartnerId = "";
	private String e2bDocName = "";
	private String e2bDocNameFU = "";
	private String e2bDocNameOutbound = "";
	private String e2bDocNameFUOutbound = "";
	private String e2bHost = "";
	private String e2bImportType = "";
	private String ackType = "";
	private String ackTiming = "";
	private String e2bUseCaseId = "";
	private String xmlDecLine = "";
	private String xmlDocTypeLine = "";
	private String ackDecLine = "";
	private String ackDocTypeLine = "";
	private String userGrp = "";
	private String inDir = "";
	private String outDir = "";
	
	private String pType = "";
	private String pOrg = "";
	private String pDept = "";
	private String title ="";
	private String fName = "";
	private String mName = "";
	private String lName = "";
	private String address = "";
	private String city = "";
	private String state = "";
	private String postal = "";
	private String country = "";
	private String phone ="";
	private String phoneExt = "";
	private String countryCode = "";
	private String email = "";
	private String fax = "";
	private String faxExt = "";
	private String faxCode = "";
	
	private String 	updateUserId = "";
	private Object updateTimeStamp;
	private long transactionType = -1;
	
	public LauTradingPartner(){
		super();
	}
	
	
	public String toString(){
		StringBuffer str = new StringBuffer(LauTradingPartner.class.getName());
		
		str.append("[");
		str.append("tradingPartnerId=");
		str.append(tradingPartnerId);
		str.append(", e2bPartnerId=");
		str.append(e2bPartnerId);
		str.append(",e2bDocName=");
		str.append(e2bDocName);
		str.append(",e2bDocNameFU=");
		str.append(e2bDocNameFU);
		str.append(",e2bHost=");
		str.append(e2bHost);
		str.append(",e2bImportType=");
		str.append(e2bImportType);
		str.append(",ackType=");
		str.append(ackType);
		str.append(",ackTiming=");
		str.append(ackTiming);
		str.append(",e2bUseCaseId=");
		str.append(e2bUseCaseId);
		str.append(",xmlDecLine=");
		str.append(xmlDecLine);
		str.append(",xmlDocTypeLine=");
		str.append(xmlDocTypeLine);
		str.append(",ackDecLine=");
		str.append(ackDecLine);
		str.append(",ackDocTypeLine=");
		str.append(ackDocTypeLine);
		str.append(",userGrp=");
		str.append(userGrp);
		str.append(",inDir=");
		str.append(inDir);
		str.append(",outDir=");
		str.append(outDir);
		str.append(",pType=");
		str.append(pType);
		str.append(",pOrg=");
		str.append(pOrg);
		str.append(",pDept=");
		str.append(pDept);
		str.append(",title=");
		str.append(title);
		str.append(",fName=");
		str.append(fName);
		str.append(",mName=");
		str.append(mName);
		str.append(",lName=");
		str.append(lName);
		str.append(",address=");
		str.append(address);
		str.append(",city=");
		str.append(city);
		str.append(",state=");
		str.append(state);
		str.append(",postal=");
		str.append(postal);
		str.append(",country=");
		str.append(country);
		str.append(",phone=");
		str.append(phone);
		str.append(",phoneExt=");
		str.append(phoneExt);
		str.append(",countryCode=");
		str.append(countryCode);
		str.append(",email=");
		str.append(email);
		str.append(",fax=");
		str.append(fax);
		str.append(",faxExt=");
		str.append(faxExt);
		str.append(",faxCode=");
		str.append(faxCode);
		str.append(",e2bDocNameOutbound=");
		str.append(e2bDocNameOutbound);
		str.append(",e2bDocNameFUOutbound=");
		str.append(e2bDocNameFUOutbound);
		str.append(", transactionType=");
		str.append(transactionType);
		str.append(", updateUserId=");
		str.append(updateUserId);
		str.append(", updateTimeStamp=");
		str.append(updateTimeStamp);
		str.append("]");
		return str.toString();
	}
	
	
}

/*
 * <?xml version="1.0"?>
<ROWSET>
<ROW>
<TRADING_PARTNER_ID>4</TRADING_PARTNER_ID>
<E2B_PARTNER_ID>PRIMODTD</E2B_PARTNER_ID>
<E2B_DOC_NAME>DTD21</E2B_DOC_NAME>
<E2B_DOC_NAME_FU>DTD21_FU</E2B_DOC_NAME_FU>
<E2B_HOST>N</E2B_HOST>
<E2B_IMPORT_TYPE>AUTOMATED</E2B_IMPORT_TYPE>
<ACK_TYPE>AUTOMATED</ACK_TYPE>
<ACK_TIMING>FIRST</ACK_TIMING>
<E2B_USE_GLOBAL_CASE_ID>N</E2B_USE_GLOBAL_CASE_ID>
<XML_DOCTYPE_LINE>&lt;!DOCTYPE ichicsr SYSTEM &quot;http://www.accessdata.fda.gov/xml/icsr-xml-v2.1.dtd&quot;&gt;</XML_DOCTYPE_LINE>
<ACK_DOCTYPE_LINE>&lt;!DOCTYPE ichicsrack SYSTEM &quot;http://www.accessdata.fda.gov/xml/icsrack-xml-v1.1.dtd&quot;&gt;
</ACK_DOCTYPE_LINE>
<INBOUND_USER_GROUP_ID>GPARM</INBOUND_USER_GROUP_ID>
<IN_DIRECTORY>MYE2BTEST</IN_DIRECTORY>
<OUT_DIRECTORY>MYE2BACK</OUT_DIRECTORY>
<PARTNER_TYPE>1</PARTNER_TYPE>
<PARTNER_ORGANIZATION>PRIMODTD</PARTNER_ORGANIZATION>
<PARTNER_DEPARTMENT>PV</PARTNER_DEPARTMENT>
<PARTNER_TITLE>DR.</PARTNER_TITLE>
<PARTNER_FIRST_NAME>BRAD</PARTNER_FIRST_NAME>
<PARTNER_LAST_NAME>GALLIEN</PARTNER_LAST_NAME>
<PARTNER_ADDRESS>500 SUNSET BLVD.</PARTNER_ADDRESS>
<PARTNER_CITY>SAN FRANCISCO</PARTNER_CITY>
<PARTNER_STATE>CA</PARTNER_STATE>
<PARTNER_POSTAL_CODE>06754</PARTNER_POSTAL_CODE>
<PARTNER_COUNTRY>US</PARTNER_COUNTRY>
<PARTNER_PHONE_NUMBER>4444444444</PARTNER_PHONE_NUMBER>
<PARTNER_PHONE_EXT>564</PARTNER_PHONE_EXT>
<PARTNER_PHONE_COUNTRY_CODE>1</PARTNER_PHONE_COUNTRY_CODE>
<PARTNER_EMAIL>BRAD@PRIMO.COM</PARTNER_EMAIL>
<PARTNER_FAX_NUMBER>7648726506</PARTNER_FAX_NUMBER>
<PARTNER_FAX_EXT>2222</PARTNER_FAX_EXT>
<PARTNER_FAX_COUNTRY_CODE>345</PARTNER_FAX_COUNTRY_CODE>
<UPDATE_USER_ID>mvulpe</UPDATE_USER_ID>
<UPDATE_TIMESTAMP>05-SEP-12 01.01.03.00 AM</UPDATE_TIMESTAMP>
</ROW>
 */

package com.nrg.lau.regulatory.beans;

import java.io.Serializable;

public class LauLinkedReports implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private long LINKED_REPORT_ID = 0;
	private String PRIMARY_REPORT_ID = "";
	private String LINK_METHOD = "";
	private String LINK_COMMENT = "";
	private String LINKED_REPORTS = "";
	private String REMOVED = "";
	private String UPDATE_USER_ID = "";
	private Object UPDATE_TIMESTAMP;
	
	public long getLINKED_REPORT_ID() {
		return LINKED_REPORT_ID;
	}
	public void setLINKED_REPORT_ID(long lINKED_REPORT_ID) {
		LINKED_REPORT_ID = lINKED_REPORT_ID;
	}
	public String getLINKED_REPORTS() {
		return LINKED_REPORTS;
	}
	public void setLINKED_REPORTS(String lINKED_REPORTS) {
		LINKED_REPORTS = lINKED_REPORTS;
	}
	public String getPRIMARY_REPORT_ID() {
		return PRIMARY_REPORT_ID;
	}
	public void setPRIMARY_REPORT_ID(String pRIMARY_REPORT_ID) {
		PRIMARY_REPORT_ID = pRIMARY_REPORT_ID;
	}
	public String getLINK_METHOD() {
		return LINK_METHOD;
	}
	public void setLINK_METHOD(String lINK_METHOD) {
		LINK_METHOD = lINK_METHOD;
	}
	public String getLINK_COMMENT() {
		return LINK_COMMENT;
	}
	public void setLINK_COMMENT(String lINK_COMMENT) {
		LINK_COMMENT = lINK_COMMENT;
	}
	public String getUPDATE_USER_ID() {
		return UPDATE_USER_ID;
	}
	public void setUPDATE_USER_ID(String uPDATE_USER_ID) {
		UPDATE_USER_ID = uPDATE_USER_ID;
	}
	public Object getUPDATE_TIMESTAMP() {
		return UPDATE_TIMESTAMP;
	}
	public void setUPDATE_TIMESTAMP(Object uPDATE_TIMESTAMP) {
		UPDATE_TIMESTAMP = uPDATE_TIMESTAMP;
	}
	
	public String toString(){
		
		StringBuffer str = new StringBuffer(LauLinkedReports.class.getName());		
		str.append("[");
		str.append("LINKED_REPORT_ID=");
		str.append(LINKED_REPORT_ID);
		str.append(", PRIMARY_REPORT_ID=");
		str.append(PRIMARY_REPORT_ID);
		str.append(", LINK_METHOD=");
		str.append(LINK_METHOD);
		str.append(",LINK_COMMENT=");
		str.append(LINK_COMMENT);
		str.append(",REMOVED=");
		str.append(REMOVED);
		str.append(", LINKED_REPORTS=");
		str.append(LINKED_REPORTS);
		str.append(", UPDATE_USER_ID=");
		str.append(UPDATE_USER_ID);
		str.append(", UPDATE_TIMESTAMP=");
		str.append(UPDATE_TIMESTAMP);		
		str.append("]");
		return str.toString();
	}
	public String getREMOVED() {
		return REMOVED;
	}
	public void setREMOVED(String rEMOVED) {
		REMOVED = rEMOVED;
	}
	
}

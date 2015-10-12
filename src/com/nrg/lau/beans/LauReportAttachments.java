package com.nrg.lau.beans;

import java.io.Serializable;

public class LauReportAttachments implements Serializable {

	private long attachmentId = 0;
	private String attachment = "";
	private String fileName = "";
	private String updateUserId = "";
	private Object updateTimeStamp;
	private long reportId = 0;
	private String reportId2 = "";
	private int attachmentType = 0;
	private long transactionType = -1;
	private String documentName = "";
	private String documentType = "";
	private String binaryFileType = "";
	private String contactId  = "";
	private long ACTIVITY_ID = 0;
	private String BINARY_FILE_TYPE = "";                     
	private String PROMOTE_DOCUMENT = "";                      
	private String EXTERNAL_DOCUMENT_URL = "";  
	private String  LAU_REPORT_ID  = ""; 
	private String OBJECT_ID = ""; 
	private String EXTERNAL_DOCUMENT_ID = "";
	private String activityId  = "";
	private String ack = ""; 
	private String DISPLAY_NUMBER = "";
	
	private static final long serialVersionUID = 1L;

	public long getAttachmentId() {
		return this.attachmentId;
	}

	public void setAttachmentId(long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getAttachment() {
		return this.attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Object getUpdateTimeStamp() {
		return this.updateTimeStamp;
	}

	public void setUpdateTimeStamp(Object updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}

	public long getReportId() {
		return this.reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	public int getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(int attachmentType) {
		this.attachmentType = attachmentType;
	}
	
	public long getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}
	
	public String toString() {
		StringBuffer str = new StringBuffer(LauReportAttachments.class.getName());
		str.append("[");
		str.append("attachmentId=");
		str.append(attachmentId);
		str.append(", attachment=");
		str.append(attachment);
		str.append(", testDate=");
		str.append(attachmentType);
		str.append(", attachmentType=");
		str.append(", updateUserId=");
		str.append(updateUserId);
		str.append(", updateTimeStamp=");
		str.append(updateTimeStamp);
		str.append(", reportId=");
		str.append(reportId);
		str.append(", transactionType=");
		str.append(transactionType);
		str.append(", documentName=");
		str.append(documentName);
		str.append(", fileName=");
		str.append(fileName);
		str.append(", documentType=");
		str.append(documentType);
		str.append(", binaryFileType=");
		str.append(binaryFileType);
		str.append(", contactId=");
		str.append(contactId);		
		str.append(", ACTIVITY_ID=");
		str.append(ACTIVITY_ID);
		str.append(", BINARY_FILE_TYPE=");
		str.append(BINARY_FILE_TYPE);
		str.append(", PROMOTE_DOCUMENT=");
		str.append(PROMOTE_DOCUMENT);
		str.append(", EXTERNAL_DOCUMENT_URL=");
		str.append(EXTERNAL_DOCUMENT_URL);	
		str.append(", OBJECT_ID=");
		str.append(OBJECT_ID);
		str.append(", EXTERNAL_DOCUMENT_ID=");
		str.append(EXTERNAL_DOCUMENT_ID);
		str.append(", ack=");
		str.append(ack);
		str.append(", DISPLAY_NUMBER=");
		str.append(DISPLAY_NUMBER);
		str.append("]");
		return str.toString();
	}

	public String getDISPLAY_NUMBER() {
		return DISPLAY_NUMBER;
	}

	public void setDISPLAY_NUMBER(String dISPLAY_NUMBER) {
		DISPLAY_NUMBER = dISPLAY_NUMBER;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getBinaryFileType() {
		return binaryFileType;
	}

	public void setBinaryFileType(String binaryFileType) {
		this.binaryFileType = binaryFileType;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getContactId() {
		return contactId;
	}

	public void setReportId2(String reportId2) {
		if(reportId2.trim().length() > 0)	{
			this.reportId = Long.valueOf(reportId2);
		} else {	this.reportId = 0;	}		
	}

	public String getReportId2() {
		return reportId2;
	}

	public long getACTIVITY_ID() {
		return ACTIVITY_ID;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getLAU_REPORT_ID() {
		return LAU_REPORT_ID;
	}

	public void setLAU_REPORT_ID(String lAU_REPORT_ID) {
		LAU_REPORT_ID = lAU_REPORT_ID;
	}

	public void setACTIVITY_ID(long aCTIVITYID) {
		ACTIVITY_ID = aCTIVITYID;
	}

	public String getBINARY_FILE_TYPE() {
		return BINARY_FILE_TYPE;
	}

	public void setBINARY_FILE_TYPE(String bINARYFILETYPE) {
		BINARY_FILE_TYPE = bINARYFILETYPE;
	}

	public String getPROMOTE_DOCUMENT() {
		return PROMOTE_DOCUMENT;
	}

	public void setPROMOTE_DOCUMENT(String pROMOTEDOCUMENT) {
		PROMOTE_DOCUMENT = pROMOTEDOCUMENT;
	}

	public String getEXTERNAL_DOCUMENT_URL() {
		return EXTERNAL_DOCUMENT_URL;
	}

	public void setEXTERNAL_DOCUMENT_URL(String eXTERNALDOCUMENTURL) {
		EXTERNAL_DOCUMENT_URL = eXTERNALDOCUMENTURL;
	}

	public String getOBJECT_ID() {
		return OBJECT_ID;
	}

	public void setOBJECT_ID(String oBJECTID) {
		OBJECT_ID = oBJECTID;
	}

	public String getEXTERNAL_DOCUMENT_ID() {
		return EXTERNAL_DOCUMENT_ID;
	}

	public void setEXTERNAL_DOCUMENT_ID(String eXTERNALDOCUMENTID) {
		EXTERNAL_DOCUMENT_ID = eXTERNALDOCUMENTID;
	}

	public String getAck() {
		return ack;
	}

	public void setAck(String ack) {
		this.ack = ack;
	}

}

package com.nrg.lau.beans;

import java.io.Serializable;

public class LauAttachmentProperties implements Serializable {

	private long attachmentId = 0;
	private String documentName = "";
	private String attachmentName = "";
	private String documentType = "";
	private String updateUserId = "";
	private Object updateTimeStamp;
	private long reportId = 0;
	private long transactionType = -1;
	private String PROMOTE_DOCUMENT="";
	private String fileID = "";
	private String DISPLAY_NUMBER = "";

	private static final long serialVersionUID = 1L;

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
	/**
	 * @return the attachmentId
	 */
	public long getAttachmentId() {
		return attachmentId;
	}

	/**
	 * @param attachmentId the attachmentId to set
	 */
	public void setAttachmentId(long attachmentId) {
		this.attachmentId = attachmentId;
	}

	/**
	 * @return the documentName
	 */
	public String getDocumentName() {
		return documentName;
	}

	/**
	 * @param documentName the documentName to set
	 */
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}	
	public String getPROMOTE_DOCUMENT() {
		return PROMOTE_DOCUMENT;
	}

	public void setPROMOTE_DOCUMENT(String pROMOTE_DOCUMENT) {
		PROMOTE_DOCUMENT = pROMOTE_DOCUMENT;
	}
	
	public String getDISPLAY_NUMBER() {
		return this.DISPLAY_NUMBER;
	}

	public void setDISPLAY_NUMBER(String DISPLAY_NUMBER) {
		this.DISPLAY_NUMBER = DISPLAY_NUMBER;
	}

	public String toString() {
		StringBuffer str = new StringBuffer(LauNarrativeText.class.getName());
		str.append("[");
		str.append("attachmentId=");
		str.append(attachmentId);
		str.append(", documentType=");
		str.append(documentType);
		str.append(", documentName=");
		str.append(documentName);
		str.append(", updateUserId=");
		str.append(updateUserId);
		str.append(", updateTimeStamp=");
		str.append(updateTimeStamp);
		str.append(", reportId=");
		str.append(reportId);
		str.append(", transactionType=");
		str.append(transactionType);
		str.append(", attachmentName=");
		str.append(attachmentName);
		str.append(", fileID=");
		str.append(fileID);
		str.append(", DISPLAY_NUMBER=");
		str.append(DISPLAY_NUMBER);
		str.append("]");
		return str.toString();
	}
	
	public long getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentType() {
		return documentType;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getFileID() {
		return fileID;
	}

	public void setFileID(String fileID) {
		this.fileID = fileID;
	}
}

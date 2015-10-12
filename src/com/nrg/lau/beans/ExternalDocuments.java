package com.nrg.lau.beans;

import java.io.Serializable;

public class ExternalDocuments implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private long docID 				= 0;
	private long externalDocumentId	= 0;
	private String status 			= "";
	private String ObjectID			= "";
	private String user 			= "";
	private String rejectReason 	= "";
	private String documentName 	= "";
	private String documentTitle	= "";
	private String documentType	= "";
	private String documentContentType = "";
	private String documentOrigFilename = "";
	private byte[] documentBytes;
	private String fileID 			= "";
	private String fileURL 			= "";
	private String groupID 			= "";
	private String docDescription 			= "";
	private Object dstamp;
	private long e2bExchangeId	= 0;	
	private String DISPLAY_NUMBER = "";
	private String E2B_PARTNER_ID;
	private String E2B_MESSAGENUMB;

	public String toString() {
		StringBuffer str = new StringBuffer(ExternalDocuments.class.getName());
		str.append("[");
		str.append("docID=");
		str.append(docID);
		str.append(", status=");
		str.append(status);
		str.append(", user=");
		str.append(user);
		str.append(", rejectReason=");
		str.append(rejectReason);
		str.append(", fileID=");
		str.append(fileID);
		str.append(", dstamp=");
		str.append(dstamp);
		str.append(", externalDocumentId=");
		str.append(externalDocumentId);
		str.append(", ObjectID=");
		str.append(ObjectID);
		str.append(", documentName=");
		str.append(documentName);
		str.append(", documentTitle=");
		str.append(documentTitle);
		str.append(", documentType=");
		str.append(documentType);
		str.append(", documentContentType=");
		str.append(documentContentType);
		str.append(", documentOrigFilename=");
		str.append(documentOrigFilename);
		str.append(", fileURL=");
		str.append(fileURL);
		str.append(", e2bExchangeId=");
		str.append(e2bExchangeId);
		str.append(", groupID=");
		str.append(groupID);
		str.append(", DISPLAY_NUMBER=");
		str.append(DISPLAY_NUMBER);
		str.append("]");
		return str.toString();
	}	
	
	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentContentType() {
		return documentContentType;
	}

	public void setDocumentContentType(String documentContentType) {
		this.documentContentType = documentContentType;
	}

	public String getDocumentOrigFilename() {
		return documentOrigFilename;
	}

	public void setDocumentOrigFilename(String documentOrigFilename) {
		this.documentOrigFilename = documentOrigFilename;
	}

	public byte[] getDocumentBytes() {
		return documentBytes;
	}

	public void setDocumentBytes(byte[] documentBytes) {
		this.documentBytes = documentBytes;
	}

	public String getDocumentTitle() {
		return documentTitle;
	}
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}
	public long getE2bExchangeId() {
		return e2bExchangeId;
	}
	public void setE2bExchangeId(long e2bExchangeId) {
		this.e2bExchangeId = e2bExchangeId;
	}
	public long getDocID() {
		return docID;
	}
	public void setDocID(long docID) {
		this.docID = docID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public String getFileID() {
		return fileID;
	}
	public void setFileID(String fileID) {
		this.fileID = fileID;
	}
	public Object getDstamp() {
		return dstamp;
	}
	public void setDstamp(Object dstamp) {
		this.dstamp = dstamp;
	}

	public long getExternalDocumentId() {
		return externalDocumentId;
	}

	public void setExternalDocumentId(long externalDocumentId) {
		this.externalDocumentId = externalDocumentId;
	}

	public String getObjectID() {
		return ObjectID;
	}

	public void setObjectID(String objectID) {
		ObjectID = objectID;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocDescription() {
		return docDescription;
	}

	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getE2B_PARTNER_ID() {
		return E2B_PARTNER_ID;
	}

	public void setE2B_PARTNER_ID(String e2B_PARTNER_ID) {
		E2B_PARTNER_ID = e2B_PARTNER_ID;
	}

	public String getE2B_MESSAGENUMB() {
		return E2B_MESSAGENUMB;
	}

	public void setE2B_MESSAGENUMB(String e2B_MESSAGENUMB) {
		E2B_MESSAGENUMB = e2B_MESSAGENUMB;
	}
	
	public String getDISPLAY_NUMBER() {
		return DISPLAY_NUMBER;
	}

	public void setDISPLAY_NUMBER(String dISPLAY_NUMBER) {
		DISPLAY_NUMBER = dISPLAY_NUMBER;
	}

}

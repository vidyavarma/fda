package com.nrg.lau.service.docmgt;

import javax.sql.DataSource;

import com.nrg.lau.beans.ExternalDocuments;

public class DocMgtParameters {
	
	private ExternalDocuments externalDocs;
	private String fileContent = "";
	private String lauReportId = "";
	private String attachmentId = "";
	private DataSource ds = null;
	private String fileName = "";
	private String userName = "";
	private String fileID = "";
	private String groupID = "";
	private String caseID = "";
	private String grpName = "";
	private String url = "";
	private String sql = "";
	private String objectID = "";
	
	public String getObjectID() {
		return objectID;
	}
	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public String getCaseID() {
		return caseID;
	}
	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	public String getLauReportId() {
		return lauReportId;
	}
	public void setLauReportId(String lauReportId) {
		this.lauReportId = lauReportId;
	}
	
	public String toString() {		
		StringBuffer str = new StringBuffer(DocMgtParameters.class.getName());
		str.append("[");
		str.append("fileName=");
		str.append(fileName);
		str.append(", fileContent=");
		str.append(fileContent);
		str.append(", fileID=");
		str.append(fileID);
		str.append(", groupID=");
		str.append(groupID);
		str.append(", caseID=");
		str.append(caseID);
		str.append(", lauReportId=");
		str.append(lauReportId);
		str.append(", objectID=");
		str.append(objectID);
		str.append(", url=");
		str.append(url);
		str.append(", grpName=");
		str.append(grpName);
		str.append(", attachmentId=");
		str.append(attachmentId);
		str.append(", sql=");
		str.append(sql);
		str.append("]");
		return str.toString();        
    }
	
	public String getFileID() {
		return fileID;
	}
	public void setFileID(String fileID) {
		this.fileID = fileID;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public DataSource getDs() {
		return ds;
	}
	public void setDs(DataSource ds) {
		this.ds = ds;
	}
	public String getGrpName() {
		return grpName;
	}
	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public ExternalDocuments getExternalDocs() {
		return externalDocs;
	}
	public void setExternalDocs(ExternalDocuments externalDocs) {
		this.externalDocs = externalDocs;
	}	
	
}
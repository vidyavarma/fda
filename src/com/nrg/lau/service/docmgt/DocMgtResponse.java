package com.nrg.lau.service.docmgt;

public class DocMgtResponse {
	
	private String fileId 	= "";
	private String message 	= "";
	private String success 	= "";
	private String fileName = "";
	private String fileUrl	= "";
	
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String toString() {		
		StringBuffer str = new StringBuffer(DocMgtResponse.class.getName());
		str.append("[");
		str.append("fileId=");
		str.append(fileId);
		str.append(", message=");
		str.append(message);
		str.append(", success=");
		str.append(success);
		str.append(", fileName=");
		str.append(fileName);		
		str.append(", fileUrl=");
		str.append(fileUrl);
		str.append("]");
		return str.toString();        
    }
}
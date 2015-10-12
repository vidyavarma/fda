package com.nrg.lau.sharepoint;

public class SPResponse {
	
	private String fileId 	= "";
	private String message 	= "";
	private String success 	= "";
	private String fileName 	= "";
	private String fileUrl	= "";
	
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String toString() {
        return("FileId = "+this.fileId + " & Message = " +  this.message + " & Success = " +  this.success + " & fileUrl = " +  this.fileUrl);
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
}

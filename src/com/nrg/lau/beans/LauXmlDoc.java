package com.nrg.lau.beans;

import java.io.Serializable;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class LauXmlDoc implements Serializable {
	
	private static Logger log	= Logger.getLogger(LauXmlDoc.class);
	private static final long serialVersionUID = 1L;
	/*public Iterator<LauXmlDoc> iterator() {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	private String docName = "";
	private String docDesc = "";
	private String langCode = "";
	
	private String 	updateUserId = "";
	private Object updateTimeStamp;
	private long transactionType = -1;
	
	public LauXmlDoc(){
		super();
	}
	
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getDocDesc() {
		return docDesc;
	}
	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}
	public String getLangCode() {
		return langCode;
	}
	public void setLangCode(String langCode) {
		this.langCode = langCode;
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
	
	public String toString(){
		StringBuffer str = new StringBuffer(LauXmlDoc.class.getName());
		
		str.append("[");
		str.append("docName=");
		str.append(docName);
		str.append(", docDesc=");
		str.append(docDesc);
		str.append(",langCode=");
		str.append(langCode);
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
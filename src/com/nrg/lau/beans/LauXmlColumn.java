package com.nrg.lau.beans;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class LauXmlColumn implements Serializable {
	
	private static Logger log	= Logger.getLogger(LauXmlColumn.class);
	private static final long serialVersionUID = 1L;
	
	private String docName = "";
	private String nodeName = "";
	private String xmlTagName = "";
	private String colFctName = "";
	private String colPosition = "";
	private String mandatoryXmlTag = "";
	
	private String 	updateUserId = "";
	private Object updateTimeStamp;
	private long transactionType = -1;
	
	public LauXmlColumn(){
		super();
	}
	
	public String getDocName() {
		return docName;
	}




	public void setDocName(String docName) {
		this.docName = docName;
	}




	public String getNodeName() {
		return nodeName;
	}




	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}




	public String getXmlTagName() {
		return xmlTagName;
	}




	public void setXmlTagName(String xmlTagName) {
		this.xmlTagName = xmlTagName;
	}




	public String getColFctName() {
		return colFctName;
	}




	public void setColFctName(String colFctName) {
		this.colFctName = colFctName;
	}




	public String getColPosition() {
		return colPosition;
	}




	public void setColPosition(String colPosition) {
		this.colPosition = colPosition;
	}




	public String getMandatoryXmlTag() {
		return mandatoryXmlTag;
	}




	public void setMandatoryXmlTag(String mandatoryXmlTag) {
		this.mandatoryXmlTag = mandatoryXmlTag;
	}




	public long getTransactionType() {
		return transactionType;
	}




	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}




	public String toString(){
		StringBuffer str = new StringBuffer(LauXmlColumn.class.getName());
		
		str.append("[");
		str.append("docName=");
		str.append(docName);
		str.append(", nodeName=");
		str.append(nodeName);
		str.append(",xmlTagName=");
		str.append(xmlTagName);
		str.append(", colFctName=");
		str.append(colFctName);
		str.append(", colPosition=");
		str.append(colPosition);
		str.append(", mandatoryXmlTag=");
		str.append(mandatoryXmlTag);
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
	

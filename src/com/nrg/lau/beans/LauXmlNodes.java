package com.nrg.lau.beans;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class LauXmlNodes implements Serializable {
	
	private static Logger log	= Logger.getLogger(LauXmlNodes.class);
	private static final long serialVersionUID = 1L;
	
	private String docName = "";
	private String nodeName = "";
	private String xmlNodeName = "";
	private String nodeType = "";
	private String parent = "";
	private String sqlTxt = "";
	private long position = 0;
	private long nodeLevel = 0;
	private String useReportId = "";
	private String mandatoryNode = "";
	
	private String 	updateUserId = "";
	private Object updateTimeStamp;
	private long transactionType = -1;
	
	public LauXmlNodes(){
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



	public String getXmlNodeName() {
		return xmlNodeName;
	}



	public void setXmlNodeName(String xmlNodeName) {
		this.xmlNodeName = xmlNodeName;
	}



	public String getNodeType() {
		return nodeType;
	}



	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}



	public String getParent() {
		return parent;
	}



	public void setParent(String parent) {
		this.parent = parent;
	}



	public String getSqlTxt() {
		return sqlTxt;
	}



	public void setSqlTxt(String sqlTxt) {
		this.sqlTxt = sqlTxt;
	}



	public long getPosition() {
		return position;
	}



	public void setPosition(long position) {
		this.position = position;
	}



	public long getNodeLevel() {
		return nodeLevel;
	}



	public void setNodeLevel(long nodeLevel) {
		this.nodeLevel = nodeLevel;
	}



	public String getUseReportId() {
		return useReportId;
	}



	public void setUseReportId(String useReportId) {
		this.useReportId = useReportId;
	}



	public String getMandatoryNode() {
		return mandatoryNode;
	}



	public void setMandatoryNode(String mandatoryNode) {
		this.mandatoryNode = mandatoryNode;
	}



	public long getTransactionType() {
		return transactionType;
	}



	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}



	public String toString(){
		StringBuffer str = new StringBuffer(LauXmlNodes.class.getName());
		
		str.append("[");
		str.append("docName=");
		str.append(docName);
		str.append(", nodeName=");
		str.append(nodeName);
		str.append(",xmlNodeName=");
		str.append(xmlNodeName);
		str.append(", nodeType=");
		str.append(nodeType);
		str.append(", parent=");
		str.append(parent);
		str.append(", sqlTxt=");
		str.append(sqlTxt);
		str.append(", position=");
		str.append(position);
		str.append(", nodeLevel=");
		str.append(nodeLevel);
		str.append(", useReportId=");
		str.append(useReportId);
		str.append(", mandatoryNode=");
		str.append(mandatoryNode);
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
	

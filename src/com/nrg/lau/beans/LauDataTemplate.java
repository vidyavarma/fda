package com.nrg.lau.beans;

import java.io.Serializable;
import org.apache.log4j.Logger;

public class LauDataTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log	= Logger.getLogger(LauDataTemplate.class);
	private long dataTemplateId = 0;
	private String attachment = "";
	private String templateName = "";
	private String templateDescription = "";
	private String stdReport = "";
	private String dataTemplate = "";
	private String 	updateUserId = "";
	private Object updateTimeStamp;
	private long transactionType = -1;
	
	public LauDataTemplate(){
		super();
	}
	
	public long getDataTemplateId(){
		return this.dataTemplateId;
	}
	
	public void setDataTemplateId(long dataTemplateId){
		this.dataTemplateId = dataTemplateId;
	}
	
	public String getAttachment() {
		return this.attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	public String getTemplateName(){
		return this.templateName;
	}
	
	public void setTemplateName(String templateName){
		this.templateName = templateName;
	}
	
	public String getTemplateDescription(){
		return this.templateDescription;
	}
	
	public void setTemplateDescription(String templateDescription){
		this.templateDescription = templateDescription;
	}
	
	public String getStdReport(){
		return this.stdReport;
	}
	
	public void setStdReport(String stdReport){
		this.stdReport = stdReport;
	}
	
	public String getDataTemplate(){
		return this.dataTemplate;
	}
	
	public void setDataTemplate(String dataTemplate){
		this.dataTemplate = dataTemplate;
	}
	
	public String getUpdateUserId(){
		return this.updateUserId;
	}
	
	public void setUpdateUserId(String updateUserId){
		this.updateUserId = updateUserId;
	}
	
	public Object getUpdateTimeStamp() {
		return this.updateTimeStamp;
	}

	public void setUpdateTimeStamp(Object updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}
	
	public String toString(){
		StringBuffer str = new StringBuffer(LauDataTemplate.class.getName());
		
		str.append("[");
		str.append("dataTemplateId=");
		str.append(dataTemplateId);
		str.append(", attachment=");
		str.append(attachment);
		str.append(",templateName=");
		str.append(templateName);
		str.append(",templateDescription=");
		str.append(templateDescription);
		str.append(",stdReport=");
		str.append(stdReport);
		str.append(",dataTemplate=");
		str.append(dataTemplate);
		str.append(", transactionType=");
		str.append(transactionType);
		str.append(", updateUserId=");
		str.append(updateUserId);
		str.append(", updateTimeStamp=");
		str.append(updateTimeStamp);
		str.append("]");
		return str.toString();
	}
	
	public long getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}
	 
}

package com.nrg.lau.beans;


import java.io.Serializable;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class LauLayoutTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log	= Logger.getLogger(LauLayoutTemplate.class);
	private long layoutTemplateId = 0;
	private long dataTemplateId = 0;
	
	private String attachment = "";
	private String templateName = "";
	private String templateDescription = "";
	private String templatePermission = "";
	private String stdReport = "";
	private String promptForContact = "";
	private String pdfOutput = "";
	private String rtfOutput = "";
	private String xmlOutput = "";
	private String xlsOutput = "";
	private String defaultOutputFormat = "";
	private String languageCode = "";
	private String dateFormat = "";
	private String layoutTemplate = "";
	private String 	updateUserId = "";
	private Object updateTimeStamp;
	//PR-766 By vinay.kumar
	private ArrayList<LauLayoutQuestions> questions = new ArrayList<LauLayoutQuestions>();
	
	private String autoUpload = "";
	private String generateEmail = "";
	
	private long transactionType = -1;
	
	public LauLayoutTemplate(){
		super();
	}
	
	public long getLayoutTemplateId(){
		return this.layoutTemplateId;
	}
	
	public String getAttachment() {
		return this.attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	public void setLayoutTemplateId(long layoutTemplateId){
		this.layoutTemplateId = layoutTemplateId;
	}
	public long getDataTemplateId(){
		return this.dataTemplateId;
	}
	public void setDataTemplateId(long dataTemplateId){
		this.dataTemplateId = dataTemplateId;
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
	
	public String getTemplatePermission(){
		return this.templatePermission;
	}
	
	public void setTemplatePermission(String templatePermission){
		this.templatePermission = templatePermission;
	}
	
	public String getStdReport(){
		return this.stdReport;
	}
	
	public void setStdReport(String stdReport){
		this.stdReport = stdReport;
	}
	
	public String getPromptContact(){
		return this.promptForContact;
	}
	
	public void setPromptContact(String promptForContact){
		this.promptForContact = promptForContact;
	}
	
	public String getPdfOutput(){
		return this.pdfOutput;
	}
	
	public void setPdfOutput(String pdfOutput){
		this.pdfOutput = pdfOutput;
	}
	
	public String getRtfOutput(){
		return this.rtfOutput;
	}
	
	public void setRtfOutput(String rtfOutput){
		this.rtfOutput = rtfOutput;
	}
	
	public String getXmlOutput(){
		return this.xmlOutput;
	}
	
	public void setXmlOutput(String xmlOutput){
		this.xmlOutput = xmlOutput;
	}
	
	public String getXlsOutput(){
		return this.xlsOutput;
	}
	
	public void setXlsOutput(String xlsOutput){
		this.xlsOutput = xlsOutput;
	}
	
	public String getDefaultOutputFormat(){
		return this.defaultOutputFormat;
	}
	
	public void setDefaultOutputFormat(String defaultOutputFormat){
		this.defaultOutputFormat = defaultOutputFormat;
	}
	public String getLanguageCode(){
		return this.languageCode;
	}
	
	public void setLanguageCode(String languageCode){
		this.languageCode = languageCode;
	}
	public String getDateFormat(){
		return this.dateFormat;
	}
	
	public void setDateFormat(String dateFormat){
		this.dateFormat = dateFormat;
	}
	public String getLayoutTemplate(){
		return this.layoutTemplate;
	}
	
	public void setLayoutTemplate(String layoutTemplate){
		this.layoutTemplate = layoutTemplate;
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
		str.append(",templatePermission=");
		str.append(templatePermission);
		str.append(",stdReport=");
		str.append(stdReport);
		str.append(",promptForContact=");
		str.append(promptForContact);
		str.append(",pdfOutput=");
		str.append(pdfOutput);
		str.append(",rtfOutput=");
		str.append(rtfOutput);
		str.append(",xmlOutput=");
		str.append(xmlOutput);
		str.append(",xlsOutput=");
		str.append(xlsOutput);
		str.append(",defaultOutputFormat=");
		str.append(defaultOutputFormat);
		str.append(",languageCode=");
		str.append(languageCode);
		str.append(",dateFormat=");
		str.append(dateFormat);			
	//	str.append(",layoutTemplate=");
	//	str.append(layoutTemplate);
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

	/**
	 * @return the questions
	 */
	public ArrayList<LauLayoutQuestions> getQuestions() {
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(ArrayList<LauLayoutQuestions> questions) {
		this.questions = questions;
	}
	/**
	 *  PR-766 By vinay.kumar
	 * @param lauLayoutQuestions
	 */
	public void addLetterQuestions(LauLayoutQuestions lauLayoutQuestions){
		questions.add(lauLayoutQuestions);
	}

	public String getPromptForContact() {
		return promptForContact;
	}

	public void setPromptForContact(String promptForContact) {
		this.promptForContact = promptForContact;
	}

	public String getAutoUpload() {
		return autoUpload;
	}

	public void setAutoUpload(String autoUpload) {
		this.autoUpload = autoUpload;
	}

	public String getGenerateEmail() {
		return generateEmail;
	}

	public void setGenerateEmail(String generateEmail) {
		this.generateEmail = generateEmail;
	}
		
}

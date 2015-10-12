package com.nrg.lau.regulatory.beans;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class LauLetterQuestion implements Serializable {
	
	private static Logger log	= Logger.getLogger(LauLetterQuestion.class);
	private static final long serialVersionUID = 1L;
	
	private long questionId;
	private String questionGroup = "";
	private String questionType = "";
	private String questionText = "";
	private String reviewerCode = "";
	private String questionStatus = "";
	private String editAllowed = "";
	private String questionName = ""; // PR - 865 by vinay.kumar
	private String 	updateUserId = "";
	private Object updateTimeStamp;
	private long transactionType = -1;
	
	public LauLetterQuestion(){
		super();
	}
	

	
	
	public long getQuestionId() {
		return questionId;
	}




	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}




	public String getQuestionGroup() {
		return questionGroup;
	}




	public void setQuestionGroup(String questionGroup) {
		this.questionGroup = questionGroup;
	}




	public String getQuestionType() {
		return questionType;
	}




	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}




	public String getQuestionText() {
		return questionText;
	}




	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}




	public String getReviewerCode() {
		return reviewerCode;
	}




	public void setReviewerCode(String reviewerCode) {
		this.reviewerCode = reviewerCode;
	}




	public String getQuestionStatus() {
		return questionStatus;
	}




	public void setQuestionStatus(String questionStatus) {
		this.questionStatus = questionStatus;
	}




	public String getEditAllowed() {
		return editAllowed;
	}




	public void setEditAllowed(String editAllowed) {
		this.editAllowed = editAllowed;
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




	/**
	 * @return the questionName
	 */
	public String getQuestionName() {
		return questionName;
	}




	/**
	 * @param questionName the questionName to set
	 */
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}




	public String toString(){
		StringBuffer str = new StringBuffer(LauLetterQuestion.class.getName());
		
		str.append("[");
		str.append(",questionId=");
		str.append(questionId);
		str.append(", questionGroup=");
		str.append(questionGroup);
		str.append(", questionType=");
		str.append(questionType);
		str.append(",questionText=");
		str.append(questionText);
		str.append(", reviewerCode=");
		str.append(reviewerCode);
		str.append(", questionStatus=");
		str.append(questionStatus);
		str.append(", editAllowed=");
		str.append(editAllowed);
		str.append(", transactionType=");
		str.append(transactionType);
		str.append(", updateUserId=");
		str.append(updateUserId);
		str.append(", updateTimeStamp=");
		str.append(updateTimeStamp);
		str.append(", questionName=");
		str.append(questionName);
		str.append("]");
		return str.toString();
	}
	
	
	
}
	

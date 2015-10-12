package com.nrg.lau.regulatory.dao;

import java.io.Serializable;

public class LauIncludedQuestions  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long LAU_GENERATED_LETTER_ID;
	private String LAU_QUESTION_ID;
	private String ACTUAL_QUESTION_TEXT;
	private String UPDATE_USER_ID;
	private Object UPDATE_TIMESTAMP;
	
	public long getLAU_GENERATED_LETTER_ID() {
		return LAU_GENERATED_LETTER_ID;
	}
	public void setLAU_GENERATED_LETTER_ID(long lAU_GENERATED_LETTER_ID) {
		LAU_GENERATED_LETTER_ID = lAU_GENERATED_LETTER_ID;
	}
	public String getLAU_QUESTION_ID() {
		return LAU_QUESTION_ID;
	}
	public void setLAU_QUESTION_ID(String lAU_QUESTION_ID) {
		LAU_QUESTION_ID = lAU_QUESTION_ID;
	}
	public String getACTUAL_QUESTION_TEXT() {
		return ACTUAL_QUESTION_TEXT;
	}
	public void setACTUAL_QUESTION_TEXT(String aCTUAL_QUESTION_TEXT) {
		ACTUAL_QUESTION_TEXT = aCTUAL_QUESTION_TEXT;
	}
	public String getUPDATE_USER_ID() {
		return UPDATE_USER_ID;
	}
	public void setUPDATE_USER_ID(String uPDATE_USER_ID) {
		UPDATE_USER_ID = uPDATE_USER_ID;
	}
	public Object getUPDATE_TIMESTAMP() {
		return UPDATE_TIMESTAMP;
	}
	public void setUPDATE_TIMESTAMP(Object uPDATE_TIMESTAMP) {
		UPDATE_TIMESTAMP = uPDATE_TIMESTAMP;
	}	
	
}

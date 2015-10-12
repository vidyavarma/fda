package com.nrg.lau.regulatory.dao;

import java.io.Serializable;

public class LauIncludedReports implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long LAU_GENERATED_LETTER_ID;
	private String REPORT_ID;
	private String UPDATE_USER_ID;
	private Object UPDATE_TIMESTAMP;
	
	public long getLAU_GENERATED_LETTER_ID() {
		return LAU_GENERATED_LETTER_ID;
	}
	public void setLAU_GENERATED_LETTER_ID(long lAU_GENERATED_LETTER_ID) {
		LAU_GENERATED_LETTER_ID = lAU_GENERATED_LETTER_ID;
	}
	public String getREPORT_ID() {
		return REPORT_ID;
	}
	public void setREPORT_ID(String rEPORT_ID) {
		REPORT_ID = rEPORT_ID;
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

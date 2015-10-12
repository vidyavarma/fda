package com.nrg.lau.regulatory.beans;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class LauReviewerNote implements Serializable{

	private static final long serialVersionUID = 1L;
	private static Logger log	= Logger.getLogger(LauReviewerNote.class);
	
	private String REPORT_ID = "";

	public String getREPORT_ID() {
		return REPORT_ID;
	}

	public void setREPORT_ID(String rEPORT_ID) {
		REPORT_ID = rEPORT_ID;
	}
	
		
	
}

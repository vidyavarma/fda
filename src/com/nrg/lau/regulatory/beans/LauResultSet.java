package com.nrg.lau.regulatory.beans;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class LauResultSet implements Serializable{

	private static final long serialVersionUID = 1L;
	private static Logger log	= Logger.getLogger(LauResultSet.class);
	
	private String GROUP_ID = "";
	
	
	public String getGROUP_ID() {
		return GROUP_ID;
	}
	public void setGROUP_ID(String gROUP_ID) {
		GROUP_ID = gROUP_ID;
	}
	
}

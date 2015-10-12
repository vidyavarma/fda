package com.nrg.lau.regulatory.beans;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class LauGetSavedResultSet implements Serializable{

	private static final long serialVersionUID = 1L;
	private static Logger log	= Logger.getLogger(LauGetSavedResultSet.class);
	
	private String CODE = "";
	private String CODE_VALUE = "";
	
	public String getCODE() {
		return CODE;
	}
	public void setCODE(String cODE) {
		CODE = cODE;
	}
	public String getCODE_VALUE() {
		return CODE_VALUE;
	}
	public void setCODE_VALUE(String cODE_VALUE) {
		CODE_VALUE = cODE_VALUE;
	}
	@Override
	public String toString() {
		return "LauGetSavedResultSet [CODE=" + CODE + ", CODE_VALUE="
				+ CODE_VALUE + "]";
	}
	
	
	
}

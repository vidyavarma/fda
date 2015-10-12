/**
 * 
 */
package com.nrg.lau.beans;

import java.io.Serializable;

/**
 * PR -766
 * @author vinay.kumar
 *
 */
public class LauLayoutQuestions implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4859266092722311512L;

	private String LAU_QUESTION_ID;
	private String QUESTION_TYPE;
	/**
	 * @return the lAU_QUESTION_ID
	 */
	public String getLAU_QUESTION_ID() {
		return LAU_QUESTION_ID;
	}
	/**
	 * @param lAU_QUESTION_ID the lAU_QUESTION_ID to set
	 */
	public void setLAU_QUESTION_ID(String lAU_QUESTION_ID) {
		LAU_QUESTION_ID = lAU_QUESTION_ID;
	}
	/**
	 * @return the qUESTION_TYPE
	 */
	public String getQUESTION_TYPE() {
		return QUESTION_TYPE;
	}
	/**
	 * @param qUESTION_TYPE the qUESTION_TYPE to set
	 */
	public void setQUESTION_TYPE(String qUESTION_TYPE) {
		QUESTION_TYPE = qUESTION_TYPE;
	}
	
		
}

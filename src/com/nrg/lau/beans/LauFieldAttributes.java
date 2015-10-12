package com.nrg.lau.beans;

import java.io.Serializable;
import java.util.Set;

public class LauFieldAttributes implements Serializable {

	private String fieldName;

	private String fieldLabel;

	private String fieldType;

	private String mandatoryFlag;

	private String fieldHelp;

	private String updateUserId;

	private Object udpateDate;

	private Set<LauUserGroupFieldAccess> lauUserGroupFieldAccessCollection;

	private static final long serialVersionUID = 1L;

	public LauFieldAttributes() {
		super();
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldLabel() {
		return this.fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	public String getFieldType() {
		return this.fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getMandatoryFlag() {
		return this.mandatoryFlag;
	}

	public void setMandatoryFlag(String mandatoryFlag) {
		this.mandatoryFlag = mandatoryFlag;
	}

	public String getFieldHelp() {
		return this.fieldHelp;
	}

	public void setFieldHelp(String fieldHelp) {
		this.fieldHelp = fieldHelp;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Object getUdpateDate() {
		return this.udpateDate;
	}

	public void setUdpateDate(Object udpateDate) {
		this.udpateDate = udpateDate;
	}

	public Set<LauUserGroupFieldAccess> getLauUserGroupFieldAccessCollection() {
		return this.lauUserGroupFieldAccessCollection;
	}

	public void setLauUserGroupFieldAccessCollection(Set<LauUserGroupFieldAccess> lauUserGroupFieldAccessCollection) {
		this.lauUserGroupFieldAccessCollection = lauUserGroupFieldAccessCollection;
	}

}

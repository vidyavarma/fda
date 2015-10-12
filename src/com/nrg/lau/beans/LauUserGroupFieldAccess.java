package com.nrg.lau.beans;

import java.io.Serializable;

public class LauUserGroupFieldAccess implements Serializable {
	
	private String visibleToUserGroup;

	private String enterableByUserGroup;

	private String updateUserId;

	private Object udpateDate;

	private LauFieldAttributes fieldName;

	private LauUserGroups userGroupId;

	private static final long serialVersionUID = 1L;

	public LauUserGroupFieldAccess() {
		super();
	}

	public String getVisibleToUserGroup() {
		return this.visibleToUserGroup;
	}

	public void setVisibleToUserGroup(String visibleToUserGroup) {
		this.visibleToUserGroup = visibleToUserGroup;
	}

	public String getEnterableByUserGroup() {
		return this.enterableByUserGroup;
	}

	public void setEnterableByUserGroup(String enterableByUserGroup) {
		this.enterableByUserGroup = enterableByUserGroup;
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

	public LauFieldAttributes getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(LauFieldAttributes fieldName) {
		this.fieldName = fieldName;
	}

	public LauUserGroups getUserGroupId() {
		return this.userGroupId;
	}

	public void setUserGroupId(LauUserGroups userGroupId) {
		this.userGroupId = userGroupId;
	}

}

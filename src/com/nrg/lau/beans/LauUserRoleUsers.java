package com.nrg.lau.beans;

import java.io.Serializable;

public class LauUserRoleUsers implements Serializable {
	
	private String updateUserId;

	private Object updateDate;

	private LauUsers userId;

	private LauUserRoles userRoleId;

	private static final long serialVersionUID = 1L;

	public LauUserRoleUsers() {
		super();
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Object getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Object updateDate) {
		this.updateDate = updateDate;
	}

	public LauUsers getUserId() {
		return this.userId;
	}

	public void setUserId(LauUsers userId) {
		this.userId = userId;
	}

	public LauUserRoles getUserRoleId() {
		return this.userRoleId;
	}

	public void setUserRoleId(LauUserRoles userRoleId) {
		this.userRoleId = userRoleId;
	}

}

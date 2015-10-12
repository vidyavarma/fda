package com.nrg.lau.beans;

import java.io.Serializable;

public class LauRolePermissionsPK implements Serializable {
	
	private long userRoleId2;

	private String applicationPermission;

	private static final long serialVersionUID = 1L;

	public LauRolePermissionsPK() {
		super();
	}

	public long getUserRoleId2() {
		return this.userRoleId2;
	}

	public void setUserRoleId2(long userRoleId2) {
		this.userRoleId2 = userRoleId2;
	}

	public String getApplicationPermission() {
		return this.applicationPermission;
	}

	public void setApplicationPermission(String applicationPermission) {
		this.applicationPermission = applicationPermission;
	}

	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if ( ! (o instanceof LauRolePermissionsPK)) {
			return false;
		}
		LauRolePermissionsPK other = (LauRolePermissionsPK) o;
		return this.applicationPermission.equals(other.applicationPermission)
			&& (this.userRoleId2 == other.userRoleId2);
	}

	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.applicationPermission.hashCode();
		hash = hash * prime + ((int) (this.userRoleId2 ^ (this.userRoleId2 >>> 32)));
		return hash;
	}

}

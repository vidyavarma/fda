package com.nrg.lau.beans;

import java.util.ArrayList;
import java.util.Iterator;

public class LauUserPermissionsCollection {
	
	private ArrayList<LauUserRoles> roles = new ArrayList<LauUserRoles>();
	private ArrayList<LauRolePermissions> rolePermissions = new ArrayList<LauRolePermissions>();

	
	public ArrayList<LauUserRoles> getRoles() {
		return roles;
	}

	public void setRoles(ArrayList<LauUserRoles> userRoles) {
		this.roles = userRoles;
	}

	public ArrayList<LauRolePermissions> getRolePermissions() {
		return rolePermissions;
	}

	public void setRolePermissions(ArrayList<LauRolePermissions> lauRolePermissions) {
		this.rolePermissions = lauRolePermissions;
	}
	
	public void addRoles(LauUserRoles userRole) {
		Iterator<LauUserRoles> itr = roles.iterator();
		int chk						= 0;
		while(itr.hasNext()) {
			LauUserRoles lauUserRoles = (LauUserRoles)itr.next();
			if(lauUserRoles.getUSER_ROLE().trim().equals(userRole.getUSER_ROLE().trim())) {
				chk = 1;
			}
		}
		if(chk==0) roles.add(userRole);
	}
	
	public void addRolePermissions(LauRolePermissions lauRolePermissions) {
		rolePermissions.add(lauRolePermissions);
	}
}

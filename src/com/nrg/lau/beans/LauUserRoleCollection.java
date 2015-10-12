package com.nrg.lau.beans;

import java.util.ArrayList;
import java.util.Iterator;

public class LauUserRoleCollection {
	
	private ArrayList<LauUsers> users 			= new ArrayList<LauUsers>();
	private ArrayList<LauUserRoles> userRoles 	= new ArrayList<LauUserRoles>();

	public ArrayList<LauUsers> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<LauUsers> user) {
		this.users = user;
	}

	public ArrayList<LauUserRoles> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(ArrayList<LauUserRoles> userRole) {
		this.userRoles = userRole;
	}
	
	public void addUsers(LauUsers user) {
		Iterator<LauUsers> itr = users.iterator();
		int chk						= 0;
		while(itr.hasNext()) {
			LauUsers lauUsers = (LauUsers)itr.next();
			if(lauUsers.getUSER_ID().trim().equals(user.getUSER_ID().trim())) {
				chk = 1;
			}
		}
		if(chk==0) users.add(user);
	}
	
	public void addUserRoles(LauUserRoles userRole) {
		userRoles.add(userRole);
	}
}

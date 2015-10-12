package com.nrg.lau.beans;

import java.util.ArrayList;
import java.util.Iterator;

public class LauUserGroupCollection {
	
	private ArrayList<LauUserGroups> groups = new ArrayList<LauUserGroups>();
	private ArrayList<LauUserGroupAccess> accessibleGroups = new ArrayList<LauUserGroupAccess>();

	public ArrayList<LauUserGroups> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<LauUserGroups> userGrp) {
		this.groups = userGrp;
	}

	public ArrayList<LauUserGroupAccess> getAccessibleGroups() {
		return accessibleGroups;
	}

	public void setAccessibleGroups(ArrayList<LauUserGroupAccess> accessibleGroups) {
		this.accessibleGroups = accessibleGroups;
	}
	
	public void addGroups(LauUserGroups userGrp) {
		Iterator<LauUserGroups> itr = groups.iterator();
		int chk						= 0;
		while(itr.hasNext()) {
			LauUserGroups lauGroups = (LauUserGroups)itr.next();
			if(lauGroups.getUSER_GROUP_ID().trim().equals(userGrp.getUSER_GROUP_ID().trim())) {
				chk = 1;
			}
		}
		if(chk==0) groups.add(userGrp);
	}
	
	public void addAccessibleGroups(LauUserGroupAccess userAccGrp) {
		accessibleGroups.add(userAccGrp);
	}
}

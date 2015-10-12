package com.nrg.lau.beans;

import java.util.ArrayList;

public class LauReferenceCollection {
	
	private ArrayList<LauReferenceLists> refLists				= new ArrayList<LauReferenceLists>();
	private ArrayList<LauReferenceListDetails> refListsDetails 	= new ArrayList<LauReferenceListDetails>();

	public ArrayList<LauReferenceLists> getRefLists() {
		return refLists;
	}

	public void setRefLists(ArrayList<LauReferenceLists> reflst) {
		this.refLists = reflst;
	}

	public ArrayList<LauReferenceListDetails> getRefListsDetails() {
		return refListsDetails;
	}

	public void setRefListsDetails(ArrayList<LauReferenceListDetails> reflstDetail) {
		this.refListsDetails = reflstDetail;
	}
	
	public void addRefLists(LauReferenceLists reflst) {
		refLists.add(reflst);
	}
	
	public void addRefListsDetails(LauReferenceListDetails reflstDetail) {
		refListsDetails.add(reflstDetail);
	}
}

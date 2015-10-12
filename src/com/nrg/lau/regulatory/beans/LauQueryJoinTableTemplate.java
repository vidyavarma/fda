package com.nrg.lau.regulatory.beans;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class LauQueryJoinTableTemplate implements Serializable{

	private static final long serialVersionUID = 1L;
	private static Logger log	= Logger.getLogger(LauQueryJoinTableTemplate.class);
	
	private long joinCriteriaID = 0 ;
	private String tableName = "";
	private String PK1 = "";
	private String PK2 = "";
	private String PK3 = "";
	private String PK4 = "";
	private String parentTable = "";
	private String FK1 = "";
	private String FK2 = "";
	private String FK3 = "";
	private String FK4 = "";
	private String hintText = "";
	private String joinSQL = "";
	
	private long transactionType = -1;
	
	
	public long getJoinCriteriaID() {
		return joinCriteriaID;
	}
	public void setJoinCriteriaID(long joinCriteriaID) {
		this.joinCriteriaID = joinCriteriaID;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getPK1() {
		return PK1;
	}
	public void setPK1(String PK1) {
		this.PK1 = PK1;
	}
	public String getPK2() {
		return PK2;
	}
	public void setPK2(String PK2) {
		this.PK2 = PK2;
	}
	public String getPK3() {
		return PK3;
	}
	public void setPK3(String PK3) {
		this.PK3 = PK3;
	}
	public String getPK4() {
		return PK4;
	}
	public void setPK4(String PK4) {
		this.PK4 = PK4;
	}

	public String getParentTable() {
		return parentTable;
	}
	public void setParentTable(String parentTable) {
		this.parentTable = parentTable;
	}
	public String getFK1() {
		return FK1;
	}
	public void setFK1(String FK1) {
		this.FK1 = FK1;
	}
	public String getFK2() {
		return FK2;
	}
	public void setFK2(String FK2) {
		this.FK2 = FK2;
	}
	public String getFK3() {
		return FK3;
	}
	public void setFK3(String FK3) {
		this.FK3 = FK3;
	}
	public String getFK4() {
		return FK4;
	}
	public void setFK4(String FK4) {
		this.FK4 = FK4;
	}	
	
	public String getJoinSQL() {
		return joinSQL;
	}
	public void setJoinSQL(String joinSQL) {
		this.joinSQL = joinSQL;
	}
	public String getHintText() {
		return hintText;
	}
	public void setHintText(String hintText) {
		this.hintText = hintText;
	}
	public long getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}
	
}

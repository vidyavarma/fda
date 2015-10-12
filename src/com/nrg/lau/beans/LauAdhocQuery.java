package com.nrg.lau.beans;

import java.io.Serializable;

public class LauAdhocQuery implements Serializable{
	
	private long queryId = 0;
	private String queryName = "";
	private long topComponentId = 0;
	private String savedQuery = "";
	private String queryPermission = "";
	private String queryDescription = "";
	private String queryXml = "";
	private String querySql = "";
	private String updateUserId = "";
	private Object updateTimeStamp;
	private long transactionType = -1;
	public long getQueryId() {
		return queryId;
	}
	public void setQueryId(long queryId) {
		this.queryId = queryId;
	}
	public String getQueryName() {
		return queryName;
	}
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	public long getTopComponentId() {
		return topComponentId;
	}
	public void setTopComponentId(long topComponentId) {
		this.topComponentId = topComponentId;
	}
	public String getSavedQuery() {
		return savedQuery;
	}
	public void setSavedQuery(String savedQuery) {
		this.savedQuery = savedQuery;
	}
	public String getQueryPermission() {
		return queryPermission;
	}
	public void setQueryPermission(String queryPermission) {
		this.queryPermission = queryPermission;
	}
	public String getQueryDescription() {
		return queryDescription;
	}
	public void setQueryDescription(String queryDescription) {
		this.queryDescription = queryDescription;
	}
	public String getQueryXml() {
		return queryXml;
	}
	public void setQueryXml(String queryXml) {
		this.queryXml = queryXml;
	}
	public String getQuerySql() {
		return querySql;
	}
	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public Object getUpdateTimeStamp() {
		return updateTimeStamp;
	}
	public void setUpdateTimeStamp(Object updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}
	public long getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}
	
	
}

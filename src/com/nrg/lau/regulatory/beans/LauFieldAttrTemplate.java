package com.nrg.lau.regulatory.beans;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class LauFieldAttrTemplate implements Serializable{

	private static final long serialVersionUID = 1L;
	private static Logger log	= Logger.getLogger(LauFieldAttrTemplate.class);
	
	private long fieldId = 0 ;
	private String fieldName = "";
	private String fieldType = "";
	private String fieldLabel = "";
	private String queryEnabled = "";
	private String tableName = "";
	private String columnName = "";
	private String moduleName = "";
	private String referenceCodeList = "";
	private String notifyEnabled = "";
	private String subQuery = "";
	private long transactionType = -1;
	
	
	public long getFieldId() {
		return fieldId;
	}
	public void setFieldId(long fieldId) {
		this.fieldId = fieldId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getFieldLabel() {
		return fieldLabel;
	}
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
	public String getQueryEnabled() {
		return queryEnabled;
	}
	public void setQueryEnabled(String queryEnabled) {
		this.queryEnabled = queryEnabled;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getReferenceCodeList() {
		return referenceCodeList;
	}
	public void setReferenceCodeList(String referenceCodeList) {
		this.referenceCodeList = referenceCodeList;
	}
	public long getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}
	public String getNotifyEnabled() {
		return notifyEnabled;
	}
	public void setNotifyEnabled(String notifyEnabled) {
		this.notifyEnabled = notifyEnabled;
	}
	public String getSubQuery() {
		return subQuery;
	}
	public void setSubQuery(String subQuery) {
		this.subQuery = subQuery;
	}
	
	
}

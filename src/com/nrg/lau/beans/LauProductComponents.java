package com.nrg.lau.beans;

import java.io.Serializable;

public class LauProductComponents implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long PRODUCT_COMPONENT_ID = 0;
	private long PRODUCT_ID = 0;
	private long REPORT_ID = 0;
	private String COMPONENT_UDI = "";
	private String COMPONENT_DUNS_NUMBER = "";
	private String CUSTOM_TEXT_01 = "";
	private String CUSTOM_TEXT_02 = "";
	private String CUSTOM_TEXT_03 = "";
	private String CUSTOM_TEXT_04 = "";
	private String CUSTOM_TEXT_05 = "";
	private String CUSTOM_TEXT_06 = "";
	private String CUSTOM_TEXT_07 = "";
	private String CUSTOM_TEXT_08 = "";
	private String CUSTOM_TEXT_09 = "";
	private String CUSTOM_TEXT_10 = "";
	private String CUSTOM_DATE_01 = "";
	private String CUSTOM_DATE_02 = "";
	private String CUSTOM_DATE_03 = "";
	private String CUSTOM_DATE_04 = "";
	private String CUSTOM_DATE_05 = "";
	private String CUSTOM_COMMENT_01 = "";
	private String CUSTOM_COMMENT_02 = "";
	private String UPDATE_USER_ID = "";
	private Object UPDATE_TIMESTAMP;
	private String CODING_SOURCE = "";
	private String COMPONENT_CODE = "";
	private long transactionType = -1;
	private String productType = "";
	private String DISPLAY_NUMBER = "";
	private String productName = "";
	private String INTERNAL_LINK_ID="";
	
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getDISPLAY_NUMBER() {
		return DISPLAY_NUMBER;
	}
	public void setDISPLAY_NUMBER(String dISPLAY_NUMBER) {
		DISPLAY_NUMBER = dISPLAY_NUMBER;
	}
	public long getPRODUCT_COMPONENT_ID() {
		return PRODUCT_COMPONENT_ID;
	}
	public void setPRODUCT_COMPONENT_ID(long pRODUCT_COMPONENT_ID) {
		PRODUCT_COMPONENT_ID = pRODUCT_COMPONENT_ID;
	}
	public long getPRODUCT_ID() {
		return PRODUCT_ID;
	}
	public void setPRODUCT_ID(long pRODUCT_ID) {
		PRODUCT_ID = pRODUCT_ID;
	}
	public long getREPORT_ID() {
		return REPORT_ID;
	}
	public void setREPORT_ID(long rEPORT_ID) {
		REPORT_ID = rEPORT_ID;
	}
	public String getCOMPONENT_UDI() {
		return COMPONENT_UDI;
	}
	public void setCOMPONENT_UDI(String cOMPONENT_UDI) {
		COMPONENT_UDI = cOMPONENT_UDI;
	}
	public String getCOMPONENT_DUNS_NUMBER() {
		return COMPONENT_DUNS_NUMBER;
	}
	public void setCOMPONENT_DUNS_NUMBER(String cOMPONENT_DUNS_NUMBER) {
		COMPONENT_DUNS_NUMBER = cOMPONENT_DUNS_NUMBER;
	}
	public String getCUSTOM_TEXT_01() {
		return CUSTOM_TEXT_01;
	}
	public void setCUSTOM_TEXT_01(String cUSTOM_TEXT_01) {
		CUSTOM_TEXT_01 = cUSTOM_TEXT_01;
	}
	public String getCUSTOM_TEXT_02() {
		return CUSTOM_TEXT_02;
	}
	public void setCUSTOM_TEXT_02(String cUSTOM_TEXT_02) {
		CUSTOM_TEXT_02 = cUSTOM_TEXT_02;
	}
	public String getCUSTOM_TEXT_03() {
		return CUSTOM_TEXT_03;
	}
	public void setCUSTOM_TEXT_03(String cUSTOM_TEXT_03) {
		CUSTOM_TEXT_03 = cUSTOM_TEXT_03;
	}
	public String getCUSTOM_TEXT_04() {
		return CUSTOM_TEXT_04;
	}
	public void setCUSTOM_TEXT_04(String cUSTOM_TEXT_04) {
		CUSTOM_TEXT_04 = cUSTOM_TEXT_04;
	}
	public String getCUSTOM_TEXT_05() {
		return CUSTOM_TEXT_05;
	}
	public void setCUSTOM_TEXT_05(String cUSTOM_TEXT_05) {
		CUSTOM_TEXT_05 = cUSTOM_TEXT_05;
	}
	public String getCUSTOM_TEXT_06() {
		return CUSTOM_TEXT_06;
	}
	public void setCUSTOM_TEXT_06(String cUSTOM_TEXT_06) {
		CUSTOM_TEXT_06 = cUSTOM_TEXT_06;
	}
	public String getCUSTOM_TEXT_07() {
		return CUSTOM_TEXT_07;
	}
	public void setCUSTOM_TEXT_07(String cUSTOM_TEXT_07) {
		CUSTOM_TEXT_07 = cUSTOM_TEXT_07;
	}
	public String getCUSTOM_TEXT_08() {
		return CUSTOM_TEXT_08;
	}
	public void setCUSTOM_TEXT_08(String cUSTOM_TEXT_08) {
		CUSTOM_TEXT_08 = cUSTOM_TEXT_08;
	}
	public String getCUSTOM_TEXT_09() {
		return CUSTOM_TEXT_09;
	}
	public void setCUSTOM_TEXT_09(String cUSTOM_TEXT_09) {
		CUSTOM_TEXT_09 = cUSTOM_TEXT_09;
	}
	public String getCUSTOM_TEXT_10() {
		return CUSTOM_TEXT_10;
	}
	public void setCUSTOM_TEXT_10(String cUSTOM_TEXT_10) {
		CUSTOM_TEXT_10 = cUSTOM_TEXT_10;
	}
	public String getCUSTOM_DATE_01() {
		return CUSTOM_DATE_01;
	}
	public void setCUSTOM_DATE_01(String cUSTOM_DATE_01) {
		CUSTOM_DATE_01 = cUSTOM_DATE_01;
	}
	public String getCUSTOM_DATE_02() {
		return CUSTOM_DATE_02;
	}
	public void setCUSTOM_DATE_02(String cUSTOM_DATE_02) {
		CUSTOM_DATE_02 = cUSTOM_DATE_02;
	}
	public String getCUSTOM_DATE_03() {
		return CUSTOM_DATE_03;
	}
	public void setCUSTOM_DATE_03(String cUSTOM_DATE_03) {
		CUSTOM_DATE_03 = cUSTOM_DATE_03;
	}
	public String getCUSTOM_DATE_04() {
		return CUSTOM_DATE_04;
	}
	public void setCUSTOM_DATE_04(String cUSTOM_DATE_04) {
		CUSTOM_DATE_04 = cUSTOM_DATE_04;
	}
	public String getCUSTOM_DATE_05() {
		return CUSTOM_DATE_05;
	}
	public void setCUSTOM_DATE_05(String cUSTOM_DATE_05) {
		CUSTOM_DATE_05 = cUSTOM_DATE_05;
	}
	public String getCUSTOM_COMMENT_01() {
		return CUSTOM_COMMENT_01;
	}
	public void setCUSTOM_COMMENT_01(String cUSTOM_COMMENT_01) {
		CUSTOM_COMMENT_01 = cUSTOM_COMMENT_01;
	}
	public String getCUSTOM_COMMENT_02() {
		return CUSTOM_COMMENT_02;
	}
	public void setCUSTOM_COMMENT_02(String cUSTOM_COMMENT_02) {
		CUSTOM_COMMENT_02 = cUSTOM_COMMENT_02;
	}
	public String getUPDATE_USER_ID() {
		return UPDATE_USER_ID;
	}
	public void setUPDATE_USER_ID(String uPDATE_USER_ID) {
		UPDATE_USER_ID = uPDATE_USER_ID;
	}
	public Object getUPDATE_TIMESTAMP() {
		return UPDATE_TIMESTAMP;
	}
	public void setUPDATE_TIMESTAMP(Object uPDATE_TIMESTAMP) {
		UPDATE_TIMESTAMP = uPDATE_TIMESTAMP;
	}
	public String getCODING_SOURCE() {
		return CODING_SOURCE;
	}
	public void setCODING_SOURCE(String cODING_SOURCE) {
		CODING_SOURCE = cODING_SOURCE;
	}
	public String getCOMPONENT_CODE() {
		return COMPONENT_CODE;
	}
	public void setCOMPONENT_CODE(String cOMPONENT_CODE) {
		COMPONENT_CODE = cOMPONENT_CODE;
	}
	public long getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getINTERNAL_LINK_ID() {
		return INTERNAL_LINK_ID;
	}
	public void setINTERNAL_LINK_ID(String iNTERNAL_LINK_ID) {
		INTERNAL_LINK_ID = iNTERNAL_LINK_ID;
	}
	
}
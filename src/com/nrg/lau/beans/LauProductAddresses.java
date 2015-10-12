package com.nrg.lau.beans;

import java.io.Serializable;

public class LauProductAddresses implements Serializable{

	private static final long serialVersionUID = 1L;
	private long PRODUCT_ADDRESS_ID = 0;
	private long PRODUCT_ID = 0;
	private long REPORT_ID = 0;
	private long DISPLAY_NUMBER = 0;
	private String PRODUCT_ADDRESS_TYPE = "";
	private String PROD_ADDRESSEE_TITLE = "";
	private String PROD_ADDRESSEE_FIRST_NAME = "";
	private String PROD_ADDRESSEE_LAST_NAME = "";
	private String PRODUCT_EMAIL = "";
	private String PROD_ADDRESSEE_CO_NAME = "";
	private String PRODUCT_DEPARTMENT2 = "";
	private String PRODUCT_ORGANIZATION2 = "";
	private String PRODUCT_ADDRESS1 = "";
	private String PRODUCT_ADDRESS2 = "";
	private String PRODUCT_ADDRESS3 = "";
	private String PRODUCT_ADDRESS4 = "";
	private String PRODUCT_CITY = "";
	private String PRODUCT_STATE = "";
	private String PRODUCT_POSTAL_CODE = "";
	private String PRODUCT_COUNTRY = "";
	private String PROD_ADDRESSEE_PHONE_CC = "";
	private String PROD_ADDRESSEE_PHONE_NO = "";
	private String PROD_ADDRESSEE_PHONE_EXT = "";
	private String PROD_ADDRESSEE_MIDDLE_NAME2 = "";
	private String PROD_ADDRESSEE_MOBILE_CC2 = "";
	private String PROD_ADDRESSEE_MOBILE_NO2 = "";
	private String PROD_ADDRESSEE_MOBILE_EXT2 = "";
	private String PROD_ADDRESSEE_FAX_CC2 = "";
	private String PROD_ADDRESSEE_FAX_NO2 = "";
	private String PROD_ADDRESSEE_FAX_EXT2 = "";
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
	private String PROD_ADDRESSEE_MIDDLE_NAME = "";
	private String PRODUCT_ORGANIZATION = "";
	private String PRODUCT_DEPARTMENT = "";
	private String PROD_ADDRESSEE_MOBILE_CC = "";
	private String PROD_ADDRESSEE_MOBILE_NO = "";
	private String PROD_ADDRESSEE_MOBILE_EXT = "";
	private String PROD_ADDRESSEE_FAX_CC = "";
	private String PROD_ADDRESSEE_FAX_NO = "";
	private String PROD_ADDRESSEE_FAX_EXT = "";
	private String PROD_ADDRESSEE_EMAIL = "";
	private long transactionType = -1;	
	private String productName = "";
	private String productType = "";	
	private String INTERNAL_LINK_ID="";
	
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
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}	
	public long getPRODUCT_ADDRESS_ID() {
		return PRODUCT_ADDRESS_ID;
	}
	public void setPRODUCT_ADDRESS_ID(long pRODUCT_ADDRESS_ID) {
		PRODUCT_ADDRESS_ID = pRODUCT_ADDRESS_ID;
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
	public long getDISPLAY_NUMBER() {
		return DISPLAY_NUMBER;
	}
	public void setDISPLAY_NUMBER(long dISPLAY_NUMBER) {
		DISPLAY_NUMBER = dISPLAY_NUMBER;
	}
	public String getPRODUCT_ADDRESS_TYPE() {
		return PRODUCT_ADDRESS_TYPE;
	}
	public void setPRODUCT_ADDRESS_TYPE(String pRODUCT_ADDRESS_TYPE) {
		PRODUCT_ADDRESS_TYPE = pRODUCT_ADDRESS_TYPE;
	}
	public String getPROD_ADDRESSEE_TITLE() {
		return PROD_ADDRESSEE_TITLE;
	}
	public void setPROD_ADDRESSEE_TITLE(String pROD_ADDRESSEE_TITLE) {
		PROD_ADDRESSEE_TITLE = pROD_ADDRESSEE_TITLE;
	}
	public String getPROD_ADDRESSEE_FIRST_NAME() {
		return PROD_ADDRESSEE_FIRST_NAME;
	}
	public void setPROD_ADDRESSEE_FIRST_NAME(String pROD_ADDRESSEE_FIRST_NAME) {
		PROD_ADDRESSEE_FIRST_NAME = pROD_ADDRESSEE_FIRST_NAME;
	}
	public String getPROD_ADDRESSEE_LAST_NAME() {
		return PROD_ADDRESSEE_LAST_NAME;
	}
	public void setPROD_ADDRESSEE_LAST_NAME(String pROD_ADDRESSEE_LAST_NAME) {
		PROD_ADDRESSEE_LAST_NAME = pROD_ADDRESSEE_LAST_NAME;
	}
	public String getPRODUCT_EMAIL() {
		return PRODUCT_EMAIL;
	}
	public void setPRODUCT_EMAIL(String pRODUCT_EMAIL) {
		PRODUCT_EMAIL = pRODUCT_EMAIL;
	}
	public String getPROD_ADDRESSEE_CO_NAME() {
		return PROD_ADDRESSEE_CO_NAME;
	}
	public void setPROD_ADDRESSEE_CO_NAME(String pROD_ADDRESSEE_CO_NAME) {
		PROD_ADDRESSEE_CO_NAME = pROD_ADDRESSEE_CO_NAME;
	}
	public String getPRODUCT_DEPARTMENT2() {
		return PRODUCT_DEPARTMENT2;
	}
	public void setPRODUCT_DEPARTMENT2(String pRODUCT_DEPARTMENT2) {
		PRODUCT_DEPARTMENT2 = pRODUCT_DEPARTMENT2;
	}
	public String getPRODUCT_ORGANIZATION2() {
		return PRODUCT_ORGANIZATION2;
	}
	public void setPRODUCT_ORGANIZATION2(String pRODUCT_ORGANIZATION2) {
		PRODUCT_ORGANIZATION2 = pRODUCT_ORGANIZATION2;
	}
	public String getPRODUCT_ADDRESS1() {
		return PRODUCT_ADDRESS1;
	}
	public void setPRODUCT_ADDRESS1(String pRODUCT_ADDRESS1) {
		PRODUCT_ADDRESS1 = pRODUCT_ADDRESS1;
	}
	public String getPRODUCT_ADDRESS2() {
		return PRODUCT_ADDRESS2;
	}
	public void setPRODUCT_ADDRESS2(String pRODUCT_ADDRESS2) {
		PRODUCT_ADDRESS2 = pRODUCT_ADDRESS2;
	}
	public String getPRODUCT_ADDRESS3() {
		return PRODUCT_ADDRESS3;
	}
	public void setPRODUCT_ADDRESS3(String pRODUCT_ADDRESS3) {
		PRODUCT_ADDRESS3 = pRODUCT_ADDRESS3;
	}
	public String getPRODUCT_ADDRESS4() {
		return PRODUCT_ADDRESS4;
	}
	public void setPRODUCT_ADDRESS4(String pRODUCT_ADDRESS4) {
		PRODUCT_ADDRESS4 = pRODUCT_ADDRESS4;
	}
	public String getPRODUCT_CITY() {
		return PRODUCT_CITY;
	}
	public void setPRODUCT_CITY(String pRODUCT_CITY) {
		PRODUCT_CITY = pRODUCT_CITY;
	}
	public String getPRODUCT_STATE() {
		return PRODUCT_STATE;
	}
	public void setPRODUCT_STATE(String pRODUCT_STATE) {
		PRODUCT_STATE = pRODUCT_STATE;
	}
	public String getPRODUCT_POSTAL_CODE() {
		return PRODUCT_POSTAL_CODE;
	}
	public void setPRODUCT_POSTAL_CODE(String pRODUCT_POSTAL_CODE) {
		PRODUCT_POSTAL_CODE = pRODUCT_POSTAL_CODE;
	}
	public String getPRODUCT_COUNTRY() {
		return PRODUCT_COUNTRY;
	}
	public void setPRODUCT_COUNTRY(String pRODUCT_COUNTRY) {
		PRODUCT_COUNTRY = pRODUCT_COUNTRY;
	}
	public String getPROD_ADDRESSEE_PHONE_CC() {
		return PROD_ADDRESSEE_PHONE_CC;
	}
	public void setPROD_ADDRESSEE_PHONE_CC(String pROD_ADDRESSEE_PHONE_CC) {
		PROD_ADDRESSEE_PHONE_CC = pROD_ADDRESSEE_PHONE_CC;
	}
	public String getPROD_ADDRESSEE_PHONE_NO() {
		return PROD_ADDRESSEE_PHONE_NO;
	}
	public void setPROD_ADDRESSEE_PHONE_NO(String pROD_ADDRESSEE_PHONE_NO) {
		PROD_ADDRESSEE_PHONE_NO = pROD_ADDRESSEE_PHONE_NO;
	}
	public String getPROD_ADDRESSEE_PHONE_EXT() {
		return PROD_ADDRESSEE_PHONE_EXT;
	}
	public void setPROD_ADDRESSEE_PHONE_EXT(String pROD_ADDRESSEE_PHONE_EXT) {
		PROD_ADDRESSEE_PHONE_EXT = pROD_ADDRESSEE_PHONE_EXT;
	}
	public String getPROD_ADDRESSEE_MIDDLE_NAME2() {
		return PROD_ADDRESSEE_MIDDLE_NAME2;
	}
	public void setPROD_ADDRESSEE_MIDDLE_NAME2(String pROD_ADDRESSEE_MIDDLE_NAME2) {
		PROD_ADDRESSEE_MIDDLE_NAME2 = pROD_ADDRESSEE_MIDDLE_NAME2;
	}
	public String getPROD_ADDRESSEE_MOBILE_CC2() {
		return PROD_ADDRESSEE_MOBILE_CC2;
	}
	public void setPROD_ADDRESSEE_MOBILE_CC2(String pROD_ADDRESSEE_MOBILE_CC2) {
		PROD_ADDRESSEE_MOBILE_CC2 = pROD_ADDRESSEE_MOBILE_CC2;
	}
	public String getPROD_ADDRESSEE_MOBILE_NO2() {
		return PROD_ADDRESSEE_MOBILE_NO2;
	}
	public void setPROD_ADDRESSEE_MOBILE_NO2(String pROD_ADDRESSEE_MOBILE_NO2) {
		PROD_ADDRESSEE_MOBILE_NO2 = pROD_ADDRESSEE_MOBILE_NO2;
	}
	public String getPROD_ADDRESSEE_MOBILE_EXT2() {
		return PROD_ADDRESSEE_MOBILE_EXT2;
	}
	public void setPROD_ADDRESSEE_MOBILE_EXT2(String pROD_ADDRESSEE_MOBILE_EXT2) {
		PROD_ADDRESSEE_MOBILE_EXT2 = pROD_ADDRESSEE_MOBILE_EXT2;
	}
	public String getPROD_ADDRESSEE_FAX_CC2() {
		return PROD_ADDRESSEE_FAX_CC2;
	}
	public void setPROD_ADDRESSEE_FAX_CC2(String pROD_ADDRESSEE_FAX_CC2) {
		PROD_ADDRESSEE_FAX_CC2 = pROD_ADDRESSEE_FAX_CC2;
	}
	public String getPROD_ADDRESSEE_FAX_NO2() {
		return PROD_ADDRESSEE_FAX_NO2;
	}
	public void setPROD_ADDRESSEE_FAX_NO2(String pROD_ADDRESSEE_FAX_NO2) {
		PROD_ADDRESSEE_FAX_NO2 = pROD_ADDRESSEE_FAX_NO2;
	}
	public String getPROD_ADDRESSEE_FAX_EXT2() {
		return PROD_ADDRESSEE_FAX_EXT2;
	}
	public void setPROD_ADDRESSEE_FAX_EXT2(String pROD_ADDRESSEE_FAX_EXT2) {
		PROD_ADDRESSEE_FAX_EXT2 = pROD_ADDRESSEE_FAX_EXT2;
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
	public String getPROD_ADDRESSEE_MIDDLE_NAME() {
		return PROD_ADDRESSEE_MIDDLE_NAME;
	}
	public void setPROD_ADDRESSEE_MIDDLE_NAME(String pROD_ADDRESSEE_MIDDLE_NAME) {
		PROD_ADDRESSEE_MIDDLE_NAME = pROD_ADDRESSEE_MIDDLE_NAME;
	}
	public String getPRODUCT_ORGANIZATION() {
		return PRODUCT_ORGANIZATION;
	}
	public void setPRODUCT_ORGANIZATION(String pRODUCT_ORGANIZATION) {
		PRODUCT_ORGANIZATION = pRODUCT_ORGANIZATION;
	}
	public String getPRODUCT_DEPARTMENT() {
		return PRODUCT_DEPARTMENT;
	}
	public void setPRODUCT_DEPARTMENT(String pRODUCT_DEPARTMENT) {
		PRODUCT_DEPARTMENT = pRODUCT_DEPARTMENT;
	}
	public String getPROD_ADDRESSEE_MOBILE_CC() {
		return PROD_ADDRESSEE_MOBILE_CC;
	}
	public void setPROD_ADDRESSEE_MOBILE_CC(String pROD_ADDRESSEE_MOBILE_CC) {
		PROD_ADDRESSEE_MOBILE_CC = pROD_ADDRESSEE_MOBILE_CC;
	}
	public String getPROD_ADDRESSEE_MOBILE_NO() {
		return PROD_ADDRESSEE_MOBILE_NO;
	}
	public void setPROD_ADDRESSEE_MOBILE_NO(String pROD_ADDRESSEE_MOBILE_NO) {
		PROD_ADDRESSEE_MOBILE_NO = pROD_ADDRESSEE_MOBILE_NO;
	}
	public String getPROD_ADDRESSEE_MOBILE_EXT() {
		return PROD_ADDRESSEE_MOBILE_EXT;
	}
	public void setPROD_ADDRESSEE_MOBILE_EXT(String pROD_ADDRESSEE_MOBILE_EXT) {
		PROD_ADDRESSEE_MOBILE_EXT = pROD_ADDRESSEE_MOBILE_EXT;
	}
	public String getPROD_ADDRESSEE_FAX_CC() {
		return PROD_ADDRESSEE_FAX_CC;
	}
	public void setPROD_ADDRESSEE_FAX_CC(String pROD_ADDRESSEE_FAX_CC) {
		PROD_ADDRESSEE_FAX_CC = pROD_ADDRESSEE_FAX_CC;
	}
	public String getPROD_ADDRESSEE_FAX_NO() {
		return PROD_ADDRESSEE_FAX_NO;
	}
	public void setPROD_ADDRESSEE_FAX_NO(String pROD_ADDRESSEE_FAX_NO) {
		PROD_ADDRESSEE_FAX_NO = pROD_ADDRESSEE_FAX_NO;
	}
	public String getPROD_ADDRESSEE_FAX_EXT() {
		return PROD_ADDRESSEE_FAX_EXT;
	}
	public void setPROD_ADDRESSEE_FAX_EXT(String pROD_ADDRESSEE_FAX_EXT) {
		PROD_ADDRESSEE_FAX_EXT = pROD_ADDRESSEE_FAX_EXT;
	}
	public String getPROD_ADDRESSEE_EMAIL() {
		return PROD_ADDRESSEE_EMAIL;
	}
	public void setPROD_ADDRESSEE_EMAIL(String pROD_ADDRESSEE_EMAIL) {
		PROD_ADDRESSEE_EMAIL = pROD_ADDRESSEE_EMAIL;
	}
	public String getINTERNAL_LINK_ID() {
		return INTERNAL_LINK_ID;
	}
	public void setINTERNAL_LINK_ID(String iNTERNAL_LINK_ID) {
		INTERNAL_LINK_ID = iNTERNAL_LINK_ID;
	}	
	
}

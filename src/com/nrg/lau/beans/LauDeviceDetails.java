package com.nrg.lau.beans;

import java.io.Serializable;

public class LauDeviceDetails implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long DEVICE_DETAILS_ID = 0;
	private long PRODUCT_ID = 0;
	private long REPORT_ID = 0;
	private String MODEL_NUMBER = "";
	private String CATALOG_NUMBER = "";
	private String SERIAL_NUMBER = "";
	private String LOT_NUMBER = "";
	private String EXPIRATION_DATE = "";
	private String OTHER_DEVICE_NUMBER = "";
	private String DEVICE_OPERATOR_HP = "";
	private String DEVICE_OPERATOR_PT = "";
	private String DEVICE_OPERATOR_OTHER = "";
	private String DEVICE_OPERATOR_OTHER_SPECIFY = "";
	private String DEVICE_OPERATOR = "";
	private String IMPLANT_DATE = "";
	private String EXPLANT_DATE = "";
	private String MANUFACTURED_DATE = "";
	private String SINGLE_USE_DEVICE = "";
	private String SINGLE_USE_DEVICE_REPROCESSED = "";
	private String DEVICE_AVAILABLE_FOR_EVAL = "";
	private String DEVICE_RETURN_DATE = "";
	private String DISTRIBUTOR_REPORT_NUMBER = "";
	private String DEVICE_USAGE = "";
	private String REMEDIAL_ACTION_TAKEN = "";
	private String REMEDIAL_ACTION_OTHER = "";
	private String DEVICE_EVAL_BY_MFG = "";
	private String DEVICE_EVAL_ATTACHED_FLAG = "";
	private String DEV_NOT_EVAL_REASON = "";
	private String DISTRIBUTOR_AWARE_DATE = "";
	private String DISTRIBUTOR_REPORT_TYPE = "";
	private String DISTRIBUTOR_FOLLOWUP_NO = "";
	private String DISTRIBUTOR_REPORT_DATE = "";
	private String DEVICE_AGE = "";
	private String DEVICE_AGE_UNITS = "";
	private String DEVICE_AGE_TEXT = "";
	private String PACKAGING_SAVED = "";
	private String LABORATORY_DEVICE_OR_TEST = "";
	private String CORRECTIVE_ACTION_NUMBER = "";
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
	private long transactionType = -1;	
	private String productName = "";
	private String productType = "";
	private String productDisplayNumber = "";
	private String DISPLAY_NUMBER = "";
	private String INTERNAL_LINK_ID="";
	
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
	public String getMODEL_NUMBER() {
		return MODEL_NUMBER;
	}
	public void setMODEL_NUMBER(String mODEL_NUMBER) {
		MODEL_NUMBER = mODEL_NUMBER;
	}
	public String getCATALOG_NUMBER() {
		return CATALOG_NUMBER;
	}
	public void setCATALOG_NUMBER(String cATALOG_NUMBER) {
		CATALOG_NUMBER = cATALOG_NUMBER;
	}
	public String getSERIAL_NUMBER() {
		return SERIAL_NUMBER;
	}
	public void setSERIAL_NUMBER(String sERIAL_NUMBER) {
		SERIAL_NUMBER = sERIAL_NUMBER;
	}
	public String getLOT_NUMBER() {
		return LOT_NUMBER;
	}
	public void setLOT_NUMBER(String lOT_NUMBER) {
		LOT_NUMBER = lOT_NUMBER;
	}
	public String getEXPIRATION_DATE() {
		return EXPIRATION_DATE;
	}
	public void setEXPIRATION_DATE(String eXPIRATION_DATE) {
		EXPIRATION_DATE = eXPIRATION_DATE;
	}
	public String getOTHER_DEVICE_NUMBER() {
		return OTHER_DEVICE_NUMBER;
	}
	public void setOTHER_DEVICE_NUMBER(String oTHER_DEVICE_NUMBER) {
		OTHER_DEVICE_NUMBER = oTHER_DEVICE_NUMBER;
	}
	public String getDEVICE_OPERATOR_HP() {
		return DEVICE_OPERATOR_HP;
	}
	public void setDEVICE_OPERATOR_HP(String dEVICE_OPERATOR_HP) {
		DEVICE_OPERATOR_HP = dEVICE_OPERATOR_HP;
	}
	public String getDEVICE_OPERATOR_PT() {
		return DEVICE_OPERATOR_PT;
	}
	public void setDEVICE_OPERATOR_PT(String dEVICE_OPERATOR_PT) {
		DEVICE_OPERATOR_PT = dEVICE_OPERATOR_PT;
	}
	public String getDEVICE_OPERATOR_OTHER() {
		return DEVICE_OPERATOR_OTHER;
	}
	public void setDEVICE_OPERATOR_OTHER(String dEVICE_OPERATOR_OTHER) {
		DEVICE_OPERATOR_OTHER = dEVICE_OPERATOR_OTHER;
	}
	public String getDEVICE_OPERATOR() {
		return DEVICE_OPERATOR;
	}
	public void setDEVICE_OPERATOR(String dEVICE_OPERATOR) {
		DEVICE_OPERATOR = dEVICE_OPERATOR;
	}
	public String getDEVICE_OPERATOR_OTHER_SPECIFY() {
		return DEVICE_OPERATOR_OTHER_SPECIFY;
	}
	public void setDEVICE_OPERATOR_OTHER_SPECIFY(
			String dEVICE_OPERATOR_OTHER_SPECIFY) {
		DEVICE_OPERATOR_OTHER_SPECIFY = dEVICE_OPERATOR_OTHER_SPECIFY;
	}
	public String getIMPLANT_DATE() {
		return IMPLANT_DATE;
	}
	public void setIMPLANT_DATE(String iMPLANT_DATE) {
		IMPLANT_DATE = iMPLANT_DATE;
	}
	public String getEXPLANT_DATE() {
		return EXPLANT_DATE;
	}
	public void setEXPLANT_DATE(String eXPLANT_DATE) {
		EXPLANT_DATE = eXPLANT_DATE;
	}
	public String getMANUFACTURED_DATE() {
		return MANUFACTURED_DATE;
	}
	public void setMANUFACTURED_DATE(String mANUFACTURED_DATE) {
		MANUFACTURED_DATE = mANUFACTURED_DATE;
	}
	public String getSINGLE_USE_DEVICE() {
		return SINGLE_USE_DEVICE;
	}
	public void setSINGLE_USE_DEVICE(String sINGLE_USE_DEVICE) {
		SINGLE_USE_DEVICE = sINGLE_USE_DEVICE;
	}
	public String getSINGLE_USE_DEVICE_REPROCESSED() {
		return SINGLE_USE_DEVICE_REPROCESSED;
	}
	public void setSINGLE_USE_DEVICE_REPROCESSED(
			String sINGLE_USE_DEVICE_REPROCESSED) {
		SINGLE_USE_DEVICE_REPROCESSED = sINGLE_USE_DEVICE_REPROCESSED;
	}
	public String getDEVICE_AVAILABLE_FOR_EVAL() {
		return DEVICE_AVAILABLE_FOR_EVAL;
	}
	public void setDEVICE_AVAILABLE_FOR_EVAL(String dEVICE_AVAILABLE_FOR_EVAL) {
		DEVICE_AVAILABLE_FOR_EVAL = dEVICE_AVAILABLE_FOR_EVAL;
	}
	public String getDEVICE_RETURN_DATE() {
		return DEVICE_RETURN_DATE;
	}
	public void setDEVICE_RETURN_DATE(String dEVICE_RETURN_DATE) {
		DEVICE_RETURN_DATE = dEVICE_RETURN_DATE;
	}
	public String getDISTRIBUTOR_REPORT_NUMBER() {
		return DISTRIBUTOR_REPORT_NUMBER;
	}
	public void setDISTRIBUTOR_REPORT_NUMBER(String dISTRIBUTOR_REPORT_NUMBER) {
		DISTRIBUTOR_REPORT_NUMBER = dISTRIBUTOR_REPORT_NUMBER;
	}
	public String getDEVICE_USAGE() {
		return DEVICE_USAGE;
	}
	public void setDEVICE_USAGE(String dEVICE_USAGE) {
		DEVICE_USAGE = dEVICE_USAGE;
	}
	public String getREMEDIAL_ACTION_TAKEN() {
		return REMEDIAL_ACTION_TAKEN;
	}
	public void setREMEDIAL_ACTION_TAKEN(String rEMEDIAL_ACTION_TAKEN) {
		REMEDIAL_ACTION_TAKEN = rEMEDIAL_ACTION_TAKEN;
	}
	public String getREMEDIAL_ACTION_OTHER() {
		return REMEDIAL_ACTION_OTHER;
	}
	public void setREMEDIAL_ACTION_OTHER(String rEMEDIAL_ACTION_OTHER) {
		REMEDIAL_ACTION_OTHER = rEMEDIAL_ACTION_OTHER;
	}
	public String getDEVICE_EVAL_BY_MFG() {
		return DEVICE_EVAL_BY_MFG;
	}
	public void setDEVICE_EVAL_BY_MFG(String dEVICE_EVAL_BY_MFG) {
		DEVICE_EVAL_BY_MFG = dEVICE_EVAL_BY_MFG;
	}
	public String getDEVICE_EVAL_ATTACHED_FLAG() {
		return DEVICE_EVAL_ATTACHED_FLAG;
	}
	public void setDEVICE_EVAL_ATTACHED_FLAG(String dEVICE_EVAL_ATTACHED_FLAG) {
		DEVICE_EVAL_ATTACHED_FLAG = dEVICE_EVAL_ATTACHED_FLAG;
	}
	public String getDEV_NOT_EVAL_REASON() {
		return DEV_NOT_EVAL_REASON;
	}
	public void setDEV_NOT_EVAL_REASON(String dEV_NOT_EVAL_REASON) {
		DEV_NOT_EVAL_REASON = dEV_NOT_EVAL_REASON;
	}
	public String getDISTRIBUTOR_AWARE_DATE() {
		return DISTRIBUTOR_AWARE_DATE;
	}
	public void setDISTRIBUTOR_AWARE_DATE(String dISTRIBUTOR_AWARE_DATE) {
		DISTRIBUTOR_AWARE_DATE = dISTRIBUTOR_AWARE_DATE;
	}
	public String getDISTRIBUTOR_REPORT_TYPE() {
		return DISTRIBUTOR_REPORT_TYPE;
	}
	public void setDISTRIBUTOR_REPORT_TYPE(String dISTRIBUTOR_REPORT_TYPE) {
		DISTRIBUTOR_REPORT_TYPE = dISTRIBUTOR_REPORT_TYPE;
	}
	public String getDISTRIBUTOR_FOLLOWUP_NO() {
		return DISTRIBUTOR_FOLLOWUP_NO;
	}
	public void setDISTRIBUTOR_FOLLOWUP_NO(String dISTRIBUTOR_FOLLOWUP_NO) {
		DISTRIBUTOR_FOLLOWUP_NO = dISTRIBUTOR_FOLLOWUP_NO;
	}
	public String getDISTRIBUTOR_REPORT_DATE() {
		return DISTRIBUTOR_REPORT_DATE;
	}
	public void setDISTRIBUTOR_REPORT_DATE(String dISTRIBUTOR_REPORT_DATE) {
		DISTRIBUTOR_REPORT_DATE = dISTRIBUTOR_REPORT_DATE;
	}
	public String getDEVICE_AGE() {
		return DEVICE_AGE;
	}
	public void setDEVICE_AGE(String dEVICE_AGE) {
		DEVICE_AGE = dEVICE_AGE;
	}
	public String getDEVICE_AGE_UNITS() {
		return DEVICE_AGE_UNITS;
	}
	public void setDEVICE_AGE_UNITS(String dEVICE_AGE_UNITS) {
		DEVICE_AGE_UNITS = dEVICE_AGE_UNITS;
	}
	public String getDEVICE_AGE_TEXT() {
		return DEVICE_AGE_TEXT;
	}
	public void setDEVICE_AGE_TEXT(String dEVICE_AGE_TEXT) {
		DEVICE_AGE_TEXT = dEVICE_AGE_TEXT;
	}
	public String getPACKAGING_SAVED() {
		return PACKAGING_SAVED;
	}
	public void setPACKAGING_SAVED(String pACKAGING_SAVED) {
		PACKAGING_SAVED = pACKAGING_SAVED;
	}
	public String getLABORATORY_DEVICE_OR_TEST() {
		return LABORATORY_DEVICE_OR_TEST;
	}
	public void setLABORATORY_DEVICE_OR_TEST(String lABORATORY_DEVICE_OR_TEST) {
		LABORATORY_DEVICE_OR_TEST = lABORATORY_DEVICE_OR_TEST;
	}
	public String getCORRECTIVE_ACTION_NUMBER() {
		return CORRECTIVE_ACTION_NUMBER;
	}
	public void setCORRECTIVE_ACTION_NUMBER(String cORRECTIVE_ACTION_NUMBER) {
		CORRECTIVE_ACTION_NUMBER = cORRECTIVE_ACTION_NUMBER;
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
	public long getDEVICE_DETAILS_ID() {
		return DEVICE_DETAILS_ID;
	}
	public void setDEVICE_DETAILS_ID(long dEVICE_DETAILS_ID) {
		DEVICE_DETAILS_ID = dEVICE_DETAILS_ID;
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
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductDisplayNumber() {
		return productDisplayNumber;
	}
	public void setProductDisplayNumber(String productDisplayNumber) {
		this.productDisplayNumber = productDisplayNumber;
	}
	public String getDISPLAY_NUMBER() {
		return DISPLAY_NUMBER;
	}
	public void setDISPLAY_NUMBER(String dISPLAY_NUMBER) {
		DISPLAY_NUMBER = dISPLAY_NUMBER;
	}
	public String getINTERNAL_LINK_ID() {
		return INTERNAL_LINK_ID;
	}
	public void setINTERNAL_LINK_ID(String iNTERNAL_LINK_ID) {
		INTERNAL_LINK_ID = iNTERNAL_LINK_ID;
	}
	

}

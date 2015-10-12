package com.nrg.lau.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import com.nrg.lau.commons.IReportsConstants;

public class LauProducts implements Serializable {

	private long productId = 0;
	private String productIdList = "";
	private String productName = "";
	private BigDecimal displayNumber = IReportsConstants.ZERO;
	private String displayNumber2 = "";
	private String productType = "";
	private String actionTaken = "";	
	private String ndcNumber = "";
	private String strength = "";
	private String indicationVerbatim = "";
	private String sampleDisposition = "";
	private String reimbursementType = "";
	private String reimbursementTo = "";
	private String reimbursementQuantity = "";
	private String reimbursementAmount = "";
	private String reimbursementAccount = "";
	private String deaNumber = "";	
	private String updateUserId = "";
	private String marketedProductType="";
	private String tradeName="";
	private Object updateTimeStamp;
	private long reportId = 0;
	private long transactionType = -1;
	private String genericName = "";
	private String prodCode = "";
	private String INTERNAL_LINK_ID="";
	private String GLOBAL_UNIQUE_ID="";
	private String PRODUCT_REVIEW_STATUS = "";
	private String PRODUCT_REVIEW_USER_ID = "";
	private String PRODUCT_REVIEW_DATE = "";
	private String COMBINATION_PRODUCT = "";

	private static final long serialVersionUID = 1L;

	public long getProductId() {
		return this.productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public BigDecimal getDisplayNumber() {
		return this.displayNumber;
	}

	public void setDisplayNumber(BigDecimal displayNumber) {
		this.displayNumber = displayNumber;
	}

	public String getProductType() {
		return this.productType;
	}

	public String getMarketedProductType() {
		return marketedProductType;
	}

	public void setMarketedProductType(String marketedProductType) {
		this.marketedProductType = marketedProductType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getActionTaken() {
		return this.actionTaken;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Object getUpdateTimeStamp() {
		return this.updateTimeStamp;
	}

	public void setUpdateTimeStamp(Object updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}

	public long getReportId() {
		return this.reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	public String toString() {
		StringBuffer str = new StringBuffer(LauProducts.class.getName());
		str.append("[");
		str.append("productId=");
		str.append(productId);
		str.append(", productName=");
		str.append(productName);
		str.append(", displayNumber=");
		str.append(displayNumber);
		str.append(", productType=");
		str.append(productType);
		str.append(", actionTaken=");
		str.append(actionTaken);
		str.append(", updateUserId=");
		str.append(updateUserId);
		str.append(", updateTimeStamp=");
		str.append(updateTimeStamp);
		str.append(", reportId=");
		str.append(reportId);
		str.append(", ndcNumber=");
		str.append(ndcNumber);
		str.append(", strength=");
		str.append(strength);
		str.append(", sampleDisposition=");
		str.append(sampleDisposition);
		str.append(", reimbursementType=");
		str.append(reimbursementType);
		str.append(", reimbursementTo=");
		str.append(reimbursementTo);
		str.append(", reimbursementQuantity=");
		str.append(reimbursementQuantity);
		str.append(", reimbursementAmount=");
		str.append(reimbursementAmount);
		str.append(", reimbursementAccount=");
		str.append(reimbursementAccount);
		str.append(", indicationVerbatim=");
		str.append(indicationVerbatim);
		str.append(", deaNumber=");
		str.append(deaNumber);
		str.append(", transactionType=");
		str.append(transactionType);
		str.append(", GLOBAL_UNIQUE_ID=");
		str.append(GLOBAL_UNIQUE_ID);
		str.append(", PRODUCT_REVIEW_STATUS=");
		str.append(PRODUCT_REVIEW_STATUS);
		str.append(", PRODUCT_REVIEW_USER_ID=");
		str.append(PRODUCT_REVIEW_USER_ID);
		str.append(", PRODUCT_REVIEW_DATE=");
		str.append(PRODUCT_REVIEW_DATE);
		str.append(", COMBINATION_PRODUCT=");
		str.append(COMBINATION_PRODUCT);
		str.append("]");
		return str.toString();
	}

	public String getCOMBINATION_PRODUCT() {
		return COMBINATION_PRODUCT;
	}

	public void setCOMBINATION_PRODUCT(String cOMBINATION_PRODUCT) {
		COMBINATION_PRODUCT = cOMBINATION_PRODUCT;
	}

	public String getPRODUCT_REVIEW_STATUS() {
		return PRODUCT_REVIEW_STATUS;
	}

	public void setPRODUCT_REVIEW_STATUS(String pRODUCT_REVIEW_STATUS) {
		PRODUCT_REVIEW_STATUS = pRODUCT_REVIEW_STATUS;
	}

	public String getPRODUCT_REVIEW_USER_ID() {
		return PRODUCT_REVIEW_USER_ID;
	}

	public void setPRODUCT_REVIEW_USER_ID(String pRODUCT_REVIEW_USER_ID) {
		PRODUCT_REVIEW_USER_ID = pRODUCT_REVIEW_USER_ID;
	}

	public String getPRODUCT_REVIEW_DATE() {
		return PRODUCT_REVIEW_DATE;
	}

	public void setPRODUCT_REVIEW_DATE(String pRODUCT_REVIEW_DATE) {
		PRODUCT_REVIEW_DATE = pRODUCT_REVIEW_DATE;
	}

	public String getGLOBAL_UNIQUE_ID() {
		return GLOBAL_UNIQUE_ID;
	}

	public void setGLOBAL_UNIQUE_ID(String gLOBAL_UNIQUE_ID) {
		GLOBAL_UNIQUE_ID = gLOBAL_UNIQUE_ID;
	}

	public String getNdcNumber() {
		return ndcNumber;
	}

	public void setNdcNumber(String ndcNumber) {
		this.ndcNumber = ndcNumber;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public String getSampleDisposition() {
		return sampleDisposition;
	}

	public void setSampleDisposition(String sampleDisposition) {
		this.sampleDisposition = sampleDisposition;
	}

	public String getReimbursementType() {
		return reimbursementType;
	}

	public String getGenericName() {
		return genericName;
	}

	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public void setReimbursementType(String reimbursementType) {
		this.reimbursementType = reimbursementType;
	}

	public String getReimbursementTo() {
		return reimbursementTo;
	}

	public void setReimbursementTo(String reimbursementTo) {
		this.reimbursementTo = reimbursementTo;
	}

	public String getReimbursementQuantity() {
		return reimbursementQuantity;
	}

	public void setReimbursementQuantity(String reimbursementQuantity) {
		this.reimbursementQuantity = reimbursementQuantity;
	}

	public String getReimbursementAmount() {
		return reimbursementAmount;
	}

	public void setReimbursementAmount(String reimbursementAmount) {
		this.reimbursementAmount = reimbursementAmount;
	}

	public String getReimbursementAccount() {
		return reimbursementAccount;
	}

	public void setReimbursementAccount(String reimbursementAccount) {
		this.reimbursementAccount = reimbursementAccount;
	}

	public String getDeaNumber() {
		return deaNumber;
	}

	public void setDeaNumber(String deaNumber) {
		this.deaNumber = deaNumber;
	}

	public String getIndicationVerbatim() {
		return indicationVerbatim;
	}

	public void setIndicationVerbatim(String indicationVerbatim) {
		this.indicationVerbatim = indicationVerbatim;
	}
	
	public long getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}
	
	public String getDisplayNumber2() {
		return this.displayNumber2;
	}

	public void setDisplayNumber2(String displayNumber2) {
		if (displayNumber2.trim().length() > 0) {
			this.displayNumber = new BigDecimal(displayNumber2);
		} else
			this.displayNumber = IReportsConstants.ZERO;
	}

	public String getINTERNAL_LINK_ID() {
		return INTERNAL_LINK_ID;
	}

	public void setINTERNAL_LINK_ID(String iNTERNAL_LINK_ID) {
		INTERNAL_LINK_ID = iNTERNAL_LINK_ID;
	}

	public String getProductIdList() {
		return productIdList;
	}

	public void setProductIdList(String productIdList) {
		this.productIdList = productIdList;
	}
	
	

}

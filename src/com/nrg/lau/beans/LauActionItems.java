package com.nrg.lau.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import com.nrg.lau.commons.IReportsConstants;

public class LauActionItems implements Serializable {

	private long actionItemId = 0;
	private BigDecimal displayNumber = IReportsConstants.ZERO;
	private String displayNumber2 = "";
	private String actionItemType = "";
	private String actionItemDescription = "";
	private String priority = "";
	private String assignedToUser = "";
	private String assignedToGroup = "";
	private String dueDate = "";
	private String actionItemStatus = "";
	private String updateUserId = "";
	private Object updateTimeStamp;
	private long reportId = 0;
	private long transactionType = -1;

	private static final long serialVersionUID = 1L;

	public long getActionItemId() {
		return this.actionItemId;
	}

	public void setActionItemId(long actionItemId) {
		this.actionItemId = actionItemId;
	}

	public BigDecimal getDisplayNumber() {
		return this.displayNumber;
	}

	public void setDisplayNumber(BigDecimal displayNumber) {
		this.displayNumber = displayNumber;
	}
	
	public String getDisplayNumber2() {
		return this.displayNumber2;
	}

	public void setDisplayNumber2(String displayNumber2) {
		if(displayNumber2.trim().length() > 0)	{
			this.displayNumber = new BigDecimal(displayNumber2);
		}else this.displayNumber = IReportsConstants.ZERO;
	}

	public String getActionItemType() {
		return this.actionItemType;
	}

	public void setActionItemType(String actionItemType) {
		this.actionItemType = actionItemType;
	}

	public String getActionItemDescription() {
		return this.actionItemDescription;
	}

	public void setActionItemDescription(String actionItemDescription) {
		this.actionItemDescription = actionItemDescription;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getAssignedToUser() {
		return this.assignedToUser;
	}

	public void setAssignedToUser(String assignedToUser) {
		this.assignedToUser = assignedToUser;
	}

	public String getAssignedToGroup() {
		return this.assignedToGroup;
	}

	public void setAssignedToGroup(String assignedToGroup) {
		this.assignedToGroup = assignedToGroup;
	}

	public String getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getActionItemStatus() {
		return this.actionItemStatus;
	}

	public void setActionItemStatus(String actionItemStatus) {
		this.actionItemStatus = actionItemStatus;
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
		StringBuffer str = new StringBuffer(LauNarrativeText.class.getName());
		str.append("[");
		str.append("actionItemId=");
		str.append(actionItemId);
		str.append(", displayNumber=");
		str.append(displayNumber);
		str.append(", actionItemType=");
		str.append(actionItemType);
		str.append(", actionItemDescription=");
		str.append(actionItemDescription);
		str.append(", priority=");
		str.append(priority);
		str.append(", assignedToUser=");
		str.append(assignedToUser);
		str.append(", assignedToGroup=");
		str.append(assignedToGroup);
		str.append(", dueDate=");
		str.append(dueDate);
		str.append(", actionItemStatus=");
		str.append(actionItemStatus);
		str.append(", updateUserId=");
		str.append(updateUserId);
		str.append(", updateTimeStamp=");
		str.append(updateTimeStamp);
		str.append(", reportId=");
		str.append(reportId);
		str.append(", transactionType=");
		str.append(transactionType);
		str.append("]");
		return str.toString();
	}
	
	public long getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}

}

package com.temple.model;

import java.util.Date;

public class Donner {

	private	Integer	pkDonnerId;
	private	String	fullName;
	private	String	amount;
	private String remarks;
	private String mobileNumber;
	private	String	donnerAddress;
	private	Date	receivedDate;
	private	String	attachment;
	private	String	createUser;
	private	Date	createDate;
	private	String	updateUser;
	private	Date	updateDate;  
	private	Integer	isDeletd;
	private Boolean hasDocumnet;
	
	public Integer getPkDonnerId() {
		return pkDonnerId;
	}
	public void setPkDonnerId(Integer pkDonnerId) {
		this.pkDonnerId = pkDonnerId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDonnerAddress() {
		return donnerAddress;
	}
	public void setDonnerAddress(String donnerAddress) {
		this.donnerAddress = donnerAddress;
	}
	public Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getIsDeletd() {
		return isDeletd;
	}
	public void setIsDeletd(Integer isDeletd) {
		this.isDeletd = isDeletd;
	}
	
	public Boolean getHasDocumnet() {
		return hasDocumnet;
	}
	public void setHasDocumnet(Boolean hasDocumnet) {
		this.hasDocumnet = hasDocumnet;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Override
	public String toString() {
		return "Donner [pkDonnerId=" + pkDonnerId + ", fullName=" + fullName + ", amount=" + amount + ", donnerAddress="
				+ donnerAddress + ", receivedDate=" + receivedDate + ", attachment=" + attachment + ", createUser="
				+ createUser + ", createDate=" + createDate + ", updateUser=" + updateUser + ", updateDate="
				+ updateDate + ", isDeletd=" + isDeletd + "]";
	}

	
}



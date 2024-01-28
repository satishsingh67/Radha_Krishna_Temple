package com.temple.model;

import java.util.Date;

public class Expense {

	private	Integer	pkExpenseId;
	private	String	expenseName;
	private	Integer	expenseAmount;
	private	String	expenseDeatails;
	private	Date	expenseDate;
	private	String	attachment;
	private	String	createUser;
	private	Date	createDate;
	private	String	updateUser;
	private	Date	updateDate;
	private	Integer	isDeletd;
	private Boolean isHasDocument;

	
	public Integer getPkExpenseId() {
		return pkExpenseId;
	}
	public void setPkExpenseId(Integer pkExpenseId) {
		this.pkExpenseId = pkExpenseId;
	}
	public String getExpenseName() {
		return expenseName;
	}
	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}
	public Integer getExpenseAmount() {
		return expenseAmount;
	}
	public void setExpenseAmount(Integer expenseAmount) {
		this.expenseAmount = expenseAmount;
	}
	public String getExpenseDeatails() {
		return expenseDeatails;
	}
	public void setExpenseDeatails(String expenseDeatails) {
		this.expenseDeatails = expenseDeatails;
	}
	public Date getExpenseDate() {
		return expenseDate;
	}
	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
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
	
	public Boolean getIsHasDocument() {
		return isHasDocument;
	}
	public void setIsHasDocument(Boolean isHasDocument) {
		this.isHasDocument = isHasDocument;
	}
	
}

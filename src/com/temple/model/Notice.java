package com.temple.model;

import java.util.Date;

public class Notice {

	private Integer pkNoticeId;
	private String noticeTitle;
	private String noticeDescription;
	private String document;
	private String base64Image;
	private	Boolean	isDeleted;
	private	String	createUser;
	private	Date	createTime;
	private	String	updateUser;
	private	Date	updateTime;
	private Boolean isHasDocument;
	
	public Integer getPkNoticeId() {
		return pkNoticeId;
	}
	public void setPkNoticeId(Integer pkNoticeId) {
		this.pkNoticeId = pkNoticeId;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeDescription() {
		return noticeDescription;
	}
	public void setNoticeDescription(String noticeDescription) {
		this.noticeDescription = noticeDescription;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public String getBase64Image() {
		return base64Image;
	}
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Boolean getIsHasDocument() {
		return isHasDocument;
	}
	public void setIsHasDocument(Boolean isHasDocument) {
		this.isHasDocument = isHasDocument;
	}
	@Override
	public String toString() {
		return "Notice [pkNoticeId=" + pkNoticeId + ", noticeTitle=" + noticeTitle + ", noticeDescription="
				+ noticeDescription + ", document=" + document + ", base64Image=" + base64Image + ", isDeleted="
				+ isDeleted + "]";
	}
}

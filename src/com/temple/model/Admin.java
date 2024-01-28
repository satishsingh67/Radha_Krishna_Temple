package com.temple.model;

public class Admin {

	private Integer pkAdminId;
	private String fullName;
	private String SecurityQuestion;
	private String SecurityQuestionAnswer;
	private String password;
	private String emailId;
	private String address;
	private String base64Image;
	private	Boolean	isDeletd;
	
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Admin(Integer pkAdminId, String fullName, String emailId) {
		super();
		this.pkAdminId = pkAdminId;
		this.fullName = fullName;
		this.emailId = emailId;
	}
	public Integer getPkAdminId() {
		return pkAdminId;
	}
	public void setPkAdminId(Integer pkAdminId) {
		this.pkAdminId = pkAdminId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getSecurityQuestion() {
		return SecurityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		SecurityQuestion = securityQuestion;
	}
	public String getSecurityQuestionAnswer() {
		return SecurityQuestionAnswer;
	}
	public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
		SecurityQuestionAnswer = securityQuestionAnswer;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getBase64Image() {
		return base64Image;
	}
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	public Boolean getIsDeletd() {
		return isDeletd;
	}
	public void setIsDeletd(Boolean isDeletd) {
		this.isDeletd = isDeletd;
	}

	@Override
	public String toString() {
		return "Admin [pkAdminId=" + pkAdminId + ", fullName=" + fullName + ", SecurityQuestion=" + SecurityQuestion
				+ ", SecurityQuestionAnswer=" + SecurityQuestionAnswer + ", password=" + password + ", emailId="
				+ emailId + ", address=" + address + ", base64Image=" + base64Image + ", isDeletd=" + isDeletd + "]";
	}	
	
}


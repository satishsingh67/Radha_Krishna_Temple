package com.temple.dataValidation;

import java.util.Date;

import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;

public class DataValidation {


	public String validateAdminLogin(String name, String emailId, String securityQuestion,
			String securityQuestionAnswer, String password) {
		// TODO Auto-generated method stub
		String result = null;

		result = StringUtils.isBlank(name) ? "Name":StringUtils.isBlank(emailId) && !StringUtils.contains(emailId,"@")? "Email Id"
						: StringUtils.isBlank(securityQuestion)
								&& securityQuestion.trim().equalsIgnoreCase("--Select--") ? "Security Question"
										: StringUtils.isBlank(securityQuestionAnswer) ? "Security Question's Answer"
												: StringUtils.isBlank(password) ? "Password" : "success";

		return result;
	}

	
	public String validateDonner(String name,String mobileNumber,String address,String donationAmount,
			String donationDate,Part file,String donorId) {
		// TODO Auto-generated method stub
		String result = null;

		result = StringUtils.isBlank(name) ? "Name":StringUtils.isBlank(mobileNumber)?"Donor Mobile Number"
				: StringUtils.isBlank(address) ? "Donner Address"
						: StringUtils.isBlank(donationAmount) ? "Donation Amount"
										: StringUtils.isBlank(donationDate) ? "Donation Date"
												: file.getSize()==0 && StringUtils.isBlank(donorId)? "Supporting Document" : "success";

		return result;
	}
	
	public String validateExpense(String expenseName,String expenseDetails,String expenseAmount,
			String expenseDate,Part file,String pkExpenseId) {
		// TODO Auto-generated method stub
		String result = null;

		result = StringUtils.isBlank(expenseName) ? "Expense Name"
				: StringUtils.isBlank(expenseDetails) ? "Expense Details"
						:StringUtils.isBlank(expenseAmount) ? "Expense Amount"
										: StringUtils.isBlank(expenseDate) ? "Expense Date"
												: file.getSize()==0 && StringUtils.isBlank(pkExpenseId) ? "Supporting Document" : "success";

		return result;
	}
	
	
	public String validateNotice(String noticeTitle,String noticeDetails) {
		// TODO Auto-generated method stub
		String result = null;

		result = StringUtils.isBlank(noticeTitle) ? "Notice Name"
				: StringUtils.isBlank(noticeDetails) ? "Notice Description": "success";

		return result;
	}
	
}

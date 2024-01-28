package com.temple.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.temple.model.Admin;

public class AdminDao {

	public Map<String,Object> validateAdminLogin(String name,String securityQuestion,String securityQuestionAnswer,String password) {

		Connection con = new DataBaseConnection().getDatabaseConnection();
		
		Map<String,Object> result =new HashMap<String,Object>();
		Boolean validationCheck =false;
		String message ="No Data Record with given deatils.";
		Admin adminObject = null;
		
		try {
		
		String loginQuery = "Select * from admin_login where full_name =? and security_question =? and security_question_answer =? and password =? limit 1";
		
		PreparedStatement pstmt =con.prepareStatement(loginQuery);
		
		pstmt.setString(1,name);
		pstmt.setString(2,securityQuestion);
		pstmt.setString(3,securityQuestionAnswer);
		pstmt.setString(4,password);

		ResultSet rs=pstmt.executeQuery();
		
		while(rs.next()) {
			validationCheck =true;

			if(!name.contentEquals(rs.getString("full_name"))) {
				validationCheck =false;
				message ="Please enter a correct Full Name";
			}else if(!securityQuestion.contentEquals(rs.getString("security_question"))) {
				validationCheck =false;
				message ="Please enter a correct Security Question";
			}else if(!securityQuestionAnswer.contentEquals(rs.getString("security_question_answer"))) {
				validationCheck =false;
				message ="Please enter a correct Security Question Answer";
			}else if(!password.contentEquals(rs.getString("password"))) {
				validationCheck =false;
				message ="Please enter a correct Password";
			}
			
			if(validationCheck) {
				adminObject =new Admin(rs.getInt(1),rs.getString("full_name"),"ss");
			}
		}
		
		result.put("status",validationCheck);
		result.put("message",message);
		result.put("admin",adminObject);

		}catch(Exception e) {
			e.printStackTrace();
		}finally{
             new DataBaseConnection().closeConnection(con);
		}
		
		return result;
		
	}
	
	
}

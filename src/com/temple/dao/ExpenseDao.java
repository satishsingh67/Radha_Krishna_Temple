package com.temple.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;

import com.temple.model.Expense;

public class ExpenseDao {
	private DataBaseConnection dbConnection = new DataBaseConnection();

	public List<Expense> getAllExpeneseDetailsForFundManagement() {
		// TODO Auto-generated method stub

		List<Expense> result = new ArrayList<Expense>();
		Connection con = null;
		try {

			con = dbConnection.getDatabaseConnection();

			String selectQuery = "Select pk_expense_id,expense_name,expense_deatails,expense_amount,expense_date,create_user,create_date,update_user,update_time,file_name from expense_details where is_deleted=?";

			PreparedStatement pstmt = con.prepareStatement(selectQuery);
			pstmt.setInt(1, 0);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Expense expenseObject = new Expense();

				expenseObject.setPkExpenseId(rs.getInt("pk_expense_id"));
				expenseObject.setExpenseName(rs.getString("expense_name"));
				expenseObject.setExpenseDeatails(rs.getString("expense_deatails"));
				expenseObject.setExpenseAmount(rs.getInt("expense_amount"));
				expenseObject.setExpenseDate(rs.getTimestamp("expense_date"));
				expenseObject.setCreateUser(rs.getString("create_user"));
				expenseObject.setCreateDate(rs.getTimestamp("create_date"));
				expenseObject.setUpdateUser(rs.getString("update_user"));
				expenseObject.setUpdateDate(rs.getTimestamp("update_time"));

				if (StringUtils.isNotBlank(rs.getString("file_name"))) {
					expenseObject.setIsHasDocument(true);
				} else {
					expenseObject.setIsHasDocument(false);
				}
				result.add(expenseObject);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(con);
		}

		return result;
	}

	public List<Expense> getExpenseForAdmin(String searchName) {
		// TODO Auto-generated method stub

		List<Expense> result = new ArrayList<Expense>();
		Connection con = null;
		try {

			con = dbConnection.getDatabaseConnection();

			String selectQuery = "Select pk_expense_id,expense_name,expense_deatails,expense_amount,expense_date,create_user,create_date,update_user,update_time,file_name from expense_details where is_deleted=?";

			if (StringUtils.isNotBlank(searchName)) {
				selectQuery = "Select pk_expense_id,expense_name,expense_deatails,expense_amount,expense_date,create_user,create_date,update_user,update_time,file_name from expense_details where expense_name like ? and is_deleted=?";
			}

			PreparedStatement pstmt = con.prepareStatement(selectQuery);
			if (StringUtils.isNotBlank(searchName)) {
				pstmt.setString(1, "%" + searchName.trim() + "%");
				pstmt.setInt(2, 0);
			} else {
				pstmt.setInt(1, 0);
			}

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Expense expenseObject = new Expense();

				expenseObject.setPkExpenseId(rs.getInt("pk_expense_id"));
				expenseObject.setExpenseName(rs.getString("expense_name"));
				expenseObject.setExpenseDeatails(rs.getString("expense_deatails"));
				expenseObject.setExpenseAmount(rs.getInt("expense_amount"));
				expenseObject.setExpenseDate(rs.getTimestamp("expense_date"));
				expenseObject.setCreateUser(rs.getString("create_user"));
				expenseObject.setCreateDate(rs.getTimestamp("create_date"));
				expenseObject.setUpdateUser(rs.getString("update_user"));
				expenseObject.setUpdateDate(rs.getTimestamp("update_time"));

				if (StringUtils.isNotBlank(rs.getString("file_name"))) {
					expenseObject.setIsHasDocument(true);
				} else {
					expenseObject.setIsHasDocument(false);
				}
				result.add(expenseObject);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(con);
		}

		return result;
	}

	public Map<String, Object> getExpenseDocument(Integer pkExpenseId) {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", false);
		Connection con = null;
		try {

			con = dbConnection.getDatabaseConnection();

			PreparedStatement pstmt = con.prepareStatement(
					"Select `file_name`,`file_extension`,attachment from expense_details where pk_expense_id=? and is_deleted=?");
			pstmt.setInt(1, pkExpenseId);
			pstmt.setInt(2, 0);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result.put("status", true);
				result.put("fileName", rs.getString("file_name"));
				result.put("fileExtension", rs.getString("file_extension"));
				Blob blob = rs.getBlob("attachment");
				InputStream inputStream = blob.getBinaryStream();
				result.put("fileData", inputStream);
				inputStream.close();
			}
		} catch (Exception e) {
			result.put("status", false);
			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(con);
		}

		return result;
	}

	public String addExpense(String expenseName, String expenseDetails, String expenseAmount, String expenseDate,
			Part file, String userName) {

		String result = "Sometheing	went wrong.Please Try again";
		Connection con = null;
		InputStream expenseDocumentInputStream = null;

		String fileName = null;
		String fileExtension = null;

		try {

			if (file.getSize() > 0) {
				String fileArray[] = StringUtils.splitByWholeSeparator(file.getSubmittedFileName(), ".", 2);

				fileName = fileArray != null && fileArray.length >= 1 ? fileArray[0] : null;
				fileExtension = fileArray != null && fileArray.length >= 2 ? fileArray[1] : null;
				expenseDocumentInputStream = file.getInputStream();
			}

			con = dbConnection.getDatabaseConnection();

			String insertQuery = "INSERT into expense_details (`expense_name`, `expense_deatails`,`expense_amount`,`expense_date`,`file_name`,`file_extension`,`attachment`, `create_user`, `create_date`, `update_user`, `update_time`, `is_deleted`) values (?,?,?,?,?,?,?,?,?,?,?,?)";

			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(expenseDate);

			PreparedStatement pstmt = con.prepareStatement(insertQuery);

			pstmt.setString(1, StringUtils.trim(expenseName));
			pstmt.setString(2, StringUtils.trim(expenseDetails));
			pstmt.setInt(3, Integer.parseInt(expenseAmount.trim()));
			pstmt.setObject(4, date);
			pstmt.setString(5, fileName);
			pstmt.setString(6, fileExtension);
			pstmt.setBlob(7, expenseDocumentInputStream);
			pstmt.setString(8, userName);
			pstmt.setObject(9, new Date());
			pstmt.setString(10, userName);
			pstmt.setObject(11, new Date());
			pstmt.setInt(12, 0);

			int dbResult = pstmt.executeUpdate();

			if (dbResult > 0) {
				result = "Expense uploaded Successfully";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(con);
			if (expenseDocumentInputStream != null) {
				try {
					expenseDocumentInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public String updateExpense(String expenseName, String expenseDetails, String expenseAmount, String expenseDate,
			Part file, String userName, Integer pkExpenseId) {

		String result = "Sometheing	went wrong.Please Try again";
		Connection con = null;
		InputStream expenseDocumentInputStream = null;

		String fileName = null;
		String fileExtension = null;
		Boolean isFileAvialable = false;

		try {

			if (file.getSize() > 0) {
				isFileAvialable = true;
				String fileArray[] = StringUtils.splitByWholeSeparator(file.getSubmittedFileName(), "\\.", 2);

				fileName = fileArray != null && fileArray.length >= 1 ? fileArray[0] : null;
				fileExtension = fileArray != null && fileArray.length >= 2 ? fileArray[1] : null;
				expenseDocumentInputStream = file.getInputStream();
			}

			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(expenseDate);

			con = dbConnection.getDatabaseConnection();

			String insertQuery;

			if (isFileAvialable) {
				insertQuery = "update expense_details set `expense_name`=?,`expense_deatails`=?,`expense_amount`=?,`expense_date`=?,`file_name`=?,`file_extension`=?, `attachment`=?,`update_user`=?,`update_time`=? where pk_expense_id=?";
			} else {
				insertQuery = "update expense_details set `expense_name`=?,`expense_deatails`=?,`expense_amount`=?,`expense_date`=?,`update_user`=?,`update_time`=? where pk_expense_id=?";
			}

			PreparedStatement pstmt = con.prepareStatement(insertQuery);

			if (isFileAvialable) {
				pstmt.setString(1, StringUtils.trim(expenseName));
				pstmt.setString(2, StringUtils.trim(expenseDetails));
				pstmt.setInt(3, Integer.parseInt(expenseAmount.trim()));
				pstmt.setObject(4, date);
				pstmt.setString(5, fileName);
				pstmt.setString(6, fileExtension);
				pstmt.setBlob(7, expenseDocumentInputStream);
				pstmt.setString(8, userName);
				pstmt.setObject(9, new Date());
				pstmt.setInt(10, pkExpenseId);
			} else {
				pstmt.setString(1, StringUtils.trim(expenseName));
				pstmt.setString(2, StringUtils.trim(expenseDetails));
				pstmt.setInt(3, Integer.parseInt(expenseAmount.trim()));
				pstmt.setObject(4, date);
				pstmt.setString(5, userName);
				pstmt.setObject(6, new Date());
				pstmt.setInt(7, pkExpenseId);
			}

			int dbResult = pstmt.executeUpdate();

			if (dbResult > 0) {
				result = "Expense Details Updated Successfully";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(con);
			if (expenseDocumentInputStream != null) {
				try {
					expenseDocumentInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public String removeExpense(String userName, String pkExpenseDetailsId) {

		String result = "Sometheing	went wrong.Please Try again";
		Connection con = null;
		try {
			con = dbConnection.getDatabaseConnection();

			String updateQuery = "update expense_details set is_deleted=?,update_user=?,update_time=? where pk_expense_id in ("
					+ pkExpenseDetailsId + ")";

			PreparedStatement pstmt = con.prepareStatement(updateQuery);

			pstmt.setInt(1, 1);
			pstmt.setString(2, userName);
			pstmt.setObject(3, new Date());
			int dbResult = pstmt.executeUpdate();

			if (dbResult > 0) {
				result = "Expense Removed Successfully";
			} else {
				result = "No record found to delete.";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(con);
		}

		return result;
	}

}

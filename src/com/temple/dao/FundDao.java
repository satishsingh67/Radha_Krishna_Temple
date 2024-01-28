package com.temple.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;

import com.temple.model.Donner;

public class FundDao {
	private DataBaseConnection dbConnection = new DataBaseConnection();

	public List<Donner> getAllDonationForFundManagement() {
		// TODO Auto-generated method stub

		List<Donner> result = new ArrayList<Donner>();
		Connection con = null;
		try {

			con = dbConnection.getDatabaseConnection();

			String selectQuery = "Select pk_donner_id,full_name,mobile_number,donner_address,amount,remarks,received_date,file_name,create_user,create_date,update_user,update_date from donner_details where is_deleted=?";

			PreparedStatement pstmt = con.prepareStatement(selectQuery);
			pstmt.setInt(1, 0);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				Donner donnerObject = new Donner();

				donnerObject.setPkDonnerId(rs.getInt("pk_donner_id"));
				donnerObject.setFullName(rs.getString("full_name"));
				donnerObject.setMobileNumber(rs.getString("mobile_number"));
				donnerObject.setDonnerAddress(rs.getString("donner_address"));
				donnerObject.setAmount(rs.getString("amount"));
				donnerObject.setRemarks(rs.getString("remarks"));
				donnerObject.setReceivedDate(rs.getTimestamp("received_date"));
				donnerObject.setCreateUser(rs.getString("create_user"));
				donnerObject.setCreateDate(rs.getTimestamp("create_date"));
				donnerObject.setUpdateUser(rs.getString("update_user"));
				donnerObject.setUpdateDate(rs.getTimestamp("update_date"));
				if (StringUtils.isNotBlank(rs.getString("file_name"))) {
					donnerObject.setHasDocumnet(true);
				} else {
					donnerObject.setHasDocumnet(false);
				}

				result.add(donnerObject);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(con);
		}

		return result;
	}

	public List<Donner> getDonationForAdmin(String searchName) {
		// TODO Auto-generated method stub

		List<Donner> result = new ArrayList<Donner>();
		Connection con = null;
		try {

			con = dbConnection.getDatabaseConnection();

			String selectQuery = "Select pk_donner_id,full_name,mobile_number,donner_address,amount,remarks,received_date,file_name,create_user,create_date,update_user,update_date from donner_details where is_deleted=?";

			if (StringUtils.isNotBlank(searchName)) {
				selectQuery = "Select pk_donner_id,full_name,mobile_number,donner_address,amount,remarks,received_date,file_name,create_user,create_date,update_user,update_date from donner_details where is_deleted=? and full_name like ?";
			}

			PreparedStatement pstmt = con.prepareStatement(selectQuery);
			if (StringUtils.isNotBlank(searchName)) {
				pstmt.setInt(1, 0);
				pstmt.setString(2, "%" + searchName.trim() + "%");
			} else {
				pstmt.setInt(1, 0);
			}

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Donner donnerObject = new Donner();

				donnerObject.setPkDonnerId(rs.getInt("pk_donner_id"));
				donnerObject.setFullName(rs.getString("full_name"));
				donnerObject.setMobileNumber(rs.getString("mobile_number"));
				donnerObject.setDonnerAddress(rs.getString("donner_address"));
				donnerObject.setAmount(rs.getString("amount"));
				donnerObject.setRemarks(rs.getString("remarks"));
				donnerObject.setReceivedDate(rs.getTimestamp("received_date"));
				donnerObject.setCreateUser(rs.getString("create_user"));
				donnerObject.setCreateDate(rs.getTimestamp("create_date"));
				donnerObject.setUpdateUser(rs.getString("update_user"));
				donnerObject.setUpdateDate(rs.getTimestamp("update_date"));
				if (StringUtils.isNotBlank(rs.getString("file_name"))) {
					donnerObject.setHasDocumnet(true);
				} else {
					donnerObject.setHasDocumnet(false);
				}
				result.add(donnerObject);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(con);
		}

		return result;
	}

	public Map<String, Object> getDonationDocument(Integer pkDonnerId) {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", false);
		Connection con = null;
		try {

			con = dbConnection.getDatabaseConnection();

			PreparedStatement pstmt = con.prepareStatement(
					"Select `file_name`,`file_extension`,attachment from donner_details where pk_donner_id=? and is_deleted=?");
			pstmt.setInt(1, pkDonnerId);
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

	public String addDonation(String fullName, String mobileNumber, String donnerAddress, String amount, String remarks,
			String receivedDate, Part file, String userName) {

		String result = "Sometheing	went wrong.Please Try again";
		Connection con = null;
		InputStream donnerDocumentInputStream = null;

		String fileName = null;
		String fileExtension = null;
		remarks = (StringUtils.isBlank(remarks)) ? "NA" : remarks;

		try {

			if (file.getSize() > 0) {
				String fileArray[] = StringUtils.splitByWholeSeparator(file.getSubmittedFileName(), ".", 2);

				fileName = fileArray != null && fileArray.length >= 1 ? fileArray[0] : null;
				fileExtension = fileArray != null && fileArray.length >= 2 ? fileArray[1] : null;
				donnerDocumentInputStream = file.getInputStream();
			}

			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(receivedDate);

			con = dbConnection.getDatabaseConnection();

			String insertQuery = "INSERT into donner_details (`full_name`,mobile_number, `donner_address`,`amount`,remarks,`received_date`,`file_name`,`file_extension`,`attachment`, `create_user`, `create_date`, `update_user`, `update_date`, `is_deleted`) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement pstmt = con.prepareStatement(insertQuery);

			pstmt.setString(1, StringUtils.trim(fullName));
			pstmt.setString(2, StringUtils.trim(mobileNumber));
			pstmt.setString(3, StringUtils.trim(donnerAddress));
			pstmt.setInt(4, Integer.parseInt(amount.trim()));
			pstmt.setString(5, remarks);
			pstmt.setObject(6, date);
			pstmt.setString(7, fileName);
			pstmt.setString(8, fileExtension);
			pstmt.setBlob(9, donnerDocumentInputStream);
			pstmt.setString(10, userName);
			pstmt.setObject(11, new Date());
			pstmt.setString(12, userName);
			pstmt.setObject(13, new Date());
			pstmt.setInt(14, 0);

			int dbResult = pstmt.executeUpdate();

			if (dbResult > 0) {
				result = "Donation Added Successfully";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(con);
			if (donnerDocumentInputStream != null) {
				try {
					donnerDocumentInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public String updateDonation(String fullName, String mobileNumber, String donnerAddress, String amount,
			String remarks, String receivedDate, Part file, String userName, String pkDonnerId) {

		String result = "Sometheing	went wrong.Please Try again";
		Connection con = null;
		InputStream donnerDocumentInputStream = null;

		String fileName = null;
		String fileExtension = null;
		Boolean isFileAvialable = false;

		try {

			if (file.getSize() > 0) {
				isFileAvialable = true;
				String fileArray[] = StringUtils.splitByWholeSeparator(file.getSubmittedFileName(), "\\.", 2);

				fileName = fileArray != null && fileArray.length >= 1 ? fileArray[0] : null;
				fileExtension = fileArray != null && fileArray.length >= 2 ? fileArray[1] : null;
				donnerDocumentInputStream = file.getInputStream();
			}

			con = dbConnection.getDatabaseConnection();

			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(receivedDate);

			String updateQuery;

			if (isFileAvialable) {
				updateQuery = "update donner_details set `full_name`=?,mobile_number=?,`donner_address`=?,`amount`=?,remarks=?`received_date`=?,`file_name`=?,`file_extension`=?, `attachment`=?,`update_user`=?,`update_date`=? where pk_donner_id=?";
			} else {
				updateQuery = "update donner_details set `full_name`=?,mobile_number=?,`donner_address`=?,`amount`=?,remarks=?,`received_date`=?,`update_user`=?,`update_date`=? where pk_donner_id=?";
			}

			PreparedStatement pstmt = con.prepareStatement(updateQuery);

			remarks = (StringUtils.isBlank(remarks)) ? "NA" : remarks;

			if (isFileAvialable) {
				pstmt.setString(1, StringUtils.trim(fullName));
				pstmt.setString(2, StringUtils.trim(mobileNumber));
				pstmt.setString(3, StringUtils.trim(donnerAddress));
				pstmt.setInt(4, Integer.parseInt(amount.trim()));
				pstmt.setString(5, remarks);
				pstmt.setObject(6, date);
				pstmt.setString(7, fileName);
				pstmt.setString(8, fileExtension);
				pstmt.setBlob(9, donnerDocumentInputStream);
				pstmt.setString(10, userName);
				pstmt.setObject(11, new Date());
				pstmt.setInt(12, Integer.parseInt(pkDonnerId));
			} else {
				pstmt.setString(1, StringUtils.trim(fullName));
				pstmt.setString(2, StringUtils.trim(mobileNumber));
				pstmt.setString(3, StringUtils.trim(donnerAddress));
				pstmt.setInt(4, Integer.parseInt(amount.trim()));
				pstmt.setString(5, remarks);
				pstmt.setObject(6, date);
				pstmt.setString(7, userName);
				pstmt.setObject(8, new Date());
				pstmt.setInt(9, Integer.parseInt(pkDonnerId));
			}

			int dbResult = pstmt.executeUpdate();

			if (dbResult > 0) {
				result = "Donation Updated Successfully";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(con);
			if (donnerDocumentInputStream != null) {
				try {
					donnerDocumentInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public String removeDonation(String userName, String pkDonationId) {

		String result = "Sometheing	went wrong.Please Try again";
		Connection con = null;
		try {
			con = dbConnection.getDatabaseConnection();

			String updateQuery = "update donner_details set is_deleted=?,update_user=?,update_date=? where pk_donner_id in ("
					+ pkDonationId + ")";

			PreparedStatement pstmt = con.prepareStatement(updateQuery);

			pstmt.setInt(1, 1);
			pstmt.setString(2, userName);
			pstmt.setObject(3, new Date());
			int dbResult = pstmt.executeUpdate();

			if (dbResult > 0) {
				result = "Donation Removed Successfully";
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

	public Map<String, Object> getFundDetails() {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("TotalPeople", 0);
		result.put("totalFund", 0);
		result.put("totalSpent", 0);
		result.put("fundLeft", 0);

		Connection con = null;
		try {
			con = dbConnection.getDatabaseConnection();

			Integer fundRecived = 0;
			String query = "SELECT COUNT(c) as totalCount,SUM(s) AS totalSum FROM (SELECT COUNT(`pk_donner_id`) AS c,SUM(`amount`) AS s FROM `donner_details` WHERE is_deleted=0 GROUP BY `full_name`,`mobile_number`) AS inn;";

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(query);

			if (rs.next()) {
				result.put("TotalPeople", rs.getInt("totalCount"));
				result.put("totalFund", rs.getInt("totalSum"));
				fundRecived = rs.getInt("totalSum");
			}
			String query1 = "SELECT sum(expense_amount) as totalSpent FROM `expense_details` WHERE is_deleted=0";

			Statement stmt1 = con.createStatement();

			ResultSet rs1 = stmt.executeQuery(query1);

			if (rs1.next()) {
				result.put("totalSpent", rs1.getInt("totalSpent"));
				result.put("fundLeft", fundRecived - rs1.getInt("totalSpent"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(con);
		}

		return result;

	}

}

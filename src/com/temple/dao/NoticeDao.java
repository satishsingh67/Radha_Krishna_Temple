package com.temple.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;

import com.temple.model.Notice;

public class NoticeDao {

	private DataBaseConnection dbConnection = new DataBaseConnection();

	public List<Notice> getAllNoticesForNoticeBoard() {
		// TODO Auto-generated method stub

		List<Notice> result = new ArrayList<Notice>();
		Connection con = null;
		try {

			con = dbConnection.getDatabaseConnection();

			String selectQuery = "Select pk_notice_id,notice_title,notice_description,create_time from notice where is_deleted=? order by 1 desc";

			PreparedStatement pstmt = con.prepareStatement(selectQuery);
			pstmt.setInt(1, 0);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Notice noticeObject = new Notice();

				noticeObject.setPkNoticeId(rs.getInt("pk_notice_id"));
				noticeObject.setNoticeTitle(rs.getString("notice_title"));
				noticeObject.setNoticeDescription(rs.getString("notice_description"));
				noticeObject.setCreateTime(rs.getTimestamp("create_time"));

				result.add(noticeObject);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(con);
		}

		return result;
	}

	public List<Notice> getAllNoticesForAdmin(String searchName) {
		// TODO Auto-generated method stub

		List<Notice> result = new ArrayList<Notice>();
		Connection con = null;
		try {

			con = dbConnection.getDatabaseConnection();

			String selectQuery = "Select pk_notice_id,notice_title,notice_description,create_user,create_time,update_user,update_time,file_name from notice where is_deleted=? order by 1 desc";

			if (StringUtils.isNotBlank(searchName)) {
				selectQuery = "Select pk_notice_id,notice_title,notice_description,create_user,create_time,update_user,update_time,file_name from notice where notice_title like ? and is_deleted=? order by 1 desc";
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
				Notice noticeObject = new Notice();

				noticeObject.setPkNoticeId(rs.getInt("pk_notice_id"));
				noticeObject.setNoticeTitle(rs.getString("notice_title"));
				noticeObject.setNoticeDescription(rs.getString("notice_description"));
				noticeObject.setCreateUser(rs.getString("create_user"));
				noticeObject.setCreateTime(rs.getTimestamp("create_time"));
				noticeObject.setUpdateUser(rs.getString("update_user"));
				noticeObject.setUpdateTime(rs.getTimestamp("update_time"));
				if (StringUtils.isNotBlank(rs.getString("file_name"))) {
					noticeObject.setIsHasDocument(true);
				} else {
					noticeObject.setIsHasDocument(false);
				}

				result.add(noticeObject);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(con);
		}

		return result;
	}

	public Map<String, Object> getNoticeDocument(Integer pkNoticeId) {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", false);
		Connection con = null;
		try {

			con = dbConnection.getDatabaseConnection();

			PreparedStatement pstmt = con.prepareStatement(
					"Select `file_name`,`file_extension`,document from notice where pk_notice_id=? and is_deleted=?");
			pstmt.setInt(1, pkNoticeId);
			pstmt.setInt(2, 0);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result.put("status", true);
				result.put("fileName", rs.getString("file_name"));
				result.put("fileExtension", rs.getString("file_extension"));
				Blob blob = rs.getBlob("document");
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

	public String addNotice(String noticeTitle, String noticeDetails, Part file, String userName) {

		String result = "Sometheing	went wrong.Please Try again";
		Connection con = null;
		InputStream noticeDocumentInputStream = null;

		String fileName = null;
		String fileExtension = null;

		try {

			if (file.getSize() > 0) {
				String fileArray[] = StringUtils.splitByWholeSeparator(file.getSubmittedFileName(), ".", 2);

				fileName = fileArray != null && fileArray.length >= 1 ? fileArray[0] : null;
				fileExtension = fileArray != null && fileArray.length >= 2 ? fileArray[1] : null;
				noticeDocumentInputStream = file.getInputStream();
			}

			con = dbConnection.getDatabaseConnection();

			String insertQuery = "INSERT into notice (`notice_title`, `notice_description`,`file_name`,`file_extension`, `document`, `create_user`, `create_time`, `update_user`, `update_time`, `is_deleted`) values (?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement pstmt = con.prepareStatement(insertQuery);

			pstmt.setString(1, StringUtils.trim(noticeTitle));
			pstmt.setString(2, StringUtils.trim(noticeDetails));
			pstmt.setString(3, fileName);
			pstmt.setString(4, fileExtension);
			pstmt.setBlob(5, noticeDocumentInputStream);
			pstmt.setString(6, userName);
			pstmt.setObject(7, new Date());
			pstmt.setString(8, userName);
			pstmt.setObject(9, new Date());
			pstmt.setInt(10, 0);

			int dbResult = pstmt.executeUpdate();

			if (dbResult > 0) {
				result = "Notice uploaded Successfully";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(con);
			if (noticeDocumentInputStream != null) {
				try {
					noticeDocumentInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public String updateNotice(String noticeTitle, String noticeDetails, Part file, String userName,
			Integer pkNoticeId) {

		String result = "Sometheing	went wrong.Please Try again";
		Connection con = null;
		InputStream noticeDocumentInputStream = null;

		String fileName = null;
		String fileExtension = null;
		Boolean isFileAvialable = false;

		try {

			if (file.getSize() > 0) {
				isFileAvialable = true;
				String fileArray[] = StringUtils.splitByWholeSeparator(file.getSubmittedFileName(), "\\.", 2);

				fileName = fileArray != null && fileArray.length >= 1 ? fileArray[0] : null;
				fileExtension = fileArray != null && fileArray.length >= 2 ? fileArray[1] : null;
				noticeDocumentInputStream = file.getInputStream();
			}

			con = dbConnection.getDatabaseConnection();

			String insertQuery;

			if (isFileAvialable) {
				insertQuery = "update notice set `notice_title`=?,`notice_description`=?,`file_name`=?,`file_extension`=?, `document`=?,`update_user`=?,`update_time`=? where pk_notice_id=?";
			} else {
				insertQuery = "update notice set `notice_title`=?,`notice_description`=?,`update_user`=?,`update_time`=? where pk_notice_id=?";
			}

			PreparedStatement pstmt = con.prepareStatement(insertQuery);

			if (isFileAvialable) {
				pstmt.setString(1, StringUtils.trim(noticeTitle));
				pstmt.setString(2, StringUtils.trim(noticeDetails));
				pstmt.setString(3, fileName);
				pstmt.setString(4, fileExtension);
				pstmt.setBlob(5, noticeDocumentInputStream);
				pstmt.setString(6, userName);
				pstmt.setObject(7, new Date());
				pstmt.setInt(8, pkNoticeId);
			} else {
				pstmt.setString(1, StringUtils.trim(noticeTitle));
				pstmt.setString(2, StringUtils.trim(noticeDetails));
				pstmt.setString(3, userName);
				pstmt.setObject(4, new Date());
				pstmt.setInt(5, pkNoticeId);
			}

			int dbResult = pstmt.executeUpdate();

			if (dbResult > 0) {
				result = "Notice Updated Successfully";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(con);
			if (noticeDocumentInputStream != null) {
				try {
					noticeDocumentInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public String removeNotice(String userName, String pkNoticeId) {

		String result = "Sometheing	went wrong.Please Try again";
		Connection con = null;
		try {

			con = dbConnection.getDatabaseConnection();

			String updateQuery = "update notice set is_deleted=?,update_user=?,update_time=? where pk_notice_id in ("
					+ pkNoticeId + ")";

			PreparedStatement pstmt = con.prepareStatement(updateQuery);

			pstmt.setInt(1, 1);
			pstmt.setString(2, userName);
			pstmt.setObject(3, new Date());
			int dbResult = pstmt.executeUpdate();

			if (dbResult > 0) {
				result = "Notice Removed Successfully";
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

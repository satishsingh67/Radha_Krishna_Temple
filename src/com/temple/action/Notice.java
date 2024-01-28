package com.temple.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.temple.dao.NoticeDao;
import com.temple.dataValidation.DataValidation;
import com.temple.model.Admin;

/**
 * Servlet implementation class Notice
 */
@WebServlet("/Notice")
@MultipartConfig()
public class Notice extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private NoticeDao noticeDaoObject = new NoticeDao();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Notice() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String action = request.getParameter("action");

		if (StringUtils.isNotBlank(action) && StringUtils.equalsIgnoreCase(action, "download")) {
			downloadNotice(request, response);
		} else if (StringUtils.isNotBlank(action) && StringUtils.equalsIgnoreCase(action, "getNotice")) {
			getNotice(request, response);
		} else {
			PrintWriter out = response.getWriter();
			out.print("Invalid Request");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");

		if (StringUtils.isNotBlank(action) && StringUtils.equalsIgnoreCase(action, "add")) {
			addNotice(request, response);
		} else if (StringUtils.isNotBlank(action) && StringUtils.equalsIgnoreCase(action, "update")) {
			updateNotice(request, response);
		} else if (StringUtils.isNotBlank(action) && StringUtils.equalsIgnoreCase(action, "delete")) {
			removeNotice(request, response);
		} else {
			PrintWriter out = response.getWriter();
			out.print("Invalid Request");
		}

	}

	private void addNotice(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String noticeTitle = request.getParameter("noticeTitle");
		String noticeDescription = request.getParameter("noticeDescrition");
		Part file = request.getPart("file");

		Object object = request.getSession().getAttribute("user");

		PrintWriter out = response.getWriter();
		String result = null;
		try {
			if (object != null) {
				String userName = ((Admin) object).getFullName();

				String dataValidation = new DataValidation().validateNotice(noticeTitle, noticeDescription);

				if (StringUtils.equalsIgnoreCase(dataValidation, "success")) {
					result = noticeDaoObject.addNotice(noticeTitle, noticeDescription, file, userName);
				} else {
					result = "Please enter a valid " + dataValidation;
				}
			} else {
				result = "Unathorized Request";
			}
			out.write(result);

		} catch (Exception e) {
			out.write("Sometheing went wrong.Please try again.");
			e.printStackTrace();
		} finally {
			out.close();
		}

	}

	private void updateNotice(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String noticeTitle = request.getParameter("noticeTitle");
		String noticeDescription = request.getParameter("noticeDescrition");
		String noticeId = request.getParameter("noticeId");
		Part file = request.getPart("file");

		Object object = request.getSession().getAttribute("user");

		PrintWriter out = response.getWriter();
		String result = null;
		try {
			if (object != null) {
				String userName = ((Admin) object).getFullName();

				String dataValidation = new DataValidation().validateNotice(noticeTitle, noticeDescription);

				if (StringUtils.equalsIgnoreCase(dataValidation, "success")) {
					result = noticeDaoObject.updateNotice(noticeTitle, noticeDescription, file, userName,
							Integer.parseInt(noticeId));
				} else {
					result = "Please enter a valid " + dataValidation;
				}
			} else {
				result = "Unathorized Request";
			}
			out.write(result);

		} catch (Exception e) {
			out.write("Sometheing went wrong.Please try again.");
			e.printStackTrace();
		} finally {
			out.close();
		}

	}

	private void getNotice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String result = "No Records";
		PrintWriter out = response.getWriter();

		String isFromAdmin = request.getParameter("isFromAdmin");
		String searchName = request.getParameter("noticeTitle");

		try {

			if (StringUtils.isBlank(isFromAdmin)) {
				List<com.temple.model.Notice> noticeList = noticeDaoObject.getAllNoticesForNoticeBoard();

				if (noticeList != null && noticeList.size() > 0) {

					Gson json = new Gson();
					result = json.toJson(noticeList);
				}
			} else if (StringUtils.isNotBlank(isFromAdmin)
					&& StringUtils.containsIgnoreCase(isFromAdmin.trim(), "yes")) {

				Map<String, Object> resultMap = new HashMap<String, Object>();

				List<com.temple.model.Notice> noticeList = noticeDaoObject.getAllNoticesForAdmin(searchName);

				if (noticeList != null && noticeList.size() > 0) {

					resultMap.put("count", noticeList.size());
					resultMap.put("rows", noticeList);

					Gson json = new Gson();
					result = json.toJson(resultMap);
				}

			}
			out.write(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	private void downloadNotice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String result = "No Records";
		PrintWriter out = response.getWriter();

		String noticeId = request.getParameter("noticeId");

		try {

			if (StringUtils.isNotBlank(noticeId)) {

				Map<String, Object> resutMap = noticeDaoObject.getNoticeDocument(Integer.parseInt(noticeId));
				if (resutMap != null && resutMap.size() > 0 && (boolean) resutMap.get("status")) {
					String fileName = (String) resutMap.get("fileName");
					String fileExtension = (String) resutMap.get("fileExtension");
					InputStream inputStream = (InputStream) resutMap.get("fileData");

					response.setContentType("APPLICATION/OCTET-STREAM");
					response.setHeader("Content-Disposition",
							"attachment; filename=\"" + fileName + "." + fileExtension + "\"");

					int in;
					while ((in = inputStream.read()) != -1) {
						out.write(in);
					}
					inputStream.close();
				} else {
					out.println("<script type=\"text/javascript\">");
					out.println("alert('Error while downloading file.Please try again.');");
					out.println("</script>");
				}

			} else {
				result = "Something went wrong.Please Try again.";
				out.print(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	private void removeNotice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String noticeId = request.getParameter("noticeId");
		Object object = request.getSession().getAttribute("user");

		PrintWriter out = response.getWriter();

		try {

			if (object != null) {
				String userName = ((Admin) object).getFullName();

				if (StringUtils.isNotBlank(noticeId)) {

					String result = noticeDaoObject.removeNotice(userName, noticeId);

					out.write(result);

				} else {
					out.write("Inavlid Request.");
				}
			} else {
				out.write("Unathorized Request.");
			}

		} catch (Exception e) {
			out.write("Sometheing went wrong.Please try again.");
			e.printStackTrace();

		}

	}

}

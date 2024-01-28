package com.temple.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.temple.dao.AdminDao;
import com.temple.dataValidation.DataValidation;
import com.temple.model.Admin;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/AdminLogin")
@MultipartConfig()
public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminDao adminDaoObject = new AdminDao();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminLogin() {
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
		HttpSession session = request.getSession();

		if (StringUtils.isNotBlank(action) && StringUtils.equalsIgnoreCase(action, "logout")) {

			session.removeAttribute("admin");
			session.setAttribute("logoutStatus", "success");
			response.sendRedirect("./static/adminLogin.jsp");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();

		String name = request.getParameter("studentName");
		String emailId = request.getParameter("emailId");
		String securityQuestion = request.getParameter("securityQuestion");
		String securityQuestionAnswer = request.getParameter("securityQuestionAnswer");
		String password = request.getParameter("securityPin");

		String dataValidationStatus = new DataValidation().validateAdminLogin(name, emailId, securityQuestion,
				securityQuestionAnswer, password);

		if (StringUtils.equalsIgnoreCase(dataValidationStatus, "success")) {
			Map<String, Object> result = adminDaoObject.validateAdminLogin(name, securityQuestion,
					securityQuestionAnswer, password);

			if (result != null && !result.isEmpty() && result.size() > 0) {
				if ((boolean) result.get("status")) {
					out.print("/Temple_Website/static/adminPage.jsp");
					HttpSession session = request.getSession();
					session.setAttribute("user", (Admin) result.get("admin"));
				} else {
					out.print(result.get("message"));
				}

			} else {
				out.print("Sometheing went wrong.Please try again.");
			}

		} else {
			out.print("Please enter a valid " + dataValidationStatus);

		}
	}

	public AdminDao getAdminDaoObject() {
		return adminDaoObject;
	}

	public void setAdminDaoObject(AdminDao adminDaoObject) {
		this.adminDaoObject = adminDaoObject;
	}

}

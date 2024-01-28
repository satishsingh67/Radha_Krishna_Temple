package com.temple.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
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
import com.temple.dao.ExpenseDao;
import com.temple.dao.NoticeDao;
import com.temple.dataValidation.DataValidation;
import com.temple.model.Admin;

/**
 * Servlet implementation class AddExpense
 */
@WebServlet("/AddExpense")
@MultipartConfig()
public class AddExpense extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ExpenseDao expenseDaoObject = new ExpenseDao();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddExpense() {
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
			downloadExpense(request, response);
		} else if (StringUtils.isNotBlank(action) && StringUtils.equalsIgnoreCase(action, "getExpense")) {
			getExpense(request, response);
		} else {
			PrintWriter out = response.getWriter();
			out.print("Invalid Request");
		}
	}

	private void getExpense(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String result = "No Records";
		PrintWriter out = response.getWriter();

		String isFromAdmin = request.getParameter("isFromAdmin");
		String searchName = request.getParameter("expenseName");
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			if (StringUtils.isBlank(isFromAdmin)) {
				List<com.temple.model.Expense> expenseList = expenseDaoObject.getAllExpeneseDetailsForFundManagement();

				if (expenseList != null && expenseList.size() > 0) {

					resultMap.put("count", expenseList.size());
					resultMap.put("rows", expenseList);

					Gson json = new Gson();
					result = json.toJson(resultMap);
				}
			} else if (StringUtils.isNotBlank(isFromAdmin)
					&& StringUtils.containsIgnoreCase(isFromAdmin.trim(), "yes")) {

				List<com.temple.model.Expense> expenseList = expenseDaoObject.getExpenseForAdmin(searchName);

				if (expenseList != null && expenseList.size() > 0) {

					resultMap.put("count", expenseList.size());
					resultMap.put("rows", expenseList);

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

	private void downloadExpense(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String result = "No Records";
		PrintWriter out = response.getWriter();

		String expenseId = request.getParameter("expenseId");

		try {

			if (StringUtils.isNotBlank(expenseId)) {

				Map<String, Object> resutMap = expenseDaoObject.getExpenseDocument(Integer.parseInt(expenseId));
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");

		if (StringUtils.isNotBlank(action) && StringUtils.equalsIgnoreCase(action, "add")) {
			addExpense(request, response);
		} else if (StringUtils.isNotBlank(action) && StringUtils.equalsIgnoreCase(action, "update")) {
			updateExpense(request, response);
		} else if (StringUtils.isNotBlank(action) && StringUtils.equalsIgnoreCase(action, "delete")) {
			removeExpense(request, response);
		} else {
			PrintWriter out = response.getWriter();
			out.print("Invalid Request");
		}

	}

	private void addExpense(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String expenseName = request.getParameter("expenseName");
		String expenseDetails = request.getParameter("expenseDetails");
		String expenseAmount = request.getParameter("expenseAmount");
		String expenseDate = request.getParameter("expenseDate");
		Part file = request.getPart("file");
		Object object = request.getSession().getAttribute("user");

		PrintWriter out = response.getWriter();
		String result = null;
		try {
			if (object != null) {
				String userName = ((Admin) object).getFullName();

				String dataValidation = new DataValidation().validateExpense(expenseName, expenseDetails, expenseAmount,
						expenseDate, file, null);

				if (StringUtils.equalsIgnoreCase(dataValidation, "success")) {
					result = expenseDaoObject.addExpense(expenseName, expenseDetails, expenseAmount, expenseDate, file,
							userName);
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

	private void updateExpense(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String expenseName = request.getParameter("expenseName");
		String expenseDetails = request.getParameter("expenseDetails");
		String expenseAmount = request.getParameter("expenseAmount");
		String expenseDate = request.getParameter("expenseDate");
		String pkExpenseId = request.getParameter("pkExpenseId");
		Part file = request.getPart("file");

		Object object = request.getSession().getAttribute("user");

		PrintWriter out = response.getWriter();
		String result = null;
		try {
			if (object != null) {
				String userName = ((Admin) object).getFullName();

				String dataValidation = new DataValidation().validateExpense(expenseName, expenseDetails, expenseAmount,
						expenseDate, file, pkExpenseId);

				if (StringUtils.equalsIgnoreCase(dataValidation, "success")) {
					result = expenseDaoObject.updateExpense(expenseName, expenseDetails, expenseAmount, expenseDate,
							file, userName, Integer.parseInt(pkExpenseId.trim()));
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

	private void removeExpense(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String expenseDetailsId = request.getParameter("pkExpenseId");
		Object object = request.getSession().getAttribute("user");

		PrintWriter out = response.getWriter();

		try {
			if (object != null) {
				String userName = ((Admin) object).getFullName();

				if (StringUtils.isNotBlank(expenseDetailsId)) {

					String result = expenseDaoObject.removeExpense(userName, expenseDetailsId);

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

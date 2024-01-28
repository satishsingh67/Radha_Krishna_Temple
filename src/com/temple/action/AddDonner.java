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
import com.temple.dao.FundDao;
import com.temple.dataValidation.DataValidation;
import com.temple.model.Admin;

/**
 * Servlet implementation class AddDonner
 */
@WebServlet("/AddDonner")
@MultipartConfig()
public class AddDonner extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FundDao fundDaoObject = new FundDao();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddDonner() {
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
			downloadDonation(request, response);
		} else if (StringUtils.isNotBlank(action) && StringUtils.equalsIgnoreCase(action, "getDonation")) {
			getDonation(request, response);
		} else if (StringUtils.isNotBlank(action) && StringUtils.equalsIgnoreCase(action, "getFundDetails")) {
			getFundDetails(request, response);
		} else {
			PrintWriter out = response.getWriter();
			out.print("Invalid Request");
		}

	}

	private void getFundDetails(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String result = "No Records";
		PrintWriter out = response.getWriter();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			resultMap = fundDaoObject.getFundDetails();

			Gson json = new Gson();
			result = json.toJson(resultMap);

			out.write(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	private void getDonation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String result = "No Records";
		PrintWriter out = response.getWriter();

		String isFromAdmin = request.getParameter("isFromAdmin");
		String searchName = request.getParameter("donnerName");
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			if (StringUtils.isBlank(isFromAdmin)) {
				List<com.temple.model.Donner> fundList = fundDaoObject.getDonationForAdmin(searchName);
				if (fundList != null && fundList.size() > 0) {

					resultMap.put("count", fundList.size());
					resultMap.put("rows", fundList);

					Gson json = new Gson();
					result = json.toJson(resultMap);
				}
			} else if (StringUtils.isNotBlank(isFromAdmin)
					&& StringUtils.containsIgnoreCase(isFromAdmin.trim(), "yes")) {

				List<com.temple.model.Donner> fundList = fundDaoObject.getDonationForAdmin(searchName);

				if (fundList != null && fundList.size() > 0) {

					resultMap.put("count", fundList.size());
					resultMap.put("rows", fundList);

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

	private void downloadDonation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String result = "No Records";
		PrintWriter out = response.getWriter();

		String pkDonnerId = request.getParameter("donnerId");

		try {

			if (StringUtils.isNotBlank(pkDonnerId)) {

				Map<String, Object> resutMap = fundDaoObject.getDonationDocument(Integer.parseInt(pkDonnerId));
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
			addDonation(request, response);
		} else if (StringUtils.isNotBlank(action) && StringUtils.equalsIgnoreCase(action, "update")) {
			updateDonation(request, response);
		} else if (StringUtils.isNotBlank(action) && StringUtils.equalsIgnoreCase(action, "delete")) {
			removeDonation(request, response);
		} else {
			PrintWriter out = response.getWriter();
			out.print("Invalid Request");
		}

	}

	private void addDonation(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String mobileNumber = request.getParameter("mobileNumber");
		String donationAmount = request.getParameter("donationAmount");
		String remarks = request.getParameter("remarks");
		String donationDate = request.getParameter("donationDate");
		Part file = request.getPart("file");
		Object object = request.getSession().getAttribute("user");

		PrintWriter out = response.getWriter();
		String result = null;
		try {
			if (object != null) {
				String userName = ((Admin) object).getFullName();

				String dataValidation = new DataValidation().validateDonner(name, mobileNumber, address, donationAmount,
						donationDate, file, null);

				if (StringUtils.equalsIgnoreCase(dataValidation, "success")) {
					result = fundDaoObject.addDonation(name, mobileNumber, address, donationAmount, remarks,
							donationDate, file, userName);
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

	private void updateDonation(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String mobileNumber = request.getParameter("mobileNumber");
		String donationAmount = request.getParameter("donationAmount");
		String remarks = request.getParameter("remarks");
		String donationDate = request.getParameter("donationDate");
		Part file = request.getPart("file");
		String donnerId = request.getParameter("donnerId");

		Object object = request.getSession().getAttribute("user");

		PrintWriter out = response.getWriter();
		String result = null;
		try {
			if (object != null) {
				String userName = ((Admin) object).getFullName();

				String dataValidation = new DataValidation().validateDonner(name, mobileNumber, address, donationAmount,
						donationDate, file, donnerId);

				if (StringUtils.equalsIgnoreCase(dataValidation, "success")) {
					result = fundDaoObject.updateDonation(name, mobileNumber, address, donationAmount, remarks,
							donationDate, file, userName, donnerId);

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

	private void removeDonation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String donnerId = request.getParameter("donnerId");
		Object object = request.getSession().getAttribute("user");

		PrintWriter out = response.getWriter();

		try {

			if (object != null) {
				String userName = ((Admin) object).getFullName();

				if (StringUtils.isNotBlank(donnerId)) {

					String result = fundDaoObject.removeDonation(userName, donnerId);

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

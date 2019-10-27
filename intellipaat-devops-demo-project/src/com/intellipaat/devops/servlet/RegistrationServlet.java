package com.intellipaat.devops.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intellipaat.devops.domain.User;
import com.intellipaat.devops.jdbc.DBHandler;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("in Servlet!");
		try {
			String firstname = request.getParameter("firstname");
			System.out.println("firstname:" + firstname);
			String lastname = request.getParameter("lastname");
			String address1 = request.getParameter("address1");
			String address2 = request.getParameter("address2");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			String zip = request.getParameter("zip");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			System.out.println("Done");
			if (firstname.isEmpty() || lastname.isEmpty() || address1.isEmpty() || city.isEmpty() || zip.isEmpty()
					|| username.isEmpty() || password.isEmpty()) {
				request.setAttribute("errorMessage", "Invalid Input");
				RequestDispatcher req = request.getRequestDispatcher("register.jsp");
				req.include(request, response);
			} else {
				User user = new User();
				user.setFirstname(firstname);
				user.setLastname(lastname);
				user.setAddress1(address1);
				user.setAddress2(address2);
				user.setCity(city);
				user.setState(state);
				user.setZip(zip);
				user.setUserid(username);
				user.setPassword(password);
				DBHandler handler = new DBHandler();
				System.out.println("handler:");

				handler.saveUser(user);

				RequestDispatcher req = request.getRequestDispatcher("success.jsp");
				req.forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			RequestDispatcher req = request.getRequestDispatcher("failure.jsp");
			req.forward(request, response);
		}
	}

}

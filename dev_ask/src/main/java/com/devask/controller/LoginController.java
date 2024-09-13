package com.devask.controller;

import java.io.IOException;

import javax.sql.DataSource;

import com.devask.model.User;
import com.devask.model.UserDAO;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Resource(name = "jdbc/devask")
	private DataSource dataSource;
	
	public LoginController() {
		
	}
	
	UserDAO userDAO;
	
	@Override
	public void init() throws ServletException {
		userDAO = new UserDAO(dataSource);
		super.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mode = req.getParameter("mode");
		if(mode == null) {
			mode = "FORM";
		}
		switch (mode) {
		case "FORM":
			showForm(req, resp);
			break;
		case "LOGIN":
			login(req, resp);
			break;
		default:
			break;
		}
	}
	
	private void showForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/login.jsp");
		dispatcher.forward(req, resp);
	}
	
	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		boolean isAuth = userDAO.isAuthenticated(email, password);
		if(isAuth) {
			User user = userDAO.getUserDataByEmail(email);
			HttpSession session = req.getSession();
			session.setAttribute("user",user);
			resp.sendRedirect("user?mode=PROFILE");
		}else {
			req.setAttribute("success", isAuth);
			showForm(req, resp);
		}
	}
	
	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}

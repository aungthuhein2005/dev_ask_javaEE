package com.devask.controller;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import com.devask.model.Question;
import com.devask.model.QuestionDAO;
import com.devask.model.Tag;
import com.devask.model.TagDAO;
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

@WebServlet("/users")
public class UsersController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UsersController() {}
	
	@Resource(name = "jdbc/devask")
	private DataSource dataSource;
	private UserDAO userDAO;
	private TagDAO tagDAO;
	private QuestionDAO questionDAO;
	
//	private 
	
	@Override
	public void init() throws ServletException {
		userDAO = new UserDAO(dataSource);
		tagDAO = new TagDAO(dataSource);
		questionDAO = new QuestionDAO(dataSource);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String mode = req.getParameter("mode");
		if(mode == null) {
			mode = "USERS";
		}
		switch (mode) {
		case "USERS":
			userList(req, resp);
			break;
		default:
			break;
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	protected void userList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<User> users = userDAO.getAllUser();
		req.setAttribute("users", users);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/index.jsp");
		dispatcher.forward(req, resp);
	}
	
	
	
}

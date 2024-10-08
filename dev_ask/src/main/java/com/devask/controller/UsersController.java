package com.devask.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
		case "PROFILE":
			showProfile(req, resp);
			break;
		case "SEARCH":
			search(req, resp);
			break;
		default:
			break;
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	protected void showProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int userId = Integer.parseInt(req.getParameter("id"));
		HttpSession session = req.getSession();
		User currentUser = (User) session.getAttribute("user");
		if(currentUser.getUser_id() == userId) {
			resp.sendRedirect("user?mode=PROFILE");
			return;
		}
		User user = userDAO.getUser(userId);
		if(user != null) {			
			List<Tag> interestTags = tagDAO.getInerestTags(userId);
			List<Question> postedQuestions = questionDAO.getQuestionByUserId(userId);
			req.setAttribute("user", user);
			req.setAttribute("interestTags", interestTags);
			req.setAttribute("postedQuestions", postedQuestions);
			RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/other-profile.jsp");
			dispatcher.forward(req, resp);
		}
	}
	
	protected void userList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int page = 1;
		int pageSize = 10; // Number of tags per page
		if (req.getParameter("page") != null) {
			page = Integer.parseInt(req.getParameter("page"));
		}
		List<User> users = userDAO.getAllUsers(page,pageSize);
		 int totalTagCount = userDAO.getTotalCount();
		    int totalPages = (int) Math.ceil((double) totalTagCount / pageSize);
		    req.setAttribute("users", users);
		    req.setAttribute("currentPage", page);
		    req.setAttribute("totalPages", totalPages);
		    req.setAttribute("topReputation", userDAO.getTopReputationUsers(3));
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/index.jsp");
		dispatcher.forward(req, resp);
	}
	
	protected void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String keyword = req.getParameter("key");
		List<User> users = userDAO.searchUsers(keyword);
		req.setAttribute("users", users);
	    req.setAttribute("topReputation", userDAO.getTopReputationUsers(3));
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/index.jsp");
		dispatcher.forward(req, resp);
	}

	
}

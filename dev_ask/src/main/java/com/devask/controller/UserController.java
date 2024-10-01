package com.devask.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.catalina.ant.ListTask;

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
import jakarta.servlet.http.Part;

@WebServlet("/user")
public class UserController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/devask")
	private DataSource dataSource;
	private UserDAO userDAO;
	private TagDAO tagDAO;
	private QuestionDAO questionDAO;

	@Override
	public void init() throws ServletException {
		userDAO = new UserDAO(dataSource);
		tagDAO = new TagDAO(dataSource);
		questionDAO = new QuestionDAO(dataSource);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mode = req.getParameter("mode");
		if (mode == null) {
			mode = "FORM";
		}
		switch (mode) {
		case "FORM":
			showRegister(req, resp);
			break;
		case "REGISTER":
			register(req, resp);
			break;
		case "PROFILE":
			showProfile(req, resp);
			break;	
		case "EDIT":
			showEditForm(req, resp);
			break;
		case "EDIT_USER":
			editUser(req, resp);
			break;
		 case "LOGOUT":  // Add the logout case
	            logout(req, resp);
	            break;
		default:
			break;
		}
	}

	private void showRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/register.jsp");
		dispatcher.forward(req, resp);
	}
	
	private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User currentUser = (User) session.getAttribute("user");
		  if (currentUser != null) {
		            int userId = currentUser.getUser_id();
		            User user = userDAO.getUser(userId);
		            List<Tag> interestTags = tagDAO.getInerestTags(userId);
		            List<Tag> tags = tagDAO.getAllTags();

		            req.setAttribute("user", user);
		            req.setAttribute("tags", tags);
		            req.setAttribute("interestTags", interestTags);

		            RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/user-edit.jsp");
		            dispatcher.forward(req, resp);
		    } else {
		        resp.sendRedirect("login");
		    }
	}

	private void showProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User currentUser = (User) session.getAttribute("user");

		if (currentUser != null) {
			int userId = currentUser.getUser_id();
			User user = userDAO.getUser(userId);
			List<Tag> interestTags = tagDAO.getInerestTags(userId);
			List<Question> postedQuestions = questionDAO.getQuestionByUserId(userId);
			req.setAttribute("user", user);
			req.setAttribute("interestTags", interestTags);
			req.setAttribute("postedQuestions", postedQuestions);
			RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/profile.jsp");
			dispatcher.forward(req, resp);
		} else {
			resp.sendRedirect("login");
		}
	}

	private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String dob = req.getParameter("dob") ;
		User user = new User(username, email, dob, password);
		boolean success = userDAO.createUser(user);
		System.out.println(success);
		if (success) {
			resp.sendRedirect("login");
		} else {
			req.setAttribute("success", success);
			showRegister(req, resp);
		}
	}

	private void editUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int userId =Integer.parseInt(req.getParameter("userId"));
		String username = req.getParameter("username");
		String about = req.getParameter("about");
		String dob = req.getParameter("dob");
	    String privateProfileParam = req.getParameter("private_profile");
        boolean privateProfile = privateProfileParam != null;
		String[] selectedTag = req.getParameterValues("tags");
		List<Tag> tags = new ArrayList<>();
	    if (selectedTag != null) {
	        for (String tag : selectedTag) {
	            tags.add(new Tag(Integer.parseInt(tag)));
	        }
	    }
		User user = new User(userId,username,privateProfile,about,dob);
		boolean success = userDAO.updateUser(user,tags);
		if(success) {
			User newUser = userDAO.getUser(userId);
			HttpSession session = req.getSession();
			session.setAttribute("user",newUser);
		}
		req.setAttribute("success", success);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/user-edit.jsp");
		dispatcher.forward(req, resp);
	}
	
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    // Invalidate the current session to log the user out
	    HttpSession session = req.getSession();
	    session.invalidate();
	    
	    // Redirect to the login page or home page after logout
	    resp.sendRedirect("login"); // You can change "login" to any page you want to redirect to
	}

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}

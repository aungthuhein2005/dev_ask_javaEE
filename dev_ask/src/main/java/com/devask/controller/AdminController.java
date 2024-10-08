package com.devask.controller;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import com.devask.model.AnswerDAO;
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

@WebServlet("/admin")
public class AdminController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/devask")
	private DataSource dataSource;
	private UserDAO userDAO;
	private TagDAO tagDAO;
	private QuestionDAO questionDAO;
	private AnswerDAO answerDAO;

	@Override
	public void init() throws ServletException {
		userDAO = new UserDAO(dataSource);
		tagDAO = new TagDAO(dataSource);
		questionDAO = new QuestionDAO(dataSource);
		answerDAO = new AnswerDAO(dataSource);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String mode = req.getParameter("mode");
		if (mode == null) {
			mode = "DASHBOARD";
		}
		switch (mode) {
		case "DASHBOARD":
			dashboard(req, resp);
			break;
		case "USERS":
			users(req, resp);
			break;
		case "USERDELETE":
			deleteUser(req, resp);
			break;
		case "TAGDELETE":
			deleteTag(req, resp);
			break;
		case "TAGEDIT":
			editTag(req, resp);
			break;
		case "TAGEDITFORM":
			tagEditForm(req, resp);
			break;
		case "TAGADD":
			addTag(req, resp);
			break;
		case "TAGADDFORM":
			tagAddFrom(req, resp);
			break;
		case "QUESTIONDELETE":
			deleteQuestion(req, resp);
			break;
		case "QUESTIONS":
			questions(req, resp);
			break;
		case "TAGS":
			tags(req, resp);
			break;
		case "SETTING":
			setting(req, resp);
			break;
		default:
			break;
		}
		
		
	}
	
	
	protected void dashboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Integer> userGrowthData = userDAO.getUserGrowthData();
        List<Integer> questionTrendsData = questionDAO.getQuestionTrendsData();
        req.setAttribute("userCount", userDAO.getTotalCount());
        req.setAttribute("questionCount", questionDAO.getTotalQuestionCount());
        req.setAttribute("tagCount", tagDAO.getTotalTagCount());
		 req.setAttribute("userGrowthData", userGrowthData);
	        req.setAttribute("questionTrendsData", questionTrendsData);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/dashboard/index.jsp");
		dispatcher.forward(req, resp);
	}
	
	protected void users(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<User> userList = userDAO.getAllUsers();
		req.setAttribute("users", userList);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/dashboard/users.jsp");
		dispatcher.forward(req, resp);
	}

	protected void questions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Question> questionList = questionDAO.getAllQuestion();
		req.setAttribute("questions", questionList);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/dashboard/questions.jsp");
		dispatcher.forward(req, resp);
	}
	
	protected void tags(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Tag> tagList = tagDAO.getAllTags();
		req.setAttribute("tags", tagList);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/dashboard/tags.jsp");
		dispatcher.forward(req, resp);
	}
	
	protected void setting(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/dashboard/tags.jsp");
		dispatcher.forward(req, resp);
	}
	
	protected void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int userId = Integer.parseInt(req.getParameter("userId"));
		boolean ok = userDAO.deleteUser(userId);
		if(ok) {			
			req.setAttribute("status", "DELETE_SUCCESS");
		}else {
			req.setAttribute("status", "DELETE_FAIL");
		}
		users(req, resp);
	}
	
	protected void deleteQuestion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int questionId = Integer.parseInt(req.getParameter("questionId"));
		boolean ok = questionDAO.deleteQuestion(questionId);
		if(ok) {			
			req.setAttribute("status", "DELETE_SUCCESS");
		}else {
			req.setAttribute("status", "DELETE_FAIL");
		}
		questions(req, resp);
	}
	
	protected void tagAddFrom(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/dashboard/tag-form.jsp");
		dispatcher.forward(req, resp);
	}
	
	protected void tagEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int tagId = Integer.parseInt(req.getParameter("tagId"));
		Tag tag = tagDAO.getTag(tagId);
		if(tag != null) {
			req.setAttribute("tag", tag);
			RequestDispatcher dispatcher = req.getRequestDispatcher("template/dashboard/tag-edit-form.jsp");
			dispatcher.forward(req, resp);
		}
	}
	
	
	protected void deleteTag(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int tagId = Integer.parseInt(req.getParameter("tagId"));
		boolean ok = tagDAO.deleteTag(tagId);
		if(ok) {			
			req.setAttribute("status", "DELETE_SUCCESS");
		}else {
			req.setAttribute("status", "DELETE_FAIL");
		}
		tags(req, resp);
	}
	
	protected void addTag(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		boolean createTag = tagDAO.createTag(name, description);
		if(createTag) {			
			req.setAttribute("status", "CREATE_SUCCESS");
		}else {
			req.setAttribute("status", "CREATE_FAIL");
		}
		tags(req, resp);
	}
	
	protected void editTag(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int tagId = Integer.parseInt(req.getParameter("tagId"));
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		boolean createTag = tagDAO.updateTag(tagId,name, description);
		if(createTag) {			
			req.setAttribute("status", "UPDATE_SUCCESS");
		}else {
			req.setAttribute("status", "UPDATE_FAIL");
		}
		tags(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}

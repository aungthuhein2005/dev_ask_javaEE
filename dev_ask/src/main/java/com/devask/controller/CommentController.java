package com.devask.controller;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import com.devask.model.Comment;
import com.devask.model.CommentDAO;
import com.devask.model.User;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/comment")
public class CommentController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Resource(name="jdbc/devask")
	private DataSource dataSource;
	private CommentDAO commentDAO;
	
	public CommentController() {};
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		commentDAO = new CommentDAO(dataSource);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mode = req.getParameter("mode");
		if(mode==null) {
			return;
		}
		switch (mode) {
		case "CREATE":
			createAnswer(req, resp);
			break;
		case "LOADCOMMENTS":
			loadComments(req, resp);
			break;
		}
	}
	
	protected void loadComments(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int answerId = Integer.parseInt(req.getParameter("answerId"));
		List<Comment> comments = commentDAO.getCommentsByAnswer(answerId);
		req.setAttribute("comments", comments);
		req.setAttribute("answerId", answerId);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/comment/comment-list.jsp");
		dispatcher.forward(req, resp);
	}
		
		private void createAnswer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			HttpSession session = req.getSession();
			User currentUser = (User) session.getAttribute("user");
			if(currentUser != null) {
				String body = req.getParameter("body");
			int answerId = Integer.parseInt(req.getParameter("answerId"));
			Comment comment = new Comment(body, currentUser.getUser_id(), answerId);
			Comment createdComment = commentDAO.createComment(comment);
			if (createdComment != null) {
				resp.getWriter().write(convertCommentToJson(createdComment));
	        } else {
	           resp.getWriter().write("fail");
	        }
			}
			
		}
		
		private String convertCommentToJson(Comment comment) {
		    // Build JSON string manually
		    StringBuilder jsonBuilder = new StringBuilder();
		    jsonBuilder.append("{");
		    jsonBuilder.append("\"commentId\":").append(comment.getCommentId()).append(",");
		    jsonBuilder.append("\"body\":\"").append(escapeJson(comment.getBody())).append("\",");
		    jsonBuilder.append("\"userId\":").append(comment.getUserId()).append(",");
		    jsonBuilder.append("\"username\":\"").append(escapeJson(comment.getUsername())).append("\",");
		    jsonBuilder.append("\"answerId\":").append(comment.getAnswerId()).append(",");
		    jsonBuilder.append("\"createdAt\":\"").append(comment.getCreatedAt()).append("\"");
		    jsonBuilder.append("}");
		    return jsonBuilder.toString();
		}

		// Utility method to escape special characters for JSON
		private String escapeJson(String value) {
		    if (value == null) return "";
		    return value.replace("\\", "\\\\")
		                .replace("\"", "\\\"")
		                .replace("\b", "\\b")
		                .replace("\f", "\\f")
		                .replace("\n", "\\n")
		                .replace("\r", "\\r")
		                .replace("\t", "\\t");
		}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}

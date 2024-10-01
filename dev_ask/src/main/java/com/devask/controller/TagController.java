package com.devask.controller;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import com.devask.model.QuestionDAO;
import com.devask.model.Tag;
import com.devask.model.TagDAO;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/tags")
public class TagController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TagController() {
		// TODO Auto-generated constructor stub
	}
	
	@Resource(name = "jdbc/devask")
	private DataSource dataSource;
	private TagDAO tagDAO;
	
	@Override
	public void init() throws ServletException {
		tagDAO = new TagDAO(dataSource);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mode = req.getParameter("mode");
		if (mode == null) {
			mode = "SHOWLIST";
		}
		switch (mode) {
		case "SHOWLIST":
			showList(req, resp);
			break;
		case "SEARCH":
			search(req, resp);
			break;
		default:
			break;
		}
		
	}

	protected void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int page = 1;
		int pageSize = 10; // Number of tags per page
		if (req.getParameter("page") != null) {
			page = Integer.parseInt(req.getParameter("page"));
		}
		List<Tag> tagList = tagDAO.getTagsWithPagination(page,pageSize);
		 int totalTagCount = tagDAO.getTotalTagCount();
		    int totalPages = (int) Math.ceil((double) totalTagCount / pageSize);
		    req.setAttribute("tags", tagList);
		    req.setAttribute("currentPage", page);
		    req.setAttribute("totalPages", totalPages);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/template/tag/index.jsp");
		dispatcher.forward(req, resp);
	}

	protected void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String keyword = req.getParameter("key");
		List<Tag> tagList = tagDAO.searchTags(keyword);
		req.setAttribute("tags", tagList);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/template/tag/index.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}

package com.devask.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.devask.model.Answer;
import com.devask.model.AnswerDAO;
import com.devask.model.Comment;
import com.devask.model.CommentDAO;
import com.devask.model.Question;
import com.devask.model.QuestionDAO;
import com.devask.model.Tag;
import com.devask.model.TagDAO;
import com.devask.model.User;
import com.devask.model.Vote;
import com.devask.model.VoteDAO;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/questions")
public class QuestionController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	private D
	public QuestionController() {
	}

	@Resource(name = "jdbc/devask")
	private DataSource dataSource;
	private QuestionDAO questionDAO;
	private TagDAO tagDAO;
	private AnswerDAO answerDAO;
	private CommentDAO commentDAO;
	private VoteDAO voteDAO;

	@Override
	public void init() throws ServletException {
		questionDAO = new QuestionDAO(dataSource);
		tagDAO = new TagDAO(dataSource);
		answerDAO = new AnswerDAO(dataSource);
		commentDAO = new CommentDAO(dataSource);
		voteDAO = new VoteDAO(dataSource);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String mode = req.getParameter("mode");
		if (mode == null) {
			mode = "QUESTIONS";
		}
		switch (mode) {
		case "FORM":
			createForm(req, resp);
			break;
		case "CREATE":
			createQuestion(req, resp);
			break;
		case "QUESTIONS":
			showQuestion(req, resp);
			break;
		case "DETAILS":
			showDetail(req, resp);
			break;
		case "ANSWER":
			createAnswer(req, resp);
			break;
		case "SEARCH":
			search(req, resp);
			break;
		case "LOADCOMMENTS":
			loadComments(req, resp);
			break;
		case "VOTE":
			voteQuestoin(req, resp);
			break;
		case "ANSWERVOTE":
			voteAnswer(req,resp);
		default:
			showQuestion(req, resp);
			break;
		}
	}

	protected void showQuestion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    int page = 1; // Default to the first page
	    int pageSize = 10; // Set the number of questions per page

	    // Get the page number from the request, if provided
	    String pageParam = req.getParameter("page");
	    if (pageParam != null) {
	        try {
	            page = Integer.parseInt(pageParam); // Parse the page number
	        } catch (NumberFormatException e) {
	            page = 1; // If parsing fails, default to page 1
	        }
	    }

	    // Fetch the questions with pagination
	    List<Question> questions = questionDAO.getAllQuestion(page, pageSize);
	    List<Question> bestQuestions = questionDAO.getTopBestQuestions();
	    req.setAttribute("questions", questions);
	    req.setAttribute("bestQuestions", bestQuestions);

	    // Get total number of questions for pagination
	    int totalQuestionCount = questionDAO.getTotalQuestionCount(); // Assume this method exists
	    int totalPages = (int) Math.ceil((double) totalQuestionCount / pageSize); // Calculate total pages

	    // Set the pagination attributes
	    req.setAttribute("currentPage", page);
	    req.setAttribute("totalPages", totalPages);
	   
	    RequestDispatcher dispatcher = req.getRequestDispatcher("template/question/index.jsp");
	    dispatcher.forward(req, resp);
	}


	protected void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String keyword = req.getParameter("key");
		List<Question> questions = questionDAO.searchQuestion(keyword);
		req.setAttribute("questions", questions);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/question/index.jsp");
		dispatcher.forward(req, resp);
	}

	
	protected void loadComments(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int answerId = Integer.parseInt(req.getParameter("answerId"));
		List<Comment> comments = commentDAO.getCommentsByAnswer(answerId);
		req.setAttribute("comments", comments);
		req.setAttribute("answerId", answerId);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/comment/comment.jsp");
		dispatcher.forward(req, resp);
	}

	protected void showDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int questionId = Integer.parseInt(req.getParameter("questionId"));
		Question question = questionDAO.getQuestion(questionId);
		if (question == null) {
			System.out.println("question with question id " + questionId + " is not found");
			return;
		}
		List<Vote> questionVotes = voteDAO.getQuestionVotes(questionId);
		List<Question> relatedQuestions = questionDAO.getRelatedQuestion(questionId);
		if (relatedQuestions != null) {
			relatedQuestions = questionDAO.getAllQuestion(1,10);
		}
		if (relatedQuestions.size() < 6) {
			int additionalQuestionsNeeded = 6 - relatedQuestions.size();
			List<Question> additionalQuestions = questionDAO.getAllQuestion(1,additionalQuestionsNeeded);
			relatedQuestions.addAll(additionalQuestions);
		}
		req.setAttribute("question", question);
		req.setAttribute("questionVotes", questionVotes);
		req.setAttribute("relatedQuestions", relatedQuestions);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/question/details.jsp");
		dispatcher.forward(req, resp);
	}

	protected void createForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User currentUser = (User) session.getAttribute("user");
		if (currentUser != null) {
			List<Tag> tags = tagDAO.getAllTags();
			req.setAttribute("tags", tags);
			RequestDispatcher dispatcher = req.getRequestDispatcher("template/question/create.jsp");
			dispatcher.forward(req, resp);
		} else {
			resp.sendRedirect("login");
		}
	}

	private void createQuestion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User currentUser = (User) session.getAttribute("user");
		if (currentUser != null) {
			String title = req.getParameter("title");
			String description = req.getParameter("description");
			String questionContent = req.getParameter("questionContent");
			String[] selectedTag = req.getParameterValues("tag");
			List<Tag> tags = new ArrayList<>();
			if (selectedTag != null) {
				for (String tag : selectedTag) {
					tags.add(new Tag(Integer.parseInt(tag)));
				}
			}
			Question question = new Question(title, description, questionContent, currentUser.getUser_id(), tags);
			boolean ok = questionDAO.createQuestion(question);
			if (ok) {
				System.out.println("question creaetd");
				showQuestion(req, resp);
			} else {
				req.setAttribute("success", ok);
				createForm(req, resp);
			}
		} else {
			resp.sendRedirect("login");
		}
	}
	
	private void voteAnswer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User currentUser = (User) session.getAttribute("user");
		if (currentUser == null) {
			return;
		}
		int answerId = Integer.parseInt(req.getParameter("answerId"));
		System.out.println("answerId"+answerId);
		Vote existingVote = voteDAO.exisitingAnswerVote(currentUser.getUser_id(), answerId);
		if (existingVote != null) {
			boolean deleted = voteDAO.deleteVote(existingVote.getVoteId());
			if(deleted) {
				resp.getWriter().write("Vote deleted successfully");				
			}else {
				resp.getWriter().write("Vote delete fail");
			}
		} else {
			Vote vote = new Vote(currentUser.getUser_id(), answerId, "upvote");
			boolean ok = voteDAO.answerVote(vote);
			System.out.println(ok);
			if (ok) {
				resp.getWriter().write("Vote registered successfully");
			} else {
				resp.getWriter().write("Vote registered fail");
			}
		}

	}

	private void voteQuestoin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User currentUser = (User) session.getAttribute("user");
		if (currentUser == null) {
			return;
		}
		int questionId = Integer.parseInt(req.getParameter("questionId"));
		String voteType = req.getParameter("voteType");
		Vote existingVote = voteDAO.existingQuestionVote(currentUser.getUser_id(), questionId,voteType);
		if (existingVote != null) {
			boolean deleted = voteDAO.deleteVote(existingVote.getVoteId());
			if(deleted) {
				resp.getWriter().write("Vote deleted successfully");				
			}else {
				resp.getWriter().write("Vote delete fail");
			}
		} else {
			Vote vote = new Vote(currentUser.getUser_id(), questionId, voteType);
			boolean ok = voteDAO.questionVote(vote);
			System.out.println(ok);
			if (ok) {
				resp.getWriter().write("Vote registered successfully");
			} else {
				resp.getWriter().write("Vote registered fail");
			}
		}

	}

	private void createAnswer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User currentUser = (User) session.getAttribute("user");
		if (currentUser != null) {
			String body = req.getParameter("answerContent");
			int questionId = Integer.parseInt(req.getParameter("questionId"));
			System.out.println("body:" + body);
			System.out.println("questionId:" + questionId);
			Answer answer = new Answer(body, currentUser.getUser_id(), questionId);
			boolean ok = answerDAO.createAnswer(answer);
			if (ok) {
				System.out.println("question creaetd");
				req.setAttribute("questionId", questionId);
			} else {
				req.setAttribute("success", ok);
			}
			 resp.sendRedirect(req.getContextPath() + "/questions?mode=DETAILS&questionId=" + questionId);
		} else {
			resp.sendRedirect("login");
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}

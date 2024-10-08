package com.devask.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


public class QuestionDAO {
	private Connection connection;
	private Statement statement;
	private PreparedStatement pStmt;
	private ResultSet rs;
	private final DataSource dataSource;
	
	public QuestionDAO(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	
	private void close() {
		if(connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public boolean createQuestion(Question question) {
		boolean ok = false;
		try {
			connection = dataSource.getConnection();
			pStmt = connection.prepareStatement("insert into questions "
					+ "(title,description,body,user_id) "
					+ "values(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			pStmt.setString(1, question.getTitle());
			pStmt.setString(2, question.getDescription());
			pStmt.setString(3, question.getBody());
			pStmt.setDouble(4, question.getUserId());
			int rowEffected = pStmt.executeUpdate();
			ResultSet generatedKey = pStmt.getGeneratedKeys();
			if(rowEffected > 0) {
				if(generatedKey.next()) {
					int questionId = generatedKey.getInt(1);
					ok = true;
					if(question.getTags() != null && !question.getTags().isEmpty()) {
						pStmt = connection.prepareStatement("INSERT INTO questiontags (question_id, tag_id) VALUES (?, ?)");
	                    for (Tag tag : question.getTags()) {
	                        pStmt.setInt(1, questionId);
	                        pStmt.setInt(2, tag.getTagId());
	                        pStmt.addBatch();
	                    }
	                    pStmt.executeBatch();
					}
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}
		return ok;
	}
	
	public List<Question> getAllQuestion() {
	    List<Question> questions = new ArrayList<Question>();
	    try {
	        connection = dataSource.getConnection();
	        
	        
	        String sql = "SELECT " +
                    "q.question_id, " +
                    "q.title, " +
                    "q.body, " +
                    "q.vote, " +
                    "q.user_id, " +
                    "q.description, " +
                    "q.created_at, " +
                    "COUNT(a.answer_id) AS answer_count, " +
                    "COUNT(v.user_id) AS vote_count " + 
                    "FROM questions q " +
                    "LEFT JOIN answers a ON q.question_id = a.question_id " +
                    "LEFT JOIN votes v ON q.question_id = v.question_id " +
                    "GROUP BY " +
                    "q.question_id, " +
                    "q.title, " +
                    "q.body, " +
                    "q.vote, " +
                    "q.user_id, " +
                    "q.description, " +
                    "q.created_at " +
                    "ORDER BY q.question_id DESC";

	        PreparedStatement statement = connection.prepareStatement(sql);
	      
	        rs = statement.executeQuery();
	        while (rs.next()) {
	            List<Tag> tags = new ArrayList<Tag>();
	            int questionId = rs.getInt("question_id");
	            String tagSql = "SELECT t.tag_id, t.tag_name " +
	                            "FROM tags t " +
	                            "INNER JOIN questiontags qt ON t.tag_id = qt.tag_id " +
	                            "WHERE qt.question_id = ?";
	            PreparedStatement tagsStmt = connection.prepareStatement(tagSql);
	            tagsStmt.setInt(1, questionId);
	            ResultSet tagsRs = tagsStmt.executeQuery();
	            while (tagsRs.next()) {
	                tags.add(new Tag(
	                    tagsRs.getInt("tag_id"),
	                    tagsRs.getString("tag_name")
	                ));
	            }
	            tagsRs.close();

	            questions.add(new Question(
	                rs.getInt("question_id"), 
	                rs.getString("title"), 
	                rs.getString("description"),
	                rs.getString("body"), 
	                rs.getInt("vote_count"), 
	                rs.getInt("user_id"), 
	                rs.getTimestamp("created_at").toLocalDateTime(),
	                tags,
	                rs.getInt("answer_count")
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	    return questions;
	}
	
	public List<Question> searchQuestion(String searchTerm) {
	    List<Question> questions = new ArrayList<Question>();
	    try {
	        connection = dataSource.getConnection();
	        statement = connection.createStatement();
	        
	        // Prepare the SQL query with fuzzy search
	        String sql = "SELECT " +
	                     "q.question_id, " +
	                     "q.title, " +
	                     "q.body, " +
	                     "q.vote, " +
	                     "q.user_id, " +
	                     "q.description, " +
	                     "q.created_at, " +
	                     "COUNT(a.answer_id) AS answer_count, " +
	                     "COUNT(DISTINCT v.user_id) AS vote_count " + 
	                     "FROM questions q " +
	                     "LEFT JOIN answers a ON q.question_id = a.question_id " +
	                     "LEFT JOIN votes v ON q.question_id = v.question_id " +
	                     "WHERE q.title LIKE ? OR q.body LIKE ? " + // Fuzzy search condition
	                     "GROUP BY " +
	                     "q.question_id, " +
	                     "q.title, " +
	                     "q.body, " +
	                     "q.vote, " +
	                     "q.user_id, " +
	                     "q.description, " +
	                     "q.created_at " +
	                     "ORDER BY q.created_at DESC;";

	        // Use PreparedStatement to set the search term with wildcards
	        PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        String fuzzySearchTerm = "%" + searchTerm + "%"; // Wildcard for fuzzy search
	        preparedStatement.setString(1, fuzzySearchTerm);
	        preparedStatement.setString(2, fuzzySearchTerm);
	        
	        rs = preparedStatement.executeQuery();
	        while (rs.next()) {
	            List<Tag> tags = new ArrayList<Tag>();
	            int questionId = rs.getInt("question_id");
	            String tagSql = "SELECT t.tag_id, t.tag_name " +
	                            "FROM tags t " +
	                            "INNER JOIN questiontags qt ON t.tag_id = qt.tag_id " +
	                            "WHERE qt.question_id = ?";
	            PreparedStatement tagsStmt = connection.prepareStatement(tagSql);
	            tagsStmt.setInt(1, questionId);
	            ResultSet tagsRs = tagsStmt.executeQuery();
	            while (tagsRs.next()) {
	                tags.add(new Tag(
	                        tagsRs.getInt("tag_id"),
	                        tagsRs.getString("tag_name")
	                ));
	                System.out.println(tagsRs.getString("tag_name"));
	            }
	            tagsRs.close(); 
	            
	            questions.add(new Question(
	                    rs.getInt("question_id"), 
	                    rs.getString("title"), 
	                    rs.getString("description"),
	                    rs.getString("body"), 
	                    rs.getInt("vote_count"), 
	                    rs.getInt("user_id"), 
	                    rs.getTimestamp("created_at").toLocalDateTime(),
	                    tags,
	                    rs.getInt("answer_count")));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	    return questions;
	}

	
	public List<Question> getQuestionByUserId(int userId) {
	    List<Question> questions = new ArrayList<>();
	    
	    try {
	        connection = dataSource.getConnection();
	        
	        // Use PreparedStatement to execute the query
	        String sql = "SELECT " +
	                     "q.question_id, " +
	                     "q.title, " +
	                     "q.body, " +
	                     "q.vote, " +
	                     "q.user_id, " +
	                     "q.description, " +
	                     "q.created_at, " +
	                     "COUNT(a.answer_id) AS answer_count " +
	                     "FROM questions q " +
	                     "LEFT JOIN answers a ON q.question_id = a.question_id " +
	                     "WHERE q.user_id = ? " +
	                     "GROUP BY " +
	                     "q.question_id, " +
	                     "q.title, " +
	                     "q.body, " +
	                     "q.vote, " +
	                     "q.user_id, " +
	                     "q.description, " +
	                     "q.created_at";
	                     
	        PreparedStatement pStmt = connection.prepareStatement(sql);
	        pStmt.setInt(1, userId);
	        ResultSet rs = pStmt.executeQuery();
	        
	        while (rs.next()) {
	            int questionId = rs.getInt("question_id");
	            List<Tag> tags = new ArrayList<>();
	            
	            // Query to get tags for the specific question
	            String tagSql = "SELECT t.tag_id, t.tag_name " +
	                            "FROM tags t " +
	                            "INNER JOIN questiontags qt ON t.tag_id = qt.tag_id " +
	                            "WHERE qt.question_id = ?";
	            PreparedStatement tagsStmt = connection.prepareStatement(tagSql);
	            tagsStmt.setInt(1, questionId);
	            ResultSet tagsRs = tagsStmt.executeQuery();
	            while (tagsRs.next()) {
	                tags.add(new Tag(
	                    tagsRs.getInt("tag_id"),
	                    tagsRs.getString("tag_name")
	                ));
	            }
	            tagsRs.close();
	            tagsStmt.close(); // Close the PreparedStatement after use
	            
	            questions.add(new Question(
	                rs.getInt("question_id"), 
	                rs.getString("title"), 
	                rs.getString("description"),
	                rs.getString("body"), 
	                rs.getInt("vote"), 
	                rs.getInt("user_id"), 
	                rs.getTimestamp("created_at").toLocalDateTime(),
	                tags,
	                rs.getInt("answer_count")
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(); // Ensure resources are closed in the finally block
	    }
	    
	    return questions;
	}

	public int getTotalQuestionCount() {
	    int totalCount = 0;
	    try {
	        connection = dataSource.getConnection();
	        String sql = "SELECT COUNT(*) AS total FROM questions";
	        Statement statement = connection.createStatement();
	        ResultSet rs = statement.executeQuery(sql);
	        if (rs.next()) {
	            totalCount = rs.getInt("total");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	    return totalCount;
	}

	
	
	public List<Question> getAllQuestion(int page, int pageSize) {
	    List<Question> questions = new ArrayList<Question>();
	    try {
	        connection = dataSource.getConnection();
	        // Calculate the offset
	        int offset = (page - 1) * pageSize;
	        
	        
	        String sql = "SELECT " +
                    "q.question_id, " +
                    "q.title, " +
                    "q.body, " +
                    "q.vote, " +
                    "q.user_id, " +
                    "q.description, " +
                    "q.created_at, " +
                    "COUNT(a.answer_id) AS answer_count, " +
                    "COUNT(v.user_id) AS vote_count " + 
                    "FROM questions q " +
                    "LEFT JOIN answers a ON q.question_id = a.question_id " +
                    "LEFT JOIN votes v ON q.question_id = v.question_id " +
                    "GROUP BY " +
                    "q.question_id, " +
                    "q.title, " +
                    "q.body, " +
                    "q.vote, " +
                    "q.user_id, " +
                    "q.description, " +
                    "q.created_at " +
                    "ORDER BY q.question_id DESC " + 
                    "LIMIT ? OFFSET ?";

	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setInt(1, pageSize); // Set the limit (number of rows to return)
	        statement.setInt(2, offset); // Set the offset (number of rows to skip)

	        rs = statement.executeQuery();
	        while (rs.next()) {
	            List<Tag> tags = new ArrayList<Tag>();
	            int questionId = rs.getInt("question_id");
	            String tagSql = "SELECT t.tag_id, t.tag_name " +
	                            "FROM tags t " +
	                            "INNER JOIN questiontags qt ON t.tag_id = qt.tag_id " +
	                            "WHERE qt.question_id = ?";
	            PreparedStatement tagsStmt = connection.prepareStatement(tagSql);
	            tagsStmt.setInt(1, questionId);
	            ResultSet tagsRs = tagsStmt.executeQuery();
	            while (tagsRs.next()) {
	                tags.add(new Tag(
	                    tagsRs.getInt("tag_id"),
	                    tagsRs.getString("tag_name")
	                ));
	            }
	            tagsRs.close();

	            questions.add(new Question(
	                rs.getInt("question_id"), 
	                rs.getString("title"), 
	                rs.getString("description"),
	                rs.getString("body"), 
	                rs.getInt("vote_count"), 
	                rs.getInt("user_id"), 
	                rs.getTimestamp("created_at").toLocalDateTime(),
	                tags,
	                rs.getInt("answer_count")
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	    return questions;
	}

	public List<Question> getTopBestQuestions() {
	    List<Question> bestQuestions = new ArrayList<>();
	    String sql = "SELECT " +
	            "q.question_id, " +
	            "q.title, " +
	            "q.body, " +
	            "q.vote, " +
	            "q.user_id, " +
	            "q.description, " +
	            "q.created_at, " +
	            "COUNT(a.answer_id) AS answer_count, " +
	            "COUNT(v.user_id) AS vote_count " + 
	            "FROM questions q " +
	            "LEFT JOIN answers a ON q.question_id = a.question_id " +
	            "LEFT JOIN votes v ON q.question_id = v.question_id " +
	            "GROUP BY " +
	            "q.question_id, " +
	            "q.title, " +
	            "q.body, " +
	            "q.vote, " +
	            "q.user_id, " +
	            "q.description, " +
	            "q.created_at " +
	            "ORDER BY vote_count DESC " + 
	            "LIMIT 2";

	    try {
	        connection = dataSource.getConnection();
	        PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        ResultSet rs = preparedStatement.executeQuery();
	        
	        while (rs.next()) {
	            List<Tag> tags = new ArrayList<>();
	            int questionId = rs.getInt("question_id");
	            String tagSql = "SELECT t.tag_id, t.tag_name " +
	                            "FROM tags t " +
	                            "INNER JOIN questiontags qt ON t.tag_id = qt.tag_id " +
	                            "WHERE qt.question_id = ?";
	            try (PreparedStatement tagsStmt = connection.prepareStatement(tagSql)) {
	                tagsStmt.setInt(1, questionId);
	                ResultSet tagsRs = tagsStmt.executeQuery();
	                
	                while (tagsRs.next()) {
	                    tags.add(new Tag(
	                        tagsRs.getInt("tag_id"),
	                        tagsRs.getString("tag_name")
	                    ));
	                }
	                tagsRs.close();
	            } catch (SQLException e) {
	                e.printStackTrace(); // Optionally log the error or handle it
	            }

	            // Creating the Question object
	            Question bestQuestion = new Question(
	                rs.getInt("question_id"),
	                rs.getString("title"),
	                rs.getString("description"),
	                rs.getString("body"),
	                rs.getInt("vote_count"),
	                rs.getInt("user_id"),
	                rs.getTimestamp("created_at").toLocalDateTime(),
	                tags,
	                rs.getInt("answer_count")
	            );

	            bestQuestions.add(bestQuestion); // Add the question to the list
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Optionally log the error or handle it
	    } finally {
	        close(); // Ensure resources are closed
	    }
	    
	    return bestQuestions; // Return the list of best questions
	}


	
	public Question getQuestion(int questionId) {
	    Question question = null;
	    List<Answer> answerList = new ArrayList<>();
	    List<Tag> tagList = new ArrayList<>();
	    
	    try {
	        connection = dataSource.getConnection();
	        String questionSql = "SELECT q.question_id, q.title, q.body, q.vote, q.user_id, q.description, q.created_at, COUNT(v.user_id) AS votes " +
	                "FROM questions q " +
	                "LEFT JOIN votes v ON q.question_id = v.question_id " +
	                "WHERE q.question_id = ? " +
	                "GROUP BY q.question_id, q.title, q.body, q.vote, q.user_id, q.description, q.created_at";
	        
	        // Use PreparedStatement to prevent SQL injection
	        PreparedStatement questionStmt = connection.prepareStatement(questionSql);
	        questionStmt.setInt(1, questionId);
	        ResultSet rs = questionStmt.executeQuery();
	        
	        if (rs.next()) {
	        	System.out.println(rs.getInt("question_id"));
	            question = new Question(
	                rs.getInt("question_id"), 
	                rs.getString("title"), 
	                rs.getString("description"),
	                rs.getString("body"), 
	                rs.getInt("votes"), 
	                rs.getInt("user_id"), 
	                rs.getTimestamp("created_at").toLocalDateTime(),
	                tagList, // Initialize with an empty list
	                answerList // Initialize with an empty list
	            );
	        }
	        
	        String answersSql = "SELECT " +
	                            "a.answer_id, " +
	                            "a.body, " +
	                            "a.user_id, " +
	                            "a.vote, " +
	                            "a.created_at, " +
	                            "COUNT(c.comment_id) AS commentCount " +
	                        "FROM answers a " +
	                        "LEFT JOIN comments c ON a.answer_id = c.answer_id " +
	                        "WHERE a.question_id = ? " +
	                        "GROUP BY a.answer_id, a.body, a.user_id, a.vote, a.created_at";
	        
	        PreparedStatement answersStmt = connection.prepareStatement(answersSql);
	        answersStmt.setInt(1, questionId);
	        rs = answersStmt.executeQuery();
	        
	        while (rs.next()) {
	            Answer answer = new Answer(
	                rs.getInt("answer_id"),
	                rs.getString("body"),
	                rs.getInt("user_id"),
	                rs.getInt("vote"),
	                rs.getInt("commentCount"),
	                rs.getTimestamp("created_at").toLocalDateTime()
	            );
	            String votesSql = "SELECT v.user_id, v.answer_id " +
	                      "FROM votes v " +
	                      "WHERE v.answer_id = ?";
	    PreparedStatement votesStmt = connection.prepareStatement(votesSql);
	    votesStmt.setInt(1, answer.getAnswerId()); // Use the answerId from the current answer
	    ResultSet votesRs = votesStmt.executeQuery();

	    List<Vote> voteList = new ArrayList<>();
	    while (votesRs.next()) {
	        Vote vote = new Vote(
	            votesRs.getInt("user_id"),
	            votesRs.getInt("answer_id")
	        );
	        voteList.add(vote);
	    }
	    
	    // Set the vote list in the answer
	    answer.setVotes(voteList);
	    
	            answerList.add(answer);
	        }
	        
	        // Third query to get tags for the specific question
	        String tagsSql = "SELECT " +
	                         "t.tag_id, " +
	                         "t.tag_name " +
	                         "FROM tags t " +
	                         "INNER JOIN questiontags qt ON t.tag_id = qt.tag_id " +
	                         "WHERE qt.question_id = ?";
	        
	        PreparedStatement tagsStmt = connection.prepareStatement(tagsSql);
	        tagsStmt.setInt(1, questionId);
	        rs = tagsStmt.executeQuery();
	        
	        while (rs.next()) {
	            Tag tag = new Tag(
	                rs.getInt("tag_id"),
	                rs.getString("tag_name")
	            );
	            tagList.add(tag);
	        }
	        
	        // Update the question with the populated answer list and tag list
	        if (question != null) {
	            question.setAnswers(answerList);
	            question.setTags(tagList); // Make sure your Question class has a setTags method
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	    
	    return question;
	}
	
	
	
	public List<Question> getRelatedQuestion(int tagId) {
	    List<Question> questions = new ArrayList<>();
	    System.out.println("work");
	    try {
	        connection = dataSource.getConnection();
	        
	        // First query to get questions and answer counts for the given tagId
	        String sql = "SELECT q.question_id, q.title, q.created_at, COUNT(a.answer_id) AS answer_count " +
	                     "FROM questions q " +
	                     "LEFT JOIN answers a ON q.question_id = a.question_id " +
	                     "JOIN questiontags qt ON q.question_id = qt.question_id " +
	                     "JOIN tags t ON qt.tag_id = t.tag_id " +
	                     "WHERE t.tag_id = ? " +
	                     "GROUP BY q.question_id;";
	        
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setInt(1, tagId);
	        rs = statement.executeQuery();
	        
	        while (rs.next()) {
	            int questionId = rs.getInt("question_id");
	            List<Tag> tagList = new ArrayList<>();
	            
	            // Second query to get tags associated with the current question
	            String tagsSql = "SELECT t.tag_id, t.tag_name " +
	                             "FROM tags t " +
	                             "INNER JOIN questiontags qt ON t.tag_id = qt.tag_id " +
	                             "WHERE qt.question_id = ?";
	            
	            PreparedStatement tagsStmt = connection.prepareStatement(tagsSql);
	            tagsStmt.setInt(1, questionId);
	            ResultSet tagsRs = tagsStmt.executeQuery();
	            
	            while (tagsRs.next()) {
	                Tag tag = new Tag(
	                    tagsRs.getInt("tag_id"),
	                    tagsRs.getString("tag_name")
	                );
	                System.out.println(tag.getTag_name());
	                tagList.add(tag);
	            }
	            tagsRs.close(); 
	            
	            questions.add(new Question(
	                rs.getInt("question_id"), 
	                rs.getString("title"), 
	                rs.getTimestamp("created_at").toLocalDateTime(), 
	                tagList, 
	                rs.getInt("answer_count")
	            ));
				tagList.clear();
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(); 
	    }
	    
	    return questions;
	}

	public boolean deleteQuestion(int questionId) {
	    boolean isDeleted = false;
	    Connection connection = null;
	    PreparedStatement statement = null;

	    try {
	        // Get a connection to the database
	        connection = dataSource.getConnection();

	        // Delete the question from the questions table
	        String sql = "DELETE FROM questions WHERE question_id = ?";
	        statement = connection.prepareStatement(sql);
	        statement.setInt(1, questionId);
	        int rowsAffected = statement.executeUpdate();

	        // Check if the question was successfully deleted
	        if (rowsAffected > 0) {
	            isDeleted = true;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Close resources
	        try {
	            if (statement != null) statement.close();
	            if (connection != null) connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return isDeleted;
	}

	public List<Integer> getQuestionTrendsData() {
	    List<Integer> questionTrendsData = new ArrayList<>();
	    String query = "SELECT COUNT(question_id) as question_count FROM questions GROUP BY WEEK(created_at)";
	    
	    try {
	        connection = dataSource.getConnection();
	        pStmt = connection.prepareStatement(query);
	        rs = pStmt.executeQuery();
	        
	        while (rs.next()) {
	            questionTrendsData.add(rs.getInt("question_count"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	    
	    return questionTrendsData;
	}

}


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
	    List<Question> questions = new ArrayList<>();
	    
	    try {
	        connection = dataSource.getConnection();
	        statement = connection.createStatement();
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
	                     "GROUP BY " +
	                     "q.question_id, " +
	                     "q.title, " +
	                     "q.body, " +
	                     "q.vote, " +
	                     "q.user_id, " +
	                     "q.description, " +
	                     "q.created_at " + 
	                     "order by q.created_at desc;";

	        ResultSet rs = statement.executeQuery(sql);
	        while (rs.next()) {
	            int questionId = rs.getInt("question_id");
	            List<Tag> tags = new ArrayList<>(); // Initialize a new list for each question
	            
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
	            tagsRs.close(); // Ensure the ResultSet is closed
	            
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


	public List<Question> getAllQuestion(int page){
		List<Question> questions = new ArrayList<Question>();
		List<Tag> tags = new ArrayList<Tag>();
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
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
		             "GROUP BY " +
		             "q.question_id, " +
		             "q.title, " +
		             "q.body, " +
		             "q.vote, " +
		             "q.user_id, " +
		             "q.description, " +
		             "q.created_at "+
		             "LIMIT "+page+";";

			rs = statement.executeQuery(sql);
			while(rs.next()) {
				
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
						rs.getInt("vote"), 
						rs.getInt("user_id"), 
						rs.getTimestamp("created_at").toLocalDateTime(),
						tags,
						rs.getInt("answer_count")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}
		return questions;
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
	                        "LEFT JOIN comments c ON a.answer_id = c.answer_id " + // Use LEFT JOIN to include answers with no comments
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
	                tagList.add(tag);
	            }
	            
	            tagsRs.close(); // Close the second ResultSet after use
	            
	            questions.add(new Question(
	                rs.getInt("question_id"), 
	                rs.getString("title"), 
	                rs.getTimestamp("created_at").toLocalDateTime(), 
	                tagList, 
	                rs.getInt("answer_count")
	            ));
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(); 
	    }
	    
	    return questions;
	}


}


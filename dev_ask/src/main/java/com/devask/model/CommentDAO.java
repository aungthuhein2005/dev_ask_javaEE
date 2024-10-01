package com.devask.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class CommentDAO {
	private Connection connection;
	private Statement statement;
	private PreparedStatement pStmt;
	private ResultSet rs;
	private final DataSource dataSource;
	
	public CommentDAO(DataSource dataSource) {
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
	
	public Comment createComment(Comment comment) {
	    Connection connection = null;
	    PreparedStatement pStmt = null;
	    PreparedStatement selectStmt = null;
	    ResultSet generatedKeys = null;
	    ResultSet rs = null;

	    try {
	        connection = dataSource.getConnection();
	        
	        // Insert the comment
	        pStmt = connection.prepareStatement(
	            "INSERT INTO comments (body, user_id, answer_id) VALUES (?, ?, ?)",
	            Statement.RETURN_GENERATED_KEYS
	        );
	        pStmt.setString(1, comment.getBody());
	        pStmt.setInt(2, comment.getUserId());
	        pStmt.setInt(3, comment.getAnswerId());

	        int rowsAffected = pStmt.executeUpdate();
	        if (rowsAffected > 0) {
	            generatedKeys = pStmt.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                long generatedId = generatedKeys.getLong(1);
	                selectStmt = connection.prepareStatement(
	                    "SELECT comment_id, body, users.user_id, answer_id, comments.created_at,question_id, username " +
	                    "FROM comments JOIN users ON users.user_id = comments.user_id " +
	                    "WHERE comment_id = ?"
	                );
	                selectStmt.setLong(1, generatedId);
	                rs = selectStmt.executeQuery();

	                if (rs.next()) {
	                    return new Comment(
	    						rs.getInt("comment_id"), 
	    						rs.getString("body"),
	    						rs.getInt("user_id"), 
	    						rs.getString("username"),
	    						rs.getInt("question_id"), 
	    						rs.getInt("answer_id"), 
	    						rs.getTimestamp("created_at").toLocalDateTime());
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Ensure resources are closed to prevent leaks
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (generatedKeys != null) {
	                generatedKeys.close();
	            }
	            if (selectStmt != null) {
	                selectStmt.close();
	            }
	            if (pStmt != null) {
	                pStmt.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace(); // Handle closing exceptions
	        }
	    }
	    return null; // Return null if creation failed or no ID was retrieved
	}


	
	public List<Comment> getCommentsByAnswer(int anwerId){
		List<Comment> comments = new ArrayList<Comment>();
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery("select comment_id,body,users.user_id,question_id,answer_id,comments.created_at,username from comments join users on users.user_id=comments.user_id where answer_id="+anwerId+";");
			while(rs.next()) {
				comments.add(new Comment(
						rs.getInt("comment_id"), 
						rs.getString("body"),
						rs.getInt("user_id"), 
						rs.getString("username"),
						rs.getInt("question_id"), 
						rs.getInt("answer_id"), 
						rs.getTimestamp("created_at").toLocalDateTime()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}
		return comments;
	}
	
}

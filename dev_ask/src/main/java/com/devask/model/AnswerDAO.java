package com.devask.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class AnswerDAO {
	private Connection connection;
	private Statement statement;
	private PreparedStatement pStmt;
	private ResultSet rs;
	private final DataSource dataSource;
	
	public AnswerDAO(DataSource dataSource) {
		super();
		this.dataSource=dataSource;
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
	
	public boolean createAnswer(Answer answer) {
		boolean ok = false;
		try {
			connection = dataSource.getConnection();
			pStmt = connection.prepareStatement("insert into answers "
					+ "(body,user_id,question_id) "
					+ "values(?, ?, ?)");
			pStmt.setString(1, answer.getBody());
			pStmt.setInt(2, answer.getUserId());
			pStmt.setInt(3, answer.getQuestionId());
			int rowEffected = pStmt.executeUpdate();
			if(rowEffected > 0) {
				ok = true; 
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}
		return ok;
	}
	
	public List<Integer> getAnswerDistributionData() {
	    List<Integer> answerDistributionData = new ArrayList<>();
	    String query = "SELECT "
	                 + "SUM(CASE WHEN status = 'correct' THEN 1 ELSE 0 END) as correct_count, "
	                 + "SUM(CASE WHEN status = 'incorrect' THEN 1 ELSE 0 END) as incorrect_count, "
	                 + "SUM(CASE WHEN status = 'pending' THEN 1 ELSE 0 END) as pending_count "
	                 + "FROM answers";
	    
	    try {
	        connection = dataSource.getConnection();
	        pStmt = connection.prepareStatement(query);
	        rs = pStmt.executeQuery();
	        
	        if (rs.next()) {
	            answerDistributionData.add(rs.getInt("correct_count"));
	            answerDistributionData.add(rs.getInt("incorrect_count"));
	            answerDistributionData.add(rs.getInt("pending_count"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	    
	    return answerDistributionData;
	}

}

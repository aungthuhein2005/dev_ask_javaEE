package com.devask.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}

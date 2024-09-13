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

public class TagDAO {
	private Connection connection;
	private Statement statement;
	private PreparedStatement pStmt;
	private ResultSet rs;
	private final DataSource dataSource;
	
	public TagDAO(DataSource dataSource) {
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
	
//	public boolean createExpense(Expense expense) {
//		boolean ok = false;
//		try {
//			connection = dataSource.getConnection();
//			pStmt = connection.prepareStatement("insert into expense "
//					+ "(name,qty,price,sub_total,issued_date,description,image) "
//					+ "values(?, ?, ?, ?, ?, ?, ?)");
//			pStmt.setString(1, expense.getName());
//			pStmt.setInt(2, expense.getQty());
//			pStmt.setDouble(3, expense.getPrice());
//			pStmt.setDouble(4, expense.getSubTotal());
//			pStmt.setTimestamp(5, Timestamp.valueOf(expense.getIssuedDate()));
//			pStmt.setString(6, expense.getDescription());
//			pStmt.setString(7, expense.getImage());
//			int rowEffected = pStmt.executeUpdate();
//			if(rowEffected > 0) {
//				ok = true;
//			}
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally {
//			close();
//		}
//		return ok;
//	}
	
	public List<Tag> getAllTags(){
		List<Tag> tags = new ArrayList<Tag>();
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery("select * from tags;");
			while(rs.next()) {
				tags.add(new Tag(
						rs.getInt("tag_id"),
						rs.getString("tag_name")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}
		return tags;
	}
	
	public List<Tag> getInerestTags(int userId) {
		List<Tag> tags = new ArrayList<Tag>();
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery("select tags.tag_id,tag_name from user_interests join tags on tags.tag_id = user_interests.tag_id where user_id ="+userId+";");
			while(rs.next()) {
				tags.add(new Tag(
						rs.getInt("tag_id"),
						rs.getString("tag_name")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}
		return tags;
	}
	
//	public Expense getExpenseById(Long expenseId){
//		try {
//			connection = dataSource.getConnection();
//			statement = connection.createStatement();
//			rs = statement.executeQuery("select * from expense where id='"+expenseId+"';");
//			while(rs.next()) {
//				return new Expense(
//						rs.getLong("id"),
//						rs.getString("name"),
//						rs.getInt("qty"),
//						rs.getDouble("price"),
//						rs.getString("description"),
//						rs.getString("image"));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally {
//			close();
//		}
//		return null;
//	}
}

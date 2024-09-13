package com.devask.model;

import java.security.NoSuchAlgorithmException; 
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import com.devask.crypto.PasswordEncoder;
import com.devask.crypto.PasswordValidator;

public class UserDAO {
	private Connection connection;
	private Statement statement;
	private PreparedStatement pStmt;
	private ResultSet rs;
	private final DataSource dataSource;
	
	public UserDAO(DataSource dataSource) {
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
	
	public List<User> getAllUser(){
		List<User> users = new ArrayList<User>();
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery("select * from users");
			while(rs.next()) {
				users.add(new User(
						rs.getInt("user_id"),
						rs.getString("username"),
						rs.getString("password"),
						rs.getInt("reputation"),
						rs.getString("created_at")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}
		return users;
	}
	
	private User getUserByEmail(String email) {
	    User user = null;
	    try {
	        connection = dataSource.getConnection();
	        statement = connection.createStatement();
	        rs = statement.executeQuery("SELECT * FROM users WHERE email='"+email+"';");
	        while (rs.next()) {
	            user = new User(
	                rs.getString("username"),
	                rs.getString("password")
	            );
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	    return user;
	}
	
	public boolean isAuthenticated(String email,String password) {
		boolean ok = false;
		User user = getUserByEmail(email);
		try {
			if(user!=null && PasswordValidator.validatePassword(password, user.getPassword())) {
				ok = true;
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok;
	}
	
	public boolean createUser(User user) {
		boolean ok = false;
		try {
			connection = dataSource.getConnection();
			pStmt = connection.prepareStatement("insert into users (username,email,password,dob) values (?,?,?,?);");
			pStmt.setString(1, user.getusername());
			pStmt.setString(2, user.getEmail());
			try {
				pStmt.setString(3, PasswordEncoder.encode(user.getPassword()));
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pStmt.setString(4, user.getDob().toString());
			int rowEffected = pStmt.executeUpdate();
			if(rowEffected > 0) {
				ok = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ok;
	}

	
	public User getUser(int userId) {
	    User user = null;
	    HashMap<String, String> socialList = new HashMap<>();
	    try {
	        connection = dataSource.getConnection();

	        // Fetch user information
	        statement = connection.createStatement();
	        rs = statement.executeQuery("SELECT * FROM users WHERE user_id = "+userId+";");
	        if (rs.next()) {
	            user = new User(
	                rs.getInt("user_id"),
	                rs.getString("username"),
	                rs.getInt("reputation"),
	                rs.getString("about"),
	                rs.getString("dob"),
	                socialList
	            );
	        }

	        // Fetch social links for the user
	        statement = connection.createStatement();
	        rs = statement.executeQuery("SELECT platform, link FROM social_links WHERE user_id = 1;");
	        while (rs.next()) {
	            String platform = rs.getString("platform");
	            String link = rs.getString("link");
	            System.out.println(platform);
	            socialList.put(platform, link);
	        }

	        // Update the user object with social links
	        if (user != null) {
	            user.setSocialList(socialList);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	    return user;
	}
	
	public boolean updateUser(User user,List<Tag> interestTags) {
		System.out.println("in sql"+user.getUser_id());
	    boolean ok = false;
	    try {
	        connection = dataSource.getConnection();
	        
	        pStmt = connection.prepareStatement("UPDATE users SET username = ?, about = ?, dob = ? WHERE user_id = ?;");
	        pStmt.setString(1, user.getusername());
	        pStmt.setString(2, user.getAbout());
	        pStmt.setString(3, user.getDob());
	        pStmt.setInt(4, user.getUser_id());
	        int rowEffected = pStmt.executeUpdate();

	        if(rowEffected > 0) {
	            pStmt = connection.prepareStatement("DELETE FROM user_interests WHERE user_id = ?;");
	            pStmt.setInt(1, user.getUser_id());
	            pStmt.executeUpdate();
	            
	            for (Tag tag : interestTags) {
	                pStmt = connection.prepareStatement("INSERT INTO user_interests (user_id, tag_id) VALUES (?, ?);");
	                pStmt.setInt(1, user.getUser_id());
	                pStmt.setInt(2, tag.getTagId());
	                pStmt.executeUpdate();
	            }
	            
	            ok = true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	    	close();
	    }
	    return ok;
	}

	
	public User getUserDataByEmail(String email) {
	    User user = null;
	    HashMap<String, String> socialList = new HashMap<>();
	    try {
	        connection = dataSource.getConnection();

	        // Fetch user information
	        statement = connection.createStatement();
	        rs = statement.executeQuery("SELECT * FROM users WHERE email='"+email+"';");
	        if (rs.next()) {
	            user = new User(
	                rs.getInt("user_id"),
	                rs.getString("username"),
	                rs.getInt("reputation"),
	                rs.getString("about"),
	                rs.getString("dob"),
	                socialList
	            );
	        }

	        // Fetch social links for the user
	        statement = connection.createStatement();
	        rs = statement.executeQuery("SELECT platform, link FROM social_links WHERE user_id = 1;");
	        while (rs.next()) {
	            String platform = rs.getString("platform");
	            String link = rs.getString("link");
	            System.out.println(platform);
	            socialList.put(platform, link);
	        }

	        // Update the user object with social links
	        if (user != null) {
	            user.setSocialList(socialList);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	    return user;
	}

}

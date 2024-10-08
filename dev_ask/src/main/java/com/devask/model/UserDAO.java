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
	
	public List<User> searchUsers(String searchQuery) {
	    List<User> users = new ArrayList<>();
	    try {
	        connection = dataSource.getConnection();
	        String sql = "SELECT * FROM users WHERE username LIKE ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        
	        // Use '%' wildcards for partial matching
	        preparedStatement.setString(1, "%" + searchQuery + "%");
	        
	        rs = preparedStatement.executeQuery();
	        while (rs.next()) {
	            users.add(new User(
	                rs.getInt("user_id"),
	                rs.getString("username"),
	                rs.getString("password"),
	                rs.getInt("reputation"),
	                rs.getString("created_at")
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	    return users;
	}

	public int getTotalCount() {
	    int totalCount = 0;
	    try {
	        connection = dataSource.getConnection();
	        String sql = "SELECT COUNT(*) AS total FROM users";
	        Statement statement = connection.createStatement();
	        rs = statement.executeQuery(sql);
	        
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

	
	public List<User> getAllUsers(int page, int pageSize) {
	    List<User> users = new ArrayList<>();
	    try {
	        connection = dataSource.getConnection();
	        String sql = "SELECT * FROM users LIMIT ? OFFSET ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        
	        // Calculate limit and offset
	        int limit = pageSize; // Number of records to retrieve
	        int offset = (page - 1) * pageSize; // Starting point for the query
	        
	        preparedStatement.setInt(1, limit);
	        preparedStatement.setInt(2, offset);
	        
	        rs = preparedStatement.executeQuery();
	        while (rs.next()) {
	            users.add(new User(
	                rs.getInt("user_id"),
	                rs.getString("username"),
	                rs.getString("password"),
	                rs.getInt("reputation"),
	                rs.getString("created_at")
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	    return users;
	}

	public List<User> getAllUsers() {
	    List<User> users = new ArrayList<>();
	    try {
	        connection = dataSource.getConnection();
	        String sql = "SELECT * FROM users";
	        PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        
	        
	        rs = preparedStatement.executeQuery();
	        while (rs.next()) {
	            users.add(new User(
		                rs.getInt("user_id"),
		                rs.getString("username"),
		                rs.getString("email"),
		                rs.getInt("reputation")
		            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	    return users;
	}
	
	public List<User> getTopReputationUsers(int limit) {
	    List<User> users = new ArrayList<>();
	    try {
	        connection = dataSource.getConnection();
	        String sql = "SELECT * FROM users ORDER BY reputation DESC LIMIT ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, limit);
	        
	        rs = preparedStatement.executeQuery();
	        while (rs.next()) {
	            users.add(new User(
	                rs.getInt("user_id"),
	                rs.getString("username"),
	                rs.getString("password"),
	                rs.getInt("reputation"),
	                rs.getString("created_at")
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
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
	
	public boolean deleteUser(int userId) {
	    boolean isDeleted = false;
	    try {
	        connection = dataSource.getConnection();
	        
	        // Begin transaction
	        connection.setAutoCommit(false);

	        // First, delete from the social_links table
	        PreparedStatement deleteSocialLinksStmt = connection.prepareStatement("DELETE FROM social_links WHERE user_id = ?;");
	        deleteSocialLinksStmt.setInt(1, userId);
	        deleteSocialLinksStmt.executeUpdate();

	        // Then, delete from the users table
	        PreparedStatement deleteUserStmt = connection.prepareStatement("DELETE FROM users WHERE user_id = ?;");
	        deleteUserStmt.setInt(1, userId);
	        int rowsAffected = deleteUserStmt.executeUpdate();
	        
	        // Check if the user was deleted
	        if (rowsAffected > 0) {
	            isDeleted = true;
	        }

	        // Commit transaction
	        connection.commit();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            if (connection != null) {
	                connection.rollback(); // Rollback in case of error
	            }
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	    } finally {
	        close(); // Ensure resources are closed
	    }
	    return isDeleted;
	}

	public List<Integer> getUserGrowthData() {
	    List<Integer> userGrowthData = new ArrayList<>();
	    String query = "SELECT COUNT(user_id) as user_count FROM users GROUP BY MONTH(created_at)";
	    
	    try {
	        connection = dataSource.getConnection();
	        pStmt = connection.prepareStatement(query);
	        rs = pStmt.executeQuery();
	        
	        while (rs.next()) {
	            userGrowthData.add(rs.getInt("user_count"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	    
	    return userGrowthData;
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
	                rs.getBoolean("private"),
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
	    boolean ok = false;
	    System.out.println("private profile:"+user.isPrivateProfile());
	    try {
	        connection = dataSource.getConnection();
	        
	        pStmt = connection.prepareStatement("UPDATE users SET username = ?, private = ? , about = ?, dob = ? WHERE user_id = ?;");
	        pStmt.setString(1, user.getusername());
	        pStmt.setBoolean(2, user.isPrivateProfile());
	        pStmt.setString(3, user.getAbout());
	        pStmt.setString(4, user.getDob());
	        pStmt.setInt(5, user.getUser_id());
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
	                rs.getBoolean("private"),
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

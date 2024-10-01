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
	
	public List<Tag> searchTags(String key) {
		System.out.println("work");
	    List<Tag> tags = new ArrayList<Tag>();
	    try {
	        connection = dataSource.getConnection();
	        String sql = "SELECT * FROM tags WHERE tag_name LIKE ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        
	        // Set the key for the fuzzy search with wildcards
	        String fuzzyKey = "%" + key + "%";
	        preparedStatement.setString(1, fuzzyKey);
	        
	        // Execute the query
	        rs = preparedStatement.executeQuery();
	        
	        // Iterate over the result set and add each tag to the list
	        while (rs.next()) {
	        	tags.add(new Tag(
						rs.getInt("tag_id"),
						rs.getString("tag_name"),
						rs.getString("description"),
						rs.getTimestamp("created_at").toLocalDateTime()
						));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(); // Make sure to close the resources
	    }
	    return tags;
	}
	

	public List<Tag> getTagsWithPagination(int page, int pageSize){
		List<Tag> tags = new ArrayList<Tag>();
	     int offset = (page - 1) * pageSize;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			pStmt = connection.prepareStatement( "SELECT * FROM tags LIMIT ? OFFSET ?");
            pStmt.setInt(1, pageSize);
            pStmt.setInt(2, offset);
            rs =pStmt.executeQuery();
			while(rs.next()) {
				tags.add(new Tag(
						rs.getInt("tag_id"),
						rs.getString("tag_name"),
						rs.getString("description"),
						rs.getTimestamp("created_at").toLocalDateTime()
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
	
	
	 public int getTotalTagCount() {
	        int count = 0;
	        try (Connection conn = dataSource.getConnection();
	             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) AS total FROM tags");
	             ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                count = rs.getInt("total");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return count;
	    }
	
	public List<Tag> getAllTags(){
		List<Tag> tags = new ArrayList<Tag>();
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery("select * from tags;");
			while(rs.next()) {
				tags.add(new Tag(
						rs.getInt("tag_id"),
						rs.getString("tag_name"),
						rs.getString("description"),
						rs.getTimestamp("created_at").toLocalDateTime()
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
	
	public boolean deleteTag(int tagId) {
	    boolean isDeleted = false;
	    try {
	        connection = dataSource.getConnection();

	        // Prepare the SQL query to delete the tag based on tag_id
	        String deleteQuery = "DELETE FROM tags WHERE tag_id = ?";

	        pStmt = connection.prepareStatement(deleteQuery);
	        pStmt.setInt(1, tagId);

	        // Execute the delete query
	        int rowsAffected = pStmt.executeUpdate();

	        // Check if any rows were affected (i.e., the tag was deleted)
	        if (rowsAffected > 0) {
	            isDeleted = true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	    return isDeleted;
	}

	public boolean createTag(String tagName, String description) {
	    boolean isCreated = false;
	    try {
	        connection = dataSource.getConnection();

	        // SQL query to insert a new tag
	        String insertQuery = "INSERT INTO tags (tag_name, description) VALUES (?, ?)";

	        pStmt = connection.prepareStatement(insertQuery);
	        pStmt.setString(1, tagName); // Set tag name
	        pStmt.setString(2, description); // Set tag description

	        // Execute the insert query
	        int rowsAffected = pStmt.executeUpdate();

	        // Check if the insertion was successful
	        if (rowsAffected > 0) {
	            isCreated = true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	    return isCreated;
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
	
	public Tag getTag(int tagId) {
	    Tag tag = null;
	    try {
	        // Establish connection to the database
	        connection = dataSource.getConnection();

	        // Prepare the SQL query to fetch the tag by tag_id
	        String query = "SELECT * FROM tags WHERE tag_id = ?";
	        pStmt = connection.prepareStatement(query);
	        pStmt.setInt(1, tagId);

	        // Execute the query
	        rs = pStmt.executeQuery();

	        // If the tag is found, create a Tag object
	        if (rs.next()) {
	            tag = new Tag(
	                rs.getInt("tag_id"),
	                rs.getString("tag_name"),
	                rs.getString("description"),
	                rs.getTimestamp("created_at").toLocalDateTime()
	            );
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Close the connection
	        close();
	    }

	    return tag; // Return the found tag or null if not found
	}

	public boolean updateTag(int tagId, String name, String description) {
	    boolean isUpdated = false;
	    try {
	        // Establish connection to the database
	        connection = dataSource.getConnection();

	        // Prepare the SQL query to update the tag
	        String query = "UPDATE tags SET tag_name = ?, description = ? WHERE tag_id = ?";
	        pStmt = connection.prepareStatement(query);
	        pStmt.setString(1, name);
	        pStmt.setString(2, description);
	        pStmt.setInt(3, tagId);

	        // Execute the update
	        int rowsAffected = pStmt.executeUpdate();

	        // Check if the update was successful
	        if (rowsAffected > 0) {
	            isUpdated = true; // The update was successful
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Close the connection
	        close();
	    }

	    return isUpdated; // Return true if updated successfully, false otherwise
	}

	
}

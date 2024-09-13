package com.devask.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class VoteDAO {
		private Connection connection;
		private Statement statement;
		private PreparedStatement pStmt;
		private ResultSet rs;
		private final DataSource dataSource;
		
		public VoteDAO(DataSource dataSource) {
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
		
		public boolean questionVote(Vote vote) { 
		    boolean created = false;
		    try {
		        connection = dataSource.getConnection();
		        pStmt = connection.prepareStatement("INSERT INTO votes (user_id, question_id, vote_type) VALUES (?, ?, ?);");
		        pStmt.setInt(1, vote.getUserId());
		        pStmt.setInt(2, vote.getQuestionId());
		        pStmt.setString(3, vote.getVoteType());  // Assuming vote.getVoteType() returns 'upvote' or 'downvote'
		        
		        int rowEffected = pStmt.executeUpdate();
		        if (rowEffected > 0) {
		            created = true;
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        close();
		    }
		    return created;
		}

		public List<Vote> getQuestionVotes(int questionId) {
		    List<Vote> votes = new ArrayList<Vote>();
		    try {
		        connection = dataSource.getConnection();
		        pStmt = connection.prepareStatement("SELECT user_id, question_id, vote_type FROM votes WHERE question_id = ?;");
		        pStmt.setInt(1, questionId);
		        rs = pStmt.executeQuery();
		        
		        while (rs.next()) {
		            votes.add(new Vote(rs.getInt("user_id"),
		            		rs.getInt("question_id"),
		            		rs.getString("vote_type")));
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        close();
		    }
		    return votes;
		}

		public Vote existingVote(int userId, int questionId,String voteType) {
			Vote vote = null;
			try {
				connection = dataSource.getConnection();
				pStmt = connection.prepareStatement("select * from votes where user_id = ? AND question_id = ? AND vote_type = ?;");
				pStmt.setInt(1, userId);
				pStmt.setInt(2, questionId);
				pStmt.setString(3, voteType);
				rs = pStmt.executeQuery();
				while(rs.next()) {
					vote = new Vote(rs.getInt("vote_id"),rs.getInt("user_id"),
		            		rs.getInt("question_id"),
		            		rs.getString("vote_type"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return vote;
		}
		
		public boolean deleteVote(int voteId) {
			try {
				connection = dataSource.getConnection();
				pStmt = connection.prepareStatement("DELETE FROM votes WHERE vote_id = ?;");
				pStmt.setInt(1, voteId);
				int rowEffected = pStmt.executeUpdate();
				if(rowEffected > 0) {
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				close();
			}
			return false;
		}

		public boolean answerVote(Vote vote) {
		    boolean created = false;
		    try {
		        connection = dataSource.getConnection();
		        pStmt = connection.prepareStatement("INSERT INTO votes (user_id, answer_id, vote_type) VALUES (?, ?, ?);");
		        pStmt.setInt(1, vote.getUserId());
		        pStmt.setInt(2, vote.getAnswerId());
		        pStmt.setString(3, vote.getVoteType());  // Assuming vote.getVoteType() returns 'upvote' or 'downvote'
		        
		        int rowEffected = pStmt.executeUpdate();
		        if (rowEffected > 0) {
		            created = true;
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        close();
		    }
		    return created;
		}

		
}

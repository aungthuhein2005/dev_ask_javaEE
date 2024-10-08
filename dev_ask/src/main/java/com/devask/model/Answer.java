package com.devask.model;

import java.time.LocalDateTime;
import java.util.List;

public class Answer {
	private int answerId;
	private String body;
	private int userId;
	private int questionId;
	private int vote;
	private int commentCount;
	private List<Vote> votes;
	private LocalDateTime createdAt;
	
	public Answer(int answerId, String body, int userId, int questionId, int vote,int commentCount ,LocalDateTime createdAt) {
		super();
		this.answerId = answerId;
		this.body = body;
		this.userId = userId;
		this.questionId = questionId;
		this.vote = vote;
		this.commentCount = commentCount;
		this.createdAt = createdAt;
	}
	public Answer(String body, int userId, int questionId, int vote, LocalDateTime createdAt) {
		super();
		this.body = body;
		this.userId = userId;
		this.questionId = questionId;
		this.vote = vote;
		this.createdAt = createdAt;
	}
	
	public Answer(String body, int userId, int questionId) {
		super();
		this.body = body;
		this.userId = userId;
		this.questionId = questionId;
	}
	
	public Answer(int answerId, String body, int userId, int vote,int commentCount, LocalDateTime createdAt) {
		super();
		this.answerId = answerId;
		this.body = body;
		this.userId = userId;
		this.vote = vote;
		this.commentCount = commentCount;
		this.createdAt = createdAt;
	}
	public int getAnswerId() {
		return answerId;
	}
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	public String getBody() {
		return body;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public int getUserId() {
		return userId;
	}
	
	public List<Vote> getVotes() {
		return votes;
	}
	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getVote() {
		return vote;
	}
	public void setVote(int vote) {
		this.vote = vote;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
}

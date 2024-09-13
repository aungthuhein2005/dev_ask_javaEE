package com.devask.model;

import java.time.LocalDateTime;

public class Comment {
	private int commentId;
	private String body;
	private int userId;
	private String username;
	private int questionId;
	private int answerId;
	private LocalDateTime createdAt;
	

	public Comment(int commentId, String body, int userId, int questionId, int answerId, LocalDateTime createdAt) {
		super();
		this.commentId = commentId;
		this.body = body;
		this.userId = userId;
		this.questionId = questionId;
		this.answerId = answerId;
		this.createdAt = createdAt;
	}
	
	
	public Comment(String body, int userId, int questionId, int answerId, LocalDateTime createdAt) {
		super();
		this.body = body;
		this.userId = userId;
		this.questionId = questionId;
		this.answerId = answerId;
		this.createdAt = createdAt;
	}
	
	public Comment(String body, int userId, int answerId) {
		super();
		this.body = body;
		this.userId = userId;
		this.answerId = answerId;
	}
	
	public Comment(int commentId,String body, int userId,String username, int questionId, int answerId, LocalDateTime createdAt) {
		super();
		this.commentId = commentId;
		this.body = body;
		this.userId = userId;
		this.username = username;
		this.questionId = questionId;
		this.answerId = answerId;
		this.createdAt = createdAt;
	}
	

	public int getCommentId() {
		return commentId;
	}


	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}


	public int getUserId() {
		return userId;
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


	public int getAnswerId() {
		return answerId;
	}


	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}

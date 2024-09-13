package com.devask.model;

import java.time.LocalDateTime;

public class Vote {
	private int voteId;
	private int userId;
	private int answerId;
	private int questionId;
	private String voteType;
	private LocalDateTime createdAt;
	public Vote(int voteId, int userId, int answerId, int questionId, LocalDateTime createdAt) {
		super();
		this.voteId = voteId;
		this.userId = userId;
		this.answerId = answerId;
		this.questionId = questionId;
		this.createdAt = createdAt;
	}
	public Vote(int userId,int questionId,String voteType) {
		super();
		this.userId = userId;
		this.questionId = questionId;
		this.voteType = voteType;
	}
	public Vote(int voteId,int userId,int questionId,String voteType) {
		super();
		this.voteId = voteId;
		this.userId = userId;
		this.questionId = questionId;
		this.voteType = voteType;
	}
	public int getVoteId() {
		return voteId;
	}
	public void setVoteId(int voteId) {
		this.voteId = voteId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAnswerId() {
		return answerId;
	}
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public String getVoteType() {
		return voteType;
	}
	public void setVoteType(String voteType) {
		this.voteType = voteType;
	}
	
	
	
	
}

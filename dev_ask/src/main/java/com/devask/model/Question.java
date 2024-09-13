package com.devask.model;

import java.time.LocalDateTime;
import java.util.List;

public class Question {
	private int questionId;
	private String title;
	private String body;
	private int vote;
	private String description;
	private int userId;
	private LocalDateTime createdAt;
	private List<Tag> tags;
	private List<Answer> answers;
	private int answerCount;
	
	
	public Question(int questionId, String title,String description, String body, int vote, int userId, LocalDateTime createdAt,
			List<Tag> tags,int answerCount) {
		super();
		this.questionId = questionId;
		this.title = title;
		this.description = description;
		this.body = body;
		this.vote = vote;
		this.userId = userId;
		this.createdAt = createdAt;
		this.tags = tags;
		this.answerCount = answerCount;
	}
	
	public Question(int questionId, String title,String description, String body, int vote, int userId, LocalDateTime createdAt,
			List<Tag> tags,List<Answer> answers) {
		super();
		this.questionId = questionId;
		this.title = title;
		this.description = description;
		this.body = body;
		this.vote = vote;
		this.userId = userId;
		this.createdAt = createdAt;
		this.tags = tags;
		this.answers = answers;
	}
	
	public Question(int questionId, String title,String description, String body, int vote, int userId, LocalDateTime createdAt,
			List<Answer> answers) {
		super();
		this.questionId = questionId;
		this.title = title;
		this.description = description;
		this.body = body;
		this.vote = vote;
		this.userId = userId;
		this.createdAt = createdAt;
		this.answers = answers;
	}
	
	public Question( String title,String description, String body, int vote, int userId, LocalDateTime createdAt,
			List<Tag> tags, List<Answer> answers) {
		super();
		this.title = title;
		this.description = description;
		this.body = body;
		this.vote = vote;
		this.userId = userId;
		this.createdAt = createdAt;
		this.tags = tags;
		this.answers = answers;
	}
	
	public Question( String title,String description,String body, int userId,List<Tag> tags) {
		super();
		this.title = title;
		this.description = description;
		this.body = body;
		this.userId = userId;
		this.tags = tags;
	}
	
	public Question( String title,String description ,String body, int userId) {
		super();
		this.title = title;
		this.description = description;
		this.body = body;
		this.userId = userId;
	}
	
	public Question(int questionId, String title,LocalDateTime createdAt,
			List<Tag> tags,int answerCount) {
		super();
		this.questionId = questionId;
		this.title = title;
		this.createdAt = createdAt;
		this.tags = tags;
		this.answerCount = answerCount;
	}

	public Question() {
		// TODO Auto-generated constructor stub
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public int getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}
	
	

}

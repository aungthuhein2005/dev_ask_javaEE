package com.devask.model;

import java.time.LocalDateTime;

public class Tag {
	private int tagId;
	private String tag_name;
	private String description;
	private LocalDateTime createdAt;
	
	public Tag() {}
	
	public Tag(int tagId, String tag_name, String description, LocalDateTime createdAt) {
		super();
		this.tagId = tagId;
		this.tag_name = tag_name;
		this.description = description;
		this.createdAt = createdAt;
	}
	
	public Tag(int tagId) {
		super();
		this.tagId = tagId;
	}

	public Tag( String tag_name, String description, LocalDateTime createdAt) {
		super();
		this.tag_name = tag_name;
		this.description = description;
		this.createdAt = createdAt;
	}
	
	public Tag(int tagId, String tag_name) {
		super();
		this.tagId = tagId;
		this.tag_name = tag_name;
	}

	
	public Tag( String tag_name, String description) {
		super();
		this.tag_name = tag_name;
		this.description = description;
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
}

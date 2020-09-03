package com.redhat.bluesmile.sprintsapp.model;

import java.util.Date;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Document(collection = "BackLog")
public class BackLog {

	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";

	@Id
	private long id;

	private String title;

	private String description;

	private String project;

	private String client;

	private Features[] features;

	private String createdBy;

	private Date createdAt;

	private int status;

	private int operation;

	public BackLog() {
	}

	public BackLog(long id, String title, String description, String project, String client, Features[] features,
			String createdBy, Date createdAt, int status, int operation) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.project = project;
		this.client = client;
		this.features = features;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.status = status;
		this.operation = operation;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Features[] getFeatures() {
		return features;
	}

	public void setFeatures(Features[] fea) {
		this.features = fea;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

}

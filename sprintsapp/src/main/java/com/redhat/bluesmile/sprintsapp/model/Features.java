package com.redhat.bluesmile.sprintsapp.model;

import java.util.Date;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@Entity
public class Features {

	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";

	@Id
	private long id;

	private String name;

	private String description;

	private Date startDate;

	private Date endDate;

	private UserModel[] developer;

	private long idBacklog;

	private Issue[] issues;

	private String createdBy;

	private Date createdAt;

	private int status;

	public Features() {
	}

	public Features(long id, String name, String description, Date startDate, Date endDate, UserModel[] developer,
			long idBacklog, Issue[] issues, String createdBy, Date createdAt, int status) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.developer = developer;
		this.idBacklog = idBacklog;
		this.issues = issues;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public UserModel[] getDeveloper() {
		return developer;
	}

	public void setDeveloper(UserModel[] developer) {
		this.developer = developer;
	}

	public long getIdBacklog() {
		return idBacklog;
	}

	public void setIdBacklog(long idBacklog) {
		this.idBacklog = idBacklog;
	}

	public Issue[] getIssues() {
		return issues;
	}

	public void setIssues(Issue[] issues) {
		this.issues = issues;
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

}

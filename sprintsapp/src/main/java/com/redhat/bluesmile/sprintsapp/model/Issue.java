package com.redhat.bluesmile.sprintsapp.model;

import java.util.Date;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@Entity
public class Issue {

	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";

	@Id
	private long id;

	private String title;
	private String description;
	private String email;
	private String project;
	private Date createdAt;
	private int estado;

	public Issue() {
	}

	public Issue(long id, String title, String description, String email, String project, Date createdAt, int estado) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.email = email;
		this.project = project;
		this.createdAt = createdAt;
		this.estado = estado;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}

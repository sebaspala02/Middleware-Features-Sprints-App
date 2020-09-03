package com.redhat.bluesmile.sprintsapp.model;

import java.util.Date;
import javax.persistence.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
//import org.springframework.data.mongodb.core.mapping.Document;

@Entity
//@Document(collection = "UserModel")
public class UserModel {

	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";

	@Id
	private long id;

	private String name;

	private String lastname;

	private String email;

	private String password;

	private Date createdAt;

	public UserModel() {
	}

	public UserModel(String name) {
		this.name = name;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserModel [id=" + id + ", name=" + name + ", lastname=" + lastname + ", email=" + email + ", password="
				+ password + ", createdAt=" + createdAt + "]";
	}
}
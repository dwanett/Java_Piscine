package edu.school21.models;

import java.util.Objects;

public class User {
	private Long id;
	private String name;
	private String password;
	private boolean isAuthentication;

	public User(Long id, String name, String password, boolean isAuthentication) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.isAuthentication = isAuthentication;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAuthentication() {
		return isAuthentication;
	}

	public void setAuthentication(boolean authentication) {
		isAuthentication = authentication;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return isAuthentication == user.isAuthentication && id.equals(user.id) && name.equals(user.name) && password.equals(user.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, password, isAuthentication);
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", password='" + password + '\'' +
				", isAuthentication=" + isAuthentication +
				'}';
	}
}

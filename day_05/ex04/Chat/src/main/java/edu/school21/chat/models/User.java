package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
	private Long id;
	private String login;
	private String password;
	private List<Chatroom> createdChatrooms;
	private List<Chatroom> chatrooms;

	public User(Long id, String login, String password, List<Chatroom> createdChatrooms, List<Chatroom> chatrooms) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.createdChatrooms = createdChatrooms;
		this.chatrooms = chatrooms;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Chatroom> getCreatedChatrooms() {
		return createdChatrooms;
	}

	public void setCreatedChatrooms(List<Chatroom> createdChatrooms) {
		this.createdChatrooms = createdChatrooms;
	}

	public List<Chatroom> getChatrooms() {
		return chatrooms;
	}

	public void setChatrooms(List<Chatroom> chatrooms) {
		this.chatrooms = chatrooms;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id.equals(user.id) && login.equals(user.login) && password.equals(user.password)
				&& createdChatrooms.equals(user.createdChatrooms) && chatrooms.equals(user.chatrooms);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login, password, createdChatrooms, chatrooms);
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", login='" + login + '\'' +
				", password='" + password + '\'' +
				", createdChatrooms=" + createdChatrooms +
				", chatrooms=" + chatrooms +
				'}';
	}
}

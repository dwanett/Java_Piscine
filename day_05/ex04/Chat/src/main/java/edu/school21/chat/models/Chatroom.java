package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class Chatroom {
	private Long id;
	private String name;
	private User creator;
	private List<Message> messagesList;

	public Chatroom(Long id, String name, User creator, List<Message> messagesList) {
		this.id = id;
		this.name = name;
		this.creator = creator;
		this.messagesList = messagesList;
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

	public User getOwner() {
		return creator;
	}

	public void setOwner(User owner) {
		this.creator = owner;
	}

	public List<Message> getMessagesList() {
		return messagesList;
	}

	public void setMessagesList(List<Message> messagesList) {
		this.messagesList = messagesList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Chatroom chatroom = (Chatroom) o;
		return id.equals(chatroom.id) && name.equals(chatroom.name) && creator.equals(chatroom.creator)
				&& messagesList.equals(chatroom.messagesList);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, creator, messagesList);
	}

	@Override
	public String toString() {
		return "Chatroom{" +
				"id=" + id +
				", name='" + name + '\'' +
				", creator=" + creator +
				", messagesList=" + messagesList +
				'}';
	}
}

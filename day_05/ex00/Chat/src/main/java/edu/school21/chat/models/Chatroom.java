package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class Chatroom {
	private int id;
	private String name;
	private User owner;
	private List<Message> messagesList;

	public Chatroom(int id, String name, User owner, List<Message> messagesList) {
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.messagesList = messagesList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
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
		return id == chatroom.id && name.equals(chatroom.name)
				&& owner.equals(chatroom.owner)
				&& messagesList.equals(chatroom.messagesList);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, owner, messagesList);
	}

	@Override
	public String toString() {
		return "Chatroom{" +
				"id=" + id +
				", name='" + name + '\'' +
				", owner=" + owner +
				", messagesList=" + messagesList +
				'}';
	}
}

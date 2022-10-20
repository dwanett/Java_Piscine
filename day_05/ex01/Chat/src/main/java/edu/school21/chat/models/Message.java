package edu.school21.chat.models;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Message {
	private Long id;
	private User author;
	private Chatroom chatroom;
	private String text;
	private Timestamp timestamp;

	public Message(Long id, User author, Chatroom chatroom, String text, Timestamp timestamp) {
		this.id = id;
		this.author = author;
		this.chatroom = chatroom;
		this.text = text;
		this.timestamp = timestamp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Chatroom getChatroom() {
		return chatroom;
	}

	public void setChatroom(Chatroom chatroom) {
		this.chatroom = chatroom;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Message message = (Message) o;
		return id.equals(message.id) && author.equals(message.author) && chatroom.equals(message.chatroom)
				&& text.equals(message.text) && timestamp.equals(message.timestamp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, author, chatroom, text, timestamp);
	}

	@Override
	public String toString() {
		return "Message{" +
				"\nid=" + id +
				", \nauthor=" + author +
				", \nchatroom=" + chatroom +
				", \ntext='" + text + '\'' +
				", \ntimestamp=" + timestamp.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")) +
				"\n}";
	}
}

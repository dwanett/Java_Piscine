package edu.school21.sockets.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Message {
	private Long id;

	private String text;

	private Timestamp time;

	private User author;

	public Message(Long id, String text, Timestamp time, User author) {
		this.id = id;
		this.text = text;
		this.time = time;
		this.author = author;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Message message = (Message) o;
		return id.equals(message.id) && text.equals(message.text) && time.equals(message.time) && author.equals(message.author);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, text, time, author);
	}

	@Override
	public String toString() {
		return "Message{" +
				"id=" + id +
				", text='" + text + '\'' +
				", time=" + time +
				", author=" + author +
				'}';
	}
}

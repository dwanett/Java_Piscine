package edu.school21.chat.exeption;

public class NotSavedSubEntityException extends RuntimeException{
	public NotSavedSubEntityException() {
		super("Error adding row to the database!");
	}
}

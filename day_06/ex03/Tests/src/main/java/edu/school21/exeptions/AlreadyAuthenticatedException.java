package edu.school21.exeptions;

public class AlreadyAuthenticatedException extends RuntimeException{
	public AlreadyAuthenticatedException() {
		super("Already authenticated!");
	}
}

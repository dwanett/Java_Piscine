package edu.school21.sockets.client;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

@Component("client")
public class Client {
	private static Socket serverSocket;
	private static Scanner reader;
	private static Scanner in;
	private static PrintWriter out;

	public void run(int port) {
		try {
			serverSocket = new Socket("localhost", port);
			reader = new Scanner(System.in);
			in = new Scanner(serverSocket.getInputStream());
			out = new PrintWriter(serverSocket.getOutputStream(), true);
			while (true) {
				receiveMessage();
				sendMessage();
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public void receiveMessage() throws IOException {
		if (in.hasNextLine()) {
			String message = in.nextLine();
			System.out.println(message);

			if ("Successful!".equalsIgnoreCase(message)) {
				try {
					reader.close();
					in.close();
					out.close();
					serverSocket.close();
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
				System.exit(0);
			}
		}
	}

	public void sendMessage() {
		System.out.print("> ");
		if (reader.hasNextLine()) {
			String message = reader.nextLine();
			out.println(message);
		}
	}

	public void start() throws IOException {
		while (true) {
			receiveMessage();
			sendMessage();
		}
	}
}

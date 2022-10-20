package edu.school21.sockets.server;


import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

@Component("server")
public class Server {
	private ServerSocket server;
	private UsersService usersService;

	public static LinkedList<Client> clientsList = new LinkedList<>();

	@Autowired
	public Server(UsersService usersService) {
		this.usersService = usersService;
	}

	public void run(int port) {
			try {
				server = new ServerSocket(port);
				while (true) {
					Socket socket = server.accept();
					clientsList.add(new Client(socket));
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
		}
	}

	private class Client extends Thread {

		private Socket clientSocket;
		private BufferedReader in;
		private PrintWriter out;

		private String username;

		private boolean logIn = false;

		public Client(Socket clientSocket) throws IOException {
			this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.out = new PrintWriter(clientSocket.getOutputStream(), true);
			this.clientSocket = clientSocket;
			start();
		}

		@Override
		public void run() {
			try {
				this.out.println("Hello from Server!");
				String getMessage;
				boolean res = false;
				while (!res) {
					getMessage = this.in.readLine();
					res = choiceMethod(getMessage);
					if ((getMessage.equals("signIn") || getMessage.equals("signUp")) && !res) {
						return;
					}
				}
				this.logIn = true;
				res = false;
				this.out.println("Start messaging");
				while (!res) {
					getMessage = this.in.readLine();
					if (getMessage != null) {
						if (getMessage.equals("Exit")) {
							sendingMessageAll("left the chat");
							this.out.println("You have left the chat.");
							break;
						}
						sendingMessageAll(getMessage);
					}
				}
				mystop();
			} catch (IOException e) {
				System.err.println(e.getMessage());
				mystop();
			}
		}

		public synchronized void sendingMessageAll(String message) {
			if (!message.equals("left the chat")) {
				usersService.createMessage(message, this.username);
			}
			for (Client client : clientsList) {
				if (client.logIn) {
					client.send(this.username + ": " + message);
				}
			}
		}

		private void send(String msg) {
			this.out.println(msg);
		}

		public boolean choiceMethod(String name) throws IOException {
			switch (name) {
				case "signUp" : return signUp();
				case "signIn" : return signIn();
				default:
					out.println("Function not found!");
			}
			return false;
		}

		private boolean signUp() throws IOException {
			boolean rsult;

			this.out.println("Enter username:");
			this.username = this.in.readLine();
			this.out.println("Enter password:");
			String password = in.readLine();
			rsult = usersService.signUp(this.username, password);
			if (!rsult) {
				this.out.println("Error signUp!");
				mystop();
			} else {
				this.out.println("Successful!");
			}
			return rsult;
		}

		private boolean signIn() throws IOException {
			boolean rsult;

			out.println("Enter username:");
			username = in.readLine();
			out.println("Enter password:");
			String password = in.readLine();
			rsult = usersService.signIn(username, password);
			if (!rsult) {
				out.println("User not found or incorrect password!");
				mystop();
			}
			return rsult;
		}

		public void mystop() {
			try {
				this.in.close();
				this.out.close();
				if (!this.clientSocket.isClosed()) {
					this.clientSocket.close();
				}
				clientsList.remove(this);
				if (clientsList.size() == 0) {
					server.close();
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}
}

package edu.school21.sockets.server;


import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Component("server")
public class Server {
	private Socket clientSocket;
	private ServerSocket server;
	private BufferedReader in;
	private PrintWriter out;
	private UsersService usersService;

	@Autowired
	public Server(UsersService usersService) {
		this.usersService = usersService;
	}

	public void run(int port) {
		try {
			server = new ServerSocket(port);
			clientSocket = server.accept();
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			out.println("Hello from Server!");
			String getMessage;
			boolean res = false;
			while (!res) {
				getMessage = in.readLine();
				res = choiceMethod(getMessage);
			}
			out.println("Successful!");
			stop();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			stop();
		}
	}


	public boolean choiceMethod(String name) throws IOException {
		switch (name) {
			case "signUp" : return signUp();
			default:
				out.println("Function not found!");
		}
		return false;
	}

	private boolean signUp() throws IOException {
		boolean rsult;

		out.println("Enter username:");
		String usernmae = in.readLine();
		out.println("Enter password:");
		String password = in.readLine();
		rsult = usersService.signUp(usernmae, password);
		if (!rsult) {
			out.println("Error signUp!");
		}
		return rsult;
	}

	public void stop() {
		try {
			server.close();
			in.close();
			out.close();
			clientSocket.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		System.exit(0);
	}
}

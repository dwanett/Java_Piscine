package edu.school21.sockets.client;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

@Component("client")
public class Client {
	private static Socket serverSocket;
	private static BufferedReader reader;
	private static Scanner in;
	private static PrintWriter out;

	public void run(int port) {
		try {
			serverSocket = new Socket("localhost", port);

			in = new Scanner(serverSocket.getInputStream());
			out = new PrintWriter(serverSocket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(System.in));
			SendMessage sendMessage = new SendMessage(reader, in, out);
			ReceiveMessage receiveMessage = new ReceiveMessage(reader, in, out, sendMessage);
			receiveMessage.start();
			sendMessage.start();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}


	class ReceiveMessage extends Thread {

		private BufferedReader reader;
		private Scanner in;
		private PrintWriter out;
		
		private SendMessage sendMessage;

		public ReceiveMessage(BufferedReader reader, Scanner in, PrintWriter out, SendMessage sendMessage) {
			this.in = in;
			this.reader = reader;
			this.out = out;
			this.sendMessage = sendMessage;
		}

		@Override
		public void run() {
			while (true) {
				String message = in.nextLine();
				System.out.println(message);
				if ("You have left the chat.".equals(message)
						|| "User not found or incorrect password!".equals(message)
						|| "Error signUp!".equals(message)) {
					try {
						sendMessage.setRun(false);
						reader.close();
						in.close();
						out.close();
						serverSocket.close();
						break;
					} catch (IOException e) {
						System.err.println(e.getMessage());
					}
				}
			}
		}
	}

	class SendMessage extends Thread {
		private BufferedReader reader;
		private Scanner in;
		private PrintWriter out;
		
		private boolean isRun = true;

		public SendMessage(BufferedReader reader, Scanner in, PrintWriter out) {
			this.in = in;
			this.reader = reader;
			this.out = out;
		}

		public void setRun(boolean run) {
			isRun = run;
		}

		@Override
		public void run() {
			while (isRun) {
				try {
					if (reader.ready()) {
						String message = reader.readLine();
						out.println(message);
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}

			}
		}
	}
}

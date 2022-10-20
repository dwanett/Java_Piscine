package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import edu.school21.chat.repositories.UserRepositoryJdbcImpl;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Program {
	private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
	private static final String USER = "postgres";
	private static final String PASS = "3954";
	private static final String DB_SCHEMA = "/resources/schema.sql";
	private static final String DB_DATA = "/resources/data.sql";

	private static final HikariConfig config =  new HikariConfig();

	private static HikariDataSource ds;

	public static void main(String[] args) {
		try {
			Connection connection;

			setConfigHC();
			connection = connect();
			runSqlFile(DB_SCHEMA, connection);
			runSqlFile(DB_DATA, connection);

			MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(connection);
			Optional<Message> messageOptional = messagesRepository.findById(10L);
			if (messageOptional.isPresent()) {
				Message message = messageOptional.get();
				message.setText("Bye");
				message.setTimestamp(null);
				messagesRepository.update(message);
			}

			connection.close();
		} catch (SQLException | FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
	}

	public static void runSqlFile(String path, Connection connection) throws SQLException, FileNotFoundException{
		File file = new File(System.getProperty("user.dir") + "/src/main" + path);
		Scanner scanner = new Scanner(file).useDelimiter(";");

		while (scanner.hasNext()) {
			String line = scanner.next();
			connection.createStatement().execute(line);
		}
	}

	public static void setConfigHC() {
		config.setJdbcUrl(DB_URL);
		config.setUsername(USER);
		config.setPassword(PASS);
		ds = new HikariDataSource( config );
	}
	public static Connection connect() throws SQLException{
		return ds.getConnection();
	}
}

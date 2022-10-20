package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

			User creator = new User(3L, "user", "user", new ArrayList(), new ArrayList());
			User author = creator;
			Chatroom room = new Chatroom(4L, "room", creator, new ArrayList());
			Message message = new Message(null, author, room, "Hello!", Timestamp.valueOf(LocalDateTime.now()));
			MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(connection);
			messagesRepository.save(message);
			System.out.println(message.getId()); // ex. id == 11

			connection.close();
		} catch (SQLException | FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
	}

	public static void runSqlFile(String path, Connection connection)
						throws SQLException, FileNotFoundException{
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

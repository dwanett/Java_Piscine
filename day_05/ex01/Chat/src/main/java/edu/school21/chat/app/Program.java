package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.io.*;
import java.sql.*;
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
			MessagesRepositoryJdbcImpl messagesRepository;
			Connection connection;

			setConfigHC();
			connection = connect();
			messagesRepository = new MessagesRepositoryJdbcImpl(connection);
			runSqlFile(DB_SCHEMA, connection);
			runSqlFile(DB_DATA, connection);

			Scanner scanner = new Scanner(System.in);

			System.out.print("Enter a message ID:\n-> ");
			Long id = scanner.nextLong();
			System.out.println(messagesRepository.findById(id).orElse(null));

			connection.close();
		} catch (SQLException | FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
	}

	public static void runSqlFile(String path,
			Connection connection) throws SQLException, FileNotFoundException{
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

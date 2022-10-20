package edu.school21.chat;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Main {
	private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
	private static final String USER = "postgres";
	private static final String PASS = "3954";
	private static final String DB_SCHEMA = "/resources/schema.sql";
	private static final String DB_DATA = "/resources/data.sql";


	public static void main(String[] args) {
		try {
			Connection connection = connect();
			runSqlFile(DB_SCHEMA, connection);
			runSqlFile(DB_DATA, connection);
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

	public static Connection connect() throws SQLException{
		Connection conn = null;

		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		System.out.println("Connected to the PostgreSQL server successfully.");
		return conn;
	}
}

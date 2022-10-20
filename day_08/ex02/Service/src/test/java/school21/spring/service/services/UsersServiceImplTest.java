package school21.spring.service.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.TestApplicationConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class UsersServiceImplTest {
	private static DataSource dataSource;
	private static UsersService usersServiceJdbc;
	private static UsersService usersServiceJdbcTemplate;

	@BeforeAll
	static void before() {
		ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
		dataSource = context.getBean("dataSource", DataSource.class);
		usersServiceJdbc = context.getBean("usersServiceJdbc", UsersService.class);
		usersServiceJdbcTemplate = context.getBean("usersServiceJdbcTemplate", UsersService.class);
	}

	@BeforeEach
	public void init() throws SQLException {
		Connection connection = dataSource.getConnection();
		connection.prepareStatement("DROP SCHEMA IF EXISTS service CASCADE").execute();
		connection.prepareStatement("CREATE SCHEMA IF NOT EXISTS service").execute();
		connection.prepareStatement("DROP TABLE IF EXISTS user CASCADE").execute();
		connection.prepareStatement("CREATE TABLE IF NOT EXISTS service.user (id INTEGER IDENTITY PRIMARY KEY, email VARCHAR(100) NOT NULL, password VARCHAR(100) NOT NULL)").execute();
	}


	@ParameterizedTest
	@ValueSource(strings = {"user1@yandex.ru", "user2@yandex.ru"})
	public void testUsersServiceJdbc(String email) {
		Assertions.assertNotNull(usersServiceJdbc.signUp(email));
	}

	@ParameterizedTest
	@ValueSource(strings = {"user1@yandex.ru", "user2@yandex.ru"})
	public void testUsersServiceJdbcTemplate(String email) {
		Assertions.assertNotNull(usersServiceJdbcTemplate.signUp(email));
	}
}

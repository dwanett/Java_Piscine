package edu.school21.sockets.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
public class SocketsApplicationConfig {

	@Value("${db.url}")
	String dbUrl;

	@Value("${db.user}")
	String dbUser;

	@Value("${db.password}")
	String dbPassword;

	@Value("${db.driver.name}")
	String dbDriverName;

	@Bean("dataSourceHikari")
	public DataSource createDataSourceHikari() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(dbDriverName);
		dataSource.setJdbcUrl(dbUrl);
		dataSource.setUsername(dbUser);
		dataSource.setPassword(dbPassword);
		return dataSource;
	}

	@Bean("encoder")
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}

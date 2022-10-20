package school21.spring.service.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
public class ApplicationConfig {

	@Value("${db.url}")
	String dbUrl;

	@Value("${db.user}")
	String dbUser;

	@Value("${db.password}")
	String dbPassword;

	@Value("${db.driver.name}")
	String dbDriverName;

	/*
	@Bean("usersRepositoryJdbcTemplate")
	public UsersRepository createUsersRepositoryJdbcTemplate (DataSource dataSourceHikari) {
		return new UsersRepositoryJdbcTemplateImpl(dataSourceHikari);
	}

	@Bean("usersRepositoryJdbc")
	public UsersRepository createUsersRepositoryJdbc (DataSource dataSourceBase) {
		return new UsersRepositoryJdbcImpl(dataSourceBase);
	}*/

	@Bean("dataSourceBase")
	public DataSource crateDataSourceBase() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(dbDriverName);
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(dbUser);
		dataSource.setPassword(dbPassword);
		return dataSource;
	}

	@Bean("dataSourceHikari")
	public DataSource createDataSourceHikari() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(dbDriverName);
		dataSource.setJdbcUrl(dbUrl);
		dataSource.setUsername(dbUser);
		dataSource.setPassword(dbPassword);
		return dataSource;
	}

}

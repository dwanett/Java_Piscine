package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component("usersRepositoryJdbcTemplate")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository{
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public UsersRepositoryJdbcTemplateImpl(@Qualifier("dataSourceHikari") DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private static final class UserMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return (new User(resultSet.getLong("id"),
					resultSet.getString("email"), resultSet.getString("password")));
		}
	}

	@Override
	public User findById(Long id) {
		User user;
		try {
			user = jdbcTemplate.queryForObject(FIND_ID_QUERY, new UserMapper(), id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return user;
	}

	@Override
	public List<User> findAll() {
		List<User> usersList;
		try {
			usersList = jdbcTemplate.query(FIND_ALL_QUERY, new UserMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return usersList;
	}

	@Override
	public void save(User entity) {
		if (findByEmail(entity.getEmail()).isPresent()) {
			return;
		}
		jdbcTemplate.update(SAVE_QUERY, entity.getEmail(), entity.getPassword());
		entity.setId(findByEmail(entity.getEmail()).get().getId());
	}

	@Override
	public void update(User entity) {
		jdbcTemplate.update(UPDATE_QUERY, entity.getEmail(), entity.getId());
	}

	@Override
	public void delete(Long id) {
		jdbcTemplate.update(DELETE_QUERY, id);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		User user;
		try {
			user = jdbcTemplate.queryForObject(FIND_EMAIL_QUERY, new UserMapper(), email);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
		return Optional.of(user);
	}
}

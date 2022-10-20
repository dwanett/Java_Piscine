package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component("messagesRepositoryJdbcImpl")
public class MessagesRepositoryJdbcImpl implements MessagesRepository {
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	private static UsersRepository usersRepository;

	@Autowired
	public MessagesRepositoryJdbcImpl(DataSource dataSource, UsersRepository usersRepository) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		MessagesRepositoryJdbcImpl.usersRepository = usersRepository;
	}

	private static final class UserMapper implements RowMapper<Message> {
		@Override
		public Message mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return (new Message(resultSet.getLong("id"),
					resultSet.getString("text"), resultSet.getTimestamp("time"),
					usersRepository.findById(resultSet.getLong("author"))));
		}
	}

	@Override
	public Message findById(Long id) {
		Message message;
		try {
			message = jdbcTemplate.queryForObject(FIND_ID_QUERY, new UserMapper(), id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return message;
	}

	@Override
	public List<Message> findAll() {
		List<Message> messagesList;
		try {
			messagesList = jdbcTemplate.query(FIND_ALL_QUERY, new UserMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return messagesList;
	}

	@Override
	public void save(Message entity) {
		jdbcTemplate.update(SAVE_QUERY, entity.getText(), entity.getTime(), entity.getAuthor().getId());
		entity.setId(findByTime(entity.getTime()).get().getId());
	}

	@Override
	public void update(Message entity) {
		jdbcTemplate.update(UPDATE_QUERY, entity.getText(), entity.getTime(), entity.getAuthor().getId(), entity.getId());
	}

	@Override
	public void delete(Long id) {
		jdbcTemplate.update(DELETE_QUERY, id);
	}

	@Override
	public Optional<Message> findByTime(Timestamp time) {
		Message message;
		try {
			message = jdbcTemplate.queryForObject(FIND_MESSAGE_TIME_QUERY, new UserMapper(), time);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
		return Optional.of(message);
	}
}

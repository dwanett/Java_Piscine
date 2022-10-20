package edu.school21.chat.repositories;

import edu.school21.chat.exeption.NotSavedSubEntityException;
import edu.school21.chat.models.Message;

import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository{

	private final static String SELECT_QUERY_TEMPLATE = "SELECT * FROM chat.message WHERE id=?";

	private final static String INSERT_QUERY_TEMPLATE = "INSERT INTO chat.Message(text, time, author, room) VALUES (?, ?, ?, ?) RETURNING id";

	private final static String UPDATE_QUERY_TEMPLATE = "UPDATE chat.Message SET text=?, time=?, author=?, room=? WHERE id=?";
	private UserRepositoryJdbcImpl userRepository;

	private ChatroomRepositoryJdbcImpl chatroomRepository;
	private Connection dataSource;

	public MessagesRepositoryJdbcImpl(Connection dataSource) {
		this.dataSource = dataSource;
		this.chatroomRepository = new ChatroomRepositoryJdbcImpl(dataSource);
		this.userRepository = new UserRepositoryJdbcImpl(dataSource);
	}

	@Override
	public void update(Message message) throws SQLException {
		if (userRepository.findById(message.getAuthor().getId()).orElse(null) == null) {
			throw new NotSavedSubEntityException();
		}
		PreparedStatement pst = dataSource.prepareStatement(UPDATE_QUERY_TEMPLATE);
		pst.setString(1, message.getText());
		pst.setTimestamp(2, message.getTimestamp());
		pst.setLong(3, message.getAuthor().getId());
		pst.setLong(4, message.getChatroom().getId());
		pst.setLong(5, message.getId());
		pst.execute();
	}

	@Override
	public void save(Message message) throws SQLException{
		if (message.getAuthor() == null || message.getChatroom() == null
				|| message.getAuthor().getId() == null
				|| message.getChatroom().getId() == null
				|| userRepository.findById(message.getAuthor().getId()).orElse(null) == null
				|| chatroomRepository.findById(message.getChatroom().getId()).orElse(null) == null) {
			throw new NotSavedSubEntityException();
		} else {
			PreparedStatement pst = dataSource.prepareStatement(INSERT_QUERY_TEMPLATE);
			pst.setString(1, message.getText());
			pst.setTimestamp(2, message.getTimestamp());
			pst.setLong(3, message.getAuthor().getId());
			pst.setLong(4, message.getChatroom().getId());
			ResultSet rs = pst.executeQuery();
			rs.next();
			message.setId(rs.getLong("id"));
		}
	}

	@Override
	public Optional<Message> findById(Long id) throws SQLException {
		Message ret = null;
		PreparedStatement pst = dataSource.prepareStatement(SELECT_QUERY_TEMPLATE);
		pst.setLong(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			ret = new Message(rs.getLong("id")
					, userRepository.findById(rs.getLong("author")).orElse(null)
					, chatroomRepository.findById(rs.getLong("room")).orElse(null)
					, rs.getString("text")
					, rs.getTimestamp("time"));
		}
		return Optional.ofNullable(ret);
	}
}

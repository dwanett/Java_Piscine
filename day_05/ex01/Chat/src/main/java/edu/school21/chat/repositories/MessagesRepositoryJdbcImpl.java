package edu.school21.chat.repositories;

import edu.school21.chat.models.Message;

import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository{

	private static String QUERY_TEMPLATE = "SELECT * FROM chat.message WHERE id=?";

	private UserRepositoryJdbcImpl userRepository;

	private ChatroomRepositoryJdbcImpl chatroomRepository;
	private Connection dataSource;

	public MessagesRepositoryJdbcImpl(Connection dataSource) {
		this.dataSource = dataSource;
		this.chatroomRepository = new ChatroomRepositoryJdbcImpl(dataSource);
		this.userRepository = new UserRepositoryJdbcImpl(dataSource);
	}

	@Override
	public Optional<Message> findById(Long id) throws SQLException {
		Message ret = null;
		PreparedStatement pst = dataSource.prepareStatement(QUERY_TEMPLATE);
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

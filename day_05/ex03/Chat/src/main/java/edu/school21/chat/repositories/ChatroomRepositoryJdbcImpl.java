package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ChatroomRepositoryJdbcImpl implements ChatroomRepository {
	private static String QUERY_TEMPLATE = "SELECT * FROM chat.chatroom WHERE id=?";

	private UserRepositoryJdbcImpl userRepository;
	private Connection dataSource;

	public ChatroomRepositoryJdbcImpl(Connection dataSource) {
		this.dataSource = dataSource;
		this.userRepository = new UserRepositoryJdbcImpl(dataSource);
	}

	@Override
	public Optional<Chatroom> findById(Long id) throws SQLException {
		Chatroom ret = null;
		PreparedStatement pst = dataSource.prepareStatement(QUERY_TEMPLATE);
		pst.setLong(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			ret = new Chatroom(rs.getLong("id")
					, rs.getString("name")
					, null
					, null);
		}
		return Optional.ofNullable(ret);
	}
}

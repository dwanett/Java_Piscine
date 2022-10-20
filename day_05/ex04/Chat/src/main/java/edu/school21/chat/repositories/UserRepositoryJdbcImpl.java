package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJdbcImpl implements UserRepository {

	private final static String SELECT_QUERY_TEMPLATE = "SELECT * FROM chat.user WHERE id=?";

	private static String SELECT_ALL_QUERY_TEMPLATE = "WITH cte_table as (\n" +
			"    SELECT chat.user.id AS user_id, login, password , chat.chatroom.id AS chatroom_id, name, owner,\n" +
			"           CASE WHEN chat.user.id = chat.chatroom.owner THEN true ELSE false END AS isOwner\n" +
			"    FROM chat.user CROSS JOIN chat.chatroom ORDER BY chat.user.id\n" +
			")\n" +
			"\n" +
			"\n" +
			"SELECT user_id, login, password, chatroom_id, chat.message.room, cte_table.isOwner, name, owner\n" +
			"FROM cte_table FULL JOIN chat.message\n" +
			"    ON (cte_table.chatroom_id = chat.message.room AND cte_table.user_id = chat.message.author) ORDER BY cte_table.user_id;";
	private Connection dataSource;

	public UserRepositoryJdbcImpl(Connection dataSource) {
		this.dataSource = dataSource;
	}


	@Override
	public List<User> findAll(int page, int size) throws SQLException {
		List<User> userList = new ArrayList<>();
		List<Chatroom> cretedChatRooms = null;
		List<Chatroom> chatRooms = null;
		User user = null;
		PreparedStatement pst = dataSource.prepareStatement(SELECT_ALL_QUERY_TEMPLATE);
		ResultSet rs = pst.executeQuery();
		int start = (page * size);
		int i = 0;
		long idCurUser = -1;

		while (rs.next()) {
			if (idCurUser != rs.getLong("user_id")) {
				idCurUser = rs.getLong("user_id");
				i++;
			}
			if (start < i || start == 0) {
				break;
			}
		}

		while (!rs.isLast() && userList.size() != size) {
			if (user == null || user.getId() != rs.getLong("user_id")) {
				if (user != null) {
					userList.add(user);
				}
				cretedChatRooms = new ArrayList<>();
				chatRooms = new ArrayList<>();
				user = new User(rs.getLong("user_id"), rs.getString("login")
							, rs.getString("password"), cretedChatRooms, chatRooms);
			}
			if (rs.getBoolean("isowner")) {
				Chatroom chatroom = new Chatroom(rs.getLong("chatroom_id")
							, rs.getString("name"), null, null);
				cretedChatRooms.add(chatroom);
				chatRooms.add(chatroom);
			} else if (rs.getLong("chatroom_id") == rs.getLong("room")) {
				Chatroom chatroom = new Chatroom(rs.getLong("chatroom_id")
						, rs.getString("name"), null, null);
				chatRooms.add(chatroom);
			}
			rs.next();
		}
		if (rs.isLast() && user != null) {
			userList.add(user);
		}
		return userList;
	}

	@Override
	public Optional<User> findById(Long id) throws SQLException {
		User ret = null;
		PreparedStatement pst = dataSource.prepareStatement(SELECT_QUERY_TEMPLATE);
		pst.setLong(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			ret = new User(rs.getLong("id")
					, rs.getString("login")
					, rs.getString("password")
					, null, null);
		}
		return Optional.ofNullable(ret);
	}
}

package edu.school21.chat.repositories;

import edu.school21.chat.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepositoryJdbcImpl implements UserRepository {
	private static String QUERY_TEMPLATE = "SELECT * FROM chat.user WHERE id=?";
	private Connection dataSource;

	public UserRepositoryJdbcImpl(Connection dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Optional<User> findById(Long id) throws SQLException {
		User ret = null;
		PreparedStatement pst = dataSource.prepareStatement(QUERY_TEMPLATE);
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

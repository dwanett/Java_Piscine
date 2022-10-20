package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("usersRepositoryJdbc")
public class UsersRepositoryJdbcImpl implements UsersRepository{

	private DataSource dataSource;

	private Connection connection;

	@Autowired
	public UsersRepositoryJdbcImpl(@Qualifier("dataSourceBase") DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public User findById(Long id) {
		User user = null;
		try {
			if (this.connection == null) {
				this.connection = dataSource.getConnection();
			}
			PreparedStatement preparedStatement = this.connection.prepareStatement(FIND_ID_QUERY);
			preparedStatement.setLong(1, id);
			ResultSet res = preparedStatement.executeQuery();
			while (res.next()) {
				user = new User(id, res.getString("email"), res.getString("password"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return user;
	}

	@Override
	public List<User> findAll() {
		List<User> usersList = new ArrayList<>();
		try {
			if (this.connection == null) {
				this.connection = dataSource.getConnection();
			}
			PreparedStatement preparedStatement = this.connection.prepareStatement(FIND_ALL_QUERY);
			ResultSet res = preparedStatement.executeQuery();
			while (res.next()) {
				usersList.add(new User(res.getLong("id"), res.getString("email"), res.getString("password")));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return usersList;
	}

	@Override
	public void save(User entity) {
		try {
			if (this.connection == null) {
				this.connection = dataSource.getConnection();
			}
			PreparedStatement preparedStatement = this.connection.prepareStatement(SAVE_QUERY);
			preparedStatement.setString(1, entity.getEmail());
			preparedStatement.setString(2, entity.getPassword());
			preparedStatement.execute();
			entity.setId(findByEmail(entity.getEmail()).get().getId());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(User entity) {
		try {
			if (this.connection == null) {
				this.connection = dataSource.getConnection();
			}
			if (findByEmail(entity.getEmail()).isPresent()) {
				return;
			}
			PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_QUERY);
			preparedStatement.setString(1, entity.getEmail());
			preparedStatement.setLong(2, entity.getId());
			preparedStatement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Long id) {
		try {
			if (this.connection == null) {
				this.connection = dataSource.getConnection();
			}
			PreparedStatement preparedStatement = this.connection.prepareStatement(DELETE_QUERY);
			preparedStatement.setLong(1, id);
			preparedStatement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Optional<User> findByEmail(String email) {
		Optional<User> result = Optional.empty();
		try {
			if (this.connection == null) {
				this.connection = dataSource.getConnection();
			}
			PreparedStatement preparedStatement = this.connection.prepareStatement(FIND_EMAIL_QUERY);
			preparedStatement.setString(1, email);
			ResultSet res = preparedStatement.executeQuery();
			if (res.next()) {
				result = Optional.of(new User(res.getLong("id"), email, res.getString("password")));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}

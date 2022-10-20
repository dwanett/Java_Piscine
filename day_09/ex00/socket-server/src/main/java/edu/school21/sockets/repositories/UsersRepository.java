package edu.school21.sockets.repositories;


import edu.school21.sockets.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {

	final String UPDATE_QUERY = "UPDATE services.users SET name = ?, password = ? WHERE id = ?";
	final String FIND_ID_QUERY = "SELECT * FROM services.users WHERE id = ?";
	final String FIND_ALL_QUERY = "SELECT * FROM services.users";
	final String FIND_USER_NAME_QUERY = "SELECT * FROM services.users WHERE name = ?";
	final String SAVE_QUERY = "INSERT INTO services.users (name, password) VALUES (?, ?)";
	final String DELETE_QUERY = "DELETE FROM services.users WHERE id = ?";
	Optional<User> findByUserName(String name);
}

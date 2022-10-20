package school21.spring.service.repositories;

import school21.spring.service.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {

	final String UPDATE_QUERY = "UPDATE service.user SET email = ? WHERE id = ?";
	final String FIND_ID_QUERY = "SELECT * FROM service.user WHERE id = ?";
	final String FIND_ALL_QUERY = "SELECT * FROM service.user";
	final String FIND_EMAIL_QUERY = "SELECT * FROM service.user WHERE email = ?";
	final String SAVE_QUERY = "INSERT INTO service.user (email, password) VALUES (?, ?)";
	final String DELETE_QUERY = "DELETE FROM service.user WHERE id = ?";
	Optional<User> findByEmail(String email);
}

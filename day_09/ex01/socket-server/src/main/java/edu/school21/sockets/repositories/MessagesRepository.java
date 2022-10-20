package edu.school21.sockets.repositories;


import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;

import java.sql.Timestamp;
import java.util.Optional;

public interface MessagesRepository extends CrudRepository<Message> {

	final String UPDATE_QUERY = "UPDATE services.message SET text = ?, time = ?, author = ? WHERE id = ?";
	final String FIND_ID_QUERY = "SELECT * FROM services.message WHERE id = ?";
	final String FIND_ALL_QUERY = "SELECT * FROM services.message";
	final String FIND_MESSAGE_TIME_QUERY = "SELECT * FROM services.message WHERE time = ?";
	final String SAVE_QUERY = "INSERT INTO services.message (text, time, author) VALUES (?, ?, ?)";
	final String DELETE_QUERY = "DELETE FROM services.message WHERE id = ?";

	Optional<Message> findByTime(Timestamp timestamp);
}

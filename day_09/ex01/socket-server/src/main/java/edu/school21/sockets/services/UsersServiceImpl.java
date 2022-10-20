package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Component("usersService")
public class UsersServiceImpl implements UsersService {

	public UsersRepository usersRepository;

	public PasswordEncoder passwordEncoder;

	private MessagesRepository messagesRepository;

	@Autowired
	public UsersServiceImpl(MessagesRepository messagesRepository, UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
		this.messagesRepository = messagesRepository;
	}

	@Override
	public boolean signUp(String name, String password) {
		Optional<User> user = usersRepository.findByUserName(name);

		if (user.isPresent()) {
			System.err.println("User with this name exists!");
			return false;
		}
		usersRepository.save(new User(null, name, passwordEncoder.encode(password)));
		return true;
	}

	@Override
	public boolean signIn(String name, String password) {
		Optional<User> user = usersRepository.findByUserName(name);

		if (!user.isPresent()) {
			System.err.println("User not found!");
			return false;
		}
		if (!passwordEncoder.matches(password, user.get().getPassword())) {
			System.err.println("Incorrect password!");
			return false;
		}
		return true;
	}

	@Override
	public void createMessage(String text, String userName) {
		Optional<User> user = usersRepository.findByUserName(userName);
		Message message = new Message(null, text, Timestamp.valueOf(LocalDateTime.now()), user.get());
		messagesRepository.save(message);
	}
}

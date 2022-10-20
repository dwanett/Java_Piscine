package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component("usersService")
public class UsersServiceImpl implements UsersService {

	public UsersRepository usersRepository;

	public PasswordEncoder passwordEncoder;

	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
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
}

package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class UsersServiceImpl implements UsersService{

	public UsersRepository usersRepository;
	@Autowired
	public UsersServiceImpl(@Qualifier("usersRepositoryJdbc") UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	@Override
	public String signUp(String email) {
		Optional<User> user = usersRepository.findByEmail(email);
		String password = UUID.randomUUID().toString();
		if (user.isPresent()) {
			System.err.println("User with this name exists!");
			return null;
		}
		usersRepository.save(new User(null, email, password));
		return password;
	}
}

package edu.school21.services;

import edu.school21.exeptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

class EntityNotFoundException extends RuntimeException {
}

public class UsersServiceImpl {
	private final UsersRepository usersRepository;

	public UsersServiceImpl(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	boolean authenticate(String login, String password) {
		boolean result = false;

		try {
			User user = usersRepository.findByLogin(login);
			if (user.isAuthentication()) {
				throw new AlreadyAuthenticatedException();
			} else if (user.getPassword().equals(password)) {
				result = true;
				user.setAuthentication(true);
				usersRepository.update(user);
			}
		} catch (EntityNotFoundException e) {
			System.err.println(e.getMessage());
		}
		return result;
	}
}

package edu.school21.services;
import edu.school21.exeptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class UsersServiceImplTest {

	private final UsersRepository usersRepository = mock(UsersRepository.class);

	private final UsersServiceImpl usersService = new UsersServiceImpl(usersRepository);
	private final User user = new User(1L, "name", "password", false);

	private final User user2 = new User(2L, "name2", "password", true);

	private final String INCORRECT_NAME = "inc";

	@BeforeEach
	public void init() {
		when(usersRepository.findByLogin("name")).thenReturn(user);
		when(usersRepository.findByLogin("name2")).thenReturn(user2);
		when(usersRepository.findByLogin(INCORRECT_NAME)).thenThrow(new EntityNotFoundException());
		doNothing().when(usersRepository).update(user);
	}

	@Test
	public void correctLoginAndPasswordTest() {
		Assertions.assertTrue(usersService.authenticate("name", "password"));
		verify(usersRepository).findByLogin("name");
		verify(usersRepository).update(user);
	}

	@Test
	public void correctLoginAndPasswordTestTrow() {
		Assertions.assertThrows(AlreadyAuthenticatedException.class
				, () -> usersService.authenticate("name2", "password"));
		verify(usersRepository).findByLogin("name2");
	}

	@Test
	public void incorrectLoginAndCorrectPasswordTest() {
		Assertions.assertFalse(usersService.authenticate(INCORRECT_NAME, "password"));
		verify(usersRepository).findByLogin(INCORRECT_NAME);
	}


	@Test
	public void correctLoginAndIncorrectPasswordTest() {
		Assertions.assertFalse(usersService.authenticate("name", "IncorrectPassword"));
		verify(usersRepository).findByLogin("name");
	}
}

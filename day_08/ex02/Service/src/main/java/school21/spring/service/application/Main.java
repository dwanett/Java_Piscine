package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.ApplicationConfig;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.util.UUID;

public class Main {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext("school21.spring.service");
		{
			UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
			System.out.println(usersRepository.findAll());
			System.out.println(usersRepository.findById(1L));
			System.out.println(usersRepository.findByEmail("user3@yandex.ru"));

			User user = new User(null, "userTmp@yandex.ru", UUID.randomUUID().toString());
			usersRepository.save(user);
			user.setEmail("userTmpReplace@yandex.ru");
			usersRepository.update(user);
			System.out.println(usersRepository.findById(user.getId()));
			usersRepository.delete(user.getId());
		}
		System.out.println();
		System.out.println();
		{
			UsersRepository usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
			System.out.println(usersRepository.findAll());
			System.out.println(usersRepository.findById(1L));
			System.out.println(usersRepository.findByEmail("user3@yandex.ru"));

			User user = new User(null, "userTmp@yandex.ru", UUID.randomUUID().toString());
			usersRepository.save(user);
			user.setEmail("userTmpReplace@yandex.ru");
			usersRepository.update(user);
			System.out.println(usersRepository.findById(user.getId()));
			usersRepository.delete(user.getId());
		}
	}
}
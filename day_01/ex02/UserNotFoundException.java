public class UserNotFoundException extends RuntimeException {
	@Override
	public String toString() {
		return ("Ошибка: Пользователь не найден!");
	}
}

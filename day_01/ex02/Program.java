public class Program {
	public static void main(String[] args) {
		User first = new User("Nikita", 100);

		User second = new User("Vlad", 200);

		UsersArrayList usersArrayList = new UsersArrayList();

		System.out.println("Количество пользователей - "
							+ usersArrayList.getCountUser());

		usersArrayList.addUser(first);

		System.out.println("Количество пользователей - "
							+ usersArrayList.getCountUser());

		for (int i = 0; i != 100; i++) {
			usersArrayList.addUser(new User("Nikita", 100));
		}

		System.out.println("Количество пользователей - "
							+ usersArrayList.getCountUser());
		System.out.println();

		System.out.println(usersArrayList.getUserForId(3));

		System.out.println();

		System.out.println(usersArrayList.getUserForIndex(100));
	}
}

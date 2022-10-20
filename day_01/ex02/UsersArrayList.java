public class UsersArrayList implements UsersList {
	private static final int START_SIZE_ARRAY = 10;

	private User[] arrayUsers = new User[START_SIZE_ARRAY];

	private Integer size = START_SIZE_ARRAY;

	private Integer countUser = 0;

	@Override
	public void addUser(User newUser) {
		if (size.intValue() == countUser) {
			resizeArray();
		}
		arrayUsers[countUser] = newUser;
		countUser++;
	}

	@Override
	public User getUserForId(Integer id) {
		for (int i = 0; i != countUser; i++) {
			if (arrayUsers[i].getId().equals(id)) {
				return arrayUsers[i];
			}
		}
		throw new UserNotFoundException();
	}

	@Override
	public User getUserForIndex(Integer index) {
		if (index < 0 || index > countUser - 1) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return arrayUsers[index];
	}

	@Override
	public Integer getCountUser() {
		return countUser;
	}

	private void resizeArray() {
		User[] newArrayUsers = new User[this.size * 2];

		this.size = this.size * 2;

		for (int i = 0; i != countUser; i++) {
			newArrayUsers[i] = arrayUsers[i];
		}
		arrayUsers = newArrayUsers;
	}
}

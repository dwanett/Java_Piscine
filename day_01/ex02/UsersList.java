interface UsersList {
	void	addUser(User newUser);
	User	getUserForId(Integer id);
	User	getUserForIndex(Integer index);
	Integer	getCountUser();
}

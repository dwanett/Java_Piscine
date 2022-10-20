public class UserIdsGenerator {
	private Integer id;

	private static final UserIdsGenerator INSTANCE = new UserIdsGenerator();

	private UserIdsGenerator(){
		this.id = 0;
	}

	public static UserIdsGenerator getInstance(){
		return INSTANCE;
	}

	public int generateId(){
		return this.id++;
	}
}

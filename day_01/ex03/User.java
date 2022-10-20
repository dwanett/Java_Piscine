public class User {
	private Integer id;

	private String  name;

	private Integer balance;

	private TransactionsList transactionsList;

	public User (String name, Integer balance) {
		this.name = name;
		this.id = UserIdsGenerator.getInstance().generateId();
		if (this.checkBalance(balance)) {
			this.balance = balance;
		} else {
			this.balance = 0;
			System.err.println("Внимание: Баланс пользователя с id"
					+ this.id + "был изменен. Текущий баланс: " + this.balance);
		}
	}

	public TransactionsList getTransactionsList() {
		return transactionsList;
	}

	public void setTransactionsList(TransactionsList transactionsList) {
		this.transactionsList = transactionsList;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		if (this.checkBalance(balance)) {
			this.balance = balance;
		} else {
			System.err.println(
				"Внимание: Баланс пользователя с id"
				+ this.id + "не был изменен. Текущий баланс: " + this.balance);
		}
	}

	private boolean checkBalance(Integer balance) {
		if (balance <= 0) {
			System.err.println(
				"Ошибка: Пользователь с id - " + this.id
				+ "не может иметь отрицательный баланс (" + balance + ")");
			return false;
		}
		return true;
	}

	@Override
	public String toString(){
		return "id - " + this.id + "\nИмя - " + this.name
				+ "\nБаланс - " + this.balance;
	}
}

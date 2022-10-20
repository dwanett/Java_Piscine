public class Program {
	public static void main(String[] args) {
		TransactionsService ts = new TransactionsService();

		User john = new User("John", 1000);

		User mike = new User("Mike", 5000);

		User eluceon = new User("Eluceon", 7777);

		User dima = new User("Dima", 99999);

		ts.addUser(john);
		ts.addUser(mike);
		ts.addUser(eluceon);
		ts.addUser(dima);

		System.out.println("User's blanes:");
		System.out.println("john: " + ts.getBalanceUserId(john.getId()) + ", mike: " + ts.getBalanceUserId(mike.getId())
				+ ", eluceon: " + ts.getBalanceUserId(eluceon.getId()) + " dima: " + ts.getBalanceUserId(dima.getId()));

		try {
			ts.creteTransaction(john.getId(), mike.getId(), 100);
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			ts.creteTransaction(john.getId(), eluceon.getId(), -500);
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			ts.creteTransaction(eluceon.getId(), dima.getId(), 1000);
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			ts.creteTransaction(eluceon.getId(), dima.getId(), 5000000);
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			ts.creteTransaction(mike.getId(), dima.getId(), -10000000);
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			ts.creteTransaction(mike.getId(), dima.getId(), 100);
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			ts.creteTransaction(mike.getId(), dima.getId(), -500);
		} catch (Exception e) {
			System.out.println(e);
		}


		Transaction[] transactions = ts.getArrayTransactionUserId(mike.getId());
		System.out.println("Mikes's transactions:");
		for (Transaction transaction : transactions) {
			System.out.println(transaction);
		}

		transactions = ts.getArrayTransactionUserId(eluceon.getId());
		System.out.println("Eluceon's transactions:");
		for (Transaction transaction : transactions) {
			System.out.println(transaction);
		}

		transactions = ts.getArrayTransactionUserId(john.getId());
		System.out.println("John's transactions:");
		for (Transaction transaction : transactions) {
			System.out.println(transaction);
		}

		transactions = ts.getArrayTransactionUserId(dima.getId());
		System.out.println("Dima's transactions:");
		for (Transaction transaction : transactions) {
			System.out.println(transaction);
		}

		ts.deleteTransaction(transactions[1].getIdentifier(), dima.getId());
		transactions = ts.getArrayTransactionUserId(dima.getId());
		System.out.println("\nDima's transactions after deleting transaction:");
		for (Transaction transaction : transactions) {
			System.out.println(transaction);
		}

		System.out.println("\nInvalid transactions:");
		Transaction invalidTransactions[] = ts.invalidTransaction();
		for (Transaction transaction : invalidTransactions) {
			System.out.println(transaction);
		}

		System.out.println("\nDima's balance: " + ts.getBalanceUserId(dima.getId()));
	}
}

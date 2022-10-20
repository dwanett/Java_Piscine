import java.util.UUID;

public class TransactionsService {
	private UsersList usersList = new UsersArrayList();

	private TransactionsList unpairedTransaction = new TransactionsLinkedList();

	public void addUser(User newUser) {
		usersList.addUser(newUser);
	}

	public int getBalanceUserId(Integer idUser) throws UserNotFoundException {
		return usersList.getUserForId(idUser).getBalance();
	}

	public int getBalanceUserIndex(Integer index) {
		return usersList.getUserForIndex(index).getBalance();
	}

	public void creteTransaction(Integer idRecipient, Integer idSender, Integer sumTransaction) {
		User recipient = usersList.getUserForId(idRecipient);

		User sender = usersList.getUserForId(idSender);

		if (sumTransaction < 0) {
			if (recipient.getBalance() + sumTransaction < 0) {
				throw new IllegalTransactionException();
			} else {
				recipient.setBalance(recipient.getBalance() + sumTransaction);
				sender.setBalance(sender.getBalance() - sumTransaction);
			}
		} else {
			if (sender.getBalance() - sumTransaction < 0) {
				throw new IllegalTransactionException();
			} else {
				recipient.setBalance(recipient.getBalance() + sumTransaction);
				sender.setBalance(sender.getBalance() - sumTransaction);
			}
		}

		Transaction first = new Transaction(recipient, sender, sumTransaction);

		Transaction second = new Transaction(sender, recipient,  -sumTransaction);

		second.setIdentifier(first.getIdentifier());
		recipient.getTransactionsList().addTransactions(first);
		sender.getTransactionsList().addTransactions(second);
	}

	public  Transaction[] getArrayTransactionUserId(Integer idUser) {
		return usersList.getUserForId(idUser).getTransactionsList().toArray();
	}

	public  Transaction[] getArrayTransactionUserIndex(Integer index) {
		return usersList.getUserForIndex(index).getTransactionsList().toArray();
	}

	public void deleteTransaction(UUID idTransation, Integer idUser) {
		Transaction [] transactionsResipient = getArrayTransactionUserId(idUser);

		Transaction [] transactionsSender = new Transaction[0];

		User		sender;

		for (int i = 0; i != transactionsResipient.length; i++) {
			if (transactionsResipient[i].getIdentifier().equals(idTransation)) {
				sender = transactionsResipient[i].getSender();
				transactionsSender = getArrayTransactionUserId(sender.getId());
			}
		}

		for (int i = 0; i != transactionsSender.length; i++) {
			if (transactionsSender[i].getIdentifier().equals(idTransation)) {
				unpairedTransaction.addTransactions(transactionsSender[i]);
			}
		}

		usersList.getUserForId(idUser).getTransactionsList().deleteTransactions(idTransation);
	}

	public Transaction[] invalidTransaction() {
		return unpairedTransaction.toArray();
	}

	public UsersList getUsersList() {
		return usersList;
	}

	public void setUsersList(UsersList usersList) {
		this.usersList = usersList;
	}
}

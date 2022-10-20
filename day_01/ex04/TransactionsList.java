import java.util.UUID;

interface TransactionsList {
	void			addTransactions(Transaction transaction);
	void			deleteTransactions(UUID uuid);
	Transaction[]	toArray();
}

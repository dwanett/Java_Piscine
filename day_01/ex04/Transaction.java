import java.util.UUID;

enum TypeTransaction{
	debits,

	credits
}

public class Transaction {
	private UUID			identifier;

	private User			recipient;

	private User			sender;

	private TypeTransaction	typeTransaction;

	private Integer			sumTransaction;

	public Transaction(User recipient, User sender, Integer sumTransaction) {
		this.identifier = UUID.randomUUID();
		this.recipient = recipient;
		this.sender = sender;

		if (sumTransaction < 0) {
			this.typeTransaction = TypeTransaction.credits;
		}
		else {
			this.typeTransaction = TypeTransaction.debits;
		}

		this.sumTransaction = sumTransaction;
	}

	public UUID getIdentifier() {
		return identifier;
	}

	public void setIdentifier(UUID identifier) {
		this.identifier = identifier;
	}

	public User getRecipient() {
		return recipient;
	}

	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public TypeTransaction getTypeTransaction() {
		return typeTransaction;
	}

	public void setTypeTransaction(TypeTransaction typeTransaction) {
		this.typeTransaction = typeTransaction;
	}

	public Integer getSumTransaction() {
		return sumTransaction;
	}

	public void setSumTransaction(Integer sumTransaction) {
		this.sumTransaction = sumTransaction;
	}

	public String toString(){
		return "id - " + this.identifier + "\nПолучатель - "
				+ recipient.getName() + "\nОтправитель - " + sender.getName()
				+ "\nТип транзакции - "
				+ ((this.typeTransaction == TypeTransaction.credits)
				? "credits"
				: "debits")
				+ "\nСумма транзакции - " + this.sumTransaction;
	}
}

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {

	private TransactionsLinkedListNode node = new TransactionsLinkedListNode(
											null, null, null);

	private Integer length = 0;
	@Override
	public void addTransactions(Transaction transaction) {
		TransactionsLinkedListNode newNode = new TransactionsLinkedListNode(
											this.node, null, transaction);
		this.node.setPrevious(newNode);
		this.node = newNode;
		this.length++;
	}

	@Override
	public void deleteTransactions(UUID uuid) {
		TransactionsLinkedListNode tmp = this.node;

		while (tmp.getNext() != null) {
			if (tmp.getData().getIdentifier().equals(uuid)) {
				tmp.getPrevious().setNext(tmp.getNext());
				tmp.setData(null);
				tmp.setPrevious(null);
				tmp.setNext(null);
				this.length--;
				return;
			}
			tmp = tmp.getNext();
		}
		if (tmp.getNext() == null) {
			throw new TransactionNotFoundException();
		}
	}

	@Override
	public Transaction[] toArray() {
		Transaction[] newArray = new Transaction[this.length];

		TransactionsLinkedListNode tmp = this.node;

		for (int i = 0; tmp.getNext() != null; i++) {
			newArray[i] = tmp.getData();
			tmp = tmp.getNext();
		}
		return newArray;
	}
}
class TransactionsLinkedListNode {
	private TransactionsLinkedListNode	next;

	private TransactionsLinkedListNode	previous;

	private Transaction					data;

	public TransactionsLinkedListNode(TransactionsLinkedListNode next,
			TransactionsLinkedListNode previous, Transaction data) {
		this.next = next;
		this.previous = previous;
		this.data = data;
	}

	public TransactionsLinkedListNode getNext() {
		return next;
	}

	public void setNext(TransactionsLinkedListNode next) {
		this.next = next;
	}

	public TransactionsLinkedListNode getPrevious() {
		return this.previous;
	}

	public void setPrevious(TransactionsLinkedListNode previous) {
		this.previous = previous;
	}

	public Transaction getData() {
		return this.data;
	}

	public void setData(Transaction data) {
		this.data = data;
	}
}
import java.util.UUID;

public class Program {
	public static void main(String[] args){
		User first = new User("Nikita", 100);

		User second = new User("Vlad", 200);

		TransactionsLinkedList arr = new TransactionsLinkedList();

		UUID tmpUuid = null;

		for (int i = 0; i != 10; i++) {
			Transaction tmp = new Transaction(first, second, i);
			if (i == 5) {
				tmpUuid = tmp.getIdentifier();
			}
			arr.addTransactions(tmp);
		}

		arr.deleteTransactions(tmpUuid);
		Transaction[] tmpArray = arr.toArray();

		for (int i = 0; i != 9; i++) {
			System.out.println();
			System.out.println(tmpArray[i]);
		}
	}
}

public class TransactionNotFoundException extends RuntimeException {
	public TransactionNotFoundException() {
		super("Элемент с таким UUID не найден!");
	}
}

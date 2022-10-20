public class IllegalTransactionException extends RuntimeException {
		public IllegalTransactionException() {
			super("Ошибка проведения транзакции!");
		}
}

public class Program {
	public static void main(String[] args) {
		User first = new User(1, "Nikita", 100);

		User second = new User(2, "Vlad", 200);
		
		Transaction transaction = new Transaction(first, second, -100);

		System.out.println(first);
		System.out.println();
		System.out.println(second);
		System.out.println();
		System.out.println(transaction);
	}
}

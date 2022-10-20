import java.util.Scanner;

public class Program {

	public static int findRoot(int number) {
		int res = number / 4;

		while (res * res > number) {
			res /= 4;
		}

		while (res * res < number) {
			res++;
		}

		return res;
	}

	public static int step(int number, int root) {
		int iterator = 0;

		int chekNumber = 2;

		while (chekNumber <= root) {
			iterator++;
			if (number % chekNumber == 0 && number != 2) {
				System.out.print("false ");
				return iterator;
			}
			chekNumber++;
		}

		System.out.print("true ");

		return iterator;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("-> ");

		final int number = scanner.nextInt();

		scanner.close();

		if (number <= 1) {
			System.err.println("IllegalArgument");
			System.exit(-1);
		} else {
			System.out.println(step(number, findRoot(number)));
		}
	}
}
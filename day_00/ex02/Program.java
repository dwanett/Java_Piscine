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

	public static boolean isEasy(int number, int root) {
		int chekNumber = 2;

		while (chekNumber <= root) {
			if (number % chekNumber == 0 && number != 2) {
				return false;
			}
			chekNumber++;
		}
		return true;
	}

	public static int sumNumber(int number) {
		int result = 0;
		int tmp = number;

		while (tmp != 0) {
			result += tmp % 10;
			tmp /= 10;
		}
		return result;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int	iterator = 0;

		int number = 0;

		int sumNumber = 0;

		while (number != 42) {
			System.out.print("-> ");
			number = scanner.nextInt();
			sumNumber = sumNumber(number);
			if (isEasy(sumNumber, findRoot(sumNumber))) {
				iterator++;
			}
		}

		scanner.close();

		System.out.printf("   Count of coffee - request - %d\n", iterator);
	}
}
import java.util.Scanner;

public class Program {

	private static final String WEEK_TEXT = "Week ";

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("-> ");

		String numberWeek = scanner.nextLine();

		int curWeek = 1;

		long vectorGrade = 0;

		while (!numberWeek.equals("42")) {
			if (curWeek >= 19 || !numberWeek.equals(WEEK_TEXT + curWeek)) {
				error();
			}
			System.out.print("-> ");
			vectorGrade = addVectorGrade(vectorGrade, getMinGrade(scanner));
			curWeek++;
			System.out.print("-> ");
			numberWeek = scanner.nextLine();
		}

		scanner.close();

		printVectorGrade(vectorGrade);
	}

	public static void error() {
		System.err.println("Illegal Argument");
		System.exit(-1);
	}

	public static int getMinGrade(Scanner scanner) {
		int curNumber = scanner.nextInt();

		int minValue = curNumber;

		for (int i = 1; i <= 4; i++) {
			if (curNumber < 1 || curNumber > 9) {
				error();
			}
			curNumber = scanner.nextInt();
			if (minValue > curNumber) {
				minValue = curNumber;
			}
		}

		scanner.nextLine();

		return minValue;
	}

	public static long addVectorGrade(long vectorGrade, int newMinValue) {
		if (vectorGrade % 10 == 0) {
			vectorGrade = newMinValue;
		} else {
			vectorGrade *= 10;
			vectorGrade += newMinValue;
		}
		
		return vectorGrade;
	}

	public static void printVectorGrade(long vectorGrade) {
		long tmp = vectorGrade;

		int curWeek = 1;

		long countNumb = 1;

		while (tmp != 0) {
			countNumb *= 10;
			tmp /= 10;
		}

		countNumb /= 10;

		while (vectorGrade != 0) {
			System.out.print(WEEK_TEXT + curWeek + " ");

			for (long i = vectorGrade / countNumb; i != 0; i--) {
				System.out.print("=");
			}
			System.out.println(">");
			curWeek++;
			vectorGrade %= countNumb;
			countNumb /= 10;
		}
	}
}

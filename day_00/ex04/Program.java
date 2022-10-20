import java.util.Scanner;

public class Program {

	private static final int MAX_CODE_SYMBOL = 65535;
	private static final int SIZE_GISTOGRAM = 10;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int[] arr = new int[MAX_CODE_SYMBOL];

		int[][] arrMaxValue = new int[SIZE_GISTOGRAM][SIZE_GISTOGRAM + 2];

		System.out.print("-> ");

		String inputStr = scanner.nextLine();

		int lengchStr = inputStr.length();

		char[] str = inputStr.toCharArray();

		for (int i = 0; i != lengchStr; i++) {
			if (arr[str[i]] < 999) {
				arr[str[i]] += 1;
			}
		}

		findMaxValue(arrMaxValue, arr);

		printArray(arrMaxValue);

		scanner.close();
	}

	public static void findMaxValue(int[][] arrMaxValue, int[] arr) {
		float coefficient = 0;

		for (int j = 0; j != SIZE_GISTOGRAM; j++) {
			int indexMaxValue = 0;

			int maxValue = 0;

			int k = 1;

			for (int i = MAX_CODE_SYMBOL - 1; i >= 0; i--) {
				if (maxValue <= arr[i]) {
					maxValue = arr[i];
					indexMaxValue = i;
				}
			}

			if (j == 0) {
				coefficient = 10.0f / (float) maxValue;
			}

			arrMaxValue[j][0] = indexMaxValue;

			for (; k <= coefficient * maxValue; k++) {
				arrMaxValue[j][k] = -1;
			}

			arrMaxValue[j][k] = arr[indexMaxValue];
			arr[indexMaxValue] = 0;
		}
	}

	public static void printArray(int[][] array) {
		int j;

		for (int i = SIZE_GISTOGRAM + 1; i >= 0; i--) {

			for (j = 0; j <= SIZE_GISTOGRAM - 1; j++) {

				if (array[j][i] != 0) {
					if (array[j][i] == -1) {
						System.out.print("  #");
					} else if ((i != 0 && array[j][i - 1] == -1) || i - 1 == 0) {
						System.out.printf("%3d", array[j][i]);
					} else {
						System.out.printf("  %c", array[j][i]);
					}
				}
			}
			System.out.println();
		}
	}
}

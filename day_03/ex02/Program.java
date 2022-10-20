import java.util.Random;
import java.util.Vector;

public class Program {

	private static Integer defaultSize = 10;

	private static Integer defaultCountThread = 2;

	public static void main(String[] args) throws InterruptedException {

		Random random = new Random();

		Vector<Integer> array = new Vector<>();

		long sum = 0;

		if (args.length >= 1) {
			defaultSize = parsArg(args[0], "--arraySize", defaultSize);
			if (args.length >= 2) {
				defaultCountThread = parsArg(args[1], "--threadsCount", defaultCountThread);
			}
		}

		if (defaultSize < defaultCountThread || defaultSize > 2_000_000
					|| defaultSize == 0 || defaultCountThread == 0) {
			setDefaultValue();
		}

		for (int i = 0; i != defaultSize; i++) {
			int newNumber = random.ints(-1000, 1001).findFirst().getAsInt();
			array.add(newNumber);
			sum += newNumber;
		}

		System.out.printf("Sum: %d\n", sum);
		startThreads(array);
	}

	public static Integer parsArg(String arg, String nameCommand, Integer value) {
		String[] command = arg.split("=");

		if (command[0].equals(nameCommand)) {
			try {
				if (command.length >= 2) {
					value = Integer.parseUnsignedInt(command[1]);
				}
			} catch (NumberFormatException e) {
				return value;
			}
		}

		return value;
	}

	public static void setDefaultValue() {
		defaultSize = 10;
		defaultCountThread = 2;
	}

	public static void startThreads(Vector<Integer> array){
		MyThread newThread;

		int start = 0;

		int end;

		int delta = defaultSize / defaultCountThread;

		if (defaultCountThread % 2 != 0 && defaultSize / defaultCountThread != 1) {
			delta += 1;
		}

		for (int i = 0; i != defaultCountThread; i++, start += delta) {
			if (start + delta != defaultCountThread * delta) {
				end = start + delta - 1;
				newThread = new MyThread(start, end, Integer.toString(i + 1), i + 1, defaultCountThread, array);
			} else {
				newThread = new MyThread(start, defaultSize - 1, Integer.toString(i + 1), i + 1, defaultCountThread, array);
			}
			newThread.start();
		}
	}
}

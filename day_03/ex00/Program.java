public class Program {

	private static Integer count = 10000;

	public static void main(String[] args) throws InterruptedException {

		if (args.length == 1) {
			count = parsArg(args[0]);
		}

		MyThread eggThread = new MyThread(count, "Egg");

		MyThread henThread = new MyThread(count, "Hen");

		henThread.start();
		eggThread.start();

		eggThread.join();
		henThread.join();

		for (int i = 0; i != count; i++) {
			System.out.println("Human");
		}
	}

	public static Integer parsArg(String arg) {
		String[] command = arg.split("=");

		if (command[0].equals("--count")) {
			try {
				count = Integer.parseUnsignedInt(command[1]);
			} catch (NumberFormatException e) {
				return count;
			}
		}

		return count;
	}
}

public class Program {

	private static Integer count = 10;


	public static void main(String[] args) {

		if (args.length >= 1) {
			count = parsArg(args[0]);
		}

		MyThread eggThread = new MyThread(count, "Egg");
		MyThread henThread = new MyThread(count, "Hen");

		henThread.start();
		eggThread.start();

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

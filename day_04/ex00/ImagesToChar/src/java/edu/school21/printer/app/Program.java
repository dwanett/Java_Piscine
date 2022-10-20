package edu.school21.printer.app;

import edu.school21.printer.logic.*;

public class Program {
	public static char WHITE;
	public static char BLACK;
	public static String PATH;

	public static void main(String[] args) {

		if (args.length != 3) {
			System.err.println("Error: number arguments!");
			System.exit(-1);
		}

		if (args[0].length() != 1 || args[1].length() != 1) {
			System.err.println("Error: Must one symbol for color!");
			System.exit(-1);
		}

		WHITE = args[0].charAt(0);
		BLACK = args[1].charAt(0);
		PATH = args[2];

		Logic logic = new Logic(WHITE, BLACK, PATH);

		logic.printArray();
	}
}

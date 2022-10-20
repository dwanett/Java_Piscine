package edu.school21.printer.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import edu.school21.printer.logic.Logic;
import com.beust.jcommander.ParameterException;

@Parameters(separators = "=")
public class Program {
	public static String PATH;

	@Parameter(names = {"--white"}, required = true)

	private static String WHITE;

	@Parameter(names = {"--black"}, required = true)

	private static String BLACK;

	public static void main(String[] args) {
		try {
			Program program = new Program();
			JCommander.newBuilder().addObject(program).build()
					.parse(args);
		} catch (ParameterException e){
			System.err.println(e.getMessage());
			System.exit(-1);;
		}

		PATH = "/resources/image.bmp";

		Logic logic = new Logic(WHITE, BLACK, PATH);

		logic.printArray();
	}
}

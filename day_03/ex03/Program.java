import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Program {

	private static Integer defaultCountThread = 2;

	public static void main(String[] args) throws IOException {
		BufferedReader readerFile = new BufferedReader(new FileReader("files_urls.txt"));

		List<String[]> listURL = getURL(readerFile);

		if (args.length == 1) {
			defaultCountThread = parsArg(args[0], "--threadsCount", defaultCountThread);
		}

		if (defaultCountThread == 0) {
			defaultCountThread = 2;
		}

		for (int i = 0; i != defaultCountThread; i++) {
			MyThread newThread = new MyThread(listURL, i + 1);
			newThread.start();
		}
	}

	public static List<String[]> getURL(BufferedReader readerFile) throws IOException {
		String line;

		List<String[]> listURL = new ArrayList<>();

		while ((line = readerFile.readLine()) != null){
			String [] splitLine = line.split(" ");
			listURL.add(splitLine);
		}
		return listURL;
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
}

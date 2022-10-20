import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Program {
	private static Path currentDirectory;

	public static void main(String[] args) {
		BufferedReader reader = null;

		reader = new BufferedReader(new InputStreamReader(System.in));
		if (currentDirectory == null)
			currentDirectory = Paths.get("").toAbsolutePath();
		if (args.length >= 1) {
			String[] path = args[0].split("=");
			currentDirectory = currentDirectory.resolve(path[1]);
		}
		currentDirectory = currentDirectory.resolve("/");
		System.out.println(currentDirectory);
		while (true) {
			try {
				System.out.print("->");
				String[] command = reader.readLine().split(" ");
				switch (command[0]) {
					case "ls":
						myListDirecory();
						break;
					case "cd":
						myChangeDir(command);
						break;
					case "mv":
						myMove(command);
						break;
					case "exit":
						System.exit(0);
					default:
						System.out.println("Command '" + command[0] + "' not found");
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	public static void myMove(String[] command) {
		try {
			if (command.length != 3) {
				System.err.println("mv: Error number arguments!");
			} else if (Files.isDirectory(currentDirectory.resolve(command[2]))) {
				Files.move(currentDirectory.resolve(command[1]),
						currentDirectory.resolve(command[2]).resolve(
								currentDirectory.resolve(command[1]).getFileName()));
			} else {
				Files.move(currentDirectory.resolve(command[1]),
						currentDirectory.resolve(command[1]).resolveSibling(command[2]));
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void myChangeDir(String[] command) {
		if (command.length != 2) {
			System.err.println("cd: Error number arguments!");
		} else if (Files.isDirectory(currentDirectory.resolve(command[1]))) {
			currentDirectory = currentDirectory.resolve(command[1]).normalize();
			System.out.println(currentDirectory);
		} else {
			System.err.println("cd: " + command[1] + " is not directory!");
		}
	}

	public static long getSize(File file) {
		long size = 0;

		if (!file.isFile()) {
			File[] listFile = file.listFiles();
			if (listFile == null) {
				return size;
			}
			for (File curFile : listFile) {
				size += getSize(curFile);
			}
		} else {
			size = file.length();
		}
		return size;
	}

	public static void myListDirecory() {
		File[] listFile = currentDirectory.toFile().listFiles();

		for (File curFile : listFile) {
			System.out.printf("%s  %d KB\n", curFile.getName(), Math.round(getSize(curFile) / 1024.0));
		}
	}
}

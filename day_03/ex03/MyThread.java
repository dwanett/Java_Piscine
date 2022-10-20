import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public class MyThread extends Thread {

	private ListUrl listUrl;

	private int numberThread;

	public MyThread(List<String[]> listURL, int numberThread) {
		this.listUrl = new ListUrl(listURL);
		this.numberThread = numberThread;
	}


	@Override
	public void run() {
		URL url;
		
		String[] splitUri = listUrl.getNextUri();

		while (splitUri != null) {
			try {
				url = new URL(splitUri[1]);
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
			try {
				String[] splitNameFile = splitUri[1].split("/");

				String nameFile = splitNameFile[splitNameFile.length - 1];

				InputStream inputStream = url.openStream();

				Path currentDirectory = Paths.get("").toAbsolutePath();

				System.out.printf(
							"Thread-%d start download file number %s\n",
									numberThread, splitUri[0]);
				Files.copy(inputStream, new File(currentDirectory.toString()
							+ "/" + nameFile).toPath(), REPLACE_EXISTING);
				System.out.printf(
							"Thread-%d finish download file number %s\n",
									numberThread, splitUri[0]);
				splitUri = listUrl.getNextUri();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

public class Program {
	public static int maxLenghtSignature = 0;

	public static void main(String[] args) {
		HashMap<Integer[], String> allTypeFile = new HashMap<>();

		Vector<Byte> vectorByte = new Vector<>();

		FileInputStream fileInputStream;

		FileOutputStream fileOutputStream = null;

		Scanner scanner = new Scanner(System.in);

		try {
			fileInputStream = new FileInputStream("signatures.txt");
			fileOutputStream = new FileOutputStream("result.txt");
			for (int i = fileInputStream.read(); i != -1; i = fileInputStream.read()) {
				if (i != '\n') {
					vectorByte.add((byte) i);
				} else {
					pasingSignatureInfo(allTypeFile, vectorByte);
				}
			}
			if (vectorByte.size() != 0) {
				pasingSignatureInfo(allTypeFile, vectorByte);
			}
			fileInputStream.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}

		System.out.print("-> ");
		String input = scanner.nextLine();

		while (!input.equals("42")) {
			checkFile(input, allTypeFile, fileOutputStream);
			System.out.print("-> ");
			input = scanner.nextLine();
		}

		scanner.close();

		try {
			fileOutputStream.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void pasingSignatureInfo(Map<Integer[], String> allTypeFile,
											Vector<Byte> vectorByte) {
		byte[] arrayByte;

		Integer[] arrayByteSignatur;

		int j = 0;

		arrayByte = toByteVector(vectorByte);
		int sizeName = vectorByte.indexOf((byte)',');
		String nameType = new String(arrayByte, 0, sizeName);
		arrayByteSignatur = new Integer[((vectorByte.size()
							- (sizeName + 2)) / 3) + 1];
		if (maxLenghtSignature < arrayByteSignatur.length) {
			maxLenghtSignature = arrayByteSignatur.length;
		}
		for (int i = 0; i < vectorByte.size() - (sizeName + 2); i += 3) {
			String hex = new String(arrayByte, sizeName + i + 2, 2);
			if (hex.equals("??")) {
				arrayByteSignatur[j] = -1;
			} else {
				arrayByteSignatur[j] = Integer.parseUnsignedInt(hex, 16);
			}
			j++;
		}
		vectorByte.clear();
		allTypeFile.put(arrayByteSignatur, nameType);
	}
	
	public static byte[] toByteVector(Vector<Byte> vectorByte) {
		byte[] arrayByte;
		int size = vectorByte.size();

		arrayByte = new byte[size];
		for (int i = 0; i != size; i++) {
			arrayByte[i] = vectorByte.get(i);
		}
		return arrayByte;
	}

	public static void checkFile(String pathFile, Map<Integer[],
			String> allTypeFile, FileOutputStream fileOutputStream) {
		FileInputStream fileInputStream;

		byte[] file = new byte[maxLenghtSignature];

		int i;

		try {
			fileInputStream = new FileInputStream(pathFile);
			fileInputStream.read(file, 0, maxLenghtSignature);
			fileInputStream.close();
		} catch (IOException e) {}

		System.out.println("PROCESSED");
		for (Integer[] value : allTypeFile.keySet()) {
			for (i = 0; i != value.length; i++) {
				if (value[i] != (file[i] < 0 ? file[i] & 0xff : file[i])
							&& value[i] != -1) {
					break;
				}
			}
			if (i == value.length) {
				try {
					fileOutputStream.write(allTypeFile.get(value).getBytes());
					fileOutputStream.write('\n');
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}
}

import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import static java.lang.Math.*;

public class Program {
	public static void main(String[] args) {
		Map<String, Integer[]> dictionary;

		Vector<Integer> vectorTextA = new Vector<>();

		Vector<Integer> vectorTextB = new Vector<>();

		double result = 0;


		if (args.length != 2) {
			System.err.println("Error: number arguments!");
			System.exit(-1);
		}

		try {
			BufferedReader textA = new BufferedReader(new FileReader(args[0]));

			BufferedReader textB = new BufferedReader(new FileReader(args[1]));

			BufferedWriter dictionaryFile = new BufferedWriter(new FileWriter("dictionary.txt"));

			dictionary = crateDictionary(textA, textB);

			int size = dictionary.size();

			for(Map.Entry entry: dictionary.entrySet()) {
				size--;
				Integer[] value = (Integer[])entry.getValue();
				if (size != 0) {
					dictionaryFile.write(entry.getKey() + ", ");
				} else {
					dictionaryFile.write(entry.getKey() + " ");
				}
				vectorTextA.add(value[0]);
				vectorTextB.add(value[1]);
			}
			textA.close();
			textB.close();
			dictionaryFile.close();
			result = calculateSimilarity(vectorTextA, vectorTextB);
			System.out.printf("Similarity = %.2f\n", result - (result % 0.01d));
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void addWords(BufferedReader text, int numberText, Map<String, Integer[]> dictionary){
		String line;

		Integer[] countWords;

		try {
			while ((line = text.readLine()) != null) {
				String[] arrWords = line.split(" ");

				for (String word : arrWords) {
					if (word.equals("")) {
						continue;
					}
					word = word.toLowerCase();
					Integer[] tmp = dictionary.get(word);
					if (tmp == null) {
						countWords = new Integer[2];
						countWords[0] = 0;
						countWords[1] = 0;
						countWords[numberText]++;
						dictionary.put(word, countWords);
					} else {
						tmp[numberText]++;
					}
				}
			}
		}catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	public static Map<String, Integer[]> crateDictionary(BufferedReader textA, BufferedReader textB) {
		Map<String, Integer[]> dictionary = new TreeMap<>();

		addWords(textA, 0, dictionary);
		addWords(textB, 1, dictionary);

		return dictionary;
	}

	public static double calculateSimilarity(Vector<Integer> vectorTextA, Vector<Integer> vectorTextB) {
		double numerator = 0;

		double denominator;

		double tmp1 = 0;

		double tmp2 = 0;

		for (int i = 0; i != vectorTextA.size(); i++) {
			numerator += vectorTextA.get(i) * vectorTextB.get(i);
			tmp1 += pow(vectorTextA.get(i), 2);
			tmp2 += pow(vectorTextB.get(i), 2);
		}

		denominator = sqrt(tmp1) * sqrt(tmp2);
		return numerator / denominator;
	}
}
package edu.school21.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {
	private final NumberWorker numberWorker = new NumberWorker();

	@ParameterizedTest
	@ValueSource(ints = { 2, 73, 239, 487 })
	public void isPrimeForPrimes(int numbers) {
		assertTrue(numberWorker.isPrime(numbers));
	}

	@ParameterizedTest
	@ValueSource(ints = { 100, 221, 256, 512})
	public void isPrimeForNotPrimes(int numbers) {
		assertFalse(numberWorker.isPrime(numbers));
	}

	@ParameterizedTest
	@ValueSource(ints = { -2, -1, 0, 1})
	public void isPrimeForIncorrectNumbers(int numbers) {
		assertThrows(IllegalNumberException.class, () -> numberWorker.isPrime(numbers));
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/data.csv")
	public void checkDigitsSum(int numbers, int result) {
		assertEquals(result, numberWorker.digitsSum(numbers));
	}
}

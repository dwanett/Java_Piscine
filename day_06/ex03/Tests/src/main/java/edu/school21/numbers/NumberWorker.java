package edu.school21.numbers;

class IllegalNumberException extends RuntimeException {
	public IllegalNumberException() {
		super("Error number!");
	}
}

public class NumberWorker {

	public boolean isPrime(int number) {

		if (number <= 1) {
			throw new IllegalNumberException();
		}
		for (int i = 2; i < Math.sqrt(number); i++) {
			if (number % i == 0) {
				return false;
			}
		}
		return true;
	}

	public int digitsSum(int number) {
		int res = 0;

		if (number < 0) {
			number *= -1;
		}
		while (number != 0){
			res += number % 10;
			number /= 10;
		}
		return res;
	}
}

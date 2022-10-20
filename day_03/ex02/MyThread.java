import java.util.Vector;

public class MyThread extends Thread {

	private final Vector<Integer> array;

	private static Sum sumAll = new Sum();

	private final int start;

	private final int end;

	private final int numberThread;

	private final int cuntThread;

	private long sum;

	public MyThread(int start, int end, String name, int numberThread, int cuntThread, Vector<Integer> array) {
		this.setName(name);
		this.array = array;
		this.start = start;
		this.end = end;
		this.sum = 0;
		this.numberThread = numberThread;
		this.cuntThread = cuntThread;
	}

	public Sum getAllSum() {
		return sumAll;
	}

	@Override
	public void run() {
		for (int i = start; i <= end; i++) {
			sum += array.get(i);
		}
		sumAll.sumInc(sum);
		System.out.printf("Thread %s: from %d to %d sum is %d\n", this.getName(), start, end, sum);
		if (numberThread == cuntThread) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			System.out.printf("Sum by threads: %d\n", sumAll.getSum());
		}
	}
}

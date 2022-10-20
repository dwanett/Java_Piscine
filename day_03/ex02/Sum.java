public class Sum {
	private static long sum = 0;

	public synchronized void sumInc(long value){
		sum+= value;
	}

	public long getSum() {
		return sum;
	}
}

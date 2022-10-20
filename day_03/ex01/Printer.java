public class Printer {
	private int ink = 1;

	public synchronized void printMessage(String message) throws InterruptedException {
		if (message.equals("Egg")) {
			if (ink < 1) {
				wait();
			}
			ink = 0;
		} else {
			if (ink == 1) {
				wait();
			}
			ink = 1;
		}
		System.out.println(message);
		notify();
	}
}
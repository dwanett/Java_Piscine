public class MyThread extends Thread {

	private final static Printer printer = new Printer();
	private final int count;

	public MyThread(Integer count, String name) {
		this.count = count;
		this.setName(name);
	}

	@Override
	public void run() {
		for (int i = 0; i < this.count; i++) {
			try {
				printer.printMessage(this.getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

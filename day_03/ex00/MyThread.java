public class MyThread extends Thread {

	private final Integer count;

	public MyThread(Integer count, String name) {
		this.count = count;
		this.setName(name);
	}

	public Integer getCount() {
		return count;
	}

	@Override
	public void run() {
		for (int i = 0; i != count; i++) {
			System.out.println(this.getName());
		}
	}
}

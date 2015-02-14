package cms.helpers;

public class ThreadHelper implements Runnable {

	private Thread t;
	
	public void run() {
		
	}
	
	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}
}

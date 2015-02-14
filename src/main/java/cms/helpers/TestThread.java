package cms.helpers;

import cms.control.user.CommandTest;

public class TestThread extends ThreadHelper{

	private boolean is_running;
	private volatile int testloop = -1;
	
	@Override
	public void run() {
		setRunning(true);
		try {
			while (!Thread.currentThread().isInterrupted()) {
				test();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean getRunning(){
		return this.is_running;
	}
	
	public void setRunning(boolean running){
		this.is_running = running;
	}
	
	public void test(){
		while (is_running && (testloop > 0 || testloop == -1)) {
			try {
				CommandTest.testdata();
				if(testloop != -1)
					testloop--;
				else
					Thread.sleep(1000);
			} catch (InterruptedException e) {
				is_running = false;
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public void setLoops(int loops){
		this.testloop = loops;
	}

}

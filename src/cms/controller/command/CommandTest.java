package cms.controller.command;

import cms.controller.LogSystem;
import cms.model.DataStore;

public class CommandTest extends AbstractCommand {
	
	private final static String command_signature = "test";
	private static Thread feed_thread;
	private static boolean is_running;

	@Override
	public void action(String arg) {
		String[] parameters = super.commandParam(arg);
		if (parameters.length > 1) {
			switch (parameters[1]) {
			case "data":
				testdata();
				break;
			case "def":
				define();
				break;
			case "console":
				testconsole();
				break;
			case "log":
				testlog();
				break;
			case "continuous":
				is_running = true;
				testthread();
				break;
			case "stop":
				is_running = false;
				testthread();
			default:
				System.out.println("e>" + "Wrong Parameters");
			}
		} else {
			System.out.println("e>" + "Command Incomplete");
		}

	}

	@Override
	public void define() {
		System.out.println("Parameters: data|def|console|log|continuous|stop");
		System.out.println("data tests data on the levels/graph once.");
		System.out.println("console and log feed 40 lines to console and log respectively.");
		System.out.println("continuous opens a thread sending data every second");
		System.out.println("stop closes the thread sending data every second, if any");
		System.out.println("Tests specific parts of the program");
		
	}
	
	@Override
	public String getCommandSignature(){
		return CommandTest.command_signature;
	}
	
	private static void testdata(){
		LogSystem.log(true, false, "Testing new data");
		DataStore.newData();
	}
	
	private void testconsole(){
		System.out.println("Testing scroll and caret");
		for(int i = 0 ; i < 40 ; i++){
			System.out.println("Line test for caret: " + (i+1));
		}
	}
	
	private void testlog(){
		LogSystem.log(true, false, "Testing scroll and caret");
		for(int i = 0 ; i < 40 ; i++){
			LogSystem.log(true, false, "Line test for caret: " + (i+1));
		}
	}
	
	private void testthread(){
		if(feed_thread == null){
			feed_thread = new Thread(new Runnable() {
				public void run() {
					while (true) {
						try {
							testdata();
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}
				}
			});
		}
		if(is_running)
			feed_thread.start();
		else
			feed_thread.interrupt();
	}

}

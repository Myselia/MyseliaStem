package cms;

import cms.controller.LogSystem;
import cms.helpers.ThreadHelper;
import cms.model.DataFactory;
import cms.model.communication.Server;
import cms.view.ProgramWindow;

public class Main {
	
	public static final int DEFAULT_PORT = 6969; //fya know what i mean
	//Route STD error to console?
	public static boolean REROUTE_ERR = false;
	
	public static void main(String[] args) {
		final Server server = new Server(DEFAULT_PORT, 100);
		
		//Model
		Thread data = new Thread(new Runnable(){
			public void run() {
				DataFactory.build();	
			}
		});
		
		//View
		Thread display = new Thread(new Runnable(){
			public void run() {
				ProgramWindow.init();
				LogSystem.log(true, false, "Log System Started");
				System.out.println("Welcome to the CMS v0.5 alpha");
				System.out.println("Enter 'help' for a list of commands");
			}
		});

		Thread communicator = new Thread(server);
		
		Thread test = new Thread(new Test());

		try {
			data.start();
			Thread.sleep(200);
			display.start();
			Thread.sleep(200);
			//test.start();
			//Thread.sleep(200);
			communicator.start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

class Test extends ThreadHelper {

	public void run() {
		try {
			while (true) {
				DataFactory.newData();
				Thread.sleep(250);
			}
		} catch (InterruptedException e) {
		}
	}

}

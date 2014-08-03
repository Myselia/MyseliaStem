package cms;

import cms.controller.CommandSystem;
import cms.controller.LogSystem;
import cms.model.DataStore;
import cms.model.communication.Server;
import cms.view.ProgramWindow;

public class Main {
	
	public static final int DEFAULT_PORT = 6969; //*wink* *wink* *nudge* *nudge*
	public static boolean REROUTE_ERR = false;	//Error Re-Routing to CMS Console
	
	public static void main(String[] args) {
		final Server server = new Server(DEFAULT_PORT, 100);
		
		loadCommands();
		
		//Model
		Thread data = new Thread(new Runnable(){
			public void run() {
				DataStore.build();	
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

		try {
			data.start();
			Thread.sleep(200);
			display.start();
			Thread.sleep(200);
			communicator.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void loadCommands(){
		try{
			CommandSystem.setClasses("cms.controller.command");
		}catch(Exception e){
			System.out.println("b>" + "&>" + "cms.controller.CommandSystem.setClasses(String) called in");
			System.out.println("b>" + "&>" + "cms.view.panel.ConsoleDisplay.ConsoleDisplay() threw an exception.");
			System.out.println("b>" + "&>" + "Force kill the app and investigate.");
			e.printStackTrace();
		}
	}
}

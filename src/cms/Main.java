package cms;

import cms.communication.Broadcast;
import cms.communication.Server;
import cms.control.CommandSystem;
import cms.databank.OverLord;
import cms.display.ProgramWindow;
import cms.monitoring.LogSystem;

public class Main {
	
	public static final int DEFAULT_PORT = 6969; //*wink* *wink* *nudge* *nudge*
	public static boolean REROUTE_ERR = false;	//Error Re-Routing to CMS Console
	
	private static Broadcast bcastRunnable;
	private static Server serverRunnable;
	
	public static Thread bCastCommunicator;
	private static Thread communicator;
	private static Thread data;
	private static Thread display;
	
	public static void main(String[] args) {
		bcastRunnable = new Broadcast();
		serverRunnable = new Server(DEFAULT_PORT, 100);
		loadCommands();
		
		//Model
		data = new Thread(new Runnable(){
			public void run() {
				OverLord.build();	
			}
		});
		
		//View
		display = new Thread(new Runnable(){
			public void run() {
				ProgramWindow.init();
				LogSystem.log(true, false, "Log System Started");
				System.out.println("Welcome to the CMS v0.5 alpha");
				System.out.println("Enter 'help' for a list of commands");
			}
		});

		communicator = new Thread(serverRunnable);
		//bCastCommunicator = new Thread(bcastRunnable);

		try {
			data.start();
			Thread.sleep(2000);
			display.start();
			Thread.sleep(2000);
			communicator.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void loadCommands(){
		try{
			CommandSystem.setClasses("cms.control.user");
		}catch(Exception e){
			System.out.println("b>" + "&>" + "cms.controller.CommandSystem.setClasses(String) called in");
			System.out.println("b>" + "&>" + "cms.view.panel.ConsoleDisplay.ConsoleDisplay() threw an exception.");
			System.out.println("b>" + "!> " + "Exception thrown by:" + e.getClass().getCanonicalName());
			System.out.println("Please force kill the application and investigate.");
			e.printStackTrace();
		}
	}

	public static void startBCastThread(Runnable instance, Thread thread) {
		thread = new Thread(instance);
		setbCastCommunicator(thread);
		thread.start();
	}

	public static Broadcast getBcastRunnable() {
		return bcastRunnable;
	}

	public static Thread getbCastCommunicator() {
		return bCastCommunicator;
	}

	public static void setbCastCommunicator(Thread bCastCommunicator) {
		Main.bCastCommunicator = bCastCommunicator;
	}
	

}

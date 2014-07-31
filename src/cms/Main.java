package cms;

import cms.controller.LogSystem;
import cms.model.DataFactory;
import cms.model.communication.Server;
import cms.view.ProgramWindow;

public class Main {
	
	public static final int DEFAULT_PORT = 6969; //fya know what i mean
	
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
		
		Thread communicator = new Thread(new Runnable(){
			public void run() {
				try{
				server.startRunning();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		});
		
		data.start();
		display.start();
		communicator.start();
		
	}
}

package cms;

import cms.controller.LogSystem;
import cms.model.DataFactory;
import cms.view.ProgramWindow;

public class Main {
	public static void main(String[] args) {
		
		DataFactory.build();
		
		Thread display = new Thread(new Runnable(){
			public void run() {
				ProgramWindow.init();
				LogSystem.log(true, false, "Log Started");
				System.out.println("Console Started");	
			}
		});
		
		Thread communicator = new Thread(new Runnable(){
			public void run() {
				
				
			}
		});
		
		display.start();
		communicator.start();
		
	}
}

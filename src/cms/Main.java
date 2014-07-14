package cms;

import cms.controller.LogSystem;
import cms.model.DataFactory;
import cms.view.ProgramWindow;

public class Main {
	public static void main(String[] args) {
		
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
		
		//Com
		Thread communicator = new Thread(new Runnable(){
			public void run() {
				
				
			}
		});
		
		data.start();
		display.start();
		communicator.start();
		
	}
}

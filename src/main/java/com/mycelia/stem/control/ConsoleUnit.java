package com.mycelia.stem.control;

import java.util.Scanner;

public class ConsoleUnit implements Runnable{
	
	private final String cmdpckg = "com.mycelia.stem.control.commands";
	
	Scanner userIn = new Scanner(System.in);
	
	public ConsoleUnit(){
		try {
			CommandSystem.setClasses(cmdpckg); 
		} catch (Exception e) {
			System.err.println("ConsoleUnit CommandSystem parsing error.");
		}
	}

	@Override
	public void run() {
		while(true){
			if(userIn.hasNextLine()){
				CommandSystem.index_reset();
				String make = userIn.nextLine();
				if (!make.equals("")) {
					CommandSystem.command(make);
				}
			}
		}
	}

}

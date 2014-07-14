package cms.controller.command;

import java.util.Random;

import cms.controller.LogSystem;
import cms.model.DataFactory;

public class CommandTest implements Command {

	@Override
	public void action(String[] parameters) {

		if (parameters.length > 1) {
			switch (parameters[1]) {
			case "log":
				testlog();
				break;
			case "console":
				testconsole();
				break;
			case "communications":
				testcommunications();
				break;
			case "data":
				testdata();
				break;
			case "three":
				testthree();
				break;
			case "def":
				define();
				break;
			}
		} else {
			System.out.println("e>Test Command Incomplete");
		}

	}

	@Override
	public void define() {
		System.out.println("Parameters: 'log' 'console' 'three'");
		System.out.println("Tests specific parts of the program");
		System.out.println("Test are to be implemented in the source code");
		
	}
	
	private void testdata(){
		LogSystem.log(true, false, "Testing new data");
		DataFactory.newData();
		
	}

	private void testlog() {
		System.out.println("Testing Log");
		Random random = new Random();
		int max = random.nextInt() % 5;
		int build = max + 10;
		for (int i = 0; i < build; i++) {
			LogSystem.log(true, false, "Log Test : " + i + "/" + build + " MAX: " + max);
		}
	}

	private void testconsole() {
		LogSystem.log(true, false, "Testing Console");
		Random random = new Random();
		int max = random.nextInt() % 5;
		int build = max + 10;
		for (int i = 0; i < build; i++) {
			System.out.println("Console Test : " + i + "/" + build + " MAX: " + max);
		}
	}
	
	private void testcommunications(){
		for(int i = 0; i < 10; i++){
			testlog();
			testconsole();
		}
	}

	private void testthree() {
		System.out.println("s>Void test");
	}

}

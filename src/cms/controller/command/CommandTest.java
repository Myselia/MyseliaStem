package cms.controller.command;

import java.util.Random;

import cms.controller.LogSystem;
import cms.model.DataFactory;

public class CommandTest extends AbstractCommand {
	
	private final static String command_signature = "test";

	@Override
	public void action(String arg) {
		String[] parameters = super.commandParam(arg);
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
			default:
				System.out.println("e>Test Command: Wrong Parameter");
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
	
	@Override
	public String getCommandSignature(){
		return CommandTest.command_signature;
	}
	
	private static void testdata(){
		LogSystem.log(true, false, "Testing new data");
		DataFactory.newData();
	}

	private static void testlog() {
		System.out.println("Testing Log");
		Random random = new Random();
		int max = random.nextInt() % 5;
		int build = max + 10;
		for (int i = 0; i < build; i++) {
			LogSystem.log(true, false, "Log Test : " + i + "/" + build + " MAX: " + max);
		}
	}

	private static void testconsole() {
		LogSystem.log(true, false, "Testing Console");
		Random random = new Random();
		int max = random.nextInt() % 5;
		int build = max + 10;
		for (int i = 0; i < build; i++) {
			System.out.println("Console Test : " + i + "/" + build + " MAX: " + max);
		}
	}
	
	private static void testcommunications(){
		for(int i = 0; i < 10; i++){
			testlog();
			testconsole();
		}
	}

	private static void testthree() {
		System.out.println("s>Void test");
	}

}

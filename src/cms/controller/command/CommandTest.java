package cms.controller.command;

import cms.controller.LogSystem;
import cms.model.DataStore;

public class CommandTest extends AbstractCommand {
	
	private final static String command_signature = "test";

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
			default:
				System.out.println("e>" + "Wrong Parameters");
			}
		} else {
			System.out.println("e>" + "Command Incomplete");
		}

	}

	@Override
	public void define() {
		System.out.println("Parameters: 'data'");
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
	
	public void testconsole(){
		System.out.println("Testing scroll and caret");
		for(int i = 0 ; i < 40 ; i++){
			System.out.println("Line test for caret: " + (i+1));
		}
	}
	
	public void testlog(){
		LogSystem.log(true, false, "Testing scroll and caret");
		for(int i = 0 ; i < 40 ; i++){
			LogSystem.log(true, false, "Line test for caret: " + (i+1));
		}
	}

}

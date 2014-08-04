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

}

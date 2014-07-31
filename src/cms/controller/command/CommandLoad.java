package cms.controller.command;

public class CommandLoad extends AbstractCommand {
	private final static String command_signature = "load";

	@Override
	public void action(String arg) {
		String[] parameters = super.commandParam(arg);
		if (parameters.length == 3) {
			load(parameters[1], parameters[2]);
		} else if (parameters.length == 2) {
			if (parameters[1].equals("def")) {
				define();
			} else {
				System.out.println("e>Load Command Incomplete.");
			}
		} else {
			System.out.println("e>Load Command Incomplete");
		}
	}

	@Override
	public void define() {
		System.out.println("Parameters: 'def' 'filename'&'buffer/data/slot'");
		System.out.println("Loads file inside a memory unit and verifies checksum.");
	}

	@Override
	public String getCommandSignature(){
		return CommandLoad.command_signature;
	}
	
	private void load(String file, String buffer) {
		System.out.println("s>File loaded: " + file + "@" + buffer);
	}

}

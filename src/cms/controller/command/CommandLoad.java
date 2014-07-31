package cms.controller.command;

public class CommandLoad extends AbstractCommand {
	private final static String command_signature = "load";

	@Override
	public void action(String arg) {
		String[] parameters = super.commandParam(arg);
		switch(parameters.length){
		case 3:
			load(parameters[1],parameters[2]);
			break;
		case 2:	
			if(parameters[1].equals("def")){
				define();
				break;
			}
		default:
			System.out.println("e>Load Command Incomplete");
			break;
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
	
	//TODO
	private void load(String file, String buffer) {
		System.out.println("s>File loaded: " + file + "@" + buffer);
	}

}

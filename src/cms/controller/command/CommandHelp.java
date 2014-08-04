package cms.controller.command;

public class CommandHelp extends AbstractCommand {
	private final static String command_signature = "help";

	@Override
	public void action(String arg) {
		String[] parameters = super.commandParam(arg);
		if(parameters.length > 1){
			if(parameters[1].equals("def")){
				define();
			} else {
				System.out.println("e>" + "Wrong Parameters");
			}
		} else {
			//TODO: replace with proper loaded commands list
			System.out.println("help - seek - call - test - exit");
		}
	}

	@Override
	public void define() {
		System.out.println("Prints a list of all commands that have been recongnised.");
	}
	
	@Override
	public String getCommandSignature(){
		return CommandHelp.command_signature;
	}

}

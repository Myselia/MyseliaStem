package cms.controller.command;

public class CommandHelp extends AbstractCommand {
	private final static String command_signature = "help";

	@Override
	public void action(String arg) {
		String[] parameters = super.commandParam(arg);
		if(parameters.length > 1){
			if(parameters[1].equals("def")){
				define();
			}
		} else {
			System.out.println("call - load - make - test - help - send - pack - note - exit");
		}
	}

	@Override
	public void define() {
		System.out.println("Prints a list of commands.");
	}
	
	@Override
	public String getCommandSignature(){
		return CommandHelp.command_signature;
	}

}

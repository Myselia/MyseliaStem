package cms.controller.command;

import cms.controller.ReflectionCommand;

public class CommandHelp extends AbstractCommand {
	private final static String command_signature = "help";
	private static String[] command_list;

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
			System.out.println(command_list);
		}
	}
	
	public static void setCommands(ReflectionCommand[] commandClasses){
		command_list = new String[commandClasses.length - 1];
		for(int i = 0; i < commandClasses.length ; i++){
			command_list[i] = commandClasses[i].getCommand();
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

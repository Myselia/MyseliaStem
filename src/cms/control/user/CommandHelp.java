package cms.control.user;


public class CommandHelp extends AbstractCommand {
	private final static String command_signature = "help";
	private static String[] command_list = null;

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
			for(int i = 0; i < command_list.length ; i++){
				if(i < command_list.length - 1)
					System.out.print(command_list[i] + " - ");
				else
					System.out.println(command_list[i]);
			}	
		}
	}
	
	public static void setCommands(ReflectionCommand[] commandClasses){
		command_list = new String[commandClasses.length];
		for(int j = 0; j < command_list.length ; j++){
			command_list[j] = commandClasses[j].getCommand();
		}
	}

	@Override
	public void define() {
		System.out.println("Prints a list of all commands that have been recognised.");
	}
	
	@Override
	public String getCommandSignature(){
		return CommandHelp.command_signature;
	}

}

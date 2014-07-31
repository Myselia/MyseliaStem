package cms.controller.command;

public class CommandMake extends AbstractCommand{
	private final static String command_signature = "make";
	
	@Override
	public void action(String arg) {
		String[] parameters = super.commandParam(arg);
		switch(parameters.length){
		case 2:
			if(parameters[1].equals("def")){
				define();
				break;
			}else if (parameters[1].equals("buffer")){}
		default:
			System.out.println("e>Make Command Incomplete");
		}
	}
	
	@Override
	public String getCommandSignature(){
		return CommandMake.command_signature;
	}

	@Override
	public void define() {
		System.out.println("Parameters: id");
		System.out.println("Calls node's setup methods.");
	}

}

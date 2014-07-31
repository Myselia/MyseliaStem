package cms.controller.command;

public class CommandPack extends AbstractCommand {

	private final static String command_signature = "pack";
	
	@Override
	public void action(String arg) {
		String[] parameters = super.commandParam(arg);
		if (parameters.length == 2) {
			if (parameters[1].equals("def")) {
				define();
			} else if (parameters[1].equals("now")){
				System.out.println("e>Currently no implementation of Pack is available.");
				pack();
			} else {
				System.out.println("e>Load Command Incomplete.");
			}
		}
		
	}
	
	public void pack(){}

	@Override
	public void define() {
		System.out.println("Packs file loaded in slot");
		
	}
	
	@Override
	public String getCommandSignature(){
		return CommandPack.command_signature;
	}

}

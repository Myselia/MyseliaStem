package cms.controller.command;

public class CommandSend extends AbstractCommand {

	private final static String command_signature = "send";
	
	@Override
	public void action(String arg) {
		String[] parameters = super.commandParam(arg);
		if (parameters.length == 2) {
			if (parameters[1].equals("def")) {
				define();
			} else if (parameters[1].equals("now")){
				send();
			} else {
				System.out.println("e>Load Command Incomplete.");
			}
		}
		
	}
	
	public void send(){}

	@Override
	public void define() {
		System.out.println("Sends file once it's packed.");
		
	}
	
	@Override
	public String getCommandSignature(){
		return CommandSend.command_signature;
	}
}

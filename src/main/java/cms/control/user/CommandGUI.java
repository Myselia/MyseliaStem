package cms.control.user;

import cms.Main;

public class CommandGUI extends AbstractCommand {
	private final static String command_signature = "gui";
	
	@Override
	public void action(String arg) {
		String[] parameters = super.commandParam(arg);
		switch(parameters.length){
		case 1:
			Main.toggleGUI();
			break;
		case 2:
			if(parameters[1].equals("def")){
				define();
			}else{
				System.out.println("e>" + "Wrong Parameters");
			}
			break;
		default:
			System.out.println("e>" + "Wrong Parameters");
			break;
		}
	}

	@Override
	public void define() {
		System.out.println("Starts or kill the GUI.");
		System.out.println("Automatically take the appropriate action depending if GUI is running or not.");
	}
	@Override
	public String getCommandSignature(){
		return CommandGUI.command_signature;
	}

}

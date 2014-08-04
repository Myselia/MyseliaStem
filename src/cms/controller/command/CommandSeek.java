package cms.controller.command;

import cms.Main;

public class CommandSeek extends AbstractCommand {
	private final static String command_signature = "seek";

	@Override
	public void action(String arg) {
		String[] parameters = super.commandParam(arg);
		if(parameters.length > 1){
			if(parameters[1].equals("def")){
				define();
			} else if(parameters[1].equals("on") || parameters[1].equals("start")){
				turnon();
			} else if(parameters[1].equals("off") || parameters[1].equals("stop")){
				turnoff();
			} else {
				System.out.println("e>" + "Wrong Parameters");
			}
		} else {
			System.out.println("e>" + "Command Incomplete");
		}
	}

	private void turnon() {

		Main.startBCastThread(Main.getBcastRunnable(), Main.getbCastCommunicator());
		System.out.println("Seek mode activated");
	}

	private void turnoff(){
		Main.getbCastCommunicator().interrupt();
		System.out.println("Seek mode deactivated");
	}

	@Override
	public void define() {
		System.out.println("Turns on seek mode: broadcasts IP address and port number.");
		System.out.println("Thread starts upon 'on' command, ends upon 'off' command.");
	}
	
	@Override
	public String getCommandSignature(){
		return CommandSeek.command_signature;
	}

}

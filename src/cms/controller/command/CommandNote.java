package cms.controller.command;

import cms.controller.LogSystem;

public class CommandNote extends AbstractCommand {

	private final static String command_signature = "note";
	
	@Override
	public void action(String arg) {
		String[] parameters = super.commandParam(arg);
		if (parameters.length > 1) {
			if (parameters[1].equals("def")) {
				define();
			} else if (parameters[1].equals("log")){
				note(parameters);
			} else {
				System.out.println("e>Load Command Incomplete.");
			}
		}
		
	}
	
	public void note(String[] note){
		String concat = "";
		for(int i = 2; i < note.length; i++){
			concat += note[i] + " ";
		}
		LogSystem.log(true, false, concat);
	}
	
	@Override
	public String getCommandSignature(){
		return CommandNote.command_signature;
	}

	@Override
	public void define() {
		System.out.println("Notes whatever is after the log.");
		
	}

}

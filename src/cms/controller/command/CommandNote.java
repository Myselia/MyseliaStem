package cms.controller.command;

import cms.controller.LogSystem;

public class CommandNote implements Command {

	@Override
	public void action(String[] parameters) {
		if (parameters.length >= 2) {
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
	public void define() {
		System.out.println("Notes whatever is after the log.");
		
	}

}

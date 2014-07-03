package cms.controller.command;

public class CommandHelp implements Command {

	@Override
	public void action(String[] parameters) {
		if(parameters.length > 1){
			if(parameters[1].equals("def")){
				define();
			}
		} else {
			System.out.println("call - load - make - test - help - exit");
		}
	}

	@Override
	public void define() {
		System.out.println("Prints a list of commands.");
	}

}

package cms.controller.command;

public class CommandMake implements Command{

	@Override
	public void action(String[] parameters) {
		if(parameters.length == 2){
			if(parameters[1].equals("def")){
				define();
			} else if (parameters[1].equals("buffer")){
				System.out.println("e>Make Command Incomplete");
			}
		} else {
			System.out.println("e>Make Command Incomplete");
		}
	}

	@Override
	public void define() {
		System.out.println("Parameters: 'def' 'slot' ");
		System.out.println("Sends loaded buffer to called node.");
		System.out.println("Calls node's setup methods.");
	}

}

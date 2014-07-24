package cms.controller.command;

public class CommandPack implements Command {

	@Override
	public void action(String[] parameters) {
		if (parameters.length == 2) {
			if (parameters[1].equals("def")) {
				define();
			} else if (parameters[1].equals("now")){
				pack();
			} else {
				System.out.println("e>Load Command Incomplete.");
			}
		}
		
	}
	
	public void pack(){
		
	}

	@Override
	public void define() {
		System.out.println("Packs file loaded in slot");
		
	}

}

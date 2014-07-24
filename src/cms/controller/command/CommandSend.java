package cms.controller.command;

public class CommandSend implements Command {

	@Override
	public void action(String[] parameters) {
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
	
	public void send(){
		
	}

	@Override
	public void define() {
		System.out.println("Sends file once it's packed.");
		
	}
}

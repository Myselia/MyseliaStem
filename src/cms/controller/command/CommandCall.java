package cms.controller.command;
import cms.view.AddressBar;

public class CommandCall extends AbstractCommand{
	private final static String command_signature = "call";
	
	@Override
	public void action(String arg){
		String[] parameters = super.commandParam(arg);
		if(parameters.length > 1){
			if(parameters[1].equals("def")){
				define();
			}else{
				try{
					int call = Integer.parseInt(parameters[1]);
					AddressBar.unselectLast(AddressBar.nodeButton(call));
					AddressBar.nodeButton(call).select(true);
				}catch(NumberFormatException e){
					AddressBar.unselectLast(null);
					System.out.println("e>" + "Wrong Parameters");
				}catch(Exception e){
					System.out.println("e>" + "No such node ID");
				}
			}	
		}else{
			System.out.println("e>" + "Command Incomplete");
		}		
	}

	@Override
	public void define() {
		System.out.println("Selects a node in the grid based on id number.");
		System.out.println("Parameters: # or 'void'. Void deselects all nodes.");
	}
	
	@Override
	public String getCommandSignature(){
		return CommandCall.command_signature;
	}

}

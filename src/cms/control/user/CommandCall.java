package cms.control.user;
import cms.control.methods.CMSCommand;
import cms.display.info.AddressPanel;

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
					CMSCommand.call(parameters[1]);
				}catch(NumberFormatException e){
					AddressPanel.unselectLast(null);
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

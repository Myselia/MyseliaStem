package cms.controller.command;
import cms.view.AddressBar;

public class CommandCall extends AbstractCommand{
	private final static String command_signature = "call";
	
	@Override
	public void action(String arg){
		String[] parameters = super.commandParam(arg);
		int call;
		if(parameters.length == 2){
			if(parameters[1].equals("def")){
				define();
			}
			try{
				call = Integer.parseInt(parameters[1]);
			}catch(Exception e){
				call = -1;
			}
			
			if(call == -1){
				AddressBar.unselectLast(null);
			} else {
				try{
					AddressBar.unselectLast(AddressBar.nodeButton(call));
					AddressBar.nodeButton(call).select(true);
				} catch(Exception e){
					System.out.println("e>No such node ID");
				}
			}
		} else {
			System.out.println("e>Call Command Incomplete");
		}		
	}

	@Override
	public void define() {
		System.out.println("Parameters: 'def' '#' 'void' ");
		System.out.println("Selects a node in the grid.");
		
	}
	
	@Override
	public String getCommandSignature(){
		return CommandCall.command_signature;
	}

}

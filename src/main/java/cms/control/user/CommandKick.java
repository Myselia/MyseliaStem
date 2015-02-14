package cms.control.user;

import cms.control.methods.CMSCommand;

public class CommandKick extends AbstractCommand{
	final static String command_signature = "kick";
	
	@Override
	public void action(String arg){
		String[] parameters = super.commandParam(arg);
		if(parameters.length > 1){
			if(isInteger(parameters[1])){
				int coreID = Integer.parseInt(parameters[1]);
				
				CMSCommand.kick(coreID);
				
			} else {
				System.out.println("e>" + "Not Supported");
			}
		}else{
			System.out.println("e>" + "Command Incomplete");
		}		
	}

	@Override
	public void define() {
		System.out.println("Kicks a node off of the cluster, the node may reconnect but it will be assigned a new ID.");
		System.out.println("Parameters: 'VAR: Node Session ID' ");
	}
	
	@Override
	public String getCommandSignature(){
		return CommandKick.command_signature;
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}

}

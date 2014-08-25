package cms.controller.command;

import cms.model.DataStore;

public class CommandKick extends AbstractCommand{
	final static String command_signature = "kick";
	
	@Override
	public void action(String arg){
		String[] parameters = super.commandParam(arg);
		if(parameters.length > 1){
			if(isInteger(parameters[1])){
				int param = Integer.parseInt(parameters[1]);
				
				kick(param);
				
			} else {
				System.out.println("e>" + "Not Supported");
			}
		}else{
			System.out.println("e>" + "Command Incomplete");
		}		
	}
	
	private void kick(int id){
		if (DataStore.isNodeAcive(id)) {
			System.out.println("Kicking node " + id);
			DataStore.removeNode(id);
		} else {
			System.out.println("Node " + id + " is not available for modification");
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

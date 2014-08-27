package cms.control.user;

import cms.control.methods.CMSCommand;

public class CommandMake extends AbstractCommand{
	final static String command_signature = "make";
	
	@Override
	public void action(String arg){
		String[] parameters = super.commandParam(arg);
		if(parameters.length > 1){
			if(parameters[1].equals("def")){
				define();
			}else if(parameters[1].equals("node")){
				CMSCommand.make(0);
			}else if(parameters[1].equals("CMS")){
				CMSCommand.make(1);
			}else if(parameters[1].equals("AMS")){
				CMSCommand.make(2);
			}else if(parameters[1].equals("DB")){
				CMSCommand.make(3);
			}else {
				System.out.println("e>" + "Not Supported");
			}
		}else{
			System.out.println("e>" + "Command Incomplete");
		}		
	}
	
	@Override
	public void define() {
		System.out.println("Changes the type of a node in the cluster.");
		System.out.println("Parameters: 'CMS', 'AMS', 'DB', or 'node'.");
	}
	
	@Override
	public String getCommandSignature(){
		return CommandMake.command_signature;
	}

}

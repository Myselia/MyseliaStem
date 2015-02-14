package cms.control.user;

import cms.control.methods.CMSCommand;

public class CommandDBConn extends AbstractCommand{
	final static String command_signature = "dbconn";
	
	@Override
	public void action(String arg){
		String[] parameters = super.commandParam(arg);
		if (parameters.length > 1) {
			if(parameters[1].equals("def")){
				define();
			} else if(parameters[1].equals("start")){
				CMSCommand.dbconnections(true);
			} else if(parameters[1].equals("stop")){
				CMSCommand.dbconnections(false);
			} else {
				System.out.println("e>" + "Wrong Parameters");
			}
		} else {
			System.out.println("e>" + "Command Incomplete");
		}	
	}

	@Override
	public void define() {
		System.out.println("Connects to databases that are loaded in the system");
		System.out.println("Parameters: 'start' all database connections are started");
		System.out.println("Parameters: 'stop' all database connections are closed");
	}
	
	@Override
	public String getCommandSignature(){
		return CommandDBConn.command_signature;
	}

}

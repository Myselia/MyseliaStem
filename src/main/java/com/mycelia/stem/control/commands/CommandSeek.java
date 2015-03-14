package com.mycelia.stem.control.commands;

import cms.control.methods.CMSCommand;

public class CommandSeek extends AbstractCommand {
	private final static String command_signature = "seek";

	@Override
	public void action(String arg) {
		String[] parameters = super.commandParam(arg);
		if(parameters.length > 1){
			if(parameters[1].equals("def")){
				define();
			} else if(parameters[1].equals("on") || parameters[1].equals("start")){
				CMSCommand.seek(true);
			} else if(parameters[1].equals("off") || parameters[1].equals("stop")){
				CMSCommand.seek(false);
			} else {
				System.out.println("wrong parameters");
			}
		} else {
			System.out.println("command incomplete");
		}
	}


	@Override
	public void define() {
		System.out.println("on / start : turns on seek mode: broadcasts IP address and port number");
		System.out.println("off / stop : turn seek mode off");
	}
	
	@Override
	public String getCommandSignature(){
		return CommandSeek.command_signature;
	}

}

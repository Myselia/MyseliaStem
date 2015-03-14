package com.mycelia.stem.control.commands;

public abstract class AbstractCommand implements Command{
	private final static String command_signature = null;
	
	public abstract void action(String arg);
	public abstract void define();
	
	public String getCommandSignature(){
		return AbstractCommand.command_signature;
	}
	
	public static String[] commandParam(String command){
		command = command.trim();
		return command.split(" ");
	}
}


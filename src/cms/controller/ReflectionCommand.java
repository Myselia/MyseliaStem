package cms.controller;

import java.lang.reflect.Method;

import cms.controller.command.Command;

public class ReflectionCommand {
	private Class<Command> c;
	private Method[] methods;
	private String command_signature;
	
	public ReflectionCommand(Class<Command> c, String command, Method ... methods){
		this.c = c;
		this.command_signature = command;
		this.methods = methods;
	}

	public Class<Command> getClassField(){
		return this.c;
	}
	
	public Method[] getMethods(){
		return this.methods;
	}
	
	public String getCommand(){
		return this.command_signature;
	}
	
	//Mainly for debug
	public String toString(){
		return "class: " + c + " methods: " + methods + " command: " + command_signature;
	}
}

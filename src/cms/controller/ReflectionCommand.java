package cms.controller;

import java.lang.reflect.Method;

public class ReflectionCommand {
	private @SuppressWarnings("rawtypes")Class c;
	private Method[] methods;
	private String command_signature;
	
	@SuppressWarnings("rawtypes")
	public ReflectionCommand(Class c, String command, Method ... methods){
		this.c = c;
		this.command_signature = command;
		this.methods = methods;
	}
	
	@SuppressWarnings("rawtypes")
	public Class getClassField(){
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

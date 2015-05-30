package com.myselia.stem.communication.topology;

import com.myselia.javacommon.constants.opcode.ComponentType;
import com.myselia.stem.communication.handlers.ComponentHandlerBase;

public class ComponentPointer {
	private ComponentType type;
	private String componentid;
	private ComponentHandlerBase handler;
	
	public ComponentPointer(ComponentType type, String componentid, ComponentHandlerBase handler){
		this.type = type;
		this.componentid = componentid;
		this.handler = handler;
	}
	
	public void setHandler(ComponentHandlerBase handler){
		this.handler = handler;
	}
	
	public ComponentType getType(){
		return type;
	}
	
	public String getID(){
		return componentid;
	}
	
	public ComponentHandlerBase getHandler(){
		return handler;
	}

}

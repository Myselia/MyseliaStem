package com.myselia.stem.communication.states;

import java.io.IOException;

import com.myselia.stem.communication.StemClientSession;
import com.myselia.stem.communication.handlers.ComponentHandlerBase;

public class KickedConnectionState implements ConnectionState{

	private ComponentHandlerBase handler;
	private StemClientSession session;
	
	@Override
	public void primeConnectionState(StemClientSession session) {
		this.session = session;
	}
	
	@Override
	public void process() throws IOException {
		handler.handleComponent();
	}

	@Override
	public ComponentHandlerBase getHandler() {
		return handler;
	}

	@Override
	public void setHandler(ComponentHandlerBase handler) {
		this.handler = handler;
	}
	
	public String toString() {
		return "DISCONNECTED STATE!!";
	}

}

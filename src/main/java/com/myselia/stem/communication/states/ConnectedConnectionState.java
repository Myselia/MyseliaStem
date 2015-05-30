package com.myselia.stem.communication.states;

import java.io.IOException;

import com.myselia.stem.communication.StemClientSession;
import com.myselia.stem.communication.handlers.ComponentHandlerBase;

public class ConnectedConnectionState implements ConnectionState {

	private ComponentHandlerBase handler;
	
	@Override
	public void primeConnectionState(StemClientSession session) {
		this.handler = session.getComponentHandler();
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
		return "CONNECTED STATE!!";
	}

}

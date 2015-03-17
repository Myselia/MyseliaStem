package com.mycelia.stem.communication.states;

import java.io.IOException;

import com.mycelia.stem.communication.StemClientSession;
import com.mycelia.stem.communication.handlers.NetworkComponentHandlerBase;

public class KickedConnectionState implements IConnectionState{

	private NetworkComponentHandlerBase handler;
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
	public NetworkComponentHandlerBase getHandler() {
		return handler;
	}

	@Override
	public void setHandler(NetworkComponentHandlerBase handler) {
		this.handler = handler;
	}
	
	public String toString() {
		return "DISCONNECTED STATE!!";
	}

}

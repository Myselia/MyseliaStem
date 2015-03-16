package com.mycelia.stem.communication.states;

import java.net.SocketException;

import com.mycelia.stem.communication.StemClientSession;
import com.mycelia.stem.communication.handlers.NetworkComponentHandlerBase;

public class ConnectedConnectionState implements IConnectionState {

	private NetworkComponentHandlerBase handler;
	private StemClientSession session;
	
	@Override
	public void primeConnectionState(StemClientSession session) {
		this.session = session;
	}
	
	@Override
	public void process() throws SocketException {
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
		return "CONNECTED STATE!!";
	}

}

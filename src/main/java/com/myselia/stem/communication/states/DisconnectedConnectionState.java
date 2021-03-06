package com.myselia.stem.communication.states;

import com.myselia.javacommon.communication.units.Transmission;
import com.myselia.stem.communication.StemClientSession;
import com.myselia.stem.communication.handlers.ComponentHandlerBase;

public class DisconnectedConnectionState implements ConnectionState {

	private ComponentHandlerBase handler;
	private StemClientSession session;
	
	@Override
	public void primeConnectionState(StemClientSession session) {
		this.session = session;
	}
	
	@Override
	public void process(Transmission t) {
		
		if (session.componentAttached)
			session.setConnectionState(session.getStateContainer().getConnectedState());
		else 
			System.out.println("[StemClientSession] : Waiting on reconnect from: " + handler.getCertificate());
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

package com.myselia.stem.communication.states;

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
	public void process() {
		
		if (session.componentAttached)
			session.setConnectionState(session.getStateContainer().getConnectedState());
		else 
			System.out.println("Waiting for re-connect");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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

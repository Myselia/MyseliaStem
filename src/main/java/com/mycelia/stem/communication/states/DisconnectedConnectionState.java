package com.mycelia.stem.communication.states;

import com.mycelia.stem.communication.StemClientSession;
import com.mycelia.stem.communication.handlers.NetworkComponentHandlerBase;

public class DisconnectedConnectionState implements IConnectionState {

	private NetworkComponentHandlerBase handler;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

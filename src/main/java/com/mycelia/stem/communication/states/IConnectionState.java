package com.mycelia.stem.communication.states;

import java.net.SocketException;

import com.mycelia.stem.communication.StemClientSession;
import com.mycelia.stem.communication.handlers.NetworkComponentHandlerBase;

public interface IConnectionState {
	
	enum stateID {
		CONNECTED,
		DISCONNECTED,
		KICKED,
		HANDSHAKE_PENDING
	}
	public void primeConnectionState(StemClientSession session);
	public void process() throws SocketException;
	public NetworkComponentHandlerBase getHandler();
	public void setHandler(NetworkComponentHandlerBase handler);
	
}

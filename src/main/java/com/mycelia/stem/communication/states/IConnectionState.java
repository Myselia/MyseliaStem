package com.mycelia.stem.communication.states;

import java.io.IOException;

import com.mycelia.stem.communication.StemClientSession;
import com.mycelia.stem.communication.handlers.NetworkComponentHandlerBase;

public interface IConnectionState {
	
	public void primeConnectionState(StemClientSession session);
	public void process() throws IOException;
	public NetworkComponentHandlerBase getHandler();
	public void setHandler(NetworkComponentHandlerBase handler);
	
}

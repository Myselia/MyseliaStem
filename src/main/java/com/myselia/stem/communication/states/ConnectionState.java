package com.myselia.stem.communication.states;

import java.io.IOException;

import com.myselia.stem.communication.StemClientSession;
import com.myselia.stem.communication.handlers.ComponentHandlerBase;

public interface ConnectionState {
	
	public void primeConnectionState(StemClientSession session);
	public void process() throws IOException;
	public ComponentHandlerBase getHandler();
	public void setHandler(ComponentHandlerBase handler);
	
}

package com.mycelia.stem.communication;

import com.mycelia.stem.communication.states.ConnectedConnectionState;
import com.mycelia.stem.communication.states.DisconnectedConnectionState;
import com.mycelia.stem.communication.states.HandshakeConnectionState;
import com.mycelia.stem.communication.states.ConnectionState;
import com.mycelia.stem.communication.states.HttpHandshakeConnectionState;
import com.mycelia.stem.communication.states.KickedConnectionState;

public class ConnectionStateContainer {

	private ConnectionState connectedState;
	private ConnectionState disconnectedState;
	private ConnectionState kickedState;
	private ConnectionState handshakeState;
	
	public ConnectionStateContainer(StemClientSession session) {
		initializeConnectionStates(session);
	}
	
	public ConnectionState getConnectedState() {
		return connectedState;
	}

	public void setConnectedState(ConnectionState connectedState) {
		this.connectedState = connectedState;
	}

	public ConnectionState getDisconnectedState() {
		return disconnectedState;
	}

	public void setDisconnectedState(ConnectionState disconnectedState) {
		this.disconnectedState = disconnectedState;
	}

	public ConnectionState getKickedState() {
		return kickedState;
	}

	public void setKickedState(ConnectionState kickedState) {
		this.kickedState = kickedState;
	}

	public ConnectionState getHandshakeState() {
		return handshakeState;
	}

	public void setHandshakeState(ConnectionState handshakeState) {
		this.handshakeState = handshakeState;
	}

	private void initializeConnectionStates(StemClientSession session) {
		connectedState = new ConnectedConnectionState();
		connectedState.primeConnectionState(session);
		
		disconnectedState = new DisconnectedConnectionState();
		disconnectedState.primeConnectionState(session);
		
		kickedState = new KickedConnectionState();
		kickedState.primeConnectionState(session);

		if (session.isHTTP()) {
			handshakeState = new HttpHandshakeConnectionState();
			handshakeState.primeConnectionState(session);
		} else {
			handshakeState = new HandshakeConnectionState();
			handshakeState.primeConnectionState(session);
		}
	}
}

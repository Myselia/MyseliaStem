package com.mycelia.stem.communication;

import com.mycelia.stem.communication.states.ConnectedConnectionState;
import com.mycelia.stem.communication.states.DisconnectedConnectionState;
import com.mycelia.stem.communication.states.HandshakeConnectionState;
import com.mycelia.stem.communication.states.IConnectionState;
import com.mycelia.stem.communication.states.KickedConnectionState;

public class ConnectionStateContainer {

	private IConnectionState connectedState;
	private IConnectionState disconnectedState;
	private IConnectionState kickedState;
	private IConnectionState handshakeState;
	private int stateCount;
	
	public ConnectionStateContainer(StemClientSession session) {
		initializeConnectionStates(session);
	}
	
	public IConnectionState getConnectedState() {
		return connectedState;
	}

	public void setConnectedState(IConnectionState connectedState) {
		this.connectedState = connectedState;
	}

	public IConnectionState getDisconnectedState() {
		return disconnectedState;
	}

	public void setDisconnectedState(IConnectionState disconnectedState) {
		this.disconnectedState = disconnectedState;
	}

	public IConnectionState getKickedState() {
		return kickedState;
	}

	public void setKickedState(IConnectionState kickedState) {
		this.kickedState = kickedState;
	}

	public IConnectionState getHandshakeState() {
		return handshakeState;
	}

	public void setHandshakeState(IConnectionState handshakeState) {
		this.handshakeState = handshakeState;
	}

	public int getStateCount() {
		return stateCount;
	}

	public void setStateCount(int stateCount) {
		this.stateCount = stateCount;
	}

	private void initializeConnectionStates(StemClientSession session) {
		int count = IConnectionState.stateID.values().length;
		connectedState = new ConnectedConnectionState();
		connectedState.primeConnectionState(session);
		disconnectedState = new DisconnectedConnectionState();
		disconnectedState.primeConnectionState(session);
		kickedState = new KickedConnectionState();
		disconnectedState.primeConnectionState(session);
		handshakeState = new HandshakeConnectionState();
		handshakeState.primeConnectionState(session);
	}
}

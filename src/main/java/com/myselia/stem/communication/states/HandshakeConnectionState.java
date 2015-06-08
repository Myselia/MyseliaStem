package com.myselia.stem.communication.states;

import java.io.IOException;

import com.myselia.javacommon.communication.units.Transmission;
import com.myselia.stem.communication.StemClientSession;
import com.myselia.stem.communication.handlers.ComponentHandlerBase;
import com.myselia.stem.communication.handlers.ComponentHandlerFactory;

public class HandshakeConnectionState implements ConnectionState {

	private ComponentHandlerBase handler;
	private StemClientSession session;

	@Override
	public void primeConnectionState(StemClientSession session) {
		this.session = session;
	}

	@Override
	public void process(Transmission t) throws IOException {
		System.out.println("HANDSHAKE RECV: " + t);
		handleSetupPacket(t);
	}

	@Override
	public ComponentHandlerBase getHandler() {
		return handler;
	}

	@Override
	public void setHandler(ComponentHandlerBase handler) {
		this.handler = handler;
	}

	private void handleSetupPacket(Transmission s) {
		System.out.print("[HANDSHAKE BEGIN]");
		ComponentHandlerBase handler = null;
		try {
			System.out.println("\t!!!!!!!!!!!!!!!RECV!!!!!!!!!!!!!!!!!!\n" + "\t|----> " + s);
			handler = ComponentHandlerFactory.createHandler(s, session);
			System.out.println("\tVALUE OF READY IS: " + handler.ready());
			if (handler.ready()) {
				this.setHandler(handler);
				session.setComponentHandler(handler);
				session.setConnectionState(session.getStateContainer().getConnectedState());
			} else {
				session.die();
			}
		} catch (Exception e) {
			System.out.println("\tSetup packet from component is malformed!");
			e.printStackTrace();
		}
		System.out.println("[HANDSHAKE COMPLETE]");
	}

	public String toString() {
		return "[State] ~~ Handshake @ " + this.getClass();
	}
}

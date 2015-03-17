package com.mycelia.stem.communication.states;

import java.io.BufferedReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.mycelia.common.communication.structures.Transmission;
import com.mycelia.stem.communication.StemClientSession;
import com.mycelia.stem.communication.handlers.NetworkComponentHandlerBase;
import com.mycelia.stem.communication.handlers.NetworkComponentHandlerBuilder;

public class HandshakeConnectionState implements IConnectionState {

	private NetworkComponentHandlerBase handler;
	private Gson jsonParser = new Gson();
	private StemClientSession session;
	private String inputS = null;
	private BufferedReader input = null;
	
	@Override
	public void primeConnectionState(StemClientSession session) {
		this.session = session;
		this.input = (BufferedReader)session.getReader();
	}
	
	@Override
	public void process() {
		try {
			while ((inputS = input.readLine()) != null) {
				System.out.println("RECV: " + inputS);
				handleSetupPacket(inputS);
				break;
				
			}
		} catch (IOException e) {
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
	
	private void handleSetupPacket(String s) {
		NetworkComponentHandlerBase handler = null;
		try {
			Transmission setupTransmission = jsonParser.fromJson(s, Transmission.class);
			handler = NetworkComponentHandlerBuilder.createHandler(setupTransmission, session);
			
			System.out.println("VALUE OF READY IS: " + handler.ready() );
			if (handler.ready()) {
				this.setHandler(handler);
				session.setComponentHandler(handler);
				session.getStateContainer().getConnectedState().setHandler(handler);
				session.setConnectionState(session.getStateContainer()
						.getConnectedState());
			} else {
				//Tell the session thread to die
				session.die();
			}
		} catch (Exception e) {
			System.out.println("Setup packet from component is malformed!");
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return "HANDSHAKER!!";
	}
}

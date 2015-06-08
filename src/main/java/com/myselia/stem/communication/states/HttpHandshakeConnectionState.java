package com.myselia.stem.communication.states;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.websocketx.WebSocket13FrameDecoder;
import io.netty.handler.codec.http.websocketx.WebSocket13FrameEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.myselia.javacommon.communication.units.Transmission;
import com.myselia.javacommon.communication.units.TransmissionBuilder;
import com.myselia.javacommon.constants.opcode.ActionType;
import com.myselia.javacommon.constants.opcode.ComponentType;
import com.myselia.javacommon.constants.opcode.OpcodeAccessor;
import com.myselia.javacommon.constants.opcode.Operation;
import com.myselia.javacommon.constants.opcode.operations.StemOperation;
import com.myselia.javacommon.framework.communication.WebSocketHelper;
import com.myselia.stem.communication.StemClientSession;
import com.myselia.stem.communication.codecs.StringToTransmissionDecoder;
import com.myselia.stem.communication.codecs.TransmissionToStringEncoder;
import com.myselia.stem.communication.codecs.WebSocketDecoder;
import com.myselia.stem.communication.handlers.ComponentHandlerBase;
import com.myselia.stem.communication.handlers.ComponentHandlerFactory;

public class HttpHandshakeConnectionState implements ConnectionState {

	private final String keyStringSearch = "Sec-WebSocket-Key: ";
	
	private ComponentHandlerBase handler;
	private StemClientSession session;
	private String webSocketKey = null;
	boolean connectionEstablished = false;
	boolean handshakeFinished = false;
	
	@Override
	public void primeConnectionState(StemClientSession session) {
		this.session = session;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void process(Transmission s) throws IOException {

		if (connectionEstablished) {
			System.err.println("CONNECTION ESTABLISHED");
			
			// Wait for the setup packet after the headers have been handled
			handleSetupPacket(s);
		} else {
			// This is first contact with the WebSocket client, generate an
			// accept response
			setWebSocketKey((ArrayList<String>) session.getFirstContact());
			handleHeaders(session.getClientChannel());	
			//Setup the pipeline since the WebSocket handshake has been completed
			setupPipeline(session.getClientChannel());
			connectionEstablished = true;
		}

	}

	private void setWebSocketKey(ArrayList<String> HTTPRequest) {
		Iterator<String> it = HTTPRequest.iterator();
		while (it.hasNext()) {
			String s = it.next();
			if (s.contains(keyStringSearch)) {
				webSocketKey = s.substring(keyStringSearch.length());
				break;
			}
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

	private void handleHeaders(Channel ch) {
		// Send our headers
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("!!!!!SENDING RESPONSE HEADERS!!!!!!");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		WebSocketHelper.sendHandshakeResponse(ch, webSocketKey);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("!!!!!!!!!!!!!DONE!!!!!!!!!!!!!!!!!!");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}

	private void handleSetupPacket(Transmission s) throws IOException {
		ComponentHandlerBase handler = null;
		try {
			handler = ComponentHandlerFactory.createHandler(s, session);

			System.out.println("VALUE OF READY IS: " + handler.ready());
			if (handler.ready()) {
				this.setHandler(handler);
				session.setComponentHandler(handler);
				session.setConnectionState(session.getStateContainer().getConnectedState());
				session.getClientChannel().writeAndFlush(connectionReadyPacket(true));
			} else {
				// Tell the session thread to die
				session.getClientChannel().writeAndFlush(connectionReadyPacket(false));
				session.die();
			}
		} catch (Exception e) {
			System.err.println("Setup packet from component is malformed!");
			e.printStackTrace();
		}
	}
	
	/**
	 * This method sets up the channel pipeline to contain the proper codecs for websocket payload transportation
	 *
	 */
	private void setupPipeline(Channel ch) {
		ChannelPipeline pipeline = ch.pipeline();
		
		//Decoders
		pipeline.remove("frameDecoder");
		pipeline.replace("stringDecoder", "webSocketFrameDecoder", new WebSocket13FrameDecoder(true, false, 4096));
		pipeline.addAfter("webSocketFrameDecoder", "webSocketDecoder", new WebSocketDecoder());
		pipeline.addAfter("webSocketDecoder", "transmissionDecoder", new StringToTransmissionDecoder());
		
		//Encoders
		pipeline.replace("stringEncoder", "transmissionEncoder", new TransmissionToStringEncoder());
		pipeline.addFirst("webSocketFrameEncoder", new WebSocket13FrameEncoder(false));
		System.err.println("[HTTP Handshaker] ~~ Pipeline Setup Complete");
	}

	private Transmission connectionReadyPacket(boolean ok) {
		String isReady = null;
		String setupStatus = null;

		if (ok) {
			isReady = "true";
			setupStatus = Operation.SETUPOK;
		} else {
			isReady = "false";
			setupStatus = Operation.SETUPERR;
		}

		TransmissionBuilder tb = new TransmissionBuilder();
		String from = OpcodeAccessor.make(ComponentType.STEM, ActionType.DATA, StemOperation.SETUP);
		String to = OpcodeAccessor.make(ComponentType.LENS, ActionType.DATA, setupStatus);
		tb.newTransmission(from, to);
		tb.addAtom("ready", "boolean", isReady);
		Transmission t = tb.getTransmission();
		return t;
	}

	public String toString() {
		return "[Connection State] ~~ HTTP Handshaker";
	}

}
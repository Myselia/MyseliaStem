package com.myselia.stem.communication;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.IOException;
import java.util.Iterator;

import com.myselia.javacommon.communication.units.Atom;
import com.myselia.javacommon.communication.units.Transmission;
import com.myselia.stem.communication.handlers.ComponentHandlerBase;
import com.myselia.stem.communication.states.ConnectionState;

public class StemClientSession extends SimpleChannelInboundHandler<Transmission> {

	public boolean componentAttached = false;
	public boolean isHandshakeComplete = false;
	// Channel object to handle client transmission on transport layer
	private Channel clientChannel = null;
	private Object firstContact = null;

	private boolean isHTTP;
	// Component handler that encapsulates MySelia Component behavior and
	// handling
	private ComponentHandlerBase componentHandler;
	// Current state of the connection
	private ConnectionState clientConnectionState;
	// Container of all the state instances needed to decide what to do at each
	// client tick
	private ConnectionStateContainer stateContainer;

	public StemClientSession(Channel clientChannel, Object firstContact, boolean isHTTP) {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("!!!!!! ~ ~ NEW STEM CLIENT SESSION ~ ~ !!!!!!!");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		this.isHTTP = isHTTP;
		this.firstContact = firstContact;
		this.clientChannel = clientChannel;
		this.stateContainer = new ConnectionStateContainer(this);
		this.clientConnectionState = stateContainer.getHandshakeState();
		
		try {
			if (isHTTP) {

				clientConnectionState.process(null);

			} else {
				clientConnectionState.process((Transmission) firstContact);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.componentAttached = true;
	}

	@Override
	public void channelActive(final ChannelHandlerContext ctx) {
		System.out.println("Established connection @ " + ctx);
		System.out.println("\t|-->Awaiting handshake");
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Transmission msg) throws Exception {
		printRecvMsg(msg);
		clientConnectionState.process(msg);
	}
	
	private void printRecvMsg(Transmission t) {
		System.out.println();
		System.out.println("[Message Received]");
		
		
		//Header
		System.out.println("\t->Header: ");
		System.out.println("\t\t->ID: " + t.get_header().get_id());
		System.out.println("\t\t->From: " + t.get_header().get_from());
		System.out.println("\t\t->To: " + t.get_header().get_to());
		//Atoms
		System.out.println("\t->Atoms:");
		Iterator<Atom> it = t.get_atoms().iterator();
		while (it.hasNext()) {
			Atom a = it.next();
			System.out.println("\t\t->Field: " + a.get_field());
			System.out.println("\t\t\t->Type: " + a.get_type());
			System.out.println("\t\t\t->Value: " + a.get_value());
		}
		System.out.println();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	public ConnectionState getConnectionState() {
		return clientConnectionState;
	}

	public ComponentHandlerBase getComponentHandler() {
		return componentHandler;
	}

	public void setComponentHandler(ComponentHandlerBase componentHandler) {
		this.componentHandler = componentHandler;
		this.stateContainer.setHandlers(componentHandler);
	}

	public ConnectionStateContainer getStateContainer() {
		return stateContainer;
	}

	public void setConnectionState(ConnectionState state) {
		this.clientConnectionState = state;
	}

	public Channel getClientChannel() {
		return clientChannel;
	}

	public void setClientChannel(Channel clientChannel) {
		this.clientChannel = clientChannel;
	}

	public boolean isHTTP() {
		return isHTTP;
	}

	public Object getFirstContact() {
		return firstContact;
	}

	public void die() {
		// TODO: Implement
	}

}
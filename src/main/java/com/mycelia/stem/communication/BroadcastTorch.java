package com.mycelia.stem.communication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.mycelia.common.communication.structures.Transmission;
import com.mycelia.common.communication.structures.TransmissionBuilder;
import com.mycelia.common.constants.ComponentType;
import com.mycelia.stem.communication.seekers.ISeek;

public class BroadcastTorch {

	private int BROADCAST_SLEEP = 1500;
	private final String TERMINATOR = "\r\n";

	private volatile ArrayList<ISeek> seekInterfaces;

	private byte[] seekProbeText; /* THIS IS IN JSON */
	public ComponentType type;

	public BroadcastTorch(	ComponentType type,
							ArrayList<ISeek> seekers) 
	{
		this.seekInterfaces = seekers;
		this.type = type;
	}

	/*
	 * ##############################| |##############################
	 * ##############################| PUBLIC |##############################
	 * ##############################| |##############################
	 */

	public void seek() throws InterruptedException, IOException {

		Iterator<ISeek> iterator = seekInterfaces.iterator();
		while (iterator.hasNext()) {
			ISeek seeker = iterator.next();
			System.out.println(seeker.printStatus(type.name(), new String(seekProbeText)));
			seeker.discoverComponents(seekProbeText);
		}
		Thread.sleep(BROADCAST_SLEEP);
		//Thread.yield(); //MUCH BETTER!
	}

	// Called to setup the broadcast torch JUST before the actual broadcasting
	// begins
	public synchronized void primeBroadcaster() {
		Iterator<ISeek> iterator = seekInterfaces.iterator();
		while (iterator.hasNext()) {
			ISeek seeker = iterator.next();

				if (seeker.getPort() == 0) {
					seeker.setPort(BroadcastPortHandler.nextPort());
					seeker.openInternalSocket();
			}

		}
		seekProbeText = buildInfoPacket();
	}

	public void close() {
		// TODO Auto-generated method stub

	}

	/*
	 * ##############################| |##############################
	 * ##############################| PRIVATE |##############################
	 * ##############################| |##############################
	 */

	// Used to build the JSON that will be sent in the UDP seek packets that
	// components will
	// interpret internally
	private byte[] buildInfoPacket() {
		String seekPacketString = null;

		switch (type) {
		case DAEMON:
			seekPacketString = seekPacket(ComponentType.DAEMON.toString());
			break;
		case LENS:
			seekPacketString = seekPacket(ComponentType.LENS.toString());
			break;
		case SANDBOXMASTER:
			seekPacketString = seekPacket(ComponentType.SANDBOXMASTER.toString());
			break;
		}

		seekPacketString += TERMINATOR;
		return seekPacketString.getBytes();
	}
	
	/*
	 * TODO, ABSTRACT THIS 
	 */
	private String seekPacket(String type) {
		TransmissionBuilder tb = new TransmissionBuilder();
		Gson g = new Gson();

		tb.newTransmission(1000, "stem", "all");
		tb.newAtom("ip", "String", ComDock.Stem_IP);
		tb.newAtom("port", "int", Integer.toString(ComDock.Stem_Listen_Port));
		tb.newAtom("type", "String", type);
		Transmission t = tb.getTransmission();

		return g.toJson(t);
	}

	static class BroadcastPortHandler {
		private static int portAssignable = 42080;

		synchronized static int nextPort() {
			portAssignable++;
			return portAssignable;
		}
	}

}

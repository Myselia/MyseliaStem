package com.mycelia.stem.communication;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.mycelia.common.communication.structures.Transmission;
import com.mycelia.common.communication.structures.TransmissionBuilder;
import com.mycelia.stem.communication.ComDock.componentType;
import com.mycelia.stem.communication.seekers.ISeek;

public class BroadcastTorch {

	private final int SLEEP_TIME = 2000;
	private final String TERMINATOR = "\r\n";

	private ArrayList<ISeek> seekInterfaces;

	
	private byte[] seekProbeText; /* THIS IS IN JSON */
	public componentType type;

	private Socket passOnSocket;

	public BroadcastTorch(	componentType type,
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
		try {

			Iterator<ISeek> iterator = seekInterfaces.iterator();
			while (iterator.hasNext()) {
				ISeek seeker = iterator.next();
				System.out.println(type + " - Current Seeker is: " + seeker.getClass() + " on port: " + seeker.getPort());
				seeker.discoverComponents(seekProbeText);
			}
			Thread.sleep(SLEEP_TIME);

		} catch (InterruptedException e) {
			/*
			 * TODO PUT CODE HERE THAT HANDLES A KILL SIGNAL TO THE THREAD
			 */
		}
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
			seekPacketString = seekPacket("0");
			break;
		case LENS:
			seekPacketString = seekPacket("1");
			break;
		case SANDBOX:
			seekPacketString = seekPacket("2");
			break;
		}

		seekPacketString += TERMINATOR;
		return seekPacketString.getBytes();
	}

	private String seekPacket(String type) {
		TransmissionBuilder tb = new TransmissionBuilder();
		Gson g = new Gson();

		tb.newTransmission(1000, "stem", "all");
		tb.newAtom("ip", "String", Integer.toString(ComDock.Stem_IP));
		tb.newAtom("port", "int", Integer.toString(ComDock.Stem_Listen_Port));
		tb.newAtom("type", "String", type);
		Transmission t = tb.getTransmission();

		return g.toJson(t);
	}

	static class BroadcastPortHandler {
		private static int portAssignable = 42080;

		static synchronized int nextPort() {
			portAssignable++;
			return portAssignable;
		}
	}

}

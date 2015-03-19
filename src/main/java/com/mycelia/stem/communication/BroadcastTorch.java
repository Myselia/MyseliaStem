package com.mycelia.stem.communication;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.mycelia.common.communication.structures.Transmission;
import com.mycelia.common.communication.structures.TransmissionBuilder;
import com.mycelia.common.constants.ComponentType;
import com.mycelia.stem.communication.seekers.Seek;

public class BroadcastTorch {

	private int BROADCAST_SLEEP = 1500;
	private volatile ArrayList<Seek> seekInterfaces;
	private byte[] seekProbeText; /* THIS IS IN JSON */
	private static DatagramSocket discoverSocket;
	public ComponentType type;

	public BroadcastTorch(	ComponentType type,
							ArrayList<Seek> seekers) 
	{
		this.seekInterfaces = seekers;
		this.type = type;
		try {
			if (discoverSocket == null)
				discoverSocket = new DatagramSocket(CommunicationDock.Stem_Broadcast_Port);
		} catch (SocketException e) {
			System.err.println("Cant create a broadcast socket on port: " + CommunicationDock.Stem_Broadcast_Port);
			e.printStackTrace();
		}
	}

	/*
	 * ##############################| |##############################
	 * ##############################| PUBLIC |##############################
	 * ##############################| |##############################
	 */

	/**
	 * Called at every tick of the BroadCaster. Discovers components using the seek implementations
	 * located within the seekInterfaces list.
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void seek() throws InterruptedException, IOException {

		Iterator<Seek> iterator = seekInterfaces.iterator();
		while (iterator.hasNext()) {
			Seek seeker = iterator.next();
			System.out.println(seeker.printStatus(type.name(), new String(seekProbeText)));
			seeker.discoverComponents(seekProbeText);
		}
		Thread.sleep(BROADCAST_SLEEP); //FOR TESTING, PREVENTS CONSOLE SPAM
		//Thread.yield(); //MUCH BETTER WHEN DEPLOYED!
	}

	/**
	 * Called to setup the broadcast torch JUST before the actual broadcasting begins
	 */
	public synchronized void primeBroadcaster() {
		Iterator<Seek> iterator = seekInterfaces.iterator();
		while (iterator.hasNext()) {
			Seek seeker = iterator.next();
			if (!seeker.hasSocket())
				seeker.setSocket(BroadcastTorch.discoverSocket);

		}
		seekProbeText = buildInfoPacket();
	}

	/*
	 * ##############################| |##############################
	 * ##############################| PRIVATE |##############################
	 * ##############################| |##############################
	 */

	/**
	 * Used to build the JSON that will be sent in the UDP seek packets that components
	 * will interpret internally.
	 * @return A packet to be sent by the seeker accepting a particular type of component connection
	 */
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
		case DATABASE:
			seekPacketString = seekPacket(ComponentType.DATABASE.toString());
			break;
		case SANDBOXSLAVE:
			//Does not accept connections of this type
			break;
		case STEMMASTER:
			//Does not accept connections of this type
			break;
		case STEMSLAVE:
			seekPacketString = seekPacket(ComponentType.STEMSLAVE.toString());
			break;
		default:
			break;
		}
		
		return seekPacketString.getBytes();
	}
	
	/*
	 * TODO, ABSTRACT THIS 
	 */
	private String seekPacket(String type) {
		TransmissionBuilder tb = new TransmissionBuilder();
		Gson g = new Gson();

		tb.newTransmission(1000, "stem", "all");
		tb.newAtom("ip", "String", CommunicationDock.Stem_IP);
		tb.newAtom("port", "int", Integer.toString(CommunicationDock.Stem_Communication_Port));
		tb.newAtom("type", "String", type);
		Transmission t = tb.getTransmission();

		return g.toJson(t);
	}

}

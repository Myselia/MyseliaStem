package com.myselia.stem.communication.seekers;

import java.io.IOException;
import java.net.DatagramSocket;

public class EchoSeek implements Seek {

	private volatile static EchoSeek uniqueInstance;
	private DatagramSocket socket;
	private String seekerName = "Echo Seeker";
	
	private EchoSeek() {
	}

	public void discoverComponents(byte[] infoPacket) throws IOException {
	}
	
	@Override
	public boolean hasSocket() {
		if (socket == null) 
			return false;
		return true;
	}

	@Override
	public void setSocket(DatagramSocket socket) {
		this.socket = socket;
	}
	
	public String printStatus(String componentType, String packet) {
		return	seekerName
				+ "\n\t|-> Looking for: " + componentType  
				+ "\n\t|-> With packet: " + packet;
	}

	public static EchoSeek getInstance() {
		if (uniqueInstance == null) {
			synchronized (LocalNetworkSeek.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new EchoSeek();
				}
			}
		}
		return uniqueInstance;
	}
}

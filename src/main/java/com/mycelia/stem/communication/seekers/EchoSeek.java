package com.mycelia.stem.communication.seekers;

import java.io.IOException;

public class EchoSeek implements Seek {

	private volatile static EchoSeek uniqueInstance;
	private String seekerName = "Echo Seeker";
	private int port;
	
	private EchoSeek() {
	}

	public synchronized void discoverComponents(byte[] infoPacket) throws IOException {
	}

	public void openInternalSocket() {
		System.out.println("ECHO SEEKER: TRYING TO OPEN SOCKET! HERE IS MY PORT: " + getPort());
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public void setPort(int port) {
		this.port = port;
	}
	
	public String printStatus(String componentType, String packet) {
		return	seekerName
				+ "\n\t|-> Looking for: " + componentType + " on local port: " + port
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

package com.mycelia.stem.communication.seekers;

import java.io.IOException;

public class SeekImpl_echo implements ISeek {

	private volatile static SeekImpl_echo uniqueInstance;
	private String seekerName = "Echo Seeker";
	private int port;
	
	private SeekImpl_echo() {
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

	public static SeekImpl_echo getInstance() {
		if (uniqueInstance == null) {
			synchronized (SeekImpl_localNetwork.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new SeekImpl_echo();
				}
			}
		}
		return uniqueInstance;
	}
}

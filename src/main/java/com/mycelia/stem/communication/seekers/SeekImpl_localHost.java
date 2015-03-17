package com.mycelia.stem.communication.seekers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.mycelia.stem.communication.ComDock;

public class SeekImpl_localHost implements ISeek {

	private volatile static SeekImpl_localHost uniqueInstance;
	private int port;
	private DatagramSocket discoverSocket;
	private String seekerName = "Local Host Seeker";
	
	private SeekImpl_localHost() {
	}

	public synchronized void discoverComponents(byte[] infoPacket) throws IOException {
		DatagramPacket networkPacket = new DatagramPacket(infoPacket, infoPacket.length,
				InetAddress.getByName("127.0.0.1"), ComDock.Component_Listen_Port);
		discoverSocket.send(networkPacket);
	}

	public void openInternalSocket() {
		try {
			discoverSocket = new DatagramSocket(port);
		} catch (Exception e) {
			System.err.println("SeekImpl_localHost: Cant open discover port on "
					+ port);
		}
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

	public static SeekImpl_localHost getInstance() {
		if (uniqueInstance == null) {
			synchronized (SeekImpl_localNetwork.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new SeekImpl_localHost();
				}
			}
		}
		return uniqueInstance;
	}
}

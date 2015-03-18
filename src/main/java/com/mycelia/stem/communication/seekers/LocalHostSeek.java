package com.mycelia.stem.communication.seekers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.mycelia.stem.communication.CommunicationDock;

public class LocalHostSeek implements Seek {

	private volatile static LocalHostSeek uniqueInstance;
	private int port;
	private DatagramSocket discoverSocket;
	private String seekerName = "Local Host Seeker";
	
	private LocalHostSeek() {
	}

	public synchronized void discoverComponents(byte[] infoPacket) throws IOException {
		DatagramPacket networkPacket = new DatagramPacket(infoPacket, infoPacket.length,
				InetAddress.getByName("127.0.0.1"), CommunicationDock.Component_Listen_Port);
		discoverSocket.send(networkPacket);
	}

	public void openInternalSocket() {
		try {
			discoverSocket = new DatagramSocket(port);
		} catch (Exception e) {
			System.err.println(seekerName + ": Cant open discover port on "
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

	public static LocalHostSeek getInstance() {
		if (uniqueInstance == null) {
			synchronized (LocalNetworkSeek.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new LocalHostSeek();
				}
			}
		}
		return uniqueInstance;
	}
}

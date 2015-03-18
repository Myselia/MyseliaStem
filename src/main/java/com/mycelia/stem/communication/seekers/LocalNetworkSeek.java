package com.mycelia.stem.communication.seekers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.mycelia.stem.communication.CommunicationDock;

public class LocalNetworkSeek implements Seek {
	private volatile static LocalNetworkSeek uniqueInstance;
	private int port;
	private DatagramSocket discoverSocket;
	private String seekerName = "Local Network Seeker";
	
	private LocalNetworkSeek() {
	}

	public synchronized void discoverComponents(byte[] infoPacket) throws IOException {
		DatagramPacket networkPacket = new DatagramPacket(infoPacket, infoPacket.length,
				InetAddress.getByName("255.255.255.255"), CommunicationDock.Component_Listen_Port);
		discoverSocket.send(networkPacket);
	}

	public synchronized void openInternalSocket() {
		try {
			discoverSocket = new DatagramSocket(port);
		} catch (Exception e) {
			System.err.println("SeekImpl_localNetwork: Cant open discover port on "
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
	
	public static LocalNetworkSeek getInstance() {
		if (uniqueInstance == null) {
			synchronized (LocalNetworkSeek.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new LocalNetworkSeek();
				}
			}
		}
		return uniqueInstance;
	}
}
package com.myselia.stem.communication.seekers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.myselia.stem.communication.CommunicationDock;

public class LocalHostSeek implements Seek {

	private volatile static LocalHostSeek uniqueInstance;
	private DatagramSocket socket = null;
	private String seekerName = "Local Host Seeker";
	
	private LocalHostSeek() {
	}

	public void discoverComponents(byte[] infoPacket) throws IOException {
		DatagramPacket networkPacket = new DatagramPacket(infoPacket, infoPacket.length,
				InetAddress.getByName("127.0.0.1"), CommunicationDock.Component_Listen_Port);
		socket.send(networkPacket);
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
				+ "\n\t|-> Looking for: " + componentType + " on local port: " + CommunicationDock.Stem_Broadcast_Port
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

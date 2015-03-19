package com.mycelia.stem.communication.seekers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.mycelia.stem.communication.CommunicationDock;

public class LocalNetworkSeek implements Seek {
	private volatile static LocalNetworkSeek uniqueInstance;
	private DatagramSocket socket = null;
	private String seekerName = "Local Network Seeker";
	
	private LocalNetworkSeek() {
	}

	public void discoverComponents(byte[] infoPacket) throws IOException {
		DatagramPacket networkPacket = new DatagramPacket(infoPacket, infoPacket.length,
				InetAddress.getByName("255.255.255.255"), CommunicationDock.Component_Listen_Port);
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
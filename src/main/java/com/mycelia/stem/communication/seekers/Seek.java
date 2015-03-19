package com.mycelia.stem.communication.seekers;

import java.io.IOException;
import java.net.DatagramSocket;

public interface Seek {

	public void discoverComponents(byte[] infoPacket)
			throws IOException;

	public boolean hasSocket();
	public void setSocket(DatagramSocket socket);
	public String printStatus(String componentType, String packet);

}

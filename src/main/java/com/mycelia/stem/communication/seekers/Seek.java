package com.mycelia.stem.communication.seekers;

import java.io.IOException;

public interface Seek {

	public void discoverComponents(byte[] infoPacket)
			throws IOException;

	public void openInternalSocket();
	public int getPort();
	public void setPort(int port);
	public String printStatus(String componentType, String packet);

}

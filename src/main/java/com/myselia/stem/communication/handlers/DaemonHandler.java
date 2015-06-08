package com.myselia.stem.communication.handlers;

import java.io.IOException;
import java.util.Map;

import com.myselia.javacommon.communication.units.Transmission;

public class DaemonHandler extends ComponentHandlerBase {
	
	public DaemonHandler() {
	}
	
	@Override
	public void primeHandler(Map<String, String> setupMap) {
		this.ip = setupMap.get("ip");
		this.mac = setupMap.get("mac");
		this.hashID = setupMap.get("hashID");
	}
	
	public void handleComponent(Transmission t) throws IOException {
		super.handleComponent(t);
	}

	@Override
	protected void transmissionReceived() {
		System.out.println("Daemon Receive:"
				+ "\n\t|-> Hash: " + getHashID());
	}
	
	public String toString() {
		return "TYPE: DAEMON, " + "IP: " + this.ip + ", MAC: " + this.mac + ", HASHID: " + hashID;
	}

}

package com.mycelia.stem.communication.handlers;

import java.util.Map;

public class SandboxHandler extends ComponentHandlerBase {

	public SandboxHandler() {
	}

	@Override
	public void primeHandler(Map<String, String> setupMap) {
		this.ip = setupMap.get("ip");
		this.mac = setupMap.get("mac");
		this.hashID = setupMap.get("hashID");
	}

	public void handleComponent() {
		super.handleComponent();
	}
	
	@Override
	protected void transmissionReceived() {
		System.out.println("Sandbox Receive:"
				+ "\n\t|-> Hash: " + getHashID()
				+ "\n\t|-> Transmission: " + jsonInterpreter.toJson(mb.getFromInQueue()));
	}

	public String toString() {
		return "TYPE: SANDBOX-MASTER, " + "IP: " + this.ip + ", MAC: " + this.mac + ", HASHID: " + hashID;
	}


}

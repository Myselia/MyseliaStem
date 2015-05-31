package com.myselia.stem.communication.handlers;

import java.io.IOException;
import java.util.Map;

import com.myselia.javacommon.communication.units.Transmission;

public class SandboxHandler extends ComponentHandlerBase {

	public SandboxHandler() {
	}

	@Override
	public void primeHandler(Map<String, String> setupMap) {
		this.ip = setupMap.get("ip");
		this.mac = setupMap.get("mac");
		this.hashID = setupMap.get("hashID");
	}

	public void handleComponent() throws IOException {
		super.handleComponent();
	}
	
	@Override
	protected void transmissionReceived() {
		System.out.println("Sandbox Receive:"
				+ "\n\t|-> Hash: " + getHashID()
				+ "\n\t|-> Transmission: " + jsonInterpreter.toJson(mb.peekOut()));
	}

	public String toString() {
		return "TYPE: SANDBOX-MASTER, " + "IP: " + this.ip + ", MAC: " + this.mac + ", HASHID: " + hashID;
	}

	@Override
	public void in(Transmission trans) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Transmission out() {
		// TODO Auto-generated method stub
		return null;
	}

}

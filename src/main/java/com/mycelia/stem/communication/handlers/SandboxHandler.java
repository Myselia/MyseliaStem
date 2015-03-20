package com.mycelia.stem.communication.handlers;

import java.util.Map;

import com.google.gson.Gson;
import com.mycelia.common.communication.structures.MailBox;
import com.mycelia.common.communication.tools.TransmissionBuilder;
import com.mycelia.common.communication.units.Transmission;

public class SandboxHandler extends ComponentHandlerBase {

	private static int count = 0;

	public SandboxHandler() {
		mb = new MailBox<Transmission>();
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
				+ "\n\t|-> Transmission: " + jsonInterpreter.toJson(mb.getNextReceived()));
	}

	public String toString() {
		return "TYPE: SANDBOX-MASTER, " + "IP: " + this.ip + ", MAC: " + this.mac + ", HASHID: " + hashID;
	}

	private static String buildTestPacket() {
		TransmissionBuilder tb = new TransmissionBuilder();
		Gson g = new Gson();

		tb.newTransmission(1000, "stem", "all");
		tb.newAtom("someNumber", "int", Integer.toString(count));
		Transmission t = tb.getTransmission();
		count++;
		return g.toJson(t);
	}



}

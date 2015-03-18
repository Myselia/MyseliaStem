package com.mycelia.stem.communication.handlers;

import java.util.Map;

import com.google.gson.Gson;
import com.mycelia.common.communication.structures.Transmission;
import com.mycelia.common.communication.structures.TransmissionBuilder;

public class SandboxHandler extends ComponentHandlerBase {

	private static int count = 0;

	public SandboxHandler() {
	}

	@Override
	public void primeHandler(Map<String, String> setupMap) {
		this.ip = setupMap.get("ip");
		this.mac = setupMap.get("mac");
		this.hashID = setupMap.get("hashID");
	}

	@Override
	public void handleComponent() {
		String s = buildTestPacket();
		System.out.println("SENDING TO CLIENT: " + s);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		output.println(s);
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

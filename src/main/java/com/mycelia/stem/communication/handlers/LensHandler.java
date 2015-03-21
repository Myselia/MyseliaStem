package com.mycelia.stem.communication.handlers;

import java.util.Map;

import com.google.gson.Gson;
import com.mycelia.common.communication.structures.MailBox;
import com.mycelia.common.communication.tools.TransmissionBuilder;
import com.mycelia.common.communication.units.Transmission;

public class LensHandler extends ComponentHandlerBase {
	
	int testcount = 8;
	private static int count = 0;
	
	public LensHandler() {
		mb = new MailBox();
	}

	@Override
	public void primeHandler(Map<String, String> setupMap) {
		this.ip = setupMap.get("ip");
		this.mac = setupMap.get("mac");
		this.hashID = setupMap.get("hashID");
	}
	
	public void handleComponent() {
		super.handleComponent();
		buildTestPacket();
	}
	
	@Override
	protected void transmissionReceived() {
		System.out.println("Lens Receive:"
				+ "\n\t|-> Hash: " + getHashID()
				+ "\n\t|-> Transmission: " + jsonInterpreter.toJson(mb.getFromInQueue()));
	}
	

	public String toString() {
		return "TYPE: LENS, " + "IP: " + this.ip + ", MAC: " + this.mac + ", HASHID: " + hashID;
	}
	
	private void buildTestPacket() {
		if (testcount > 0) {
		TransmissionBuilder tb = new TransmissionBuilder();
		Gson g = new Gson();
		
		tb.newTransmission(1000, "stem", "all");
		tb.newAtom("someNumber", "int", Integer.toString(count));
		Transmission t = tb.getTransmission();
		count++;
		
		mb.putInOutQueue(t);
		}
		
		if (testcount == -8) {
			testcount = 8;
		}
		
		testcount--;
	}
	
	private String buildTestPacketS() {
		TransmissionBuilder tb = new TransmissionBuilder();
		Gson g = new Gson();
		
		tb.newTransmission(1000, "stem", "all");
		tb.newAtom("someNumber", "int", Integer.toString(count));
		Transmission t = tb.getTransmission();
		count++;
		return jsonInterpreter.toJson(t);
	}

	

}

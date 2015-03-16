package com.mycelia.stem.communication.handlers;

import java.net.SocketException;
import java.util.Map;

import com.google.gson.Gson;
import com.mycelia.common.communication.structures.Transmission;
import com.mycelia.common.communication.structures.TransmissionBuilder;

public class LensHandler extends NetworkComponentHandlerBase {
	
	private static int count = 0;
	
	public LensHandler() {
	}

	@Override
	public void primeHandler(Map<String, String> setupMap) {
		this.ip = setupMap.get("ip");
		this.mac = setupMap.get("mac");
		this.ready = true;
	}
	
	@Override
	public void handleComponent() throws SocketException{
		// TODO Auto-generated method stub
		String s = buildTestPacket();
		System.out.println("SENDING TO CLIENT: " + s);
		output.println(s);
	}
	
	public String toString() {
		return "TYPE: LENS, " + "IP: " + this.ip + ", MAC: " + this.mac;
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

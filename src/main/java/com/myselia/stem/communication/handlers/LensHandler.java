package com.myselia.stem.communication.handlers;

import java.io.IOException;
import java.util.Map;

import com.myselia.javacommon.communication.mail.MailService;
import com.myselia.javacommon.communication.units.Transmission;
import com.myselia.javacommon.communication.units.TransmissionBuilder;
import com.myselia.javacommon.constants.opcode.ActionType;
import com.myselia.javacommon.constants.opcode.ComponentType;
import com.myselia.javacommon.constants.opcode.OpcodeAccessor;
import com.myselia.javacommon.constants.opcode.operations.LensOperation;
import com.myselia.javacommon.constants.opcode.operations.StemOperation;

public class LensHandler extends ComponentHandlerBase {

	private static int count = 0;
	
	public LensHandler() {
		MailService.register("SANDBOXSLAVE", this);
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
		System.out.println("Lens Receive:"
				+ "\n\t|-> Hash: " + getHashID()
				+ "\n\t|-> Transmission: " + jsonInterpreter.toJson(mb.peekOut()));
	}
	
	public String toString() {
		return "TYPE: LENS, " + "IP: " + this.ip + ", MAC: " + this.mac + ", HASHID: " + hashID;
	}

	private void buildTestPacket() {
		TransmissionBuilder tb = new TransmissionBuilder();
		String from = OpcodeAccessor.make(ComponentType.STEM, ActionType.DATA, StemOperation.TEST);
		String to = OpcodeAccessor.make(ComponentType.LENS, ActionType.DATA, LensOperation.TEST);
		tb.newTransmission(from, to);
		tb.addAtom("someNumber", "int", Integer.toString(count));
		Transmission t = tb.getTransmission();
		count++;

		mb.enqueueOut(t);
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

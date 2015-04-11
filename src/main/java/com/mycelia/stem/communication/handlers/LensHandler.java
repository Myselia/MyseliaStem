package com.mycelia.stem.communication.handlers;

import java.io.IOException;
import java.util.Map;

import com.mycelia.common.communication.MailService;
import com.mycelia.common.communication.structures.MailBox;
import com.mycelia.common.communication.units.Transmission;
import com.mycelia.common.communication.units.TransmissionBuilder;
import com.mycelia.common.constants.opcode.ActionType;
import com.mycelia.common.constants.opcode.ComponentType;
import com.mycelia.common.constants.opcode.OpcodeAccessor;
import com.mycelia.common.constants.opcode.operations.LensOperation;
import com.mycelia.common.constants.opcode.operations.StemOperation;

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
				+ "\n\t|-> Transmission: " + jsonInterpreter.toJson(mb.peekOutQueue()));
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

		mb.putInOutQueue(t);
	}
	
	@Override
	public MailBox<Transmission> getMailBox() {
		return this.mb;
	}

}

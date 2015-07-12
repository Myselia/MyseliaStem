package com.myselia.stem.communication.handlers;

import java.io.IOException;
import java.util.Map;

import com.myselia.javacommon.communication.mail.MailService;
import com.myselia.javacommon.communication.units.Transmission;

public class LensHandler extends ComponentHandlerBase {

	public LensHandler() {
		MailService.register("LENS_RUNTIME", this);
		MailService.register("LENS_DATA", this);
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
	protected void transmissionReceived(Transmission t) {
		System.out.println("Lens Receive:"
				+ "\n\t|-> Hash: " + getHashID());
	}
	
	@Override
	protected void endpointReceive() {
		System.out.println("[Sandbox] ~ Sending To: " + mailbox.peekIn().get_header().get_to());
		write(mailbox.dequeueIn());
	}
	
	public String toString() {
		return "TYPE: LENS, " + "IP: " + this.ip + ", MAC: " + this.mac + ", HASHID: " + hashID;
	}

	/*private void buildTestPacket() {
		TransmissionBuilder tb = new TransmissionBuilder();
		String from = OpcodeBroker.make(ComponentType.STEM, null, ActionType.DATA, StemOperation.TEST);
		String to = OpcodeBroker.make(ComponentType.LENS, null, ActionType.DATA, LensOperation.TEST);
		tb.newTransmission(from, to);
		tb.addAtom("someNumber", "int", Integer.toString(count));
		Transmission t = tb.getTransmission();
		count++;

		mailbox.enqueueOut(t);
	}*/

}

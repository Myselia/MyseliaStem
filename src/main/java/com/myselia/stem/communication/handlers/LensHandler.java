package com.myselia.stem.communication.handlers;

import java.io.IOException;

import com.myselia.javacommon.communication.mail.MailService;
import com.myselia.javacommon.communication.units.Transmission;

public class LensHandler extends ComponentHandlerBase {

	public LensHandler() {
		MailService.register("LENS_RUNTIME", this);
		MailService.register("LENS_DATA", this);
	}

	public void handleComponent(Transmission t) throws IOException {
		super.handleComponent(t);
	}
	
	@Override
	protected void transmissionReceived(Transmission t) {
	}
	
	@Override
	protected void endpointReceive() {
		System.out.println("[Sandbox] ~ Sending To: " + mailbox.peekIn().get_header().get_to());
		write(mailbox.dequeueIn());
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

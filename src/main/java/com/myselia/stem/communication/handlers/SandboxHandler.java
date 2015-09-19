package com.myselia.stem.communication.handlers;

import java.io.IOException;

import com.myselia.javacommon.communication.mail.MailService;
import com.myselia.javacommon.communication.units.Transmission;

public class SandboxHandler extends ComponentHandlerBase {

	public SandboxHandler() {
		MailService.register("SANDBOXMASTER_DATA", this);
		MailService.register("SANDBOXMASTER_RUNTIME", this);
	}

	public void handleComponent(Transmission t) throws IOException {
		super.handleComponent(t);
	}
	
	@Override
	protected void transmissionReceived(Transmission t) {
		mailbox.enqueueOut(t);
		MailService.notify(this);
	}

	@Override
	protected void endpointReceive() {
		write(mailbox.dequeueIn());
	}
	
}

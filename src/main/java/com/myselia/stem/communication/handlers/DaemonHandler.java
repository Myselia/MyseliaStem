package com.myselia.stem.communication.handlers;

import java.io.IOException;

import com.myselia.javacommon.communication.mail.MailService;
import com.myselia.javacommon.communication.units.Transmission;

public class DaemonHandler extends ComponentHandlerBase {
	
	public DaemonHandler() {
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

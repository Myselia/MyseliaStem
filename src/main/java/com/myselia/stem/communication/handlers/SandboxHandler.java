package com.myselia.stem.communication.handlers;

import java.io.IOException;
import java.util.Map;

import com.myselia.javacommon.communication.mail.MailService;
import com.myselia.javacommon.communication.units.Transmission;

public class SandboxHandler extends ComponentHandlerBase {

	public SandboxHandler() {
		//String toOpcode = OpcodeBroker.makeMailCheckingOpcode(ComponentType.SANDBOXMASTER, ActionType.DATA);
		//String toOpcode2 = OpcodeBroker.makeMailCheckingOpcode(ComponentType.SANDBOXMASTER, ActionType.RUNTIME);
		MailService.register("SANDBOXMASTER_DATA", this);
		MailService.register("SANDBOXMASTER_RUNTIME", this);
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
		System.out.println("Sandbox Receive:"
				+ "\n\t|-> Hash: " + getHashID());
		
		mailbox.enqueueOut(t);
		MailService.notify(this);
	}

	public String toString() {
		return "TYPE: SANDBOX-MASTER, " + "IP: " + this.ip + ", MAC: " + this.mac + ", HASHID: " + hashID;
	}

	@Override
	protected void endpointReceive() {
		System.out.println("[Sandbox] ~ Sending To: " + mailbox.peekIn().get_header().get_to());
		session.getClientChannel().writeAndFlush(mailbox.dequeueIn());
	}

}

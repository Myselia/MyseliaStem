package com.myselia.stem.communication.handlers;

import java.io.IOException;
import java.util.Map;

import com.myselia.javacommon.communication.mail.MailService;
import com.myselia.javacommon.communication.units.Transmission;
import com.myselia.javacommon.constants.opcode.ActionType;
import com.myselia.javacommon.constants.opcode.ComponentType;
import com.myselia.javacommon.constants.opcode.OpcodeBroker;

public class SandboxHandler extends ComponentHandlerBase {

	public SandboxHandler() {
		String toOpcode = OpcodeBroker.makeMailCheckingOpcode(ComponentType.SANDBOXMASTER, ActionType.DATA);
		String toOpcode2 = OpcodeBroker.makeMailCheckingOpcode(ComponentType.SANDBOXMASTER, ActionType.RUNTIME);
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
	public void in(Transmission trans) {
		System.out.println("GOT TRANS WITH ID : " + trans.get_header().get_id());
		super.in(trans);
		System.out.println("~~!! SANDBOX-MASTER GOT MESSAGE !!~~ ");
		System.out.println("MAILBOX SIZE: " + mailbox.getInSize());
		
		session.getClientChannel().writeAndFlush(mailbox.dequeueIn());
	}
	
	@Override
	protected void transmissionReceived() {
		System.out.println("Sandbox Receive:"
				+ "\n\t|-> Hash: " + getHashID());
		

		/*Transmission t = mailbox.dequeueIn();
		mailbox.enqueueOut(t);
		MailService.notify(this);*/
	}

	public String toString() {
		return "TYPE: SANDBOX-MASTER, " + "IP: " + this.ip + ", MAC: " + this.mac + ", HASHID: " + hashID;
	}

}

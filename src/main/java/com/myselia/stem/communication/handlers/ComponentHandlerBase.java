package com.myselia.stem.communication.handlers;

import java.io.IOException;
import java.util.Map;

import com.myselia.javacommon.communication.mail.Addressable;
import com.myselia.javacommon.communication.mail.MailBox;
import com.myselia.javacommon.communication.mail.MailService;
import com.myselia.javacommon.communication.units.Transmission;
import com.myselia.javacommon.communication.units.TransmissionBuilder;
import com.myselia.javacommon.constants.opcode.ActionType;
import com.myselia.javacommon.constants.opcode.ComponentType;
import com.myselia.javacommon.constants.opcode.OpcodeBroker;
import com.myselia.javacommon.constants.opcode.operations.SandboxMasterOperation;
import com.myselia.javacommon.constants.opcode.operations.StemOperation;
import com.myselia.stem.communication.CommunicationDock;
import com.myselia.stem.communication.StemClientSession;

public abstract class ComponentHandlerBase implements Handler, Addressable {

	protected String ip = "";
	protected String mac = "";
	protected String hashID = "";

	protected boolean ready = false;
	protected StemClientSession session;
	protected MailBox<Transmission> mailbox;
	
	public void setSession(StemClientSession session) {
		if (CommunicationDock.getNetworkComponentbyHash(hashID) != null) {
			//This is an old session, revive it
			System.out.println("Reviving dead session.");
			CommunicationDock.getNetworkComponentbyHash(hashID).reviveDeadSession(session);
		} else {
			//This is a brand new session, initialize required objects
			this.session = session;
			CommunicationDock.addNewNetworkComponent(hashID, this);
			mailbox = new MailBox<Transmission>();
			MailService.registerAddressable(this);
			ready = true;
		}
	}

	/*
	 * Primary network handling code
	 */
	
	//Called by xConnectionState when Transmission is received
	@Override
	public void handleComponent(Transmission t) throws IOException {
		transmissionReceived(t);
	}
	
	public void write(Transmission t) {
		System.err.println("****~~ CALLED WRITE ON ID{" + t.get_header().get_id() +"}~~****");
		session.getClientChannel().writeAndFlush(t);
	}

	//Override in child classes to handle received transmission 
	protected abstract void transmissionReceived(Transmission t);
	//Use in conjunction with this class' overridden MailBox.In method to signify that a transmission has
	//reached the in queue from the system
	protected abstract void endpointReceive();

	/*
	 * Setup Methods
	 */
	@Override
	public abstract void primeHandler(Map<String, String> setupMap);

	@Override
	public boolean ready() {
		return ready;
	}

	public void reviveDeadSession(StemClientSession session) {
		//this.session.resetExistingConnection(session);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getHashID() {
		return hashID;
	}

	public void setHashID(String hashID) {
		this.hashID = hashID;
	}
	
	/*
	 * MailBox Overrides
	 */
	@Override
	public void in(Transmission trans) {
		mailbox.enqueueIn(trans);
		endpointReceive();
	}

	@Override
	public Transmission out() {
		return mailbox.dequeueOut();
	}
	
}

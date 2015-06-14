package com.myselia.stem.communication.handlers;

import java.io.IOException;
import java.util.Map;

import com.myselia.javacommon.communication.mail.Addressable;
import com.myselia.javacommon.communication.mail.MailBox;
import com.myselia.javacommon.communication.mail.MailService;
import com.myselia.javacommon.communication.units.Transmission;
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
	@Override
	public void handleComponent(Transmission t) throws IOException {
		System.out.println("[Component Handler] ~~ Handling stuff");
		System.out.println("\t[Transmission] --> " + t.toString());
		mailbox.enqueueIn(t);
		transmissionReceived();
	}

	protected abstract void transmissionReceived();

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
	
	@Override
	public void in(Transmission trans) {
		mailbox.enqueueIn(trans);
	}

	@Override
	public Transmission out() {
		return mailbox.dequeueOut();
	}

}

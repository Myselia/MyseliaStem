package com.myselia.stem.communication.handlers;

import java.io.IOException;

import com.myselia.javacommon.communication.mail.Addressable;
import com.myselia.javacommon.communication.mail.MailBox;
import com.myselia.javacommon.communication.mail.MailService;
import com.myselia.javacommon.communication.units.Transmission;
import com.myselia.javacommon.topology.ComponentCertificate;
import com.myselia.stem.communication.CommunicationDock;
import com.myselia.stem.communication.StemClientSession;

public abstract class ComponentHandlerBase implements Handler, Addressable {

	protected ComponentCertificate componentCert;
	protected StemClientSession session;
	protected MailBox<Transmission> mailbox;
	protected boolean ready = false;
	
	//Called by xConnectionState when Transmission is received
	@Override
	public void handleComponent(Transmission t) throws IOException {
		transmissionReceived(t);
	}
	
	public void write(Transmission t) {
		session.getClientChannel().writeAndFlush(t);
	}

	//Override in child classes to handle received transmission 
	protected abstract void transmissionReceived(Transmission t);
	//Use in conjunction with this class' overridden MailBox.In method to signify that a transmission has reached the in queue from the system
	protected abstract void endpointReceive();

	/*
	 * Setup Methods
	 */
	public void setSession(StemClientSession session) {
		if (CommunicationDock.getComponentHandlerByCert(componentCert) != null) {
			//This is an old session, revive it
			System.out.println("[ComponentHandler] : Reviving dead session with UUID " + componentCert.getUUID());
			CommunicationDock.getComponentHandlerByCert(componentCert).reviveDeadSession();
		} else {
			//This is a brand new session, initialize required objects
			System.out.println("[ComponentHandler] : This is a new session " + componentCert.getUUID());
			this.session = session;
			CommunicationDock.addNetworkComponent(componentCert, this);
			mailbox = new MailBox<Transmission>();
			MailService.registerAddressable(this);
			ready = true;
		}
	}
	
	public void primeHandler(ComponentCertificate componentCert) {
		this.componentCert = componentCert;
	}

	public void reviveDeadSession() {
		session.resetExistingConnection();
	}

	@Override
	public boolean ready() {
		return ready;
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
	
	public String toString() {
		return "[Component Handler]" +
				"\n\t[Registered Under]: " + componentCert.getUUID() +
				"\n\t[Certificate Dump]: " + 
				"\n\t\t" + componentCert;
	}
	
	public ComponentCertificate getCertificate() {
		return componentCert;
	}
	
}

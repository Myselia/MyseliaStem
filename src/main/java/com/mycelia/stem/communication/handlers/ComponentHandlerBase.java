package com.mycelia.stem.communication.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import com.google.gson.Gson;
import com.mycelia.common.communication.structures.MailBox;
import com.mycelia.common.communication.units.Transmission;
import com.mycelia.stem.communication.CommunicationDock;
import com.mycelia.stem.communication.StemClientSession;

public abstract class ComponentHandlerBase implements Handler {

	protected String ip = "";
	protected String mac = "";
	protected String hashID = "";

	protected boolean ready = false;
	protected StemClientSession session;
	protected BufferedReader input;
	protected PrintWriter output;
	protected MailBox<Transmission> mb;
	protected Gson jsonInterpreter;
	private String inputToken = "";
	private String outputToken = "";
	
	public void setSession(StemClientSession session) {
		if (CommunicationDock.getNetworkComponentbyHash(hashID) != null) {
			System.out.println("Reviving dead session.");
			CommunicationDock.getNetworkComponentbyHash(hashID).reviveDeadSession(session);
		} else {
			CommunicationDock.addNewNetworkComponent(hashID, this);
			this.session = session;
			input = (BufferedReader) session.getReader();
			output = (PrintWriter) session.getWriter();
			jsonInterpreter = new Gson();
			mb = new MailBox<Transmission>();
			ready = true;
		}
	}

	@Override
	public void handleComponent() {
		try {
			if (input.ready()) {
				if ((inputToken = input.readLine()) != null) {
					mb.putInInQueue(jsonInterpreter.fromJson(inputToken, Transmission.class));
					transmissionReceived();
				}
			}
			// Send Packets
			if (mb.getOutQueueSize() > 0) {
				outputToken = jsonInterpreter.toJson(mb.getFromOutQueue());
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	protected abstract void transmissionReceived();
	
	@Override
	public abstract void primeHandler(Map<String, String> setupMap);

	@Override
	public boolean ready() {
		return ready;
	}
	
	public void reviveDeadSession(StemClientSession session) {
		this.session.resetExistingConnection(session);
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
	
	public BufferedReader getInput() {
		return input;
	}

	public PrintWriter getOutput() {
		return output;
	}
	
	public void resetStreams(PrintWriter w, BufferedReader r) {
		input = (BufferedReader)r;
		output = (PrintWriter)w;
	}

}

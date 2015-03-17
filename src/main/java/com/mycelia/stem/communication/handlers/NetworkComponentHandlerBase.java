package com.mycelia.stem.communication.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import com.mycelia.stem.communication.ComDock;
import com.mycelia.stem.communication.StemClientSession;

public class NetworkComponentHandlerBase implements IHandler {

	protected String ip = "";
	protected String mac = "";
	protected String hashID = "";

	protected boolean ready = false;
	protected StemClientSession session;
	protected BufferedReader input;
	protected PrintWriter output;
	
	@Override
	public void primeHandler(Map<String, String> setupMap) {
	}
	
	public void setSession(StemClientSession session) {
		if (ComDock.getNetworkComponentbyHash(hashID) != null) {
			System.out.println("&&&&&&&&&%%%%%%%%%%%%%%&&&&&&&&&&&&&&&&");
			ComDock.getNetworkComponentbyHash(hashID).reviveDeadSession(session);
		} else {
			ComDock.addNewNetworkComponent(hashID, this);
			this.session = session;
			input = (BufferedReader) session.getReader();
			output = (PrintWriter) session.getWriter();
			ready = true;
		}
	}

	@Override
	public void handleComponent() throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	public void reviveDeadSession(StemClientSession session) {
		this.session.resetExistingConnection(session);
	}
	
	@Override
	public boolean ready() {
		return ready;
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

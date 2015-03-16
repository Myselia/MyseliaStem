package com.mycelia.stem.communication.handlers;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.Map;

import com.mycelia.stem.communication.StemClientSession;

public class NetworkComponentHandlerBase implements IHandler {

	protected String ip = "";
	protected String mac = "";

	protected boolean ready = false;
	protected StemClientSession session;
	protected BufferedReader input;
	protected PrintWriter output;
	
	@Override
	public void primeHandler(Map<String, String> setupMap) {
		// TODO Auto-generated method stub
	}
	
	public void setSession(StemClientSession session) {
		this.session = session;
		input = (BufferedReader) session.getReader();
		output = (PrintWriter) session.getWriter();
		ready = true;
	}

	@Override
	public void handleComponent() throws SocketException {
		// TODO Auto-generated method stub
		
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
	
	public BufferedReader getInput() {
		return input;
	}

	public PrintWriter getOutput() {
		return output;
	}

}

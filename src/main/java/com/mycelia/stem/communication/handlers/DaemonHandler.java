package com.mycelia.stem.communication.handlers;

import java.util.Map;

import com.mycelia.common.communication.structures.MailBox;
import com.mycelia.common.communication.units.Transmission;

public class DaemonHandler extends ComponentHandlerBase {
	
	public DaemonHandler() {
	}

	
	public void handleComponent() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void primeHandler(Map<String, String> setupMap) {
		this.ip = setupMap.get("ip");
		this.mac = setupMap.get("mac");
	}
	
	@Override
	protected void transmissionReceived() {
		// TODO Auto-generated method stub
		
	}
	
	public String toString() {
		return "TYPE: DAEMON, " + "IP: " + this.ip + ", MAC: " + this.mac;
	}
	
	@Override
	public MailBox<Transmission> getMailBox() {
		return this.mb;
	}
}

package com.mycelia.stem.communication.handlers;

import java.util.Map;

public class DaemonHandler extends NetworkComponentHandlerBase {
	
	
	public DaemonHandler() {
	}

	@Override
	public void primeHandler(Map<String, String> setupMap) {
		this.ip = setupMap.get("ip");
		this.mac = setupMap.get("mac");
	}
	
	@Override
	public void handleComponent() {
		// TODO Auto-generated method stub
		
	}
	
	public String toString() {
		return "TYPE: DAEMON, " + "IP: " + this.ip + ", MAC: " + this.mac;
	}

}

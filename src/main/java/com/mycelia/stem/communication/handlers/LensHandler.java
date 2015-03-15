package com.mycelia.stem.communication.handlers;

import java.util.Map;

import com.mycelia.stem.databank.DataStore;

public class LensHandler implements IHandler {
	

	private String ip = "";
	private String mac = "";
	private DataStore datastore = null;
	
	public LensHandler() {
	}

	@Override
	public void primeHandler(Map<String, String> setupMap) {
		this.ip = setupMap.get("ip");
		this.mac = setupMap.get("map");
	}
	
	@Override
	public void handleComponent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIP(String ip) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getIP() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMAC(String mac) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getMAC() {
		// TODO Auto-generated method stub
		return null;
	}

	

}

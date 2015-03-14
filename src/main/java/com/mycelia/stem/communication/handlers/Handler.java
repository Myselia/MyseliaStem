package com.mycelia.stem.communication.handlers;

import com.mycelia.stem.databank.DataStore;

public abstract class Handler {
	private String ip = "";
	private String mac = "";
	protected DataStore datastore;
	
	protected Handler(){}
	
	protected Handler(String ip, String mac){
		this.ip = ip;
		this.mac = mac;
	}

	public void setIP(String ip){	this.ip = ip; }
	public String getIP(){ return this.ip; }
	
	public void setMAC(String mac){	this.mac = mac; }
	public String getMAC(){ return this.mac; }

}

package com.mycelia.stem.communication.handlers;

import java.util.Map;


public interface IHandler {
	
	
	/*
	 * ##############################|        |##############################
	 * ##############################| PUBLIC |##############################
	 * ##############################|        |##############################
	 */
	void primeHandler(Map<String, String> setupMap);
	public void handleComponent();
	
	/*
	 * ##############################|		GET		|##############################
	 * ##############################|		AND		|##############################
	 * ##############################|		SET		|##############################
	 */
	public void setIP(String ip);

	public String getIP();

	public void setMAC(String mac);

	public String getMAC();
	

}

package com.mycelia.stem.communication.handlers;

import java.net.SocketException;
import java.util.Map;


public interface IHandler {
	
	
	/*
	 * ##############################|        |##############################
	 * ##############################| PUBLIC |##############################
	 * ##############################|        |##############################
	 */
	public void primeHandler(Map<String, String> setupMap);
	public void handleComponent() throws SocketException;
	public boolean ready();
	
	/*
	 * ##############################|		GET		|##############################
	 * ##############################|		AND		|##############################
	 * ##############################|		SET		|##############################
	 */

}

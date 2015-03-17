package com.mycelia.stem.communication.handlers;

import java.io.IOException;
import java.util.Map;


public interface IHandler {
	
	
	/*
	 * ##############################|        |##############################
	 * ##############################| PUBLIC |##############################
	 * ##############################|        |##############################
	 */
	public void primeHandler(Map<String, String> setupMap);
	public void handleComponent() throws IOException;
	public boolean ready();
	
	/*
	 * ##############################|		GET		|##############################
	 * ##############################|		AND		|##############################
	 * ##############################|		SET		|##############################
	 */

}

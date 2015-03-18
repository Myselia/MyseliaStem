package com.mycelia.stem.communication.handlers;

import java.util.Map;


public interface Handler {
	
	
	/*
	 * ##############################|        |##############################
	 * ##############################| PUBLIC |##############################
	 * ##############################|        |##############################
	 */
	public void primeHandler(Map<String, String> setupMap);
	public void handleComponent();
	public boolean ready();
	
	/*
	 * ##############################|		GET		|##############################
	 * ##############################|		AND		|##############################
	 * ##############################|		SET		|##############################
	 */

}

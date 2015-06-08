package com.myselia.stem.communication.handlers;

import java.io.IOException;
import java.util.Map;

import com.myselia.javacommon.communication.units.Transmission;


public interface Handler {
	
	
	/*
	 * ##############################|        |##############################
	 * ##############################| PUBLIC |##############################
	 * ##############################|        |##############################
	 */
	public void primeHandler(Map<String, String> setupMap);
	public void handleComponent(Transmission t) throws IOException;
	public boolean ready();
	
	/*
	 * ##############################|		GET		|##############################
	 * ##############################|		AND		|##############################
	 * ##############################|		SET		|##############################
	 */

}

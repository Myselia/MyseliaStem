package com.mycelia.stem.communication;

import java.util.ArrayList;

import com.mycelia.stem.communication.seekers.ISeek;
import com.mycelia.stem.communication.seekers.SeekImpl_localNetwork;

public class ComDock {

	public static int Component_Listen_Port = 42071;
	public static int Stem_Listen_Port = 42069;
	public static int Stem_IP;
	//private int lensPort, daemonPort, defaultPort;
	private BroadCaster seeker;
	
	private static Server serverRunnable = new Server(Stem_Listen_Port, 100);
	private static Thread serverThread;
	
	public enum componentType {
		DAEMON,
		LENS,
		SANDBOX
	};
	
	public ComDock() {
		System.out.println("Initializing ComDock");
		seeker = new BroadCaster();
		
		//Initialize main server thread
		serverThread = new Thread(serverRunnable);
	}
	
	/*
	 * ##############################|        |##############################
	 * ##############################| PUBLIC |##############################
	 * ##############################|        |##############################
	 */
	
	/*
	 *__________________SEEK METHODS__________________
	 * 
	 * These methods are used to activate the seeking 
	 * functionality of the ComDock. By calling one or
	 * more of these methods, the Stem will attempt to 
	 * find and register the desired MyCelia component.
	 * 
	 *__________________SEEK METHODS__________________
	 */
	public void seekLenses(ArrayList<ISeek> seekers) {
		seeker.seekLenses(seekers);
	}
	
	public void seekDaemons(ArrayList<ISeek> seekers) {
		seeker.seekDaemons(seekers);
	}
	
	public void seekSandboxes(ArrayList<ISeek> seekers) {
		seeker.seekSandboxes(seekers);
	}
	
	/*
	 * ##############################|         |##############################
	 * ##############################| PRIVATE |##############################
	 * ##############################|         |##############################
	 */
	


	
	
}

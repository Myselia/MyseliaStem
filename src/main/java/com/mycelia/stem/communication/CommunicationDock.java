package com.mycelia.stem.communication;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.mycelia.stem.communication.handlers.ComponentHandlerBase;
import com.mycelia.stem.communication.seekers.Seek;

public class CommunicationDock {

	public static int Component_Listen_Port = 42068;
	public static int Stem_Communication_Port = 42069;
	public static int Stem_Broadcast_Port = 42070;
	//FIX THIS TO BE MORE DYNAMIC!
	public static String Stem_IP;
	public static Set<String> reqSet;
	/* 
	 * TODO: This should be put in a DAO of some sort
	 */
	private static Map<String, ComponentHandlerBase> connectedDeviceMap;
	private Broadcaster seeker;
	private Thread serverThread;
	
	static {
		/*
		 * INITIALIZATION PACKET FIELD REQUIREMENTS FOR COMPONENTS THAT WISH TO CONNECT
		 */
		reqSet = new HashSet<String>();
		reqSet.add("ip");
		reqSet.add("type");
		reqSet.add("mac");
		reqSet.add("hashID");
	}

	public CommunicationDock() {
		Stem_IP = getLocalIP();
		System.out.println("Initializing ComDock with local IP - " + Stem_IP);
		
		connectedDeviceMap = new ConcurrentHashMap<String, ComponentHandlerBase>();
		seeker = new Broadcaster();

		// Initialize main server thread
		serverThread = new Thread(new StemServer(Stem_Communication_Port, 100));
		serverThread.start();
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
	public void seekLenses(ArrayList<Seek> seekers) {
		seeker.seekLenses(seekers);
	}
	
	public void seekDaemons(ArrayList<Seek> seekers) {
		seeker.seekDaemons(seekers);
	}
	
	public void seekSandboxes(ArrayList<Seek> seekers) {
		seeker.seekSandboxes(seekers);
	}
	
	/*
	 * ##############################| |##############################
	 * ##############################| PRIVATE |##############################
	 * ##############################| |##############################
	 */

	private String getLocalIP() {
		InetAddress ip = null;
		try {
			ip = Inet4Address.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ip.getHostAddress();
	}
	
	public static void addNewNetworkComponent(String hashID, ComponentHandlerBase handler) {
		connectedDeviceMap.put(hashID, handler);
	}
	
	public static ComponentHandlerBase getNetworkComponentbyHash(String hashID) {
		return connectedDeviceMap.get(hashID);
	}	
	
}

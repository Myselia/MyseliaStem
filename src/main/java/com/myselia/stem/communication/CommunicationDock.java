package com.myselia.stem.communication;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.myselia.stem.Main;
import com.myselia.stem.communication.handlers.ComponentHandlerBase;
import com.myselia.stem.communication.seekers.Seek;

public class CommunicationDock {

	public static final int Component_Listen_Port = 42068;
	public static final int Stem_Communication_Port = 42069;
	public static final int Stem_Broadcast_Port = 42070;
	public static final int Stem_HTTP_Port = 42071;
	
	
	public static String Stem_IP;
	public static Set<String> reqSet;
	private static Map<String, ComponentHandlerBase> connectedDeviceMap;
	private Broadcaster seeker;

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
	}

	/*
	 * ##############################| |##############################
	 * ##############################| PUBLIC |##############################
	 * ##############################| |##############################
	 */

	public void startServers() {
		try {
			Main.startSeeking();
			new StemServer(Stem_Communication_Port).run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 *__________________SEEK METHODS__________________
	 * 
	 * These methods are used to activate the seeking 
	 * functionality of the ComDock. By calling one or
	 * more of these methods, the Stem will attempt to 
	 * find and register the desired myselia component.
	 * 
	 *__________________SEEK METHODS__________________
	 */
	
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

	//TODO: Fix to work on linux machines (currently gets lo)
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

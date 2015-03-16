package com.mycelia.stem.communication;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mycelia.stem.communication.seekers.ISeek;

public class ComDock {

	public static int Component_Listen_Port = 42068;
	public static int Stem_Listen_Port = 42069;
	//FIX THIS TO BE MORE DYNAMIC!
	public static String Stem_IP;
	private BroadCaster seeker;

	private Thread serverThread;

	public ComDock() {
		Stem_IP = getLocalIP();
		System.out.println("Initializing ComDock with local IP - " + Stem_IP);
		
		seeker = new BroadCaster();

		// Initialize main server thread
		serverThread = new Thread(new StemServer(Stem_Listen_Port, 100));
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

	/*private InetAddress getServerNetworkBroadcast() throws SocketException {
		Enumeration<NetworkInterface> interfaces = null;
		InetAddress broadcast = null;

		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (interfaces.hasMoreElements()) {
			NetworkInterface networkInterface = interfaces.nextElement();

			if (networkInterface.isLoopback())
				continue;

			for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
				broadcast = interfaceAddress.getBroadcast();
				//System.out.println("BCAST FIND: " + broadcast);

				if (broadcast == null)
					continue;
			}
		}

		return broadcast;
	}*/
	
	
}

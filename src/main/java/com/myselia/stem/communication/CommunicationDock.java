package com.myselia.stem.communication;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.myselia.javacommon.communication.mail.MailService;
import com.myselia.javacommon.constants.opcode.ComponentType;
import com.myselia.javacommon.topology.ComponentCertificate;
import com.myselia.javacommon.topology.MyseliaUUID;
import com.myselia.stem.Main;
import com.myselia.stem.communication.handlers.ComponentHandlerBase;
import com.myselia.stem.communication.seekers.Seek;

public class CommunicationDock {

	public static final int Component_Listen_Port = 42068;
	public static final int Stem_Communication_Port = 42069;
	public static final int Stem_Broadcast_Port = 42070;

	public static Set<String> reqSet;
	public static ComponentCertificate stemCertificate;
	private static Map<MyseliaUUID, ComponentCertificate> uuidMap;
	private static Map<ComponentCertificate, ComponentHandlerBase> connectedDeviceMap;
	private Broadcaster seeker;
	private MailService s;

	public CommunicationDock(ComponentCertificate stemCertificate) {
		CommunicationDock.stemCertificate = stemCertificate;
		
		System.out.println("Initializing ComDock with local IP - " + stemCertificate.getIpAddress());
		
		s = new MailService(ComponentType.STEM, CommunicationDock.getStemCertificate().getUUID());
		connectedDeviceMap = new ConcurrentHashMap<ComponentCertificate, ComponentHandlerBase>();
		uuidMap = new ConcurrentHashMap<MyseliaUUID, ComponentCertificate>();
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
	
	public static ComponentCertificate getStemCertificate() {
		return stemCertificate;
	}

	/*
	 * __________________SEEK METHODS__________________
	 * 
	 * These methods are used to activate the seeking functionality of the
	 * ComDock. By calling one or more of these methods, the Stem will attempt
	 * to find and register the desired myselia component.
	 * 
	 * __________________SEEK METHODS__________________
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

	// TODO: IMPROVE THESE -Sylvain
	public static void addNetworkComponent(ComponentCertificate cert, ComponentHandlerBase handler) {
		connectedDeviceMap.put(cert, handler);
		System.out.println(cert.toString());
		uuidMap.put(cert.getUUID(), cert);
	}

	public static void removeNetworkComponent(ComponentCertificate cert) {
		connectedDeviceMap.remove(cert);
		uuidMap.remove(cert.getUUID());
	}

	public static ComponentHandlerBase getComponentHandlerByCert(ComponentCertificate cert) {
		return connectedDeviceMap.get(cert);
	}

}

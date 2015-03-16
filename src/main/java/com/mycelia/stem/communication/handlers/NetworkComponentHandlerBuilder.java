package com.mycelia.stem.communication.handlers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.mycelia.common.communication.structures.Atom;
import com.mycelia.common.communication.structures.Transmission;
import com.mycelia.stem.communication.StemClientSession;

public class NetworkComponentHandlerBuilder {
	
	private static Set<String> reqSet;
	private static Map<String, String> setupMapPrototype;
	private static Map<String, String> setupMap;
	
	public static int createdCount = 0;
	
	static {
		setRequirements();
		prepareSetupMap(reqSet);
	}
	
	private static void setRequirements() {
		reqSet = new HashSet<String>();
		/*
		 * Add fields absolutely required in the setup packet here
		 */
		reqSet.add("ip");
		reqSet.add("type");
		reqSet.add("mac");
	}
	
	private static void prepareSetupMap(Set<String> s) {
		setupMapPrototype = new HashMap<String, String>();
		Iterator<String> it = s.iterator();
		while(it.hasNext()) {
			String requiredField = it.next();
			System.out.println("Found a required field: " + requiredField);
			setupMapPrototype.put(requiredField, "");
		}
		setupMap = new HashMap<String, String>(setupMapPrototype);
	}
	
	private static void resetSetupMap() {
		setupMap = new HashMap<String, String>(setupMapPrototype);
	}

	public static synchronized NetworkComponentHandlerBase createHandler(Transmission setupPacket, StemClientSession clientSession) {
		NetworkComponentHandlerBase newComponent = null;
		int reqCount = reqSet.size();
		
		Iterator<Atom> it = setupPacket.get_atoms().iterator();
		while (it.hasNext()) {
			if (reqCount == 0)
				break;
			Atom a = it.next();
			String atomField = a.get_field();
			if (reqSet.contains(atomField)) {
				setupMap.put(atomField, a.get_value());
				reqCount--;
			}
		}
		
		if (reqCount == 0) {
			switch (setupMap.get("type")) {
			case "DAEMON":
				newComponent = new DaemonHandler();
				break;
			case "LENS":
				newComponent = new LensHandler();
				break;
			case "SANDBOX":
				newComponent = new SandboxHandler();
				break;
			}
			// All required fields fulfilled
			newComponent.primeHandler(setupMap);
			newComponent.setSession(clientSession);
			createdCount++;
		} else {
			// There is a setup field missing, throw an exception and allow the
			// Server to retry
			System.err.println("Could not create a network component handler!");
		}

		//Reset the map for the next component that comes along
		resetSetupMap();
		return newComponent;
	}

}

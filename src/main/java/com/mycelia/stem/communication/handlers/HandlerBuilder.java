package com.mycelia.stem.communication.handlers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.mycelia.common.communication.structures.Atom;
import com.mycelia.common.communication.structures.Transmission;

public class HandlerBuilder {
	
	private static Set<String> reqSet;
	private static Map<String, String> setupMap;
	
	static {
		setRequirements();
	}
	
	private static void setRequirements() {
		reqSet = new HashSet<String>();
		reqSet.add("ip");
		reqSet.add("type");
		reqSet.add("mac");
	}
	
	private static void prepareSetupMap(Set<String> s) {
		Iterator<String> it = s.iterator();
		while(it.hasNext()) {
			String requiredField = it.next();
			setupMap.put(requiredField, "");
		}
	}

	public static synchronized IHandler createHandler(Transmission setupPacket) {
		IHandler newComponent = null;
		/*
		 * Expects IP, Component Type, MAC
		 */
		int reqCount = reqSet.size();
		prepareSetupMap(reqSet);
		
		Iterator<Atom> it = setupPacket.get_atoms().iterator();
		while (it.hasNext()) {
			Atom a = it.next();
			String atomField = a.get_field();
			if (atomField.equals(reqSet.contains(atomField))) {
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
		} else {
			// There is a setup field missing, throw an exception and allow the
			// Server to retry
			System.out.println("FAILURE RIGHT HERE, YEAH?");
		}

		//Reset the map for the next component that comes along
		prepareSetupMap(reqSet);
		return newComponent;
	}

}

package com.mycelia.stem.communication.handlers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.mycelia.common.communication.units.Atom;
import com.mycelia.common.communication.units.Transmission;
import com.mycelia.common.constants.ComponentType;
import com.mycelia.stem.communication.CommunicationDock;
import com.mycelia.stem.communication.StemClientSession;

public class ComponentHandlerFactory {

	private static Map<String, String> setupMapPrototype;
	private static Map<String, String> setupMap;

	static {
		prepareSetupMap(CommunicationDock.reqSet);
	}

	/**
	 * Creates an appropriate network component handler.
	 * 
	 * @param setupPacket
	 *            The setup packet sent from the component during the handshake.
	 *            Determines type of component and its identification fields.
	 * @param clientSession
	 *            The client session handling the component that will be handed
	 *            to the handler.
	 * @return A fully setup handler object ready for processing.
	 */
	public static synchronized ComponentHandlerBase createHandler(Transmission setupPacket, StemClientSession clientSession) {
		ComponentHandlerBase newComponent = null;
		int reqCount = CommunicationDock.reqSet.size();

		Iterator<Atom> it = setupPacket.get_atoms().iterator();
		while (it.hasNext()) {
			if (reqCount == 0)
				break;
			Atom a = it.next();
			String atomField = a.get_field();
			if (CommunicationDock.reqSet.contains(atomField)) {
				setupMap.put(atomField, a.get_value());
				reqCount--;
			}
		}

		if (reqCount == 0) {
			switch (ComponentType.valueOf(setupMap.get("type"))) {
			case DAEMON:
				newComponent = new DaemonHandler();
				break;
			case LENS:
				newComponent = new LensHandler();
				break;
			case SANDBOXMASTER:
				newComponent = new SandboxHandler();
				break;
			case DATABASE:
				newComponent = null;
				break;
			case SANDBOXSLAVE:
				// Does not accept!
				break;
			case STEMMASTER:
				// Does not accept!
				break;
			case STEMSLAVE:
				newComponent = null;
				break;
			}
			// All required fields fulfilled
			newComponent.primeHandler(setupMap);
			newComponent.setSession(clientSession);
		} else {
			// There is a setup field missing, throw an exception and allow the
			// Server to retry
			System.err.println("Could not create a network component handler!");
		}

		// Reset the map for the next component that comes along
		resetSetupMap();
		return newComponent;
	}

	/**
	 * Run once at static init. Takes a set of fields that the component must
	 * send in the handshake packet to be properly initialized.
	 * 
	 * @param s
	 *            The set of required parameters.
	 */
	private static void prepareSetupMap(Set<String> s) {
		setupMapPrototype = new HashMap<String, String>();
		Iterator<String> it = s.iterator();
		while (it.hasNext()) {
			String requiredField = it.next();
			System.out.println("Found a required field: " + requiredField);
			setupMapPrototype.put(requiredField, "");
		}
		setupMap = new HashMap<String, String>(setupMapPrototype);
	}

	/**
	 * This method is run every time the builder finishes creating a handler. It
	 * resets the setupMap that is passed to the handler for initialization to
	 * the empty prototype, ensuring it is ready to create a new handler.
	 */
	private static void resetSetupMap() {
		setupMap = new HashMap<String, String>(setupMapPrototype);
	}

}

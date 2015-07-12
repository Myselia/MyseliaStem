package com.myselia.stem.communication.handlers;

import java.util.Iterator;

import com.google.gson.Gson;
import com.myselia.javacommon.communication.units.Atom;
import com.myselia.javacommon.communication.units.Transmission;
import com.myselia.javacommon.topology.ComponentCertificate;
import com.myselia.stem.communication.StemClientSession;

public class ComponentHandlerFactory {

	private static Gson jsonInterpreter = new Gson();

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
		ComponentCertificate cert = null;
		
		System.err.println("SETUP PACKET: " + setupPacket.toString());
		Iterator<Atom> it = setupPacket.get_atoms().iterator();
		while (it.hasNext()) {
			Atom a = it.next();
			String atomValue = a.get_value();
			
			cert = jsonInterpreter.fromJson(atomValue, ComponentCertificate.class);
			break;
		}

		switch (cert.getComponentType()) {
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
		case STEM:
			// Does not accept!
			break;
		}
		
		// All required fields fulfilled
		newComponent.primeHandler(cert);
		newComponent.setSession(clientSession);

		return newComponent;
	}
}

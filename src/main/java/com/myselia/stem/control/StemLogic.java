package com.myselia.stem.control;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myselia.javacommon.communication.mail.Addressable;
import com.myselia.javacommon.communication.mail.MailBox;
import com.myselia.javacommon.communication.mail.MailService;
import com.myselia.javacommon.communication.units.Atom;
import com.myselia.javacommon.communication.units.Transmission;
import com.myselia.javacommon.constants.opcode.ComponentType;
import com.myselia.javacommon.constants.opcode.OpcodeBroker;
import com.myselia.javacommon.constants.opcode.Operation;
import com.myselia.javacommon.constants.opcode.operations.DaemonOperation;
import com.myselia.javacommon.topology.MyseliaRoutingTable;
import com.myselia.javacommon.topology.MyseliaUUID;

public class StemLogic implements Addressable {

	private MailBox<Transmission> mb;
	private static Gson jsonInterpreter = new Gson();
	private String[] actionTypes = { "CONFIG" };

	public StemLogic() {
		mb = new MailBox<Transmission>();
	//	registerOpcodes();
		
		//MailService.register("CONFIG_TABLEBROADCAST", this);
		MailService.register("DAEMON_CONFIG", this);
		MailService.register("STEM_CONFIG", this);
	}

	private void processTransmission(Transmission trans) {

		// Get relevant header info
		String header = trans.get_header().get_from();

		// Deduce Myselia internal objects from header
		ComponentType componentType = OpcodeBroker.deduceComponent(header);
		Operation o = OpcodeBroker.deduceOperation(header);

		switch (componentType) {
		case DAEMON:
			handleDaemon(trans);
			break;
		case DATABASE:
			break;
		case LENS:
			break;
		case SANDBOXMASTER:
			break;
		case SANDBOXSLAVE:
			break;
		case STEM:
			break;
		default:
			break;
		}
	}

	private void handleDaemon(Transmission trans) {
		String header = trans.get_header().get_from();
		DaemonOperation o = (DaemonOperation) OpcodeBroker.deduceOperation(header);

		switch (o) {
		case BROADCAST:
			break;
		case FETCH:
			break;
		case STARTSANDBOX:
			break;
		case TABLEBROADCAST:
			Iterator<Atom> it = trans.get_atoms().iterator();
			MyseliaRoutingTable clientTable = null;
			
			while (it.hasNext()) {
				Atom a = it.next();
				String atomValue = a.get_value();
				System.out.println("Routing Table from Client is: ");
				System.out.println("||" + atomValue + "||");
			
				//This is the exact location Sylvain almost lost his mind.
				clientTable = jsonInterpreter.fromJson(atomValue, MyseliaRoutingTable.class);
				
				System.out.println(clientTable);
			}
			
			MailService.routingTable.updateTablePropagation(clientTable);
			
			System.out.println(clientTable);
			
			System.out.println("STEM TABLE: " + MailService.routingTable);
			break;
		default:
			break;
		}
	}

	private void registerOpcodes() {
		for (String action : actionTypes) {
			MailService.register("LENS_" + action, this);
			MailService.register("DAEMON_" + action, this);
			MailService.register("SANDBOXMASTER_" + action, this);
		}
	}

	@Override
	public void in(Transmission trans) {
		// The Stem Logic component has received a transmission for processing.
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("TRANSMISSION WITH OPCODE: " + trans.get_header().get_from());
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		processTransmission(trans);

		// mb.enqueueIn(trans);
		// MailService.notify(this);
	}

	@Override
	public Transmission out() {
		return mb.dequeueOut();
	}

}

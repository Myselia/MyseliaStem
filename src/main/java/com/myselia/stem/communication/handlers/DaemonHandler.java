package com.myselia.stem.communication.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;
import com.myselia.javacommon.communication.mail.MailService;
import com.myselia.javacommon.communication.units.Atom;
import com.myselia.javacommon.communication.units.Transmission;

public class DaemonHandler extends ComponentHandlerBase {
	
	private Gson json = new Gson();
	public DaemonHandler() {
	}
	
	@Override
	public void primeHandler(Map<String, String> setupMap) {
		this.ip = setupMap.get("ip");
		this.mac = setupMap.get("mac");
		this.hashID = setupMap.get("hashID");
	}
	
	public void handleComponent(Transmission t) throws IOException {
		super.handleComponent(t);
	}

	@Override
	protected void transmissionReceived() {
		System.out.println("Daemon Receive:" + "\n\t|-> Hash: " + getHashID());

		// Temporary Adapter
		Transmission t = mailbox.dequeueIn();

		if (t.get_header().get_to().equals("LENS_RUNTIME_DATA")) {
			// Add Time Field
			long unixTime = System.currentTimeMillis() / 1000L;
			Atom a = new Atom("Time", "long", Long.toString(unixTime));
			ArrayList<Atom> alist = new ArrayList<Atom>();
			alist.add(a);
			t.add_atoms(alist);

			// Header opcode adapter
			String s = json.toJson(t, Transmission.class);
			String s2 = s.replace("LENS_RUNTIME_DATA", "LENS:1234_RUNTIME_DATA");
			Transmission t2 = json.fromJson(s2, Transmission.class);
			// ~Temporary Adapter

			// Put the trans out for redirection
			mailbox.enqueueOut(t2);
		} else {
			mailbox.enqueueOut(t);
		}
		
		MailService.notify(this);
	}

	public String toString() {
		return "TYPE: DAEMON, " + "IP: " + this.ip + ", MAC: " + this.mac + ", HASHID: " + hashID;
	}

}

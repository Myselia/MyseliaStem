package com.myselia.stem.communication.handlers;

import java.io.IOException;

import com.myselia.javacommon.communication.mail.MailService;
import com.myselia.javacommon.communication.units.Transmission;

public class DaemonHandler extends ComponentHandlerBase {
	
	public DaemonHandler() {
	}
	
	public void handleComponent(Transmission t) throws IOException {
		super.handleComponent(t);
	}

	@Override
	protected void transmissionReceived(Transmission t) {

		/*if (t.get_header().get_to().equals("LENS_RUNTIME_DATA")) {
			System.out.println("WRAPPING LENS_RUNTIME_DATA IN NEW FORMAT");
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
		}*/
		
		mailbox.enqueueOut(t);
		MailService.notify(this);
	}

	@Override
	protected void endpointReceive() {
		write(mailbox.dequeueIn());
	}

}

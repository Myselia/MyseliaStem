package com.myselia.stem;

import java.util.ArrayList;

import com.myselia.stem.communication.CommunicationDock;
import com.myselia.stem.communication.seekers.LocalNetworkSeek;
import com.myselia.stem.communication.seekers.Seek;
import com.myselia.stem.control.ConsoleUnit;
import com.myselia.stem.databank.Databank;

public class Main {
	long id = 2L;

	private static Thread databank_thread;
	private static Thread console_thread;

	private static Databank databank = new Databank();
	private static ConsoleUnit console = new ConsoleUnit();
	private static CommunicationDock comDock;

	public static void main(String[] args) {
		System.out.println("MyseliaStem Initialization... ");

		console_thread = new Thread(console);
		console_thread.start();

		databank_thread = new Thread(databank);
		databank_thread.start();
		
		comDock = new CommunicationDock();	
		comDock.startServers();
	}
	
	public static void startSeeking() {
		ArrayList<Seek> seekerListDaemon = new ArrayList<Seek>();
		//seekerListDaemon.add(LocalHostSeek.getInstance());
		seekerListDaemon.add(LocalNetworkSeek.getInstance());
		comDock.seekDaemons(seekerListDaemon);
		comDock.seekSandboxes(seekerListDaemon);
	}
}

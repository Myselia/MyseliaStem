package com.mycelia.stem;

import java.util.ArrayList;

import com.mycelia.stem.communication.CommunicationDock;
import com.mycelia.stem.communication.seekers.LocalHostSeek;
import com.mycelia.stem.communication.seekers.LocalNetworkSeek;
import com.mycelia.stem.communication.seekers.Seek;
import com.mycelia.stem.control.ConfigHandler;
import com.mycelia.stem.control.ConsoleUnit;
import com.mycelia.stem.databank.Databank;

public class Main {
	long id = 2L;

	public static final int DEFAULT_PORT = 42069;
	public static final int BROADCAST_PORT = 42071;
	public static final int NODECOM_PORT = 42068;

	public static final boolean DEBUG = true;

	private static Thread databank_thread;
	private static Thread console_thread;

	private static Databank databank = new Databank();
	private static ConsoleUnit console = new ConsoleUnit();
	private static CommunicationDock comDock;

	public static void main(String[] args) {
		System.out.print("MyceliaStem setup... ");
		
		ConfigHandler.init();

		console_thread = new Thread(console);
		console_thread.start();

		databank_thread = new Thread(databank);
		databank_thread.start();
		
		comDock = new CommunicationDock();
		
		ArrayList<Seek> seekerListLens = new ArrayList<Seek>();
		seekerListLens.add(LocalHostSeek.getInstance());
		comDock.seekLenses(seekerListLens);
		

		ArrayList<Seek> seekerListDaemon = new ArrayList<Seek>();
		//seekerListSandbox.add(LocalHostSeek.getInstance());
		seekerListDaemon.add(LocalHostSeek.getInstance());
		seekerListDaemon.add(LocalNetworkSeek.getInstance());
		comDock.seekDaemons(seekerListDaemon);
		
	}
}

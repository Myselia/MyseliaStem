package com.mycelia.stem;

import java.util.ArrayList;

import com.mycelia.stem.communication.ComDock;
import com.mycelia.stem.communication.seekers.ISeek;
import com.mycelia.stem.communication.seekers.SeekImpl_echo;
import com.mycelia.stem.communication.seekers.SeekImpl_localHost;
import com.mycelia.stem.communication.seekers.SeekImpl_localNetwork;
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
	private static ComDock comDock;

	public static void main(String[] args) {
		System.out.print("MyceliaStem setup... ");
		
		ConfigHandler.init();

		console_thread = new Thread(console);
		console_thread.start();

		databank_thread = new Thread(databank);
		databank_thread.start();
		
		comDock = new ComDock();
				
		/*ArrayList<ISeek> seekerListDaemon = new ArrayList<ISeek>();
		seekerListDaemon.add(SeekImpl_localNetwork.getInstance());
		seekerListDaemon.add(SeekImpl_echo.getInstance());
		comDock.seekDaemons(seekerListDaemon);*/
		
		ArrayList<ISeek> seekerListLens = new ArrayList<ISeek>();
		//seekerListLens.add(SeekImpl_localNetwork.getInstance());
		//seekerListLens.add(SeekImpl_localNetwork.getInstance());
		//seekerListLens.add(SeekImpl_echo.getInstance());
		seekerListLens.add(SeekImpl_localHost.getInstance());
		comDock.seekLenses(seekerListLens);
		
	}
}

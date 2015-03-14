package com.mycelia.stem;

import com.mycelia.stem.control.ConsoleUnit;
import com.mycelia.stem.databank.Databank;

public class Main {
	
	public static final int DEFAULT_PORT = 42069;
	public static final int BROADCAST_PORT = 42071;
	public static final int NODECOM_PORT = 42068;
	
	public static final boolean DEBUG = true;
	
	private static Thread comdock_thread;
	private static Thread databank_thread;
	private static Thread console_thread;
	
	//private static CommunicationDock comdock = new CommunicationDock();
	private static Databank databank = new Databank();
	private static ConsoleUnit console = new ConsoleUnit();
	
	private static void main(String[] args){
		
		console_thread = new Thread(console);
		console_thread.start();
		
		databank_thread = new Thread(databank);
		databank_thread.start();
		
		//comdock_thread = new Thread (comdock);
		//comdock_thread.start();
		
	}
	

}

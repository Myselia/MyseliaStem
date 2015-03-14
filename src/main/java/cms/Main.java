package cms;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

import cms.communication.Broadcast;
import cms.control.CommandSystem;
import cms.control.ConfigHandler;
import cms.databank.OverLord;

import com.mycelia.stem.communication.ComDock;
import com.mycelia.stem.communication.seekers.ISeek;
import com.mycelia.stem.communication.seekers.SeekImpl_echo;
import com.mycelia.stem.communication.seekers.SeekImpl_localNetwork;

public class Main {
	
	public static final int DEFAULT_PORT = 6969; //TODO: change
	public static boolean REROUTE_ERR = false;	//Error Re-Routing to CMS Console
	
	private static Broadcast bcastRunnable = new Broadcast();
	//private static Server serverRunnable = new Server(DEFAULT_PORT, 100);
	
	//RENAME
	private static ComDock comDock;
	//~RENAME~
	private static Thread bCastCommunicator;
	//private static Thread communicator;
	private static Thread data;
	private static Thread display;
	private static Thread console;
	private static volatile JFrame frame;
	private static volatile boolean hasLocalGUI = false;
	
	public static void main(String[] args) {		
		loadCommands(); //loads the user commands
		ConfigHandler.init();

		//Model
		data = new Thread(new Runnable(){
			public void run() {
				OverLord.build();	
			}
		});
		//communicator = new Thread(serverRunnable);
		comDock = new ComDock();
		
	
		ArrayList<ISeek> seekerListDaemon = new ArrayList<ISeek>();
		seekerListDaemon.add(SeekImpl_localNetwork.getInstance());
		seekerListDaemon.add(SeekImpl_echo.getInstance());
		comDock.seekDaemons(seekerListDaemon);
		
		ArrayList<ISeek> seekerListLens = new ArrayList<ISeek>();
		//seekerListLens.add(SeekImpl_localNetwork.getInstance());
		seekerListLens.add(SeekImpl_echo.getInstance());
		comDock.seekLenses(seekerListLens);
		

		try {
			data.start();
			Thread.sleep(2000);
			//communicator.start();
		} catch (InterruptedException e) {
			System.out.println("e>Error starting main threads. Please restart.");
			//e.printStackTrace();
		}
		
		//View
		System.out.println("Welcome to the CMS v0.6 alpha");
		System.out.println("Enter 'help' for a list of commands");
		System.out.print("$> ");
		console = consoleThread();
		console.start();
	}

	private static void loadCommands(){
		try{
			CommandSystem.setClasses("cms.control.user");
		}catch(Exception e){
			System.out.println("Exception thrown by:" + e.getClass().getCanonicalName());
			System.out.println("Please force kill the application and investigate.");
		}
	}

	public static void startBCastThread(Runnable instance, Thread thread) {
		thread = new Thread(instance);
		setbCastCommunicator(thread);
		thread.start();
	}

	public static Broadcast getBcastRunnable() {
		return bcastRunnable;
	}

	public static Thread getbCastCommunicator() {
		return bCastCommunicator;
	}

	public static void setbCastCommunicator(Thread bCastCommunicator) {
		Main.bCastCommunicator = bCastCommunicator;
	}
	
	public static boolean hasGUI(){
		return Main.hasLocalGUI;
	}
	
	public static void toggleGUI(){
		if(hasLocalGUI){
			frame.dispose();
			frame = null;
			System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
			hasLocalGUI = false;
			console = consoleThread();
			console.start();
		}else{
			display = displayThread();
			display.start();
			hasLocalGUI = true;
		}
	}
	
	private static Thread consoleThread(){
		return new Thread(new Runnable(){
			public void run() {
				Scanner userIn = new Scanner(System.in);
				while(!hasLocalGUI){
					//TODO: Find why on kill of gui the scanner behaves differently from prior gui
					//System.out.println("listening");
					if(userIn.hasNextLine()){
						CommandSystem.index_reset();
						String make = userIn.nextLine();
						if (!make.equals("")) {
							CommandSystem.command(make);
							System.out.print("$> ");
						}
					}
				}
				userIn.close();
				userIn = null;
			}
		});
	}
	
	private static Thread displayThread(){
		return new Thread(new Runnable(){
			public void run() {
				System.out.println("Welcome to the CMS v0.6 alpha");
				System.out.println("Enter 'help' for a list of commands");
			}
		});
	}

}

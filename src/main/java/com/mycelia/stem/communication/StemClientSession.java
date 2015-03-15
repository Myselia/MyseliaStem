package com.mycelia.stem.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;

import com.google.gson.Gson;
import com.mycelia.common.communication.structures.Atom;
import com.mycelia.common.communication.structures.Transmission;
import com.mycelia.stem.communication.handlers.Handler;

public class StemClientSession implements Runnable {

	//Socket object to handle client transmission on transport layer
		protected Socket clientConnectionSocket = null;
		//Socket object to handle server transmission on transport layer
		protected String serverTransmission = null;
		private Handler componentHandler;
		//The client's IP address
		private String ipAddress;
		//Input stream buffer
		private BufferedReader input;
		//Output stream buffer
		private PrintWriter output;
		private Gson jsonParser;
		
		//-----!FLAGS!-----
		private boolean setupComplete = false;
		
		//The string currently being processed by the server (\r\n terminated from client)
		protected Gson gson = new Gson();
		private String inputS = "";
		
		public StemClientSession(Socket clientConnectionSocket, int componentID) {
			this.clientConnectionSocket = clientConnectionSocket;
			this.ipAddress = clientConnectionSocket.getInetAddress().getHostAddress();
			jsonParser = new Gson();
		}

		@Override
		public void run() {
			
			try {
				input =  new BufferedReader(new InputStreamReader(
						clientConnectionSocket.getInputStream()));
				output =  new PrintWriter(
						clientConnectionSocket.getOutputStream(), true);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			// !HANDLING HAPPENS HERE!
			try {
				System.out.println("WAITING FOR A TRANSMISSION FROM CLIENT!!!!!!!!!!!!!!!!!!!!!!!!!");
				while (((inputS = input.readLine() ) != null)) {
					//Wait for the setup packet from the newly connected component, once received handle it!
					if (!setupComplete) {
						//SETUP PHASE
						System.out.println("RECV: " + inputS);
						//handleSetupPacket(inputS);
					} else {
						//REGULAR HANDLE TICK
						
					}
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
		private void handleSetupPacket(String s) {
			Transmission trans = jsonParser.fromJson(s, Transmission.class);
			Iterator<Atom> it = trans.get_atoms().iterator();
			while (it.hasNext()) {
				Atom a = it.next();
				if (a.get_field().equals("componentType")) {
					//switch ()
				}
			}
		}
		
}

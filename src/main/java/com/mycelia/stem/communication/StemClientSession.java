package com.mycelia.stem.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;

public class StemClientSession implements Runnable {

	//Socket object to handle client transmission on transport layer
		protected Socket clientConnectionSocket = null;
		//Socket object to handle server transmission on transport layer
		protected String serverTransmission = null;
		//The client's IP address
		private String ipAddress;
		//This flag is true once the setup phase of the session is complete 
		private boolean SETUP = false;
		//This flag is true if the node is already known to the CMS by IP
		private boolean HAS_CONNECTED = false;
		//This is the id of the session which is the same as the node id in datastore
		private int sessionID = -1;
		//Input stream buffer
		private BufferedReader input;
		//Output stream buffer
		private PrintWriter output;
		
		//The string currently being processed by the server (\r\n terminated from client)
		protected Gson gson = new Gson();
		private String inputS = "";
		
		public StemClientSession(Socket clientConnectionSocket, int componentID) {
			this.clientConnectionSocket = clientConnectionSocket;
			this.ipAddress = clientConnectionSocket.getInetAddress().getHostAddress();
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
		
			// TODO Auto-generated method stub
			try {
				while (((inputS = input.readLine() ) != null)) {
					System.out.println("GOT RESPONSE FROM FAGGOT MACHINE: " + inputS);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
}

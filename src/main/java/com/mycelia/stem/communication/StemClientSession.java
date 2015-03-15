package com.mycelia.stem.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;

import com.google.gson.Gson;
import com.mycelia.common.communication.structures.Atom;
import com.mycelia.common.communication.structures.Transmission;
import com.mycelia.common.communication.structures.TransmissionBuilder;
import com.mycelia.common.constants.Constants.componentType;
import com.mycelia.stem.communication.handlers.DaemonHandler;
import com.mycelia.stem.communication.handlers.HandlerBuilder;
import com.mycelia.stem.communication.handlers.IHandler;
import com.mycelia.stem.communication.handlers.LensHandler;
import com.mycelia.stem.communication.handlers.SandboxHandler;

public class StemClientSession implements Runnable {

	// Socket object to handle client transmission on transport layer
	protected Socket clientConnectionSocket = null;
	// Socket object to handle server transmission on transport layer
	protected String serverTransmission = null;
	// Component handler that encapsulates MySelia Component behavior and
	// handling
	private IHandler componentHandler;
	// The client's IP address
	private String ipAddress;
	// Input stream used to receive data from MySelia Component
	private BufferedReader input;
	// The string currently being processed by the server (\r\n terminated from
	// client)
	private String inputS = "";
	// Output stream used to send data to MySelia Component
	private PrintWriter output;
	// Instance level jsonParser to transform received strings into Transmission
	// objects
	private Gson jsonParser;

	// -----!FLAGS!-----
	private boolean setupComplete = false;

	public StemClientSession(Socket clientConnectionSocket, int componentID) {
		this.clientConnectionSocket = clientConnectionSocket;
		this.ipAddress = clientConnectionSocket.getInetAddress().getHostAddress();
		jsonParser = new Gson();
	}

	@Override
	public void run() {

		try {
			input = new BufferedReader(new InputStreamReader(clientConnectionSocket.getInputStream()));
			output = new PrintWriter(clientConnectionSocket.getOutputStream(), true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// !HANDLING HAPPENS HERE!
		try {
			while ((inputS = input.readLine()) != null) {
				// Wait for the setup packet from the newly connected component,
				// once received handle it!
				if (!setupComplete) {
					System.out.println("RECV: " + inputS);
					/*
					 * THIS IS BAD AND FOR TESTING K?
					 */
					while (true) {
						// SETUP PHASE
						String packetToSend = buildTestPacket();
						System.out.print("Sending: " + packetToSend);
						output.println(packetToSend);
						System.out.println("\n......done!");
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//handleSetupPacket(inputS);
				} else {
					// REGULAR HANDLE TICK

				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleSetupPacket(String s) {
		try {
			Transmission setupTransmission = jsonParser.fromJson(s, Transmission.class);
			componentHandler = HandlerBuilder.createHandler(setupTransmission);
		} catch (Exception e) {
			System.out.println("Setup packet from component is malformed!");
			e.printStackTrace();
		}

	}
	static int count = 0;
	private static String buildTestPacket() {
		TransmissionBuilder tb = new TransmissionBuilder();
		Gson g = new Gson();
		
		
		tb.newTransmission(1000, "stem", "all");
		tb.newAtom("someNumber", "int", Integer.toString(count));
		Transmission t = tb.getTransmission();
		count++;
		return g.toJson(t);
	}

}

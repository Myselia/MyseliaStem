package com.mycelia.stem.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

import com.mycelia.stem.communication.handlers.NetworkComponentHandlerBase;
import com.mycelia.stem.communication.states.IConnectionState;

public class StemClientSession implements Runnable {

	// Socket object to handle client transmission on transport layer
	private Socket clientConnectionSocket = null;
	//private connectionStatus clientConnectionState = connectionStatus.PENDING_HANDSHAKE;
	private boolean isRunning = true; 
	public boolean componentAttached = false;
	// Component handler that encapsulates MySelia Component behavior and handling
	private NetworkComponentHandlerBase componentHandler;
	// Current state of the connection
	private IConnectionState clientConnectionState;
	// Container of all the state instances needed to decide what to do at each client tick
	private ConnectionStateContainer stateContainer;
	// The client's IP address
	private String ipAddress;
	// Input stream used to receive data from MySelia Component
	public volatile BufferedReader input;
	// Output stream used to send data to MySelia Component
	private volatile PrintWriter output;
	
	public StemClientSession(Socket clientConnectionSocket, int componentID) {
		this.clientConnectionSocket = clientConnectionSocket;
		this.ipAddress = clientConnectionSocket.getInetAddress().getHostAddress();

		setupStreams();

		this.stateContainer = new ConnectionStateContainer(this);
		this.clientConnectionState = stateContainer.getHandshakeState();
		componentAttached = true;
	}

	@Override
	public void run() {

		while (isRunning) {
			try {
				/*
				 * If you wish to modify what the components do, change the
				 * handleComponent method in their handlers located in
				 * stem.communication.handlers
				 */
				clientConnectionState.process();

			} catch (IOException e) {
				System.err.println("Client with IP " + componentHandler.getIp() + " has disconnected.");
				clientConnectionState = stateContainer.getDisconnectedState();
				componentAttached = false;
				try {
					clientConnectionSocket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
	}

	public IConnectionState getConnectionState() {
		return clientConnectionState;
	}
	
	public NetworkComponentHandlerBase getComponentHandler() {
		return componentHandler;
	}

	public void setComponentHandler(NetworkComponentHandlerBase componentHandler) {
		this.componentHandler = componentHandler;
	}

	public ConnectionStateContainer getStateContainer() {
		return stateContainer;
	}
	
	public void setConnectionState(IConnectionState state) {
		this.clientConnectionState = state;
	}
	
	
	public Writer getWriter() {
		return output;
	}
	
	public Reader getReader() {
		return input;
	}
	
	public void resetExistingConnection(StemClientSession session) {
		setClientConnectionSocket(session.getClientConnectionSocket());
		componentAttached = true;
	}
	
	public Socket getClientConnectionSocket() {
		return clientConnectionSocket;
	}

	private void setClientConnectionSocket(Socket clientConnectionSocket) {
		this.clientConnectionSocket = clientConnectionSocket;
		setupStreams();
		componentHandler.resetStreams(output, input);
	}

	public void die() {
		isRunning = false;
		try {
			System.err.println("Killing old StemClientSession Thread!");
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setupStreams() {
		try {
			input = new BufferedReader(new InputStreamReader(clientConnectionSocket.getInputStream()));
			output = new PrintWriter(clientConnectionSocket.getOutputStream(), true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
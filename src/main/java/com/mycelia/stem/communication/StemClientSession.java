package com.mycelia.stem.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.mycelia.stem.communication.handlers.ComponentHandlerBase;
import com.mycelia.stem.communication.states.ConnectionState;

public class StemClientSession implements Runnable {

	// Socket object to handle client transmission on transport layer
	private Socket clientConnectionSocket = null;
	//private connectionStatus clientConnectionState = connectionStatus.PENDING_HANDSHAKE;
	private boolean isRunning = true; 
	public boolean componentAttached = false;
	private boolean socketProblem = false;
	// Component handler that encapsulates MySelia Component behavior and handling
	private ComponentHandlerBase componentHandler;
	// Current state of the connection
	private ConnectionState clientConnectionState;
	// Container of all the state instances needed to decide what to do at each client tick
	private ConnectionStateContainer stateContainer;
	// Input stream used to receive data from MySelia Component
	public volatile BufferedReader input;
	// Output stream used to send data to MySelia Component
	private volatile PrintWriter output;
	private boolean isHTTP;
	
	public StemClientSession(Socket clientConnectionSocket, int componentID, boolean isHTTP) {
		this.isHTTP = isHTTP;
		this.clientConnectionSocket = clientConnectionSocket;
		//this.ipAddress = clientConnectionSocket.getInetAddress().getHostAddress();
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
				if (!output.checkError() || socketProblem) {
					clientConnectionState.process();
				} else {
					throw new IOException();
				}

			} catch (IOException e) {
				System.err.println("Session at " + this.toString() + " has broken stream!");
				clientConnectionState = stateContainer.getDisconnectedState();
				componentAttached = false;
				socketProblem = true;
				try {
					clientConnectionSocket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
	}

	public ConnectionState getConnectionState() {
		return clientConnectionState;
	}
	
	public ComponentHandlerBase getComponentHandler() {
		return componentHandler;
	}

	public void setComponentHandler(ComponentHandlerBase componentHandler) {
		this.componentHandler = componentHandler;
	}

	public ConnectionStateContainer getStateContainer() {
		return stateContainer;
	}
	
	public void setConnectionState(ConnectionState state) {
		this.clientConnectionState = state;
	}
	
	
	public PrintWriter getWriter() {
		return output;
	}
	
	public BufferedReader getReader() {
		return input;
	}
	
	public OutputStream getOutStream() {
		try {
			return clientConnectionSocket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public InputStream getInStream() {
		try {
			return clientConnectionSocket.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void resetExistingConnection(StemClientSession session) {
		setClientConnectionSocket(session.getClientConnectionSocket());
		componentAttached = true;
		socketProblem = false;
	}
	
	public Socket getClientConnectionSocket() {
		return clientConnectionSocket;
	}
	
	public boolean isHTTP() {
		return isHTTP;
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
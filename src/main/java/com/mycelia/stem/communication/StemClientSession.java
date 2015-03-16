package com.mycelia.stem.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.net.SocketException;

import com.mycelia.stem.communication.handlers.NetworkComponentHandlerBase;
import com.mycelia.stem.communication.states.IConnectionState;

public class StemClientSession implements Runnable {

	// Socket object to handle client transmission on transport layer
	private Socket clientConnectionSocket = null;
	//private connectionStatus clientConnectionState = connectionStatus.PENDING_HANDSHAKE;
	private boolean isRunning = true; 
	// Component handler that encapsulates MySelia Component behavior and handling
	private NetworkComponentHandlerBase componentHandler;
	// Current state of the connection
	private IConnectionState clientConnectionState;
	private ConnectionStateContainer stateContainer;
	// The client's IP address
	private String ipAddress;
	// Input stream used to receive data from MySelia Component
	private volatile BufferedReader input;
	// Output stream used to send data to MySelia Component
	private volatile PrintWriter output;
	// Instance level jsonParser to transform received strings into Transmission
	// objects
	//private Gson jsonParser;

	public StemClientSession(Socket clientConnectionSocket, int componentID) {
		this.clientConnectionSocket = clientConnectionSocket;
		this.ipAddress = clientConnectionSocket.getInetAddress().getHostAddress();
		
		try {
			input = new BufferedReader(new InputStreamReader(clientConnectionSocket.getInputStream()));
			output = new PrintWriter(clientConnectionSocket.getOutputStream(), true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.stateContainer = new ConnectionStateContainer(this);
		this.clientConnectionState = stateContainer.getHandshakeState();
		//jsonParser = new Gson();
	}

	@Override
	public void run() {

		// !HANDLING HAPPENS HERE!
		while (isRunning) {
			try {
				clientConnectionState.process();
			} catch (SocketException e) {
				System.err.println("Client with IP " + componentHandler.getIp() + " has disconnected.");
				isRunning = false;
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

}

/*if (clientConnectionState == connectionStatus.PENDING_HANDSHAKE) {
if ((inputS = input.readLine()) != null) {
	System.out.println("RECV: " + inputS);
	handleSetupPacket(inputS);
}

} else if (clientConnectionState == connectionStatus.LOST_CONNECTION) {
componentHandler.handleComponent();
try {
	Thread.sleep(1);
} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
} else if (clientConnectionState == connectionStatus.CONNECTED) {
componentHandler.handleComponent();
try {
	Thread.sleep(1);
} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
} else if (clientConnectionState == connectionStatus.CONNECTED) {
componentHandler.handleComponent();
try {
	Thread.sleep(1);
} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}*/
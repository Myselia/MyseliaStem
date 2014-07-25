package cms.model.communication;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import cms.controller.LogSystem;

public class Server {
	// public ObjectOutputStream output;
	// public ObjectInputStream input;
	public BufferedOutputStream output;
	public BufferedReader input;
	public ServerSocket serverSocket;
	public Socket clientConnectionSocket;

	private boolean SERVER_RUNNING = false;
	private int port, backlog;

	public Server(int port, int backlog) {
		this.port = port;
		this.backlog = backlog;
	}

	public void startRunning() throws ClassNotFoundException {
		System.out.println("STARTING SERVER LISTENING ON PORT " + port);
		SERVER_RUNNING = true;
		
		try {
			while (SERVER_RUNNING) {
				/*
				 * SETUP
				 */
				// Create server socket
				serverSocket = new ServerSocket(port, backlog);
				System.out.println("IN HERE");
				// Accept client connection
				clientConnectionSocket = serverSocket.accept();

				if (clientConnectionSocket.isConnected()) {
					System.out.println("Client connected on port "
							+ clientConnectionSocket.getPort());
				}

				output = new BufferedOutputStream(
						clientConnectionSocket.getOutputStream());
				LogSystem.log(true, false, "Started output stream buffer: " + output.toString());
				output.flush();
				input = new BufferedReader(new InputStreamReader(
						clientConnectionSocket.getInputStream()));
				LogSystem.log(true, false, "Started input stream buffer: " + input.toString());

				/*
				 * LOGIC
				 */
				String inputL = "";
			
				
				do {
					LogSystem.log(true, false, "Starting communication..");
					inputL = input.readLine();
					LogSystem.log(true, false, "Read line.");
				    System.out.println("inputL: " + inputL);
					System.out.println("Respone from Client(" + clientConnectionSocket.toString() + ": " + inputL);
					Thread.sleep(100);
					LogSystem.log(true, false, "Client sent : " + inputL);
					//output.println("anal beads lawnmower style");
				} while (SERVER_RUNNING);
				
			
				
			}

		} catch (IOException e) {
			System.err.println("EXCEPTION LISTENING ON PORT " + port);
			System.err.println(e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

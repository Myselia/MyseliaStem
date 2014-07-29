package cms.model.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import cms.controller.LogSystem;
import cms.helpers.ThreadHelper;

public class Server extends ThreadHelper {
	public PrintWriter output;
	public BufferedReader input;
	public ServerSocket serverSocket;
	public Socket clientConnectionSocket;

	public boolean SERVER_RUNNING = false;
	private int port, backlog;
	private boolean NOCONN = true;

	public Server(int port, int backlog) {
		this.serverSocket = null;
		this.clientConnectionSocket = null;
		this.port = port;
		this.backlog = backlog;
		this.SERVER_RUNNING = true;
	}

	public void startRunning() throws ClassNotFoundException {
		System.out.println("STARTING SERVER LISTENING ON PORT " + port);

		openServerSocket(this.port);

		while (SERVER_RUNNING) {
			clientConnectionSocket = null;

			try {
				clientConnectionSocket = this.serverSocket.accept();
				if (clientConnectionSocket != null) {
					break;
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			NOCONN = false;

			if (clientConnectionSocket.isConnected() && !NOCONN) {
				System.out.println("Client connected on port "
						+ clientConnectionSocket.getPort());

				output = new PrintWriter(
						clientConnectionSocket.getOutputStream(), true);
				LogSystem.log(true, false, "Started output stream buffer: "
						+ output.toString());
				// output.flush();
				input = new BufferedReader(new InputStreamReader(
						clientConnectionSocket.getInputStream()));
				LogSystem.log(true, false, "Started input stream buffer: "
						+ input.toString());
				// output.flush();
			}

			// }
			/*
			 * LOGIC
			 */
			String inputL = "";

			int c = 0;
			if (c == 0)
				LogSystem.log(true, false, "Starting communication..");

			do {
				if (clientConnectionSocket.isClosed())
					break;
				c = 1;
				inputL = input.readLine();
				LogSystem.log(true, false, "Read line.");
				System.out.println("inputL: " + inputL);
				LogSystem.log(true, false, "Response from Client("
						+ clientConnectionSocket.getInetAddress()
								.getHostAddress() + ": " + inputL + "(BYTES: "
						+ inputL.getBytes().length + ")");
				output.println("You said: " + inputL);

				Thread.sleep(100);
			} while (inputL != null);

			LogSystem.log(true, false, "Client "
					+ clientConnectionSocket.getInetAddress().getHostAddress()
					+ " has stopped communicating");
			NOCONN = true;

		}
	}

	private void openServerSocket(int port) {
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (Exception e) {
			System.err.println("Cant open port on " + port);
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			// while (true) {
			startRunning();
			// Thread.sleep(50);
			// }
			// startRunning();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		try {
			serverSocket = new ServerSocket(port, backlog);
			clientConnectionSocket = new Socket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.start();
		}
	}

}

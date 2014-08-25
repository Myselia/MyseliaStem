package cms.model.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cms.controller.LogSystem;
import cms.helpers.ThreadHelper;

public class Server extends ThreadHelper {

	public boolean SERVER_RUNNING = false;
	public int MAX_SERVER_THREAD_POOLS = 10;
	protected int port, backlog;

	protected PrintWriter output;
	protected BufferedReader input;
	protected ServerSocket serverSocket;

	protected Socket clientConnectionSocket;
	protected ExecutorService threadPool;

	public Server(int port, int backlog) {
		this.serverSocket = null;
		this.clientConnectionSocket = null;
		this.port = port;
		this.backlog = backlog;
		this.SERVER_RUNNING = true;
		threadPool = Executors.newFixedThreadPool(MAX_SERVER_THREAD_POOLS);
	}

	public void startRunning() throws ClassNotFoundException {
		LogSystem.log(true, false, "STARTING SERVER LISTENING ON PORT " + port);

		openServerSocket(this.port);

		while (SERVER_RUNNING) {
			clientConnectionSocket = null;

			try {
				clientConnectionSocket = this.serverSocket.accept();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Create a thread for a client if accepted containing an SClientSession
			this.threadPool.execute(new Thread(new SClientSession(clientConnectionSocket)));

		}

		LogSystem.log(true, false, "Server has stopped running");
		System.out.println("Server no longer listening on port: " + port);
	}


	private void openServerSocket(int port) {
		try {
			serverSocket = new ServerSocket(port);
		} catch (Exception e) {
			System.err.println("Cant open port on " + port);
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				startRunning();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
}
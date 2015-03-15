package cms.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cms.communication.ClientSession;

public class Server implements Runnable{

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
			this.threadPool.execute(new Thread(new ClientSession(clientConnectionSocket)));

		}
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
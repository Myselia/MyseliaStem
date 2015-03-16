package com.mycelia.stem.communication;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StemServer implements Runnable {

	public boolean SERVER_RUNNING = false;
	public int MAX_SERVER_THREAD_POOLS = 10;
	protected int port, backlog;

	protected PrintWriter output;
	protected BufferedReader input;

	protected ServerSocket serverSocket;
	protected Socket clientConnectionSocket;
	protected ExecutorService threadPool;

	public StemServer(int port, int backlog) {
		this.serverSocket = null;
		this.clientConnectionSocket = null;
		this.port = port;
		this.backlog = backlog;
		this.SERVER_RUNNING = true;
		threadPool = Executors.newFixedThreadPool(MAX_SERVER_THREAD_POOLS);
	}

	//Ticks as long as the server is running. Used to accept connections. Blocks until one is received.
	public void serverTick() throws ClassNotFoundException {
		if (serverSocket == null){
			openServerSocket(port);
		}

		while (SERVER_RUNNING) {
			clientConnectionSocket = null;
			try {
				clientConnectionSocket = this.serverSocket.accept();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Create a thread for a client if accepted containing an StemClientSession
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ACCEPTING CONNECTION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			this.threadPool.execute(new Thread(new StemClientSession(
					clientConnectionSocket,
					assignInternalID(clientConnectionSocket))));

		}
		System.out.println("server no longer listening on port: " + port);
	}

	private int assignInternalID(Socket clientConnectionSocket) {
		return 0;
	}

	private void openServerSocket(int port) {
		try {
			serverSocket = new ServerSocket(port);
		} catch (Exception e) {
			System.err.println("FATAL: Cannot open Stem Server port on " + port);
		}
	}

	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				serverTick();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
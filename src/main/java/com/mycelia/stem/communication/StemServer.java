package com.mycelia.stem.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cms.communication.ClientSession;

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

	public void serverTick() throws ClassNotFoundException {
		if (serverSocket == null){
			openServerSocket(port);
		}

		while (SERVER_RUNNING) {
			clientConnectionSocket = null;
			try {
				System.out.println("COCK!");
				clientConnectionSocket = this.serverSocket.accept();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Create a thread for a client if accepted containing an
			// StemClientSession
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
			System.err.println("cannot open port on " + port);
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
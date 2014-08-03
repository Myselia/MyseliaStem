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
	public boolean DISCOVER_MODE = true;

	public int MAX_SERVER_THREAD_POOLS = 10;

	protected int port, backlog;

	protected PrintWriter output;
	protected BufferedReader input;
	protected ServerSocket serverSocket;
	protected DatagramSocket discoverSocket;
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
		System.out.println("STARTING SERVER LISTENING ON PORT " + port);

		openServerSocket(this.port, false);

		while (SERVER_RUNNING) {
			clientConnectionSocket = null;

			try {
				clientConnectionSocket = this.serverSocket.accept();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Create a thread for a client if accepted containing an
			// SClientSession
			this.threadPool.execute(new Thread(new SClientSession(
					clientConnectionSocket, "Multithreaded Server")));

		}

		LogSystem.log(true, false, "Server has stopped running");
		System.out.println("Server no longer listening on port: " + port);
	}

	public void startDiscovering() throws IOException {
		System.out.println("STARTING DISCOVERY SERVICE");

		openServerSocket(this.port, true);
		byte[] info = buildInfoPacket();
		InetAddress broadcast = null;

		if (broadcast == null)
			broadcast = getServerNetworkBroadcast();

		int i = 5;

		while (DISCOVER_MODE) {

			// Send infoPacket to broadcast address
			try {
				if (i > 0) {
					DatagramPacket infoPacket = new DatagramPacket(info,
							info.length,
							InetAddress.getByName("255.255.255.255"), 8888);
					// DatagramPacket infoPacket = new DatagramPacket(info,
					// info.length, broadcast, 8888);
					discoverSocket.send(infoPacket);
					System.out.println("Sent packet " + infoPacket + " to: "
							+ infoPacket.getAddress().getHostAddress());
					Thread.sleep(2000);
					i--;
				} else {
					DISCOVER_MODE = false;
				}
			} catch (Exception e) {

			}

		}

	}

	private InetAddress getServerNetworkBroadcast() throws SocketException {
		Enumeration<NetworkInterface> interfaces = null;
		InetAddress broadcast = null;

		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (interfaces.hasMoreElements()) {
			NetworkInterface networkInterface = interfaces.nextElement();

			if (networkInterface.isLoopback())
				continue;

			for (InterfaceAddress interfaceAddress : networkInterface
					.getInterfaceAddresses()) {
				broadcast = interfaceAddress.getBroadcast();
				System.out.println("BCAST FIND: " + broadcast);

				if (broadcast == null)
					continue;
			}
		}

		return broadcast;
	}

	private byte[] buildInfoPacket() {
		String ip = null;

		try {
			ip = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return (ip + ":" + port + "\r\n").getBytes();
	}

	private void openServerSocket(int port, boolean bcast) {
		try {
			if (bcast) {
				discoverSocket = new DatagramSocket(8888);
				discoverSocket.setBroadcast(true);
				System.out.println("Discover socket initialized");
			} else {
				serverSocket = new ServerSocket(port);
				System.out.println("Server socket initialized");
			}
		} catch (Exception e) {
			System.err.println("Cant open port on " + port);
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (DISCOVER_MODE) {
					startDiscovering();
				} else {
					startRunning();
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

/*
 * if (clientConnectionSocket.isConnected() && !NOCONN) {
 * System.out.println("Client connected on port " +
 * clientConnectionSocket.getPort());
 * 
 * output = new PrintWriter( clientConnectionSocket.getOutputStream(), true);
 * LogSystem.log(true, false, "Started output stream buffer: " +
 * output.toString()); // output.flush(); input = new BufferedReader(new
 * InputStreamReader( clientConnectionSocket.getInputStream()));
 * LogSystem.log(true, false, "Started input stream buffer: " +
 * input.toString()); // output.flush(); }
 */

// }
/*
 * LOGIC
 */
/*
 * String inputL = "";
 * 
 * int c = 0; if (c == 0) LogSystem.log(true, false,
 * "Starting communication..");
 * 
 * do { if (clientConnectionSocket.isClosed()) break; c = 1; inputL =
 * input.readLine(); LogSystem.log(true, false, "Read line.");
 * System.out.println("inputL: " + inputL); LogSystem.log(true, false,
 * "Response from Client(" + clientConnectionSocket.getInetAddress()
 * .getHostAddress() + ": " + inputL + "(BYTES: " + inputL.getBytes().length +
 * ")"); output.println("You said: " + inputL);
 * 
 * Thread.sleep(100); } while (inputL != null);
 * 
 * LogSystem.log(true, false, "Client " +
 * clientConnectionSocket.getInetAddress().getHostAddress() +
 * " has stopped communicating"); NOCONN = true;
 */


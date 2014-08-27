package cms.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import cms.Main;
import cms.helpers.ThreadHelper;
import cms.monitoring.LogSystem;

public class Broadcast extends ThreadHelper {

	protected int port;
	protected long SLEEP_TIME = 2000;
	//This is the port the broadcast server will create a socket on
	private final int DEFAULT_PORT = 8888;
	private boolean RUNNING = false;

	protected DatagramSocket discoverSocket;

	public Broadcast() {
		this.port = DEFAULT_PORT;
	}
	
	public Broadcast(int port) {
		this.port = port;
	}


	public void startDiscovering() throws IOException {
		System.out.println("STARTING DISCOVERY SERVICE");

		openServerSocket(this.port);
		byte[] info = buildInfoPacket();
		InetAddress broadcast = null;

		if (broadcast == null)
			broadcast = getServerNetworkBroadcast();

		while (RUNNING) {

			// Send infoPacket to broadcast address
			try {

				DatagramPacket infoPacket = new DatagramPacket(info, info.length, InetAddress.getByName("255.255.255.255"), 8888);
				discoverSocket.send(infoPacket);
				LogSystem.log(true, false, "Sent packet " + infoPacket + " to: " + infoPacket.getAddress().getHostAddress());
				Thread.sleep(SLEEP_TIME);

			} catch (InterruptedException e) {
				//If the sleep is interrupted by an attempt to stop the thread, the exception will properly terminate the thread.
				//Do cleanup in here.
				setRunning(false);
				discoverSocket.close();
				Thread.currentThread().interrupt();	
			}

		}

	}

	public boolean isRunning() {
		return RUNNING;
	}

	public void setRunning(boolean Running) {
		RUNNING = Running;
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

			for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
				broadcast = interfaceAddress.getBroadcast();
				//System.out.println("BCAST FIND: " + broadcast);

				if (broadcast == null)
					continue;
			}
		}

		return broadcast;
	}

	private void openServerSocket(int port) {
		try {
			discoverSocket = new DatagramSocket(port);
		} catch (Exception e) {
			System.err.println("Cant open port on " + port);
			e.printStackTrace();
		}
	}

	private byte[] buildInfoPacket() {
		String ip = null;

		try {
			ip = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return (ip + ":" + Main.DEFAULT_PORT + "\r\n").getBytes();
	}

	public void run() {
		setRunning(true);
		try {
			while (!Thread.currentThread().isInterrupted()) {
				startDiscovering();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

package cms.model.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
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
		System.out.println("STARTING SERVER LISTENING ON PORT " + port);

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
			
			//Create a thread for a client if accepted containing an SClientSession
			this.threadPool.execute(new Thread(new SClientSession(clientConnectionSocket, "Multithreaded Server")));
			
			}
		
		LogSystem.log(true, false, "Server has stopped running");
		System.out.println("Server no longer listening on port: " + port);
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
			startRunning();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}

/*if (clientConnectionSocket.isConnected() && !NOCONN) {
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
}*/

// }
/*
* LOGIC
*/
/*String inputL = "";

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
NOCONN = true;*/


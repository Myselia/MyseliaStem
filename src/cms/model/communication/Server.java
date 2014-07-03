package cms.model.communication;

import java.io.*;
import java.net.*;

public class Server {
	public ObjectOutputStream output;
	public ObjectInputStream input;
	public ServerSocket server;
	public Socket connection;
	
	private int port, backlog;
	
	public Server(int port, int backlog){
		this.port = port;
		this.backlog = backlog;
	}
	
	public void startRunning(){
		try{
			server = new ServerSocket(port, backlog);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}

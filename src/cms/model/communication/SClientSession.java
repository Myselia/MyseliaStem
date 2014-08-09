package cms.model.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import cms.controller.LogSystem;
import cms.helpers.ThreadHelper;
import cms.model.DataStore;
import cms.model.communication.format.Transmission;
import cms.model.communication.format.XMLParser;
import cms.model.data.BeanNode;
<<<<<<< HEAD
import cms.view.element.GraphingParent;
=======
import cms.model.data.NodeState;
>>>>>>> 95fd28df67da74fa98a3b2cd76ec027c9571e0e3
import cms.view.panel.AddressBar;

public class SClientSession extends ThreadHelper {

	//Socket object to handle client transmission on transport layer
	protected Socket clientConnectionSocket = null;
	//Socket object to handle server transmission on transport layer
	protected String serverTransmission = null;
	//The client's IP address
	private String ipAddress;
	//This flag is true once the setup phase of the session is complete 
	private boolean SETUP = false;
	//This is the id of the session which is the same as the node id in datastore
	private int sessionID = -1;
	//Input stream buffer
	private BufferedReader input;
	//Output stream buffer
	private PrintWriter output;
	
	//The string currently being processed by the server (\r\n terminated from client)
	protected String inputS;
	
	public SClientSession(Socket clientConnectionSocket, String serverTransmission) {
		this.clientConnectionSocket = clientConnectionSocket;
		this.serverTransmission = serverTransmission;
		this.ipAddress = clientConnectionSocket.getInetAddress().getHostAddress();
	}

	public void run() {
		try {
			System.out.println("STARTING CLIENT SESSION");
			//Add a graphic node
			DataStore.coreA.add(new BeanNode());
			System.err.println("SIZE: " + DataStore.coreA.size());
			DataStore.coreA.get(DataStore.coreA.size() - 1).setType(0);
			DataStore.coreA.get(DataStore.coreA.size() - 1).setId(DataStore.nextNodeID());
			System.err.println("NODE ID IS: " + DataStore.coreA.get(DataStore.coreA.size() - 1).getId());
			DataStore.coreA.get(DataStore.coreA.size() - 1).setState(NodeState.ABSENT);
			System.out.println("CLIENT STORE AT: " + DataStore.coreA.get(DataStore.coreA.size() - 1));
			sessionID = DataStore.coreA.get(DataStore.coreA.size() - 1).getId();
			AddressBar.updateButtonList();
			GraphingParent.updateBarCount();
			
			input =  new BufferedReader(new InputStreamReader(
					clientConnectionSocket.getInputStream()));
			output =  new PrintWriter(
					clientConnectionSocket.getOutputStream(), true);

			Transmission trans;
			
			if (!SETUP) {
				//Once connected perform initial setup (assign node ID, etc)
				String infoParams = Integer.toString(DataStore.nextNodeID());
				System.out.println("infoparams: " + infoParams);
				output.println(infoParams);
				SETUP = true;
			}
			
			while (SETUP && ((inputS = input.readLine() ) != null)) {
				//System.out.println("inputS: " + inputS);
				LogSystem.log(true, false, "Response from Client(" + ipAddress + ")");
				XMLParser xmlp = new XMLParser();
				trans = xmlp.makedoc(inputS);
				if(trans.type.equals("sys")){
					DataStore.insertData(trans);
				}
				//trans.printTransmission();

			}
			
			//If the program gets here the connection has been lost, do some cleanup stuff
			System.out.println("Lost connection from client: " + ipAddress);
			cleanUp();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void cleanUp() {
		try {
			input.close();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataStore.removeNode(sessionID);
		AddressBar.updateButtonList();
	}
}
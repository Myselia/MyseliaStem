package cms.model.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import cms.controller.LogSystem;
import cms.helpers.ThreadHelper;
import cms.model.DataFactory;
import cms.model.XMLParser;
import cms.model.communication.format.Transmission;

public class SClientSession extends ThreadHelper {

	protected Socket clientConnectionSocket = null;
	protected String serverTransmission = null;
	
	protected XMLParser bobbyhill;
	
	String inputS;
	public SClientSession(Socket clientConnectionSocket, String serverTransmission) {
		this.clientConnectionSocket = clientConnectionSocket;
		this.serverTransmission = serverTransmission;
		this.bobbyhill = new XMLParser();
	}

	public void run() {
		System.out.println("CLIENT THREAD RUNNING!!!!!!!!!!");
		try {
			System.out.println("STARTING CLIENT SESSION");
			BufferedReader input =  new BufferedReader(new InputStreamReader(
					clientConnectionSocket.getInputStream()));
			PrintWriter output =  new PrintWriter(
					clientConnectionSocket.getOutputStream(), true);

			Transmission trans;

			while ((inputS = input.readLine() ) != null) {
				
				LogSystem.log(true, false, "Read line.");
				System.out.println("inputS: " + inputS);
				LogSystem.log(true, false, "Response from Client("
						+ clientConnectionSocket.getInetAddress()
								.getHostAddress());
				XMLParser xmlp = new XMLParser();
				trans = xmlp.makedoc(inputS);
				if(trans.type.equals("sys")){
					DataFactory.insertData(trans);
				}
				trans.printTransmission();

			}
			
			output.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
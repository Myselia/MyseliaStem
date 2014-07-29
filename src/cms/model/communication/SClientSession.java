package cms.model.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import cms.controller.LogSystem;
import cms.helpers.ThreadHelper;

public class SClientSession extends ThreadHelper {

	protected Socket clientConnectionSocket = null;
	protected String serverTransmission = null;
	protected String serverInput = null;

	public SClientSession(Socket clientConnectionSocket, String serverTransmission) {
		this.clientConnectionSocket = clientConnectionSocket;
		this.serverTransmission = serverTransmission;
	}

	public void run() {
		try {
			System.out.println("STARTING CLIENT SESSION");
			BufferedReader input =  new BufferedReader(new InputStreamReader(
					clientConnectionSocket.getInputStream()));
			PrintWriter output =  new PrintWriter(
					clientConnectionSocket.getOutputStream(), true);
			
			long time = System.currentTimeMillis();

			while ((serverInput = input.readLine()) != null) {
				LogSystem.log(true, false, "Read line.");
				System.out.println("inputL: " + serverInput);
				LogSystem.log(true, false, "Response from Client("
						+ clientConnectionSocket.getInetAddress()
								.getHostAddress() + ": " + serverInput
						+ "(BYTES: " + serverInput.getBytes().length + ")");
				output.println("You said: " + serverInput);
			}
			
			output.close();
			input.close();
			//System.out.println("Request processed: " + time);
		} catch (IOException e) {
			// report exception somewhere.
			e.printStackTrace();
		}
	}
}
package com.mycelia.stem.communication.states;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;

import com.mycelia.stem.communication.StemClientSession;
import com.mycelia.stem.communication.handlers.ComponentHandlerBase;

public class HttpHandshakeConnectionState implements ConnectionState {

	private ComponentHandlerBase handler;
	private StemClientSession session;
	private String inputS = null;
	private BufferedReader input = null;
	private String webSocketKey = null;
	private String keyStringSearch = "Sec-WebSocket-Key: ";
	private String webSocketUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
	boolean finished = false;
	
	@Override
	public void primeConnectionState(StemClientSession session) {
		this.session = session;
		this.input = (BufferedReader)session.getReader();
	}
	
	@Override
	public void process() throws IOException {
		if (!finished) {
			while ((inputS = input.readLine()) != null) {
				System.out.println("RECV IN HTTP: " + inputS);

				handleHeaders(inputS);
				if (finished)
					break;
			}
		} else {
			System.out.println("!!!!!!!!!!!SENDING TEST BITS!!!!!!!!!!!");
			session.getOutStream().write(encodeWebSocketPayload("number: "));
			session.getOutStream().flush();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public ComponentHandlerBase getHandler() {
		return handler;
	}

	@Override
	public void setHandler(ComponentHandlerBase handler) {
		this.handler = handler;
	}
	
	public String toString() {
		return "HTTP HANDSHAKER!!";
	}

	private void handleHeaders(String input) {
		if (!finished) {
			// Get the webSocketKey
			if (input.startsWith(keyStringSearch)) {
				webSocketKey = input.substring(keyStringSearch.length());
				System.out.println("KEY IS:" + webSocketKey);
			}

			// They're done
			if (input.equals(""))
				finished = true;

			// We need a webSocketKey to continue
			if (webSocketKey == null)
				System.err.println("No Sec-WebSocket-Key was passed at: " + session);
		}

		if (finished && webSocketKey != null) {
			// Send our headers
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println("!!!!!SENDING RESPONSE HEADERS!!!!!!");
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			//session.getWriter().println("HTTP/1.1 101 Web Socket Protocols\r");
			session.getWriter().println("HTTP/1.1 101 Switching Protocols");
			session.getWriter().println("Upgrade: WebSocket");
			session.getWriter().println("Connection: Upgrade");
			session.getWriter().println("Sec-WebSocket-Accept: " + generateOK(webSocketKey));
			//session.getWriter().println("Sec-WebSocket-Protocol: chat\r");
			session.getWriter().println("");
			session.getWriter().flush();
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println("!!!!!!!!!!!!!DONE!!!!!!!!!!!!!!!!!!");
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This funciton is used to wrap a string passed into it with a websocket compatible frame
	 * This frame is RFC 6455 compatible and available at: http://tools.ietf.org/html/rfc6455#page-20.
	 * @param s	The string to be wrapped with the frame
	 * @return	An array of bytes to be sent over a connection
	 */
	private byte[] encodeWebSocketPayload(String s) {
		byte[] payloadBytes = s.getBytes();
		byte[] contructedWebSocketFrame = new byte[10];
		byte[] replyWebSocketFrame;
		int payloadLen = payloadBytes.length;
		int amountOfFrames = 0;
		int totalFrameLength = 0;

		/*
		 * SETUP OF FIRST BYTE, BY RFC 6455 THIS IS 129d -> "1000 0001"
		 */
		contructedWebSocketFrame[0] = (byte) 129; /* FOR TEXT FRAME*/

		/*
		 * SETUP OF LENGTH BYTES
		 * PAYLOAD LENGTH
		 * 	0-125 : NO ADDITIONAL BYTES
		 * 	126-65535 : TWO ADDITIONAL BYTES, SECOND(IN FRAME) BYTE 126
		 * 	>65536 : EIGHT ADDITIONAL BYTES, SECOND(IN FRAME) BYTE 127
		 */
		if (payloadBytes.length <= 125) {
			contructedWebSocketFrame[1] = (byte) payloadBytes.length;
			amountOfFrames = 2;
		} else if (payloadBytes.length >= 126 && payloadBytes.length <= 65535) {
			contructedWebSocketFrame[1] = (byte) 126;
			contructedWebSocketFrame[2] = (byte) ((payloadLen >> 8) & (byte) 255);
			contructedWebSocketFrame[3] = (byte) (payloadLen & (byte) 255);
			amountOfFrames = 4;
		} else {
			contructedWebSocketFrame[1] = (byte) 127;
			contructedWebSocketFrame[2] = (byte) ((payloadLen >> 56) & (byte) 255);
			contructedWebSocketFrame[3] = (byte) ((payloadLen >> 48) & (byte) 255);
			contructedWebSocketFrame[4] = (byte) ((payloadLen >> 40) & (byte) 255);
			contructedWebSocketFrame[5] = (byte) ((payloadLen >> 32) & (byte) 255);
			contructedWebSocketFrame[6] = (byte) ((payloadLen >> 24) & (byte) 255);
			contructedWebSocketFrame[7] = (byte) ((payloadLen >> 16) & (byte) 255);
			contructedWebSocketFrame[8] = (byte) ((payloadLen >> 8) & (byte) 255);
			contructedWebSocketFrame[9] = (byte) (payloadLen & (byte) 255);
			amountOfFrames = 10;
		}

		totalFrameLength = amountOfFrames + payloadBytes.length;

		replyWebSocketFrame = new byte[totalFrameLength];

		int frameByteLimiter = 0;
		for (int i = 0; i < amountOfFrames; i++) {
			replyWebSocketFrame[frameByteLimiter] = contructedWebSocketFrame[i];
			frameByteLimiter++;
		}
		for (int i = 0; i < payloadBytes.length; i++) {
			replyWebSocketFrame[frameByteLimiter] = payloadBytes[i];
			frameByteLimiter++;
		}
		
		return replyWebSocketFrame;
	}

	/*private String decodeWebSocketPayload(byte[] s) {
		int len = 0;
		byte[] b = new byte[1024];
		// rawIn is a Socket.getInputStream();
		while (true) {
			len = rawIn.read(b);
			if (len != -1) {

				byte rLength = 0;
				int rMaskIndex = 2;
				int rDataStart = 0;
				// b[0] is always text in my case so no need to check;
				byte data = b[1];
				byte op = (byte) 127;
				rLength = (byte) (data & op);

				if (rLength == (byte) 126)
					rMaskIndex = 4;
				if (rLength == (byte) 127)
					rMaskIndex = 10;

				byte[] masks = new byte[4];

				int j = 0;
				int i = 0;
				for (i = rMaskIndex; i < (rMaskIndex + 4); i++) {
					masks[j] = b[i];
					j++;
				}

				rDataStart = rMaskIndex + 4;

				int messLen = len - rDataStart;

				byte[] message = new byte[messLen];

				for (i = rDataStart, j = 0; i < len; i++, j++) {
					message[j] = (byte) (b[i] ^ masks[j % 4]);
				}

				return new String(message);

			}
		}
	}*/
	
	
	private void printBytes(byte[] b) {
		for (int i = 0; i < b.length; i++) {
			String s = String.format("%8s", Integer.toBinaryString(b[i] & 0xFF)).replace(' ', '0');
			System.out.println(s);
		}
	}

	private String generateOK(String key) {
		Encoder encoder = Base64.getEncoder();
		String genWith = key + webSocketUID;
		String encodedKey = null;
		MessageDigest md = null;
		byte[] sha1hash = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			sha1hash = new byte[40];
			md.update(genWith.getBytes("iso-8859-1"), 0, genWith.length());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		sha1hash = md.digest();
		
		encodedKey = new String(encoder.encode(sha1hash));
		return encodedKey;
	}
	
	
}
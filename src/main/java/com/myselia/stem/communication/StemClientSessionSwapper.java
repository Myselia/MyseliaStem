package com.myselia.stem.communication;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.myselia.javacommon.communication.units.Transmission;

/**
 * The StemClientSessionSwapper is, at the creation of a channel pipeline, at
 * the tail of the pipeline. It handles the initial transmission and figures out
 * whether the endpoint we're dealing with is a regular MyseliaComponent(Daemon,
 * Stem) or requires special codecs (ie websocket for MyseliaLens). Once setup
 * is complete, the swapper initializes the proper StemClientSession after
 * setting up the pipeline.
 */
public class StemClientSessionSwapper extends SimpleChannelInboundHandler<String> {

	private boolean isHTTP = false;
	private Channel clientChannel;
	private ArrayList<String> HTTPRequest = new ArrayList<String>();
	private final CharSequence HTTPHeader = "GET / HTTP/1.1";
	private Gson jsonCodec = new Gson();
	
	public StemClientSessionSwapper(Channel clientChannel) {
		this.clientChannel = clientChannel;
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("[MESSAGE RECEIVED]" + msg);

		if (msg.contains(HTTPHeader)) {
			System.out.println("THIS IS HTTP!");
			isHTTP = true;
		} 
		if (isHTTP) {
			//Wait for the full WebSocket request to complete
			if (msg.equals("")) {
				System.out.println("HTTP ENDED");
				setupPipeline(HTTPRequest);
			} else {
				HTTPRequest.add(msg);
			}
		} else {
			//We have a transmission, we can directly pass it for further processing now. 
			//First we have to convert from string to Transmission.
			
			Transmission t = jsonCodec.fromJson(msg, Transmission.class);
			setupPipeline(t);
		}
	}
	
	private void setupPipeline(Object passTo) {
		ChannelPipeline pipeline = clientChannel.pipeline();
		if (isHTTP) {
			pipeline.replace(this, "stemClientSession", new StemClientSession(clientChannel, passTo, true));
		} else {
			pipeline.replace(this, "stemClientSession", new StemClientSession(clientChannel, passTo, false));
		}
	}

}

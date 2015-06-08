package com.myselia.stem.communication;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Handles a server-side channel.
 */
public class DiscardServer extends ChannelHandlerAdapter {

	public int test = 0;
	
	public DiscardServer() {
		test++;
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		System.out.println("Server got: " + msg + " TEST TOKEN " + test + " CONTEXT " + ctx.pipeline());
		ctx.write(msg);
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// Close the connection when an exception is raised.
		cause.printStackTrace(); 
		ctx.close();
	}
}
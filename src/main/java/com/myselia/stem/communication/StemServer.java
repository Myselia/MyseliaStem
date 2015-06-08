package com.myselia.stem.communication;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class StemServer {

	protected int port, backlog;
	
	public StemServer(int port) {
		System.out.println("Starting Stem Server with Port: " + port);
		this.port = port; 
	}
	
    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); 
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) 
             .childHandler(new StemClientSessionInitializer())
             .option(ChannelOption.SO_BACKLOG, 128)          
             .childOption(ChannelOption.SO_KEEPALIVE, true); 

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
	

	/*private void openServerSocket(int port) {
		try {
			serverSocket = new ServerSocket(port);
		} catch (Exception e) {
			System.err.println("FATAL: Cannot open Stem Server port on " + port);
		}
	}*/
}

/*//Ticks as long as the server is running. Used to accept connections. Blocks until one is received.
public void serverTick() throws ClassNotFoundException {
	if (serverSocket == null){
		openServerSocket(port);
	}

	while (SERVER_RUNNING) {
		clientConnectionSocket = null;
		try {
			clientConnectionSocket = this.serverSocket.accept();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Create a thread for a client if accepted containing an StemClientSession
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!--------------------!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ACCEPTING CONNECTION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!--------------------!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		if (isHTTP)
			this.threadPool.execute(new Thread(new StemClientSession(clientConnectionSocket, assignInternalID(clientConnectionSocket), true)));
		else
			this.threadPool.execute(new Thread(new StemClientSession(clientConnectionSocket, assignInternalID(clientConnectionSocket), false)));

	}
	System.out.println("server no longer listening on port: " + port);
}

private int assignInternalID(Socket clientConnectionSocket) {
	return 0;
}*/


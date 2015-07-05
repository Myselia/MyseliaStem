package com.myselia.stem.communication;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class StemClientSessionInitializer extends ChannelInitializer<SocketChannel> {

	private static final int MAX_FRAME_SIZE = 8096;
	private final StringDecoder stringDecoder = new StringDecoder(CharsetUtil.UTF_8);
	private final StringEncoder stringEncoder = new StringEncoder(CharsetUtil.UTF_8);

	public StemClientSessionInitializer() {
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		// ///////////////////////////////////////////////////////////////
		/*
		 * Here we add all the relevant channel handlers to the pipeline
		 */
		// ///////////////////////////////////////////////////////////////

		/*
		 * LOGGING
		 */
		//pipeline.addLast("loggingHandler", new LoggingHandler(LogLevel.TRACE));

		/*
		 * TODO: TLS/SSL Security
		 */

		/*
		 * CODECS
		 */

		// Decoders
		//pipeline.addLast("frameDecoder", new LineBasedFrameDecoder(MAX_FRAME_SIZE));
		pipeline.addLast("frameDecoder", new DelimiterBasedFrameDecoder(MAX_FRAME_SIZE, true, Delimiters.lineDelimiter()));
		pipeline.addLast("stringDecoder", stringDecoder);

		// Encoders
		pipeline.addLast("stringEncoder", stringEncoder);

		/*
		 * APPLICATION BUSINESS LOGIC
		 */

		// Stem Session
		pipeline.addLast("sessionSwapper", new StemClientSessionSwapper(ch));
	}

}

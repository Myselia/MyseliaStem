package com.myselia.stem.communication.codecs;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Takes a string and turns it into a decoded string ready for transformation into a transmission
 * @author Sylvain
 *
 */
public class WebSocketDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
	//TODO: Fix to decode properly
	}


}

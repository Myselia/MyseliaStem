package com.myselia.stem.communication.codecs;

import com.myselia.javacommon.framework.communication.WebSocketHelper;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


public class WebSocketEncoder extends MessageToByteEncoder<String>{

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
		byte[] encodedMessage = WebSocketHelper.encodeWebSocketPayload(msg);
		out.writeBytes(encodedMessage);
	}

}

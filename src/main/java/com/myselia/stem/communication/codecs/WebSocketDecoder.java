package com.myselia.stem.communication.codecs;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.CharsetUtil;

import java.util.List;

public class WebSocketDecoder extends MessageToMessageDecoder<WebSocketFrame> {

	@Override
	protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
		String payload = msg.content().toString(CharsetUtil.UTF_8);
		out.add(payload);
	}

}

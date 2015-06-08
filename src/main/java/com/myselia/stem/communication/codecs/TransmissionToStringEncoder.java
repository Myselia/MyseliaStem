package com.myselia.stem.communication.codecs;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

import com.google.gson.Gson;
import com.myselia.javacommon.communication.units.Transmission;

public class TransmissionToStringEncoder extends MessageToMessageEncoder<Transmission> {

	static Gson jsonCodec = new Gson();
	@Override
	protected void encode(ChannelHandlerContext ctx, Transmission msg, List<Object> out) throws Exception {
		String payLoad = jsonCodec.toJson(msg);
		TextWebSocketFrame preparedFrame = new TextWebSocketFrame(payLoad);
		out.add(preparedFrame);
	}

}
	
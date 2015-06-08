package com.myselia.stem.communication.codecs;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import com.google.gson.Gson;
import com.myselia.javacommon.communication.units.Transmission;

public class StringToTransmissionDecoder extends MessageToMessageDecoder<String> {

	static Gson jsonCodec = new Gson();

	@Override
	protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
		out.add(jsonCodec.fromJson(msg, Transmission.class));
	}

}

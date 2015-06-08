package com.myselia.stem.communication.codecs;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.google.gson.Gson;
import com.myselia.javacommon.communication.units.Transmission;

public class TransmissionToStringEncoder extends MessageToByteEncoder<Transmission> {

	static Gson jsonCodec = new Gson();
	@Override
	protected void encode(ChannelHandlerContext ctx, Transmission msg, ByteBuf out) throws Exception {
		CharSequence s = jsonCodec.toJson(msg);
		ByteBufUtil.writeUtf8(out, s);
	}

}

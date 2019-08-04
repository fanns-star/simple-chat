package org.qianshan.chat.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.qianshan.chat.component.protocol.Packet;
import org.qianshan.chat.component.protocol.PacketCodec;

@ChannelHandler.Sharable
public class PacketEncodeHandler extends MessageToByteEncoder<Packet> {

    public static final PacketEncodeHandler INSTANCE = new PacketEncodeHandler();

    private PacketEncodeHandler() {
    }

    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        PacketCodec.encode(msg, out);
    }
}

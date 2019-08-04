package org.qianshan.chat.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.qianshan.chat.component.protocol.Packet;
import org.qianshan.chat.component.protocol.PacketCodec;

@ChannelHandler.Sharable
public class PacketDecodeHandler extends SimpleChannelInboundHandler<ByteBuf> {

    public static final PacketDecodeHandler INSTANCE = new PacketDecodeHandler();

    private PacketDecodeHandler() {
    }

    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        Packet packet = PacketCodec.decode(msg);
        ctx.fireChannelRead(packet);
    }
}

package org.qianshan.chat.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.qianshan.chat.component.Session;
import org.qianshan.chat.component.protocol.Packet;
import org.qianshan.chat.component.protocol.PayloadTypeEnum;
import org.qianshan.chat.component.utils.ChannelAttrUtils;

/**
 * 处理ping消息
 */
@ChannelHandler.Sharable
public class PingHandler extends SimpleChannelInboundHandler<Packet> {

    public static final PingHandler INSTANCE = new PingHandler();

    private PingHandler() {
    }

    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        Packet response = new Packet(PayloadTypeEnum.PONG.getType(), false, true, msg.getPacketId(), null);
        Session session = ChannelAttrUtils.getSession(ctx.channel());
        session.sendMessage(response);
    }
}

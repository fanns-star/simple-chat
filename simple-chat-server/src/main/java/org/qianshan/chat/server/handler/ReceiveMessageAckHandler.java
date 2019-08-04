package org.qianshan.chat.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.qianshan.chat.component.protocol.Packet;

/**
 * 处理ReceiveMessageAck消息
 */
@ChannelHandler.Sharable
public class ReceiveMessageAckHandler extends SimpleChannelInboundHandler<Packet> {

    public static final ReceiveMessageAckHandler INSTANCE = new ReceiveMessageAckHandler();

    private ReceiveMessageAckHandler(){

    }

    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {

    }
}

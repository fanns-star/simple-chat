package org.qianshan.chat.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.qianshan.chat.component.Session;
import org.qianshan.chat.component.protocol.ErrorPayload;
import org.qianshan.chat.component.protocol.Packet;
import org.qianshan.chat.component.protocol.PayloadTypeEnum;
import org.qianshan.chat.component.protocol.ReceiveMessagePayload;
import org.qianshan.chat.component.utils.ChannelAttrUtils;

@ChannelHandler.Sharable
public class ReceiveMessageHandler extends SimpleChannelInboundHandler<Packet> {

    public static final ReceiveMessageHandler INSTANCE = new ReceiveMessageHandler();

    private ReceiveMessageHandler() {
    }

    private static final String TEMPLATE = "收到【%s】发来的消息：%s";

    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        if (msg.getPayload() instanceof ReceiveMessagePayload){
            ReceiveMessagePayload payload = (ReceiveMessagePayload) msg.getPayload();
            System.out.println(String.format(TEMPLATE, payload.getFrom(), payload.getContent()));

            Packet response = new Packet(PayloadTypeEnum.RECEIVE_MESSAGE_ACK.getType(), false, false, msg.getPacketId(), null);
            Session session = ChannelAttrUtils.getSession(ctx.channel());
            session.sendMessage(response);
        }else if (msg.getPayload() instanceof ErrorPayload){
            ErrorPayload errorPayload = (ErrorPayload) msg.getPayload();
            System.err.println(errorPayload);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("断开连接，聊天关闭");
        ctx.channel().close();
    }
}

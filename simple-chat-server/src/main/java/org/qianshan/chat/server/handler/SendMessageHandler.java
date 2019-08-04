package org.qianshan.chat.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.qianshan.chat.component.Session;
import org.qianshan.chat.component.exception.ChatException;
import org.qianshan.chat.component.exception.CodeEnum;
import org.qianshan.chat.component.protocol.*;
import org.qianshan.chat.component.utils.ChannelAttrUtils;
import org.qianshan.chat.component.utils.StringUtils;
import org.qianshan.chat.server.SessionManager;

/**
 * 处理SendMessage消息
 */
@ChannelHandler.Sharable
public class SendMessageHandler extends SimpleChannelInboundHandler<Packet> {

    public static final SendMessageHandler INSTANCE = new SendMessageHandler();

    private SendMessageHandler() {
    }

    private static final String TEMPLATE = "收到%s发来的消息：%s";

    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        SendMessagePayload sendMessagePayload = (SendMessagePayload) msg.getPayload();
        if (StringUtils.isBlank(sendMessagePayload.getContent())){
            throw new ChatException(CodeEnum.NULL_MESSAGE_CONTENT.getCode(), msg.getPacketId());
        }
        String userName = ChannelAttrUtils.getUserName(ctx.channel());

        Session currentSession = ChannelAttrUtils.getSession(ctx.channel());
        //发送ack
        Packet response = new Packet(PayloadTypeEnum.RECEIVE_MESSAGE_ACK.getType(), false, true, msg.getPacketId(), null);
        currentSession.sendMessage(response);

        System.out.println(String.format(TEMPLATE, currentSession.getUserName(), sendMessagePayload.getContent()));
        //将消息推送给其他用户
        for (Session session : SessionManager.getSessionList()){
            if (session == currentSession){
                continue;
            }
            Payload payload = new ReceiveMessagePayload(userName, sendMessagePayload.getContent());
            Packet packet = new Packet(PayloadTypeEnum.RECEIVE_MESSAGE.getType(), true, true, session.getNextPacketId(), payload);
            session.sendMessage(packet);
        }
    }
}

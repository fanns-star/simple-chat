package org.qianshan.chat.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.qianshan.chat.component.exception.ChatException;
import org.qianshan.chat.component.exception.CodeEnum;
import org.qianshan.chat.component.protocol.*;
import org.qianshan.chat.component.Session;
import org.qianshan.chat.server.SessionManager;
import org.qianshan.chat.component.utils.ChannelAttrUtils;

import java.util.HashMap;
import java.util.Map;

@ChannelHandler.Sharable
public class ChatHandler extends SimpleChannelInboundHandler<Packet> {

    private static final Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap = new HashMap<Byte, SimpleChannelInboundHandler<? extends Packet>>();

    static {
        handlerMap.put(PayloadTypeEnum.PING.getType(), PingHandler.INSTANCE);
        handlerMap.put(PayloadTypeEnum.SEND_MESSAGE.getType(), SendMessageHandler.INSTANCE);
        handlerMap.put(PayloadTypeEnum.RECEIVE_MESSAGE_ACK.getType(), ReceiveMessageAckHandler.INSTANCE);
    }

    public static final ChatHandler INSTANCE = new ChatHandler();

    private ChatHandler() {
    }

    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        SimpleChannelInboundHandler handler = handlerMap.get(msg.getType());
        if (handler != null){
            //将消息分发给对应的handler
            handler.channelRead(ctx, msg);
        }else {
            throw new ChatException(CodeEnum.UNKNOW_PACKET.getCode(), msg.getPacketId());
        }
    }

    /**
     * 处理channel关闭
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Session session = ChannelAttrUtils.getSession(ctx.channel());
        if (session != null){
            System.out.println(String.format("用户【%s】退出登录", session.getUserName()));
            SessionManager.logout(session.getUserName());
        }
    }

    /**
     * 处理异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof ChatException){
            ChatException chatException = (ChatException) cause;
            if (chatException.getPacketId() != null && ctx.channel().isActive()){
                Packet response = new Packet(PayloadTypeEnum.ERROR.getType(), false, false, chatException.getPacketId(), new ErrorPayload(chatException));
                //将异常信息发送给客户端
                ctx.channel().writeAndFlush(response);
                System.out.println(chatException);
                return;
            }
        }

        cause.printStackTrace();
    }
}

package org.qianshan.chat.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.qianshan.chat.client.ShutdownHook;
import org.qianshan.chat.client.UserInput;
import org.qianshan.chat.component.Session;
import org.qianshan.chat.component.protocol.ErrorPayload;
import org.qianshan.chat.component.protocol.Packet;
import org.qianshan.chat.component.protocol.PayloadTypeEnum;
import org.qianshan.chat.component.utils.ChannelAttrUtils;

import java.util.concurrent.TimeUnit;

@ChannelHandler.Sharable
public class LoginAckHandler extends SimpleChannelInboundHandler<Packet> {

    public static final LoginAckHandler INSTANCE = new LoginAckHandler();

    private LoginAckHandler() {
    }

    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        if (!ChannelAttrUtils.hasLogin(ctx.channel()) && msg.getType() != PayloadTypeEnum.LOGIN_ACK.getType()){
            if (msg.getType() == PayloadTypeEnum.ERROR.getType()){
                ErrorPayload errorPayload = (ErrorPayload) msg.getPayload();
                System.err.println(errorPayload);
                ctx.channel().close();
                return;
            }

            System.err.println("聊天异常，请重连");
            ctx.channel().close();
        }else if (msg.getType() == PayloadTypeEnum.LOGIN_ACK.getType()){
            System.out.println("登录成功");
            ChannelAttrUtils.login(ctx.channel());
            Session session = ChannelAttrUtils.getSession(ctx.channel());

            //新起线程处理用户输入
            Thread inputThread = new Thread(new UserInput(session));
            inputThread.setDaemon(true);
            inputThread.start();
            scheduleSendHeartBeat(ctx, session);
            ctx.channel().pipeline().remove(this);

            Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook(ctx.channel())));
        }
    }

    /**
     * 发送心跳包
     * @param ctx
     * @param session
     */
    private void scheduleSendHeartBeat(final ChannelHandlerContext ctx, final Session session){
        ctx.executor().schedule(new Runnable() {
            public void run() {
                if (ctx.channel().isActive()){
                    Packet packet = new Packet(PayloadTypeEnum.PING.getType(), true, true, session.getNextPacketId(), null);
                    session.sendMessage(packet);
                    scheduleSendHeartBeat(ctx, session);
                }
            }
        }, 15, TimeUnit.SECONDS);
    }
}

package org.qianshan.chat.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.qianshan.chat.component.Session;
import org.qianshan.chat.component.utils.ChannelAttrUtils;
import org.qianshan.chat.server.SessionManager;

import java.util.concurrent.TimeUnit;

/**
 * 心跳handler
 */
public class HeartBeatHandler extends IdleStateHandler {

    private static final int READER_IDLE_TIME = 45;

    public HeartBeatHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        Session session = ChannelAttrUtils.getSession(ctx.channel());
        SessionManager.logout(session.getUserName());
    }
}

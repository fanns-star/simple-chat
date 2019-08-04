package org.qianshan.chat.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.qianshan.chat.server.SessionManager;
import org.qianshan.chat.component.utils.ChannelAttrUtils;

/**
 * 鉴权的handler
 */
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {

    public static final AuthHandler INSTANCE = new AuthHandler();

    private AuthHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (ChannelAttrUtils.hasLogin(ctx.channel())){
            //用户已登录
            super.channelRead(ctx, msg);
        }else {
            //用户未登录
            SessionManager.logout(ChannelAttrUtils.getUserName(ctx.channel()));
        }
    }
}

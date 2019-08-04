package org.qianshan.chat.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.qianshan.chat.server.task.CheckLoginTask;

import java.util.concurrent.TimeUnit;

@ChannelHandler.Sharable
public class BuildConnectionHandler extends ChannelInboundHandlerAdapter {

    public static final BuildConnectionHandler INSTANCE = new BuildConnectionHandler();

    private BuildConnectionHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //添加检查用户是否登录的任务，30s后还未登录，则关闭连接
        ctx.executor().schedule(new CheckLoginTask(ctx.channel()), 30, TimeUnit.SECONDS);
        ctx.channel().pipeline().remove(this);
    }
}

package org.qianshan.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.qianshan.chat.client.handler.LoginAckHandler;
import org.qianshan.chat.client.handler.PacketDecodeHandler;
import org.qianshan.chat.client.handler.PacketEncodeHandler;
import org.qianshan.chat.client.handler.ReceiveMessageHandler;
import org.qianshan.chat.client.listener.ConnectListener;

public class Client {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();

        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 5, 4));
                        ch.pipeline().addLast(PacketDecodeHandler.INSTANCE);
                        ch.pipeline().addLast(LoginAckHandler.INSTANCE);
                        ch.pipeline().addLast(ReceiveMessageHandler.INSTANCE);
                        ch.pipeline().addLast(PacketEncodeHandler.INSTANCE);
                    }
                });

        bootstrap.connect("127.0.0.1",18180).addListener(new ConnectListener());
    }

}

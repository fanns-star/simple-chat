package org.qianshan.chat.client;


import io.netty.channel.Channel;

public class ShutdownHook implements Runnable{

    private Channel channel;

    public ShutdownHook(Channel channel) {
        this.channel = channel;
    }

    public void run() {
        if (channel.isActive()){
            channel.close();
        }
    }
}

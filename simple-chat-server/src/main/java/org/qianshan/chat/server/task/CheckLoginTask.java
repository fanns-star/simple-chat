package org.qianshan.chat.server.task;

import io.netty.channel.Channel;
import org.qianshan.chat.component.utils.ChannelAttrUtils;

public class CheckLoginTask implements Runnable {

    private Channel channel;

    public CheckLoginTask(Channel channel) {
        this.channel = channel;
    }

    public void run() {
        if (!ChannelAttrUtils.hasLogin(channel)){
            //关闭连接
            channel.close();
        }
    }
}

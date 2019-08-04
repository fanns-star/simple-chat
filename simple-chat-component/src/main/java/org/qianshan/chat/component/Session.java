package org.qianshan.chat.component;

import io.netty.channel.Channel;
import org.qianshan.chat.component.protocol.Packet;

public class Session {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 性别
     */
    private byte gender;

    /**
     * packetId
     */
    private int packetId = 0;

    /**
     * channel
     */
    private Channel channel;

    public Session(String userName, byte gender, Channel channel) {
        this.userName = userName;
        this.gender = gender;
        this.channel = channel;
    }

    public String getUserName() {
        return userName;
    }

    public byte getGender() {
        return gender;
    }

    /**
     * 获取下一个packetId
     * @return
     */
    public synchronized int getNextPacketId() {
        return ++packetId;
    }

    public Channel getChannel() {
        return channel;
    }

    public void sendMessage(Packet packet){
        this.channel.writeAndFlush(packet);
    }

    public void close(){
        this.channel.close();
    }
}

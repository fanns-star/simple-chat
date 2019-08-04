package org.qianshan.chat.component.protocol;

import lombok.Data;

/**
 * 消息的包
 */
@Data
public class Packet {

    /**
     * 对应payload的type
     */
    private byte type;

    /**
     * 是否是请求
     */
    private boolean request;

    /**
     * 请求是否需要回复
     */
    private boolean needReply;

    /**
     * packet对应的id
     */
    private long packetId;

    /**
     * 消息内容
     */
    private Payload payload;

    public Packet() {
    }

    public Packet(byte type, boolean request, boolean needReply, long packetId, Payload payload) {
        this.type = type;
        this.request = request;
        this.needReply = needReply;
        this.packetId = packetId;
        this.payload = payload;
    }
}

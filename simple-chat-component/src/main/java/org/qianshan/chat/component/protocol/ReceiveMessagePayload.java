package org.qianshan.chat.component.protocol;

import lombok.Data;

/**
 * 服务端给用户推送消息的payload
 */
@Data
public class ReceiveMessagePayload implements Payload{

    /**
     * 消息发送人
     */
    private String from;

    /**
     * 消息内容
     */
    private String content;

    public ReceiveMessagePayload() {
    }

    public ReceiveMessagePayload(String from, String content) {
        this.from = from;
        this.content = content;
    }
}

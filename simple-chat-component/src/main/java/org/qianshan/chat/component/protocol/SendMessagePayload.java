package org.qianshan.chat.component.protocol;

import lombok.Data;

/**
 * 发送消息的payload
 */
@Data
public class SendMessagePayload implements Payload{

    /**
     * 消息发送给谁
     */
    private String to;

    /**
     * 消息内容
     */
    private String content;

    public SendMessagePayload() {
    }

    public SendMessagePayload(String to, String content) {
        this.to = to;
        this.content = content;
    }
}

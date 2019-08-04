package org.qianshan.chat.component.protocol;

import lombok.Data;
import org.qianshan.chat.component.exception.ChatException;

/**
 * 错误消息的payload
 */
@Data
public class ErrorPayload implements Payload{

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误消息
     */
    private String message;

    public ErrorPayload() {
    }

    public ErrorPayload(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorPayload(ChatException chatException) {
        this.code = chatException.getMetaCode().getCode();
        this.message = chatException.getMetaCode().getMessage();
    }
}

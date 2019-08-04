package org.qianshan.chat.component.exception;

import java.io.Serializable;

public class ChatException extends Exception implements Serializable {

    private static final long serialVersionUID = 1L;

    private MetaCode metaCode;

    private Long packetId;

    public ChatException(int code, String message, Long packetId) {
        super(message);
        this.metaCode = new MetaCode(code, message);
        this.packetId = packetId;
    }

    public ChatException(int code,String message,  Long packetId,Throwable cause) {
        super(message, cause);
        this.metaCode = new MetaCode(code, message);
        this.packetId = packetId;
    }

    public ChatException(MetaCode metaCode,  Long packetId) {
        super(metaCode.getMessage());
        this.metaCode = metaCode;
        this.packetId = packetId;
    }

    public ChatException(MetaCode metaCode,  Long packetId,Throwable cause) {
        super(metaCode.getMessage(), cause);
        this.metaCode = metaCode;
        this.packetId = packetId;
    }

    public MetaCode getMetaCode() {
        return metaCode;
    }

    public Long getPacketId() {
        return packetId;
    }
}

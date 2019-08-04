package org.qianshan.chat.component.protocol;

public enum  PayloadTypeEnum {
    PING((byte)1),
    PONG((byte)2),
    LOGIN((byte)3),
    LOGIN_ACK((byte)4),
    SEND_MESSAGE((byte)5),
    SEND_MESSAGE_ACK((byte)6),
    RECEIVE_MESSAGE((byte)7),
    RECEIVE_MESSAGE_ACK((byte)8),
    ERROR((byte)63)
    ;

    private byte type;

    PayloadTypeEnum(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }
}

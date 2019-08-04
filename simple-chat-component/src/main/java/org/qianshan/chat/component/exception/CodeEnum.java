package org.qianshan.chat.component.exception;

public enum  CodeEnum {
    NULL_USER_NAME(new MetaCode(1,"用户名为空")),
    USER_NAME_TOO_LONG(new MetaCode(2,"用户名太长")),
    USER_NAME_EXIST(new MetaCode(3,"用户名被占用")),
    PASSWORD_ERROR(new MetaCode(4,"密码错误")),
    NULL_MESSAGE_CONTENT(new MetaCode(10,"消息内容不能为空")),
    UNKNOW_PACKET(new MetaCode(50, "未知Packet类型"))
    ;

    private MetaCode code;

    CodeEnum(MetaCode code) {
        this.code = code;
    }

    public MetaCode getCode() {
        return code;
    }
}

package org.qianshan.chat.component.protocol;

import lombok.Data;

/**
 * 登录消息的payload
 */
@Data
public class LoginPayload implements Payload{

    /**
     * 用户名
     */
    private String userName;

    /**
     * 性别
     */
    private byte gender;

    /**
     * 密码
     */
    private String password;

    public LoginPayload() {
    }

    public LoginPayload(String userName, byte gender, String password) {
        this.userName = userName;
        this.gender = gender;
        this.password = password;
    }
}

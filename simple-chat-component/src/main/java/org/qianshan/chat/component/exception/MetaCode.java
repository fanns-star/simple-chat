package org.qianshan.chat.component.exception;

import java.io.Serializable;

public class MetaCode implements Serializable {

    private static final long serialVersionUID = 1L;

    public MetaCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;

    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

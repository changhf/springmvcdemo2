package com.changhf.common.exception;

import com.changhf.common.message.MessageFormatter;

/**
 * @author changhuafeng
 * @version V1.0.0
 * @since 2017/09/15
 */
public class DemoRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -3467200232789888242L;

    private String code;

    public DemoRuntimeException() {
    }

    public DemoRuntimeException(String code, String message, Object... args) {
        super(MessageFormatter.build(message, args));
        this.code = code;
    }

    public DemoRuntimeException(String code, Throwable cause, String message, Object... args) {
        super(MessageFormatter.build(message, args), cause);
        this.code = code;
    }

    public DemoRuntimeException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

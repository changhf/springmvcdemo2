package com.changhf.common.exception;

/**
 * DemoBusinessException
 *
 * @author <a href="mailto:longlin.ll@alibaba-inc.com">根宝</a>
 * @version V1.0.0
 * @since 2017/3/11
 */
public class DemoBusinessException extends DemoRuntimeException {

    private static final long serialVersionUID = -3467200232789888242L;

    public DemoBusinessException() {
    }

    public DemoBusinessException(String code, String message, Object... args) {
        super(code, message, args);
    }

    public DemoBusinessException(String code, Throwable cause, String message, Object... args) {
        super(code, cause, message, args);
    }

    public DemoBusinessException(String code, Throwable cause) {
        super(code, cause);
    }
}

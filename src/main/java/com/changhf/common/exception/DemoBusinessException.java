package com.changhf.common.exception;

/**
 * @author <a href="mailto:wb-chf309549@alibaba-inc.com">常华锋</a>
 * @version V1.0.0
 * @since 2017/09/15
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

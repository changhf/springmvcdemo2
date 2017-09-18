package com.changhf.utils;

/**
 * 手机端recode值
 *
 */
public enum RecodeEnumUtils {
    COOKIE_NOT_EXIST(-1, "cookie不存在"),
    SESSION_ID_NULL(-2, "sessionId 为空"),
    NO_LOGIN(-3, "用户未登录");

    private int code;

    private String msg;

    private RecodeEnumUtils(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

}

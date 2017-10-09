package com.changhf.common.session;

import java.io.Serializable;

/**
 * 响应基类
 */
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 5476021138590546022L;
    private static final int SUCCESS_CODE = 200;
    public static final int SYSTEM_ERROR_CODE = 500;
    private String requestId;
    private int code;
    private T data;
    private String exception;
    private String message;
    /**
     * 请求耗时
     */
    private long elapseTime;
    private long timestamp;

    public BaseResponse() {
        this.code = 200;
    }

    public BaseResponse(T data) {
        this.code = 200;
        this.data = data;
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getElapseTime() {
        return elapseTime;
    }

    public void setElapseTime(long elapseTime) {
        this.elapseTime = elapseTime;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

package com.changhf.common.session;

import java.io.Serializable;

/**
 * 请求基础类
 */
public class BaseRequest implements Serializable {


    private static final long serialVersionUID = -4502904087732563319L;
    private String requestId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}
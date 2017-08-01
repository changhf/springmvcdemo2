package com.changhf.common.session;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 往redis缓存写入的SessionCache
 * @author 曾斌
 *
 */
public class SessionCache implements Serializable {

    private static final long serialVersionUID = 5679857937499865045L;

    private String sessionId;

    private Object sessionUser;

    public Map<String, Object> attributes = Maps.newHashMap();

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setAttribute(String name, Object obj) {
        attributes.put(name, obj);
    }

    /**
     * 获得属性
     * 
     * @param name
     * @return
     */
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * 删除
     * 
     * @param name
     * @return
     */
    public Object removeAttribute(String name) {
        return attributes.remove(name);
    }

    public Object getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(Object sessionUser) {
        this.sessionUser = sessionUser;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}

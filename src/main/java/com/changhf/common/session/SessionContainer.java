package com.changhf.common.session;

/**
 * session容器
 * @author LMB
 *
 */
public class SessionContainer {
    private static final ThreadLocal<SessionCache> SESSION_THREAD_LOCAL = new ThreadLocal<SessionCache>();

    public static SessionCache getSession() {
        return SESSION_THREAD_LOCAL.get();
    }

    public static void setSession(SessionCache sessionCache) {
        SESSION_THREAD_LOCAL.set(sessionCache);
    }

    /**
     * 清除缓存
     */
    public static void clear() {
        SESSION_THREAD_LOCAL.set(null);
    }
}

package com.changhf.plugin.interceptor;

import com.changhf.common.session.SessionCache;
import com.changhf.common.session.SessionContainer;
import com.changhf.domain.SysUser;
import com.changhf.plugin.login.LoginRequired;
import com.changhf.utils.FarmUtils;
import com.changhf.utils.RecodeEnumUtils;
import com.changhf.utils.StringUtils;
import com.changhf.utils.WebUtil;
import com.changhf.utils.redis.CacheService;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    protected CacheService cacheService;
//    @Autowired
//    protected SysUserService sysUserService;

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @SuppressWarnings("rawtypes")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // String referer = request.getHeader("Referer");
//    	if(!WebUtils.valideCSRFAddress(referer)) {
//    		logger.error("=================referer===============" + referer);
//            WebUtil.returnJSON(response,
//                    "{\"recode\":" + RecodeEnumUtils.REFERER_ERROR.getCode() + ",\"msg\":\"" + RecodeEnumUtils.REFERER_ERROR.getMsg() + "\"}",
//                    "json");
//            return false;
//    	}
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            LoginRequired annotation = method.getAnnotation(LoginRequired.class);
            boolean needLogin = true;
            boolean needAuth = true;
            if (annotation != null) {
                needLogin = annotation.needLogin();
                needAuth = annotation.needAuth();
            }
            if (needLogin) {
                SessionCache sessionCache = SessionContainer.getSession();
                if (sessionCache == null) {
                    String sessionId = request.getParameter(FarmUtils.USER_SESSION_ID);
                    Cookie[] cookies = request.getCookies();
                    if (null == cookies) {
                        WebUtil.returnJSON(response,
                                "{\"recode\":" + RecodeEnumUtils.COOKIE_NOT_EXIST.getCode() + ",\"msg\":\"" + RecodeEnumUtils.COOKIE_NOT_EXIST.getMsg() + "\"}",
                                "json");
                        return false;
                    }
                    for (int i = 0; i < cookies.length; i++) {
                        if (FarmUtils.USER_SESSION_ID.equals(cookies[i].getName())) {
                            sessionId = cookies[i].getValue();
                        }
                    }
                    if (StringUtils.isEmpty(sessionId)) {
                        WebUtil.returnJSON(response,
                                "{\"recode\":" + RecodeEnumUtils.SESSION_ID_NULL.getCode() + ",\"msg\":\"" + RecodeEnumUtils.SESSION_ID_NULL.getMsg() + "\"}",
                                "json");
                        return false;
                    }
                    sessionCache = (SessionCache) cacheService.getObj(sessionId);
                    if (sessionCache == null) {
                        WebUtil.returnJSON(response,
                                "{\"recode\":" + RecodeEnumUtils.NO_LOGIN.getCode() + ",\"msg\":\"" + RecodeEnumUtils.NO_LOGIN.getMsg() + "\"}", "json");
                        return false;
                    }
                    SysUser sysUser = new SysUser();
                    BeanUtils.populate(sysUser, (Map) sessionCache.getSessionUser());
                    // String requestUrl = request.getServletPath();
                    // if (needAuth && !this.hasAuthority(sysUser, requestUrl)) {
                    //     WebUtil.returnJSON(response,
                    //             "{\"recode\":" + RecodeEnumUtils.NO_AUTHORITY.getCode() + ",\"msg\":\"" + RecodeEnumUtils.NO_AUTHORITY.getMsg() + "\"}",
                    //             "json");

                    //     return false;
                    // }
                    sessionCache.setSessionUser(sysUser);
                    SessionContainer.setSession(sessionCache);
                }
            }
        }
        return true;
    }

    // private boolean hasAuthority(SysUser sysUser, String requestUrl) throws InvocationTargetException, IllegalAccessException {
    //     if (UserTypeEnum.SUPER_USER.getUserType().equals(sysUser.getUserType())) {
    //         return true;
    //     }
    //     int count = this.sysUserService.validateUserAuthority(requestUrl, sysUser.getUserId());
    //     if (count > 0) {
    //         return true;
    //     }
    //     return false;
    // }
}

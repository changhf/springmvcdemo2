package com.changhf.utils.http;

import java.net.URI;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.changhf.common.Platform;

/**
 * WebUtils.java
 * @author liMengbiao
 *
 */
public class WebUtils {

    public static final String DESKEY = "adf&**^%~@(k1";
    public static String[] WHITELIST = { "app.walking.komect.com", "data.walking.komect.com", "oss.walking.komect.com", "walking.komect.com", "www.hiifit.com", "www.hiifit.net" };


    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    public static String getCurrentIP(HttpServletRequest request) {
        String result = request.getHeader("X-Real-IP");
        if (result == null || result.length() == 0 || "unknown".equalsIgnoreCase(result)) {
            result = request.getHeader("x-forwarded-for");
        }
        if (result == null || result.length() == 0 || "unknown".equalsIgnoreCase(result)) {
            result = request.getHeader("Proxy-Client-IP");
        }
        if (result == null || result.length() == 0 || "unknown".equalsIgnoreCase(result)) {
            result = request.getHeader("WL-Proxy-Client-IP");
        }
        if (result == null || result.length() == 0 || "unknown".equalsIgnoreCase(result)) {
            result = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (!StringUtils.isEmpty(result)) {
            if (result.indexOf(".") != -1) { // 没有"."肯定是非IPv4格式
                Pattern pat = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
                Matcher mat = pat.matcher(result);
                result = null;
                while (mat.find()) {
                    // System.out.println(111);
                    result = mat.group(0);
                    break;
                }
            } else {
                result = null;
            }
        }

        if (StringUtils.isEmpty(result)) {
            result = request.getRemoteAddr();
        }
        return result;
    }

    public static String getSessionId(String mobile, Platform platform, String ipAddress, String desKey) {
        Assert.notNull(mobile, "登陆账号不能为空");
        Assert.notNull(platform, "platform不能为空");
        StringBuffer sb = new StringBuffer();
        try {
            sb.append("mobile=").append(DesUtils.encrypt(mobile, desKey)).append("|");
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append("platform=").append(platform.getPlatName()).append("|");
        sb.append("ipAddress=").append(ipAddress);
        sb.append("timestamp=").append(System.currentTimeMillis());
        String base64Mobile = Base64Util.encode(sb.toString());
        return base64Mobile;
    }

    public static String getIdentifyCodeKey(HttpServletRequest request, String desKey) {
        String ipAdd = getCurrentIP(request);
        String sessionId = request.getSession().getId();
        return Base64Util.encode(ipAdd + "|" + sessionId);
    }
    
    /**
     * valideCSRFAddress
     * @param referer
     * @return
     */
    public static boolean valideCSRFAddress(String referer) {
    	if(StringUtils.isEmpty(referer)) {
    		return false;
    	}
        if (referer.contains("?")) {
            referer = referer.substring(0, referer.indexOf("?"));
        }
        URI referUri = null;
        try {
            referUri = new URI(referer);
        } catch (Exception e) {
        	e.printStackTrace();
            return false;
        }
        String domain = referUri.getHost().toLowerCase();
        for (int i = 0; i < WHITELIST.length; i++) {
            if (WHITELIST[i].toLowerCase().equals(domain)) {
                return true;
            }
        }
        return false;
    }
}

package com.changhf.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * @File: WebUtil.java
 */

public class WebUtil {

    public static void returnJSON(HttpServletResponse response, String jsonData, String dataType) {
        if ("text".equals(dataType)) {
            response.setContentType("text/html;charset=UTF-8");
        } else {
            response.setContentType("application/json;charset=UTF-8");
        }
        response.setHeader("Charset", "UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.write(jsonData);
        out.flush();
    }

    /**
     */
    public static String escapeParamString(String value) {

        if (value == null)
            return "";

        value = StringUtils.replace(value, "|", "");
        value = StringUtils.replace(value, "&amp;", "");
        //value = StringUtils.replace(value, ";", "");
        value = StringUtils.replace(value, "$", "");
//		value = StringUtils.replace(value, "%", "");
        //value = StringUtils.replace(value, "@", "");
        value = StringUtils.replace(value, "'", "");
        value = StringUtils.replace(value, "\"", "");
        value = StringUtils.replace(value, "\\'", "");
        value = StringUtils.replace(value, "&lt;", "");
        value = StringUtils.replace(value, "&gt;", "");
        value = StringUtils.replace(value, "<", "");
        value = StringUtils.replace(value, ">", "");
        //value = StringUtils.replace(value, "(", "");
        //value = StringUtils.replace(value, ")", "");
        //value = StringUtils.replace(value, "+", "");
        value = StringUtils.replace(value, "\n", "");
        value = StringUtils.replace(value, "\r", "");
        //value = StringUtils.replace(value, ",", "");
        value = StringUtils.replace(value, "\\", "");

        return value;
    }

    @SuppressWarnings("rawtypes")
    public static String getSearchParamsString(HttpServletRequest servletRequest, String searchParamsString, String prefix, String... equalsNames) {
        StringBuilder sb = new StringBuilder();
        Enumeration paramNames = servletRequest.getParameterNames();
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if ("".equals(prefix) || paramName.startsWith(prefix) || (equalsNames.length > 0 && ArrayUtils.contains(equalsNames, paramName))) {
                String[] values = servletRequest.getParameterValues(paramName);
                if (values != null && values.length > 0) {
                    for (String value : values) {
                        if (!StringUtils.isBlank(value)) {
                            try {
                                sb.append("&").append(paramName).append("=").append(URLEncoder.encode(value, "UTF-8"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        sb.append(searchParamsString);
        if (sb.length() > 0)
            return sb.substring(1);
        else
            return "";
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix, String... equalsNames) {
        Enumeration<String> paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<String, Object>();
        if (prefix == null) {
            prefix = "";
        }
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            if ("".equals(prefix) || paramName.startsWith(prefix) || (equalsNames.length > 0 && ArrayUtils.contains(equalsNames, paramName))) {
                String[] values = request.getParameterValues(paramName);
                if (values == null || values.length == 0) {
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(paramName, values);
                } else {
                    params.put(paramName, values[0]);
                }
            }
        }
        return params;
    }


}

package com.changhf.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version V1.0.0
 * @since 2017/09/14
 */
public abstract class StringUtils {
    /**
     * processSqlQueryLike
     *
     * @param string
     * @return
     */
    public final static String processSqlQueryLike(String string) {
        if (org.springframework.util.StringUtils.hasText(string)) {
            string = string.replace("%", "\\%").replace("_", "\\_");
            string = "%" + string + "%";
        }
        return string;
    }

    /**
     * encode
     *
     * @param value
     * @return
     */
    public final static String encode(String value) {
        if (!org.springframework.util.StringUtils.hasText(value)) {
            return value;
        }
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * decode
     *
     * @param value
     * @return
     */
    public final static String decode(String value) {
        if (!org.springframework.util.StringUtils.hasText(value)) {
            return value;
        }
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断一个字符串是否包含数字
     *
     * @param content 需要判断的字符串
     * @return
     */
    public static boolean hasDigit(String content) {

        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 验证是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    public static boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }

    /**
     * 字符串空处理，去除首尾空格 如果str为null，返回"",否则返回str
     *
     * @param str
     * @return
     */
    public static String operateNull(String str) {
        if (str == null) {
            return "";
        }
        return str.trim();
    }

    /**
     * 判断一个密码是否符合规则:密码长度为8~20位，至少包含字母，数字或符号中的两种
     *
     * @param password 需要判断的字符串
     * @return
     */
    public static boolean validatePwdRegex(String password) {

        Pattern pattern = Pattern.compile("^((?=.*[0-9])(?=.*[a-zA-Z])|(?=.*[0-9])(?=.*[^a-zA-Z0-9])|(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9])).{8,20}$");
        Matcher m = pattern.matcher(password);
        return m.matches();
    }

    /**
     * 判断是否包含中文
     *
     * @param str
     * @return
     */
    public static boolean hasChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str == null ? "" : str);
        return m.find();
    }

    /**
     * 判断字符串是否不为整数
     *
     * @param str
     * @return
     */
    public static boolean isNotInteger(String str) {
        str = StringUtils.operateNull(str);
        if (isEmpty(str)) {
            return true;
        }
        Pattern regex = Pattern.compile("\\d*");
        Matcher matcher = regex.matcher(str);
        boolean isMatched = matcher.matches();
        return !isMatched;
    }

    /**
     * 是否包含指定的字符串
     *
     * @param str
     * @param searchStr
     * @return
     */
    public static boolean contains(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        return str.indexOf(searchStr) >= 0;
    }
}

package com.changhf.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author yinhu
 *
 */
public class RegExUtils {

    /**
     * 用户名正则表达式
     * @param userName
     * @return
     */
    public static boolean matUserName(String userName) {

        //限20位字符，支持中英文、数字、减号或下划线，不得重名
        String regEx = "^[-_0-9A-Za-z\\u4e00-\\u9fa5]{1,20}$";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(userName);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    /**
     * 用户真实名正则表达式
     * @param userName
     * @return
     */
    public static boolean matUserRealName(String userRealName) {

        //限20位字符，支持中英文、数字、减号或下划线，不得重名
        String regEx = "^([\\u4E00-\\u9FA5]|[a-zA-Z]){1,20}$";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(userRealName);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    /**
     * 员工编号正则表达式
     * @param memberCode
     * @return
     */
    public static boolean matMemberCode(String memberCode) {

        //限20位字符，支持中英文、数字、减号或下划线，不得重名
        String regEx = "^[0-9A-Za-z]{1,20}$";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(memberCode);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    /**
     * 用户名正则表达式
     * @param userName
     * @return
     */
    public static boolean checkMemberName(String memberName) {

        //限20位字符，支持中英文、数字、减号或下划线，不得重名
        String regEx = "^[*-_0-9A-Za-z\\u4e00-\\u9fa5]{1,16}$";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(memberName);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    /**
     * 用户名正则表达式
     * @param userName
     * @return
     */
    public static boolean matPassword(String password) {

        //密码长度为8~20位，至少包含字母，数字或符号中的两种
        String regEx = "^((?=.*[0-9])(?=.*[a-zA-Z])|(?=.*[0-9])(?=.*[^a-zA-Z0-9])|(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9])).{8,20}$";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(password);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    /**
     * 检验手机号
     * 
     * @param memberPhone
     * @return
     */
    public static boolean checkPhone(String phone) {
        phone = StringUtils.operateNull(phone);
        Pattern regex = Pattern.compile("^((1[3,5,4,7,8][0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher matcher = regex.matcher(phone);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

}

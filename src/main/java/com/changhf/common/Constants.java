package com.changhf.common;
/**
 * 常量类
 * @author changhf
 *
 */
public class Constants {
	public static final String MOBILE_RECODE = "recode";
	public static final String MOBILE_MSG = "msg";
	public static final String MOBILE_DATA = "data";
	public static final String LIST = "list";
	
	/**
	 * notice相关
	 */
//	public static final byte NOTICE_RECEIVE = 1;
	public static final byte NOTICE_SMS = 1;//发短信
	public static final byte NOTICE_EMAIL = 2;//发邮件
	public static final byte NOTICE_MESSAGE = 3;//站内信
	public static final int ADMIN_ID = 1;//消息发送方
	 /**
     * excel_date
     */
    public static final String EXCEL_TYPE_DATE = "DATE";
    /**
     * 每张sheet最大数量
     */
    public static final int MAX_EXCEL_CELL = 50000;
	/**
     * 分隔符
     */
    public static final String SEPERATOR_FLAG = "$";
    public static final String SEND_CODE_FIND_PWD = "send_code_find_pwd_";//找回密码的key
    public static final String SEND_CODE_USER_REGISTER = "send_code_user_register_";//用户注册的key
    public static final String VERIFY_FIND_PWD_CODE_RESULT = "verify_find_pwd_code_result_";//验证结果的key
    public static final String VERIFY_USER_REGISTER_CODE_RESULT = "verify_user_register_code_result_";//验证结果的key
}

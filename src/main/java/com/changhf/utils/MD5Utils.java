package com.changhf.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MD5加密工具类
 */
public class MD5Utils {
	private final static Logger logger = LoggerFactory.getLogger(MD5Utils.class);
	/** 十六进制下数字到字符的映射数组. */
	private final static String[] HEXDIGITS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	/**
	 * 
	 * Description：对字符串进行加密.
	 *
	 * @author name：lljqiu
	 *         <p>
	 *         ============================================
	 *         </p>
	 *         Modified No： Modified By： Modified Date： Modified Description:
	 *         <p>
	 *         ============================================
	 *         </p>
	 * @param originString
	 *            the origin string
	 * @return String
	 */
	private static String encodeByMD5(String originString) {
		if (originString != null) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] results = md.digest(originString.getBytes());
				String resultString = byteArrayToHexString(results);
				return resultString.toUpperCase();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 
	 * Description：转换字节数组为16进制字串 .
	 *
	 * @author name：lljqiu
	 *         <p>
	 *         ============================================
	 *         </p>
	 *         Modified No： Modified By： Modified Date： Modified Description:
	 *         <p>
	 *         ============================================
	 *         </p>
	 * @param b
	 *            字节数组
	 * @return String 十六进制字串
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 
	 * Description：将一个字节转化成16进制形式的字符串 .
	 *
	 * @author name：lljqiu
	 *         <p>
	 *         ============================================
	 *         </p>
	 *         Modified No： Modified By： Modified Date： Modified Description:
	 *         <p>
	 *         ============================================
	 *         </p>
	 * @param b
	 *            the b
	 * @return String
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return HEXDIGITS[d1] + HEXDIGITS[d2];
	}

	/**
	 * 
	 * Description：调用MD5对输入的字符串进行加密.
	 *
	 * @author name：lljqiu
	 *         <p>
	 *         ============================================
	 *         </p>
	 *         Modified No： Modified By： Modified Date： Modified Description:
	 *         <p>
	 *         ============================================
	 *         </p>
	 * @param inputString
	 *            用户输入的字符串
	 * @return String 加密过后的字符串
	 */
	public static String createEncryption(String inputString) {
		return encodeByMD5(inputString);
	}

	/**
	 * 
	 * 验证输入的密码是否正确 .
	 *
	 * @param password
	 *            真正的密码（加密后的真密码）
	 * @param inputString
	 *            输入的字符串
	 * @return true, if successful
	 */
	public static boolean authenticatePassword(String password, String inputString) {
		return password.equals(encodeByMD5(inputString));
	}
	/**
	 * 加密
	 * @param pass
	 * @return
	 */
	public static String encodePassword(String pass) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(pass.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			logger.error("md5:", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("md5:", e);
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString(); // pass
	}
	public static void main(String[] args) {
		System.out.println(MD5Utils.encodePassword("123321abc"));
	}
}

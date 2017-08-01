package com.changhf.service.code;

import java.util.Map;

public interface CodeService {
	/**
	 * 验证验证码
	 * @param mobile
	 * @param sendCode
	 * @param codeType 验证码类型：1，注册；2找回密码
	 * @return
	 */
	public Map<String, Object> verifySMSCode(String mobile, String sendCode, Integer codeType);
}

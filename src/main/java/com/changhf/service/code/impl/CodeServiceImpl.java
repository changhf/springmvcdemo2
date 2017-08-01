package com.changhf.service.code.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changhf.common.Constants;
import com.changhf.common.ResponseMessageMap;
import com.changhf.common.SmsCodeTypeEnum;
import com.changhf.common.session.SessionCache;
import com.changhf.service.code.CodeService;
import com.changhf.utils.redis.RedisService;
@Service("codeService")
public class CodeServiceImpl implements CodeService {
	@Autowired
	private RedisService redisService;

	@Override
	public Map<String, Object> verifySMSCode(String mobile, String sendCode, Integer codeType) {
		SmsCodeTypeEnum codeTypeEnum = SmsCodeTypeEnum.getEnumByCodeType(codeType);

		String codeKey = null;// 缓存的key
		String resultKey = null;// 检验验证码是否输入正确，然后存入缓存，以便下一步的操作
		switch (codeTypeEnum) {
		case USER_REGISTER_TYPE:
			codeKey = Constants.SEND_CODE_USER_REGISTER + mobile;
			resultKey = Constants.VERIFY_USER_REGISTER_CODE_RESULT + mobile;
			break;
		case FIND_PWD_TYPE:
			codeKey = Constants.SEND_CODE_FIND_PWD + mobile;
			resultKey = Constants.VERIFY_FIND_PWD_CODE_RESULT + mobile;
			break;
		default:
			break;
		}
		SessionCache cache = (SessionCache) redisService.getObj(codeKey);
		String code = cache.getAttribute(codeKey).toString();// 拿到缓存的验证码
		boolean verifyResult = sendCode.equals(code);// 验证码是否正确
		cache.setAttribute(resultKey, verifyResult);
		if (verifyResult) {
			cache.removeAttribute(codeKey);
			redisService.setObj(codeKey, cache);
			return ResponseMessageMap.successMap();
		} else {
			redisService.setObj(codeKey, cache);
			return ResponseMessageMap.selfDefineMap(-1, "输入的验证码错误");
		}

	}

}

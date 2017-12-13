package com.changhf.controller.user;

import com.changhf.domain.User;
import com.changhf.plugin.login.LoginRequired;
import com.changhf.service.rocketmq.GuavaCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.changhf.common.Platform;
import com.changhf.controller.base.WebActionSupport;
import com.changhf.service.user.UserService;
import com.changhf.utils.ParamUtil;
import com.changhf.utils.http.WebUtils;

import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping(value = "login")
public class LoginController extends WebActionSupport {
	@Autowired
	protected UserService userService;
	@Autowired
	private GuavaCache cache;

	@RequestMapping(value = "pwdLogin")
	@LoginRequired(needLogin=false)
	@ResponseBody
	public void pwdLogin() {
		
		String mobile = ParamUtil.getStringParameter(request, "mobile", null);
		String password = ParamUtil.getStringParameter(request, "password", null);
		String ipAddress = WebUtils.getCurrentIP(request);
		String sessionId = WebUtils.getSessionId(mobile, Platform.NEWCITY_CMS, ipAddress, WebUtils.DESKEY);
		returnFastJSON(userService.pwdLogin(mobile,password,sessionId,ipAddress));
		try {
			User user = cache.userCache.get(mobile);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}

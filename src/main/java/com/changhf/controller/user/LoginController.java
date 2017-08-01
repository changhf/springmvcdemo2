package com.changhf.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.changhf.common.Platform;
import com.changhf.controller.base.WebActionSupport;
import com.changhf.service.user.UserService;
import com.changhf.utils.ParamUtil;
import com.changhf.utils.http.WebUtils;

@Controller
@RequestMapping(value = "login")
public class LoginController extends WebActionSupport {
	@Autowired
	protected UserService userService;

	@RequestMapping(value = "pwdLogin")
	@ResponseBody
	public void pwdLogin() {
		
		String mobile = ParamUtil.getStringParameter(request, "mobile", null);
		String password = ParamUtil.getStringParameter(request, "password", null);
		String ipAddress = WebUtils.getCurrentIP(request);
		String sessionId = WebUtils.getSessionId(mobile, Platform.NEWCITY_CMS, ipAddress, WebUtils.DESKEY);
		returnFastJSON(userService.pwdLogin(mobile,password,sessionId,ipAddress));
	}
}

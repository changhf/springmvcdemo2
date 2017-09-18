package com.changhf.controller.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.changhf.controller.base.WebActionSupport;
import com.changhf.plugin.login.LoginRequired;
import com.changhf.service.code.CodeService;
import com.changhf.service.user.UserService;
import com.changhf.utils.ParamUtil;

@Controller
@RequestMapping(value = "user")
public class UserController extends WebActionSupport {
	@Autowired
	protected UserService userService;
	@Autowired
	protected CodeService codeService;

	@RequestMapping(value = "findUserById")
	@ResponseBody
	public void findUserById() {
		int id = ParamUtil.getIntParameter(request, "id", null);
		returnFastJsonExcludeProperties(userService.getUserById(id));
	}

	@RequestMapping(value = "listUser")
	@ResponseBody
	public void listUser() {
		Integer pageSize = ParamUtil.getIntParameter(request, "pageSize", 5);
		Integer pageOffset = ParamUtil.getIntParameter(request, "pageOffset", 0);

		Map<String, Object> map = userService.listUser(pageSize, pageOffset);

		returnFastJSON(map);
	}

	@RequestMapping(value = "verifyCode")
	@ResponseBody
	public void verifyCode() {
		String mobile = ParamUtil.getStringParameter(request, "mobile", null);
		String sendCode = ParamUtil.getStringParameter(request, "sendCode", null);
		Integer codeType = ParamUtil.getIntParameter(request, "codeType", null);// 只能为1或2

		Map<String, Object> rtnMap = codeService.verifySMSCode(mobile, sendCode, codeType);

		returnFastJSON(rtnMap);
	}

	@RequestMapping(value = "checkMobileExist")
	@ResponseBody
	public void checkMobile() {
		String mobile = ParamUtil.getStringParameter(request, "mobile", null);
		Map<String, Object> rtnMap = userService.checkMobileExist(mobile);

		returnFastJSON(rtnMap);
	}

	@RequestMapping(value = "register")
	@ResponseBody
	public void register() {
		String mobile = ParamUtil.getStringParameter(request, "mobile", null);
		String userName = ParamUtil.getStringParameter(request, "userName", null);
		String password = ParamUtil.getStringParameter(request, "password", null);
		Map<String, Object> map = userService.register(mobile, userName, password);

		returnFastJSON(map);
	}

	/**
	 * 找回密码
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "findPassword")
	@LoginRequired(needLogin = false)
	@ResponseBody
	public void findPassword() throws Exception {
		String mobile = ParamUtil.getFilteredParameter(request, "mobile", 0, "");
		// 发送短信验证码
		Map<String, Object> map = userService.findPassword(mobile);
		returnFastJSON(map);
	}

	@RequestMapping(value = "resetPassword")
	@ResponseBody
	public void resetPassword() {
		String mobile = ParamUtil.getStringParameter(request, "mobile", null);
		String password = ParamUtil.getStringParameter(request, "password", null);
		Map<String, Object> map = userService.updateUser(mobile, password);
		returnFastJSON(map);
	}

	@RequestMapping(value = "delUser")
	@ResponseBody
	public void delUser() {
		Integer id = ParamUtil.getIntParameter(request, "id", null);
		Map<String, Object> map = userService.delUserById(id);
		returnFastJSON(map);
	}
}

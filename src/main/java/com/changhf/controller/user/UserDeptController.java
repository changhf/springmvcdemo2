package com.changhf.controller.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.changhf.controller.base.WebActionSupport;
import com.changhf.service.user.UserDeptService;

@Controller
@RequestMapping("dept")
public class UserDeptController extends WebActionSupport {
	@Autowired
	protected UserDeptService userDeptService;

	@RequestMapping(value = "getDeptTree")
	@ResponseBody
	public void getDeptTree(){
		
		Map<String, Object> rtnMap = userDeptService.getDepTreeByDepth(1, null, null);
		returnFastJSON(rtnMap);
	}
}

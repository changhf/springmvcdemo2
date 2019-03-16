package com.changhf.controller;

import com.changhf.plugin.login.LoginRequired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author changhuafeng
 * @version V1.0.0
 * @since 2019/3/16
 */
@RequestMapping
@Controller
public class IndexController {
    @RequestMapping(value = "/index")
    @LoginRequired(needLogin = false)
    @ResponseBody
    public String index() {
        return "welcome!";
    }


    @RequestMapping(value = "/httpTest", method = RequestMethod.POST)
    @LoginRequired(needLogin = false)
    @ResponseBody
    public String httpTest(String name, String pwd) {
        System.out.println("name=" + name);
        System.out.println("pwd=" + pwd);
        return "http post request success!";
    }

    @RequestMapping(value = "/httpPostWithJson", method = RequestMethod.POST)
    @LoginRequired(needLogin = false)
    @ResponseBody
    public String httpPostWithJson(@RequestBody String param) {
        System.out.println("json request param" + param);
        return "http post request success!";
    }
}

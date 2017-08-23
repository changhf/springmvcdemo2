package com.changhf.controller.oss;

import com.changhf.controller.base.WebActionSupport;
import com.changhf.service.oss.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * oss操作静态资源
 *
 * @author <a href="mailto:wb-chf309549@alibaba-inc.com">常华锋</a>
 * @version V1.0.0
 * @since 2017/08/22
 */
@Controller
@RequestMapping(value = "oss")
public class OssController extends WebActionSupport {
    @Autowired
    private OssService ossService;

    @RequestMapping(value = "query")
    @ResponseBody
    public void query() {
        Map<String, Object> query = ossService.query();
        returnFastJsonExcludeProperties(query);
    }
}
